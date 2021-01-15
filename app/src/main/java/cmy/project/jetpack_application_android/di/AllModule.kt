package cmy.project.jetpack_application_android.di

import cmy.project.cmy_account.di.accountRepoModule
import cmy.project.cmy_account.di.accountViewModelModule
import cmy.project.cmy_user.di.loginRepoModule
import cmy.project.cmy_user.di.loginViewModelModule
import cmy.project.lib_home.di.homeRepoModule
import cmy.project.lib_home.di.homeViewModelModule
import cmy.project.lib_net.net.RetrofitClient
import com.google.gson.GsonBuilder
import org.koin.dsl.module

val otherModule = module {
    single {
        RetrofitClient.instance
    }

    single {
        GsonBuilder().disableHtmlEscaping().create()
    }
}

val allModule = listOf(
    otherModule,
    loginRepoModule, loginViewModelModule,
    homeRepoModule, homeViewModelModule,
    accountRepoModule, accountViewModelModule,
)