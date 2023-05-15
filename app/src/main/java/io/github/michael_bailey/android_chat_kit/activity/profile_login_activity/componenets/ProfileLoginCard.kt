package io.github.michael_bailey.android_chat_kit.activity.profile_login_activity.componenets

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import io.github.michael_bailey.android_chat_kit.database.dao.EntProfileDao
import io.github.michael_bailey.android_chat_kit.theme.smallUUID
import java.util.UUID

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProfileLoginCard(
	index: Int,
	max: Int,
	profile: EntProfileDao.EntProfileOverview,
	onSubmit: (UUID, String) -> Unit
) {

	var passwordState by remember { mutableStateOf("") }
	var isDroppedDown by remember { mutableStateOf(false) }

	val cornerEnd = 16.dp
	val cornerNormal = 8.dp

	val corner = when {
		max == 1 -> RoundedCornerShape(cornerEnd, cornerEnd, cornerEnd, cornerEnd)
		index == 0 -> RoundedCornerShape(cornerEnd, cornerEnd, cornerNormal, cornerNormal)
		index == max-1 -> RoundedCornerShape(cornerNormal,cornerNormal, cornerEnd, cornerEnd)
		else -> RoundedCornerShape(cornerNormal)
	}

	Card(
		shape = corner,
		colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
	) {
		AnimatedContent(targetState = isDroppedDown) {
			Column(
				Modifier
					.fillMaxSize()
					.padding(18.dp),
				Arrangement.spacedBy(8.dp),
				Alignment.CenterHorizontally
			) {
				Row(
					Modifier
						.fillMaxWidth()
						.wrapContentHeight()
						.padding(horizontal = 24.dp, vertical = 0.dp),
					Arrangement.SpaceBetween,
					Alignment.CenterVertically
				) {
					Column {
						Text("${profile.username}")
						Text(
							"${profile.uuid.toString().slice(0..12)}...",
							fontSize = smallUUID,
							fontWeight = FontWeight(400)
						)
					}
					Button(
						modifier = Modifier
							.width(42.dp)
							.height(42.dp)
							.padding(0.dp),
						contentPadding = PaddingValues(0.dp),
						onClick = { isDroppedDown = !isDroppedDown }
					) {
						Icon(
							modifier = Modifier.rotate(if (isDroppedDown) 180f else 0f),
							imageVector = Icons.Default.ArrowDropDown,
							contentDescription = ""
						)
					}
				}

				if (isDroppedDown) OutlinedTextField(
					value = passwordState,
					onValueChange = {passwordState = it},
					label = { Text("Password...") },
					visualTransformation = PasswordVisualTransformation(),
					keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
					keyboardActions = KeyboardActions(onGo = {onSubmit(profile.uuid, passwordState)})
				)
			}
		}
	}
}
