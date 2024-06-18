package nlu.hmuaf.android_bookapp.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = "Cart_Items")
public class CartItem implements Serializable {
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
    private Double averageRating;
    private double originalPrice;
    private double discountedPrice;
    private Double discount;

    public CartItem(String username, long bookId, String title, String thumbnail, int quantity, int availableQuantity, Double averageRating, double originalPrice, double discountedPrice, Double discount) {
        this.username = username;
        this.bookId = bookId;
        this.title = title;
        this.thumbnail = thumbnail;
        this.quantity = quantity;
        this.availableQuantity = availableQuantity;
        this.averageRating = averageRating;
        this.originalPrice = originalPrice;
        this.discountedPrice = (discount != null) ? originalPrice * (1 - discount) : 0.0;
        this.discount = (discount != null) ? discount : 0.0;
    }
}
