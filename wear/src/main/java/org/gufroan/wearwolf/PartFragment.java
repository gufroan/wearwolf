package org.gufroan.wearwolf;

import android.app.Fragment;
import android.content.Intent;
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
    public static final String PART = "PART";

    public static Fragment create(final int row, final int col, final Part part) {
        final PartFragment fragment = new PartFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ROW, row);
        bundle.putInt(COL, col);
        bundle.putParcelable(PART, part);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.part_item, null);
        final TextView partView = (TextView) v.findViewById(R.id.part);

        final Part part = getArguments().getParcelable(PART);
        partView.setText(part.getStringData());
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if (NavigationEngine.getCurrentItems().size() > 0) {
                    final Intent intent = new Intent(getActivity(), SentenceActivity.class);
                    NavigationEngine.navigateTo(getArguments().getInt(ROW));
                    getActivity().startActivity(intent);
                } else {
                    final Intent intent = new Intent(getActivity(), ActionActivity.class);
                    getActivity().startActivity(intent);

                    Intent wearP2PIntent = new Intent(getActivity(), WearP2PService.class);
                    wearP2PIntent.setAction(Constants.ACTION_CLICK);
                    wearP2PIntent.putExtra(PART, part);
                    getActivity().startService(wearP2PIntent);
                }
            }
        });

        return v;
    }

}
