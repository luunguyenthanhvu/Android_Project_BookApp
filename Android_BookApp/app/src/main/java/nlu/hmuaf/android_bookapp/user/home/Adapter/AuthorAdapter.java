package nlu.hmuaf.android_bookapp.user.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import nlu.hmuaf.android_bookapp.user.home.classess.Author;
import nlu.hmuaf.android_bookapp.R;

public class AuthorAdapter extends RecyclerView.Adapter<AuthorAdapter.AuthorViewHolder> {

    private  List<Author> mListAuthor;
    private OnItemClickListener listener;
    public AuthorAdapter(List<Author> mListAuthor, OnItemClickListener listener) {
        this.mListAuthor= mListAuthor;
        this.listener = listener;
    }


    @NonNull
    @Override
    public AuthorAdapter.AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.author_item, parent, false);
        return new AuthorAdapter.AuthorViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull AuthorAdapter.AuthorViewHolder holder, int position) {
        Author author = mListAuthor.get(position);
        if (author==null){
            return;
        }
        holder.imgAuthor.setImageResource(author.getResourceid());
        holder.nameAuthor.setText(author.getName());
        holder.ageAuthor.setText(String.valueOf(author.getAge()));
//        holder.priceB.setText(author.getAge());
    }

    @Override
    public int getItemCount() {
        if(mListAuthor!=null){
            return mListAuthor.size();
        }
        return 0;
    }

    public class AuthorViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAuthor;
        private TextView nameAuthor;
        private TextView ageAuthor;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAuthor = itemView.findViewById(R.id.img_author);
            nameAuthor = itemView.findViewById(R.id.tv_authorName);
            ageAuthor = itemView.findViewById(R.id.tv_age);
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
        }
    }}
//    private Context mContext;
//    private List<Integer> mAuthorImageIds;
//    private List<String> mAuthorNames;
//
//    public AuthorAdapter(Context context, List<Integer> authorImageIds, List<String> authorNames) {
//        mContext = context;
//        mAuthorImageIds = authorImageIds;
//        mAuthorNames = authorNames;
//    }
//
//    @NonNull
//    @Override
//    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.author_item, parent, false);
//        return new AuthorViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull AuthorViewHolder holder, int position) {
//        holder.imageView.setImageResource(mAuthorImageIds.get(position));
//        holder.textView.setText(mAuthorNames.get(position));
//
//        // Sự kiện onClick cho ImageView
//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Chuyển hướng tới BookActivity khi nhấn vào hình ảnh
//                Intent intent = new Intent(mContext, FantasyActivity.class);
//                mContext.startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return mAuthorImageIds.size();
//    }
//
//    public static class AuthorViewHolder extends RecyclerView.ViewHolder {
//        ImageView imageView;
//        TextView textView;
//
//        public AuthorViewHolder(@NonNull View itemView) {
//            super(itemView);
//            imageView = itemView.findViewById(R.id.AuthorItemImageView);
//            textView = itemView.findViewById(R.id.AuthorItemTextView);
//        }
//    }

