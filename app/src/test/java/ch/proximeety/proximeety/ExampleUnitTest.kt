package ch.proximeety.proximeety

import ch.proximeety.proximeety.core.entities.User
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val user = User("test", "test")
        val json = Json.encodeToString(User.serializer(), user)
        val userDecoded = Json.decodeFromString(User.serializer(), json)
        assertEquals(user.id, userDecoded.id)
        assertEquals(user.displayName, userDecoded.displayName)
    }
}