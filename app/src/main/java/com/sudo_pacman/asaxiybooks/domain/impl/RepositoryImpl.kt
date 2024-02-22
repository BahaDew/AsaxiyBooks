package com.sudo_pacman.asaxiybooks.domain.impl

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sudo_pacman.asaxiybooks.data.model.BookUIData


class RepositoryImpl {
    private val fireStore = Firebase.firestore

    fun getBooks() {
        fireStore
            .collection("book_data")
            .addSnapshotListener { value, error ->
                val books = mutableListOf<BookUIData>()

                value?.forEach {snapshot ->
                    books.add(
                        BookUIData(
                            docID = snapshot.id,
                            author = snapshot.data.getOrDefault("author", "").toString(),,,
                            coverImage = snapshot.data.getOrDefault("coverImage", "").toString(),
                            description = snapshot.data.getOrDefault("description", "").toString(),
                            filePath = snapshot.data.getOrDefault("filePath", "").toString(),
                            name = snapshot.data.getOrDefault("name", "").toString(),
                            totalSize = snapshot.data.getOrDefault("totalSize", "").toString(),
                            type = snapshot.data.getOrDefault("pdf", "").toString(),
                        )
                    )
                }
            }
    }
}