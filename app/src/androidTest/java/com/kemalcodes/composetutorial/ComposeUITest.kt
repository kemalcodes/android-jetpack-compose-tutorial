// Tutorial #18: Compose UI Tests
// Tests for CounterScreen and LoginScreen composables.
// Uses createComposeRule, onNodeWithText, performClick, and assertion APIs.
// kemalcodes — https://kemalcodes.com

package com.kemalcodes.composetutorial

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.kemalcodes.composetutorial.ui.theme.AndroidjetpackcomposetutorialTheme
import org.junit.Rule
import org.junit.Test

class ComposeUITest {

    // createComposeRule provides a test environment for Compose UI.
    // It handles setting up the composition and provides finder/assertion APIs.
    @get:Rule
    val composeTestRule = createComposeRule()

    // Test: Counter starts at 0 and increments when button is clicked.
    // Verifies the text updates from "Count: 0" to "Count: 1".
    @Test
    fun counterIncrements() {
        composeTestRule.setContent {
            AndroidjetpackcomposetutorialTheme {
                CounterScreen()
            }
        }

        // Verify initial count is displayed
        composeTestRule.onNodeWithText("Count: 0").assertIsDisplayed()

        // Click the increment button
        composeTestRule.onNodeWithText("Increment").performClick()

        // Verify count updated to 1
        composeTestRule.onNodeWithText("Count: 1").assertIsDisplayed()
    }

    // Test: Login button is disabled when both fields are empty.
    // The button should not be clickable until the form is filled.
    @Test
    fun loginButtonDisabledWhenFieldsEmpty() {
        composeTestRule.setContent {
            AndroidjetpackcomposetutorialTheme {
                LoginScreen()
            }
        }

        // Login button should be present but disabled
        composeTestRule.onNodeWithText("Login").assertIsDisplayed()
        composeTestRule.onNodeWithText("Login").assertIsNotEnabled()

        // Helper text should be visible
        composeTestRule.onNodeWithText("Please fill in all fields").assertIsDisplayed()
    }

    // Test: Login button becomes enabled when both email and password are filled.
    // Uses performTextInput to type into the text fields.
    @Test
    fun loginButtonEnabledWhenFieldsFilled() {
        composeTestRule.setContent {
            AndroidjetpackcomposetutorialTheme {
                LoginScreen()
            }
        }

        // Initially disabled
        composeTestRule.onNodeWithText("Login").assertIsNotEnabled()

        // Fill in email field
        composeTestRule.onNodeWithText("Email").performClick()
        composeTestRule.onNodeWithText("Email").performTextInput("sam@example.com")

        // Still disabled — password is empty
        composeTestRule.onNodeWithText("Login").assertIsNotEnabled()

        // Fill in password field
        composeTestRule.onNodeWithText("Password").performClick()
        composeTestRule.onNodeWithText("Password").performTextInput("secret123")

        // Now the button should be enabled
        composeTestRule.onNodeWithText("Login").assertIsEnabled()
    }
}
