package io.github.michael_bailey.android_chat_kit.components.navigation

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SwipeActionView(
	actions: @Composable() (RowScope.() -> Unit),
	content: @Composable () -> Unit,
) {
	val swipeableState = rememberSwipeableState(0)
	val (actionsSize, setActionSize) = remember {
		mutableStateOf(IntSize(2, 2))
	}
	val size = actionsSize.width.toFloat()
//	context.log("sizePx:$size")
	val anchors = mapOf(
		0f to 0,
		size to 1,
	)

	Box(
		Modifier
			.swipeable(
				state = swipeableState,
				anchors = anchors,
				thresholds = { _, _ -> FractionalThreshold(0.3f) },
				orientation = Orientation.Horizontal
			)
			.fillMaxWidth()
			.wrapContentHeight()
			.height(IntrinsicSize.Max),
	) {
		Row(
			Modifier
				.wrapContentHeight()
				.wrapContentWidth()
				.onSizeChanged(setActionSize),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween,
			content = actions
		)

		Surface(
			Modifier
				.offset {
					IntOffset(swipeableState.offset.value.roundToInt(), 0)
				}
				.fillMaxWidth()
				.fillMaxHeight()
		) {
			content()
		}
	}

}

@Composable
fun RowScope.action(
	icon: ImageVector,
	contentColour: Color =  MaterialTheme.colorScheme.primary,
	containerColour: Color = MaterialTheme.colorScheme.primaryContainer,
	onClick: () -> Unit,
) {
	Button(
		modifier = Modifier.fillMaxHeight(),
		onClick = onClick,
		shape = RectangleShape,
		colors = ButtonDefaults.buttonColors(
			containerColour,
			contentColour,
			MaterialTheme.colorScheme.background,
			MaterialTheme.colorScheme.onBackground,
		),
	) {
		Icon(Icons.Outlined.Delete, "")
	}
}
@Composable
fun RowScope.actionDelete(
	onClick: () -> Unit,
) {
	action(
		icon = Icons.Outlined.Delete,
		containerColour = MaterialTheme.colorScheme.error,
		contentColour = MaterialTheme.colorScheme.onErrorContainer,
		onClick = onClick
	)
}