package com.example.finalproject.post.factory;

import com.example.finalproject.post.PostCategoryID;

public class PostFactoryServices {

    private static final AskPostFactory askPostFactory = new AskPostFactory();
    private static final BlogPostFactory blogPostFactory = new BlogPostFactory();
    private static final ShareSongPostFactory shareSongPostFactory = new ShareSongPostFactory();

    public static PostFactory GetInstancePostFactory(@PostCategoryID int categoryID) {
        switch (categoryID) {
            case PostCategoryID.ASK:
                return askPostFactory;
            case PostCategoryID.BLOG:
                return blogPostFactory;
            case PostCategoryID.SHARE_SONG:
                return shareSongPostFactory;
        }
        return null;
    }
}
