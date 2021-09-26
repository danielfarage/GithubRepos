package com.daniel.farage.githubrepos.robot

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText

open class BaseRobot {

    fun matchText(resId: Int, text: String): ViewInteraction =
        view(resId).check(ViewAssertions.matches(withText(text)))

    fun clickButton(resId: Int) = view(resId).perform(ViewActions.click())

    fun view(resId: Int) = onView(withId(resId))

    fun sleep() = apply {
        Thread.sleep(500)
    }

}