package com.sudo_pacman.asaxiybooks.di

import com.sudo_pacman.asaxiybooks.domain.LoginRepository
import com.sudo_pacman.asaxiybooks.domain.Repository
import com.sudo_pacman.asaxiybooks.domain.impl.LoginRepositoryImpl
import com.sudo_pacman.asaxiybooks.domain.impl.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindRepository(impl: RepositoryImpl): Repository

    @[Binds Singleton]
    fun bindLogin(impl: LoginRepositoryImpl): LoginRepository
}