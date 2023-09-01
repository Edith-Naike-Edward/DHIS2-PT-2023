package com.iCareapi.sms;

public class SMSRequestPayload {
    private Recipient patient;
    private Recipient provider;
    private Recipient user;
    private Criteria criteria;
    private String status;

    private String message;

    public SMSRequestPayload(){

    }

    public SMSRequestPayload(Recipient patient, Recipient provider, Recipient user, Criteria criteria, String status, String message) {
        this.patient = patient;
        this.provider = provider;
        this.user = user;
        this.criteria = criteria;
        this.status = status;
        this.message = message;
    }

    public Recipient getPatient() {
        return patient;
    }

    public void setPatient(Recipient patient) {
        this.patient = patient;
    }

    public Recipient getProvider() {
        return provider;
    }

    public void setProvider(Recipient provider) {
        this.provider = provider;
    }

    public Recipient getUser() {
        return user;
    }

    public void setUser(Recipient user) {
        this.user = user;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString(){
        return "SMSRequestPayload{" +
                ", patient='" + patient + '\'' +
                ", provider='" + provider + '\'' +
                ", user=" + user +  '\'' +
                ", criteria=" + criteria + '\'' +
                ", status=" + status + '\'' +
                ", message=" + message + '\'' +
                '}';

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
