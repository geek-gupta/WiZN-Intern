package com.company.charnear.taskthreewizn.model;

/**
 * Created by gaurav on 14/4/18.
 */

public class ResultModel {


    private String name,watchers_count,forks_count,html_url;


    public ResultModel(String mRepoTitle, String mStarCount, String mForkCount, String html_url) {
        this.name = mRepoTitle;
        this.watchers_count = mStarCount;
        this.forks_count = mForkCount;
        this.html_url = html_url;
    }

    public String getmRepoTitle() {
        return name;
    }

    public String getHtml_url(){ return  html_url;}



    public String getmStarCount() {
        return watchers_count;
    }



    public String getmForkCount() {
        return forks_count;
    }


}
