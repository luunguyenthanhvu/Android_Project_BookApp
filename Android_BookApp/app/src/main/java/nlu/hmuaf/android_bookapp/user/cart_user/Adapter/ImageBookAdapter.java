package nlu.hmuaf.android_bookapp.user.cart_user.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import nlu.hmuaf.android_bookapp.user.cart_user.fragment_front_end.ImageBookInEachBillFragment;

public class ImageBookAdapter extends FragmentStateAdapter {
    private List<String> listImageBook;
    public ImageBookAdapter(@NonNull FragmentActivity fragmentActivity, List<String> listImageBook) {
        super(fragmentActivity);
        this.listImageBook =listImageBook;
    }

    @NonNull
    @Override

    public Fragment createFragment(int position) {
        String linkImage = listImageBook.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("linkImage",linkImage);
        ImageBookInEachBillFragment fragment = new ImageBookInEachBillFragment();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public int getItemCount() {
        return listImageBook.size();

    }
}
