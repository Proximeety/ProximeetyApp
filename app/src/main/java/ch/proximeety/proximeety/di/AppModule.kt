package ch.proximeety.proximeety.di

import android.content.Context
import ch.proximeety.proximeety.core.interactions.AuthenticateWithGoogle
import ch.proximeety.proximeety.core.interactions.GetAuthenticatedUser
import ch.proximeety.proximeety.core.interactions.UserInteractions
import ch.proximeety.proximeety.core.repositories.UserRepository
import ch.proximeety.proximeety.data.repositories.UserRepositoryImplementation
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
    fun provideUserRepository(firebaseAccessObject: FirebaseAccessObject): UserRepository {
        return UserRepositoryImplementation(firebaseAccessObject)
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