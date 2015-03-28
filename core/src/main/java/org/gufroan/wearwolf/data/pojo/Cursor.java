package org.gufroan.wearwolf.data.pojo;

import java.util.ArrayList;
import java.util.List;

public class Cursor {

    private final List<Page> pages = new ArrayList<Page>();

    /**
     * @return The pages
     */
    public List<Page> getPages() {
        return pages;
    }
}
