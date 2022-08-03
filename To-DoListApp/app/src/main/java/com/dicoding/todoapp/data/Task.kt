package com.dicoding.todoapp.data

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//TODO 1 : Define a local database table using the schema in app/schema/tasks.json

@Entity(tableName = "tasks")

data class Task(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    val id: Int = 0,
    @ColumnInfo(name="title")
    @NonNull
    val title: String,
    @ColumnInfo(name = "description")
    @NonNull
    val description: String,
    @ColumnInfo(name = "dueDate")
    @NonNull
    val dueDateMillis: Long,
    @ColumnInfo(name = "completed")
    @NonNull
    val isCompleted: Boolean = false
)
