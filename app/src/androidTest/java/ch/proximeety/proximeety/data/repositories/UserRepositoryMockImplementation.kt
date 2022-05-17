package ch.proximeety.proximeety.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ch.proximeety.proximeety.core.entities.Comment
import ch.proximeety.proximeety.core.entities.Post
import ch.proximeety.proximeety.core.entities.Story
import ch.proximeety.proximeety.core.entities.Tag
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.core.repositories.UserRepository
import ch.proximeety.proximeety.util.SyncActivity

/**
 * Mock Implementation of the user repository.
 */
class UserRepositoryMockImplementation : UserRepository {

    private var user: User? = null
    private var friends = mutableListOf<User>(
        User(
            id = "friendWithStoryId",
            displayName = "friendWithStory",
            hasStories = true,
        )
    )
    private var nonNullUser: User =
        User (
            id = "-MzJUaBgPuPH8EHhw0yQ",
            displayName = "Proximeety",
            profilePicture = null
        )
    private var posts =
        mutableListOf<Post>(
            Post(
                id = "-MzLEU_55gpq5ZQgAOAp",
                userDisplayName = "Yanis De Busschere",
                userProfilePicture = null,
                timestamp = 1648566863399,
                postURL = null,
                likes = 0,
                posterId = "testUserId"
            ),
            Post(
                id = "-MzLCslyh0zEGu6dioMT",
                userDisplayName = "Yanis De Busschere",
                userProfilePicture = null,
                timestamp = 1648566440714,
                postURL = null,
                likes = 0,
                posterId = "testUserId"
            ),
            Post(
                id = "-MzLClBigTOTIoY6fLHT",
                userDisplayName = "Yanis De Busschere",
                userProfilePicture = null,
                timestamp = 1648566409875,
                postURL = null,
                likes = 0,
                posterId = "testUserId"
            ),
            Post(
                id = "-MzKNArXlI8iMmVkVeXP",
                userDisplayName = "Yanis De Busschere",
                userProfilePicture = null,
                timestamp = 1648552363683,
                postURL = null,
                likes = 0,
                posterId = "testUserId"
            ),
            Post(
                id = "-MzJUlMmW-UnG1MnuC7o",
                userDisplayName = "Yanis De Busschere",
                userProfilePicture = null,
                timestamp = 1648537574380,
                postURL = null,
                likes = 0,
                posterId = "testUserId"
            ),
            Post(
                id = "-MzJUaBgPuPH8EHhw0yQ",
                userDisplayName = "Yanis De Busschere",
                userProfilePicture = null,
                timestamp = 1648537528605,
                postURL = null,
                likes = 0,
                posterId = "testUserId"
            )
        )

    private var comments = mutableListOf(
        Comment(
            id = "-MzJUaBgPuPH8EHhw0yQ",
            postId = "-MzLEU_55gpq5ZQgAOAp",
            posterId = "testUserId2",
            userDisplayName = "Hamza LAAROUS",
            userProfilePicture = null,
            timestamp = 1648537528605,
            comment = "test1",
            likes = 0,
            isLiked = true
        ),
        Comment(
            id = "-MzJUaBgPuPH8EHhw0yQ",
            postId = "-MzLEU_55gpq5ZQgAOAp",
            posterId = "testUserId2",
            userDisplayName = "Hamza LAAROUS",
            userProfilePicture = null,
            timestamp = 1648537528605,
            comment = "test2",
            likes = 0,
            isLiked = true
        ),
        Comment(
            id = "-MzJUaBgPuPH8EHhw0yQ",
            postId = "-MzLEU_55gpq5ZQgAOAp",
            posterId = "testUserId2",
            userDisplayName = "Hamza LAAROUS",
            userProfilePicture = null,
            timestamp = 1648537528605,
            comment = "test3",
            likes = 0,
            isLiked = true
        )
    )

    private var stories =
        mutableListOf<Story>(
            Story(
                id = "-MzKNArXlI8iMmVkVeXP",
                userDisplayName = "Yanis De Busschere",
                userProfilePicture = null,
                timestamp = 1648552363683,
                posterId = "testUserId",
            ),
            Story(
                id = "-MzJUlMmW-UnG1MnuC7o",
                userDisplayName = "Yanis De Busschere",
                userProfilePicture = null,
                timestamp = 1648537574380,
                posterId = "testUserId"
            ),
            Story(
                id = "-MzJUaBgPuPH8EHhw0yQ",
                userDisplayName = "Yanis De Busschere",
                userProfilePicture = null,
                timestamp = 1648537528605,
                posterId = "testUserId"
            )
        )

    override fun setActivity(activity: SyncActivity) {}

    override fun getAuthenticatedUser(): LiveData<User?> {
        return MutableLiveData(user)
    }

    override suspend fun authenticateWithGoogle(): User? {
        user = User(id = "testUserId", displayName = "Test User")
        return user
    }

    override suspend fun setAuthenticatedUserVisible() {}

    override fun getNearbyUsers(): LiveData<List<User>> {
        return MutableLiveData(
            listOf(
                User("testUserId1", "User1"),
                User("testUserId2", "User2"),
                User("testUserId3", "User3")
            )
        )
    }

    override fun signOut() {
        user = null
    }

    override fun fetchUserById(id: String): LiveData<User?> {
        return when (id) {
            "testUserId" -> MutableLiveData(User("testUserId1", "Test User"))
            "testUserId1" -> MutableLiveData(User("testUserId1", "User1"))
            "testUserId2" -> MutableLiveData(User("testUserId2", "User2"))
            "testUserId3" -> MutableLiveData(User("testUserId3", "User3"))
            "friendWithStoryId" -> MutableLiveData(User("friendWithStoryId", "friendWithStory", hasStories = true))
            else -> MutableLiveData()
        }
    }

    override suspend fun addFriend(id: String) {
        fetchUserById(id).value?.let { friends.add(it) }
    }

    override suspend fun getFriends(): List<User> {
        user?.also {
            return friends.plus(it)
        }
        return listOf()
    }

    override suspend fun getPostsByUserId(id: String): List<Post> {
        when (id) {
            user?.id -> {
                return posts.toList()
            }
        }
        return listOf()
    }

    override suspend fun post(url: String) {
        user?.id?.let {
            Post(
                id = url,
                userDisplayName = "",
                userProfilePicture = null,
                timestamp = 0,
                likes = 0,
                posterId = it
            )
        }?.let {
            posts.add(
                it
            )
        }
    }

    override suspend fun deletePost(postId: String) {
        posts.forEach { post ->
            if (post.id == postId) {
                posts.remove(post)
            }
        }
    }

    override suspend fun deleteStory(storyId: String) {
        stories.forEach { story ->
            if (story.id == storyId) {
                stories.remove(story)
            }
        }
    }

    override suspend fun postStory(url: String) {
        user?.id?.let {
            Story(
                id = url,
                userDisplayName = "",
                userProfilePicture = null,
                timestamp = 0,
                posterId = it
            )
        }?.let {
            stories.add(
                it
            )
        }
    }

    override suspend fun downloadPost(post: Post): Post {
        return post
    }

    override suspend fun togglePostLike(post: Post) {
    }

    override suspend fun isPostLiked(post: Post): Boolean {
        return true
    }

    override suspend fun postComment(postId: String, comment: String) {
        user?.id?.let {
            Comment(
                id = comment.hashCode().toString(),
                postId = postId,
                userDisplayName = "",
                userProfilePicture = null,
                timestamp = 0,
                likes = 0,
                posterId = it,
                comment = comment
            )
        }?.let {
            comments.add(
                it
            )
        }
    }

    override suspend fun getStoriesByUserId(id: String): List<Story> {
        return when (id) {
            user?.id -> {
                stories
            }
            else ->
                listOf()
        }
    }

    override suspend fun downloadStory(story: Story): Story {
        return story.copy(storyURL = story.id)
    }

    override fun getFriendsLocations(): LiveData<Map<String, Triple<Long, Double, Double>>> {
        return MutableLiveData(
            mapOf(
                "testUserId1" to Triple(
                    1648566863399,
                    -122.4194,
                    37.7749
                ),
                "testUserId2" to Triple(
                    1648566863399,
                    -122.4194,
                    37.7749
                ),
                "testUserId3" to Triple(
                    1648566863399,
                    -122.4194,
                    37.7749
                )
            )
        )
    }

    override fun startLiveLocation() {
    }

    override suspend fun getComments(id: String): List<Comment> {
        when (id) {
            user?.id -> {
                return comments.toList()
            }
        }

        return listOf()
    }
    
    override fun enableNfc() {
    }

    private val tag = MutableLiveData<Tag?>(Tag(
        "00:00:00:00:00:00",
        "testTag",
        47.0,
        47.0,
        listOf(Pair(1651653481L, User("testUserVisitorId", "testUserVisitor"))),
        User("testUserId", "testUser")
    ))

    private val tags = mutableListOf<Tag>(
        Tag(
            "00:00:00:00:00:00",
            "testTag",
            47.0,
            47.0,
            listOf(Pair(1651653481L, User("testUserVisitorId", "testUserVisitor"))),
            User("testUserId", "testUser")
        )
    )

    override fun getLiveNfcTagId(): LiveData<String?> {
        return MutableLiveData(tag.value?.id)
    }

    override suspend fun getAllNfcs(): List<Tag> {
        return tags
    }

    override suspend fun createNewNfcTag(): Tag? {
        return null
        //return Tag("-MzJUaBgPuPH8EHhw0yQ","test", 0.0, 0.0, emptyList(), nonNullUser)
    }

    override suspend fun getNfcTagById(id: String): Tag? {
        when (id) {
            tag.value?.id -> {
                return tag.value
            }
        }
        return null
    }

    override suspend fun writeNfcTag(tag: Tag) {
    }

//    fun setTag() {
//        tag.postValue(Tag(
//            "00:00:00:00:00:00",
//            "testTag",
//            47.0,
//            47.0,
//            listOf(Pair(1651653481L, User("testUserVisitorId", "testUserVisitor"))),
//            User("testUserId", "testUser")
//        ))
//    }
}