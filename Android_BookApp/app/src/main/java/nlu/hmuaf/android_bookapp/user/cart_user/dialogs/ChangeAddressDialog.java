package nlu.hmuaf.android_bookapp.user.cart_user.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.List;

import nlu.hmuaf.android_bookapp.dto.request.AddressRequestDTO;
import nlu.hmuaf.android_bookapp.dto.response.ListAddressResponseDTO;
import nlu.hmuaf.android_bookapp.dto.response.TokenResponseDTO;
import nlu.hmuaf.android_bookapp.networking.BookAppApi;
import nlu.hmuaf.android_bookapp.networking.BookAppService;
import nlu.hmuaf.android_bookapp.user.cart_user.adapter.RecycleVIewAddressUserAdapter;
import nlu.hmuaf.android_bookapp.user.cart_user.beans.Address;
import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.user.cart_user.fragment_front_end.FragmentListAddressUser;
import nlu.hmuaf.android_bookapp.utils.MyUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeAddressDialog extends Dialog {

    private Activity activity;
    private ListAddressResponseDTO address;

    private EditText editTextAddressDetail;
    private FragmentListAddressUser fragmentListAddressUser;

    private Button buttonOK;
    private Button buttonCancel;
    private BookAppApi bookAppApi;


    public ChangeAddressDialog(@NonNull Activity activity, ListAddressResponseDTO address, RecycleVIewAddressUserAdapter.OnAddressSelectedListener listener) {
        super(activity);

        this.activity = activity;
        this.address = address;
        this.fragmentListAddressUser = (FragmentListAddressUser) listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_change_address_user_dialog);

        this.editTextAddressDetail = (EditText) findViewById(R.id.edt_change_detail_address);
        this.buttonOK = (Button) findViewById(R.id.btn_Ok);
        this.buttonCancel = (Button) findViewById(R.id.btn_Cancel);

        this.buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOKClick();
            }
        });
        this.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCancelClick();
            }
        });
    }

    // User click "OK" button.
    private void buttonOKClick() {
        String addressDetail = editTextAddressDetail.getText().toString();
        if (addressDetail == null || addressDetail.isEmpty()) {
            Toast.makeText(this.activity, "Bạn chưa điền đầy đủ thông tin. Vui lòng điền lại", Toast.LENGTH_LONG).show();
            return;
        } else {
            bookAppApi = BookAppService.getClient();
            TokenResponseDTO tokenResponseDTO = MyUtils.getTokenResponse(this.activity);
            AddressRequestDTO addressRequestDTO = AddressRequestDTO.builder()
                    .addressId(address.getAddressId()) // hoặc giá trị thích hợp
                    .addressDetails(addressDetail)
                    .build();

            Call<List<ListAddressResponseDTO>> call = bookAppApi.updateUserAddress(tokenResponseDTO.getUserId(), addressRequestDTO);

            call.enqueue(new Callback<List<ListAddressResponseDTO>>() {
                @Override
                public void onResponse(Call<List<ListAddressResponseDTO>> call, Response<List<ListAddressResponseDTO>> response) {
                    if (response.isSuccessful()) {
                        List<ListAddressResponseDTO> addresses = response.body();
                        fragmentListAddressUser.updateData(addresses);

                    } else {
                        // Xử lý lỗi ở đây
                        System.out.println("Cập nhật địa chỉ thất bại: " + response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<List<ListAddressResponseDTO>> call, Throwable t) {
                    // Xử lý lỗi khi gọi API thất bại
                    t.printStackTrace();
                }
            });

            this.dismiss();
        }


    }


    // User click "Cancel" button.
    private void buttonCancelClick() {
        this.dismiss();
    }

}
