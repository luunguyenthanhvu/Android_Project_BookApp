package nlu.hmuaf.android_bookapp.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = "Cart_Items")
public class CartItem {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String username;
    private long bookId;
    private String title;
    private String thumbnail;
    /**
     * Cart quantity
     */
    private int quantity;
    /**
     * Product available quantity
     */
    private int availableQuantity;
}
