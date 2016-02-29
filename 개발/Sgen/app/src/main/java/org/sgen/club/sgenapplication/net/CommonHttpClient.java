package org.sgen.club.sgenapplication.net;

/**
 * Created by HOJU on 2016-01-10.
 */
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by krcho on 2015-11-24.
 */
public class CommonHttpClient {

    private static AsyncHttpClient client = new AsyncHttpClient();


    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        Log.d("CommonClient", params.toString());
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return NetDefine.BASE_URL + relativeUrl;
    }
}
