package org.gufroan.wearwolf;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.view.GestureDetector;

import org.gufroan.wearwolf.data.Part;

import java.util.Arrays;
import java.util.List;

public class SentenceActivity extends Activity {

    private static final String EXTRA_PARTS = "EXTRA_PARTS";

    private GridViewPager partsPager;
    private List<Part> parts;
    private GestureDetector mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parts);

        partsPager = (GridViewPager) findViewById(R.id.pager);
        parts = Arrays.asList(new Part(), new Part());
        partsPager.setAdapter(new PartsPagerAdapter(getFragmentManager()));
    }


    private class PartsPagerAdapter extends FragmentGridPagerAdapter {

        public PartsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getColumnCount(int row) {
            return 1;
        }

        @Override
        public int getRowCount() {
            return parts.size();
        }

        @Override
        public Fragment getFragment(int row, int col) {
            Part part = parts.get(row);
            Fragment fragment = PartFragment.create(row, col, part);
            return fragment;

        }
    }
}
