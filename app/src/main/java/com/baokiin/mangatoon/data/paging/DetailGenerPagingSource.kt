package com.baokiin.mangatoon.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.baokiin.mangatoon.data.api.ApiService
import com.baokiin.mangatoon.data.model.Manga
import java.lang.Exception

class DetailGenerPagingSource(
    private val service: ApiService,
    val query:String
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
            val response = service.getDetailGenres(query,nextPageNumber)
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

class NoResultException: Exception() {
    override val message: String?
        get() = "No result"
}

object PagingUtil {
    const val UNKNOWN_PAGE: Int = -1
    const val PREPEND: Int = -2
    const val INIT_LOAD_SIZE: Int = 20
    const val PAGE_SIZE: Int = 10
    const val PREFECT_DISTANCE: Int = 5
    const val OUT_DATE_TIME_STAMP: Long = 120000000

    /**
     * if START = 15, LOOP = 10
     * if page == 1: start: end 0: 15 -1
     * if page == 2: start: end 15 + 10 * (page - 1 - page 1(results are available)): end 15 + 10 * (page - 1)
     * */
    fun pageToItem(page: Int): Pair<Long, Long> {
        return when (if (page < 1) 1 else page) {
            1 -> {
                0L to (INIT_LOAD_SIZE - 1).toLong()
            }
            else -> {
                (INIT_LOAD_SIZE + PAGE_SIZE * (page - 2)).toLong() to (INIT_LOAD_SIZE + PAGE_SIZE * (page - 1) - 1).toLong()
            }
        }
    }
}