package com.sudo_pacman.asaxiybooks.domain.impl

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.ktx.storage
import com.sudo_pacman.asaxiybooks.data.dao.BookDao
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.data.model.UploadData
import com.sudo_pacman.asaxiybooks.data.source.MySharedPreference
import com.sudo_pacman.asaxiybooks.utils.Mapper
import com.sudo_pacman.asaxiybooks.utils.Mapper.toUserDataList
import com.sudo_pacman.asaxiybooks.utils.myLog
import com.sudo_pacman.asaxiybooks.utils.toEntityBookData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryPdf @Inject constructor(
    private val bookDao: BookDao,
) {
    private val fireStorage = Firebase.storage
    private val fireStore = Firebase.firestore
    private var loadTask: FileDownloadTask? = null

    fun downloadBook(bookUIData: BookUIData): Flow<Result<File>> = callbackFlow {

        "repo olish kerak $bookUIData".myLog()

        val bookId = bookDao.isHas(bookUIData.bookUrl)
        if (bookId != 0L) {
            "bunaqasi borakan".myLog()
            trySend(Result.success(File(bookDao.getBooksById(id = bookId).filePath)))
        } else {
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
                .addOnProgressListener {
                    "yuklanyapti ${it.bytesTransferred * 100 / it.totalByteCount}".myLog()
                }
        }

        awaitClose()
    }

    fun downloadBookWithProgress(bookUIData: BookUIData): Flow<UploadData> = callbackFlow {

        "repo olish kerak $bookUIData".myLog()

        val bookId = bookDao.isHas(bookUIData.bookUrl)

        if (bookId != 0L) {
            "repo o'zi bor ekan".myLog()
            trySend(UploadData.Success(File(bookDao.getBooksById(id = bookId).filePath)))
        } else {
            "bunaqasi yo'q ekan yuklaymiz".myLog()

            val book = File.createTempFile("Book", "pdf")
            loadTask = fireStorage.getReferenceFromUrl(bookUIData.bookUrl)
                .getFile(book)

            loadTask!!
                .addOnSuccessListener {
                    bookDao.setBook(bookUIData.toEntityBookData(book.path))
                    bookUIData.filePath = book.absolutePath

                    fireStore
                        .collection("books_data")
                        .document(bookUIData.docID)
                        .set(bookUIData)

                    trySend(UploadData.Success(File(bookUIData.filePath)))
                }
                .addOnFailureListener {
                    trySend(UploadData.Error(it.message ?: ""))
                }
                .addOnPausedListener {
                    trySend(UploadData.PAUSE)
                }
                .addOnCanceledListener {
                    trySend(UploadData.CANCEL)
                }
                .addOnProgressListener {
//                    "yuklanyapti ${it.bytesTransferred*100 / it.totalByteCount}".myLog()
                    trySend(UploadData.Progress((it.bytesTransferred * 100) / it.totalByteCount))
                }
        }

        awaitClose()
    }
        .flowOn(Dispatchers.IO)


    fun cancelDownload() {
        loadTask?.cancel()
    }

    fun pauseDownload() {
        loadTask?.pause()
    }

    fun resumeDownload() {
        loadTask?.resume()
    }

    fun buyBook(book: BookUIData): Flow<Unit> = callbackFlow {
        val user = MySharedPreference.getUserData()
        user.booksId.add(book.docID)

        fireStore
            .collection("users")
            .document(MySharedPreference.getUserData().id)
            .set(user)
            .addOnSuccessListener {
                "repo add book to user".myLog()
                trySend(Unit)
            }
            .addOnFailureListener {
                "repo add book to user fail ${it.message}".myLog()
            }

        awaitClose()
    }

    fun isBought(bookUIData: BookUIData): Flow<Boolean> = callbackFlow<Boolean>{
        fireStore
            .collection("users")
            .whereEqualTo("password", MySharedPreference.getUserData().password)
            .whereEqualTo("gmail", MySharedPreference.getUserData().gmail)
            .limit(1)
            .addSnapshotListener { value, error ->
                val user = Mapper.run { value!!.toUserDataList()[0] }

                trySend(user.booksId.contains(bookUIData.docID))
            }

        awaitClose()
    }
}
