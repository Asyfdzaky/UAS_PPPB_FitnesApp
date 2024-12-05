package com.asyfdzaky.uas_pppb_fitnes_app.databaseLokal

import androidx.room.TypeConverter


class Converters {
    // Convert List<String> to a comma-separated String
    @TypeConverter
    fun fromListToString(details: List<String>): String {
        return details.joinToString(", ") // Convert list to comma-separated string
    }

    // Convert comma-separated String back to List<String>
    @TypeConverter
    fun fromStringToList(details: String): List<String> {
        return details.split(", ").map { it.trim() } // Split the string and create a list
    }
}