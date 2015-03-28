package org.gufroan.wearwolf.network;

import android.net.Uri;

import org.gufroan.wearwolf.data.pojo.ResponseRoot;

import retrofit.RestAdapter;

/**
 * Network API aggregator.
 * <p/>
 * Created by vitaliyistomov on 25/02/15.
 */
public class NetworkApi {

    /**
     * Singleton instance pointer.
     */
    private static NetworkApi sInstance;

    /**
     * Search results fetched per request.
     */
    public static final int RESULTS_PER_PAGE = 1;

    /**
     * Network API service implementation.
     */
    private final IGoogleImageSearchApiService mGoogleImageSearchApiService;

    /**
     * Private constructor.
     */
    private NetworkApi() {
        final RestAdapter googleImageSearchAdapter = new RestAdapter.Builder()
                .setEndpoint(IGoogleImageSearchApiService.HOST)
                .build();

        mGoogleImageSearchApiService = googleImageSearchAdapter.create(IGoogleImageSearchApiService.class);
    }

    /**
     * @return singleton value
     */
    public static NetworkApi getInstance() {
        if (sInstance == null) {
            sInstance = new NetworkApi();
        }

        return sInstance;
    }

    /**
     * Query search engine for the results and parse results based on user provided query
     * and start position.
     *
     * @param query         to ask search engine
     * @return {@link ResponseRoot} object with the search query results
     */
    public ResponseRoot triggerSearchRequest(final String query) {
        return mGoogleImageSearchApiService.listResults(Uri.encode(query));
    }
}
