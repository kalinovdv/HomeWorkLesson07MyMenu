package ru.geekbrains.mymenu.ui;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import ru.geekbrains.mymenu.R;
import ru.geekbrains.mymenu.data.NotesSource;
import ru.geekbrains.mymenu.data.NotesSourceImpl;

public class NotesFragment extends Fragment {

    public static NotesFragment newInstance() {
        return new NotesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.fragmentNotesRecyclerView);
        NotesSource data = new NotesSourceImpl(getResources()).init();
        initRecyclerView(recyclerView, data);
        setHasOptionsMenu(true);
        initPopupMenu(view);
        return view;
    }

    private void initRecyclerView(RecyclerView recyclerView, NotesSource data) {
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        NotesAdapter adapter = new NotesAdapter(data);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
            recyclerView.addItemDecoration(itemDecoration);
        }

        adapter.SetOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(View view, int position) {
                Toast.makeText(getContext(), String.format("%s - %d", ((TextView) view).getText(), position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initPopupMenu(View view) {
//        TextView textView = view.findViewById(R.id.textViewNotesFragment);
//        textView.setOnClickListener(v -> {
//            Activity activity = requireActivity();
//            PopupMenu popupMenu = new PopupMenu(activity, v);
//            activity.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
//            popupMenu.setOnMenuItemClickListener(item -> {
//                int id = item.getItemId();
//                switch (id) {
//                    case R.id.popupMenuAdd:
//                        Toast.makeText(getContext(), "Добавить заметку", Toast.LENGTH_SHORT).show();
//                        return true;
//                    case R.id.popupMenuEdit:
//                        Toast.makeText(getContext(), "Изменить заметку", Toast.LENGTH_SHORT).show();
//                        return true;
//                    case R.id.popupMenuDelete:
//                        Toast.makeText(getContext(), "Удалить заметку", Toast.LENGTH_SHORT).show();
//                        return true;
//                    case R.id.popupMenuUpdate:
//                        Toast.makeText(getContext(), "Обновить список", Toast.LENGTH_SHORT).show();
//                        return true;
//                }
//                return true;
//            });
//            popupMenu.show();
//        });
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