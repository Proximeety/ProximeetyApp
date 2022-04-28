package ch.proximeety.proximeety.util

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.CompletableDeferred


/**
 * An activity result.
 */
class ActivityResult(
    val resultCode: Int,
    val data: Intent?
)

/**
 * A permission result.
 */
class PermissionResult(
    val grantResults: IntArray
)

/**
 * An synchronous activity.
 *
 * Used to send an receive intent synchronously in a coroutine.
 */
abstract class SyncActivity : ComponentActivity(),
    ActivityCompat.OnRequestPermissionsResultCallback {

    private var resultByCode = mutableMapOf<Int, CompletableDeferred<ActivityResult?>>()
    private var permissionsRequestCode = 0
    private var permissionsResultByCode =
        mutableMapOf<Int, CompletableDeferred<PermissionResult>>()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        resultByCode[requestCode]?.complete(ActivityResult(resultCode, data)) ?: run {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    /**
     * Launch an intent a wait for the result to come back before continuing.
     *
     * @param launchIntent the function that launches the intent.
     * @param requestCode the request code for the intent.
     * @return The result of the launched activity.
     */
    suspend fun waitForIntentResult(launchIntent: () -> Unit, requestCode: Int): ActivityResult? {
        val activityResult = CompletableDeferred<ActivityResult?>()

        resultByCode[requestCode] = activityResult
        launchIntent()

        val result = activityResult.await()
        resultByCode.remove(requestCode)
        return result
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        permissionsResultByCode[requestCode]?.complete(PermissionResult(grantResults)) ?: run {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    /**
     * Launch a permission request and wait for the result to come back before continuing.
     *
     * @param permissions the list of permissions to request.
     * @return The result of the permissions request.
     */
    suspend fun waitForPermissionResult(
        permissions: Array<String>
    ): PermissionResult {
        val permissionResult = CompletableDeferred<PermissionResult>()

        val requestCode = permissionsRequestCode
        permissionsRequestCode += 1
        permissionsResultByCode[requestCode] = permissionResult
        requestPermissions(permissions, requestCode)

        val result = permissionResult.await()
        permissionsResultByCode.remove(requestCode)
        return result
    }

}