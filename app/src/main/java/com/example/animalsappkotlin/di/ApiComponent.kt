package com.example.animalsappkotlin.di

import com.example.animalsappkotlin.model.AnimalApiService
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: AnimalApiService)
}