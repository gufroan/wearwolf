package org.gufroan.wearwolf.network;


import org.gufroan.wearwolf.data.pojo.ResponseRoot;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Network service implementation for Google Image Search API for Retrofit framework.
 * <p/>
 * Created by vitaliyistomov on 25/02/15.
 */
public interface IGoogleImageSearchApiService {

    /**
     * Google Image Search API URL.
     */
    final String HOST = "https://ajax.googleapis.com";

    /**
     * Query to Google Image Search API. Asking for xlarge images to to have high enough resolution.
     *
     * @param query         to ask search engine
     * @return {@link ResponseRoot} object with the search query results
     */
    @GET("/ajax/services/search/images?v=1.0&rsz=" + NetworkApi.RESULTS_PER_PAGE + "&imgsz=small")
    ResponseRoot listResults(@Query("q") String query, @Query("start") int startPosition);
}
