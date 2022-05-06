package ch.proximeety.proximeety.core.interactions

import androidx.lifecycle.LiveData
import ch.proximeety.proximeety.core.entities.Tag
import ch.proximeety.proximeety.core.repositories.UserRepository

class GetAllNfcTags (
    private val repository: UserRepository,
) {
    suspend operator fun invoke(): List<Tag> {
        return repository.getAllNfcs()
    }
}