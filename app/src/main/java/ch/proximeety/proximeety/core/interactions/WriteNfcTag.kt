package ch.proximeety.proximeety.core.interactions

import androidx.lifecycle.LiveData
import ch.proximeety.proximeety.core.entities.Tag
import ch.proximeety.proximeety.core.repositories.UserRepository

class WriteNfcTag (
    private val repository: UserRepository,
) {
    suspend operator fun invoke(tag : Tag) {
        return repository.writeNfcTag(tag)
    }
}