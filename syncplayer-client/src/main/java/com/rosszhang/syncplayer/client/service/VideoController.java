package com.rosszhang.syncplayer.client.service;

public interface VideoController {

    void openVideoWebSite();

    void searchVideoByName(String videoName);

    void pauseVideoByUrl(String url);

    void openVideoByUrl(String url);

    void playCurrentVideo();

    Integer listenProgress();

    void syncProgress(int curSeconds);

}
