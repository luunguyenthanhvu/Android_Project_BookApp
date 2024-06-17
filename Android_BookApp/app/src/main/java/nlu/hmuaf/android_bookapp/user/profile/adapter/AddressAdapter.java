package nlu.hmuaf.android_bookapp.user.profile.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.user.profile.Class.Address;
import nlu.hmuaf.android_bookapp.user.profile.activity.EditAddressActivity;
import nlu.hmuaf.android_bookapp.user.profile.activity.AddressActivity;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    private List<Address> addressList;
    private Context context;

    public AddressAdapter(Context context, List<Address> addressList) {
        this.context = context;
        this.addressList = addressList;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        Address address = addressList.get(position);
        holder.bind(address);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditAddressActivity.class);
            intent.putExtra("address", address);
            intent.putExtra("addressList", new ArrayList<>(addressList)); // Truyền danh sách địa chỉ
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
            nameTextView = itemView.findViewById(R.id.nameTextView);
            phoneTextView = itemView.findViewById(R.id.phoneTextView);
            addressLine1TextView = itemView.findViewById(R.id.addressLine1TextView);
            addressLine2TextView = itemView.findViewById(R.id.addressLine2TextView);
            defaultTextView = itemView.findViewById(R.id.defaultTextView);
        }

        public void bind(Address address) {
            nameTextView.setText(address.getUser().getFirstName() + " " + address.getUser().getLastName());
            phoneTextView.setText(address.getUser().getPhoneNum());
            addressLine1TextView.setText(address.getCity() + ", " + address.getDistrict());
            addressLine2TextView.setText(address.getStreet());
            defaultTextView.setVisibility(address.isDefault() ? View.VISIBLE : View.GONE);
        }
    }
}
