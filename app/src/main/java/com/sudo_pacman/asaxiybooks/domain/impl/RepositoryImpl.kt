package com.sudo_pacman.asaxiybooks.domain.impl

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.utils.myLog
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.callbackFlow


class RepositoryImpl {
    private val fireStore = Firebase.firestore

    val booksList: MutableSharedFlow<List<BookUIData>> = MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    val bookLoadError: MutableSharedFlow<String> = MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

    fun getBooks() {
        fireStore
            .collection("book_data")
            .addSnapshotListener { value, error ->
                val books = mutableListOf<BookUIData>()

                value?.forEach { snapshot ->

                    books.add(
                        BookUIData(
                            docID = snapshot.id,
                            audioUrl = snapshot.data.getOrDefault("audioUrl", "").toString(),
                            author = snapshot.data.getOrDefault("author", "").toString(),
                            bookUrl = snapshot.data.getOrDefault("bookUrl", "").toString(),
                            categoryId = snapshot.data.getOrDefault("categoryId", "").toString(),
                            coverImage = snapshot.data.getOrDefault("coverImage", "").toString(),
                            description = snapshot.data.getOrDefault("description", "").toString(),
                            filePath = snapshot.data.getOrDefault("filePath", "").toString(),
                            name = snapshot.data.getOrDefault("name", "").toString(),
                            totalSize = snapshot.data.getOrDefault("totalSize", "").toString(),
                            type = snapshot.data.getOrDefault("pdf", "").toString(),
                        )
                    )
                }

                if (error != null) bookLoadError.tryEmit(error.message.toString())

                "yaroqli olindi ${books.size}".myLog()
                booksList.tryEmit(books)
            }
    }

    fun setData(books: List<BookUIData>): Flow<Result<Unit>> = callbackFlow {

        for (index in books.indices) {
            fireStore
                .collection("books_data")
                .add(books[index])
                .addOnSuccessListener {

                }
                .addOnFailureListener {

                }
        }

        awaitClose()
    }
}