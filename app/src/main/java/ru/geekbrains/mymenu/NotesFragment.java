package ru.geekbrains.mymenu;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class NotesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        setHasOptionsMenu(true);
        initPopupMenu(view);
        return view;
    }

    private void initPopupMenu(View view) {
        TextView textView = view.findViewById(R.id.textViewNotesFragment);
        textView.setOnClickListener(v -> {
            Activity activity = requireActivity();
            PopupMenu popupMenu = new PopupMenu(activity, v);
            activity.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int id = item.getItemId();
                    switch (id) {
                        case R.id.popupMenuAdd:
                            Toast.makeText(getContext(), "Добавить заметку", Toast.LENGTH_SHORT).show();
                            return true;
                        case R.id.popupMenuEdit:
                            Toast.makeText(getContext(), "Изменить заметку", Toast.LENGTH_SHORT).show();
                            return true;
                        case R.id.popupMenuDelete:
                            Toast.makeText(getContext(), "Удалить заметку", Toast.LENGTH_SHORT).show();
                            return true;
                        case R.id.popupMenuUpdate:
                            Toast.makeText(getContext(), "Обновить список", Toast.LENGTH_SHORT).show();
                            return true;
                    }
                    return true;
                }
            });
            popupMenu.show();
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.notes_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.notesFragmentMenuAdd) {
            Toast.makeText(getContext(), "Выбрано меню Добавить", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}