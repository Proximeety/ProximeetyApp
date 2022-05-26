package ch.proximeety.proximeety.data.sources

import android.util.Base64
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import ch.proximeety.proximeety.core.entities.Tag
import ch.proximeety.proximeety.core.entities.User
import ch.proximeety.proximeety.di.TestAppModule
import ch.proximeety.proximeety.presentation.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File
import javax.inject.Inject

private const val TEST_ONE_PIXEL_IMAGE =
    "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mP8z/C/HgAGgwJ/lK3Q6wAAAABJRU5ErkJggg=="

@HiltAndroidTest
@UninstallModules(TestAppModule::class)
class FirebaseAccessObjectTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var firebaseAccessObject: FirebaseAccessObject

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    private fun signIn(email: String, password: String): User? = runBlocking(Dispatchers.Main) {
        firebaseAccessObject.authenticateWithEmailAndPassword(email, password)
        val user = firebaseAccessObject.getAuthenticatedUser(null).value
        assertNotNull("Authentication failed.", user)

        return@runBlocking user
    }

    @Test
    fun signOutTest() {
        signIn("test1@test.com", "test123")

        firebaseAccessObject.signOut()
        runBlocking {
            assertNull(firebaseAccessObject.getAuthenticatedUser(null).value)
        }
    }

    @Test
    fun friendTest() {
        val user1 = signIn("test1@test.com", "test123")
        firebaseAccessObject.signOut()
        val user2 = signIn("test2@test.com", "test123")

        assertNotNull(user1)
        assertNotNull(user2)
        assertNotEquals(user1?.id, user2?.id)

        runBlocking {
            firebaseAccessObject.addFriend(user1!!.id)
            assertTrue(firebaseAccessObject.getFriends().any { it.id == user1.id })
            val friend = firebaseAccessObject.getFriends().first { it.id == user1.id }
            assertEquals(friend.id, user1.id)
            assertEquals(friend.displayName, user1.displayName)
            assertEquals(friend.givenName, user1.givenName)
            assertEquals(friend.familyName, user1.familyName)
            assertEquals(friend.email, user1.email)
            assertEquals(friend.profilePicture, user1.profilePicture)

            firebaseAccessObject.removeFriend(user1.id)
            assertFalse(firebaseAccessObject.getFriends().any { it.id == user1.id })
        }
    }

    @Test
    fun postTest() {
        val user1 = signIn("test1@test.com", "test123")
        assertNotNull(user1)

        val tempFile = File.createTempFile("test", ".jpeg")
        tempFile.writeBytes(
            Base64.decode(TEST_ONE_PIXEL_IMAGE, Base64.DEFAULT)
        )

        runBlocking {
            firebaseAccessObject.post(tempFile.toURI().toString())
            val posts = firebaseAccessObject.getPostsByUserID(user1!!.id)
            assertTrue(posts.isNotEmpty())
            assertTrue(firebaseAccessObject.downloadPost(posts.first()).postURL != null)
            assertFalse(firebaseAccessObject.isPostLiked(posts.first()))
            firebaseAccessObject.togglePostLike(posts.first())
            assertTrue(firebaseAccessObject.isPostLiked(posts.first()))
            posts.forEach {
                firebaseAccessObject.deletePost(it.id)
            }
            assertTrue(firebaseAccessObject.getPostsByUserID(user1.id).isEmpty())
        }
    }

    @Test
    fun storyTest() {
        val user1 = signIn("test1@test.com", "test123")
        assertNotNull(user1)

        val tempFile = File.createTempFile("test", ".jpeg")
        tempFile.writeBytes(
            Base64.decode(TEST_ONE_PIXEL_IMAGE, Base64.DEFAULT)
        )

        runBlocking {

            firebaseAccessObject.postStory(tempFile.toURI().toString())
            val stories = firebaseAccessObject.getStoriesByUserID(user1!!.id)
            assertTrue(stories.isNotEmpty())
            assertTrue(firebaseAccessObject.downloadStory(stories.first()).storyURL != null)
            stories.forEach {
                firebaseAccessObject.deleteStory(it.id)
            }
            assertTrue(firebaseAccessObject.getStoriesByUserID(user1.id).isEmpty())

        }
    }

    @Test
    fun locationTest() {
        val user1 = signIn("test1@test.com", "test123")
        runBlocking {
            firebaseAccessObject.uploadLocation(44.1, 43.1)
        }
        firebaseAccessObject.signOut()
        val user2 = signIn("test2@test.com", "test123")

        assertNotNull(user1)
        assertNotNull(user2)
        assertNotEquals(user1?.id, user2?.id)

        runBlocking {
            firebaseAccessObject.addFriend(user2!!.id)
            val locations = firebaseAccessObject.getFriendsLocation(null)
            composeTestRule.waitUntil(1000) { locations.value != null && locations.value?.isNotEmpty() == true }
            assertNotNull(locations.value)
            assertTrue(locations.value!!.contains(user1!!.id))
            assertEquals(44.1, locations.value!![user1.id]?.second)
            assertEquals(43.1, locations.value!![user1.id]?.third)
        }
    }

    @Test
    fun tagTest() {
        val user1 = signIn("test1@test.com", "test123")
        assertNotNull(user1)

        val tag = Tag("testTagId", "testTagName", 43.1, 44.1, listOf(Pair(0, user1!!)), user1)

        runBlocking {
            firebaseAccessObject.writeTag(tag)
            assertEquals(tag.id, firebaseAccessObject.getTagById(tag.id)?.id)
            assertEquals(tag.name, firebaseAccessObject.getTagById(tag.id)?.name)
            assertEquals(tag.latitude, firebaseAccessObject.getTagById(tag.id)?.latitude)
            assertEquals(tag.longitude, firebaseAccessObject.getTagById(tag.id)?.longitude)
            assertEquals(tag.owner.id, firebaseAccessObject.getTagById(tag.id)?.owner?.id)

            firebaseAccessObject.signOut()
            val user2 = signIn("test2@test.com", "test123")
            assertNotNull(user2)

            firebaseAccessObject.visitTag(tag.id)
            assertTrue(firebaseAccessObject.getTagById(tag.id)?.visitors?.any { it.second.id == user2!!.id } == true)

            val tags = firebaseAccessObject.getAllNfcs()
            assertTrue(tags.any { it.id == tag.id })
        }
    }

    @Test
    fun commentTest() {
        val user1 = signIn("test1@test.com", "test123")
        assertNotNull(user1)

        val tempFile = File.createTempFile("test", ".jpeg")
        tempFile.writeBytes(
            Base64.decode(TEST_ONE_PIXEL_IMAGE, Base64.DEFAULT)
        )

        runBlocking {
            firebaseAccessObject.post(tempFile.toURI().toString())
            val posts = firebaseAccessObject.getPostsByUserID(user1!!.id)
            assertTrue(posts.isNotEmpty())

            val post = posts.first()
            assertTrue(firebaseAccessObject.getComments(post.id).isEmpty())

            firebaseAccessObject.postComment(post.id, "testComment")

            val comments = firebaseAccessObject.getComments(post.id)
            assertTrue(comments.isNotEmpty())
            assertEquals(comments.first().comment, "testComment")

            firebaseAccessObject.toggleCommentLike(comments.first())
            assertTrue(firebaseAccessObject.isCommentLiked(comments.first()))
        }
    }

    @Test
    fun commentReplyTest() {
        val user1 = signIn("test1@test.com", "test123")
        assertNotNull(user1)

        val tempFile = File.createTempFile("test", ".jpeg")
        tempFile.writeBytes(
            Base64.decode(TEST_ONE_PIXEL_IMAGE, Base64.DEFAULT)
        )

        runBlocking {
            firebaseAccessObject.post(tempFile.toURI().toString())
            val posts = firebaseAccessObject.getPostsByUserID(user1!!.id)
            assertTrue(posts.isNotEmpty())

            val post = posts.first()
            assertTrue(firebaseAccessObject.getComments(post.id).isEmpty())

            firebaseAccessObject.postComment(post.id, "testComment")

            val comments = firebaseAccessObject.getComments(post.id)
            assertTrue(comments.isNotEmpty())

            firebaseAccessObject.replyToComment(post.id, comments.first().id, "testReply")

            val replies = firebaseAccessObject.getCommentReplies(comments.first().id)
            assertTrue(replies.isNotEmpty())
            assertEquals(replies.first().commentReply, "testReply")

        }
    }
}