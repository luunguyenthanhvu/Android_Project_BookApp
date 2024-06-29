package nlu.hmuaf.android_bookapp.user.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import nlu.hmuaf.android_bookapp.user.home.classess.Publisher;
import nlu.hmuaf.android_bookapp.R;

public class OtherAdapter extends RecyclerView.Adapter<OtherAdapter.OtherViewHolder> {

    private List<Publisher> mListPublisher;
    private OnItemClickListener listener;
    private List<Publisher> displayList;
    private static final int ITEMS_PER_PAGE = 10; // Số mục trên mỗi trang

    public OtherAdapter(List<Publisher> mListPublisher, OnItemClickListener listener) {
        this.mListPublisher = mListPublisher;
        this.listener = listener;
        this.displayList = new ArrayList<>();
        updateDisplayList(0); // Khởi tạo với trang đầu tiên
    }

    public void updateDisplayList(int page) {
        int start = page * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, mListPublisher.size());
        displayList.clear();
        displayList.addAll(mListPublisher.subList(start, end));
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OtherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.other_item, parent, false);
        return new OtherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OtherViewHolder holder, int position) {
        Publisher publisher = displayList.get(position);
        if (publisher == null) {
            return;
        }
        holder.imgBookB.setImageResource(publisher.getResourceid());
        holder.nameB.setText(publisher.getName());
        holder.hotLine.setText(String.valueOf(publisher.getHotLine()));
        holder.address.setText(publisher.getAddress());
    }

    @Override
    public int getItemCount() {
        return displayList.size();
    }

    public class OtherViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgBookB;
        private TextView nameB;
        private TextView hotLine;
        private TextView address;

        public OtherViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBookB = itemView.findViewById(R.id.OtherItemImageView);
            nameB = itemView.findViewById(R.id.OtherItemTextView);
            hotLine = itemView.findViewById(R.id.tv_hl);
            address = itemView.findViewById(R.id.tv_address);

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
