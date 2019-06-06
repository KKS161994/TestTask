package croom.konekom.in.testtask.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "QREntry")

public class QREntry {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "data")
    public String data;

    @ColumnInfo(name = "timestamp")
    public int timestamp;
}