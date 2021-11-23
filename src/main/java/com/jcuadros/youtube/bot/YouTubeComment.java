package com.jcuadros.youtube.bot;

import com.google.api.services.youtube.model.Comment;
//import org.springframework.data.mongodb.core.mapping.Document;


//@Document(collection = "comment")
public class YouTubeComment {

    private Comment comment;

    public YouTubeComment(Comment comment){
        this.comment = comment;
    }



}
