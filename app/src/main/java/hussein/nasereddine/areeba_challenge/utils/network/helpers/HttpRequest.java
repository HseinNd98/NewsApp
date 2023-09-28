package hussein.nasereddine.areeba_challenge.utils.network.helpers;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import hussein.nasereddine.areeba_challenge.constants.Endpoints;
import hussein.nasereddine.areeba_challenge.dialogs.DialogLoader;
import hussein.nasereddine.areeba_challenge.utils.data.JsonDataParser;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@SuppressWarnings("FieldCanBeLocal")
public class HttpRequest extends JsonDataParser {
    private final static String TAG = "HttpRequestLogs";

    // Context
    private final Context context;

    // HTTP Client
    private static OkHttpClient mClient;

    // Request data - Configs
    private final Request.Builder mRequestBuilder;
    private final Method mMethod;
    private final String mRequestURL;
    private final HttpCallback mCallback;

    /**
     * Default class constructor to build an API request
     * This class can be built using {@link Builder} class
     *
     * @param context                   Activity content required if running on UI Thread
     * @param method                    HTTP request method
     * @param requestURL                HTTP request URL
     * @param callback                  Request callback
     */
    private HttpRequest(@NonNull Context context,
                        @NonNull Method method,
                        @NonNull String requestURL,
                        @Nullable HttpCallback callback){
        this.context = context;
        this.mMethod = method;
        this.mRequestURL = requestURL;
        this.mCallback = callback;

        // Building OkHttpClient
        mClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        // Request Builder
        mRequestBuilder = new Request.Builder().url(requestURL);

        // Adding headers to request
        mRequestBuilder.addHeader("Content-Type", "application/json");

        // Enqueue Request
        enqueueRequest();
    }

    /**
     * Sends API request and handles response
     */
    private void enqueueRequest(){
        mClient.newCall(mRequestBuilder.build()).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                DialogLoader.hide();

                // String body
                if(mCallback != null){
                    boolean hasValidResponse = hasValidResponseCode(response.code());

                    try {
                        final String responseBody = Objects.requireNonNull(response.body()).string();
                        if(hasValidResponse){
                            JSONObject jsonObject = new JSONObject(responseBody);

                            // Return listener response
                            if(context instanceof android.app.Activity){
                                ((Activity)context).runOnUiThread(() -> mCallback.onSuccess(jsonObject));
                            }
                        }else{
                            // No valid response
                            HttpRequestErrorHandler.handleErrorResponse(HttpRequest.this,
                                    HttpRequestErrorHandler.parseErrorResponse(responseBody),
                                    response.code());
                        }
                    } catch (IOException | JSONException e) {
                        HttpRequestErrorHandler.handleErrorResponse(HttpRequest.this,
                                String.valueOf(e.getMessage()),
                                response.code());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                // Hide loader
                DialogLoader.hide();
                Log.d(TAG, "Error: "+e);
                HttpRequestErrorHandler.handleRequestFailure(HttpRequest.this, e);
            }
        });
    }

    private boolean hasValidResponseCode(int responseCode){
        return responseCode == 200 ||
                responseCode == 201 ||
                responseCode == 202 ||
                responseCode == 204;
    }

    public Context getContext(){
        return this.context;
    }

    public HttpCallback getCallback(){
        return this.mCallback;
    }

    /**
     * Builder class for creating an instance of {@link HttpRequest}
     */
    public static class Builder{
        private final Context context;
        private Method mMethod = Method.GET;
        private String mRequestURL = Endpoints.GET_ARTICLES;

        public Builder(@NonNull Context ctx){
            context = ctx;
        }

        public Builder usingMethod(@NonNull Method method){
            mMethod = method;
            return this;
        }

        public Builder withRequestURL(@NonNull String requestURL){
            mRequestURL = requestURL;
            return this;
        }

        public Builder showLoaderDialog(){
            ((Activity) context).runOnUiThread(() -> DialogLoader.show(context));
            return this;
        }

        public HttpRequest request(@Nullable HttpCallback callback){
            Log.d(TAG, "URL: "+mRequestURL);
            return new HttpRequest(context,
                    mMethod,
                    mRequestURL,
                    callback);
        }
    }

    public interface HttpCallback{
        void onSuccess(@NonNull JSONObject jsonResponse);
        void onFailure(@NonNull String message);
    }

    public enum Method{
        GET,
    }
}
