package com.academy.fundamentals.moviesapp;


import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class DetailsActivity extends FragmentActivity {
    int movieIndex;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    ScreenSlideMoviesFragment movieFragment;
    ScreenSlideMoviesFragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        movieIndex = getIntent().getIntExtra("position", 0);
        viewPager = (ViewPager) findViewById(R.id.movies_pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(movieIndex);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            currentFragment = ((ScreenSlideMoviesFragment) object);
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("movie", MoviesActivity.movies.get(position));
            movieFragment = new ScreenSlideMoviesFragment();
            movieFragment.setArguments(bundle);
            Log.d("Ron - ", MoviesActivity.movies.get(position).getName());
            return movieFragment;
        }

        @Override
        public int getCount() {
            return MoviesActivity.movies.size();
        }
    }

    public void movieTrailerOnClick(View view) {
        currentFragment.movieTrailerOnClick(view);
    }
}
