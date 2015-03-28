package org.gufroan.wearwolf.data.pojo;

import java.util.ArrayList;
import java.util.List;

public class ResponseData {

    private final List<Result> results = new ArrayList<Result>();
    private Cursor cursor;

    /**
     * @return The results
     */
    public List<Result> getResults() {
        return results;
    }

    /**
     * @return The cursor
     */
    public Cursor getCursor() {
        return cursor;
    }
}