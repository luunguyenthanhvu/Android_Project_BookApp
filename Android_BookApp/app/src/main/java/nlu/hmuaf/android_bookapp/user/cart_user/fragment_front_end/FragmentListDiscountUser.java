package nlu.hmuaf.android_bookapp.user.cart_user.fragment_front_end;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import nlu.hmuaf.android_bookapp.user.cart_user.adapter.RecycleViewDiscountUserAdapter;
import nlu.hmuaf.android_bookapp.user.cart_user.beans.Discounts;
import nlu.hmuaf.android_bookapp.R;


public class FragmentListDiscountUser extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private RecyclerView recycleViewDiscountsUser;
    private List<Discounts> couponList = new ArrayList<>();
    public FragmentListDiscountUser() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FragmentListDiscountUser newInstance() {
        FragmentListDiscountUser fragment = new FragmentListDiscountUser();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_list_discount_user, container, false);
        //     Hàm này sẽ nạp dữ liệu Coupon vào List<Discounts>
        getDataCoupon();

        recycleViewDiscountsUser = view.findViewById(R.id.listDiscountsUser);
        RecycleViewDiscountUserAdapter adapter = new RecycleViewDiscountUserAdapter(getActivity(),couponList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycleViewDiscountsUser.setLayoutManager(linearLayoutManager);
        recycleViewDiscountsUser.setAdapter(adapter);
        
        return view;
    }

    //     Hàm này sẽ nạp dữ liệu Coupon vào List<Discounts>
    public List<Discounts> getDataCoupon() {

        couponList.add(new Discounts(1, 0.2, Date.valueOf ("2024-5-19"), "giảm loại sách trinh thám 10%",3,"Còn"));
        couponList.add(new Discounts(1, 0.2, Date.valueOf ("2024-5-19"), "giảm loại sách trinh thám 10%",3,"Còn"));
        couponList.add(new Discounts(1, 0.2, Date.valueOf ("2024-5-19"), "giảm loại sách trinh thám 10%",3,"Còn"));
        couponList.add(new Discounts(1, 0.2, Date.valueOf ("2024-5-19"), "giảm loại sách trinh thám 10%",3,"Còn"));
        couponList.add(new Discounts(1, 0.2, Date.valueOf ("2024-5-19"), "giảm loại sách trinh thám 10%",3,"Còn"));
        couponList.add(new Discounts(1, 0.2, Date.valueOf ("2024-5-19"), "giảm loại sách trinh thám 10%",3,"Còn"));
        couponList.add(new Discounts(1, 0.2, Date.valueOf ("2024-5-19"), "giảm loại sách trinh thám 10%",3,"Còn"));
        couponList.add(new Discounts(1, 0.2, Date.valueOf ("2024-5-19"), "giảm loại sách trinh thám 10%",3,"Còn"));
        return couponList;
    }

}