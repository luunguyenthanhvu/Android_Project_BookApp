package nlu.hmuaf.android_bookapp.admin.manage_inventory;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nlu.hmuaf.android_bookapp.R;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> booksList;
    private Context context;

    public BookAdapter(List<Book> booksList, Context context) {
        this.booksList = booksList;
        this.context = context;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_product_stock, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.bind(booksList.get(position));
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    public void updateBooks(List<Book> newBooksList) {
        this.booksList = newBooksList;
        notifyDataSetChanged();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProduct;
        private TextView tvProductName, tvProductCode, tvSupplementLevelData;
        private Button btnEdit;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductCode = itemView.findViewById(R.id.tvProductCode);
            tvSupplementLevelData = itemView.findViewById(R.id.tvSupplementLevelData);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }

        public void bind(Book book) {
            tvProductName.setText(book.getTitle());
            tvProductCode.setText(book.getBookID());
            tvSupplementLevelData.setText(book.getStatus());

            int imageResource = context.getResources().getIdentifier(book.getImageUrl(), "drawable", context.getPackageName());
            imgProduct.setImageResource(imageResource);

            btnEdit.setOnClickListener(v -> {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("book", book);
                context.startActivity(intent);
            });
        }
    }
}
