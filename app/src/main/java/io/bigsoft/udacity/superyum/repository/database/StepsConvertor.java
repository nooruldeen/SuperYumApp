package io.bigsoft.udacity.superyum.repository.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import io.bigsoft.udacity.superyum.model.StepsModel;

public class StepsConvertor {

    @TypeConverter
    public static String encode(List<StepsModel> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<StepsModel> decode(String value) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<StepsModel>>() {
        }.getType();
        return gson.fromJson(value, listType);
    }
}
