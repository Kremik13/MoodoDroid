package com.example.kremik.moododroid;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class WeekMoodFragment extends Fragment {

    private static final String TAG = "WeekMoodFragment";
    private ImageView mWeekImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week_mood, container, false);

        mWeekImage = (ImageView) view.findViewById(R.id.week_mood_pic);
        setWeekMood();
        Log.d(TAG, "onCreate: Starting.");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setWeekMood();
    }

    private void setWeekMood() {
        int weekMood = MoodLab.getInstance(getActivity()).getWeekMood();
        int moodDrawable =  MoodLab.getInstance(getActivity()).getMoodEmojiId(weekMood);
        Drawable weekMoodDrawable = ContextCompat.getDrawable(getActivity(), moodDrawable);
        mWeekImage.setImageDrawable(weekMoodDrawable);
    }
}
