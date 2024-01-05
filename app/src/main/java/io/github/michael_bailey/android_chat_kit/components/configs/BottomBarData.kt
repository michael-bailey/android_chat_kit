package io.github.michael_bailey.android_chat_kit.components.configs

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

data class BottomBarData(
	val bottomBar: @Composable RowScope.() -> Unit
)
