package mx.ipn.escom.TTA024.model

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface Api {
 companion object{
     val instance = Retrofit.Builder()
         .baseUrl("")
         .addConverterFactory(MoshiConverterFactory.create())
         .client(
             OkHttpClient.Builder().build()
         ).build().create(Api :: class.java)
 }

}
