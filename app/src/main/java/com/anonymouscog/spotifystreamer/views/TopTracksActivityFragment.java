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
import com.anonymouscog.spotifystreamer.adapters.TrackAdapter;
import com.anonymouscog.spotifystreamer.models.Track;
import com.anonymouscog.spotifystreamer.services.MusicServiceApi;
import com.anonymouscog.spotifystreamer.services.MusicServiceFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class TopTracksActivityFragment extends Fragment {
    private static final String LOG_TAG = TopTracksActivityFragment.class.getSimpleName();
    private static final String CURRENT_POSITION = "current_possition";
    private static final String TRACK_LIST = "track_list";

    private ArrayList<Track> mTrackList = new ArrayList<Track>();
    private ListView mListView;
    private TrackAdapter mAdapter;
    private String mArtistId;
    private int mLastTopItem;


    public TopTracksActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_top_tracks, container, false);

        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mLastTopItem = savedInstanceState.getInt(CURRENT_POSITION, 0);
            mTrackList = (ArrayList<Track>)savedInstanceState.getSerializable(TRACK_LIST);
        }

        mAdapter = new TrackAdapter(getActivity(), mTrackList);
        mListView = (ListView) rootView.findViewById(R.id.tracks_listView);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*String trackUrl = mAdapter.getItem(i).getTrackImageUrlLarge();
                //Toast.makeText(getActivity(), trackUrl, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MusicPlayerActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(intent);*/
            }
        });

        mArtistId = getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT);
        if(mTrackList == null || mTrackList.isEmpty()){
            searchForTopTracks(mArtistId);
        } else{
            mListView.setSelection(mLastTopItem);
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int index = mListView.getFirstVisiblePosition();
        outState.putInt(CURRENT_POSITION, index);
        outState.putSerializable(TRACK_LIST, mTrackList);
    }

    public void searchForTopTracks(String artistId){
        SearchTopTracksTask searchTracks = new SearchTopTracksTask();
        searchTracks.execute(artistId);

    }

    public String getUserCountryPreference(){
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getActivity());

        return SP.getString(
                getString(R.string.pref_country_key),
                getString(R.string.pref_country_default));
    }

    public class SearchTopTracksTask extends AsyncTask<String, Void, List<Track>>{
        private final String LOG_TAG = SearchTopTracksTask.class.getSimpleName();

        @Override
        protected List<Track> doInBackground(String... artistId) {
            if(artistId.length == 0 || artistId[0]==null || artistId[0].isEmpty()) {
                Log.d(LOG_TAG, "Invalid artist id: "+ artistId);
                return new ArrayList<Track>();
            }

            MusicServiceApi musicService = MusicServiceFactory.getMusicServiceInstance(MusicServiceFactory.SPOTIFY);
            return musicService.top10Tracks(artistId[0], getUserCountryPreference());
        }

        @Override
        protected void onPostExecute(List<Track> tracks) {
            mTrackList.clear();
            if(tracks.isEmpty()){
                Toast.makeText(getActivity(), getString(R.string.no_results_found), Toast.LENGTH_SHORT).show();
            } else {
                mTrackList.addAll(tracks);
            }
            mAdapter.notifyDataSetChanged();
        }
    }
}
