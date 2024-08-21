package de.ljz.questify.data.database.models.entities.quests

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "main_quests")
data class MainQuestEntity(
  @PrimaryKey
  @ColumnInfo(name = "id")
  val id: Int,

  @ColumnInfo(name = "title")
  val title: String,

  @ColumnInfo(name = "description")
  val description: String? = null,

  @ColumnInfo(name = "points")
  val points: Int,

  @ColumnInfo(name = "due_date")
  val dueDate: Date? = null,

  @ColumnInfo(name = "created_at")
  val createdAt: Date,

  @ColumnInfo(name = "updated_at")
  val updatedAt: Date? = null,

  @ColumnInfo(name = "lock_deletion")
  val lockDeletion: Boolean,

  @ColumnInfo(name = "done")
  val done: Boolean,

  @ColumnInfo(name = "archived")
  val archived: Boolean
)
