package com.example.animalsappkotlin

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.animalsappkotlin.di.AppModule
import com.example.animalsappkotlin.di.DaggerViewModelComponent
import com.example.animalsappkotlin.model.AnimalApiService
import com.example.animalsappkotlin.model.Animals
import com.example.animalsappkotlin.model.ApiKey
import com.example.animalsappkotlin.util.SharedPreferenceHelper
import com.example.animalsappkotlin.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor

class ListViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var animalApiService: AnimalApiService

    @Mock
    lateinit var prefs : SharedPreferenceHelper

    val application = Mockito.mock(Application::class.java)

    var listViewModel = ListViewModel(application,true)

    private val key = "Test Key"

    @Before
    fun setUp()
    {
        MockitoAnnotations.initMocks(this)

        DaggerViewModelComponent.builder()
            .appModule(AppModule(application))
            .apiModule(ApiModuleTest(animalApiService))
            .prefsModule(PrefsModuleTest(prefs))
            .build()
            .inject(listViewModel)
    }

    @Test
    fun getAnimalsSuccess(){
        Mockito.`when`(prefs.getApiKey()).thenReturn(key)
        val animal = Animals("Cow",null,null,null,null,null,null);
        val animalList = listOf(animal)

        val testSingle = Single.just(animalList)

        Mockito.`when`(animalApiService.getAnimals(key)).thenReturn(testSingle)

        listViewModel.refresh()

        Assert.assertEquals(1,listViewModel.animals.value?.size)
        Assert.assertEquals(false,listViewModel.loadError.value)
        Assert.assertEquals(false,listViewModel.loading.value)


    }

    @Test
    fun getAnimalFailure()
    {
        Mockito.`when`(prefs.getApiKey()).thenReturn(key)

        val testSingle = Single.error<List<Animals>>(Throwable())
        val keySingle = Single.just(ApiKey("OK",key))

        Mockito.`when`(animalApiService.getAnimals(key)).thenReturn(testSingle)
        Mockito.`when`(animalApiService.getApiKey()).thenReturn(keySingle)

        listViewModel.refresh()

        Assert.assertEquals(null,listViewModel.animals.value)
        Assert.assertEquals(false,listViewModel.loading.value)
        Assert.assertEquals(true,listViewModel.loadError.value)

    }

    @Test
    fun getKeySuccess()
    {
        Mockito.`when`(prefs.getApiKey()).thenReturn(null)

        val apiKey = ApiKey("OK",key)
        val keySingle = Single.just(apiKey)

        Mockito.`when`(animalApiService.getApiKey()).thenReturn(keySingle)

        val animals = Animals("cow",null,null,null,null,null,null)
        val animalList = listOf(animals)

        val testSingle = Single.just(animalList)
        Mockito.`when`(animalApiService.getAnimals(key)).thenReturn(testSingle)

        listViewModel.refresh()

        Assert.assertEquals(1,listViewModel.animals.value?.size)
        Assert.assertEquals(false,listViewModel.loading.value)
        Assert.assertEquals(false,listViewModel.loadError.value)


    }

    @Test
    fun getKeyFailure()
    {
        Mockito.`when`(prefs.getApiKey()).thenReturn(null)
        val keySingle = Single.error<ApiKey>(Throwable())

        Mockito.`when`(animalApiService.getApiKey()).thenReturn(keySingle)

        listViewModel.refresh()

        Assert.assertEquals(null,listViewModel.animals.value?.size)
        Assert.assertEquals(true,listViewModel.loadError.value)
        Assert.assertEquals(false,listViewModel.loading.value)

    }

    @Before
    fun setUpRxSchedulers()
    {
        val immediate = object : Scheduler(){
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() },true)
            }
        }

        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }
    }
}