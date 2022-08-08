package com.android.nortontestapplication.database

import androidx.room.TypeConverter
import com.android.nortontestapplication.model.ServersListItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class AppTypeConverter {

    val gson: Gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data: String?): ServersListItem? {
        if (data == null)
            return null
        val listType: Type = object :TypeToken<ServersListItem?>(){}.type
        return gson.fromJson<ServersListItem?>(data,listType)
    }

    @TypeConverter
    fun someObjectListToString(result: ServersListItem?): String? {
        return gson.toJson(result)
    }
}