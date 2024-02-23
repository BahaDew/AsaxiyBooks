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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
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


    private val allBookData = ArrayList<BookUIData>()

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

    override fun getCategoryByBooks(): Flow<Result<List<CategoryByBookData>>> =
        callbackFlow<Result<List<CategoryByBookData>>> {
            val categoryList = ArrayList<CategoryByBookData>()
            var count = 0
            fireStore.collection("category")
                .addSnapshotListener { value, _ ->
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
                                            categoryId = snapshot.data.getOrDefault(
                                                "categoryId",
                                                ""
                                            )
                                                .toString(),
                                            coverImage = snapshot.data.getOrDefault(
                                                "coverImage",
                                                ""
                                            )
                                                .toString(),
                                            description = snapshot.data.getOrDefault(
                                                "description",
                                                ""
                                            )
                                                .toString(),
                                            filePath = snapshot.data.getOrDefault("filePath", "")
                                                .toString(),
                                            name = snapshot.data.getOrDefault("name", "")
                                                .toString(),
                                            totalSize = snapshot.data.getOrDefault("totalSize", "")
                                                .toString(),
                                            type = snapshot.data.getOrDefault("type", "")
                                                .toString(),
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
                                "$count chi ma'lumot ${value.size()} ta ma'lumot bor".myLog("BAHA")
                                if (count == value.size()) {
                                    "$count ta ma'lumot".myLog("BAHA")
                                    trySend(Result.success(categoryList))
                                }
                            }
                            .addOnFailureListener { exp ->
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

//    override fun getBookByName(name: String): List<BookUIData> {
//          if(name.isEmpty()) return  arrayListOf()
//        val ls = allBookData.filter {
//            it.name.lowercase().contains(name.lowercase()) ?: false
//        }
//
//        return ls
//    }

    override fun getBooksByName(name: String): Flow<Result<List<BookUIData>>> =
        channelFlow<Result<List<BookUIData>>> {
            ("name -> #$name#  " + name.trim().isEmpty()).myLog("BUSH")
            if (name.trim().isEmpty())
                trySend(Result.success(arrayListOf()))
            else {
                fireStore.collection("books_data")
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        val booksList = ArrayList<BookUIData>()
                        querySnapshot.forEach { snapshot ->
                            val userName = snapshot.data.getOrDefault("name", "").toString()
                            "qidir name : $name kegan name  solishtir $userName ${
                                userName.lowercase().contains(name.lowercase())
                            }".myLog("BAHA2")
                            "2 - > qidir name : $name kegan name  solishtir $userName ${
                                name.lowercase().contains(userName.lowercase())
                            }".myLog("BAHA2")
                            if (userName.lowercase().contains(name.lowercase())) {
                                val data =
                                    BookUIData(
                                        docID = snapshot.id,
                                        audioUrl = snapshot.data.getOrDefault("audioUrl", "")
                                            .toString(),
                                        author = snapshot.data.getOrDefault("author", "")
                                            .toString(),
                                        bookUrl = snapshot.data.getOrDefault("bookUrl", "")
                                            .toString(),
                                        categoryId = snapshot.data.getOrDefault(
                                            "categoryId",
                                            ""
                                        )
                                            .toString(),
                                        coverImage = snapshot.data.getOrDefault(
                                            "coverImage",
                                            ""
                                        )
                                            .toString(),
                                        description = snapshot.data.getOrDefault(
                                            "description",
                                            ""
                                        )
                                            .toString(),
                                        filePath = snapshot.data.getOrDefault("filePath", "")
                                            .toString(),
                                        name = snapshot.data.getOrDefault("name", "")
                                            .toString(),
                                        totalSize = snapshot.data.getOrDefault("totalSize", "")
                                            .toString(),
                                        type = snapshot.data.getOrDefault("type", "")
                                            .toString(),
                                    )
                                booksList.add(data)
                            }
                        }
                        booksList.size.toString().myLog("BAHA")
                        trySend(Result.success(booksList))
                    }
                    .addOnFailureListener { ex ->
                        trySend(Result.failure(ex))
                    }
            }
            awaitClose()
        }.flowOn(Dispatchers.IO).catch { emit(Result.failure(it)) }
}