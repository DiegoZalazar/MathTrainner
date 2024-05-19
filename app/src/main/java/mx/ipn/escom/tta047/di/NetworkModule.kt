package mx.ipn.escom.tta047.di
/*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.ipn.escom.TTA024.data.network.EjercicioApiClient
import mx.ipn.escom.TTA024.data.network.LeccionApiClient
import mx.ipn.escom.TTA024.data.network.ModuloApiClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://w8b6as9g2h.execute-api.us-east-1.amazonaws.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideModuloApiClient(retrofit: Retrofit): ModuloApiClient {
        return retrofit.create(ModuloApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideLeccionApiClient(retrofit: Retrofit): LeccionApiClient {
        return retrofit.create(LeccionApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideEjercicioApiClient(retrofit: Retrofit): EjercicioApiClient {
        return retrofit.create(EjercicioApiClient::class.java)
    }

}
*/
