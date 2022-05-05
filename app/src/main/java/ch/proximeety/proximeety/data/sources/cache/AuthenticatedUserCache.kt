package ch.proximeety.proximeety.data.sources.cache

import android.content.Context
import androidx.documentfile.provider.DocumentFile
import ch.proximeety.proximeety.core.entities.User
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.InputStreamReader
import java.io.OutputStreamWriter

@OptIn(ExperimentalSerializationApi::class)
class AuthenticatedUserCache(private val context: Context) {

    companion object {
        /**
         * The name of the file where the user is stored.
         */
        private const val CACHE_FILE_NAME = "authenticated_user_cache"
    }

    private var cacheDir = context.cacheDir

    var user: User?
        get() {
            (DocumentFile.fromFile(cacheDir).findFile("$CACHE_FILE_NAME.json")
                ?: DocumentFile.fromFile(cacheDir).findFile(CACHE_FILE_NAME))?.also {
                    val reader = InputStreamReader(context.contentResolver.openInputStream(it.uri))
                    return Json.decodeFromString(reader.readText())
                }
            return null
        }
        set(value) {
            DocumentFile.fromFile(cacheDir).createFile("application/json", CACHE_FILE_NAME)?.also {
                val writer = OutputStreamWriter(context.contentResolver.openOutputStream(it.uri))
                writer.write(Json.encodeToString(value))
                writer.close()
            }
        }
}