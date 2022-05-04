package ch.proximeety.proximeety.di

import ch.proximeety.proximeety.core.interactions.*
import ch.proximeety.proximeety.core.repositories.UserRepository
import ch.proximeety.proximeety.data.repositories.UserRepositoryMockImplementation
import ch.proximeety.proximeety.presentation.navigation.NavigationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Main test application module.
 */
@Module
@InstallIn(SingletonComponent::class)
class TestAppModule {

    @Provides
    @Singleton
    fun provideNavigationManager(): NavigationManager {
        return NavigationManager()
    }

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository {
        return UserRepositoryMockImplementation()
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
            setActivity = SetActivity(repository),
            setAuthenticatedUserVisible = SetAuthenticatedUserVisible(repository),
            signOut = SignOut(repository),
            getPostUserId = GetPostUserId(repository),
            downloadStory = DownloadStory(repository),
            postStory = PostStory(repository),
            getPostsByUserId = GetPostsByUserId(repository),
            getStoriesByUserId = GetStoriesByUserId(repository),
            getFriendsLocations = GetFriendsLocations(repository),
            startLiveLocation = StartLiveLocation(repository),
        )
    }
}
