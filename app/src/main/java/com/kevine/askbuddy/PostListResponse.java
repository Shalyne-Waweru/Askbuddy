package com.kevine.askbuddy;

public class PostListResponse {
    private String description;
    private String imageurl;
    private String u_id;
    private String topic;

    public PostListResponse(String description, String imageurl, String u_id, String topic) {
        this.description = description;
        this.imageurl = imageurl;
        this.u_id = u_id;
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
