package org.gufroan.wearwolf.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import org.gufroan.wearwolf.ImageFetchingService;
import org.gufroan.wearwolf.NavigationEngine;
import org.gufroan.wearwolf.R;
import org.gufroan.wearwolf.TextToSpeechService;
import org.gufroan.wearwolf.data.Part;
import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by vitaliyistomov on 28/03/15.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        setActionBar((android.widget.Toolbar) findViewById(R.id.main_toolbar));
        final GridView grid = (GridView) findViewById(R.id.main_grid);
        TypedArray masterArray = getResources().obtainTypedArray(R.array.master);
        if (NavigationEngine.getCurrentItems().size() == 0)
            NavigationEngine.populateList(masterArray, getResources(), null);
        List<Part> navigationTree = NavigationEngine.getCurrentItems();
        grid.setAdapter(new MainGridAdapter(this, navigationTree));
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Part current = NavigationEngine.navigateTo(i);
                List<Part> navigationTree = NavigationEngine.getCurrentItems();
                if (navigationTree.size() == 0) {
                    Intent ttsIntent = new Intent(getApplicationContext(), TextToSpeechService.class);
                    ttsIntent.putExtra("message", current.getStringData());
                    startService(ttsIntent);
                    NavigationEngine.goBack();
                } else {
                    MainGridAdapter mainGridAdapter = ((MainGridAdapter) grid.getAdapter());
                    updateTitle(current.getStringData());
                    mainGridAdapter.updateList(navigationTree);
                    mainGridAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Part parent = NavigationEngine.goBack();
        if (parent == null)
            super.onBackPressed();
        else {
            MainGridAdapter mainGridAdapter = ((MainGridAdapter)
                    ((GridView) findViewById(R.id.main_grid)).getAdapter());
            mainGridAdapter.updateList(NavigationEngine.getCurrentItems());
            mainGridAdapter.notifyDataSetChanged();
            updateTitle(parent.getStringData());
        }
    }

    private void updateTitle(String newTitle){
        if (newTitle.equals("root_element"))
            newTitle = getString(R.string.categories);
        setTitle(newTitle);
    }

}
