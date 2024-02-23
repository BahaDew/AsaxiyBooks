package com.sudo_pacman.asaxiybooks.utils

import android.util.Log
import com.google.firebase.firestore.QuerySnapshot
import com.sudo_pacman.asaxiybooks.data.entity.EntityBookData
import com.sudo_pacman.asaxiybooks.data.model.BookUIData
import com.sudo_pacman.asaxiybooks.data.model.UserData

fun String.myLog(tag: String = "TTT") = Log.d(tag, this)

fun BookUIData.toEntityBookData(path: String): EntityBookData {
    return EntityBookData(
        id = 0,
        docID = this.docID,
        audioUrl = this.audioUrl,
        author = this.author,
        bookUrl = this.bookUrl,
        categoryId = this.categoryId,
        coverImage = this.coverImage,
        description = this.description,
        filePath = path,
        name = this.name,
        totalSize = this.totalSize,
        type = this.type
    )
}

object Mapper {
    fun QuerySnapshot.toUserDataList(): List<UserData> {
        val userList = mutableListOf<UserData>()
        for (document in documents) {
            val id = document.id
            val name = document.getString("name") ?: ""
            val gmail = document.getString("gmail") ?: ""
            val password = document.getString("password") ?: ""
            val userData = UserData(id, name, gmail, password)
            userList.add(userData)
        }
        return userList
    }
}
