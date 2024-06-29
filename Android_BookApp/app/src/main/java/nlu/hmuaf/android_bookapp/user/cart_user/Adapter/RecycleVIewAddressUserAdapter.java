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

import java.util.List;

import nlu.hmuaf.android_bookapp.user.cart_user.beans.Address;

import nlu.hmuaf.android_bookapp.user.cart_user.dialogs.ChangeAddressDialog;
import nlu.hmuaf.android_bookapp.R;

public class RecycleVIewAddressUserAdapter extends RecyclerView.Adapter<RecycleVIewAddressUserAdapter.MyViewHolder> {
    private Activity context;

    private List<Address> list;

    private boolean clickAdress = false;
    private OnAddressSelectedListener listener;
    public interface OnAddressSelectedListener {
        void onAddressSelected(Address address);
    }
    public RecycleVIewAddressUserAdapter(Activity context, List<Address> list, OnAddressSelectedListener listener) {
        this.list = list;
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
       holder.addressUser.setText(list.get(position).getStreet()+" , "+list.get(position).getWard()+" , "+list.get(position).getDistrict()+" , "+list.get(position).getCity());
       holder.radioButton.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               if(clickAdress != true){
                   holder.radioButton.setChecked(true);
                   clickAdress = true;
                   if (listener != null) {
                       listener.onAddressSelected(list.get(position));
                   }
               }else{
                   holder.radioButton.setChecked(false);
                   clickAdress = false;
               }
           }
       });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Address address = list.get(position);

                ChangeAddressDialog dialog = new ChangeAddressDialog(context,address);
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
