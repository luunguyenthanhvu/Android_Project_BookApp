package nlu.hmuaf.android_bookapp.user.cart_user.Adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nlu.hmuaf.android_bookapp.dto.response.ListBookResponseDTO;
import nlu.hmuaf.android_bookapp.room.entity.CartItem;
import nlu.hmuaf.android_bookapp.user.cart_user.Dialog.AlertExceedQuantityDialog;
import nlu.hmuaf.android_bookapp.user.cart_user.Dialog.AlertQuantityTo0Dialog;
import nlu.hmuaf.android_bookapp.R;

public class RecycleViewBookForMyCartAdapter extends RecyclerView.Adapter<RecycleViewBookForMyCartAdapter.MyViewHolder> {
    private Activity context;

    private List<CartItem> listBook;
    private SparseBooleanArray checkBoxStates;
    private SparseIntArray quantityStates;

    public RecycleViewBookForMyCartAdapter(Activity context, List<CartItem> list) {
        this.listBook = list;
        this.context = context;
        this.checkBoxStates = new SparseBooleanArray(list.size());
        this.quantityStates = new SparseIntArray(list.size());
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private CheckBox checkBoxBook;
        private ImageView imageViewBook;
        private TextView textViewBookDetail;
        private ImageButton decreaseQuantity;
        private ImageButton increaseQuantity;
        private EditText quantityBook;
        private TextView price;
        private TextView priceConfirm;
        private ImageButton deleteBook;

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
        }


    }

    @NonNull
    @Override
    public RecycleViewBookForMyCartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_book_for_mycart, parent, false);


        return new RecycleViewBookForMyCartAdapter.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecycleViewBookForMyCartAdapter.MyViewHolder holder, int position) {
        // Thiết lập các giá trị ban đầu
        holder.textViewBookDetail.setText(listBook.get(position).getTitle());
//    holder.price.setText(String.valueOf(listBook.get(position).getPrice()));
//    holder.priceConfirm.setText(String.valueOf(listBook.get(position).getPrice()));
        holder.imageViewBook.setImageResource(R.drawable.saber);
        holder.checkBoxBook.setChecked(checkBoxStates.get(position, false));
        holder.checkBoxBook.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition != RecyclerView.NO_POSITION) {
                checkBoxStates.put(currentPosition, isChecked);
            }
        });
        quantityStates.put(position, 1);

        // Xử lý trường hợp người dùng nhập số lượng trực tiếp
        holder.quantityBook.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition == RecyclerView.NO_POSITION) {
                    return;
                }

                String quantityText = s.toString();
                if (quantityText.isEmpty()) {
                    // Nếu văn bản rỗng, thiết lập giá trị mặc định là 1
                    holder.quantityBook.setText(String.valueOf(1));
                    return;
                }

                int quantity;
                try {
                    quantity = Integer.parseInt(quantityText);
                } catch (NumberFormatException e) {
                    // Nếu không thể chuyển đổi thành số, thiết lập giá trị mặc định là 1
                    holder.quantityBook.setText(String.valueOf(1));
                    return;
                }

                if (quantity == 0) {
                    AlertQuantityTo0Dialog dialog = new AlertQuantityTo0Dialog(context);
                    dialog.show();
                    holder.quantityBook.setText(String.valueOf(1));
                } else if (quantity > 100) {
                    AlertExceedQuantityDialog dialog = new AlertExceedQuantityDialog(context);
                    dialog.show();
                    holder.quantityBook.setText(String.valueOf(100));
                }
                if (quantity > 0 || quantity <= 100) {
                    holder.priceConfirm.setText(String.valueOf(quantity * listBook.get(currentPosition).getOriginalPrice()));
                    quantityStates.put(currentPosition, quantity);
                }
            }
        });

        // Nút giảm số lượng sản phẩm mua
        holder.decreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    int quantity = Integer.parseInt(holder.quantityBook.getText().toString());
                    if (quantity > 1) {
                        quantity--;
                        holder.quantityBook.setText(String.valueOf(quantity));

                        holder.priceConfirm.setText(String.valueOf(quantity * listBook.get(currentPosition).getOriginalPrice()));
                        quantityStates.put(currentPosition, quantity);
                    } else {
                        AlertQuantityTo0Dialog dialog = new AlertQuantityTo0Dialog(context);
                        dialog.show();
                        holder.quantityBook.setText(String.valueOf(1));
                    }
                }
            }
        });

        // Nút tăng số lượng sản phẩm mua
        holder.increaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    int quantity = Integer.parseInt(holder.quantityBook.getText().toString());
                    if (quantity < 100) {
                        quantity++;

                        holder.quantityBook.setText(String.valueOf(quantity));
                        holder.priceConfirm.setText(String.valueOf(quantity * listBook.get(currentPosition).getOriginalPrice()));
                        quantityStates.put(currentPosition, quantity);
                    } else {
                        AlertExceedQuantityDialog dialog = new AlertExceedQuantityDialog(context);
                        dialog.show();
                        holder.quantityBook.setText(String.valueOf(100));
                    }
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return listBook.size();
    }

    public List<CartItem> getSelectedCartItem() {
        List<CartItem> selectedCartItem = new ArrayList<>();
        for (int i = 0; i < listBook.size(); i++) {
            if (checkBoxStates.get(i, false)) {
                selectedCartItem.add(listBook.get(i));
            }
        }
        return selectedCartItem;
    }

    public SparseIntArray getQuantityStates() {
        return quantityStates;
    }

    public void updateData(List<CartItem> newCartItem) {
        this.listBook.clear();
        this.listBook.addAll(newCartItem);
        notifyDataSetChanged();
    }
}
