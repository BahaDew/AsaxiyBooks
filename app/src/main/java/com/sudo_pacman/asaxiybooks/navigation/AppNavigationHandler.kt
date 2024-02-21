package com.sudo_pacman.asaxiybooks.navigation

import com.sudo_pacman.asaxiybooks.navigation.AppNavigation
import kotlinx.coroutines.flow.Flow

interface AppNavigationHandler {
    val buffer : Flow<AppNavigation>
}