package cmy.project.cmy_user.di

import cmy.project.cmy_user.model.LoginViewModel
import cmy.project.cmy_user.repository.LoginRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginRepoModule = module {
    single {
        LoginRepository(get())
    }
}


val loginViewModelModule = module {
    viewModel {
        LoginViewModel(get())
    }
}