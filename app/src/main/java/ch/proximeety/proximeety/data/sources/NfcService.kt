package ch.proximeety.proximeety.data.sources

import android.content.Context
import android.nfc.NfcAdapter
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ch.proximeety.proximeety.util.SyncActivity

/**
 * The NFC service.
 */
class NfcService(private val context: Context) {

    private val nfcAdapter = NfcAdapter.getDefaultAdapter(context)
    private var activity: SyncActivity? = null
    private var tag = MutableLiveData<String?>()

    /**
     * Registers an activity to be notified when a NFC tag is scanned.
     */
    fun enable(activity: SyncActivity) {
        if (isReady()) {
            this.activity = activity
            nfcAdapter.enableReaderMode(activity, NfcAdapter.ReaderCallback { tag ->
                this.tag.postValue(tag.id.joinToString(":") { "%02X".format(it) })
                Log.d(
                    "NfcService",
                    "Tag scanned: ${tag.id.joinToString(":") { "%02X".format(it) }}"
                )
            }, NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK, null)
        }
    }

    /**
     * Get the LiveData object that contains the NFC tag ID.
     */
    fun getTag(): LiveData<String?> {
        return tag
    }

    /**
     * Unregisters the activity.
     */
    fun disable() {
        if (isReady()) {
            nfcAdapter.disableReaderMode(activity)
        }
    }

    /**
     * Checks if NFC is available and enable.
     */
    private fun isReady(): Boolean {
        if (nfcAdapter == null) {
            return false
        }
        return nfcAdapter.isEnabled
    }

}