package nlu.hmuaf.android_bookapp.HomeScreen.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import nlu.hmuaf.android_bookapp.HomeScreen.Class.BookB;
import nlu.hmuaf.android_bookapp.R;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {
    private List<BookB> mListBookB;
    private OnItemClickListener listener;

    public PopularAdapter(List<BookB> mListBookB, OnItemClickListener listener) {
        this.mListBookB = mListBookB;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_item, parent, false);
        return new PopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        BookB bookB = mListBookB.get(position);
        if (bookB == null) {
            return;
        }
        holder.imgBookB2.setImageResource(bookB.getResourceid());
        holder.nameB2.setText(bookB.getName());
        holder.priceB2.setText(bookB.getPrice());
    }

    @Override
    public int getItemCount() {
        if (mListBookB != null) {
            return mListBookB.size();
        }
        return 0;
    }

    public class PopularViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgBookB2;
        private TextView nameB2;
        private TextView priceB2;

        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBookB2 = itemView.findViewById(R.id.img_imageB2);
            nameB2 = itemView.findViewById(R.id.tv_nameB2);
            priceB2 = itemView.findViewById(R.id.tv_priceB2);

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
