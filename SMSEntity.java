package com.iCareapi.sms;

import jakarta.persistence.*;

@Entity
@Table(name = "sms_entity")
public class SMSEntity{
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )

    private Long id;

    @ManyToOne
    private Recipient patient;

    @ManyToOne
    private Recipient provider;

    @ManyToOne
    private Recipient user;

    @Transient
    private Criteria criteria;
    private String status;

    private String message;

    public SMSEntity() {
    }

    public SMSEntity(Long id,
                     Recipient patient,
                     Recipient provider, Recipient user, Criteria criteria,
                     String status, String message) {
        this.id = id;
        this.patient = patient;
        this.provider = provider;
        this.user = user;
        this.criteria = criteria;
        this.status = status;
        this.message = message;
    }

    public SMSEntity(Recipient patient, Recipient provider,
                     Recipient user, Criteria criteria, String status, String message) {
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
        return "SMSEntity{" +
                ", patient='" + patient + '\'' +
                ", provider='" + provider + '\'' +
                ", user=" + user + '\'' +
                ", criteria=" + criteria + '\'' +
                ", status=" + status + '\'' +
                ", message=" + message + '\'' +
                '}';

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}



