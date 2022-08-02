package com.bcd.gifsearch.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bcd.gifsearch.api.GIFApi
import retrofit2.HttpException
import java.io.IOException

class GIFPagingSource(
    private val gifApi: GIFApi,
    private val query: String
) : PagingSource<String, GIF>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, GIF> {

        val currentPage = params.key ?: ""

        return try {
            val response = gifApi.getSearchResults(query, currentPage, params.loadSize)

            LoadResult.Page(
                data = response.results,
                prevKey = currentPage,
                nextKey = response.next
            )

        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<String, GIF>): String? = null

    override val keyReuseSupported: Boolean get() = true
}