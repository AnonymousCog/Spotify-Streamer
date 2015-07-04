package com.anonymouscog.spotifystreamer.services;

import com.anonymouscog.spotifystreamer.models.Artist;
import com.anonymouscog.spotifystreamer.models.Track;

import java.util.List;



/**
 * Created by manuelcintron on 7/2/15.
 */
public interface MusicServiceApi {
    public List<Artist> searchForArtist(String artistName);
    public List<Track> top10Tracks(String artistId, String countryCode);
}
