package ru.geekbrains.mymenu.data;

import ru.geekbrains.mymenu.NoteData;

public interface NotesSource {
    NotesSource init(NotesSourceResponse notesSourceResponse);
    NoteData getNoteData(int position);
    int size();
    void addNoteData(NoteData noteData);
    void deleteNoteData(int position);
    void updateNoteData(int position, NoteData noteData);
    void clearNoteData();
}
