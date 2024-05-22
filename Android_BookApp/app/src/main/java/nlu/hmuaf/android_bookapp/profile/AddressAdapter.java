package nlu.hmuaf.android_bookapp.profile;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.profile.Address;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    private List<Address> addressList;

    public AddressAdapter(List<Address> addressList) {
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
        holder.nameTextView.setText(address.getName() + " | " + address.getPhone());
        holder.addressLine1TextView.setText(address.getAddressLine1());
        holder.addressLine2TextView.setText(address.getAddressLine2());
        if (address.isDefault()) {
            holder.defaultTextView.setVisibility(View.VISIBLE);
        } else {
            holder.defaultTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public static class AddressViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView addressLine1TextView;
        TextView addressLine2TextView;
        TextView defaultTextView;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            addressLine1TextView = itemView.findViewById(R.id.addressLine1TextView);
            addressLine2TextView = itemView.findViewById(R.id.addressLine2TextView);
            defaultTextView = itemView.findViewById(R.id.defaultTextView);
        }
    }
}
