package com.example.animalsappkotlin

import com.example.animalsappkotlin.di.ApiModule
import com.example.animalsappkotlin.model.AnimalApiService

class ApiModuleTest(val mockService : AnimalApiService) : ApiModule() {

    override fun provideAnimalApiService(): AnimalApiService {
        return mockService
    }
}