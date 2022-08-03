package com.test.jokertime.di

import com.test.jokertime.data.RepositoryImpl
import com.test.jokertime.data.remote.RemoteDataSource
import com.test.jokertime.data.remote.RemoteDataSourceImpl
import com.test.jokertime.data.remote.connection.MService
import com.test.jokertime.domain.Repository
import com.test.jokertime.domain.exceptions.IErrorHandler
import com.test.moviehub.data.ErrorHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSourceImpl: RemoteDataSourceImpl,
    ): Repository {
        return RepositoryImpl(remoteDataSourceImpl)
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(
        service: MService
    ): RemoteDataSource {
        return RemoteDataSourceImpl(service)
    }

    @Singleton
    @Provides
    fun provideErrorHandler(): IErrorHandler {
        return ErrorHandler()
    }
}