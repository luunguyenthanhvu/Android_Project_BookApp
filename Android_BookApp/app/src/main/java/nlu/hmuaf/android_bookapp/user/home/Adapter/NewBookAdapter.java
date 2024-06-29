package nlu.hmuaf.android_bookapp.user.home.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.dto.response.ListBookResponseDTO;
import nlu.hmuaf.android_bookapp.utils.MyUtils;

public class NewBookAdapter extends RecyclerView.Adapter<NewBookAdapter.PopularViewHolder> {
    private List<ListBookResponseDTO> listBook;
    private OnItemClickListener listener;
    private OnPriceClickListener priceClickListener;

    // Interface bắt sự kiện khi nút giá tiền được bấm vào để add vào giỏ hàng
    public interface OnPriceClickListener {
        void onPriceClick(int position);
    }

    public NewBookAdapter(List<ListBookResponseDTO> listBook, OnItemClickListener listener, OnPriceClickListener priceClickListener) {
        this.listBook = listBook != null ? listBook : new ArrayList<>();
        this.listener = listener;
        this.priceClickListener = priceClickListener;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_item, parent, false);
        return new PopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PopularViewHolder holder, int position) {
        ListBookResponseDTO book = listBook.get(position);
        if (book == null) {
            return;
        }
        Picasso.get().load(book.getThumbnail()).into(holder.imgBookB2);
        Picasso.get().load(book.getThumbnail()).into(holder.imgCopyB2);

        holder.nameB2.setText(book.getTitle());
        if (book.getDiscount() != 0.0) {
            double originalPrice = book.getOriginalPrice();
            holder.priceB2.setText(MyUtils.convertToVND(book.getDiscountedPrice()));

            // setting discount
            holder.tvDiscount.setText((int) (book.getDiscount() * 100) + "%");
            holder.tvDiscount.setVisibility(View.VISIBLE);

            // setting originalPrice
            holder.originalPrice.setText(MyUtils.convertToVND(book.getOriginalPrice()));
            holder.originalPrice.setVisibility(View.VISIBLE);
            holder.originalPrice.setPaintFlags(holder.originalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.priceB2.setText(MyUtils.convertToVND(book.getOriginalPrice()));
            holder.tvDiscount.setVisibility(View.GONE);
            holder.originalPrice.setVisibility(View.GONE);
        }

        holder.imgCopyB2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listBook != null) {
            return listBook.size();
        }
        return 0;
    }

    public class PopularViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgBookB2;
        private ImageView imgCopyB2;
        private TextView nameB2, priceB2, tvDiscount, originalPrice;

        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBookB2 = itemView.findViewById(R.id.img_imageB2);
            imgCopyB2 = itemView.findViewById(R.id.img_copyB2);
            nameB2 = itemView.findViewById(R.id.tv_nameB2);
            priceB2 = itemView.findViewById(R.id.tv_priceB2);
            tvDiscount = itemView.findViewById(R.id.tv_discount);
            originalPrice = itemView.findViewById(R.id.originalPrice);

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

            // Nút price giá gốc nhận sự kiện để bỏ vào giỏ hàng
            priceB2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (priceClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            priceClickListener.onPriceClick(position);
                        }
                    }
                }
            });
        }

        public ImageView getImgBook() {
            return imgBookB2;
        }
    }

    public void updateData(List<ListBookResponseDTO> newBookList) {
        this.listBook.clear();
        this.listBook.addAll(newBookList);
        notifyDataSetChanged();
    }
}

