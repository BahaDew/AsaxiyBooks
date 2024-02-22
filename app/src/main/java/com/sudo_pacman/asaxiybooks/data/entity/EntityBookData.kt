package com.sudo_pacman.asaxiybooks.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
data class EntityBookData(
    @PrimaryKey(autoGenerate = true) var id: Long,
    var docID: String,
    var audioUrl: String,
    var author: String,
    var bookUrl: String,
    var categoryId: String,
    var coverImage: String,
    var description: String,
    var filePath: String,
    var name: String,
    var totalSize: String,
    var type: String,
)