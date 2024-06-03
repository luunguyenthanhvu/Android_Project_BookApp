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

import nlu.hmuaf.android_bookapp.HomeScreen.Activity.BookActivity;
import nlu.hmuaf.android_bookapp.R;

public class FantasyAdapter extends RecyclerView.Adapter<FantasyAdapter.FantasyViewHolder> {

    private Context mContext;
    private List<Integer> mFantasyImageIds;
    private List<String> mFantasyNames;

    public FantasyAdapter(Context context, List<Integer> fantasyImageIds, List<String> fantasyNames) {
        mContext = context;
        mFantasyImageIds = fantasyImageIds;
        mFantasyNames = fantasyNames;
    }

    @NonNull
    @Override
    public FantasyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fantasy_item, parent, false);
        return new FantasyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FantasyViewHolder holder, int position) {
        int imageId = mFantasyImageIds.get(position);
        String name = mFantasyNames.get(position);

        holder.imageView.setImageResource(imageId);
        holder.textView.setText(name);

        // Sự kiện onClick cho item
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng tới BookActivity khi nhấn vào item
                Intent intent = new Intent(mContext, BookActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFantasyImageIds.size();
    }

    public static class FantasyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public FantasyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.FantasyItemImageView);
            textView = itemView.findViewById(R.id.FantasyItemTextView);
        }
    }
}
