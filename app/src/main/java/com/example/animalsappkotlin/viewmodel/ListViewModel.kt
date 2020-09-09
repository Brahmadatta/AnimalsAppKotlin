package com.example.animalsappkotlin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.animalsappkotlin.model.Animals

class ListViewModel(application: Application) : AndroidViewModel(application){

    val animals by lazy { MutableLiveData<List<Animals>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }



    fun refresh()
    {
        getAnimals()
    }

    private fun getAnimals() {

        val a1 = Animals("cat")
        val a2 = Animals("Dog")
        val a3 = Animals("Fox")
        val a4 = Animals("tiger")
        val a5 = Animals("lion")

        val animalList : ArrayList<Animals> = arrayListOf(a1,a2,a3,a4,a5)

        animals.value = animalList
        loadError.value = false
        loading.value = false

    }

}