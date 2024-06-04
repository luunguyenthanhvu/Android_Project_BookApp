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
import java.util.ArrayList;

import nlu.hmuaf.android_bookapp.HomeScreen.Activity.BookActivity;
import nlu.hmuaf.android_bookapp.R;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<String> mBookNames;
    private ArrayList<Integer> mBookImageIds; // Giả sử mỗi cuốn sách có một hình ảnh liên kết với nó

    public LibraryAdapter(Context context, ArrayList<String> bookNames, ArrayList<Integer> bookImageIds) {
        this.mContext = context;
        this.mBookNames = bookNames;
        this.mBookImageIds = bookImageIds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bookImageView.setImageResource(mBookImageIds.get(position));
        holder.bookNameTextView.setText(mBookNames.get(position));

        // Sự kiện onClick cho ImageView
        holder.bookImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng tới BookActivity khi nhấn vào hình ảnh
                Intent intent = new Intent(mContext, BookActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBookNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bookImageView;
        TextView bookNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            bookImageView = itemView.findViewById(R.id.imageView2);
            bookNameTextView = itemView.findViewById(R.id.BookName);
        }
    }
}
