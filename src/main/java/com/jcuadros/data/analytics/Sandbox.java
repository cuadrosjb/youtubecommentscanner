package com.jcuadros.data.analytics;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.youtube.YouTube;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.stream.Collectors;

public class Sandbox {

    public static void main(String[] args) {

        try {
            List<Document> documents = new ArrayList<>();

            MongoClientURI connectionString = new MongoClientURI("mongodb://root:root@localhost:27017");
            MongoClient mongoClient = new MongoClient(connectionString);
            MongoDatabase database = mongoClient.getDatabase("youtube_comments");

            MongoCollection<Document> databaseCollection = database.getCollection("comments");

            FindIterable<Document> documentsIterator = databaseCollection.find();
            MongoCursor<Document> iterator = documentsIterator.iterator();

            while (iterator.hasNext()) {
                Document document = iterator.next();
                documents.add(document);
            }
            Set<Comment> scammerComment = new HashSet<>();
            for (Document document : documents) {
                String textOriginal = (String) ((Document) document.get("snippet")).get("textOriginal");
                Comment comment = new Comment(document, false, textOriginal, null);
                scammerComment.add(comment);
            }


            Set<Comment> complexComments = scammerComment.stream()
                    .map(comment -> {
                        String msg = comment.getMessage();
                        msg = msg.replaceAll("[^0-9]", "");
                        if (msg.length() != 11){ comment.setComplex(true); }
                        return new Comment(comment.getDocument(), comment.getComplex(), comment.getMessage(), msg);
                    }).filter(comment -> comment.getComplex().equals(true)).collect(Collectors.toSet());

            Set<Comment> uniquePhoneNumbers = complexComments.stream().filter(cc -> {
                String parsed = cc.getMessage().replaceAll("[^a-zA-Z+0-9 ]", "");

                Set<Object> phoneNumbers = Collections.list(new StringTokenizer(parsed, " ")).stream()
                        .filter(token -> {
                            String onlyNumeric = (String) token;
                            if (onlyNumeric.length() == 12) {
                                onlyNumeric = onlyNumeric.replaceAll("[^0-9]", "");
                                if (onlyNumeric.length() == 11 && onlyNumeric.matches("\\d+")) {
                                    return true;
                                }
                            }
                            return false;
                        })
                        .collect(Collectors.toSet());
                if (phoneNumbers.size() == 1) {
                    return true;
                }
                return false;

            }).collect(Collectors.toSet());

            uniquePhoneNumbers.forEach(cc -> System.out.println(cc.getMessage()));

        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

}
