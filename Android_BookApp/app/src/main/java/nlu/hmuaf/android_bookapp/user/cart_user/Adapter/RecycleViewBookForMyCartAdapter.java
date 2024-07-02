package nlu.hmuaf.android_bookapp.user.cart_user.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import nlu.hmuaf.android_bookapp.R;
import nlu.hmuaf.android_bookapp.dto.response.MessageResponseDTO;
import nlu.hmuaf.android_bookapp.networking.BookAppApi;
import nlu.hmuaf.android_bookapp.networking.BookAppService;
import nlu.hmuaf.android_bookapp.room.entity.CartItems;
import nlu.hmuaf.android_bookapp.room.service.CartService;
import nlu.hmuaf.android_bookapp.user.cart_user.dialogs.AlertExceedQuantityDialog;
import nlu.hmuaf.android_bookapp.user.cart_user.dialogs.AlertQuantityTo0Dialog;
import nlu.hmuaf.android_bookapp.user.home.adapter.OnItemClickListener;
import nlu.hmuaf.android_bookapp.utils.MyUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecycleViewBookForMyCartAdapter extends RecyclerView.Adapter<RecycleViewBookForMyCartAdapter.MyViewHolder> {

    private Activity context;
    private List<CartItems> listBook;
    private CartService cartService;
    private BookAppApi bookAppApi;
    private OnItemClickListener listener;
    private SparseBooleanArray checkBoxStates;

    public RecycleViewBookForMyCartAdapter(Activity context, List<CartItems> list, CartService cartService, OnItemClickListener listener) {
        this.listBook = list;
        this.context = context;
        this.cartService = cartService;
        this.checkBoxStates = new SparseBooleanArray(list.size());
        this.listener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewBook;
        private TextView textViewBookDetail;
        private ImageButton decreaseQuantity;
        private ImageButton increaseQuantity;
        private EditText quantityBook;
        private TextView price;
        private TextView priceConfirm;
        private ImageButton deleteBook;
        private CheckBox checkBoxBook;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxBook = itemView.findViewById(R.id.checkBoxBook);
            imageViewBook = itemView.findViewById(R.id.imageViewBook);
            textViewBookDetail = itemView.findViewById(R.id.textViewBookDetail);
            decreaseQuantity = itemView.findViewById(R.id.imageButtonDecreaseBook);
            increaseQuantity = itemView.findViewById(R.id.imageButtonIncreaseBook);
            quantityBook = itemView.findViewById(R.id.quantityBook);
            price = itemView.findViewById(R.id.textViewPrice);
            priceConfirm = itemView.findViewById(R.id.textViewPriceConfirm);
            deleteBook = itemView.findViewById(R.id.imageButtonDeleteBook);
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

        public void bind(CartItems currentItem) {
            // Thiết lập các giá trị ban đầu
            textViewBookDetail.setText(currentItem.getTitle());
            // Xử lý giá tiền hiển thị
            double priceToShow;
            if ((Double) currentItem.getDiscountedPrice() != null && currentItem.getDiscountedPrice() != 0) {
                priceToShow = currentItem.getDiscountedPrice();
            } else {
                priceToShow = currentItem.getOriginalPrice();
            }
            Picasso.get().load(currentItem.getThumbnail()).into(imageViewBook);
            quantityBook.setText(String.valueOf(currentItem.getQuantity()));
            price.setText(MyUtils.convertToVND(priceToShow));
            priceConfirm.setText(MyUtils.convertToVND(priceToShow * currentItem.getQuantity()));
            // Xử lý sự kiện giảm số lượng sản phẩm mua
            decreaseQuantity.setOnClickListener(v -> {
                int quantity = Integer.parseInt(quantityBook.getText().toString());
                if (quantity > 1) {
                    quantity--;
                    updateQuantity(currentItem, quantity);
                } else {
                    showQuantityTo0Dialog();
                }
            });

            // Xử lý sự kiện tăng số lượng sản phẩm mua
            increaseQuantity.setOnClickListener(v -> {
                int quantity = Integer.parseInt(quantityBook.getText().toString());
                if (quantity < 100 && quantity < currentItem.getAvailableQuantity()) {
                    quantity++;
                    updateQuantity(currentItem, quantity);
                } else {
                    showExceedQuantityDialog();
                }
            });

            // Xử lý sự kiện xóa sản phẩm
            deleteBook.setOnClickListener(v -> {
                showDeleteConfirmationDialog(currentItem);
            });
        }

        private void updateQuantity(CartItems currentItem, int quantity) {
            quantityBook.setText(String.valueOf(quantity));
            priceConfirm.setText(MyUtils.convertToVND(quantity * currentItem.getOriginalPrice()));
            cartService.updateQuantity(MyUtils.getTokenResponse(context), currentItem.getBookId(), quantity);
        }

        private void showQuantityTo0Dialog() {
            AlertQuantityTo0Dialog dialog = new AlertQuantityTo0Dialog(context);
            dialog.show();
            quantityBook.setText(String.valueOf(1));
        }

        private void showExceedQuantityDialog() {
            AlertExceedQuantityDialog dialog = new AlertExceedQuantityDialog(context);
            dialog.show();
            quantityBook.setText(String.valueOf(100));
        }

        private void showDeleteConfirmationDialog(CartItems currentItem) {
            new AlertDialog.Builder(itemView.getContext())
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa mục này không?")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        Executor executor = Executors.newSingleThreadExecutor();
                        executor.execute(() -> {
                            cartService.deleteItem(currentItem);
                            bookAppApi = BookAppService.getClient(MyUtils.getTokenResponse(context).getToken());
                            Call<MessageResponseDTO> call = bookAppApi.deleteCartItem(MyUtils.getTokenResponse(context).getUserId(), currentItem.getBookId());
                            call.enqueue(new Callback<MessageResponseDTO>() {
                                @Override
                                public void onResponse(Call<MessageResponseDTO> call, Response<MessageResponseDTO> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(context, "Bạn đã xóa thành công", Toast.LENGTH_SHORT).show();
                                        listBook.remove(getAdapterPosition());
                                        notifyItemRemoved(getAdapterPosition());
                                        notifyItemRangeChanged(getAdapterPosition(), listBook.size());
                                    } else {
                                        if (response.code() == 500) {
                                            Toast.makeText(context, "Lỗi server khi xóa. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            String errorMessage = "Xóa không thành công. Mã lỗi: " + response.code();
                                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<MessageResponseDTO> call, Throwable throwable) {
                                    Toast.makeText(context, "Đã xảy ra lỗi khi xóa: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    System.out.println(throwable);
                                }
                            });
                        });
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        }

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_book_for_mycart, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(listBook.get(position));
        holder.checkBoxBook.setChecked(checkBoxStates.get(position, false));
        holder.checkBoxBook.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition != RecyclerView.NO_POSITION) {
                checkBoxStates.put(currentPosition, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBook.size();
    }

    public void updateData(List<CartItems> newCartItem) {
        this.listBook.clear();
        this.listBook.addAll(newCartItem);
        notifyDataSetChanged();
    }

    public List<Long> getSelectedCartItems() {
        List<Long> selectedItems = new ArrayList<>();
        for (int i = 0; i < checkBoxStates.size(); i++) {
            int key = checkBoxStates.keyAt(i);
            if (checkBoxStates.get(key)) {
                selectedItems.add(listBook.get(key).getBookId());
            }
        }
        return selectedItems;
    }

}
