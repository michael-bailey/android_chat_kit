package io.github.michael_bailey.android_chat_kit.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import com.google.android.material.elevation.SurfaceColors
import io.github.michael_bailey.android_chat_kit.App

private val DarkColorScheme = darkColorScheme(
	primary = Purple80,
	secondary = PurpleGrey80,
	tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
	primary = Purple40,
	secondary = PurpleGrey40,
	tertiary = Pink40

	/* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun ChatKitAndroidTheme(
	darkTheme: Boolean = isSystemInDarkTheme(),
	// Dynamic color is available on Android 12+
	dynamicColor: Boolean = true,
	colourNavBar: Boolean = false,
	content: @Composable () -> Unit
) {
	val app = LocalContext.current.applicationContext
	val view = LocalView.current
	val context = LocalContext.current

	val colorScheme = when {
		dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
			val context = LocalContext.current
			if (darkTheme) dynamicDarkColorScheme(
				context
			) else dynamicLightColorScheme(
				context
			)
		}

		darkTheme -> DarkColorScheme
		else -> LightColorScheme
	}

	if (app is App) {
		val debugStatusBarEnabled by app.appDebugPreferencesManager
			.isDebugStatusBarColourEnabled.observeAsState(false)

		val debugNavBarEnabled by app.appDebugPreferencesManager
			.isDebugNavBarColourEnabled.observeAsState(false)

		LaunchedEffect(debugNavBarEnabled, debugStatusBarEnabled) {
			(view.context as Activity).window.statusBarColor =
				if (debugStatusBarEnabled) {
					Color.Red.toArgb()
				} else {
					colorScheme.background.toArgb()
				}

			(view.context as Activity).window.navigationBarColor =
				if (debugNavBarEnabled) {
					Color.Red.toArgb()
				} else if (colourNavBar) {
					SurfaceColors.SURFACE_2.getColor(view.context)
				} else {
					colorScheme.surface.toArgb()
				}
		}
	}

	if (!view.isInEditMode) {
		SideEffect {
			ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars =
				!darkTheme
		}
	}

	MaterialTheme(
		colorScheme = colorScheme,
		typography = Typography,
		content = content
	)
}