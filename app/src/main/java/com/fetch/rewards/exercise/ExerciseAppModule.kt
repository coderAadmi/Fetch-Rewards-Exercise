package com.fetch.rewards.exercise

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object ExerciseAppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): ExerciseAppDb =
        Room.databaseBuilder(context, ExerciseAppDb::class.java,"exercise")
            .fallbackToDestructiveMigration(true).build()


    @Provides
    fun provideListItemDao(db: ExerciseAppDb): ListItemDao = db.listItemDao()

    @Singleton
    @Provides
    fun provideListItemRepository(api : ListApi ,listItemDao: ListItemDao): ListItemRepository = ListItemRepository(api,listItemDao)



    @Singleton
    @Provides
    fun provideListApi() : ListApi =
        Retrofit.Builder().baseUrl("https://hiring.fetch.com/")
            .client(OkHttpClient
                .Builder().connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .writeTimeout(10000L, TimeUnit.MILLISECONDS).build())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ListApi::class.java)

}