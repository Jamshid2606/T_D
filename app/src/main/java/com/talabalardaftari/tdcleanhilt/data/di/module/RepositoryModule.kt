package com.talabalardaftari.tdcleanhilt.data.di.module

import com.talabalardaftari.tdcleanhilt.data.auth.AuthRepositoryImpl
import com.talabalardaftari.tdcleanhilt.data.auth.AuthService
import com.talabalardaftari.tdcleanhilt.data.main.MainRepositoryImpl
import com.talabalardaftari.tdcleanhilt.data.main.MainService
import com.talabalardaftari.tdcleanhilt.domain.repository.AuthRepository
import com.talabalardaftari.tdcleanhilt.domain.repository.MainRepository
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
    @Provides
    fun provideMainRepository(mainService: MainService):MainRepository{
        return MainRepositoryImpl(mainService)
    }
}