package com.iCareapi.sms;

import jakarta.persistence.*;

@Entity
@Table(name = "recipient")
public class Recipient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String recipient;
    private String message;
    private String messageType;

    public Recipient(){

    }

//    public Recipient(Long id, String to, String message, String messageType) {
//        this.id = id;
//        this.to = to;
//        this.message = message;
//        this.messageType = messageType;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String to) {
        this.recipient = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return "Recipient{" +
                ", recipient='" + recipient + '\'' +
                ", message='" + message + '\'' +
                ", messageType=" + messageType +
                '}';
    }
}
