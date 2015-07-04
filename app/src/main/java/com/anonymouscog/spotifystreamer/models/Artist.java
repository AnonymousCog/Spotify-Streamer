package com.anonymouscog.spotifystreamer.models;

import java.io.Serializable;

/**
 * Created by manuelcintron on 7/2/15.
 */
public class Artist implements Serializable {
    private String artistName;
    private String artistId;
    private String artistImageUrl;
    private String artistImageUrlSmall;
    private String artistImageUrlLarge;

    public Artist(String artistName, String artistId, String artistImageUrlLarge, String artistImageUrl, String artistImageUrlSmall) {
        this.artistName = artistName;
        this.artistId = artistId;
        this.artistImageUrl = artistImageUrl;
        this.artistImageUrlSmall = artistImageUrlSmall;
        this.artistImageUrlLarge = artistImageUrlLarge;
    }


    public String getArtistName() {
        return artistName;
    }

    public String getArtistImageUrl() {
        return artistImageUrl;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getArtistImageUrlSmall() {
        return artistImageUrlSmall;
    }

    public String getArtistImageUrlLarge() {
        return artistImageUrlLarge;
    }
}
