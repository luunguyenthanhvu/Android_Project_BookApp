package nlu.hmuaf.android_bookapp.room.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import nlu.hmuaf.android_bookapp.room.entity.CartItems;
import nlu.hmuaf.android_bookapp.room.repository.CartItemDao;

@Database(entities = {CartItems.class}, version = 5, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CartItemDao cartItemDao();
}
