package com.sudo_pacman.asaxiybooks.domain.impl

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.sudo_pacman.asaxiybooks.data.dao.BookDao
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.utils.myLog
import com.sudo_pacman.asaxiybooks.utils.toEntityBookData
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryPdf @Inject constructor(
    private val bookDao: BookDao
) {
    private val fireStorage = Firebase.storage
    private val fireStore = Firebase.firestore

    fun downloadBook(bookUIData: BookUIData): Flow<Result<File>> = callbackFlow {

        if (bookDao.isHas(bookUIData.bookUrl) != 0L) {
            "bunaqasi borakan".myLog()
            trySend(Result.success(File(bookUIData.filePath)))
        }
        else {
            "bunaqasi yo'q ekan yuklaymiz".myLog()

            val book = File.createTempFile("Book", "pdf")
            fireStorage.getReferenceFromUrl(bookUIData.bookUrl)
                .getFile(book)
                .addOnSuccessListener {
                    bookDao.setBook(bookUIData.toEntityBookData(book.path))
                    bookUIData.filePath = book.absolutePath

                    fireStore
                        .collection("books_data")
                        .document(bookUIData.docID)
                        .set(bookUIData)

                    trySend(Result.success(book))
                }
                .addOnFailureListener {
                    trySend(Result.failure(Throwable(it.message)))
                }
        }

        awaitClose()
    }
}
