package com.dicoding.newsapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dicoding.newsapp.data.local.room.NewsDao
import com.dicoding.newsapp.data.remote.retrofit.ApiService
import com.dicoding.newsapp.utils.DataDummy
import com.dicoding.newsapp.utils.MainDispatcherRule
import com.dicoding.newsapp.utils.getOrAwaitValue
import com.dicoding.newsapp.utils.observeForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NewsRepositoryTest{

    @get: Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var apiService: ApiService
    private lateinit var newsDao : NewsDao
    private lateinit var newsRepository: NewsRepository

    @Before
    fun setUp() {
        apiService = FakeApiService()
        newsDao = FakeNewsDao()
        newsRepository = NewsRepository(apiService, newsDao)
    }

    @Test
    fun `when getHeadlineNews Should Not Null`() = runTest {
        val expectedNews = DataDummy.generateDummyNewsResponse()
        val actualNews = newsRepository.getHeadlineNews()
        actualNews.observeForTesting {
            assertNotNull(actualNews)
            assertEquals(
                expectedNews.articles.size,
                (actualNews.value as Result.Success).data.size
            )
        }
    }
    @Test
    fun `when saveNews Should Exist in getBookmarkedNews`() = runTest {
        val sampleNews = DataDummy.generateDummyNewsEntity()[0]
        newsDao.saveNews(sampleNews)
        val actualNews = newsRepository.getBookmarkedNews().getOrAwaitValue()
        assertTrue(actualNews.contains(sampleNews))
        assertTrue(newsRepository.isNewsBookmarked(sampleNews.title).getOrAwaitValue())
    }
    @Test
    fun `when deleteNews Should Not Exist in getBookmarkedNews`() = runTest {
        val sampleNews = DataDummy.generateDummyNewsEntity()[0]
        newsRepository.saveNews(sampleNews)
        newsRepository.deleteNews(sampleNews.title)
        val actualNews = newsRepository.getBookmarkedNews().getOrAwaitValue()
        assertFalse(actualNews.contains(sampleNews))
        assertFalse(newsRepository.isNewsBookmarked(sampleNews.title).getOrAwaitValue())
    }

}