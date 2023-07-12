package io.github.michael_bailey.android_chat_kit.utils.page

import androidx.annotation.StringRes
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel

data class FabConfig<VM: ViewModel>(
	@StringRes val label: Int,
	val icon: ImageVector,
	val exec: (VM) -> Unit
) {
	
	@Composable
	fun compose(vm: VM) {
		ExtendedFloatingActionButton(onClick = {exec(vm)}) {
			Icon()
			Label()
		}
	}
	
	@Composable
	fun Label() {
		Text(stringResource(id = label))
	}
	
	@Composable
	fun Icon() {
		androidx.compose.material.Icon(
			imageVector = icon,
			contentDescription = "A Home Button For The App"
		)
	}
}
