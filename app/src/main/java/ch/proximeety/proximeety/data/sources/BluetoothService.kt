package ch.proximeety.proximeety.data.sources

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.le.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.ParcelUuid
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.util.SyncActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


/**
 * Bluetooth service to interact with other users.
 */
class BluetoothService(
    private val context: Context
) {

    companion object {
        /**
         * The UUID used when advertising the user id.
         */
        private val SERVICE_UUID = UUID.fromString("B965ED0B-37F6-405B-9AF6-444212389C53")

        /**
         * The required permissions for the bluetooth service to operate
         */
        private val REQUIRED_PERMISSIONS =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    listOf(
                        Manifest.permission.BLUETOOTH_SCAN,
                        Manifest.permission.BLUETOOTH_ADVERTISE,
                        Manifest.permission.BLUETOOTH_CONNECT,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                } else {
                    listOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                    )
                }
            } else {
                listOf(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
    }

    private val bluetoothManager =
        context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val locationManger =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private val bluetoothAdapter = bluetoothManager.adapter

    /**
     * Advertise the user to others users.
     */
    @SuppressLint("MissingPermission")
    suspend fun advertiseUser(user: User, activity: SyncActivity) {
        if (!isReady(activity)) return

        val bluetoothAdvertiser = bluetoothAdapter.bluetoothLeAdvertiser

        if (!bluetoothAdapter.isLeExtendedAdvertisingSupported) {
            Log.e("BLUETOOTH_SERVICE", "LE Extended Advertising not supported!")
            return
        }

        val parameters = AdvertisingSetParameters.Builder()
            .setLegacyMode(false)
//            .setConnectable(false)
//            .setAnonymous(true)
            .setInterval(AdvertisingSetParameters.INTERVAL_HIGH)
            .setTxPowerLevel(AdvertisingSetParameters.TX_POWER_MEDIUM)

        if (bluetoothAdapter.isLe2MPhySupported) {
            parameters
                .setPrimaryPhy(BluetoothDevice.PHY_LE_1M)
                .setSecondaryPhy(BluetoothDevice.PHY_LE_2M)
        }

        val data = AdvertiseData.Builder()
            .setIncludeDeviceName(true)
            .addServiceData(
                ParcelUuid(SERVICE_UUID),
                user.id.toByteArray()
            ).build()

        val callback = object : AdvertisingSetCallback() {}

        bluetoothAdvertiser.startAdvertisingSet(
            parameters.build(),
            data,
            null,
            null,
            null,
            callback
        )
    }

    /**
     * Scan for nearby users using bluetooth low energy.
     */
    @SuppressLint("MissingPermission")
    suspend fun scanForUsers(activity: SyncActivity): LiveData<List<User>> {
        if (!isReady(activity)) return MutableLiveData(listOf())

        val bluetoothScanner = bluetoothAdapter.bluetoothLeScanner

        val serviceFilter = ScanFilter.Builder()
            .setServiceData(ParcelUuid(SERVICE_UUID), List<Byte>(28) {0}.toByteArray(),List<Byte>(28) {0}.toByteArray())
            .build()
        val filters = listOf(serviceFilter)

        val settings = ScanSettings.Builder()
            .setLegacy(false)
            .build()

        val users = MutableLiveData(listOf<User>())
        val callback = object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult?) {
                super.onScanResult(callbackType, result)
                result?.scanRecord?.serviceData?.get(ParcelUuid(SERVICE_UUID))?.also { bytes ->
                    val user =
                        User(displayName = bytes.decodeToString(), id = bytes.decodeToString())
                    if (users.value == null || users.value!!.none { user.id == it.id }) {
                        users.postValue(users.value?.plus(user) ?: listOf(user))
                    }
                }
            }
        }

        bluetoothScanner.startScan(filters, settings, callback)
        return users
    }

    /**
     * Checks whether the service is ready to run and request for missing permissions if needed.
     *
     * * Checks that the bluetooth adapter is enabled.
     * * Checks that the location manager is enabled.
     * * Checks that all permissions are granted.
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
            activity.waitForPermissionResult(missingPermissions.toTypedArray())?.also { result ->
                delay(100)
                return isReady(activity)
            }
        }

        if (!bluetoothAdapter.isEnabled) {
            activity.waitForIntentResult({
                activity.startActivityForResult(
                    Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE),
                    2
                )
            }, 2)
        }

        if (!LocationManagerCompat.isLocationEnabled(locationManger)) {
            activity.waitForIntentResult({
                activity.startActivityForResult(
                    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),
                    3
                )
            }, 3)
        }

        return true
    }
}