package org.gufroan.wearwolf.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.gufroan.wearwolf.R;
import org.gufroan.wearwolf.data.Part;

import java.util.List;
import java.util.Random;

/**
 * This file is part of wearwolf
 * Created by geecko on 28/03/15.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class MainGridAdapter extends BaseAdapter {

    private List<Part> mParts;
    Context mContext;

    public MainGridAdapter(Context c, List<Part> parts) {
        mContext = c;
        mParts = parts;
    }

    public void updateList(List<Part> parts) {
        this.mParts = parts;
    }

    @Override
    public int getCount() {
        return mParts.size();
    }

    @Override
    public Object getItem(int position) {
        return mParts.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View card, ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        final String label = mParts.get(position).getStringData();
        if (card == null) {
            card = inflater.inflate(R.layout.card_menu, parent, false);
        }

        ((TextView) card.findViewWithTag("card_tag")).setText(label);
        final int backgroundId = mContext.getResources().getIdentifier(label, null, null);
        if (backgroundId == 0) {
            Random rnd = new Random();
            // 150 alpha would make colors more soft
            int color = Color.argb(150, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            ((CardView) card).getChildAt(0).setBackgroundColor(color);
        } else {
            ((CardView) card).getChildAt(0).setBackgroundResource(backgroundId);
        }

        return card;
    }

}