package com.desafio.tokenlab.gameslist.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.desafio.tokenlab.gameslist.R;
import com.desafio.tokenlab.gameslist.model.GameModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Holds the parsed data from Volley's requests on MainActivity.
 */
public class GameFragment extends Fragment {

    private String mId;
    private String mName;
    private String mImageUrl;
    private String mReleaseDate;
    private String mTrailerUrl;
    private List<String> mPlatforms;

    public GameFragment() {
        // Required empty public constructor
    }

    /**
     * This factory is used to instantiate th GameFragment qith given params.
     * @param game
     * @return A new instance of fragment GameFragment.
     */
    public static GameFragment newInstance(GameModel game) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putString("id", game.getId());
        args.putString("name", game.getName());
        args.putString("image", game.getImagename());
        args.putString("release_date", game.getReleaseDate());
        args.putString("trailer", game.getTrailerUrl());
        args.putStringArrayList("platforms", (ArrayList<String>) game.getPlatforms());
        fragment.setArguments(args);
        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            mId = bundle.getString("id");
            mName = bundle.getString("name");
            mImageUrl = bundle.getString("image");
            mReleaseDate = bundle.getString("release_date");
            mTrailerUrl = bundle.getString("trailer");
            mPlatforms = bundle.getStringArrayList("platforms");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        TextView tvId = view.findViewById(R.id.fragment_game_id);
        TextView tvName = view.findViewById(R.id.fragment_game_name);
        ImageView ivImage = view.findViewById(R.id.fragment_game_image);
        TextView tvRelease = view.findViewById(R.id.fragment_game_release);
        RecyclerView rvPlatforms = view.findViewById(R.id.fragment_game_platforms);
        Button btTrailer = view.findViewById(R.id.fragment_game_trailer);

        btTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = mTrailerUrl.substring(mTrailerUrl.indexOf("=") + 1);
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(mTrailerUrl));
                try {
                    // try to open link on youtube app
                    startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    // if app is not available, it opens on the browser
                    startActivity(webIntent);
                }
            }
        });

        readBundle(getArguments());

        tvId.setText(mId);
        tvName.setText(mName);
        tvRelease.setText(mReleaseDate);

        PlatformsAdapter platformsAdapter = new PlatformsAdapter(mPlatforms);
        // Changes RecyclerView orientation to horizontal
        rvPlatforms.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        rvPlatforms.setAdapter(platformsAdapter);
        rvPlatforms.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.HORIZONTAL));

        // Picasso is only used to load the image of the given url
        Picasso.with(getContext()).load(mImageUrl)
                .placeholder(getContext().getResources().getDrawable(R.mipmap.im_loading_image))
                .error(getContext().getResources().getDrawable(R.mipmap.im_image_not_found))
                .fit().into(ivImage);
        return view;
    }
}