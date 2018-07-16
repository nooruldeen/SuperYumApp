package io.bigsoft.udacity.superyum.repository.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import io.bigsoft.udacity.superyum.model.IngredientsModel;

public class IngredientConvertor {

    @TypeConverter
    public static String encode(List<IngredientsModel> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<IngredientsModel> decode(String value) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<IngredientsModel>>() {
        }.getType();
        return gson.fromJson(value, listType);
    }
}