package com.anonymouscog.spotifystreamer.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.anonymouscog.spotifystreamer.R;
import com.anonymouscog.spotifystreamer.adapters.ArtistAdapter;
import com.anonymouscog.spotifystreamer.models.Artist;
import com.anonymouscog.spotifystreamer.services.MusicServiceApi;
import com.anonymouscog.spotifystreamer.services.MusicServiceFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A Fragment that contains ListView of artists obtained from a search.
 */
public class ArtistActivityFragment extends Fragment {

    private static final String LOG_TAG = ArtistActivityFragment.class.getSimpleName();
    private static final String CURRENT_POSITION = "current_possition";
    private static final String CACHE = "cache";

    private HashMap<String, List<Artist>> mCache = new HashMap<>();
    private List<Artist> mArtistList = new ArrayList<Artist>();
    private ArtistAdapter mAdapter;
    private ListView mListView;
    private String lastQuery;
    private int mLastTopItem;

    public ArtistActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Saves:
     * CURRENT_POSITION: The index of the top displayed item in the list.
     * CACHE: A cache of requests to the music service that have been made in
     * the lifetime of this activity
     * LAST_QUERY: The last artists searched for before state changed. Used to identify
     * screen rotation or duplicate request if the current query and this match.
     * @param outState
     */

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int index = mListView.getFirstVisiblePosition();
        outState.putInt(CURRENT_POSITION, index);
        outState.putSerializable(CACHE, mCache);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_artist, container, false);

        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mLastTopItem = savedInstanceState.getInt(CURRENT_POSITION, 0);
            mCache = (HashMap) savedInstanceState.getSerializable(CACHE);
            lastQuery = getQueryString();

            mArtistList = mCache.get(lastQuery);
        }

        mAdapter = new ArtistAdapter(getActivity(), mArtistList);
        mListView = (ListView) rootView.findViewById(R.id.artist_listView);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), TopTracksActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, mAdapter.getItem(i).getArtistId());
                intent.putExtra(Intent.EXTRA_TITLE, mAdapter.getItem(i).getArtistName());
                startActivity(intent);
            }
        });
        mListView.setSelection(mLastTopItem);
        return rootView;

    }

    /**
     * Store responses for artist searches in order to reduce the amount of api calls
     *
     * @param artistName: The artist the user is searching for
     * @param artists A response list of Artists that the criteria that have been cached
     */

    private void addToCache(String artistName, List<Artist> artists) {
        mCache.put(artistName, artists);
    }

    /**
     * Looks for previously stored music service responses
     *
     * @param artistName: The artist the user is searching for
     * @return A response list of Artists that the criteria that have been cached
     */

    public List<Artist> getFromCache(String artistName) {
        if (mCache.containsKey(artistName)) {
            return new ArrayList<Artist>(mCache.get(artistName));
        } else {
            Log.d(LOG_TAG, "Result for this artist search not in cache");
            return null;
        }
    }

    /**
     * Called from the parent activity. Tries to find a previously cached response
     * before going out to the api.
     *
     * @param artistName The artist the user is searching for
     */

    public void searchForartist(String artistName) {
        List<Artist> cachedResponse = getFromCache(artistName);
        if (cachedResponse == null) {
            SearchArtistTask searchArtist = new SearchArtistTask();
            searchArtist.execute(artistName);
        } else {
            updateList(cachedResponse);
            if (artistName.equals(lastQuery)) {
                mListView.setSelection(mLastTopItem);
            }
        }
    }

    private void updateList(List<Artist> artists) {
        mArtistList.clear();
        if (artists.isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.no_results_found), Toast.LENGTH_SHORT).show();
        } else {
            mArtistList.addAll(artists);
        }
        mAdapter.notifyDataSetChanged();

    }


    private String getQueryString(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        return prefs.getString(getString(R.string.pref_key_last_artist_query), "");
    }

    public class SearchArtistTask extends AsyncTask<String, Void, List<Artist>> {
        private final String LOG_TAG = SearchArtistTask.class.getSimpleName();


        @Override
        protected List<Artist> doInBackground(String... artistName) {
            if (artistName.length == 0 || artistName[0] == null || artistName[0].isEmpty()) {
                Log.d(LOG_TAG, "Artist name is empty or null");
                return new ArrayList<Artist>();
            }

            MusicServiceApi musicService = MusicServiceFactory.getMusicServiceInstance(MusicServiceFactory.SPOTIFY);
            List<Artist> result = musicService.searchForArtist(artistName[0]);
            addToCache(artistName[0], result);
            return result;
        }

        @Override
        protected void onPostExecute(List<Artist> artists) {
            updateList(artists);
        }
    }

}
