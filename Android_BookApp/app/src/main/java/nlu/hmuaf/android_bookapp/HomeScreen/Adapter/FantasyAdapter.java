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
import nlu.hmuaf.android_bookapp.HomeScreen.Class.Author;
import nlu.hmuaf.android_bookapp.HomeScreen.Class.BookB;
import nlu.hmuaf.android_bookapp.R;

public class FantasyAdapter extends RecyclerView.Adapter<FantasyAdapter.FantasyViewHolder> {

    private List<BookB> mListBookB;
    private OnItemClickListener listener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public FantasyAdapter(List<BookB> mListBookB, OnItemClickListener listener) {
        this.mListBookB = mListBookB;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FantasyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fantasy_item, parent, false);
        return new FantasyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FantasyViewHolder holder, int position) {
        BookB bookB = mListBookB.get(position);
        if (bookB == null){
            return;
        }
        holder.imgFantasy.setImageResource(bookB.getResourceid());
        holder.nameFantasy.setText(bookB.getName());
        holder.priceFantasy.setText(bookB.getPrice());
    }

    @Override
    public int getItemCount() {
        if (mListBookB != null) {
            return mListBookB.size();
        }
        return 0;
    }

    public class FantasyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgFantasy;
        private TextView nameFantasy;
        private TextView priceFantasy;

        public FantasyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFantasy = itemView.findViewById(R.id.FantasyItemImageView2);
            nameFantasy = itemView.findViewById(R.id.FantasyItemTextView);
            priceFantasy = itemView.findViewById(R.id.buttonMua);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
