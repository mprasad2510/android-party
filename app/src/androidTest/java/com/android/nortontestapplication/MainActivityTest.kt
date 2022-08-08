package com.android.nortontestapplication

import androidx.test.core.app.launchActivity
import com.android.nortontestapplication.view.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MainActivityTest {

    @get:Rule()
    var hiltRule =  HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun mainActivityTest() {
        val scenario = launchActivity<MainActivity>()
    }
}