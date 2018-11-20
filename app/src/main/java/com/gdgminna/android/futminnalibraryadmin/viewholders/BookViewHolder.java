package com.gdgminna.android.futminnalibraryadmin.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gdgminna.android.futminnalibraryadmin.R;
import com.gdgminna.android.futminnalibraryadmin.models.Book;

public class BookViewHolder extends RecyclerView.ViewHolder {

    public TextView postedByField;
    public TextView titleView;
    public TextView authorView;
    public TextView ispnView;
    public TextView dateAndTimeView;
    public ImageView starView;
    public TextView numStarsView;


    public BookViewHolder(View itemView) {
        super(itemView);

        postedByField = itemView.findViewById(R.id.posted_by_Tv);
        titleView = itemView.findViewById(R.id.titleTv);
        authorView = itemView.findViewById(R.id.authorTv);
        ispnView = itemView.findViewById(R.id.ispnTv);
        dateAndTimeView = itemView.findViewById(R.id.dateAndTimeTV);
        starView = itemView.findViewById(R.id.star);
        numStarsView = itemView.findViewById(R.id.post_num_stars);
    }

    public void bindToPost(Book book, View.OnClickListener starClickListener) {

        postedByField.setText(book.postedBy);
        titleView.setText(book.title);
        authorView.setText(book.author);
        ispnView.setText(book.ispnNumber);
        dateAndTimeView.setText(String.valueOf(book.dateAndTime));
        numStarsView.setText(String.valueOf(book.starCount));


        starView.setOnClickListener(starClickListener);
    }
}
