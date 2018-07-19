package io.bigsoft.udacity.superyum.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import io.bigsoft.udacity.superyum.R;
import io.bigsoft.udacity.superyum.activities.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeIngredientWidgetProvider extends AppWidgetProvider {


    private static final String TAG = RecipeIngredientWidgetProvider.class.getSimpleName();

    private RemoteViews mViews;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String recipeName;
        recipeName = pref.getString("recipe_title", "");
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_ingredient_widget);
        views.setTextViewText(R.id.appwidget_text, recipeName);

        Intent remoteAdapter = new Intent(context, ListWidgetService.class);
        if (true) {views.setRemoteAdapter(R.id.widget_list_view, remoteAdapter);}
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.ll_recipe_widget, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created

        Log.d(TAG, "onEnabled(): This is called once when the widget is created.");
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        ComponentName bakingAppWidget = new ComponentName(context.getPackageName(), RecipeIngredientWidgetProvider.class.getName());
        int[] appWidgetIds = AppWidgetManager.getInstance(context).getAppWidgetIds(bakingAppWidget);
        onUpdate(context, AppWidgetManager.getInstance(context), appWidgetIds);

        super.onReceive(context, intent);

        Log.d(TAG, "onReceive(): Set on click pending intent action");

    }
}

