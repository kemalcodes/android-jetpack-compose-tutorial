// App.kt — Application class required by Hilt for dependency injection setup.
// The @HiltAndroidApp annotation triggers Hilt's code generation,
// creating the base classes needed for dependency injection throughout the app.
package com.kemalcodes.composetutorial

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application()
