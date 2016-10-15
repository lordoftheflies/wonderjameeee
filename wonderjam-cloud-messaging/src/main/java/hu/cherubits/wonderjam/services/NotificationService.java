/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.services;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Notification;
import com.google.android.gcm.server.Sender;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import hu.cherubits.wonderjam.fcm.FcmMessage;
import hu.cherubits.wonderjam.fcm.FcmSender;
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

    public void send(String title, String body, String... to) throws IOException {

        try {
            LOG.log(Level.INFO, "SEND MESSAGE ...");
            LOG.log(Level.INFO, "FCM Server Key: {0}", fcmServerKey);
            LOG.log(Level.INFO, "FCM Sender ID: {0}", fcmSenderId);
            
            Notification notification = new Notification.Builder("myicon")
                    .title(title)
                    .body(body)
                    .badge(5)
//                    .color("red")
                    .clickAction("onClickNotification")
                    .sound("default")
                    .tag("notification")
                    .build();

            LOG.log(Level.INFO, "Properties: {0}", notification.toString());

            
            FcmSender sender = new FcmSender(fcmServerKey);

            sender.send(notification, Arrays.asList(to));

//            LOG.log(Level.INFO, "Result: {0}", result.toString());
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Could not send message.", ex);
        }
    }
}
