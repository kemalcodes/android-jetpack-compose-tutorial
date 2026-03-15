// App.kt — Application class required by Hilt for dependency injection setup.
package com.kemalcodes.composetutorial

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application()
