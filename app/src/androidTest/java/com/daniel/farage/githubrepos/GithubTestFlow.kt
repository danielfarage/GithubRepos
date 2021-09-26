package com.daniel.farage.githubrepos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.daniel.farage.githubrepos.base.HomeActivity
import com.daniel.farage.githubrepos.robot.home
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GithubTestFlow : BaseUITest() {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var scenario: ActivityScenario<HomeActivity>

    @After
    override fun tearDown() {
        super.tearDown()
        scenario.close()
    }

    @Test
    fun initFlow() {
        mockResponseWithJson("github_repos_success.json", 200)
        mockResponseWithJson("github_repos_success.json", 200)
        scenario = launchActivity()
        home {
            checkTitle("Repositórios Github")
            checkTotalLoaded("Total encontrados: 30")
        }
    }

    @Test
    fun apiError() {
        mockResponseWithJson("github_repos_success.json", 503)
        scenario = launchActivity()
        home {
            checkErrorTitle("Ooopss! Algo deu errado")
            checkButton("TENTAR NOVAMENTE")
        }
    }


}