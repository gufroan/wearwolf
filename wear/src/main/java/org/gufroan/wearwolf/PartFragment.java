package org.gufroan.wearwolf;

import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.gufroan.wearwolf.data.Part;

public class PartFragment extends CardFragment {
    private static final String ROW = "ROW";
    private static final String COL = "COL";
    private static final String EXTRA_PART = "PART";

    public static CardFragment create(final int row, final int col, final Part part) {
        final PartFragment fragment = new PartFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ROW, row);
        bundle.putInt(COL, col);
        bundle.putParcelable(EXTRA_PART, null);
        return fragment;
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.part_item, null);
        final TextView partView = (TextView) v.findViewById(R.id.part);

        partView.setText("Hello");

        return v;
    }

}
