package nlu.hmuaf.android_bookapp.HomeScreen.Adapter;

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

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {
    private List<ListBookResponseDTO> listBook;
    private OnItemClickListener listener;

    public PopularAdapter(List<ListBookResponseDTO> listBook, OnItemClickListener listener) {
        this.listBook = listBook != null ? listBook : new ArrayList<>();
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
        ListBookResponseDTO book = listBook.get(position);
        if (book == null) {
            return;
        }
        Picasso.get().load(book.getThumbnail()).into(holder.imgBookB2);
        holder.nameB2.setText(book.getTitle());
        if (book.getDiscount() != 0.0) {
            double originalPrice = book.getOriginalPrice();
            holder.priceB2.setText(MyUtils.convertToVND(book.getDiscountedPrice()));

            //setting discount
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
        private TextView nameB2, priceB2, tvDiscount, originalPrice;


        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBookB2 = itemView.findViewById(R.id.img_imageB2);
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
        }
    }

    public void updateData(List<ListBookResponseDTO> newBookList) {
        this.listBook.clear();
        this.listBook.addAll(newBookList);
        notifyDataSetChanged();
    }
}
