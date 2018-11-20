package com.gdgminna.android.futminnalibraryadmin.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gdgminna.android.futminnalibraryadmin.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;


public class BookedBooksFragment extends BookListFragment {

    public BookedBooksFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // [START recent_posts_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys
        Query recentPostsQuery = databaseReference.child("posts");

        // [END recent_posts_query]

        return recentPostsQuery;
    }
}
