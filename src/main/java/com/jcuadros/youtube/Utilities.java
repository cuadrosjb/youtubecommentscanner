package com.jcuadros.youtube;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utilities {

    private YouTube.CommentThreads.List request;
    private CommentThreadListResponse response;
    private List<Comment> flaggedComments;
    private List<Comment> publishedComments;

    public Utilities(YouTube.CommentThreads.List request, CommentThreadListResponse response) {
        this.flaggedComments = new ArrayList<>();
        this.publishedComments = new ArrayList<>();
        this.request = request;
        this.response = response;
    }

    public void loopThruVideo(List<CommentThread> videoComments) {

        for (CommentThread videoComment : videoComments) {

            CommentThreadSnippet snippet = videoComment.getSnippet();
            CommentThreadReplies replies = videoComment.getReplies();

            Comment topLevelComment = snippet.getTopLevelComment();
            publishedComments.add(topLevelComment);
            printComment(topLevelComment);

            if (replies != null) {
                List<Comment> comments = replies.getComments();
                for (Comment comment : comments) {
                    CommentSnippet replyCommentSnippet = comment.getSnippet();
                    publishedComments.add(comment);
                    printComment(comment);
                }
            }
        }
    }

    public void printComment(Comment comment) {
        String textOriginal = comment.getSnippet().getTextOriginal();
        String onlyAlphaNumericCharacters = textOriginal.replaceAll("[^a-zA-Z0-9 ]", "");
        List<String> commentBlocks = parseCommentIntoBlocks(onlyAlphaNumericCharacters);

        for (String block : commentBlocks) {
            if (isItANumber(block)) {
                if (block.length() == 11) {
                    flaggedComments.add(comment);
                }
            }
        }
    }

    public static boolean isItACommentFromAScammer(Comment comment){
        String textOriginal = comment.getSnippet().getTextOriginal();
        String onlyAlphaNumericCharacters = textOriginal.replaceAll("[^a-zA-Z0-9 ]", "");
        List<String> commentBlocks = parseCommentIntoBlocks(onlyAlphaNumericCharacters);

        for (String block : commentBlocks) {
            if (isItANumber(block)) {
                if (block.length() == 11) {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<String> parseCommentIntoBlocks(String comment) {
        List<String> blocks = Arrays.asList(comment.toLowerCase().split(" "));
        List<String> filtered = new ArrayList<>();

        for (int i = 0; i < blocks.size(); i++) {
            String block = blocks.get(i).trim();
            if (!block.isEmpty()) {
                filtered.add(block);
            }
        }
        return filtered;
    }

    public static boolean isItANumber(String block) {
        try {
            Double.parseDouble(block);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public List<Comment> getListOfCommentsByScammers() {
        loopThruVideo(response.getItems());

        try {
            while (response.getNextPageToken() != null) {
                response = request.setPageToken(response.getNextPageToken()).execute();
                loopThruVideo(response.getItems());
            }
        } catch (IOException ex){
            System.out.println("An error occurred while gathering the next page.");
        }
        return flaggedComments;
    }


    public List<String> getListOfVideoFromChannel(){


        return null;
    }
    public List<Comment> getAllComments(String id){
        return null;
    }

}
