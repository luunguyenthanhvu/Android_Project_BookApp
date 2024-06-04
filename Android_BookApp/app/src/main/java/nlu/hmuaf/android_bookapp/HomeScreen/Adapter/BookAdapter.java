package nlu.hmuaf.android_bookapp.HomeScreen.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import nlu.hmuaf.android_bookapp.HomeScreen.Activity.FantasyActivity;
import nlu.hmuaf.android_bookapp.R;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private Context mContext;
    private List<Integer> mBookImageIds;
    private List<String> mBookNames;

    public BookAdapter(Context context, List<Integer> bookImageIds, List<String> bookNames) {
        mContext = context;
        mBookImageIds = bookImageIds;
        mBookNames = bookNames;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.bookname_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.bookImageView.setImageResource(mBookImageIds.get(position));
        holder.bookNameTextView.setText(mBookNames.get(position));

        // Sự kiện onClick cho ImageView
        holder.bookImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng tới BookActivity khi nhấn vào hình ảnh
                Intent intent = new Intent(mContext, FantasyActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBookImageIds.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView bookImageView;
        TextView bookNameTextView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookImageView = itemView.findViewById(R.id.BookItemImageView);
            bookNameTextView = itemView.findViewById(R.id.BookItemTextView);
        }
    }
}
