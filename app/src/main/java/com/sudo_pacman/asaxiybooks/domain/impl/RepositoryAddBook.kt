package com.sudo_pacman.asaxiybooks.domain.impl

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sudo_pacman.asaxiybooks.data.model.BookData
import com.sudo_pacman.asaxiybooks.utils.myLog
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class RepositoryAddBook {
    private val fireStorage = Firebase.firestore
    private var books = mutableListOf<BookData>()

    init {
        "mylog".myLog()
        books = mutableListOf(
            BookData(
                name = "Cho'qintirgan ota",
                author = "Mario Puzo",
                description = "Info",
                totalSize = "2.75",
                coverImage = "",
                filePath = "",
                type = "pdf"
            ),
            BookData(
                name = "O'tkan kunlar",
                author = "Abdulla qodiriy",
                description = "Info",
                totalSize = "1.51",
                coverImage = "",
                filePath = "",
                type = "pdf"
            ),
        )

    }

     fun addBook() = callbackFlow<String> {
        var size = books.size
        "books size ${books.size}".myLog()

        for (index in books.indices) {
            fireStorage
                .collection("book_data")
                .document()
                .set(books[index])
                .addOnSuccessListener {
                    --size

                    "add book: $size".myLog()
                }
                .addOnFailureListener { "add book error ${it.message}".myLog() }
        }

        awaitClose()
    }

}