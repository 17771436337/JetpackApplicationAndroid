package cmy.project.lib_home.di


import cmy.project.lib_home.model.HomeMainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val homeRepoModule = module{

}


val homeViewModelModule = module{
    viewModel {
        HomeMainViewModel()
    }
}