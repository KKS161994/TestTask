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
    public long timestamp;

    public void setData(String data) {
        this.data = data;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getData() {
        return data;
    }
}