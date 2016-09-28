/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.fcm;

import com.google.android.gcm.server.Sender;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Firebase Cloud Messaging sender.
 * @author lordoftheflies
 */
public class FcmSender extends Sender {
    
    String fcmUrl = "https://fcm.googleapis.com/fcm/send"; 
    
    public FcmSender(String key) {
        super(key);
    }
    
    @Override
    protected HttpURLConnection getConnection(String url) throws IOException {
        return (HttpURLConnection) new URL(fcmUrl).openConnection();
    }
    
}
