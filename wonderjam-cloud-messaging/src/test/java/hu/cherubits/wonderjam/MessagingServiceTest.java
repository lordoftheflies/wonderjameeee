/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam;

import com.google.api.client.util.DateTime;
import com.google.gson.JsonObject;
import hu.cherubits.wonderjam.services.NotificationService;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author lordoftheflies
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {
    Application.class
})
@PropertySource("classpath:application.properties")
//@WebAppConfiguration
public class MessagingServiceTest {

    @Autowired
    NotificationService instance;

    public MessagingServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of send method, of class NotificationService.
     */
    @Test
    public void testSend() throws IOException {
        System.out.println("send");
        String text = "message";

        Map<String, String> props = new HashMap<>();
        props.put("message", "kajsdkasjdkjasd");
//        props.put("text", "Hahah");
//        props.put("sender", "me");

        instance.send("Title " + new Date().toString(), "Message", SUBSCRIPTION_ID);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
    private static final String SUBSCRIPTION_ID = "e5rm9nNwoqY:APA91bF4q33X3MaTakZZsoJL_EBxizURWOPg8AV7tMtpBpmjU-7vcfsWukMm12WfNGApd-ocA504JMNZK5tPlKi7Zzo_Q3Z7imfwiHsz0xGY9eftFCkU4iLo6XR_uI0f-PKvTWuEj4It";

//    @Test
//    public CompletableFuture<String> testNotification() {
//        // https://firebase.google.com/docs/cloud-messaging/http-server-ref
//        String host = "fcm.googleapis.com";
//        String requestURI = "/fcm/send";
//        String CLIENT_TOKEN = "ASK_YOUR_MOBILE_CLIENT_DEV"; // https://developers.google.com/instance-id/
//
//        CompletableFuture<String> fut = new CompletableFuture<>();
//        JsonObject body = new JsonObject();
//        // JsonArray registration_ids = new JsonArray();
//        // body.put("registration_ids", registration_ids);
//        body.put("to", CLIENT_TOKEN);
//        body.put("priority", "high");
//        // body.put("dry_run", true);
//
//        JsonObject notification = new JsonObject();
//        notification.put("body", "body string here");
//        notification.put("title", "title string here");
//        // notification.put("icon", "myicon");
//
//        JsonObject data = new JsonObject();
//        data.put("key1", "value1");
//        data.put("key2", "value2");
//
//        body.put("notification", notification);
//        body.put("data", data);
//
//        HttpClientRequest hcr = httpsClient.post(443, host, requestURI).handler(response -> {
//            response.bodyHandler(buffer -> {
//                logger.debug("FcmTest1 rcvd: {}, {}", response.statusCode(), buffer.toString());
//                if (response.statusCode() == 200) {
//                    fut.complete(buffer.toString());
//                } else {
//                    fut.completeExceptionally(new RuntimeException(buffer.toString()));
//                }
//            });
//        });
//        hcr.putHeader("Authorization", "key=" + Utils.FIREBASE_SERVER_KEY)
//                .putHeader("content-type", "application/json").end(body.encode());
//        return fut;
//
//    }
//    @Test
//    public void testNotification() throws IOException {
//        HttpClient client = HttpClientBuilder.create().build();
//        HttpPost post = new HttpPost("https://fcm.googleapis.com/fcm/send");
//        post.setHeader("Content-type", "application/json");
//        post.setHeader("Authorization", "key=AIzaSyDBGFKR7xzsG8z5VHDVgOSMXsb6hjfFbrQ");
//
//        JSONObject message = new JSONObject();
//        message.put("to", SUBSCRIPTION_ID);
//        message.put("priority", "high");
//
//        JSONObject notification = new JSONObject();
//        notification.put("title", "Java");
//        notification.put("body", "Notificação do Java");
//
//        message.put("notification", notification);
//
//        post.setEntity(new StringEntity(message.toString(), "UTF-8"));
//        HttpResponse response = client.execute(post);
//        System.out.println(response);
//        System.out.println(message);
//    }
}
