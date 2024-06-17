package nlu.hmuaf.android_bookapp.room.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import nlu.hmuaf.android_bookapp.room.entity.CartItem;
import nlu.hmuaf.android_bookapp.room.repository.CartItemDao;

@Database(entities = {CartItem.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CartItemDao cartItemDao();
}
