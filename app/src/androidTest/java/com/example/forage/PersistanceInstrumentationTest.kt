package com.example.forage

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PersistenceInstrumentationTests {
    @Before
    fun setup() {
        launchActivity<MainActivity>()
        onView(withId(R.id.add_forageable_fab)).perform(click())

        onView(withId(R.id.name_input)).perform(replaceText("Name"))
        onView(withId(R.id.location_address_input)).perform(replaceText("Address"))
        onView(withId(R.id.notes_input)).perform(replaceText("Notes"))
        onView(withId(R.id.save_btn)).perform(click())
    }

    @Test
    fun new_forageable_is_displayed_in_list() {
        onView(withText("Name")).check(matches(isDisplayed()))
        onView(withText("Address")).check(matches(isDisplayed()))
    }

    @Test
    fun new_forageable_is_displayed_in_detail() {
        onView(withText("Name")).perform(click())
        onView(withText("Name")).check(matches(isDisplayed()))
        onView(withText("Address")).check(matches(isDisplayed()))
        onView(withText("Currently out of season")).check(matches(isDisplayed()))
        onView(withText("Notes")).check(matches(isDisplayed()))
    }

    @Test
    fun edit_new_forageable() {
        onView(withText("Name")).perform(click())
        onView(withId(R.id.edit_forageable_fab)).perform(click())
        onView(withId(R.id.name_input)).perform(replaceText("New Name"))
        onView(withId(R.id.location_address_input)).perform(replaceText("New Address"))
        onView(withId(R.id.notes_input)).perform(replaceText("New Notes"))
        onView(withId(R.id.in_season_checkbox)).perform(click())
        onView(withId(R.id.save_btn)).perform(click())
        Thread.sleep(1000)
        onView(withText("New Name")).perform(click())
        Thread.sleep(2000)
        onView(withText("New Name")).check(matches(isDisplayed()))
        onView(withText("New Address")).check(matches(isDisplayed()))
        onView(withText("Currently in season")).check(matches(isDisplayed()))
        onView(withText("New Notes")).check(matches(isDisplayed()))
    }

    @Test
    fun delete_new_forageable() {
        onView(withText("Name")).perform(click())
        onView(withId(R.id.edit_forageable_fab)).perform(click())
        onView(withId(R.id.delete_btn)).perform(click())
        Thread.sleep(1000)
        onView(withText("Name")).check(doesNotExist())
    }
}