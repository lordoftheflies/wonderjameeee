/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.services;

import hu.cherubits.wonderjam.fcm.FcmSender;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author lordoftheflies
 */
@Service
public class NotificationService {

    private static final Logger LOG = Logger.getLogger(NotificationService.class.getName());

    private final String fcmServerKey;

    private final String fcmSenderId;

    private static FirebaseOptions options = null;

    public NotificationService(String cloudMessagingServer, String fileName, String fcmServerKey, String fcmSenderId) {
        this.fcmSenderId = fcmSenderId;
        this.fcmServerKey = fcmServerKey;
//    }
//
//    @PostConstruct
//    public void startUp() {

//        try {
        if (options == null) {
            options = new FirebaseOptions.Builder()
                    .setServiceAccount(getClass().getClassLoader().getResourceAsStream(fileName))
                    .setDatabaseUrl(cloudMessagingServer)
                    .build();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

            FirebaseApp.initializeApp(options);

        }
    }

    private void append(Message.Builder builder, String key, String value) {
        builder.collapseKey(key).addData(key, value);
    }

    public void send(Map<String, String> props, String... to) {

        try {
            LOG.log(Level.INFO, "SEND MESSAGE ...");
            LOG.log(Level.INFO, "FCM Server Key: {0}", fcmServerKey);
            LOG.log(Level.INFO, "FCM Sender ID: {0}", fcmSenderId);
            LOG.log(Level.INFO, "Properties: {0}", props.toString());

            Sender sender = new FcmSender(fcmServerKey);

            Message.Builder builder = new Message.Builder()
                    .timeToLive(3)
                    .delayWhileIdle(true);

            props.forEach((String key, String value) -> append(builder, key, value));

            Message message = builder.build();

            // Use the same token(or registration id) that was earlier
            // used to send the message to the client directly from
            // Firebase Console's Notification tab.
            MulticastResult result = sender.send(message, Arrays.asList(to), 1);
            LOG.log(Level.INFO, "Result: {0}", result.toString());
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Could not send message.", ex);
        }
    }
}
