package com.adi.moviedbstage1.dao;

import java.util.List;

/**
 * Created by user on 7/25/17.
 */

public class ListReviewsDao {
    public int id ;
    public int page ;
    public List<ReviewDao> results ;
    public int total_pages ;
    public int total_results ;
}
