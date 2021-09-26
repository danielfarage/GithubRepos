package com.daniel.farage.githubrepos.rule

import androidx.test.espresso.IdlingRegistry
import com.daniel.farage.githubrepos.GitAppTest
import com.jakewharton.espresso.OkHttp3IdlingResource
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class OkHttpIdlingResourceRule: TestRule {

    private val resource = OkHttp3IdlingResource.create(
        "okhttp",
        GitAppTest.okhttpProvider
    )

    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {
                IdlingRegistry.getInstance().register(resource)
                base?.evaluate()
                IdlingRegistry.getInstance().unregister(resource)
            }
        }
    }
}