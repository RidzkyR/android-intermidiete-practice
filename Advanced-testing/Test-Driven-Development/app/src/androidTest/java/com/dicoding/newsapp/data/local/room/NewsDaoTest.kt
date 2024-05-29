package com.dicoding.newsapp.data.local.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.dicoding.newsapp.utils.DataDummy
import com.dicoding.newsapp.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
@SmallTest
class NewsDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: NewsDatabase
    private lateinit var dao: NewsDao
    private val sampleNews = DataDummy.generateDummyNewsEntity()[0]

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NewsDatabase::class.java
        ).build()
        dao = database.newsDao()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun saveNews_Success() = runTest {
        dao.saveNews(sampleNews)
        val actualNews = dao.getBookmarkedNews().getOrAwaitValue()
        assertEquals(sampleNews.title, actualNews[0].title)
        assertTrue(dao.isNewsBookmarked(sampleNews.title).getOrAwaitValue())
    }

    @Test
    fun deleteNews_Success() = runTest {
        dao.saveNews(sampleNews)
        dao.deleteNews(sampleNews.title)
        val actualNews = dao.getBookmarkedNews().getOrAwaitValue()
        assertTrue(actualNews.isEmpty())
        assertFalse(dao.isNewsBookmarked(sampleNews.title).getOrAwaitValue())
    }
}