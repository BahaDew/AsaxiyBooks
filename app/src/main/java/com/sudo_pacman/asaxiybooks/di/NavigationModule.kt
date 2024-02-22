package com.sudo_pacman.asaxiybooks.di

import com.sudo_pacman.asaxiybooks.navigation.AppNavigationDispatcher
import com.sudo_pacman.asaxiybooks.navigation.AppNavigationHandler
import com.sudo_pacman.asaxiybooks.navigation.AppNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @[Binds Singleton]
    fun bindAppNavigator(impl : AppNavigationDispatcher): AppNavigator


    @[Binds Singleton]
    fun bindAppNavigationHandler(imp: AppNavigationDispatcher): AppNavigationHandler

}