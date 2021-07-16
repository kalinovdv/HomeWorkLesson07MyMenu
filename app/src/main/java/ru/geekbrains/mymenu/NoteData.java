package ru.geekbrains.mymenu;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class NoteData {
    private String titel;
    private String text;
    private Date date;

    public NoteData(String titel, String text, Date date) {
        this.titel = titel;
        this.text = text;
        this.date = date;
    }

    public String getTitel() {
        return this.titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
