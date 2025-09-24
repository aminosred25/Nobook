package com.ycngmn.nobook.ui.screens

import androidx.compose.runtime.Composable
import com.ycngmn.nobook.ui.NobookViewModel

const val DESKTOP_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36"

@Composable
fun MessengerWebView(onNavigateFB: () -> Boolean, viewModel: NobookViewModel) {
    BaseWebView(
        url = "https://www.messenger.com",
        userAgent = DESKTOP_USER_AGENT,
        onOpenMessenger = { false },
        onNavigateFB = onNavigateFB,
        viewModel = viewModel
    )
}