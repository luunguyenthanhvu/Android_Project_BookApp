package nlu.hmuaf.android_bookapp.user.home.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import nlu.hmuaf.android_bookapp.user.home.Class.BookB;
import nlu.hmuaf.android_bookapp.R;

public class BookMAdapter extends RecyclerView.Adapter<BookMAdapter.NSXViewHolder> {

    private List<BookB> mListBookB;
    private OnItemClickListener listener;
    private List<BookB> displayList;
    private static final int ITEMS_PER_PAGE = 10; // Số mục trên mỗi trang

    public BookMAdapter(List<BookB> mListBookB, OnItemClickListener listener) {
        this.mListBookB = mListBookB;
        this.listener = listener;
        this.displayList = new ArrayList<>();
        updateDisplayList(0); // Khởi tạo với trang đầu tiên
    }

    public void updateDisplayList(int page) {
        int start = page * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, mListBookB.size());
        displayList.clear();
        displayList.addAll(mListBookB.subList(start, end));
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookMAdapter.NSXViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookname_item, parent, false);
        return new BookMAdapter.NSXViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookMAdapter.NSXViewHolder holder, int position) {
        BookB bookB = displayList.get(position);
        if (bookB == null) {
            return;
        }
        holder.imgBookB.setImageResource(bookB.getResourceid());
        holder.nameB.setText(bookB.getName());
    }

    @Override
    public int getItemCount() {
        return displayList.size();
    }

    public class NSXViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgBookB;
        private TextView nameB;

        public NSXViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBookB = itemView.findViewById(R.id.BookItemImageView);
            nameB = itemView.findViewById(R.id.BookItemTextView);

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
