package io.github.michael_bailey.android_chat_kit.components.configs

import androidx.compose.ui.graphics.vector.ImageVector

data class FABData(
	val icon: ImageVector,
	val label: String? = null,
	val onClick: () -> Unit,
)
