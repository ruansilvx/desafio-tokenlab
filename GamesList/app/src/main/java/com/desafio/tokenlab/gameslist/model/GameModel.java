package com.desafio.tokenlab.gameslist.model;

import java.util.List;

public class GameModel {
    private String mId;
    private String mName;
    private String mImagename;
    private String mReleaseDate;
    private String mTrailerUrl;
    private List<String> mPlatforms;

    public GameModel(String id, String name, String imageName, String releaseDate, String trailerUrl,
                     List<String> platforms) {
        this.setId(id);
        this.setName(name);
        this.setImagename(imageName);
        this.setReleaseDate(releaseDate);
        this.setTrailerUrl(trailerUrl);
        this.setPlatforms(platforms);
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getImagename() {
        return mImagename;
    }

    public void setImagename(String mImagename) {
        this.mImagename = mImagename;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public String getTrailerUrl() {
        return mTrailerUrl;
    }

    public void setTrailerUrl(String mTrailerUrl) {
        this.mTrailerUrl = mTrailerUrl;
    }

    public List<String> getPlatforms() {
        return mPlatforms;
    }

    public void setPlatforms(List<String> mPlatforms) {
        this.mPlatforms = mPlatforms;
    }
}
