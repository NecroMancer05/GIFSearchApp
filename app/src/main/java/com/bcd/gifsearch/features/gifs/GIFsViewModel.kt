package com.bcd.gifsearch.features.gifs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.bcd.gifsearch.api.GIFRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class GIFsViewModel @Inject constructor(
    private val repository: GIFRepository
) : ViewModel() {

    val searchQuery = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    private val gifSearchFlow = searchQuery.flatMapLatest {
        repository.getSearchResults(it).cachedIn(viewModelScope)
    }

    var searchResults = gifSearchFlow.asLiveData()
}