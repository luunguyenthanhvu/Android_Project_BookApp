package nlu.hmuaf.android_bookapp.user.profile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import nlu.hmuaf.android_bookapp.R;

public class BankAccountAdapter extends RecyclerView.Adapter<BankAccountAdapter.BankAccountViewHolder> {
    private List<String> accountDetailsList;
    private OnBankAccountUnlinkListener listener;

    public BankAccountAdapter(List<String> accountDetailsList, OnBankAccountUnlinkListener listener) {
        this.accountDetailsList = accountDetailsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BankAccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_item_bank_account, parent, false);
        return new BankAccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BankAccountViewHolder holder, int position) {
        String accountDetails = accountDetailsList.get(position);
        holder.bind(accountDetails, listener);
    }

    @Override
    public int getItemCount() {
        return accountDetailsList.size();
    }

    public static class BankAccountViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewBankInfo;
        private Button buttonUnlink;

        public BankAccountViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewBankInfo = itemView.findViewById(R.id.textViewBankInfo);
            buttonUnlink = itemView.findViewById(R.id.buttonUnlink);
        }

        public void bind(String accountDetails, OnBankAccountUnlinkListener listener) {
            textViewBankInfo.setText(accountDetails);

            buttonUnlink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onUnlink(accountDetails);
                }
            });
        }
    }

    public interface OnBankAccountUnlinkListener {
        void onUnlink(String accountDetails);
    }
}
