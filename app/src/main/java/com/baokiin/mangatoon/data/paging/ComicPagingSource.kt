package com.baokiin.mangatoon.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.baokiin.mangatoon.data.api.ApiService
import com.baokiin.mangatoon.data.model.Manga
import java.lang.Exception

class ComicPagingSource(
    private val service: ApiService,
    private val query:String
): PagingSource<Int, Manga>(){


    override fun getRefreshKey(state: PagingState<Int, Manga>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(20) ?: anchorPage?.nextKey?.minus(20)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Manga> {
        try {
            val nextPageNumber = params.key ?: 1
            val response = service.getComic(query,nextPageNumber)
            if (response.manga_list.size == 0) {
                return LoadResult.Error(NoResultException())
            }
            val (start, end) = PagingUtil.pageToItem(nextPageNumber)

            return LoadResult.Page(
                data = response.manga_list
                ,
                prevKey = null,
                nextKey = nextPageNumber+1
            )
        } catch (e: Exception) {

            return LoadResult.Error(e)
        }
    }

}
