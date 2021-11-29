package com.mycompany.app;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.SearchResultSnippet;
import com.jcuadros.data.analytics.Channel;
import com.jcuadros.data.analytics.Snippet;
import com.jcuadros.youtube.bot.YoutubeSearchBot;
import org.bson.Document;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Set;

public class ChannelStatistics {

    private String channelId;

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
    public static void main(String[] args) throws GeneralSecurityException, IOException{
        YouTube youtubeService = getService();

        YoutubeSearchBot searchBot = new YoutubeSearchBot(youtubeService);
        List<SearchResult> allVideosFromChannelId = searchBot.getAllVideosFromChannelId(CHANNEL_ID);

        int counter = 0;

        for (SearchResult result : allVideosFromChannelId) {
            DateTime publishTime = new DateTime(((SearchResultSnippet) result.get("snippet")).get("publishTime").toString());
            Snippet snippet = new Snippet(
                    result.getSnippet().getChannelId(),
                    result.getSnippet().getChannelTitle(),
                    result.getSnippet().getDescription(),
                    result.getSnippet().getLiveBroadcastContent(),
                    result.getSnippet().getPublishedAt(),
                    result.getSnippet().getTitle(),
                    publishTime);

            Channel c = new Channel(
                    result.getEtag(),
                    result.getId().getVideoId(),
                    result.getKind(),
                    snippet);

            counter++;
        }
        System.out.println(counter);

    }

    public Set<Document> getAllVideosFromChannelId(String channelId){
        return null;
    }

}
