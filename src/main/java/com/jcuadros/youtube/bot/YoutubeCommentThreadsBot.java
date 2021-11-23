package com.jcuadros.youtube.bot;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class YoutubeCommentThreadsBot {

    private YouTube youtubeService;

    public YoutubeCommentThreadsBot(YouTube youtubeService){
        this.youtubeService = youtubeService;
    }

    public List<Comment> getAllCommentsFromVideoId(String videoId){
        List<Comment> comments = new ArrayList<>();

        try {
            YouTube.CommentThreads.List request = youtubeService.commentThreads()
                    .list("id,replies,snippet")
                    .setOrder("time")
                    .setMaxResults(100L)
                    .setModerationStatus("published")
                    .setTextFormat("plainText");

            CommentThreadListResponse response = null;

            do {
                if (response == null) {
                    response = request
                            .setKey(System.getenv("YOUTUBE_DATA_API_DEVELOPER_KEY"))
                            .setVideoId(videoId).execute();
                } else {
                    response = request.setPageToken(response.getNextPageToken()).execute();
                }
                List<CommentThread> commentThreads = response.getItems();

                for(CommentThread commentThread : commentThreads){
                    CommentThreadSnippet snippet = commentThread.getSnippet();
                    CommentThreadReplies replies = commentThread.getReplies();

                    Comment topLevelComment = snippet.getTopLevelComment();
                    comments.add(topLevelComment);

                    if (replies != null) {
                        List<Comment> repliesComments = replies.getComments();
                        for (Comment comment : repliesComments) {
                            comments.add(comment);
                        }
                    }
                }

            } while (response.getNextPageToken() != null);
        } catch (IOException exception){
            System.out.println(exception);
        }
        return comments;
    }


    public List<Comment> getCommentsFromScammers(List<Comment> comments) {
        List<Comment> commentsFromScammers = new ArrayList<>();
        for (Comment comment : comments) {
            if(Utilities.isItACommentFromAScammer(comment)){
                commentsFromScammers.add(comment);
            }
        }
        return commentsFromScammers;
    }

}
