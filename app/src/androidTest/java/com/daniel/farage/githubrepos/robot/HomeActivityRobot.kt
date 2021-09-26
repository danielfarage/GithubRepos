package com.daniel.farage.githubrepos.robot

import com.daniel.farage.githubrepos.R

class HomeActivityRobot: BaseRobot() {

    fun checkTitle(text: String) = matchText(R.id.textView_titleRepo, text)
    fun checkTotalLoaded(text: String) = matchText(R.id.textView_subtitleFounds, text)

    fun checkErrorTitle(text: String) = matchText(R.id.textView_messageError, text)
    fun clickOnTryAgain() = clickButton(R.id.button_tryAgain)
    fun checkButton(text: String) = matchText(R.id.button_tryAgain, text)

}

fun home(func: HomeActivityRobot.() -> Unit) = HomeActivityRobot().apply {
    func()
}