package nlu.hmuaf.android_bookapp.Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.Admin.Book;

public class InStockFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private List<Book> bookList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
//        View view = inflater.inflate(R.layout.fragment_in_stock, container, false);
//        recyclerView = view.findViewById(R.id.recyclerViewInStock);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        bookList = new ArrayList<>();
//        bookAdapter = new BookAdapter(bookList);
//        recyclerView.setAdapter(bookAdapter);
//
//        loadBooks();

        return view;
    }

    private void loadBooks() {
        // TODO: Tải dữ liệu sách từ cơ sở dữ liệu và cập nhật bookList
        bookAdapter.notifyDataSetChanged();
    }
}
