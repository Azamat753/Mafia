package com.lawlett.mafia.di

import com.google.firebase.auth.FirebaseAuth
import com.lawlett.mafia.data.repository.FirebaseAuthRepository
import com.lawlett.mafia.ui.auth.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var viewModelModule = module {
    viewModel { AuthViewModel(get()) }
}

var databaseModule = module {

}

var networkModule = module {
    factory { FirebaseAuth.getInstance() }
}

var repositoryModule = module {
    factory { FirebaseAuthRepository() }
}