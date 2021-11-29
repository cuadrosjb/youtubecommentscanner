package com.jcuadros.data.mongo.analytics;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.*;

public class Verify {


    private List<String> channelIds = Arrays.asList(new String[]{"UC_xMKlHYdRNXLX9XzuOLjsg", "UCnMn36GT_H0X-w5_ckLtlgQ", "UC4a-9NnHFv4ZhI-HWc4xNaA", "UCWKODoiwSuLNSXIKuKnuQTQ", "UCV6KDgJskWaEckne5aPA0aQ"});


    public static void main(String[] args)  {
        try {

            Set<String> videoIds = new HashSet<>();

            MongoClientURI connectionString = new MongoClientURI("mongodb://root:root@localhost:27017");
            MongoClient mongoClient = new MongoClient(connectionString);
            MongoDatabase database = mongoClient.getDatabase("youtube_comments");

            MongoCollection<Document> databaseCollection = database.getCollection("comments");

            FindIterable<Document> documentsIterator = databaseCollection.find();
            MongoCursor<Document> iterator = documentsIterator.iterator();

            while (iterator.hasNext()) {
                Document document = iterator.next();
                String videoId = ((Document) document.get("snippet")).getString("videoId");
                videoIds.add(videoId);
            }

            System.out.println(videoIds.size());

        } catch (Exception exception) {
            System.out.println(exception);
        }
    }
}
