package com.example.chelyabinskgo.di

import androidx.room.Room
import com.example.chelyabinskgo.data.database.AppDatabase
import com.example.chelyabinskgo.data.remote.ApiConfig
import com.example.chelyabinskgo.data.remote.ApiService
import com.example.chelyabinskgo.data.repository.EventsRepository
import com.example.chelyabinskgo.data.repository.EventsRepositoryImpl
import com.example.chelyabinskgo.data.repository.PlacesRepository
import com.example.chelyabinskgo.data.repository.PlacesRepositoryImpl
import com.example.chelyabinskgo.domain.usecase.GetEventsUseCase
import com.example.chelyabinskgo.domain.usecase.GetPlacesUseCase
import com.example.chelyabinskgo.domain.usecase.ToggleEventFavoriteUseCase
import com.example.chelyabinskgo.presentation.viewmodel.EventsViewModel
import com.example.chelyabinskgo.presentation.viewmodel.PlacesViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single { get<Retrofit>().create(ApiService::class.java) }

    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "chelyabinsk_go.db"
        ).build()
    }
    single { get<AppDatabase>().favoritesDao() }

    single<EventsRepository> { EventsRepositoryImpl(get(), get()) }
    single<PlacesRepository> { PlacesRepositoryImpl(get()) }

    single { GetEventsUseCase(get()) }
    single { GetPlacesUseCase(get()) }
    single { ToggleEventFavoriteUseCase(get()) }

    viewModel { EventsViewModel(get(), get()) }
    viewModel { PlacesViewModel(get()) }
}
