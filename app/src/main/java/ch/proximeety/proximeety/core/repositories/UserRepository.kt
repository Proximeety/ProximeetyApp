package ch.proximeety.proximeety.core.repositories

import androidx.lifecycle.LiveData
import ch.proximeety.proximeety.core.entities.Post
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.util.SyncActivity

/**
 * User repository interface.
 */
interface UserRepository {

    /**
     * Set the activity for the user repository. The activity will be used for requesting permissions and launching intents.
     */
    fun setActivity(activity: SyncActivity)

    /**
     * Get the currently authenticated user.
     */
    fun getAuthenticatedUser(): LiveData<User?>

    /**
     * Authenticate a new user with Google.
     * @see [ch.proximeety.proximeety.util.extensions.getActivity]
     */
    suspend fun authenticateWithGoogle(): User?

    /**
     * Set the current user visible for other users. He will appear in their nearby users list.
     */
    suspend fun setAuthenticatedUserVisible()

    /**
     * Get the list of nearby users
     */
    fun getNearbyUsers(): LiveData<List<User>>

    /**
     * Logout the user.
     */
    fun signOut()

    /**
     * Fetch an user
     *
     * @param id the id of the user to fetch.
     * @return the user as a LiveData.
     */
    fun fetchUserById(id: String): LiveData<User?>

    /**
     * Add user as friend.
     *
     * @param id the id of the user to add as friend.
     */
    suspend fun addFriend(id: String)

    /**
     * Get the friends of the authenticated user.
     */
    suspend fun getFriends(): List<User>

    /**
     * Get the posts of an user.
     *
     * @param id the user id.
     *
     * @return The list of posts.
     */
    suspend fun getPostsByUserId(id: String): List<Post>

    /**
     * Post a picture
     *
     * @param url the local URL of the image.
     */
    suspend fun post(url: String)

    /**
     * Download the image of the post and returns the post.
     *
     * @param post the post download.
     *
     * @return The post with its postURL none null.
     */
    suspend fun downloadPost(post: Post): Post

    /**
     * Toggle likes on a User's  Post
     *
     * @param postId the post's id
     */
    suspend fun togglePostLike(postId: String)

    /**
     * is a Post liked
     *
     * @param postId the post's id
     */
    suspend fun isPostLiked(postId: String) : Boolean
}