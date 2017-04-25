/*
 * Copyright (c) 2016 Metin Kale
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
 *
 */

package com.metinkale.prayerapp.vakit;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.metinkale.prayerapp.settings.Prefs;
import com.metinkale.prayerapp.vakit.widget.WidgetLegacy;
import com.metinkale.prayerapp.vakit.widget.WidgetV24;

public class WidgetProviderClock extends AppWidgetProvider {

    public static void updateAppWidget(@NonNull Context context, @NonNull AppWidgetManager appWidgetManager, int widgetId) {
        if (!Prefs.showLegacyWidget())
            WidgetV24.update4x2Clock(context, appWidgetManager, widgetId);
        else
            WidgetLegacy.update4x2Clock(context, appWidgetManager, widgetId);


    }

    @Override
    public void onEnabled(@NonNull Context context) {
        ComponentName thisWidget = new ComponentName(context, WidgetProviderClock.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        onUpdate(context, manager, manager.getAppWidgetIds(thisWidget));
    }

    @Override
    public void onUpdate(@NonNull Context context, @NonNull AppWidgetManager appWidgetManager, @NonNull int[] appWidgetIds) {
        for (int widgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, widgetId);
        }

    }

    @Override
    public void onDisabled(Context context) {
    }

    @Override
    public void onAppWidgetOptionsChanged(@NonNull Context context, @NonNull AppWidgetManager appWidgetManager, int appWidgetId, @NonNull Bundle newOptions) {
        updateAppWidget(context, appWidgetManager, appWidgetId);
    }
}
