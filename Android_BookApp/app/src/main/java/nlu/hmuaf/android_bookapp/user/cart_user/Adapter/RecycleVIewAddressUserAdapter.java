package nlu.hmuaf.android_bookapp.user.cart_user.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nlu.hmuaf.android_bookapp.dto.request.AddressRequestDTO;
import nlu.hmuaf.android_bookapp.dto.response.ListAddressResponseDTO;
import nlu.hmuaf.android_bookapp.dto.response.TokenResponseDTO;
import nlu.hmuaf.android_bookapp.networking.BookAppApi;
import nlu.hmuaf.android_bookapp.networking.BookAppService;
import nlu.hmuaf.android_bookapp.user.cart_user.beans.Address;

import nlu.hmuaf.android_bookapp.user.cart_user.dialogs.ChangeAddressDialog;
import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.utils.MyUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecycleVIewAddressUserAdapter extends RecyclerView.Adapter<RecycleVIewAddressUserAdapter.MyViewHolder> {
    private Activity context;

    private List<ListAddressResponseDTO> listDTO;
    private List<Address> listAddress;

    private boolean clickAdress = false;
    private OnAddressSelectedListener listener;

    public interface OnAddressSelectedListener {
        void onAddressSelected(ListAddressResponseDTO addressResponseDTO);

    }

    public RecycleVIewAddressUserAdapter(Activity context, List<ListAddressResponseDTO> listDTO, OnAddressSelectedListener listener) {
        this.listDTO = listDTO;
        this.context = context;
        this.listener = listener;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView addressUser;
        private Button edit;
        private RadioButton radioButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            addressUser = itemView.findViewById(R.id.tv_addressUser);
            edit = itemView.findViewById(R.id.btn_editAddress);
            radioButton = itemView.findViewById(R.id.buttonChooseAddress);
        }
    }

    @NonNull
    @Override
    public RecycleVIewAddressUserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_address_for_recycle_view, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecycleVIewAddressUserAdapter.MyViewHolder holder, int position) {
        holder.addressUser.setText(listDTO.get(position).getAddressDetails());
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickAdress != true) {
                    holder.radioButton.setChecked(true);
                    clickAdress = true;
                    if (listener != null) {
                        listener.onAddressSelected(listDTO.get(position));
                    }
                } else {
                    holder.radioButton.setChecked(false);
                    clickAdress = false;
                }
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListAddressResponseDTO address = listDTO.get(position);

                ChangeAddressDialog dialog = new ChangeAddressDialog(context, address, listener);
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listDTO.size();
    }

//    private void setStatusClickedRadioButton(boolean status, long idAddress, String addressDetail) {
//        BookAppApi bookAppApi = BookAppService.getClient();
//        TokenResponseDTO tokenResponseDTO = MyUtils.getTokenResponse(context);
//        AddressRequestDTO addressRequestDTO = AddressRequestDTO.builder()
//                .addressId(idAddress) // hoặc giá trị thích hợp
//                .addressDetails(addressDetail)
//                .build();
//
//        Call<List<ListAddressResponseDTO>> call = bookAppApi.updateUserAddress(tokenResponseDTO.getUserId(), addressRequestDTO);
//
//        call.enqueue(new Callback<List<ListAddressResponseDTO>>() {
//            @Override
//            public void onResponse(Call<List<ListAddressResponseDTO>> call, Response<List<ListAddressResponseDTO>> response) {
//                if (response.isSuccessful()) {
//                    List<ListAddressResponseDTO> addresses = response.body();
//                    System.out.println("Địa chỉ đã được cập nhật: " + addresses);
//                } else {
//                    // Xử lý lỗi ở đây
//                    System.out.println("Cập nhật địa chỉ thất bại: " + response.errorBody());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<ListAddressResponseDTO>> call, Throwable t) {
//                // Xử lý lỗi khi gọi API thất bại
//                t.printStackTrace();
//            }
//        });
//
//    }
}
