package com.example.animalsappkotlin

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.animalsappkotlin.model.AnimalApiService
import com.example.animalsappkotlin.util.SharedPreferenceHelper
import com.example.animalsappkotlin.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
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