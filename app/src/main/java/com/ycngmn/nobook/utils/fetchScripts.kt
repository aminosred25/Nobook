package com.ycngmn.nobook.utils

import android.content.Context

data class Script(
    val condition: Boolean,
    val scriptRes: Int
)

fun fetchScripts(scripts: List<Script>, context: Context): String {
    return buildString {
        scripts.filter { it.condition }.forEach { script ->
            val content = context.resources.openRawResource(script.scriptRes).bufferedReader().use { it.readText() }
            append(content)
        }
    }
}