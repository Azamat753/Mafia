package com.lawlett.mafia.di

import com.lawlett.mafia.ui.auth.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var viewModelModule = module {
    viewModel { AuthViewModel() }
}

var databaseModule = module {


}

var networkModule = module {


}

var repositoryModule = module {


}