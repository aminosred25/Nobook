package com.ycngmn.nobook.utils

import android.content.Context
import android.content.Intent
import com.multiplatform.webview.request.RequestInterceptor
import com.multiplatform.webview.request.WebRequest
import com.multiplatform.webview.request.WebRequestInterceptResult
import com.multiplatform.webview.web.WebViewNavigator

class ExternalRequestInterceptor(
    private val context: Context,
    private val onOpenMessenger: () -> Boolean,
    private val onNavigateFB: () -> Boolean
) : RequestInterceptor {

    override fun onInterceptUrlRequest(
        request: WebRequest,
        navigator: WebViewNavigator
    ): WebRequestInterceptResult {

        val url = fbRedirectSanitizer(request.url)
        val metaLinksRegex = Regex("https?://([^/]+\\.)?(facebook\\.com|instagram\\.com|threads\\.com)(|/.*|\\?.*)")
        val messengerRegex = Regex("(https?://([^/]+\\.)?(facebook\\.com/messages|messenger\\.com)|.*://threads)(|/.*|\\?.*)")

        return if (messengerRegex.matches(url)) {
            if (onOpenMessenger()) {
                openInBrowser(url)
                WebRequestInterceptResult.Reject
            } else {
                WebRequestInterceptResult.Allow
            }
        } else if (metaLinksRegex.matches(url)) {
            if (onNavigateFB()) {
                openInBrowser(url)
                WebRequestInterceptResult.Reject
            } else {
                WebRequestInterceptResult.Allow
            }
        } else {
            openInBrowser(url)
            WebRequestInterceptResult.Reject
        }
    }

    private fun openInBrowser(url: String) {
        try {
            val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
            context.startActivity(intent)
        } catch (_: Exception) {}
    }
}