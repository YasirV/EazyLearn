package com.wheic.EazyLearn;

public class VideoModel {
    String videoname;
    String url;

    public VideoModel(String videoname, String url) {
        this.videoname = videoname;
        this.url = url;
    }

    //    int image;


    public String getVideoname() {
        return videoname;
    }

    public void setVideoname(String videoname) {
        this.videoname = videoname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
