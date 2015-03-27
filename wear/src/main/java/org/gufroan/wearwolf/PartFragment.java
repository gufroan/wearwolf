package org.gufroan.wearwolf;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PartFragment extends CardFragment {
    private static final String ROW = "ROW";
    private static final String COL = "COL";
    private static final String EXTRA_PART = "PART";

    public static Fragment create(final int row, final int col, final Part part) {
        final PartFragment fragment = new PartFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ROW, row);
        bundle.putInt(COL, col);
        bundle.putParcelable(EXTRA_PART, null);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.part_item, null);
        final TextView partView = (TextView) v.findViewById(R.id.part);

        partView.setText("Hello");
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent intent = new Intent(getActivity(), SentenceActivity.class);
                getActivity().startActivity(intent);
            }
        });

        return v;
    }

}
