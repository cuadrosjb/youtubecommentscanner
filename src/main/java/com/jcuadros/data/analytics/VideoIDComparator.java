package com.jcuadros.data.analytics;

import org.bson.Document;

import java.util.Comparator;

public class VideoIDComparator implements Comparator<Document> {
    @Override
    public int compare(Document o1, Document o2) {
        String videoId1 = ((Document) o1.get("snippet")).getString("videoId");
        String videoId2 = ((Document) o2.get("snippet")).getString("videoId");

        return videoId1.compareToIgnoreCase(videoId2);
    }
}
