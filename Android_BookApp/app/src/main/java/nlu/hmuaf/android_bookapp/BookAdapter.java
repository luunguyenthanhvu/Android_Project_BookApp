package nlu.hmuaf.android_bookapp;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter {

    private List<Book> listBook;
    private Context mContext;         // Lưu Context để dễ dàng truy cập

    public BookAdapter(List<Book> listBook, Context mContext) {
        this.listBook = listBook;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Nạp layout cho View biểu diễn phần tử sách
        View bookView = inflater.inflate(R.layout.book_item, parent, false);
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return listBook==null ? 0 : listBook.size();
    }
}
