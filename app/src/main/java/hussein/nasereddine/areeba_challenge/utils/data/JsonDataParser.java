package hussein.nasereddine.areeba_challenge.utils.data;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonDataParser {
    protected static String tryToGet(JSONObject jsonObject, String key){
        try{
            String value = jsonObject.getString(key);
            return value.equalsIgnoreCase("null") ? "" : value;
        }catch (JSONException je){
            return "";
        }
    }

    protected static double tryToGetDouble(JSONObject jsonObject, String key){
        try{
            return jsonObject.getDouble(key);
        }catch (JSONException je){
            return 0;
        }
    }

    protected static int tryToGetInt(JSONObject jsonObject, String key){
        try{
            return jsonObject.getInt(key);
        }catch (JSONException je){
            return 0;
        }
    }

    protected static boolean tryToGetBool(JSONObject jsonObject, String key){
        try{
            return jsonObject.getBoolean(key);
        }catch (JSONException je){
            return false;
        }
    }

    protected static JSONObject tryToGetObject(JSONObject jsonObject, String objectKey){
        try{
            return jsonObject.getJSONObject(objectKey);
        }catch (JSONException je){
            return new JSONObject();
        }
    }

    protected static JSONArray tryToGetArray(JSONObject jsonObject, String arrayKey){
        try{
            return new JSONArray(jsonObject.get(arrayKey).toString());
        }catch (JSONException je){
            return new JSONArray();
        }
    }

    @Nullable
    protected static JSONObject tryToGetArrayObject(JSONArray jsonArray, int index){
        try{
            return jsonArray.getJSONObject(index);
        }catch (JSONException je){
            return new JSONObject();
        }
    }

    protected static String tryToGetArrayString(JSONArray jsonArray, int index){
        try{
            return jsonArray.getString(index);
        }catch (JSONException je){
            return "";
        }
    }

    protected static double tryToGetArrayDouble(JSONArray jsonArray, int index){
        try{
            return jsonArray.getDouble(index);
        }catch (JSONException je){
            return 0;
        }
    }

    protected static JSONArray tryToGetArrayArray(JSONArray jsonArray, int index){
        try{
            return jsonArray.getJSONArray(index);
        }catch (JSONException je){
            return new JSONArray();
        }
    }

    protected static JSONObject stringToJSON(String stringJSON){
        try{
            return new JSONObject(stringJSON);
        }catch (JSONException je){
            return new JSONObject();
        }
    }
}
