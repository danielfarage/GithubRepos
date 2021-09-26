package com.daniel.farage.githubrepos

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.daniel.farage.githubrepos.base.GitApp

class GithubTestRunner: AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, GitAppTest::class.java.name, context)
    }

}