package com.example.scoreboard.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.scoreboard.R;
import com.example.scoreboard.ui.adapters.TabAdapter;
import com.example.scoreboard.ui.fragments.GameDetailsFragment;
import com.example.scoreboard.ui.fragments.SearchFragment;
import com.example.scoreboard.utility.HideSoftKeyUtility;
import com.example.scoreboard.viewModel.ScoreDetailsViewModel;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ScoreDetailsViewModel scoreDetailsViewModel;

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
        setupSoftKeyBoard(viewPager);
        scoreDetailsViewModel = ViewModelProviders.of(this).get(ScoreDetailsViewModel.class);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        for (int iconsLoad = 0; iconsLoad < tabLayout.getTabCount(); iconsLoad++) {
            tabLayout.getTabAt(iconsLoad).setIcon(icons[iconsLoad]);
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("Search")) {
                    //TODO
                    scoreDetailsViewModel.setTeamToNull();

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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

    public void setupSoftKeyBoard(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    HideSoftKeyUtility.hideSoftKeyboard(MainActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupSoftKeyBoard(innerView);
            }
        }
    }


}
