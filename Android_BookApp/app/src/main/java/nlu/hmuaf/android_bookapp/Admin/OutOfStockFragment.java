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

public class OutOfStockFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private List<Book> bookList;

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_out_of_stock, container, false);
//        recyclerView = view.findViewById(R.id.recyclerViewOutOfStock);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        bookList = new ArrayList<>();
//        bookAdapter = new BookAdapter(bookList);
//        recyclerView.setAdapter(bookAdapter);
//
//        return view;
//    }
}
