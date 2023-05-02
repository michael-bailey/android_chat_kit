package io.github.michael_bailey.android_chat_kit.activity.profile_login_activity.Componenets

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import io.github.michael_bailey.android_chat_kit.database.dao.EntProfileDao
import io.github.michael_bailey.android_chat_kit.database.entity.EntProfile

@Composable
fun ProfileLoginList(modifier: Modifier? = null, profiles: List<EntProfileDao.EntProfileOverview>) {

	val ctx = LocalContext.current
	val profiles = profiles.mapIndexed {i, e ->
		i to e
	}
	val corner = 24.dp
	
	Column(modifier ?: Modifier, Arrangement.Center,Alignment.CenterHorizontally) {
		Card(
			Modifier.fillMaxSize(0.9f),
			shape = RoundedCornerShape(corner),
			colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary)
		) {
			LazyColumn(
				Modifier.fillMaxSize(),
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.spacedBy(2.dp),
				contentPadding = PaddingValues(vertical = 16.dp,horizontal = 16.dp)
			) {
				items(items = profiles) { item ->
					ProfileLoginCard(
						index = item.first,
						max = profiles.count(),
						profile = item.second,
						onSubmit = {
							Toast(ctx).apply {
								setText("Password $it")
								show()
							}
						}
					)
				}
			}
		}
	}
}