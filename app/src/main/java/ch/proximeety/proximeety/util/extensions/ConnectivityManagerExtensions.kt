package ch.proximeety.proximeety.util.extensions

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun ConnectivityManager.isConnected(): Boolean {
    return getNetworkCapabilities(activeNetwork)?.run {
        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    } ?: false
}