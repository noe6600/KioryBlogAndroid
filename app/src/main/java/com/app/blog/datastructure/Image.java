package com.app.blog.datastructure;

import com.app.blog.utils.AlternativeName;

/**
 * Created by darknoe on 27/4/15.
 */
public class Image {
    @AlternativeName("blog-square")
    private ImageDetails blog_square;
    private ImageDetails thumbnail;

    public Image() {
    }

    public ImageDetails getBlog_square() {
        return blog_square;
    }

    public ImageDetails getThumbnail() {
        return thumbnail;
    }
}
