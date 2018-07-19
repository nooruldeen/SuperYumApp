package io.bigsoft.udacity.superyum.widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import io.bigsoft.udacity.superyum.R;
import io.bigsoft.udacity.superyum.model.IngredientsModel;
import io.bigsoft.udacity.superyum.repository.RecipesRepository;
import io.bigsoft.udacity.superyum.utils.InjectorUtils;


/**
 * This is the service that provides the factory to be bound to the collection service.
 */
public class ListWidgetService extends RemoteViewsService {

    private static final String TAG = ListWidgetService.class.getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        Log.d(TAG, "1st Step: onGetViewFactory");

        return new ListRemoteViewsFactory(getApplicationContext());
    }
}


/**
 * This is the factory that will provide data to the collection widget.
 */

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = ListRemoteViewsFactory.class.getSimpleName();
    private Context mContext;
    private List<IngredientsModel> ingredientsModelList;
    private RecipesRepository mRepository;

    public ListRemoteViewsFactory(Context context) {
        mContext = context;
        mRepository = InjectorUtils.provideRepository(context);
        ingredientsModelList = new ArrayList<>();
    }

    @Override
    public void onCreate() {

        Log.d(TAG, "2nd Step: onCreate");

    }

    @Override
    public void onDataSetChanged() {
        if (ingredientsModelList != null) {
            ingredientsModelList.clear();
        }
        ingredientsModelList = mRepository.getFavorite();
        Log.d(TAG, "onDataSetChanged: ingredient list has: " + ingredientsModelList.size());
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {

        if (ingredientsModelList == null) return 0;
        Log.d(TAG, "getCount: ingredient list has: " + ingredientsModelList.size());
        return ingredientsModelList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        Log.d(TAG, "4th Step: getViewAt");
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_view_item);
        if(ingredientsModelList != null){
            remoteViews.setTextViewText(R.id.widget_list_view_text_ingredient, ingredientsModelList.get(i).getIngredient());
            remoteViews.setTextViewText(R.id.widget_list_view_text_measure, ingredientsModelList.get(i).getMeasure());
            remoteViews.setTextViewText(R.id.widget_list_view_text_quantity, ingredientsModelList.get(i).getQuantity() + "");
            Log.d(TAG, "getViewAt: ingredient position: " + i + ingredientsModelList.get(i).getIngredient());
        }
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        Log.d(TAG, "3rd Step: getViewTypeCount");
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
