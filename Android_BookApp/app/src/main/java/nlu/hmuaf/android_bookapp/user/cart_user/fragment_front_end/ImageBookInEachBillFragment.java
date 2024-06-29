package nlu.hmuaf.android_bookapp.user.cart_user.fragment_front_end;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import nlu.hmuaf.android_bookapp.R;

public class ImageBookInEachBillFragment extends Fragment {
    private View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_image_book_in_each_item_bill, container, false);
        Bundle bundle = getArguments();
        String linkImage = bundle.getString("linkImage");
//        Set lại link ảnh
//        view.findViewById(R.id.imageViewBookInEachBill).setBackgroundResource(Integer.parseInt(linkImage));
        return view;
    }
}
