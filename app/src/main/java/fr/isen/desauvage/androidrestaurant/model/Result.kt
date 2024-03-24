package fr.isen.desauvage.androidrestaurant.model

import com.google.gson.annotations.SerializedName
import fr.isen.desauvage.androidrestaurant.model.Data
import java.io.Serializable


data class Result (

    @SerializedName("data" ) var data : ArrayList<Data> = arrayListOf()

): Serializable