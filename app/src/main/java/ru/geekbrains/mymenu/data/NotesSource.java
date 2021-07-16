package ru.geekbrains.mymenu.data;

import ru.geekbrains.mymenu.NoteData;

public interface NotesSource {
    NoteData getNoteData(int position);
    int size();
}
