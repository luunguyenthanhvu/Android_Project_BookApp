package nlu.hmuaf.android_bookapp.user.cart_user.FragmentFrontEnd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nlu.hmuaf.android_bookapp.user.cart_user.Adapter.RecycleVIewAddressUserAdapter;
import nlu.hmuaf.android_bookapp.user.cart_user.Bean.Address;
import nlu.hmuaf.android_bookapp.R;

public class FragmentListAddressUser extends Fragment implements RecycleVIewAddressUserAdapter.OnAddressSelectedListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private View view;
    private RecyclerView recyclerViewAddressUser;
    private RecycleVIewAddressUserAdapter adapter;
    private List<Address> listAddress = new ArrayList<>();
    private OnAddressSelectedListenerFragment listener;

    public interface OnAddressSelectedListenerFragment {
        void onAddressSelectedFragment(Address address);
    }

    // Constructor mặc định công khai
    public FragmentListAddressUser() {
        // Required empty public constructor
    }

    // Phương thức factory để khởi tạo fragment với listener
    public static FragmentListAddressUser newInstance(OnAddressSelectedListenerFragment listener) {
        FragmentListAddressUser fragment = new FragmentListAddressUser();
        fragment.setOnAddressSelectedListener(listener);
        return fragment;
    }

    public void setOnAddressSelectedListener(OnAddressSelectedListenerFragment listener) {
        this.listener = listener;
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
        view = inflater.inflate(R.layout.fragment_address_user, container, false);

        getDataAddressUser();

        recyclerViewAddressUser = view.findViewById(R.id.listAddressUser);
        adapter = new RecycleVIewAddressUserAdapter(getActivity(), listAddress, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewAddressUser.setLayoutManager(linearLayoutManager);
        recyclerViewAddressUser.setAdapter(adapter);
        return view;
    }

    // Hàm này sẽ nạp dữ liệu Address vào List<Address>
    public List<Address> getDataAddressUser() {
        listAddress.add(new Address(1, "HCM", "HCM", "HCM", "HCM"));
        listAddress.add(new Address(2, "HCM", "HCM", "HCM", "HCM"));
        return listAddress;
    }

    public void updateAddressList(Address newAddress) {
        listAddress.add(newAddress);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAddressSelected(Address address) {
        listener.onAddressSelectedFragment(address);
    }
}
