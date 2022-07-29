package com.bcd.gifsearch.features.gifs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bcd.gifsearch.api.GIFApi
import com.bcd.gifsearch.utilities.NetworkResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class GIFsViewModel @Inject constructor(
    private val api: GIFApi
) : ViewModel() {

    val searchQuery = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    private val gifSearchFlow = searchQuery.flatMapLatest {

        flow {

            emit(NetworkResource.Loading())

            if (it.isBlank()) {
                emit(NetworkResource.Error())
            } else {
                try {
                    emit(NetworkResource.Success(api.getSearchResults(it)))
                } catch (e: Throwable) {
                    emit(NetworkResource.Error(e))
                }
            }
        }
    }

    var searchResults = gifSearchFlow.asLiveData()
}