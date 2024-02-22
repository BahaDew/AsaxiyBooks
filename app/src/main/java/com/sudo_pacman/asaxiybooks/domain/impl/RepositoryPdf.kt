package com.sudo_pacman.asaxiybooks.domain.impl

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.io.File

class RepositoryPdf {
    private val storage = Firebase.storage.reference

    fun downloadBook(): Flow<Result<File>> = callbackFlow {




    }
}