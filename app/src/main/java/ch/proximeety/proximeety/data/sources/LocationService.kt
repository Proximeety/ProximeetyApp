package ch.proximeety.proximeety.data.sources

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ch.proximeety.proximeety.util.SyncActivity
import ch.proximeety.proximeety.util.extensions.executor
import kotlinx.coroutines.delay
import java.util.function.Consumer


/**
 * Bluetooth service to interact with other users.
 */
class LocationService(
    private val context: Context
) {

    companion object {
        private val REQUIRED_PERMISSIONS =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                listOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                )
            } else {
                listOf(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
    }

    private val locationManger =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    /**
     * Returns the current location of the user.
     */
    @SuppressLint("MissingPermission")
    suspend fun getLiveLocation(syncActivity: SyncActivity): LiveData<Pair<Double, Double>?>? {
        if (!isReady(syncActivity)) return null

        val location = MutableLiveData<Pair<Double, Double>?>()
        val gpsLocationListener = LocationListener {
            location.value = Pair(it.latitude, it.longitude)
        }
        locationManger.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            100,
            0F,
            gpsLocationListener
        )

        return location
    }

    /**
     * Returns the current location of the user.
     */
    @SuppressLint("MissingPermission")
     fun getLastLocation(syncActivity: SyncActivity): Pair<Double, Double>? {
        return locationManger.getLastKnownLocation(LocationManager.FUSED_PROVIDER)?.let {
            Pair(it.latitude, it.longitude)
        }
    }


    /**
     * Checks whether the service is ready to run and request for missing permissions if needed.
     *
     * * Checks that all permissions are granted.
     * * Checks that the location manager is enabled.
     * * Checks that the location provider is enabled.
     *
     * @return true if the service is ready to run.
     */
    private suspend fun isReady(activity: SyncActivity): Boolean {

        val missingPermissions = REQUIRED_PERMISSIONS.filter {
            ActivityCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_DENIED
        }

        if (missingPermissions.isNotEmpty()) {
            activity.waitForPermissionResult(missingPermissions.toTypedArray()).also {
                delay(100)
                return isReady(activity)
            }
        }

        if (!LocationManagerCompat.isLocationEnabled(locationManger)) {
            activity.waitForIntentResult({
                activity.startActivityForResult(
                    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),
                    3
                )
            }, 3)
        }

        if (!locationManger.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return false
        }

        return true
    }
}