package com.example.kremik.moododroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kremik.moododroid.MoodDB.MoodContract;

public class AddMoodActivity extends AppCompatActivity {

    private static final String TAG = "AddMoodActivity";

    ImageButton mVerySadButton;
    ImageButton mSadButton;
    ImageButton mNeutralButton;
    ImageButton mHappyButton;
    ImageButton mVeryHappyButton;
    Button mAddMoodTagsButton;
    Button mAddMoodButton;
    Button mAddMoodTagButton;
    TextView mTagsTextView;
    EditText mTagsEditText;
    String mTags;
    CardView mCardView;
    TextView mTextView;
    MoodLog mMoodLog;
    String mString;
    int mMood;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: Starting.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mood);
        mMoodLog = new MoodLog();
        mTags = "";

        mCardView = (CardView) findViewById(R.id.card_view);
        mCardView.setVisibility(View.INVISIBLE);
        mVerySadButton = (ImageButton) findViewById(R.id.very_sad_mood_button);
        mVerySadButton.setOnClickListener((View v) -> mMood = MoodContract.MOOD_VERY_SAD);
        mSadButton = (ImageButton) findViewById(R.id.sad_mood_button);
        mSadButton.setOnClickListener((View v) -> mMood = MoodContract.MOOD_SAD);
        mNeutralButton = (ImageButton) findViewById(R.id.neutral_mood_button);
        mNeutralButton.setOnClickListener((View v) -> mMood = MoodContract.MOOD_NEUTRAL);
        mHappyButton = (ImageButton) findViewById(R.id.happy_mood_button);
        mHappyButton.setOnClickListener((View v) -> mMood = MoodContract.MOOD_HAPPY);
        mVeryHappyButton = (ImageButton) findViewById(R.id.very_happy_mood_button);
        mVeryHappyButton.setOnClickListener((View v) -> mMood = MoodContract.MOOD_VERY_HAPPY);
        mTextView = (TextView) findViewById(R.id.text_view) ;

        mAddMoodTagsButton = (Button) findViewById(R.id.add_mood_tags_button);
        mAddMoodTagsButton.setOnClickListener((View v) -> {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddMoodActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.dialog_add_mood_tags, null);
            mBuilder.setView(mView);
            AlertDialog addTagsDialog = mBuilder.create();
            addTagsDialog.show();
            mTagsTextView = (TextView) mView.findViewById(R.id.tvTags);
            mTagsEditText = (EditText) mView.findViewById(R.id.tags_edit_text);
            mTagsEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mString = s.toString();
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            mAddMoodTagButton = (Button) mView.findViewById(R.id.add_tag_button);
            mAddMoodTagButton.setOnClickListener((View view) -> {
                mTags +=  "#" + mString + " ";
                mTagsTextView.setText(mTags);
                mTagsEditText.setText("");
                mCardView.setVisibility(View.VISIBLE);
                mTextView.setText(mTags);
            });
        });
        mAddMoodButton = (Button) findViewById(R.id.add_mood_button);
        mAddMoodButton.setOnClickListener((View v) -> {
            mMoodLog.setMood(mMood);
            mMoodLog.setTags(mTags);
            MoodLab.getInstance(this).addMoodLog(mMoodLog);
            this.onBackPressed();
        });
    }
}
