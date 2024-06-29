package nlu.hmuaf.android_bookapp.user.profile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.user.profile.classess.Review;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<Review> reviewList;

    public ReviewAdapter(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.titleTextView.setText(review.getTitle());
        holder.contentTextView.setText(review.getContent());
        holder.authorTextView.setText(review.getAuthor());
        holder.bookTitleTextView.setText(review.getBookTitle());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView contentTextView;
        TextView authorTextView;
        TextView bookTitleTextView;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.review_item_title);
            contentTextView = itemView.findViewById(R.id.review_item_content);
            authorTextView = itemView.findViewById(R.id.review_item_author);
            bookTitleTextView = itemView.findViewById(R.id.review_item_book_title);
        }
    }
}
