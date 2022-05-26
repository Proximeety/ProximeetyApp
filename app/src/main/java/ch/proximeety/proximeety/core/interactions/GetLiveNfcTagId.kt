package ch.proximeety.proximeety.core.interactions

import androidx.lifecycle.LiveData
import ch.proximeety.proximeety.core.repositories.UserRepository

/**
 * Get the ID of the NFC tag.
 */
class GetLiveNfcTagId(
    private val repository: UserRepository,
) {
    operator fun invoke(): LiveData<String?> {
        return repository.getLiveNfcTagId()
    }
}