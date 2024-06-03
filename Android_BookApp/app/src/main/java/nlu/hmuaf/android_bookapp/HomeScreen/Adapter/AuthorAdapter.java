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

public class AuthorAdapter extends RecyclerView.Adapter<AuthorAdapter.AuthorViewHolder> {

    private Context mContext;
    private List<Integer> mAuthorImageIds;
    private List<String> mAuthorNames;

    public AuthorAdapter(Context context, List<Integer> authorImageIds, List<String> authorNames) {
        mContext = context;
        mAuthorImageIds = authorImageIds;
        mAuthorNames = authorNames;
    }

    @NonNull
    @Override
    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.author_item, parent, false);
        return new AuthorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder holder, int position) {
        holder.imageView.setImageResource(mAuthorImageIds.get(position));
        holder.textView.setText(mAuthorNames.get(position));

        // Sự kiện onClick cho ImageView
        holder.imageView.setOnClickListener(new View.OnClickListener() {
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
        return mAuthorImageIds.size();
    }

    public static class AuthorViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.AuthorItemImageView);
            textView = itemView.findViewById(R.id.AuthorItemTextView);
        }
    }
}
