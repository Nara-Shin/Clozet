/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sgen.club.sgenapplication.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.sgen.club.sgenapplication.CallListAdapter;
import org.sgen.club.sgenapplication.CallListItem;
import org.sgen.club.sgenapplication.LogProcess;
import org.sgen.club.sgenapplication.MainActivity;
import org.sgen.club.sgenapplication.R;

import java.net.URLEncoder;
import java.util.ArrayList;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {

        String message = data.getString("message");
       // String roomNumber="room 1";//더미값
        String roomNumber="";
        String prdName="";
        String imageUrl="";
        String size="";
        String count="";
        String color="";
        String code="";
        String price="";
        String stockURL="";
        String request_code="";

        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);
        StringBuffer sb=new StringBuffer(message);


        char [] msg = message.toCharArray();
        String newMsg="";
        for(int i=0; i< msg.length;i++) {
            try {
                if (msg[i] != '\\') {
                    newMsg = newMsg + msg[i];
                }
            } catch (Exception e) {

            }


        }
        LogProcess.normalLog(getClass(),"Message: " + newMsg);
        //one
        final ArrayList<CallListItem> arrayList=new ArrayList<>();
        try{
            String query="";
            query = URLEncoder.encode(newMsg,"utf-8");
            JSONObject product = new JSONObject(newMsg);

            if(product.has("room")){
                roomNumber=product.getString("room");
            }
            if(product.has("count")){
                count=product.getString("count");
            }
            if(product.has("request_code")){
                request_code=product.getString("request_code");
            }
            if(product.has("products")){
                LogProcess.normalLog(getClass(),"Message : "+product.getString("products"));
                JSONArray jsonArray=new JSONArray(product.getString("products"));

                for(int i=0;i<Integer.parseInt(count);i++){
                    JSONObject arrayValueObject=new JSONObject(jsonArray.getString(i));
                    if(arrayValueObject.has("code")){
                        code=arrayValueObject.getString("code");
                    }
                    if(arrayValueObject.has("prdname")){
                        prdName=arrayValueObject.getString("prdname");

                    }
                    if(arrayValueObject.has("img")){
                        imageUrl=arrayValueObject.getString("img");
                    }
                    if(arrayValueObject.has("size")){
                        size=arrayValueObject.getString("size");
                    }
                    if(arrayValueObject.has("color")){
                        color=arrayValueObject.getString("color");
                    }
                    if(arrayValueObject.has("price")){
                        price=arrayValueObject.getString("price");
                    }
                    if(arrayValueObject.has("stockURL")){
                        stockURL=arrayValueObject.getString("stockURL");

                    }
                     CallListItem item=new CallListItem();
                    item.setRoom_number(roomNumber);
                    item.setStockURL(stockURL);
                    item.setPrice(price);
                    item.setName(prdName);
                    item.setCode(code);
                    item.setAmount(count);
                    item.setImageURL_cloth(imageUrl);
                    item.setColor(color);
                    item.setSize(size);
                    item.setRequest_code(request_code);
                    arrayList.add(item);
                }


            }


        }catch (Exception e){
           LogProcess.errorLog(e);
        }


//        Intent intent = this.getPackageManager().getLaunchIntentForPackage("org.sgen.club.sgenapplication");
        Intent intent=new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("roomNumber",roomNumber);//key, value

        intent.putExtra("prdName",prdName);//key, value
        intent.putExtra("imageUrl",imageUrl);//key, value
        intent.putExtra("size",size);//key, value
        intent.putExtra("count", count);//key, value
        intent.putExtra("color", color);//key, value
        intent.putExtra("code", code);
        intent.putExtra("price", price);
        intent.putExtra("stockURL", stockURL);
        intent.putExtra("request_code", request_code);


        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                CallListAdapter adapter = CallListAdapter.getInstance();

                LogProcess.normalLog(getClass(),"color : "+arrayList.get(0).getColor());

                adapter.addAllItems(arrayList);
            }
        });


        //intent로 데이터 전달

        if(intent!=null) {
            startActivity(intent);
        }

        if (from.startsWith("/topics/")) {
            // message received from some topic.

        } else {
            // normal downstream message.
        }

        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
        sendNotification(message);
        // [END_EXCLUDE]
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(String message) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);



        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_ic_notification)
                .setContentTitle("GCM Message")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
