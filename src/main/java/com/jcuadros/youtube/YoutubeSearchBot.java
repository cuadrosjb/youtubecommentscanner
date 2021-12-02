package com.jcuadros.youtube;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class YoutubeSearchBot {

    private YouTube youtubeService;

    public YoutubeSearchBot(YouTube youtubeService) {
        this.youtubeService = youtubeService;
    }

    public List<SearchResult> getAllVideosFromChannelId(String channelId) {
        List<SearchResult> videos = new ArrayList<>();

        try {
            YouTube.Search.List contentDetails = youtubeService.search()
                    .list("snippet")
                    .setOrder("date")
                    .setType("video")
                    .setMaxResults(50L);
            SearchListResponse execute = contentDetails.setKey(System.getenv("YOUTUBE_DATA_API_DEVELOPER_KEY"))
                    .setChannelId(channelId)
                    .execute();
            videos.addAll(execute.getItems());

            while (execute.getNextPageToken() != null) {
                execute = contentDetails
                        .setPageToken(execute.getNextPageToken())
                        .execute();

                videos.addAll(execute.getItems());
            }
        } catch (IOException exception) {
            System.out.println(exception);
        }

        return videos;
    }
}
