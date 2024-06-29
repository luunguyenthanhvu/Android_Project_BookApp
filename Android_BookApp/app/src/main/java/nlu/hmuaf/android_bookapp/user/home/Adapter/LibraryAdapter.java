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

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder> {

    private List<BookB> books;

    public LibraryAdapter(List<BookB> books) {
        this.books = books;
    }

    @NonNull
    @Override
    public LibraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_item, parent, false);
        return new LibraryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryViewHolder holder, int position) {
        BookB book = books.get(position);
        holder.bind(book);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void addBook(BookB book) {
        books.add(book);
        notifyItemInserted(books.size() - 1);
    }

    static class LibraryViewHolder extends RecyclerView.ViewHolder {

        private ImageView bookImageView;
        private TextView bookNameTextView;
        private TextView bookPriceTextView;

        public LibraryViewHolder(@NonNull View itemView) {
            super(itemView);
            bookImageView = itemView.findViewById(R.id.LibraryImageView);
            bookNameTextView = itemView.findViewById(R.id.LibraryTextView);
            bookPriceTextView = itemView.findViewById(R.id.buttonM);
        }

        public void bind(BookB book) {
            bookImageView.setImageResource(book.getResourceid());
            bookNameTextView.setText(book.getName());
            bookPriceTextView.setText(book.getPrice());
        }
    }
}
