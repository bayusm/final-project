package com.example.finalproject.post.data;

public class PostFile {

    public final String name;
    public final String type;
    public final byte[] bytes;

    public PostFile(String name, String type, byte[] bytes) {
        this.name = name;
        this.type = type;
        this.bytes = bytes;
    }
}
