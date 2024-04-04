package mx.ipn.escom.TTA024.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit(): Retrofit {
       return Retrofit.Builder()
           .baseUrl("https://quh6vek861.execute-api.us-east-1.amazonaws.com/dev")
           .addConverterFactory(GsonConverterFactory.create())
           .build()
    }
}