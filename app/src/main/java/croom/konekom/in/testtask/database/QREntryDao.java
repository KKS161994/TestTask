package croom.konekom.in.testtask.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface QREntryDao {
    @Query("SELECT * FROM QREntry")
    List<QREntry> getAll();

    @Insert
    void insert(QREntry users);

}