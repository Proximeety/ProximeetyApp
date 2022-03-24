package ch.proximeety.proximeety.data.sources

import android.app.Activity
import android.content.Context
import ch.proximeety.proximeety.R
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.util.SyncActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * Object used to access firebase.
 * @param context the application context.
 */
class FirebaseAccessObject(
    private val context: Context
) {

    companion object {
        /**
         * Intent request code for Google Authentication
         */
        private const val REQUEST_CODE_GOOGLE_SIGN_IN = 1
    }

    private var auth = Firebase.auth

    /**
     * Gets the currently authenticated user.
     * @return The authenticated user.
     */
    fun getAuthenticatedUser(): User? {
        return auth.currentUser?.let { firebaseToLocalUser(it) }
    }

    /**
     * Authenticate a new user with Google.
     * @param activity the current activity, needed to launch the Google authentication process.
     * @return The authenticated user.
     */
    suspend fun authenticateWithGoogle(activity: SyncActivity): User? {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.resources.getString(R.string.firebase_server_client_id))
            .requestEmail()
            .requestId()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(activity, gso)
        val intent = googleSignInClient.signInIntent

        googleSignInClient.signOut()

        val result = activity.waitForIntentResult({
            activity.startActivityForResult(intent, REQUEST_CODE_GOOGLE_SIGN_IN)
        }, REQUEST_CODE_GOOGLE_SIGN_IN)

        result?.also { activityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK) {
                try {
                    val account =
                        GoogleSignIn.getSignedInAccountFromIntent(activityResult.data).result
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    Tasks.await(auth.signInWithCredential(credential)).user?.also {
                        return firebaseToLocalUser(it)
                    }
                } catch (e: ApiException) {
                }
            }
        }

        return null
    }

    private fun firebaseToLocalUser(user: FirebaseUser): User {
        return User(id = user.uid, displayName = user.displayName ?: user.uid)
    }

    fun signOut() {
        auth.signOut()
    }
}