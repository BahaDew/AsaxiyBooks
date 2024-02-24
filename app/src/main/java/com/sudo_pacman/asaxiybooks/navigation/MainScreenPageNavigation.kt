package com.sudo_pacman.asaxiybooks.navigation

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainScreenPageNavigation @Inject constructor() {
    var pageTo : ((Int) -> Unit)? = null
}