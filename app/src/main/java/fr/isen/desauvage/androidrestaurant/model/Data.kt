package fr.isen.desauvage.androidrestaurant.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Data (
    @SerializedName("name_fr" ) var nameFr : String?          = null,
    @SerializedName("name_en" ) var nameEn : String?          = null,
    @SerializedName("items"   ) var items  : ArrayList<Items> = arrayListOf()
    ) :Serializable
