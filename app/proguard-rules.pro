# ProGuard rules for the Task Manager app
#
# These rules tell R8 (the code shrinker) what to keep when building
# a release APK. Without them, R8 might remove or rename code that
# is accessed via reflection.

# ---- Compose ----
# Keep Compose runtime classes needed for reflection
-keep class androidx.compose.** { *; }

# Don't warn about Compose internal APIs
-dontwarn androidx.compose.**

# ---- General Android ----
# Keep line numbers for better crash reports in production
-keepattributes SourceFile,LineNumberTable

# Hide the original source file name in stack traces
-renamesourcefileattribute SourceFile

# Keep the Application class
-keep class com.kemalcodes.composetutorial.** { *; }

# ---- Kotlin ----
# Keep Kotlin metadata for reflection
-keepattributes *Annotation*
-keepattributes KotlinMetadata

# Don't warn about missing Kotlin reflect classes if not used
-dontwarn kotlin.reflect.**
