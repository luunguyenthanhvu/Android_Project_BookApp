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

public class OtherAdapter extends RecyclerView.Adapter<OtherAdapter.OtherViewHolder> {

    private Context mContext;
    private List<Integer> mOtherImageIds;
    private List<String> mOtherNames;

    public OtherAdapter(Context context, List<Integer> otherImageIds, List<String> otherNames) {
        mContext = context;
        mOtherImageIds = otherImageIds;
        mOtherNames = otherNames;
    }

    @NonNull
    @Override
    public OtherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.other_item, parent, false);
        return new OtherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OtherViewHolder holder, int position) {
        holder.otherImageView.setImageResource(mOtherImageIds.get(position));
        holder.otherNameTextView.setText(mOtherNames.get(position));

        // Sự kiện onClick cho ImageView
        holder.otherImageView.setOnClickListener(new View.OnClickListener() {
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
        return mOtherImageIds.size();
    }

    public static class OtherViewHolder extends RecyclerView.ViewHolder {
        ImageView otherImageView;
        TextView otherNameTextView;

        public OtherViewHolder(@NonNull View itemView) {
            super(itemView);
            otherImageView = itemView.findViewById(R.id.OtherItemImageView);
            otherNameTextView = itemView.findViewById(R.id.OtherItemTextView);
        }
    }
}
