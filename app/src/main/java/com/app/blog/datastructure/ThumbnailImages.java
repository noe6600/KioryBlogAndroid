package com.app.blog.datastructure;

import com.app.blog.utils.AlternativeName;

/**
 * Created by darknoe on 28/4/15.
 */
public class ThumbnailImages {
    @AlternativeName("blog-square")
    private ImageDetails blog_square;
    private ImageDetails thumbnail;
    private ImageDetails full;

    public ImageDetails getBlog_square() {
        return blog_square;
    }

    public ImageDetails getThumbnail() {
        return thumbnail;
    }

    public ImageDetails getFull() {
        return full;
    }
}
