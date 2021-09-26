package com.daniel.farage.githubrepos

import androidx.test.platform.app.InstrumentationRegistry
import com.daniel.farage.githubrepos.rule.OkHttpIdlingResourceRule
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.test.KoinTest
import java.io.BufferedReader
import java.io.Reader

abstract class BaseUITest : KoinTest {

    @get:Rule
    var okhttpIdleRule = OkHttpIdlingResourceRule()

    private lateinit var server: MockWebServer

    private fun initMockServer() {
        server = MockWebServer()
        server.start(8080)
    }

    private fun stopMockserver() {
        server.shutdown()
    }

    @Before
    fun setUp() {
        initMockServer()
    }

    @After
    open fun tearDown() {
        stopMockserver()
    }

    fun mockResponseWithJson(jsonPath: String, statusCode: Int) = server.enqueue(
        MockResponse()
            .setResponseCode(statusCode)
            .setBody(getJson(jsonPath))
    )

    private fun getJson(path: String): String {
        var content = ""
        val testContext = InstrumentationRegistry.getInstrumentation().context
        val inputStream = testContext.assets.open(path)
        val reader = BufferedReader(inputStream.reader() as Reader?)
        reader.use { reader ->
            content = reader.readText()
        }
        return content
    }
}