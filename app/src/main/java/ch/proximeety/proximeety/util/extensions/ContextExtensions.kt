package ch.proximeety.proximeety.util.extensions

import android.content.Context
import android.content.ContextWrapper
import ch.proximeety.proximeety.util.SyncActivity

/**
 * Get the activity from the context.
 * @return the activity or null if no activity could be found.
 */
tailrec fun Context.getActivity(): SyncActivity? = when (this) {
    is SyncActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}