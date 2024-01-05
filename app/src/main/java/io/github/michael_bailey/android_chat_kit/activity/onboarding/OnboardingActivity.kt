package io.github.michael_bailey.android_chat_kit.activity.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import io.github.michael_bailey.android_chat_kit.activity.home_activity.HomeActivity
import io.github.michael_bailey.android_chat_kit.theme.ChatKitAndroidTheme

/**
 * # OnboardingActivity
 *
 * This activity will onboard the user to the app.
 * This includes setup of:
 * - UUID
 * - username
 */
@AndroidEntryPoint
class OnboardingActivity : ComponentActivity() {
	
	val vm: OnboardingActivityViewModel by viewModels()
	
	/**
	 * creates activity for onboarding.
	 * Or redirects to home if already onboarded.
	 */
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		// if there are user details already
		// go to home
		if (vm.hasProfile) {
			goToHomePage()
			return
		}
		
		setContent {
			ChatKitAndroidTheme {
				// A surface container using the 'background' color from the theme
				Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
					Main(vm)
				}
			}
		}
	}
	
	fun goToHomePage() {
		val homeActivity = Intent(baseContext, HomeActivity::class.java)
		startActivity(homeActivity)
		
		finish()
	}
}

