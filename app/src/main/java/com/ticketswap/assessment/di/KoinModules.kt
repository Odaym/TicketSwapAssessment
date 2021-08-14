package com.ticketswap.assessment.di

import com.ticketswap.assessment.App.Companion.appInstance
import com.ticketswap.assessment.BuildConfig
import com.ticketswap.assessment.PrefStore
import com.ticketswap.assessment.PrefStoreImpl
import com.ticketswap.assessment.base.BaseViewModel
import com.ticketswap.assessment.network.*
import com.ticketswap.assessment.ui.artist.ArtistDetailViewModel
import com.ticketswap.assessment.ui.login.LoginViewModel
import com.ticketswap.assessment.ui.search.SearchViewModel
import com.ticketswap.assessment.util.NetworkAvailabilityChecker
import com.ticketswap.assessment.util.NetworkAvailabilityCheckerImpl
import com.ticketswap.assessment.util.ResourcesProvider
import com.ticketswap.assessment.util.ResourcesProviderImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val json = Json {
    encodeDefaults = false
    ignoreUnknownKeys = true
    coerceInputValues = true
}

val application = module {
    single {
        appInstance
    }

    single {
        json
    }

    single<PrefStore> {
        PrefStoreImpl(get())
    }

    single<ResourcesProvider> {
        ResourcesProviderImpl(get())
    }

    single(IoScheduler) {
        Schedulers.io()
    }

    single(UiScheduler) {
        AndroidSchedulers.mainThread()
    }
}

val network = module {

    single<NetworkAvailabilityChecker> {
        NetworkAvailabilityCheckerImpl(get())
    }

    single {
        HostProvider()
    }

    factory {
        val cache = Cache(androidContext().cacheDir, 10 * 1024 * 1024L)

        val client = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .also {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(
                        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                } else {
                    it.addInterceptor(
                        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                }
            }
            .addInterceptor(CacheInterceptor())
            .cache(cache)
            .build()

        NetworkRequestProcessor(client, get())
    }

    single<ApiService> {
        ApiServiceImpl(
            processor = get(),
            hostProvider = get(),
            prefStore = get()
        )
    }
}

val viewModels = module {
    single {
        BaseViewModel.Dependencies(
            resourceProvider = get(),
            ioScheduler = get(IoScheduler),
            uiScheduler = get(UiScheduler),
        )
    }
    viewModel {
        LoginViewModel(
            dependencies = get(),
            prefStore = get(),
            networkChecker = get()
        )
    }

    viewModel {
        SearchViewModel(
            dependencies = get(),
            apiService = get(),
            networkChecker = get()
        )
    }

    viewModel {
        ArtistDetailViewModel(
            dependencies = get()
        )
    }
}