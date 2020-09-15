package com.example.animalsappkotlin.di

import com.example.animalsappkotlin.viewmodel.ListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ViewModelComponent {
    fun inject(viewModel: ListViewModel)
}