package nlu.hmuaf.android_bookapp.user.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nlu.hmuaf.android_bookapp.user.home.classess.BookB;
import nlu.hmuaf.android_bookapp.R;

public class FantasyAdapter extends RecyclerView.Adapter<FantasyAdapter.FantasyViewHolder> {

    private List<BookB> displayListFantasy;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public FantasyAdapter(List<BookB> displayListFantasy, OnItemClickListener listener) {
        this.displayListFantasy = displayListFantasy;
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
        BookB bookB = displayListFantasy.get(position);
        if (bookB == null) {
            return;
        }
        holder.imgFantasy.setImageResource(bookB.getResourceid());
        holder.nameFantasy.setText(bookB.getName());
    }

    @Override
    public int getItemCount() {
        return displayListFantasy != null ? displayListFantasy.size() : 0;
    }

    public void updateList(List<BookB> newList) {
        displayListFantasy.clear();
        displayListFantasy.addAll(newList);
        notifyDataSetChanged();
    }

    public class FantasyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgFantasy;
        private TextView nameFantasy;

        public FantasyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFantasy = itemView.findViewById(R.id.FantasyItemImageView2);
            nameFantasy = itemView.findViewById(R.id.FantasyItemTextView);

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
