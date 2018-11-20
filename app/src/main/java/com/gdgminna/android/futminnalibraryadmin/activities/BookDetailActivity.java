package com.gdgminna.android.futminnalibraryadmin.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gdgminna.android.futminnalibraryadmin.R;
import com.gdgminna.android.futminnalibraryadmin.models.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookDetailActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "BookDetailActivity";
    public static final String EXTRA_POST_KEY = "post_key";

    private DatabaseReference mPostReference;
    private ValueEventListener mPostListener;
    private String mPostKey;

    private TextView mPostedByField;
    private TextView mAuthorView;
    private TextView mTitleView;
    private TextView mIspnNumberView;
    private TextView mDetail;
    private TextView mDateAndTime;
    public TextView mNumStarsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPostKey = getIntent().getStringExtra(EXTRA_POST_KEY);
        if (mPostKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_POST_KEY");
        }

        // Initialize Database
        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("posts").child(mPostKey);


        // Initialize Views
        mPostedByField = findViewById(R.id.posted_by_Tv);
        mAuthorView = findViewById(R.id.authorTv);
        mTitleView = findViewById(R.id.titleTv);
        mIspnNumberView = findViewById(R.id.ispnTv);
        mDetail = findViewById(R.id.detail);
        mDateAndTime = findViewById(R.id.dateAndTimeTV);
        mNumStarsView = findViewById(R.id.post_num_stars);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Add value event listener to the post
        // [START post_value_event_listener]
        ValueEventListener postListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Book post = dataSnapshot.getValue(Book.class);
                // [START_EXCLUDE]
                mPostedByField.setText(post.postedBy);
                mAuthorView.setText(post.author);
                mTitleView.setText(post.title);
                mIspnNumberView.setText(post.ispnNumber);
                mDetail.setText(post.detail);
                mDateAndTime.setText(String.valueOf(post.dateAndTime));
                mNumStarsView.setText(String.valueOf(post.starCount));
                // [END_EXCLUDE]
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(BookDetailActivity.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        mPostReference.addValueEventListener(postListener);
        // [END post_value_event_listener]

        // Keep copy of post listener so we can remove it when app stops
        mPostListener = postListener;
    }

    @Override
    public void onStop() {
        super.onStop();

        // Remove post value event listener
        if (mPostListener != null) {
            mPostReference.removeEventListener(mPostListener);
        }
    }

    @Override
    public void onClick(View view) {

    }
}

