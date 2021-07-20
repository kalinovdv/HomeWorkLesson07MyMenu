package ru.geekbrains.mymenu.data;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.geekbrains.mymenu.NoteData;
import ru.geekbrains.mymenu.R;

public class NotesSourceImpl implements NotesSource{

    private List<NoteData> dataSource;
    private Resources resources;

    public NotesSourceImpl(Resources resources) {
        this.dataSource = new ArrayList<>(20);
        this.resources = resources;
    }

    public NotesSourceImpl init() {
        String[] titels = resources.getStringArray(R.array.titels);
        String[] discription = resources.getStringArray(R.array.discription);

        for (int i = 0; i < discription.length; i++) {
            dataSource.add(new NoteData(titels[i], discription[i], new Date()));
        }
        return this;
    }

    @Override
    public NoteData getNoteData(int position) {
        return dataSource.get(position);
    }

    @Override
    public int size() {
        return dataSource.size();
    }
}
