package com.anonymouscog.spotifystreamer.services;

import com.anonymouscog.spotifystreamer.models.Artist;
import com.anonymouscog.spotifystreamer.models.Track;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Tracks;


/**
 * Created by manuelcintron on 6/30/15.
 */
public class Spotify implements MusicServiceApi {
    private SpotifyApi api;
    private SpotifyService service;
    private static final String ACCESS_TOKEN="";


    public Spotify(){
        api = new SpotifyApi();
        service = api.getService();
    }

    public List<Artist> searchForArtist(String artistName){
        ArtistsPager pager = service.searchArtists(artistName);
        return convertToGenericArtistList(pager.artists.items);
    }

    private  List<Artist> convertToGenericArtistList(List<kaaes.spotify.webapi.android.models.Artist> result){
        List<Artist>  artist = new ArrayList<Artist>();
        int size = result.size();
        for(int i=0; i< size; i++){
            kaaes.spotify.webapi.android.models.Artist t = result.get(i);
            if(!t.images.isEmpty()){
                artist.add(new Artist(t.name, t.id, t.images.get(0).url, t.images.get(1).url, t.images.get(2).url));
            }

        }
        return artist;
    }

    public List<Track> top10Tracks(String artistId, String countryCode){
        Map<String, Object > options = new HashMap<String, Object>();
        options.put("country", countryCode);
        Tracks tracks = service.getArtistTopTrack(artistId, options);
        return convertToGenericTrackList(tracks.tracks);
    }

    private List<Track> convertToGenericTrackList(List<kaaes.spotify.webapi.android.models.Track> result){
        List<Track> tracks = new ArrayList<Track>();
        int size = result.size();
        for(int i=0; i< size; i++){
            kaaes.spotify.webapi.android.models.Track t = result.get(i);
            if(!t.album.images.isEmpty()) {
                tracks.add(new Track(t.name, t.album.name, t.preview_url, t.id, t.album.images.get(0).url,t.album.images.get(1).url,t.album.images.get(2).url));
            }
        }
        return tracks;
    }
}
