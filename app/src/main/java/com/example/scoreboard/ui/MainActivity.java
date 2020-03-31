package com.example.scoreboard.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.StrictMode;

import com.example.scoreboard.R;
import com.example.scoreboard.ui.adapters.TabAdapter;
import com.example.scoreboard.ui.fragments.GameDetailsFragment;
import com.example.scoreboard.ui.fragments.SearchFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public int[] icons= {
            R.drawable.ic_home_blue_24dp,
            R.drawable.ic_search_blue_24dp
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        adapter = new TabAdapter(getSupportFragmentManager());
        //adapter.addFragment(new HomeFragment(), "Home");
        adapter.addFragment(new GameDetailsFragment(), "Home");
        adapter.addFragment(new SearchFragment(), "Search");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager, false);
        tabLayout.setupWithViewPager(viewPager);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        for (int iconsLoad = 0; iconsLoad < tabLayout.getTabCount(); iconsLoad++) {
            tabLayout.getTabAt(iconsLoad).setIcon(icons[iconsLoad]);
        }
    }

    @Override
    public void onBackPressed() {
        //Do nothing (disabled bak button)
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    public void switchToHome(int fragment) {
        try {
            if (viewPager.getCurrentItem() != fragment)
                viewPager.setCurrentItem(fragment);
        } catch (Exception e) {

        }

    }
}
