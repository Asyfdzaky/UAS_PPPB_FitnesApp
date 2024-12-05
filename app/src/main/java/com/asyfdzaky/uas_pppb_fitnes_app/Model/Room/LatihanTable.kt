package com.asyfdzaky.uas_pppb_fitnes_app.Model.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.asyfdzaky.uas_pppb_fitnes_app.databaseLokal.Converters

@Entity(tableName = "latihan_table")
@TypeConverters(Converters::class)
data class LatihanTable(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "jumlah_latihan")
    val jumlahLatihan: String,
    @ColumnInfo(name = "total_repetisi")
    val jumlahRepetisi: String,
    @ColumnInfo(name = "details")
    val details: List<String>,
)
