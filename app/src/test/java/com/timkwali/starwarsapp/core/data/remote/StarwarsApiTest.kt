package com.timkwali.starwarsapp.core.data.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.timkwali.starwarsapp.core.utils.startPage
import com.timkwali.starwarsapp.core.utils.testCharacterDetailsResponse
import com.timkwali.starwarsapp.core.utils.testSearchQuery
import com.timkwali.starwarsapp.core.utils.testSearchResponse
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StarwarsApiTest {

    private lateinit var server: MockWebServer//Fake server from square lib
    private lateinit var api: StarwarsApi

    @Before
    fun setUp() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(StarwarsApi::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `test searchStarwarsApi returns success`() = runTest {
        val gson: Gson = GsonBuilder().create()
        val json = gson.toJson(testSearchResponse)
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)

        val data = api.searchStarwarsApi(testSearchQuery, startPage)
        server.takeRequest()

        assertEquals(data.body(), testSearchResponse)
    }

    @Test
    fun `test getCharacterDetail return success`() = runTest{
        val gson: Gson = GsonBuilder().create()
        val json = gson.toJson(testCharacterDetailsResponse)
        val res = MockResponse()
        res.setResponseCode(200)
        res.setBody(json)
        server.enqueue(res)

        val data = api.getCharacterDetail("1")
        server.takeRequest()

        assertEquals(data.body(), testCharacterDetailsResponse)
    }

    @Test
    fun `test searchStarwarsApi returns error`() = runTest {
        val gson: Gson = GsonBuilder().create()
        val json = gson.toJson(testCharacterDetailsResponse)
        val res = MockResponse()
        res.setResponseCode(404)
        res.setBody(json)
        server.enqueue(res)

        val data = api.searchStarwarsApi(testSearchQuery, startPage)
        server.takeRequest()

        assert(!data.isSuccessful)
    }
}