package com.example.animalsappkotlin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.animalsappkotlin.model.AnimalApiService
import com.example.animalsappkotlin.model.Animals
import com.example.animalsappkotlin.model.ApiKey
import com.example.animalsappkotlin.util.SharedPreferenceHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListViewModel(application: Application) : AndroidViewModel(application){

    val animals by lazy { MutableLiveData<List<Animals>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    private val compositeDisposable = CompositeDisposable()

    private val apiService = AnimalApiService()

    private val prefs = SharedPreferenceHelper(getApplication())

    private var invalidApiKey = false


    fun refresh()
    {
        loading.value = true
        loadError.value = false
        invalidApiKey = false
        val key : String? = prefs.getApiKey()
        if (key.isNullOrEmpty()) {
            getKey()
        }else{
            getAnimals(key)
        }
    }

    fun hardRefresh()
    {
        loading.value = true
        getKey()
    }

    private fun getKey()
    {

        compositeDisposable.add(
            apiService.getApiKey().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ApiKey>()
            {
                override fun onSuccess(key : ApiKey) {

                    if (key.key.isNullOrEmpty())
                    {
                        loadError.value = true
                        loading.value = false
                    }else{
                        prefs.saveApiKey(key.key)
                        getAnimals(key.key)
                    }
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    loading.value = false
                    loadError.value = true
                }

            })
        )

    }

    private fun getAnimals(key : String) {

        compositeDisposable.add(
            apiService.getAnimals(key)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Animals>>(){
                    override fun onSuccess(list : List<Animals>) {
                        loadError.value = false
                        loading.value = false
                        animals.value = list
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        if (!invalidApiKey)
                        {
                            invalidApiKey = true
                            getKey()
                        }else {
                            loadError.value = true
                            loading.value = false
                            animals.value = null
                        }
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}