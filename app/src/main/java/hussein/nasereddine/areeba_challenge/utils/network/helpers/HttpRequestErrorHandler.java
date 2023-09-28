package hussein.nasereddine.areeba_challenge.utils.network.helpers;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import hussein.nasereddine.areeba_challenge.utils.data.JsonDataParser;

public class HttpRequestErrorHandler extends JsonDataParser {

    /**
     * Handling HttpRequest onFailure method response
     *
     * @param httpRequest   Instance
     * @param e             IOException
     */
    public static void handleRequestFailure(@NonNull HttpRequest httpRequest, @NonNull IOException e){
        final Context context = httpRequest.getContext();
        final HttpRequest.HttpCallback callback = httpRequest.getCallback();

        try{
            if(callback != null){
                final String errMsg = "We couldn't connect to our servers! Please check your internet connection.";
                ((Activity)context).runOnUiThread(() -> callback.onFailure(errMsg));
            }
        }catch (ClassCastException ce){
            ce.printStackTrace();
        }
    }

    /**
     * Handles error response codes returned by the server
     *
     * If response code is 401 Unauthorized, then the decision will be that
     * the session has expired
     * Will show toast message and redirect user to login screen
     *
     * Otherwise, will return String message to listener
     */
    public static void handleErrorResponse(@NonNull HttpRequest httpRequest, @NonNull String message, int responseCode){
        final Context context = httpRequest.getContext();
        final HttpRequest.HttpCallback callback = httpRequest.getCallback();

        try{
            switch(responseCode){
                case 500:
                    message = "Something went wrong on our servers! We're working on fixing it.";
                    break;
                case 503:
                    message = "Service temporarily unavailable!";
                    break;
                case 504:
                    message = "Request timed out! Please check your internet connection.";
                    break;
            }

            String finalMessage = message;
            ((Activity)context).runOnUiThread(() -> callback.onFailure(finalMessage));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Error response body messages are being returned in different ways
     *
     * This method is responsible of parsing error messages from
     * JSON arrays and JSON objects to a readable String text
     */
    public static String parseErrorResponse(@NonNull String errResponse){
        StringBuilder stringBuilder = new StringBuilder();
        try{
            JSONObject jsonObject = new JSONObject(errResponse);

            // Check if response contains a message field
            final String message = tryToGet(jsonObject, "message");
            if(!message.equalsIgnoreCase("null") && !message.isEmpty()){
                return message;
            }

            return "Unexpected error occurred! Please try again..";
        }catch (JSONException je){
            stringBuilder.append(je.getMessage());
            return stringBuilder.toString();
        }
    }
}
