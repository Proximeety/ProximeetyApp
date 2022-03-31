package ch.proximeety.proximeety.di

import android.content.Context
import ch.proximeety.proximeety.core.interactions.*
import ch.proximeety.proximeety.core.repositories.UserRepository
import ch.proximeety.proximeety.data.repositories.UserRepositoryImplementation
import ch.proximeety.proximeety.data.sources.BluetoothService
import ch.proximeety.proximeety.data.sources.FirebaseAccessObject
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
    fun provideUserRepository(
        firebaseAccessObject: FirebaseAccessObject,
        bluetoothService: BluetoothService
    ): UserRepository {
        return UserRepositoryImplementation(firebaseAccessObject, bluetoothService)
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
            togglePostLike = TogglePostLike(repository),
            isPostLiked = IsPostLiked(repository),
            setActivity = SetActivity(repository),
            setAuthenticatedUserVisible = SetAuthenticatedUserVisible(repository),
            signOut = SignOut(repository),
            getPostUserId = GetPostUserId(repository)
        )
    }
}