package com.ycngmn.nobook.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.ycngmn.nobook.R
import com.ycngmn.nobook.ui.NobookViewModel
import com.ycngmn.nobook.utils.Script
import com.ycngmn.nobook.utils.fetchScripts
import com.ycngmn.nobook.utils.isAutoDesktop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun FacebookWebView(
    url: String,
    onRestart: () -> Unit,
    onOpenMessenger: () -> Boolean,
    viewModel: NobookViewModel
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val isDesktop = viewModel.desktopLayout.collectAsState()
    val isAutoDesktop = isAutoDesktop()

    LaunchedEffect(Unit) {
        if (isAutoDesktop && !isDesktop.value) {
            viewModel.setRevertDesktop(true)
            viewModel.setDesktopLayout(true)
        }
        else if (!isAutoDesktop && viewModel.isRevertDesktop.value) {
            viewModel.setRevertDesktop(false)
            viewModel.setDesktopLayout(false)
        }
    }

    BaseWebView(
        url = url,
        userAgent = if (isDesktop.value) DESKTOP_USER_AGENT else null,
        onOpenMessenger = onOpenMessenger,
        onNavigateFB = { false },
        onRestart = onRestart,
        viewModel = viewModel,
        onPostLoad = {
            val scripts = listOf(
                Script(true, R.raw.scripts), // always apply
                Script(viewModel.removeAds.value, R.raw.adblock),
                Script(viewModel.enableDownloadContent.value, R.raw.download_content),
                Script(viewModel.stickyNavbar.value, R.raw.sticky_navbar),
                Script(viewModel.pinchToZoom.value, R.raw.enable_pinch_to_zoom),
                Script(!viewModel.pinchToZoom.value, R.raw.disable_pinch_to_zoom),
                Script(viewModel.amoledBlack.value, R.raw.amoled_black),
                Script(viewModel.hideSuggested.value, R.raw.hide_suggested),
                Script(viewModel.hideReels.value, R.raw.hide_reels),
                Script(viewModel.hideStories.value, R.raw.hide_stories),
                Script(viewModel.hidePeopleYouMayKnow.value, R.raw.hide_pymk),
                Script(viewModel.hideGroups.value, R.raw.hide_groups)
            )

            scope.launch {
                withContext(Dispatchers.IO) {
                    viewModel.scripts.value = fetchScripts(scripts, context)
                }
            }
        }
    )
}

