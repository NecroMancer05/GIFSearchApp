package com.bcd.gifsearch.api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.bcd.gifsearch.data.GIFPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GIFRepository @Inject constructor(private val gifApi: GIFApi) {

    fun getSearchResults(query: String) =
        Pager(
            PagingConfig(
                pageSize = 10,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GIFPagingSource(gifApi, query) }
        ).flow
}