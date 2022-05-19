package ch.proximeety.proximeety.di

import android.content.Context
import androidx.room.Room
import ch.proximeety.proximeety.core.interactions.*
import ch.proximeety.proximeety.core.repositories.UserRepository
import ch.proximeety.proximeety.data.repositories.UserRepositoryImplementation
import ch.proximeety.proximeety.data.sources.BluetoothService
import ch.proximeety.proximeety.data.sources.FirebaseAccessObject
import ch.proximeety.proximeety.data.sources.LocationService
import ch.proximeety.proximeety.data.sources.NfcService
import ch.proximeety.proximeety.data.sources.cache.AuthenticatedUserCache
import ch.proximeety.proximeety.data.sources.cache.FriendCacheDB
import ch.proximeety.proximeety.data.sources.cache.PostCacheDB
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Main application module.
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideNavigationManager(): NavigationManager {
        return NavigationManager()
    }

    @Provides
    @Singleton
    fun provideFirebaseAccessObject(@ApplicationContext context: Context): FirebaseAccessObject {
        return FirebaseAccessObject(context)
    }

    @Provides
    @Singleton
    fun provideBluetoothService(@ApplicationContext context: Context): BluetoothService {
        return BluetoothService(context)
    }

    @Provides
    @Singleton
    fun provideLocationService(@ApplicationContext context: Context): LocationService {
        return LocationService(context)
    }

    @Provides
    @Singleton
    fun providePostCacheDatabase(@ApplicationContext context: Context): PostCacheDB {
        return Room.databaseBuilder(
            context,
            PostCacheDB::class.java,
            PostCacheDB.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideFriendCacheDatabase(@ApplicationContext context: Context): FriendCacheDB {
        return Room.databaseBuilder(
            context,
            FriendCacheDB::class.java,
            FriendCacheDB.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideAuthenticatedUserCache(@ApplicationContext context: Context): AuthenticatedUserCache {
        return AuthenticatedUserCache(context)
    }

    @Provides
    @Singleton
    fun provideNfcService(@ApplicationContext context: Context): NfcService {
        return NfcService(context)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        @ApplicationContext context: Context,
        firebaseAccessObject: FirebaseAccessObject,
        bluetoothService: BluetoothService,
        locationService: LocationService,
        postCacheDB: PostCacheDB,
        friendCacheDB: FriendCacheDB,
        authenticatedUserCache: AuthenticatedUserCache,
        nfcService: NfcService
    ): UserRepository {
        return UserRepositoryImplementation(
            context,
            firebaseAccessObject,
            bluetoothService,
            locationService,
            postCacheDB.dao,
            friendCacheDB.dao,
            authenticatedUserCache,
            nfcService
        )
    }

    @Provides
    @Singleton
    fun provideUserInteractions(repository: UserRepository): UserInteractions {
        return UserInteractions(
            addFriend = AddFriend(repository),
            authenticateWithGoogle = AuthenticateWithGoogle(repository),
            downloadPost = DownloadPost(repository),
            fetchUserById = FetchUserById(repository),
            getAuthenticatedUser = GetAuthenticatedUser(repository),
            getFeed = GetFeed(repository),
            getFriends = GetFriends(repository),
            getNearbyUsers = GetNearbyUsers(repository),
            post = Post(repository),
            deletePost = DeletePost(repository),
            deleteStory = DeleteStory(repository),
            togglePostLike = TogglePostLike(repository),
            isPostLiked = IsPostLiked(repository),
            postComment = PostComment(repository),
            setActivity = SetActivity(repository),
            setAuthenticatedUserVisible = SetAuthenticatedUserVisible(repository),
            signOut = SignOut(repository),
            getPostUserId = GetPostUserId(repository),
            postStory = PostStory(repository),
            getStoriesByUserId = GetStoriesByUserId(repository),
            downloadStory = DownloadStory(repository),
            startLiveLocation = StartLiveLocation(repository),
            getFriendsLocations = GetFriendsLocations(repository),
            getComments = GetComments(repository),
            isCommentLiked = IsCommentLiked(repository),
            toggleCommentLike = ToggleCommentLike(repository),
            enableNfc = EnableNfc(repository),
            getNfcTag = GetNfcTag(repository)
        )
    }
}
