package com.jcuadros.data.analytics;

import org.bson.Document;

public class Comment implements Comparable<Comment>{
    private Document document;
    private Boolean complex;
    private String message;
    private String phoneNumber;

    public Comment(Document document, Boolean complex, String message, String phoneNumber) {
        this.document = document;
        this.complex = complex;
        this.message = message;
        this.phoneNumber = phoneNumber;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Boolean getComplex() {
        return complex;
    }

    public void setComplex(Boolean complex) {
        this.complex = complex;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int compareTo(Comment o) {
        return this.getMessage().compareTo(o.getMessage());
    }
}
