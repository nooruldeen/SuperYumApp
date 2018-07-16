package io.bigsoft.udacity.superyum.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
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
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String recipeName;
        for (int appWidgetId : appWidgetIds) {
            recipeName = pref.getString("recipe_title", "");
            updateAppWidget(context, appWidgetManager, recipeName, appWidgetId);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, String recipeName, int appWidgetId) {


        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_ingredient_widget);
        views.setTextViewText(R.id.appwidget_text, recipeName);

        Intent remoteAdapter = new Intent(context, ListWidgetService.class);
        views.setRemoteAdapter(R.id.widget_list_view, remoteAdapter);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.ll_recipe_widget, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateIngredientWidgets(Context context, AppWidgetManager appWidgetManager, String recipeName, int[] appWidgetIds) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("recipe_title", recipeName);
        for (int appWidgetId : appWidgetIds) {
            editor.commit();
            updateAppWidget(context, appWidgetManager, recipeName, appWidgetId);
        }
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
        super.onReceive(context, intent);

        mViews = new RemoteViews(context.getPackageName(), R.layout.recipe_ingredient_widget);
        Intent launchActivity = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0, launchActivity, 0);
        mViews.setOnClickPendingIntent(R.id.widget_list_view, pendingIntent);

        Log.d(TAG, "onReceive(): Set on click pending intent action");

    }
}

