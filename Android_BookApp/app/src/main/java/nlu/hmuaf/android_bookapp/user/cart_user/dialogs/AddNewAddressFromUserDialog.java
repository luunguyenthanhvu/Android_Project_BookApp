package nlu.hmuaf.android_bookapp.user.cart_user.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.List;

import nlu.hmuaf.android_bookapp.dto.request.AddressRequestDTO;
import nlu.hmuaf.android_bookapp.dto.response.ListAddressResponseDTO;
import nlu.hmuaf.android_bookapp.dto.response.TokenResponseDTO;
import nlu.hmuaf.android_bookapp.networking.BookAppApi;
import nlu.hmuaf.android_bookapp.networking.BookAppService;
import nlu.hmuaf.android_bookapp.user.cart_user.beans.Address;
import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.user.cart_user.fragment_front_end.FragmentListAddressUser;
import nlu.hmuaf.android_bookapp.utils.MyUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewAddressFromUserDialog extends Dialog {
    private Activity activity;
    private EditText editTextDetailAddress;

    private Button buttonOK;
    private Button buttonCancel;
    private Address address = new Address();
    private BookAppApi bookAppApi;

    private OnAddressAddedListener listener;
    private FragmentListAddressUser fragmentListAddressUser;

    public interface OnAddressAddedListener {
        void onAddressAdded(Address address);
    }

    public AddNewAddressFromUserDialog(@NonNull Activity activity, FragmentListAddressUser fragmentListAddressUser) {
        super(activity);
        this.fragmentListAddressUser = fragmentListAddressUser;
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_add_address_from_user_dialog);

        this.editTextDetailAddress = (EditText) findViewById(R.id.edt_change_detail_address);
        this.buttonCancel = (Button) findViewById(R.id.btn_Cancel);
        this.buttonOK = (Button) findViewById(R.id.btn_Ok);

        this.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCancelClick();
            }
        });

        this.buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOKClick();
            }
        });
    }

    private void buttonOKClick() {
        String detailAddress = editTextDetailAddress.getText().toString();
        if (detailAddress == null || detailAddress.isEmpty()) {
            Toast.makeText(this.activity, "Bạn chưa điền đầy đủ thông tin. Vui lòng điền lại", Toast.LENGTH_LONG).show();
            return;
        } else {
            TokenResponseDTO tokenResponseDTO = MyUtils.getTokenResponse(this.activity);
            bookAppApi = BookAppService.getClient();

            AddressRequestDTO addressRequestDTO = AddressRequestDTO.builder()
                    .addressDetails(detailAddress)
                    .mainAddress(false)
                    .build();

            Call<List<ListAddressResponseDTO>> call = bookAppApi.addNewAddress(tokenResponseDTO.getUserId(), addressRequestDTO);

            call.enqueue(new Callback<List<ListAddressResponseDTO>>() {
                @Override
                public void onResponse(Call<List<ListAddressResponseDTO>> call, Response<List<ListAddressResponseDTO>> response) {
                    if (response.isSuccessful()) {
                        List<ListAddressResponseDTO> addresses = response.body();
                        fragmentListAddressUser.updateAddressList(address);
                    } else {
                        // Xử lý lỗi ở đây
                        System.out.println("Thêm địa chỉ thất bại: " + response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<List<ListAddressResponseDTO>> call, Throwable t) {
                    // Xử lý lỗi khi gọi API thất bại
                    t.printStackTrace();
                }
            });
            Toast.makeText(this.activity, "Đã thêm thành công", Toast.LENGTH_SHORT).show();
            this.dismiss();
        }

    }

    private void buttonCancelClick() {
        this.dismiss();
    }
}
