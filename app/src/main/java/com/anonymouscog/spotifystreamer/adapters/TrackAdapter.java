package com.anonymouscog.spotifystreamer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anonymouscog.spotifystreamer.R;
import com.anonymouscog.spotifystreamer.models.Track;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by manuelcintron on 7/3/15.
 */
public class TrackAdapter extends ArrayAdapter<Track> {

    public TrackAdapter(Context context, List<Track> tracks){
        super(context,0,tracks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Track t = getItem(position);
        View v = convertView;
        TrackViewHolder holder;

        if(v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.list_item_track, parent, false);
            holder = new TrackViewHolder();
            holder.imageView = (ImageView) v.findViewById(R.id.track_image);
            holder.albumName = (TextView)v.findViewById(R.id.album_name_label);
            holder.trackName = (TextView)v.findViewById(R.id.track_name_label);
            v.setTag(holder);

        } else{
            holder = (TrackViewHolder) v.getTag();
        }

        Picasso.with(getContext())
                .load(t.getTrackImageUrlSmall())
                .into(holder.imageView);

        holder.trackName.setText(t.getTrackName());
        holder.albumName.setText(t.getTrackAlbum());

        return v;
    }

    static class TrackViewHolder{
        ImageView imageView;
        TextView trackName;
        TextView albumName;
    }
}
