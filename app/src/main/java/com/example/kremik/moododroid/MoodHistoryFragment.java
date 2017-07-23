package com.example.kremik.moododroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

public class MoodHistoryFragment extends Fragment {

    private static final String TAG = "MoodHistoryFragment";

    RecyclerView mRecyclerView;
    private MoodAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mood_history, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        updateUI();
        Log.d(TAG, "onCreate: Starting.");
        return view;
    }

    private class MoodHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        private TextView mDateTextView;
        private TextView mTagsTextView;
        private ImageView mMoodImageView;
        private MoodLog mMoodLog;

        public MoodHolder(View view) {
            super(view);
            itemView.setOnLongClickListener(this);
            mDateTextView = (TextView) view.findViewById(R.id.list_item_mood_date_text_view);
            mTagsTextView = (TextView) view.findViewById(R.id.list_item_mood_tags_text_view);
            mMoodImageView = (ImageView) view.findViewById(R.id.list_item_mood_image_view);
        }

        public void bindMoodLog(MoodLog moodLog) {
            mMoodLog = moodLog;
            String sDate = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss")
                    .format(mMoodLog.getDate());
            mDateTextView.setText(sDate);
            mTagsTextView.setText(mMoodLog.getTags());
            mMoodImageView.setImageDrawable(
                        ContextCompat.getDrawable(
                            getActivity(),
                            MoodLab.getInstance(getActivity()).getMoodEmojiId(mMoodLog.getMood()))
            );
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    private class MoodAdapter extends RecyclerView.Adapter<MoodHolder> {
        private List<MoodLog> mMoodLogs;
        public MoodAdapter(List<MoodLog> moodLogs) {
            mMoodLogs = moodLogs;
        }

        @Override
        public MoodHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(
                    R.layout.list_item,
                    parent, false);
            return new MoodHolder(view);
        }

        @Override
        public void onBindViewHolder(MoodHolder holder, int position) {
            MoodLog moodLog = mMoodLogs.get(position);
            holder.bindMoodLog(moodLog);
        }

        @Override
        public int getItemCount() {
            return mMoodLogs.size();
        }
    }

    private void updateUI() {
        MoodLab moodLab = MoodLab.getInstance(getActivity());
        List<MoodLog> moodLogs = moodLab.getMoodLogs();

        mAdapter = new MoodAdapter(moodLogs);
        mRecyclerView.setAdapter(mAdapter);
    }
}
