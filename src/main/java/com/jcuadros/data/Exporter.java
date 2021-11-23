package com.jcuadros.data;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mycompany.app.MyFirestore;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;
import org.bson.Document;

public class Exporter {


    public static void main(String[] args) {
        try {

            MyFirestore myFirestore = new MyFirestore();
            Firestore firestore = myFirestore.getMyFirestoreInstance();
            CollectionReference commentsCollection = firestore.collection("comments");

            MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
            MongoClient mongoClient = new MongoClient(connectionString);
            MongoDatabase database = mongoClient.getDatabase("youtube_comments");

            MongoCollection<Document> databaseCollection = database.getCollection("comments");

            FindIterable<Document> documentsIterator = databaseCollection.find();
            MongoCursor<Document> iterator = documentsIterator.iterator();

            while (iterator.hasNext()) {
                Document document = iterator.next();
                DocumentReference documentReference = commentsCollection.document(document.get("_id").toString());
                ApiFuture<WriteResult> resultApiFuture = documentReference.set(document);
                resultApiFuture.get();
            }
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

}
