package com.desafio.tokenlab.gameslist.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.desafio.tokenlab.gameslist.R;
import com.desafio.tokenlab.gameslist.model.GameModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link FragmentActivity} subclass.
 * Makes requests using {@link Volley} and populates a ViewPager with fragments with data from
 * the requests responses.
 */

public class MainActivity extends FragmentActivity {
    private ViewPager mPager;
    private List<GameModel> mGames = new ArrayList<>();
    private GamesPagerAdapter mAdapter;
    private ProgressBar mLoading;

    private static String JSON_URL = "https://dl.dropboxusercontent.com/s/1b7jlwii7jfvuh0/games";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoading = findViewById(R.id.main_activity_progressbar);

        mPager = findViewById(R.id.pager);
        mPager.setVisibility(View.GONE);

        getGames();

        mAdapter = new GamesPagerAdapter(getSupportFragmentManager(), mGames);
        mPager.setAdapter(mAdapter);
    }

    private void getGames() {
        mLoading.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objReq = new JsonObjectRequest(Request.Method.GET, JSON_URL, null,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request and populate a list with the parsed data
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray gamesArray = response.getJSONArray("games");
                            for (int i = 0; i < gamesArray.length(); i++) {
                                JSONObject game = gamesArray.getJSONObject(i);
                                JSONArray platformsArray = game.getJSONArray("platforms");
                                List<String> platforms = new ArrayList<>();
                                for (int j = 0; j < platformsArray.length(); j++) {
                                    platforms.add(platformsArray.getString(j));
                                }

                                String id = game.getString("id");
                                String name = game.getString("name");
                                String image = game.getString("image");
                                String release = game.getString("release_date");
                                String trailer = game.getString("trailer");
                                mGames.add(new GameModel(id, name, image, release, trailer,
                                        platforms));
                            }
                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (JSONException e) {
                            // If an error occurs, this prints the error to the log
                            e.printStackTrace();
                        }
                        // Updates pager adapter so that the parsed data can be shown
                        mAdapter.notifyDataSetChanged();
                        mLoading.setVisibility(View.GONE);
                        mPager.setVisibility(View.VISIBLE);
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                        mLoading.setVisibility(View.GONE);

                        AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(MainActivity.this,
                                    android.R.style.Theme_Material_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(MainActivity.this);
                        }
                        builder.setTitle("ERROR FETCHING DATA")
                                .setMessage("Try again?")
                                .setPositiveButton(android.R.string.yes,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                getGames();
                                            }
                                        })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                                        homeIntent.addCategory(Intent.CATEGORY_HOME);
                                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(homeIntent);
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                }
        );
        // Adds the JSON object request "objReq" to the request queue
        requestQueue.add(objReq);
    }
}
