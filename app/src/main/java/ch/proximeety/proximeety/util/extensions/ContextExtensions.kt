package ch.proximeety.proximeety.util.extensions

import android.content.Context
import android.content.ContextWrapper
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import ch.proximeety.proximeety.util.SyncActivity
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Get the activity from the context.
 *
 * @return the activity or null if no activity could be found.
 */
tailrec fun Context.getActivity(): SyncActivity? = when (this) {
    is SyncActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}

/**
 * Returns an [Executor] using [ContextCompat].
 */
val Context.executor: Executor
    get() = ContextCompat.getMainExecutor(this)

/**
 * Returns the [ProcessCameraProvider].
 */
suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { future ->
        future.addListener(
            {
                continuation.resume(future.get())
            },
            executor
        )
    }
}
