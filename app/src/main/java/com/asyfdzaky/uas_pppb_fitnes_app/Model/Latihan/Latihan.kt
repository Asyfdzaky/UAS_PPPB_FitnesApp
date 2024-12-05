package com.asyfdzaky.uas_pppb_fitnes_app.Model.Latihan

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "latihan_db")
data class Latihan(
    @SerializedName("_id")
    val id: String ?  = null,
    @SerializedName("name")
    val name: String,
    @SerializedName("jumlah_latihan")
    val jumlahLatihan: String,
    @SerializedName("total_repetisi")
    val jumlahRepetisi: String,
    @SerializedName("details")
    val details: List<String>
)
