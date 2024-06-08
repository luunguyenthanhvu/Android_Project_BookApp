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

import nlu.hmuaf.android_bookapp.HomeScreen.Activity.BookActivity;
import nlu.hmuaf.android_bookapp.HomeScreen.Class.BookB;
import nlu.hmuaf.android_bookapp.R;
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>{



    private List<BookB> mListBookB;
    private OnItemClickListener listener;
    public ImageAdapter(List<BookB> mListBookB, OnItemClickListener listener) {
        this.mListBookB = mListBookB;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ImageViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        BookB bookB = mListBookB.get(position);
        if (bookB==null){
            return;
        }
        holder.imgBookB.setImageResource(bookB.getResourceid());
        holder.nameB.setText(bookB.getName());
        holder.priceB.setText(bookB.getPrice());
    }

    @Override
    public int getItemCount() {
        if(mListBookB!=null){
            return mListBookB.size();
        }
        return 0;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgBookB;
        private TextView nameB;
        private TextView priceB;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBookB =itemView.findViewById(R.id.img_imageB);
            nameB =itemView.findViewById(R.id.tv_nameB);
            priceB =itemView.findViewById(R.id.tv_priceB);
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
    }

//public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
//    private Context mContext;
//    private List<Integer> mImageIds;
//
//    public ImageAdapter(Context context, List<Integer> imageIds) {
//        mContext = context;
//        mImageIds = imageIds;
//    }
//
//    @NonNull
//    @Override
//    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item, parent, false);
//        return new ImageViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
//        int imageId = mImageIds.get(position);
//        holder.imageView.setImageResource(imageId);
//
//        // Sự kiện onClick cho ImageView
//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Chuyển hướng tới BookActivity khi nhấn vào hình ảnh
//                Intent intent = new Intent(mContext, BookActivity.class);
//                mContext.startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return mImageIds.size();
//    }
//
//    public static class ImageViewHolder extends RecyclerView.ViewHolder {
//        ImageView imageView;
//
//        public ImageViewHolder(@NonNull View itemView) {
//            super(itemView);
//            imageView = itemView.findViewById(R.id.imageRecyclerView);
//        }
//    }
}
