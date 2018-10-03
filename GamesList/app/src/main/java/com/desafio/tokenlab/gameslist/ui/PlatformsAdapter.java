package com.desafio.tokenlab.gameslist.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.desafio.tokenlab.gameslist.R;

import java.util.List;

/**
 * Adapter for the platforms list from {@link com.desafio.tokenlab.gameslist.model.GameModel}.
 * It's necessary so that the items can be shown on RecyclerView.
 */
public class PlatformsAdapter extends RecyclerView.Adapter<PlatformsAdapter.PlatformsViewHolder> {
    private List<String> mPlatforms;

    /**
     * A simple {@link RecyclerView.ViewHolder} subclass.
     * It holds the view of each item of the RecyclerView list
     */
    public class PlatformsViewHolder extends RecyclerView.ViewHolder {
        private TextView mPlatformName;

        public PlatformsViewHolder(View view) {
            super(view);
            mPlatformName = view.findViewById(R.id.platform_name);
        }
    }

    public PlatformsAdapter(List<String> platforms) {
        this.mPlatforms = platforms;
    }

    @Override
    public PlatformsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.platform_item, parent, false);

        return new PlatformsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlatformsAdapter.PlatformsViewHolder platformsViewHolder, int i) {
        String name = mPlatforms.get(i);
        platformsViewHolder.mPlatformName.setText(name);
    }

    @Override
    public int getItemCount() {
        return mPlatforms.size();
    }
}
