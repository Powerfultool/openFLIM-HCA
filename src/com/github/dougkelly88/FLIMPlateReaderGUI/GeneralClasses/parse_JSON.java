/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Fogim
 */
public class parse_JSON {
    static JsonParser parser = new JsonParser();
    Gson gson = new Gson();
    
    public void parse_JSON(){
    }
    
    public HashMap<String, Object> createHashMapFromJsonString(String json) {
        JsonObject object = (JsonObject) parser.parse(json);
        Set<Map.Entry<String, JsonElement>> set = object.entrySet();
        Iterator<Map.Entry<String, JsonElement>> iterator = set.iterator();
        HashMap<String, Object> map = new HashMap<String, Object>();

        while (iterator.hasNext()) {
            Map.Entry<String, JsonElement> entry = iterator.next();
            String key = entry.getKey();
            JsonElement value = entry.getValue();
                if (null != value) {
                    if (!value.isJsonPrimitive()) {
                        if (value.isJsonObject()) {
                            map.put(key, createHashMapFromJsonString(value.toString()));
                        } else if (value.isJsonArray() && value.toString().contains(":")) {
                            List<HashMap<String, Object>> list = new ArrayList<>();
                            JsonArray array = value.getAsJsonArray();
                            if (null != array) {
                                for (JsonElement element : array) {
                                    list.add(createHashMapFromJsonString(element.toString()));
                                }
                                map.put(key, list);
                            }
                        } else if (value.isJsonArray() && !value.toString().contains(":")) {
                            map.put(key, value.getAsJsonArray());
                        }
                    } else {
                        map.put(key, value.getAsString());
                    }
                }
            }
        return map;
    }
    
    public String createJSONFromHashMap(HashMap<String, Object> hashmap_in) {
        return gson.toJson(hashmap_in);
    }
    
}