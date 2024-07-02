package nlu.hmuaf.android_bookapp.user.cart_user.fragment_front_end;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


import nlu.hmuaf.android_bookapp.dto.response.ListAddressResponseDTO;
import nlu.hmuaf.android_bookapp.dto.response.TokenResponseDTO;
import nlu.hmuaf.android_bookapp.networking.BookAppApi;
import nlu.hmuaf.android_bookapp.networking.BookAppService;
import nlu.hmuaf.android_bookapp.user.cart_user.adapter.RecycleVIewAddressUserAdapter;
import nlu.hmuaf.android_bookapp.user.cart_user.beans.Address;
import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.utils.MyUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentListAddressUser extends Fragment implements RecycleVIewAddressUserAdapter.OnAddressSelectedListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private View view;
    private RecyclerView recyclerViewAddressUser;
    private RecycleVIewAddressUserAdapter adapter;
    private List<ListAddressResponseDTO> listAddressDTO = new ArrayList<>();
    private OnAddressSelectedListenerFragment listener;
    private BookAppApi bookAppApi;

    @Override
    public void onAddressSelected(ListAddressResponseDTO addressResponseDTO) {
        listener.onAddressSelectedFragment(addressResponseDTO);
    }

    public interface OnAddressSelectedListenerFragment {
        void onAddressSelectedFragment(ListAddressResponseDTO address);
    }

    // Constructor mặc định công khai
    public FragmentListAddressUser() {
        // Required empty public constructor
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
        adapter = new RecycleVIewAddressUserAdapter(getActivity(), listAddressDTO, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewAddressUser.setLayoutManager(linearLayoutManager);
        recyclerViewAddressUser.setAdapter(adapter);
        return view;
    }

    // Hàm này sẽ nạp dữ liệu Address vào List<Address>
    public void getDataAddressUser() {
        bookAppApi = BookAppService.getClient();
        TokenResponseDTO tokenResponseDTO = MyUtils.getTokenResponse(getActivity());
        Call<List<ListAddressResponseDTO>> call = bookAppApi.getUserAddress(tokenResponseDTO.getUserId());
        call.enqueue(new Callback<List<ListAddressResponseDTO>>() {
            @Override
            public void onResponse(Call<List<ListAddressResponseDTO>> call, Response<List<ListAddressResponseDTO>> response) {
                if (response.isSuccessful()) {
                    List<ListAddressResponseDTO> addresses = response.body();
                    // Xử lý dữ liệu nhận được ở đây
                    listAddressDTO.clear(); // Xóa dữ liệu cũ trong listAddressDTO
                    listAddressDTO.addAll(addresses);

                    // Cập nhật lại RecyclerView thông qua adapter
                    adapter.notifyDataSetChanged();

                    System.out.println("Lấy thành công: ");
                } else {
                    // Xử lý lỗi ở đây
                    System.out.println("Lấy danh sách địa chỉ thất bại: ");
                }
            }

            @Override
            public void onFailure(Call<List<ListAddressResponseDTO>> call, Throwable t) {
                // Xử lý lỗi khi gọi API thất bại
                t.printStackTrace();
            }
        });
    }

    public void updateAddressList(Address newAddress) {
        bookAppApi = BookAppService.getClient();
        TokenResponseDTO tokenResponseDTO = MyUtils.getTokenResponse(getActivity());
        Call<List<ListAddressResponseDTO>> call = bookAppApi.getUserAddress(tokenResponseDTO.getUserId());
        call.enqueue(new Callback<List<ListAddressResponseDTO>>() {
            @Override
            public void onResponse(Call<List<ListAddressResponseDTO>> call, Response<List<ListAddressResponseDTO>> response) {
                if (response.isSuccessful()) {
                    List<ListAddressResponseDTO> addresses = response.body();
                    updateData(addresses);
                } else {
                    // Xử lý lỗi ở đây
                    System.out.println("Lấy danh sách địa chỉ thất bại: ");
                }
            }

            @Override
            public void onFailure(Call<List<ListAddressResponseDTO>> call, Throwable t) {
                // Xử lý lỗi khi gọi API thất bại
                t.printStackTrace();
            }
        });

    }

    public void updateData(List<ListAddressResponseDTO> listAddressResponseDTOS) {
        this.listAddressDTO.clear();
        this.listAddressDTO.addAll(listAddressResponseDTOS);
        adapter.notifyDataSetChanged();
    }

}
