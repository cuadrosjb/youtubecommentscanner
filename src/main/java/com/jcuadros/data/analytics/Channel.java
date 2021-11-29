package com.jcuadros.data.analytics;

public class Channel {

    private String etag;
    private String id;
    private String kind;
    private Snippet snippet;

    public Channel(String etag, String id, String kind, Snippet snippet) {
        this.etag = etag;
        this.id = id;
        this.kind = kind;
        this.snippet = snippet;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Snippet getSnippet() {
        return snippet;
    }

    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "etag='" + etag + '\'' +
                ", id='" + id + '\'' +
                ", kind='" + kind + '\'' +
                "," + snippet.toString() +
                '}';
    }
}



