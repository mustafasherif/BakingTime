package com.example.mustafa.bakingtime.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.example.mustafa.bakingtime.DataModels.Ingredients;
import com.example.mustafa.bakingtime.DataModels.Recipe;
import com.example.mustafa.bakingtime.R;
import com.example.mustafa.bakingtime.RecipeDetail;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    public static ArrayList<Ingredients> mIngredients;

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetIds[], ArrayList<Ingredients> ingredients)
    {

        mIngredients=ingredients;
        for (int appWidgetId : appWidgetIds)
        {
            Intent intent = new Intent(context, listViewsService.class);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
            views.setRemoteAdapter(R.id.widgetListView, intent);
            ComponentName component = new ComponentName(context, RecipeWidgetProvider.class);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widgetListView);
            appWidgetManager.updateAppWidget(component, views);
        }

    }


}

