/*
 * Copyright 2014 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.apps.muzei.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.apps.muzei.sources.SourceManager
import kotlinx.coroutines.experimental.launch

/**
 * AppWidgetProvider for Muzei. The actual updating is done asynchronously in
 * [updateAppWidget].
 */
class MuzeiAppWidgetProvider : AppWidgetProvider() {

    companion object {
        internal const val ACTION_NEXT_ARTWORK = "com.google.android.apps.muzei.action.WIDGET_NEXT_ARTWORK"
    }

    override fun onReceive(context: Context, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent?.action == ACTION_NEXT_ARTWORK) {
            SourceManager.nextArtwork(context)
        }
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        updateWidgets(context)
    }

    override fun onAppWidgetOptionsChanged(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int,
            newOptions: Bundle
    ) {
        updateWidgets(context)
    }

    private fun updateWidgets(context: Context) {
        val result = goAsync()
        launch {
            updateAppWidget(context)
            result.finish()
        }
    }
}
