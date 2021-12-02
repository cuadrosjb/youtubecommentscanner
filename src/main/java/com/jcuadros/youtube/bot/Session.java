package com.jcuadros.youtube.bot;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class Session {

    private static Session instance = null;
    private static final String APPLICATION_NAME = "spam-comment-detector";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    public YouTube service;

    private Session() throws GeneralSecurityException, IOException {
        service = getService();
    }

    // Static method
    // Static method to create instance of Singleton class
    public static Session getInstance() throws GeneralSecurityException, IOException {
        if (instance == null)
            instance = new Session();

        return instance;
    }


    /**
     * Build and return an authorized API client service.
     *
     * @return an authorized API client service
     * @throws GeneralSecurityException, IOException
     */
    public static YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }


}
