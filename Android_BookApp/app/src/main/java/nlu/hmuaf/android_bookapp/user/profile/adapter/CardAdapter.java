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

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private List<String> cardList;
    private OnCardUnlinkListener listener;

    public CardAdapter(List<String> cardList, OnCardUnlinkListener listener) {
        this.cardList = cardList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_item_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        String cardNumber = cardList.get(position);
        holder.bind(cardNumber, listener);
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewCardInfo;
        private Button buttonUnlink;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCardInfo = itemView.findViewById(R.id.textViewCardInfo);
            buttonUnlink = itemView.findViewById(R.id.buttonUnlink);
        }

        public void bind(String cardNumber, OnCardUnlinkListener listener) {
            String cardInfo = "Card *" + cardNumber.substring(cardNumber.length() - 4);
            textViewCardInfo.setText(cardInfo);

            buttonUnlink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onUnlink(cardNumber);
                }
            });
        }
    }

    public interface OnCardUnlinkListener {
        void onUnlink(String cardNumber);
    }
}
