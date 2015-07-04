package com.anonymouscog.spotifystreamer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anonymouscog.spotifystreamer.R;
import com.anonymouscog.spotifystreamer.models.Artist;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by manuelcintron on 7/2/15.
 */
public class ArtistAdapter extends ArrayAdapter<Artist>  {
    public ArtistAdapter(Context context,  List<Artist> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ArtistViewHolder holder;
        Artist a = getItem(position);

        if(v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.list_item_artist, parent, false);
            holder = new ArtistViewHolder();
            holder.imageView = (ImageView) v.findViewById(R.id.artist_image);
            holder.textView = (TextView) v.findViewById(R.id.artist_name_label);
            v.setTag(holder);
        }
        else{
            holder = (ArtistViewHolder) v.getTag();
        }

        Picasso.with(getContext())
                .load(a.getArtistImageUrlSmall())
                .into(holder.imageView);

        holder.textView.setText(a.getArtistName());
        return v;
    }

    static class ArtistViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
