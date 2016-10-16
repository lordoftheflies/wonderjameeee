/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.fcm;

import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Notification;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;

/**
 * Firebase Cloud Messaging sender.
 *
 * @author lordoftheflies
 */
public class FcmSender extends Sender {

    protected static final int BACKOFF_INITIAL_DELAY = 10000;

    private static final Logger LOG = Logger.getLogger(FcmSender.class.getName());

    String fcmUrl = "https://fcm.googleapis.com/fcm/send";

    private String fcmKey;

    public FcmSender(String key) {
        super(key);
        this.fcmKey = key;
    }

    @Override
    protected HttpURLConnection getConnection(String url) throws IOException {
        return (HttpURLConnection) new URL(fcmUrl).openConnection();
    }

    public void send(Notification notification, List<String> regIds) throws IOException {
        regIds.forEach(id -> {
            try {
                LOG.log(Level.INFO, "Sending FCM notification to {0} ...", id);
                HttpClient client = HttpClientBuilder.create().build();
                HttpPost post = new HttpPost(fcmUrl);
                post.setHeader("Content-type", "application/json");
                post.setHeader("Authorization", "key=" + fcmKey);
                JSONObject message = new JSONObject();
                message.put("to", id);
                message.put("priority", "high");

                JSONObject notificationJson = new JSONObject();
                notificationJson.put("badge", notification.getBadge());
                notificationJson.put("body", notification.getBody());
                notificationJson.put("clickAction", notification.getClickAction());
                notificationJson.put("color", notification.getColor());
                notificationJson.put("icon", notification.getIcon());
                notificationJson.put("sound", notification.getSound());
                notificationJson.put("tag", notification.getTag());
                notificationJson.put("title", notification.getTitle());

                message.put("notification", notificationJson);
                post.setEntity(new StringEntity(message.toString(), "UTF-8"));
                HttpResponse response = client.execute(post);
                LOG.log(Level.INFO, response.toString());
            } catch (IOException ex) {
                LOG.log(Level.WARNING, "Notification sending failed.", ex);
            }
        });
    }

}
