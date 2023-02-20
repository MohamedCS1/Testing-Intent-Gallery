package com.example.testgalleryintent

import android.app.Activity.RESULT_OK
import android.app.Instrumentation
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.provider.MediaStore
import androidx.test.InstrumentationRegistry
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
internal class MainActivityTest{

    @get:Rule
    val intentsTestRule = IntentsTestRule(MainActivity::class.java)

    @Test
    fun testValidateGalleryIntent()
    {

        val expectedIntent = allOf(hasAction(Intent.ACTION_PICK) , hasData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI))

        val activityResult = createGalleryPickActivityResultStub(ApplicationProvider.getApplicationContext())
        intending(expectedIntent).respondWith(activityResult)

        onView(withId(R.id.buttonOpenGallery)).perform(click())
        intended(expectedIntent)
    }

   private fun createGalleryPickActivityResultStub(context: Context): Instrumentation.ActivityResult {

        val builder: StringBuilder = StringBuilder().append(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .append("://")
            .append(context.resources.getResourcePackageName(R.drawable.ic_launcher_background))
            .append("/")
            .append(context.resources.getResourceTypeName(R.drawable.ic_launcher_background))
            .append("/")
            .append(context.resources.getResourceEntryName(R.drawable.ic_launcher_background))

        val imageUri = Uri.parse(builder.toString())
        val resultIntent = Intent()
        resultIntent.data = imageUri

        return Instrumentation.ActivityResult(RESULT_OK, resultIntent)
    }
}