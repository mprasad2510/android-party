package com.android.nortontestapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "servers_list_item_table")
data class ServersListItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    @SerializedName("id")
    val id : Int = 0,
    @ColumnInfo(name="name")
    @SerializedName("name")
    val name: String,
    @ColumnInfo(name = "distance")
    @SerializedName("distance")
    val distance: Int
)