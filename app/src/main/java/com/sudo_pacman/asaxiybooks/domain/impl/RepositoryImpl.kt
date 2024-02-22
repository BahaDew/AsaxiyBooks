package com.sudo_pacman.asaxiybooks.domain.impl

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.data.model.CategoryByBookData
import com.sudo_pacman.asaxiybooks.domain.Repository
import com.sudo_pacman.asaxiybooks.utils.myLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor() : Repository {
    private val fireStore = Firebase.firestore

    override val booksList: MutableSharedFlow<List<BookUIData>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

    override val bookLoadError: MutableSharedFlow<String> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

    override fun getBooks() {
        fireStore
            .collection("books_data")
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

    override fun getCategoryByBooks() :  Flow<Result<List<CategoryByBookData>>> = callbackFlow<Result<List<CategoryByBookData>>> {
        val categoryList = ArrayList<CategoryByBookData>()
        fireStore.collection("category")
            .addSnapshotListener { value, _ ->
                var count = 0
                value?.forEach {
                    val categoryName = it.data.getOrDefault("name", "") as String
                    val categoryId = it.id
                    fireStore.collection("books_data")
                        .whereEqualTo("categoryId", categoryId)
                        .get()
                        .addOnSuccessListener { qs ->
                            count++
                            val listBookData = ArrayList<BookUIData>()
                            qs.forEach { snapshot ->
                                val data =
                                    BookUIData(
                                        docID = snapshot.id,
                                        audioUrl = snapshot.data.getOrDefault("audioUrl", "")
                                            .toString(),
                                        author = snapshot.data.getOrDefault("author", "")
                                            .toString(),
                                        bookUrl = snapshot.data.getOrDefault("bookUrl", "")
                                            .toString(),
                                        categoryId = snapshot.data.getOrDefault("categoryId", "")
                                            .toString(),
                                        coverImage = snapshot.data.getOrDefault("coverImage", "")
                                            .toString(),
                                        description = snapshot.data.getOrDefault("description", "")
                                            .toString(),
                                        filePath = snapshot.data.getOrDefault("filePath", "")
                                            .toString(),
                                        name = snapshot.data.getOrDefault("name", "").toString(),
                                        totalSize = snapshot.data.getOrDefault("totalSize", "")
                                            .toString(),
                                        type = snapshot.data.getOrDefault("type", "").toString(),
                                    )
                                listBookData.add(data)
                            }
                            categoryList.add(
                                CategoryByBookData(
                                    categoryName = categoryName,
                                    categoryId = categoryId,
                                    books = listBookData,
                                    type = 10
                                )
                            )
                            if(count == it.data.size) {
                                trySend(Result.success(categoryList))
                            }
                        }
                        .addOnFailureListener {exp ->
                            trySend(Result.failure(exp))
                        }
                }
            }
        awaitClose()
    }.flowOn(Dispatchers.IO)

    override fun setData(books: List<BookUIData>): Flow<Result<Unit>> = callbackFlow {

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