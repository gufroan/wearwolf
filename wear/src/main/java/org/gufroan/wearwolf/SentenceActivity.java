package org.gufroan.wearwolf;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.GridViewPager;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;

public class SentenceActivity extends Activity {

    private static final String EXTRA_PARTS = "EXTRA_PARTS";

    private GridViewPager partsPager;
    private ArrayList<Part> parts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parts);

        partsPager = (GridViewPager) findViewById(R.id.pager);
        parts = new ArrayList<>();
        partsPager.setAdapter(new PartsPagerAdapter(getFragmentManager()));

    }

    private class PartsPagerAdapter extends FragmentGridPagerAdapter {

        public PartsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getColumnCount(int row) {
            return 3;
        }

        @Override
        public int getRowCount() {
            return parts.size();
        }

        @Override
        public Fragment getFragment(int row, int col) {
            if (col == 0) {
                Part part = parts.get(row);
                CardFragment fragment = PartFragment.create(row, col, part);
                return fragment;
            } else {
                Fragment fragment = new ActionFragmentBuilder(createAppIntent(), getString(R.string.speak), R.drawable.ic_launcher_white).build();
                return fragment;
            }
        }

        @Override
        public ImageReference getBackground(int row, int column) {
            if (column == 0) {
                Part part = parts.get(row);
                return PartFragment.getBackgroundFor(part);
            } else {
                return ImageReference.forDrawable(R.drawable.microphone);
            }
        }

    }

    private Intent createAppIntent() {
        Intent intent = new Intent(this, WearP2PService.class);
        intent.setAction(Constants.ACTION_OPEN_MOBILE_APP);
        return intent;
    }


}
