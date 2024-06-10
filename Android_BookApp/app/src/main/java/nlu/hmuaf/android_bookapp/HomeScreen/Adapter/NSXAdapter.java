package nlu.hmuaf.android_bookapp.HomeScreen.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import nlu.hmuaf.android_bookapp.HomeScreen.Activity.FantasyActivity;
import nlu.hmuaf.android_bookapp.HomeScreen.Class.BookB;
import nlu.hmuaf.android_bookapp.R;

public class NSXAdapter extends RecyclerView.Adapter<NSXAdapter.NSXViewHolder> {

    private List<BookB> mListBookB;
    private OnItemClickListener listener;
    public NSXAdapter(List<BookB> mListBookB, OnItemClickListener listener) {
        this.mListBookB = mListBookB;
        this.listener = listener;
    }


    @NonNull
    @Override
    public NSXAdapter.NSXViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookname_item, parent, false);
        return new NSXAdapter.NSXViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull NSXAdapter.NSXViewHolder holder, int position) {
        BookB bookB = mListBookB.get(position);
        if (bookB==null){
            return;
        }
        holder.imgBookB.setImageResource(bookB.getResourceid());
        holder.nameB.setText(bookB.getName());

    }

    @Override
    public int getItemCount() {
        if(mListBookB!=null){
            return mListBookB.size();
        }
        return 0;
    }

    public class NSXViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgBookB;
        private TextView nameB;

        public NSXViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBookB =itemView.findViewById(R.id.BookItemImageView);
            nameB =itemView.findViewById(R.id.BookItemTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }}}