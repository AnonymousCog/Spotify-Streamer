package com.anonymouscog.spotifystreamer.services;

/**
 * Created by manuelcintron on 7/2/15.
 */
public class MusicServiceFactory {
    public static final String SPOTIFY = "spotify";

    public static MusicServiceApi getMusicServiceInstance(String serviceIdentifier){
        if(serviceIdentifier == null || serviceIdentifier.isEmpty()){
            return null;
        }
        switch (serviceIdentifier){
            case SPOTIFY:
                return getSpotifyApiModelInstance();

            default:
                return null;
        }
    }

    private static MusicServiceApi getSpotifyApiModelInstance(){
        return new Spotify();
    }
}
