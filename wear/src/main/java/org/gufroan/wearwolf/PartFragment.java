package org.gufroan.wearwolf;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.CardScrollView;
import android.support.wearable.view.GridPageOptions;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.gufroan.wearwolf.data.Part;

import java.util.Random;

public class PartFragment extends CardFragment implements GridPageOptions {
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

        final CardScrollView rootView = ((CardScrollView) container.getParent());
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PartFragment.this.onClick(getActivity(), part, getArguments().getInt(ROW));
            }
        });

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PartFragment.this.onClick(getActivity(), part, getArguments().getInt(ROW));
            }
        });


        return v;
    }

    public static void onClick(Context context, final Part part, int row) {
        NavigationEngine.navigateTo(row);

        if (NavigationEngine.getCurrentItems().size() > 0) {
            final Intent intent = new Intent(context, SentenceActivity.class);
            context.startActivity(intent);
        } else {
            NavigationEngine.goBack();

            Intent wearP2PIntent = new Intent(context, WearP2PService.class);
            wearP2PIntent.setAction(Constants.ACTION_CLICK);
            wearP2PIntent.putExtra(PART, part);

            final Intent intent = new Intent(context, ActionActivity.class);
            intent.putExtra(ActionActivity.INTENT, wearP2PIntent);
            intent.putExtra(ActionActivity.ICON, R.drawable.ic_action_phone);
            context.startActivity(intent);
        }
    }

    @Override
    public Drawable getBackground() {
        String name = NavigationEngine.getCurrentItems().get(getArguments().getInt(ROW)).getStringData();
        name = name.toLowerCase().replace(' ', '_').replaceAll("[^a-z]","");
        int bgID = getResources().getIdentifier(name, "drawable", getActivity().getPackageName());

        Random rnd = new Random();
        int color = Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        Drawable background;

        if (bgID != 0) {
            background = new LayerDrawable(new Drawable[]{
                    new ColorDrawable(color),
                    getResources().getDrawable(bgID)});
        }
        else
            background = new ColorDrawable(color);

        return background;
    }

    @Override
    public void setBackgroundListener(BackgroundListener backgroundListener) {
    }
}
