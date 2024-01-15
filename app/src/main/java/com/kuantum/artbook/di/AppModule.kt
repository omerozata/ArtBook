package com.kuantum.artbook.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kuantum.artbook.R
import com.kuantum.artbook.api.RetrofitApi
import com.kuantum.artbook.repo.ArtRepository
import com.kuantum.artbook.repo.ArtRepositoryImpl
import com.kuantum.artbook.roomdb.ArtDao
import com.kuantum.artbook.roomdb.ArtDatabase
import com.kuantum.artbook.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, ArtDatabase::class.java, "ArtBookDatabase"
    ).build()

    @Singleton
    @Provides
    fun injectDao(database: ArtDatabase) = database.artDao()

    @Singleton
    @Provides
    fun injectRetrofitApi(): RetrofitApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitApi::class.java)
    }

    @Singleton
    @Provides
    fun injectRepository(dao: ArtDao, api: RetrofitApi) =
        ArtRepositoryImpl(dao, api) as ArtRepository

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
        )

    @Singleton
    @Provides
    fun injectSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(
            "com.kuantum.artbook",
            Context.MODE_PRIVATE
        )
    }
}