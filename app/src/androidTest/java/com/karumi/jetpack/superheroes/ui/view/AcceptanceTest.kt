package com.karumi.jetpack.superheroes.ui.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import com.karumi.jetpack.superheroes.asApp
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.kodein.di.Kodein
import org.mockito.MockitoAnnotations

@LargeTest
@RunWith(AndroidJUnit4::class)
abstract class AcceptanceTest<T : Activity>(clazz: Class<T>) : ScreenshotTest {

    @Rule
    @JvmField
    val testRule: IntentsTestRule<T> = IntentsTestRule(clazz, true, false)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        val app = InstrumentationRegistry.getInstrumentation().targetContext.asApp()
        app.override(testDependencies)
    }

    fun startActivity(args: Bundle = Bundle()): T {
        val intent = Intent()
        intent.putExtras(args)
        return testRule.launchActivity(intent)
    }

    abstract val testDependencies: Kodein.Module
}