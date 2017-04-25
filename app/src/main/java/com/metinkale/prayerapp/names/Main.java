/*
 * Copyright (c) 2016 Metin Kale
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.metinkale.prayerapp.names;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

import com.metinkale.prayer.R;
import com.metinkale.prayerapp.BaseActivity;
import com.metinkale.prayerapp.names.Adapter.Item;
import com.metinkale.prayerapp.utils.Utils;

import net.steamcrafted.materialiconlib.MaterialMenuInflater;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main extends BaseActivity implements OnQueryTextListener {

    private RecyclerView mRecyclerView;
    private Item[] mValues;
    private GridLayoutManager mLayoutManager;
    private int mCols;

    @SuppressLint("NewApi")
    private static String normalize(CharSequence str) {
        String string = Normalizer.normalize(str, Normalizer.Form.NFD);
        string = string.replaceAll("[^\\p{ASCII}]", "");
        return string.toLowerCase(Locale.ENGLISH);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.names_main);

        mRecyclerView = (RecyclerView) findViewById(android.R.id.list);
        DisplayMetrics dimension = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dimension);

        float w = Utils.convertPixelsToDp(dimension.widthPixels, this);

        mCols = (int) (w / 300);
        mLayoutManager = new GridLayoutManager(this, mCols);

        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? mCols : 1;
            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);
        mValues = new Item[99];

        String[] ar = getResources().getStringArray(R.array.names_ar);
        String[] name = getResources().getStringArray(R.array.names_name);
        String[] desc = getResources().getStringArray(R.array.names_desc);

        for (int i = 0; i < 99; i++) {
            Item item = new Item();
            item.arabic = ar[i];
            if (name.length > i) item.name = name[i];
            if (desc.length > i) item.desc = desc[i];
            mValues[i] = item;
        }
        mRecyclerView.setAdapter(new Adapter(this, mValues, mCols > 1));
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MaterialMenuInflater.with(this)
                .setDefaultColor(0xFFFFFFFF)
                .inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<Item> values = new ArrayList<>();
        for (Item val : this.mValues) {
            if (normalize(val.toString()).contains(normalize(newText))) {
                values.add(val);
            }
        }

        mRecyclerView.setAdapter(new Adapter(this, values.toArray(new Item[values.size()]), mCols > 1));
        return false;
    }


}
