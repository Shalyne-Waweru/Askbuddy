package com.kevine.askbuddy.post.model;

public class PostModel {
    String description;
    String u_id;
    String topic;
    String likes;

    public PostModel(String description, String u_id, String topic, String likes) {
        this.description = description;
        this.u_id = u_id;
        this.topic = topic;
        this.likes = likes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }
}
