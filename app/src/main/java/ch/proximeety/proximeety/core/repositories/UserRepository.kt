package ch.proximeety.proximeety.core.repositories

import androidx.lifecycle.LiveData
import ch.proximeety.proximeety.core.entities.*
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
     * Remove user as friend.
     *
     * @param id the id of the user to add as friend.
     */
    suspend fun removeFriend(id: String)

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
     * Delete a post
     *
     * @param postId the local id of the image.
     */
    suspend fun deletePost(postId: String)

    /**
     * Delete a story
     *
     * @param storyId the local id of the image.
     */
    suspend fun deleteStory(storyId: String)

    /**
     * Post a story.
     *
     * @param url the local URL of the image.
     */
    suspend fun postStory(url: String)

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
     * @param post the post
     */
    suspend fun togglePostLike(post: Post)

    /**
     * is a Post liked
     *
     * @param post the post
     */
    suspend fun isPostLiked(post: Post): Boolean

    /**
     * Leave a comment under a post
     *
     * @param postId id of the post under which to comment
     * @param comment the comment to post
     */
    suspend fun postComment(postId: String, comment: String)

    /**
     * Reply to a Comment
     *
     * @param postId id of the post where the comment has been posted
     * @param commentId id of the comment to reply to
     * @param comment the reply
     */
    suspend fun replyToComment(postId: String, commentId: String, comment: String)

    /**
     * Toggle likes on a Comment
     *
     * @param comment the comment
     */
    suspend fun toggleCommentLike(comment: Comment)

    /**
     * is a Comment liked
     *
     * @param comment the comment
     */
    suspend fun isCommentLiked(comment: Comment): Boolean

    /**
     * does a comment have replies
     *
     * @param comment the comment in question
     */
    suspend fun hasReplies(comment: Comment): Boolean

    /**
     * Get the stories of an user.
     */
    suspend fun getStoriesByUserId(id: String): List<Story>

    /**
     * Download the image of the story and returns the story.
     *
     * @param story the story to download.
     *
     * @return The story post with its [Story.storyURL] not null.
     */
    suspend fun downloadStory(story: Story): Story

    /**
     * Get the location of the friends.
     */
    fun getFriendsLocations(): LiveData<Map<String, Triple<Long, Double, Double>>>

    /**
     * Start sharing the live location of the authenticated user.
     */
    fun startLiveLocation()

    /**

     * Get comments for a post.
     */
    suspend fun getComments(id: String): List<Comment>

    /**
     * Get a comment's replies
     */
    suspend fun getCommentReplies(commentId: String): List<CommentReply>

    /**
     * Enable NFC.
     */
    fun enableNfc()

    /**
     * Get NFC tag.
     */
    fun getLiveNfcTagId(): LiveData<String?>

    /**
     * Get all existing NFC tags.
     */
    suspend fun getAllNfcs(): List<Tag>

    suspend fun createNewNfcTag(): Tag?

    suspend fun getNfcTagById(id: String): Tag?

    suspend fun writeNfcTag(tag: Tag)

    /**
     * Get a certain post from a user.
     *
     * @param userId the id of the poster
     * @param postId the id of the post
     */
    suspend fun getPostByIds(userId: String, postId: String): Post?

    fun getNfcTag(): LiveData<Tag?>
}
