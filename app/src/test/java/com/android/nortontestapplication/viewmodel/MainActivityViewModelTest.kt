package com.android.nortontestapplication.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.android.nortontestapplication.model.AuthToken
import com.android.nortontestapplication.model.ServersListItem
import com.android.nortontestapplication.repository.ServersRepository
import com.android.nortontestapplication.utils.Constants
import com.android.nortontestapplication.utils.Event
import com.android.nortontestapplication.utils.SessionManager
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verify
import retrofit2.Response


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainActivityViewModelTest {

    @Mock
    lateinit var repository: ServersRepository

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var serversListObserver: Observer<List<ServersListItem>>

    @Mock
    lateinit var processingObserver: Observer<Boolean>

    @Mock
    lateinit var statusMessageObserver: Observer<Event<String>>

    @Mock
    lateinit var sessionManager: SessionManager

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var viewModel: MainActivityViewModel


    @Before
    fun setUp() {
        viewModel = MainActivityViewModel(repository, context)
        viewModel.serversList.observeForever(serversListObserver)
        viewModel.processing.observeForever(processingObserver)
        viewModel.statusMessage.observeForever(statusMessageObserver)
    }

    @Test
    fun `getServersList failure Testing`() {
        runBlocking {
            val dummyResponse = Response.error<String>(500,
                ResponseBody.create("text/plain".toMediaType(),
                    "Unable to access servers list"))
            doReturn(dummyResponse).`when`(repository).getServersList(AuthToken("Bearer ${sessionManager.fetchAuthToken()}"))
            viewModel.getAllServersList()

            verify(processingObserver).onChanged(true)
            verify(repository).getServersList(AuthToken("Bearer ${sessionManager.fetchAuthToken()}"))
            verify(processingObserver).onChanged(false)

            val expectedResult = "Failed to fetch list , Error code: 500"

            verify(statusMessageObserver).onChanged(Event(expectedResult))
            viewModel.statusMessage.removeObserver(statusMessageObserver)
        }
    }

    @Test
    fun `getCategory success testing`() {
        runBlockingTest {
            val typeToken = object : TypeToken<List<ServersListItem>>() {}.type

            val dummyResponse = Response.success(
                Gson().fromJson<List<ServersListItem>>(Constants.SERVER_LIST_RESPONSE, typeToken))

            doReturn(dummyResponse).`when`(repository).getServersList(AuthToken("Bearer ${sessionManager.fetchAuthToken()}"))

            val viewModel = MainActivityViewModel(repository,context)
            viewModel.serversList.observeForever(serversListObserver)
            viewModel.processing.observeForever(processingObserver)
            viewModel.getAllServersList()

            verify(processingObserver).onChanged(true)
            verify(repository).getServersList(AuthToken("Bearer ${sessionManager.fetchAuthToken()}"))
            verify(processingObserver).onChanged(false)

            val expectedResult = Gson().fromJson(Constants.SERVER_LIST_RESPONSE,ServersListItem::class.java)

            verify(serversListObserver).onChanged(listOf(expectedResult))
            viewModel.serversList.removeObserver(serversListObserver)
            viewModel.processing.removeObserver(processingObserver)

        }
    }

}