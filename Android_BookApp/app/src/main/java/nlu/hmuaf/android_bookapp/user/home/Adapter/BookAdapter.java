package nlu.hmuaf.android_bookapp.user.home.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import nlu.hmuaf.android_bookapp.dto.response.ListBookResponseDTO;
import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.utils.MyUtils;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<ListBookResponseDTO> listBook;
    private OnItemClickListener listener;
    private OnAddToCartClick onAddToCartClick;

    public interface OnAddToCartClick {
        void onCartClick(int position);
    }

    public BookAdapter(List<ListBookResponseDTO> listBook, OnItemClickListener listener, OnAddToCartClick onAddToCartClick) {
        this.listBook = listBook != null ? listBook : new ArrayList<>();
        this.listener = listener;
        this.onAddToCartClick = onAddToCartClick;
    }

    @NonNull
    @Override
    public BookAdapter.BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new BookAdapter.BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.BookViewHolder holder, int position) {
        ListBookResponseDTO book = listBook.get(position);
        if (book == null) {
            return;
        }
        Picasso.get().load(book.getThumbnail()).into(holder.imgBook);
        Picasso.get().load(book.getThumbnail()).into(holder.imgCopyB2);
        holder.nameB.setText(book.getTitle());
        double averageRating = book.getAverageRating();
        double roundedRating = Math.round(averageRating * 10.0) / 10.0;
        holder.star.setText("Đánh giá: " + String.format("%.1f", roundedRating));
        if (book.getDiscount() != 0.0) {
            double originalPrice = book.getOriginalPrice();
            holder.priceB.setText(MyUtils.convertToVND(book.getDiscountedPrice()));
            holder.author.setText(book.getAuthor());
            //setting discount
            holder.tvDiscount.setText((int) (book.getDiscount() * 100) + "%");
            holder.tvDiscount.setVisibility(View.VISIBLE);
            holder.priceDcLayout.setVisibility(View.VISIBLE);
            // setting originalPrice
            holder.originalPrice.setText(MyUtils.convertToVND(book.getOriginalPrice()));
            holder.originalPrice.setVisibility(View.VISIBLE);
            holder.originalPrice.setPaintFlags(holder.originalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.priceB.setText(MyUtils.convertToVND(book.getOriginalPrice()));
            holder.tvDiscount.setVisibility(View.GONE);
            holder.originalPrice.setVisibility(View.GONE);
            holder.priceDcLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (listBook != null) {
            return listBook.size();
        }
        return 0;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        private ImageView addProduct, wishList;
        private ImageView imgBook;
        private ImageView imgCopyB2;
        private TextView nameB, priceB, tvDiscount, originalPrice, author, star;
        private LinearLayout priceDcLayout;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBook = itemView.findViewById(R.id.img_imageB2);
            imgCopyB2 = itemView.findViewById(R.id.img_copyB2);
            nameB = itemView.findViewById(R.id.tv_nameB2);
            author = itemView.findViewById(R.id.AuthorName);
            priceB = itemView.findViewById(R.id.tv_priceB2);
            tvDiscount = itemView.findViewById(R.id.tv_discountB2);
            originalPrice = itemView.findViewById(R.id.originalPriceB2);
            star = itemView.findViewById(R.id.star);
            addProduct = itemView.findViewById(R.id.cart);
            wishList = itemView.findViewById(R.id.wishList);
            priceDcLayout = itemView.findViewById(R.id.priceDcLayout);
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
            addProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onAddToCartClick != null) {
                        System.out.println("Thêm vào giỏ hàng");
                        int position = getAdapterPosition();
                        System.out.println(position);
                        if (position != RecyclerView.NO_POSITION) {
                            onAddToCartClick.onCartClick(position);
                        }
                    }
                }
            });
        }

        public ImageView getImgBook() {
            return imgBook;
        }
    }

    public void updateData(List<ListBookResponseDTO> newBookList) {
        this.listBook.clear();
        this.listBook.addAll(newBookList);
        notifyDataSetChanged();
    }
}