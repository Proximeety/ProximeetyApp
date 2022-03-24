package ch.proximeety.proximeety.data.sources

import android.app.Activity
import android.content.Context
import androidx.lifecycle.*
import ch.proximeety.proximeety.R
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.util.SyncActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import java.util.HashMap


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

        private const val TAG = "FIREBASE_ACCESS_OBJECT"

        private const val USER_PATH = "users"
        private const val USER_DISPLAY_NAME_KEY = "displayName"
        private const val USER_NAME_KEY = "displayName"
        private const val USER_GIVEN_NAME_KEY = "givenName"
        private const val USER_FAMILY_NAME_KEY = "familyName"
        private const val USER_EMAIL_KEY = "email"
        private const val USER_PROFILE_PICTURE_KEY = "profilePicture"

        private const val USER_FRIENDS_PATH = "usersFriends"
    }

    private var auth = Firebase.auth
    private var database = Firebase.database.reference

    private var authenticatedUser: LiveData<User?>? = null

    /**
     * Gets the currently authenticated user.
     * @return The authenticated user.
     */
    fun getAuthenticatedUser(lifecycleOwner: LifecycleOwner): LiveData<User?> {
        return auth.currentUser?.let {
            if (authenticatedUser == null) {
                authenticatedUser = fetchUserById(it.uid, lifecycleOwner)
            }
            authenticatedUser
        } ?: MutableLiveData()
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
            .requestProfile()
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
                    Tasks.await(auth.signInWithCredential(credential)).user?.also { firebaseUser ->
                        val user = initUser(firebaseUser.uid, account)
                        database.child(USER_PATH).child(user.id).setValue(
                            mapOf(
                                USER_DISPLAY_NAME_KEY to user.displayName,
                                USER_NAME_KEY to user.displayName,
                                USER_GIVEN_NAME_KEY to user.givenName,
                                USER_FAMILY_NAME_KEY to user.familyName,
                                USER_EMAIL_KEY to user.email,
                                USER_PROFILE_PICTURE_KEY to user.profilePicture,
                            )
                        ).addOnSuccessListener {
                            Log.d(
                                TAG,
                                "Added user"
                            )
                        }.addOnFailureListener {
                            Log.e(
                                TAG,
                                it.message.toString()
                            )
                        }
                        return user
                    }
                } catch (e: ApiException) {
                }
            }
        }

        return null
    }

    fun fetchUserById(id: String, owner: LifecycleOwner?): LiveData<User?> {
        val user = MutableLiveData<User?>(User(id, id))
        val ref = database.child(USER_PATH).child(id)
        val listener = ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    user.postValue(
                        User(
                            id = id,
                            displayName = snapshot.child(USER_DISPLAY_NAME_KEY).value as String?
                                ?: id,
                            givenName = snapshot.child(USER_GIVEN_NAME_KEY).value as String?,
                            familyName = snapshot.child(USER_FAMILY_NAME_KEY).value as String?,
                            email = snapshot.child(USER_EMAIL_KEY).value as String?,
                            profilePicture = snapshot.child(USER_PROFILE_PICTURE_KEY).value as String?,
                        )
                    )
                }
                if (owner == null) {
                    ref.removeEventListener(this)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, error.message)
                if (owner == null) {
                    ref.removeEventListener(this)
                }
            }
        })
        owner?.lifecycle?.addObserver(LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_DESTROY -> ref.removeEventListener(listener)
                else -> {}
            }
        })
        return user
    }

    fun addFriend(id: String) {
        authenticatedUser?.value?.also {
            database.child(USER_FRIENDS_PATH).child(it.id).child(id).setValue(true)
        }
    }

    private fun initUser(id: String, account: GoogleSignInAccount): User {
        return User(
            id = id,
            displayName = account.displayName ?: id,
            givenName = account.givenName,
            familyName = account.familyName,
            email = account.email,
            profilePicture = account.photoUrl?.toString()
        )
    }

    fun signOut() {
        auth.signOut()
    }
  
  fun writeNewUser(userId: String, name: String, email: String) {
        val user = User(name, email)

        database.child("users").child(userId).setValue(user)
    }

    fun readAllMessages(userId: String, name: String, email: String) {
        database.child("messages").child(userId).get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
        }.addOnFailureListener {
            Log.i("firebase", "Error getting data", it)
        }
    }
}