package ch.proximeety.proximeety.di

import ch.proximeety.proximeety.core.interactions.AuthenticateWithGoogle
import ch.proximeety.proximeety.core.interactions.GetAuthenticatedUser
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.core.repositories.UserRepository
import ch.proximeety.proximeety.data.repositories.UserRepositoryMockImplementation
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
    fun provideUserRepository(): UserRepository {
        return UserRepositoryMockImplementation()
    }

    @Provides
    @Singleton
    fun provideUserInteractions(repository: UserRepository): UserInteractions {
        return UserInteractions(
            getAuthenticatedUser = GetAuthenticatedUser(repository),
            authenticateWithGoogle = AuthenticateWithGoogle(repository)
        )
    }
}