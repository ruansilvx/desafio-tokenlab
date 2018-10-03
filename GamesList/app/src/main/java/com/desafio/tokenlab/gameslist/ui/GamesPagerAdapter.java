package com.desafio.tokenlab.gameslist.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.desafio.tokenlab.gameslist.model.GameModel;

import java.util.List;

/**
 * A simple {@link FragmentStatePagerAdapter} subclass.
 * This adapter instantiates the fragments to be used on MainActivity's ViewPager.
 */
public class GamesPagerAdapter extends FragmentStatePagerAdapter {

    private List<GameModel> mGames;

    public GamesPagerAdapter(FragmentManager fm, List<GameModel> games) {
        super(fm);
        this.setGames(games);
    }

    @Override
    public Fragment getItem(int i) {
        GameModel game = mGames.get(i);
        return GameFragment.newInstance(game);
    }

    @Override
    public int getCount() {
        if (mGames == null)
            return 0;
        return mGames.size();
    }

    private void setGames(List<GameModel> mGames) {
        this.mGames = mGames;
    }

}