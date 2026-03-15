package com.kemalcodes.composetutorial

// Tutorial #14: Dependency Injection with Hilt — Application class
// This file demonstrates the @HiltAndroidApp annotation.
// Every Hilt app needs an Application class annotated with @HiltAndroidApp.
// This triggers Hilt's code generation and creates the dependency container
// that lives as long as the app is running.

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// @HiltAndroidApp tells Hilt to generate the dependency injection code
// and set up the app-level dependency container.
// This is the entry point for Hilt — without it, nothing else works.
@HiltAndroidApp
class App : Application()
