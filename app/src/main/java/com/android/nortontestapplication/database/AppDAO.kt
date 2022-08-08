package com.android.nortontestapplication.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.nortontestapplication.model.ServersListItem

@Dao
interface AppDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertServers(serversListItem: ServersListItem) : Long

    @Query("DELETE FROM servers_list_item_table")
    fun deleteAll() : Int

    @Query("SELECT * FROM servers_list_item_table")
    fun getAllServers() : LiveData<List<ServersListItem>>

}