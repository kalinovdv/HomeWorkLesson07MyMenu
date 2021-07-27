package ru.geekbrains.mymenu.observe;

import ru.geekbrains.mymenu.data.NoteData;

public interface Observer {
    void updateNoteData(NoteData noteData);
}
