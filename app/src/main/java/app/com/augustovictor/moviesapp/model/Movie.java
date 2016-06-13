package app.com.augustovictor.moviesapp.model;

import java.io.Serializable;

/**
 * Created by victoraweb on 6/7/16.
 */
public class Movie implements Serializable{

    private int mId;

    private String
            mTitle,
            mOverview,
            mPoster,
            mBackdropPath,
            mLanguage,
            mReleaseDate;

    private int mVotes;

    private double mVotesAvg;

//    private Date mReleaseDate;

    private boolean
            mAdult,
            mHasVideo;

    public int getmId() {
        return mId;
    }

    public void setmId(int id) {
        this.mId= id;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmOverview() {
        return mOverview;
    }

    public void setmOverview(String mOverview) {
        this.mOverview = mOverview;
    }

    public String getmPoster() {
        return mPoster;
    }

    public void setmPoster(String mPoster) {
        this.mPoster = "http://image.tmdb.org/t/p/w300" + mPoster;
    }

    public String getmBackdropPath() {
        return mBackdropPath;
    }

    public void setmBackdropPath(String mBackdropPath) {
        this.mBackdropPath = "http://image.tmdb.org/t/p/w600" + mBackdropPath;
    }

    public String getmLanguage() {
        return mLanguage;
    }

    public void setmLanguage(String mLanguage) {
        this.mLanguage = mLanguage;
    }

    public int getmVotes() {
        return mVotes;
    }

    public void setmVotes(int mVotes) {
        this.mVotes = mVotes;
    }

    public double getmVotesAvg() {
        return mVotesAvg;
    }

    public void setmVotesAvg(double mVotesAvg) {
        this.mVotesAvg = mVotesAvg;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public void setmReleaseDate(String mReleaseDate) {
        if (mReleaseDate.equals("")) {
            this.mReleaseDate = "Release date not available";
        } else {
            this.mReleaseDate = mReleaseDate;
        }
    }

    public boolean ismAdult() {
        return mAdult;
    }

    public void setmAdult(boolean mAdult) {
        this.mAdult = mAdult;
    }

    public boolean ismHasVideo() {
        return mHasVideo;
    }

    public void setmHasVideo(boolean mHasVideo) {
        this.mHasVideo = mHasVideo;
    }

    private String formatImageUrl(String urlPath) {
        return "http://image.tmdb.org/t/p/w300/" + urlPath;
    }
}
