package io.github.michael_bailey.android_chat_kit.components.scopes

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

/**
 * A generic scope for handling pages,
 * for new ways of UI navigation a new sub-class should be made.
 * along with an accompanying pageData subtype
 */
open class PageScaffoldScope<S>(
) where
	S: PageScaffoldScope.PageData
{
	
	sealed class PageData(
		val route: String,
		val title: String? = null,
		val composable: @Composable (NavController, PaddingValues?) -> Unit,
	)
	
	class BasicPageData(
		route: String,
		composable: @Composable (NavController, PaddingValues?) -> Unit,
	): PageData(
		route = route,
		composable = composable
	)
	
	protected val pages: MutableList<PageData> = mutableListOf()
	
	fun getPage(): List<PageData> = pages
	
	
	fun page(
		route: String,
		composable: @Composable (NavController, PaddingValues?) -> Unit,
	) {
		pages.add(BasicPageData(route = route, composable = composable))
	}
}