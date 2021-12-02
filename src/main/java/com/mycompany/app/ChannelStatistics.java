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
import com.jcuadros.youtube.YoutubeSearchBot;
import com.jcuadros.youtube.bot.Session;
import org.bson.Document;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ChannelStatistics {

    private String channelId;


    private static final String CHANNEL_ID = "UC-CfltxhlMMCT5pJNIfQhlQ";
    //Jeremy Financial Education 3 = UC_xMKlHYdRNXLX9XzuOLjsg
    //Jeremy Financial Education = UCnMn36GT_H0X-w5_ckLtlgQ
    //Trey's Trade = UC4a-9NnHFv4ZhI-HWc4xNaA
    //Matt Kohrs = UCWKODoiwSuLNSXIKuKnuQTQ
    //Graham Stephan = UCV6KDgJskWaEckne5aPA0aQ
    //Popular Investor = UC-CfltxhlMMCT5pJNIfQhlQ --


    /**
     * Call function to create API service object. Define and
     * execute API request. Print API response.
     *
     * @throws GeneralSecurityException, IOException, GoogleJsonResponseException
     */
    public static void main(String[] args) throws GeneralSecurityException, IOException {
        YouTube youtubeService = Session.getInstance().service;

        List<Channel> all = getAllVideosFromChannelId(youtubeService, CHANNEL_ID);
        all.forEach(System.out::println);


    }

    public Set<Document> getAllVideosFromChannelId(String channelId) {
        return null;
    }

    public static List<Channel> getAllVideosFromChannelId(YouTube service, String channelId) {

        YoutubeSearchBot searchBot = new YoutubeSearchBot(service);

        List<SearchResult> allVideosFromChannelId = searchBot.getAllVideosFromChannelId(channelId);
        List<Channel> videos = new ArrayList<>();

        for (SearchResult result : allVideosFromChannelId) {
            Channel c = searchResultParser(result);
            videos.add(c);

        }
        return videos;
    }

    private static Channel searchResultParser(SearchResult searchResult){
        DateTime publishTime = new DateTime(
                ((SearchResultSnippet) searchResult.get("snippet")).get("publishTime").toString());
        Snippet snippet = new Snippet(
                searchResult.getSnippet().getChannelId(),
                searchResult.getSnippet().getChannelTitle(),
                searchResult.getSnippet().getDescription(),
                searchResult.getSnippet().getLiveBroadcastContent(),
                searchResult.getSnippet().getPublishedAt(),
                searchResult.getSnippet().getTitle(),
                publishTime);

        Channel channel = new Channel(
                searchResult.getEtag(),
                searchResult.getId().getVideoId(),
                searchResult.getKind(),
                snippet);

        return channel;
    }

}
