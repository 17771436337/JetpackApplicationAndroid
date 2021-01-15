package cmy.project.cmy_account.di

import cmy.project.cmy_account.model.MainViewModel
import org.koin.dsl.module

val accountRepoModule = module {

}

val accountViewModelModule = module {
    single {
            MainViewModel()
    }
}