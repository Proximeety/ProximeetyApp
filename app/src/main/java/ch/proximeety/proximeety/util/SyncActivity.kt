package ch.proximeety.proximeety.util

import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import kotlinx.coroutines.CompletableDeferred


/**
 * An activity result.
 */
class ActivityResult(
    val resultCode: Int,
    val data: Intent?
)

/**
 * An synchronous activity.
 *
 * Used to send an receive intent synchronously in a coroutine.
 */
abstract class SyncActivity : ComponentActivity() {

    private var resultByCode = mutableMapOf<Int, CompletableDeferred<ActivityResult?>>()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("onActivityResult", "$resultByCode")
        resultByCode[requestCode]?.let {
            Log.d("onActivityResult", "in $requestCode")
            it.complete(ActivityResult(resultCode, data))
            resultByCode.remove(requestCode)
        } ?: run {
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

}