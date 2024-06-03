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

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {
    private Context mContext;
    private List<Integer> mImageIds;
    private List<String> mTexts;

    public PopularAdapter(Context context, List<Integer> imageIds, List<String> texts) {
        mContext = context;
        mImageIds = imageIds;
        mTexts = texts;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popular_item, parent, false);
        return new PopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        int imageId = mImageIds.get(position);

        holder.imageView.setImageResource(imageId);

        // Sự kiện onClick cho ImageView
        holder.imageView.setOnClickListener(new View.OnClickListener() {
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
        return mImageIds.size();
    }

    public static class PopularViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.popularItemImageView);
            textView = itemView.findViewById(R.id.popularItemTextView);
        }
    }
}
