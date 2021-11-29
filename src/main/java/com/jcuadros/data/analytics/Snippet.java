package com.jcuadros.data.analytics;

import com.google.api.client.util.DateTime;

public class Snippet {

    private String channelId;
    private String channelTitle;
    private String description;
    private String liveBroadcastContent;
    private DateTime publishedAt;
    private String title;
    private DateTime publishTime;

    public Snippet(String channelId, String channelTitle, String description, String liveBroadcastContent, DateTime publishedAt, String title, DateTime publishTime) {
        this.channelId = channelId;
        this.channelTitle = channelTitle;
        this.description = description;
        this.liveBroadcastContent = liveBroadcastContent;
        this.publishedAt = publishedAt;
        this.title = title;
        this.publishTime = publishTime;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLiveBroadcastContent() {
        return liveBroadcastContent;
    }

    public void setLiveBroadcastContent(String liveBroadcastContent) {
        this.liveBroadcastContent = liveBroadcastContent;
    }

    public DateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(DateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DateTime getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(DateTime publishTime) {
        this.publishTime = publishTime;
    }

    @Override
    public String toString() {
        return "Snippet{" +
                "channelId='" + channelId + '\'' +
                ", channelTitle='" + channelTitle + '\'' +
                ", description='" + description + '\'' +
                ", liveBroadcastContent='" + liveBroadcastContent + '\'' +
                ", publishedAt=" + publishedAt +
                ", title='" + title + '\'' +
                ", publishTime=" + publishTime +
                '}';
    }
}
