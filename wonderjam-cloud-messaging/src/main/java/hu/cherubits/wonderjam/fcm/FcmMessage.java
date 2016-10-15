/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.fcm;

import com.google.android.gcm.server.Notification;
import java.io.Serializable;

/**
 *
 * @author lordoftheflies
 */
public class FcmMessage implements Serializable {

    public enum Priority {
        normal, high
    }

    public FcmMessage() {
    }

    public FcmMessage(String to, Priority priority, Notification notification) {
        this.to = to;
        this.priority = priority;
        this.notification = notification;
    }

    public FcmMessage(String to, Priority priority) {
        this.to = to;
        this.priority = priority;
    }
    
    /**
     * Firebase clien token.
     */
    private String to;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    private Priority priority;

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    
    private Notification notification;

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
    
    
}
