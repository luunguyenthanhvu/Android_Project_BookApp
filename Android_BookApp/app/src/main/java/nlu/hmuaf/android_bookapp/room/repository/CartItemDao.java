package nlu.hmuaf.android_bookapp.room.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import nlu.hmuaf.android_bookapp.room.entity.CartItems;

@Dao
public interface CartItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CartItems cartItem);

    @Update
    void update(CartItems cartItem);

    @Query("DELETE FROM Cart_Items WHERE bookId = :bookId")
    void deleteCartItemByProductId(long bookId);

    @Query("SELECT * FROM Cart_Items WHERE userId = :userId")
    List<CartItems> getCartItemByUserId(long userId);

    @Query("UPDATE Cart_Items SET quantity = :quantity WHERE bookId = :bookId AND userId = :userId")
    void updateQuantity(long bookId, int quantity, long userId);

    @Query("SELECT * FROM Cart_Items WHERE bookId = :bookId")
    CartItems getById(long bookId);

    @Delete
    void delete(CartItems cartItem);

    @Query("DELETE FROM Cart_Items WHERE bookId  = :bookId")
    void deleteCartItemByBookId(long bookId);

}
