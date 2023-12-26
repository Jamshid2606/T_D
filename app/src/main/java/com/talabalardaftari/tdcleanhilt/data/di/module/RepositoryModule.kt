package com.talabalardaftari.tdcleanhilt.data.di.module

import com.talabalardaftari.tdcleanhilt.data.auth.AuthRepositoryImpl
import com.talabalardaftari.tdcleanhilt.data.auth.AuthService
import com.talabalardaftari.tdcleanhilt.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideAuthRepository(authService: AuthService):AuthRepository{
        return AuthRepositoryImpl(authService)
    }
}