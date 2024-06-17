package nlu.hmuaf.android_bookapp.room.database;

import androidx.room.Database;

import nlu.hmuaf.android_bookapp.room.entity.CartItem;
import nlu.hmuaf.android_bookapp.room.repository.CartItemDao;

@Database(entities = {CartItem.class}, version = 1)
public abstract class AppDatabase {
    public abstract CartItemDao cartItemDao();
}
