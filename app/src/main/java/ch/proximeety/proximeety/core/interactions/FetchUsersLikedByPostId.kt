package ch.proximeety.proximeety.core.interactions

import androidx.lifecycle.LiveData
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.core.repositories.UserRepository

class FetchUsersLikedByPostId (
    private val repository: UserRepository
) {
    operator fun invoke(userId: String, id: String): LiveData<List<User>> {
        return repository.fetchUsersLikedByPostId(userId, id)
    }
}

//A possible version of GetMapForAllPosts
//var map = mutableMapOf<String, List<User>>()
//for (friend in repository.getFriends()) {
//    val keys = repository.getPostsByUserId(friend.id)
//        .sortedBy { post -> post.timestamp }.reversed()
//        .map {post -> post.id}
//    for (key in keys) {
//        val value = map.put(key, repository.fetchUsersLikedByPostId(friend.id, key).value!!)
//    }
//}
//return map