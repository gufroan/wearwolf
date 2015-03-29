package org.gufroan.wearwolf.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import org.gufroan.wearwolf.NavigationEngine;
import org.gufroan.wearwolf.R;
import org.gufroan.wearwolf.TextToSpeechService;
import org.gufroan.wearwolf.data.Part;

import java.util.List;

/**
 * Created by vitaliyistomov on 28/03/15.
 */
public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        final GridView grid = (GridView) findViewById(R.id.main_grid);
        if (NavigationEngine.getCurrentItems().size() == 0) {
            NavigationEngine.populateList(this);
        }

        final List<Part> navigationTree = NavigationEngine.getCurrentItems();
        grid.setAdapter(new MainGridAdapter(this, navigationTree));
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Part current = NavigationEngine.navigateTo(i);
                final List<Part> navigationTree = NavigationEngine.getCurrentItems();
                if (navigationTree.size() == 0) {
                    final Intent ttsIntent = new Intent(getApplicationContext(), TextToSpeechService.class);
                    ttsIntent.putExtra("message", current.getStringData());
                    startService(ttsIntent);
                    NavigationEngine.goBack();
                } else {
                    final MainGridAdapter mainGridAdapter = (MainGridAdapter) grid.getAdapter();
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
        if (parent == null) {
            super.onBackPressed();
        } else {
            final MainGridAdapter mainGridAdapter = (MainGridAdapter) ((GridView) findViewById(R.id.main_grid)).getAdapter();
            mainGridAdapter.updateList(NavigationEngine.getCurrentItems());
            mainGridAdapter.notifyDataSetChanged();
            updateTitle(parent.getStringData());
        }
    }

    private void updateTitle(String newTitle) {
        if (newTitle.equals("root_element")) {
            newTitle = getString(R.string.categories);
        }

        setTitle(newTitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result;
        switch (item.getItemId()) {
            case R.id.action_add_item:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setView(R.layout.add_custom_sentence)
                        .setTitle(R.string.custom_sentence)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                final AlertDialog ad = (AlertDialog) dialog;
                                final String newSentence = ((EditText) ad.findViewById(R.id.text1)).getText().toString().trim();
                                NavigationEngine.writeCustomElementToFileStorage(MainActivity.this, newSentence);
                                NavigationEngine.addToCurrentNode(newSentence);
                                final GridView grid = (GridView) findViewById(R.id.main_grid);
                                final List<Part> parts = NavigationEngine.getCurrentItems();
                                ((MainGridAdapter) grid.getAdapter()).updateList(parts);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                dialog.cancel();
                            }
                        });
                builder.show();
                result = true;
                break;
            default:
                result = super.onOptionsItemSelected(item);
                break;
        }

        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
