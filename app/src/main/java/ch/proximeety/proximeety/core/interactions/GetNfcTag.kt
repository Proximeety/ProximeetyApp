package ch.proximeety.proximeety.core.interactions

import androidx.lifecycle.LiveData
import ch.proximeety.proximeety.core.entities.Tag
import ch.proximeety.proximeety.core.repositories.UserRepository

/**
 * Get the ID of the NFC tag.
 */
class GetNfcTag(
    private val repository: UserRepository,
) {
    operator fun invoke(): LiveData<Tag?> {
        return repository.getNfcTag()
    }
}