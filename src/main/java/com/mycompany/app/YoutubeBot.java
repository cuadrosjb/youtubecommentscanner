package com.mycompany.app;

/**
 * Sample Java code for youtube.commentThreads.list
 * See instructions for running these code samples locally:
 * https://developers.google.com/explorer-help/guides/code_samples#java
 */

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import com.jcuadros.youtube.bot.Utilities;
import com.jcuadros.youtube.bot.YoutubeCommentThreadsBot;
import com.jcuadros.youtube.bot.YoutubeSearchBot;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoClient;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;


public class YoutubeBot {
    // You need to set this value for your code to compile.
    // For example: ... DEVELOPER_KEY = "YOUR ACTUAL KEY";
    private static final String DEVELOPER_KEY = System.getenv("YOUTUBE_DATA_API_DEVELOPER_KEY");
    ;

    private static final String APPLICATION_NAME = "spam-comment-detector";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static final String CHANNEL_ID = "UCV6KDgJskWaEckne5aPA0aQ";
    //Jeremy Financial Education 3 = UC_xMKlHYdRNXLX9XzuOLjsg
    //Jeremy Financial Education = UCnMn36GT_H0X-w5_ckLtlgQ
    //Trey's Trade = UC4a-9NnHFv4ZhI-HWc4xNaA
    //Matt Kohrs = UCWKODoiwSuLNSXIKuKnuQTQ
    //Graham Stephan = UCV6KDgJskWaEckne5aPA0aQ


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

    /**
     * Call function to create API service object. Define and
     * execute API request. Print API response.
     *
     * @throws GeneralSecurityException, IOException, GoogleJsonResponseException
     */
    public static void main(String[] args)
            throws GeneralSecurityException, IOException, GoogleJsonResponseException {
        YouTube youtubeService = getService();
        // Define and execute the API request

        YoutubeSearchBot searchBot = new YoutubeSearchBot(youtubeService);
        List<SearchResult> allVideosFromChannelId = searchBot.getAllVideosFromChannelId(CHANNEL_ID);

        YoutubeCommentThreadsBot commentThreadsBot = new YoutubeCommentThreadsBot(youtubeService);


        MongoClientURI connectionString = new MongoClientURI("mongodb://root:root@localhost:27017");
        MongoClient mongoClient = new MongoClient(connectionString);
        MongoDatabase database = mongoClient.getDatabase("youtube_comments");

        MongoCollection<Document> databaseCollection = database.getCollection("comments");

        for (SearchResult searchResult : allVideosFromChannelId) {
            List<Comment> allCommentsFromVideoId = commentThreadsBot
                    .getAllCommentsFromVideoId(searchResult.getId().getVideoId());
            List<Comment> allCommentsFromScammer = commentThreadsBot.getCommentsFromScammers(allCommentsFromVideoId);
            for (Comment comment : allCommentsFromScammer) {
                try {
                    Document document = new Document("etag", comment.getEtag())
                            .append("_id", comment.getId())
                            .append("kind", comment.getKind())
                            .append("reported", false)
                            .append("snippet",
                                    new Document("authorChannelId",
                                            new Document("value", comment.getSnippet().getAuthorChannelId()))
                                            .append("authorChannelUrl", comment.getSnippet().getAuthorChannelUrl())
                                            .append("authorDisplayName", comment.getSnippet().getAuthorDisplayName())
                                            .append("authorProfileImageUrl", comment.getSnippet().getAuthorProfileImageUrl())
                                            .append("canRate", comment.getSnippet().getCanRate())
                                            .append("parentId", comment.getSnippet().getParentId())
                                            .append("publishedAt", comment.getSnippet().getPublishedAt().toStringRfc3339())
                                            .append("textDisplay", comment.getSnippet().getTextDisplay())
                                            .append("textOriginal", comment.getSnippet().getTextOriginal())
                                            .append("updatedAt", comment.getSnippet().getUpdatedAt().toStringRfc3339())
                                            .append("videoId", comment.getSnippet().getVideoId())
                                            .append("viewerRating", comment.getSnippet().getViewerRating()));

                    databaseCollection.insertOne(document);
                } catch (MongoWriteException exception) {
                    System.out.println("This key as already been inserted: " + comment.getId());
                }
            }
        }
    }
}
