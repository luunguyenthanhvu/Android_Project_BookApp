package nlu.hmuaf.android_bookapp.user.profile.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.dto.response.ListAddressResponseDTO;
import nlu.hmuaf.android_bookapp.user.profile.classess.Address;
import nlu.hmuaf.android_bookapp.user.profile.activity.EditAddressActivity;
import nlu.hmuaf.android_bookapp.user.profile.activity.AddressActivity;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    private List<ListAddressResponseDTO> addressList;
    private Context context;

    public AddressAdapter(Context context, List<ListAddressResponseDTO> addressList) {
        this.context = context;
        this.addressList = addressList;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_item_address, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        ListAddressResponseDTO address = addressList.get(position);
        holder.bind(address);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditAddressActivity.class);
            intent.putExtra("address", address);
            ((AddressActivity) context).startActivityForResult(intent, 1);
        });
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    static class AddressViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView, phoneTextView, addressLine1TextView, addressLine2TextView, defaultTextView;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            addressLine1TextView = itemView.findViewById(R.id.addressLine1TextView);
            defaultTextView = itemView.findViewById(R.id.defaultTextView);
        }

        public void bind(ListAddressResponseDTO responseDTO) {
            addressLine1TextView.setText(responseDTO.getAddressDetails());
            defaultTextView.setVisibility(responseDTO.isMainAddress() ? View.VISIBLE : View.GONE);
        }
    }

    public void updateData(List<ListAddressResponseDTO> newAddressList) {
        this.addressList.clear();
        this.addressList.addAll(newAddressList);
        notifyDataSetChanged();
    }
}
