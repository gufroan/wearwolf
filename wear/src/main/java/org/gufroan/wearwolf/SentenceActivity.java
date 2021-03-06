package org.gufroan.wearwolf;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;

import org.gufroan.wearwolf.data.Part;

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

        NavigationEngine.populateList(this);
        parts = NavigationEngine.getCurrentItems();
        final PartsPagerAdapter adapter = new PartsPagerAdapter(getFragmentManager());
        partsPager.setAdapter(adapter);
        partsPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d("CLICK", "here"  + v);

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            NavigationEngine.goBack();
        }
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
