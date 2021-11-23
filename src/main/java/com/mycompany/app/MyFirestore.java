package com.mycompany.app;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.IOException;

public class MyFirestore {

    public MyFirestore(){

    }


    public Firestore getMyFirestoreInstance() throws IOException {

        FirestoreOptions firestoreOptions =
                FirestoreOptions.getDefaultInstance().toBuilder()
                        .setProjectId(System.getenv("GOOGLE_CLOUD_PLATFORM_PROJECT_ID"))
                        .setCredentials(GoogleCredentials.getApplicationDefault())
                        .build();
        Firestore db = firestoreOptions.getService();

        return db;
    }


}
