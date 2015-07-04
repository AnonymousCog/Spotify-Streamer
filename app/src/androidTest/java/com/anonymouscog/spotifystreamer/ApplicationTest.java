package com.anonymouscog.spotifystreamer;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.anonymouscog.spotifystreamer.models.Artist;
import com.anonymouscog.spotifystreamer.models.Track;
import com.anonymouscog.spotifystreamer.services.Spotify;

import java.util.List;


/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testSearchForArtist() throws Exception {
        Spotify model = new Spotify();
        List<Artist> results = model.searchForArtist("Maroon 5");
        assertNotNull(results);
    }

    public void testtop10Tracks(){
        Spotify model = new Spotify();
        List<Track> results = model.top10Tracks("04gDigrS5kc9YWfZHwBETP", "US");
        assertNotNull(results);
    }
}