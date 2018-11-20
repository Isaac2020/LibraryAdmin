package com.gdgminna.android.futminnalibraryadmin.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gdgminna.android.futminnalibraryadmin.R;
import com.gdgminna.android.futminnalibraryadmin.models.User;
import com.gdgminna.android.futminnalibraryadmin.models.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class NewBookActivity extends BaseActivity {

    private static final String TAG = "NewBookActivity";
    private static final String REQUIRED = "Required";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private EditText mTitleField;
    private EditText mAuthorField;
    private EditText mIspnNumberField;
    private EditText mBriefDetailField;
    private Button mSubmitButton;
    private Button mCancelButton;
    private Long mdateAndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_book);

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // [END initialize_database_ref]


        mTitleField = findViewById(R.id.title_field);
        mAuthorField = findViewById(R.id.author_field);
        mIspnNumberField = findViewById(R.id.ispnNumber_field);
        mBriefDetailField = findViewById(R.id.detail_field);
        mCancelButton = findViewById(R.id.cancleBT);
        mSubmitButton = findViewById(R.id.submitBT);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewBookActivity.this, MainActivity.class));
            }
        });
    }
    private void submitPost() {
        final String title = mTitleField.getText().toString();
        final String author = mAuthorField.getText().toString();
        final String ispnNumber = mIspnNumberField.getText().toString();
        final String detail = mBriefDetailField.getText().toString();
        final Long dateAndTime = mdateAndTime;


        // Title is required
        if (TextUtils.isEmpty(title)) {
            mTitleField.setError(REQUIRED);
            return;
        }

        // Author is required
        if (TextUtils.isEmpty(author)) {
            mAuthorField.setError(REQUIRED);
            return;
        }

        // Author is required
        if (TextUtils.isEmpty(ispnNumber)) {
            mBriefDetailField.setError(REQUIRED);
            return;
        }

        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

        // [START single_value_read]
        final String userId = getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);

                        // [START_EXCLUDE]
                        if (user == null) {
                            // User is null, error out
                            Log.e(TAG, "users" + userId + " is unexpectedly null");
                            Toast.makeText(NewBookActivity.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new post
                            writeNewPost(userId, user.username, title, author, ispnNumber, detail, dateAndTime );
                        }

                        // Finish this Activity, back to the stream
                        setEditingEnabled(true);
                        finish();
                        // [END_EXCLUDE]
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        setEditingEnabled(true);
                        // [END_EXCLUDE]
                    }
                });
        // [END single_value_read]
    }

    private void setEditingEnabled(boolean enabled) {
        mTitleField.setEnabled(enabled);
        mAuthorField.setEnabled(enabled);
        mIspnNumberField.setEnabled(enabled);
        mBriefDetailField.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }

    // [START write_fan_out]
    private void writeNewPost(String userId, String username, String title, String author, String ispnNumber, String detail, Long dateAndTime) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("posts").push().getKey();
        Book post = new Book(userId, username, title, author, ispnNumber, detail, dateAndTime );
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/admin-posts/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }

}
