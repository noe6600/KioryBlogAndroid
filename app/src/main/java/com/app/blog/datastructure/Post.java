package com.app.blog.datastructure;

import java.util.List;

/**
 * Created by darknoe on 15/4/15.
 */
public class Post {
    private int id;
    private String slug;
    private String type;
    private String url;
    private String status;
    private String title;
    private String title_plain;
    private String content;
    private String excerpt;
    private String date;
    private String thumbnail;
    private List<Category> categories;
    private List<Attachment> attachments;
    private ThumbnailImages thumbnail_images;

    public Post(){}

    public int getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getTitle_plain() {
        return title_plain;
    }

    public String getContent() {
        return content;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public String getDate() {
        return date;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public ThumbnailImages getThumbnail_images() {
        return thumbnail_images;
    }
}
