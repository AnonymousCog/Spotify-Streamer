package com.anonymouscog.spotifystreamer.models;

import java.io.Serializable;

/**
 * Created by manuelcintron on 7/2/15.
 */
public class Track implements Serializable {
    private String trackName;
    private String trackAlbum;
    private String trackId;
    private String trackUrl;
    private String trackImageUrl;
    private String trackImageUrlSmall;
    private String trackImageUrlLarge;

    public Track(String trackName, String trackAlbum, String trackUrl, String trackId, String trackImageUrlLarge, String trackImageUrl, String trackImageUrlSmall) {
        this.trackName = trackName;
        this.trackAlbum = trackAlbum;
        this.trackUrl = trackUrl;
        this.trackId = trackId;
        this.trackImageUrl = trackImageUrl;
        this.trackImageUrlSmall = trackImageUrlSmall;
        this.trackImageUrlLarge = trackImageUrlLarge;
    }

    public String getTrackImageUrl() {
        return trackImageUrl;
    }

    public String getTrackAlbum() {
        return trackAlbum;
    }

    public String getTrackName() { return trackName; }

    public String getTrackId() { return trackId; }

    public String getTrackUrl() { return trackUrl; }

    public String getTrackImageUrlSmall() {
        return trackImageUrlSmall;
    }

    public String getTrackImageUrlLarge() {
        return trackImageUrlLarge;
    }
}
