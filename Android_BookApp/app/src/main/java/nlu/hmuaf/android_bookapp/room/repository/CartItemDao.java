package nlu.hmuaf.android_bookapp.room.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import nlu.hmuaf.android_bookapp.room.entity.CartItem;

@Dao
public interface CartItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CartItem cartItem);

    @Update
    void update(CartItem cartItem);

    @Query("DELETE FROM Cart_Items WHERE bookId = :bookId")
    void deleteCartItemByProductId(long bookId);

    @Query("SELECT * FROM Cart_Items WHERE username = :username")
    List<CartItem> getCartItemByUsername(String username);

    @Query("UPDATE cart_items SET quantity = quantity + :quantity WHERE bookId = :bookId AND username = :username")
    void updateQuantity(long bookId, int quantity, String username);
}
