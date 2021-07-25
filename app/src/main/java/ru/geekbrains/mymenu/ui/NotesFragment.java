package ru.geekbrains.mymenu.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;

import ru.geekbrains.mymenu.MainActivity;
import ru.geekbrains.mymenu.Navigation;
import ru.geekbrains.mymenu.NoteData;
import ru.geekbrains.mymenu.R;
import ru.geekbrains.mymenu.data.NotesSource;
import ru.geekbrains.mymenu.data.NotesSourceImpl;
import ru.geekbrains.mymenu.observe.Observer;
import ru.geekbrains.mymenu.observe.Publisher;

public class NotesFragment extends Fragment {

    private static final int MY_DEFAULT_DURATION = 1000;

    private NotesSource data;
    private NotesAdapter adapter;
    private RecyclerView recyclerView;
    private Navigation navigation;
    private Publisher publisher;
    private boolean moveToLastPosition;

    public static NotesFragment newInstance() {
        return new NotesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = new NotesSourceImpl(getResources()).init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        //RecyclerView recyclerView = view.findViewById(R.id.fragmentNotesRecyclerView);
        initView(view);
        setHasOptionsMenu(true);
        //initPopupMenu(view);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity =(MainActivity) context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.fragmentNotesRecyclerView);
        data = new NotesSourceImpl(getResources()).init();
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new NotesAdapter(data, this);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
            recyclerView.addItemDecoration(itemDecoration);
        }

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(MY_DEFAULT_DURATION);
        animator.setRemoveDuration(MY_DEFAULT_DURATION);
        recyclerView.setItemAnimator(animator);

        if (moveToLastPosition) {
            recyclerView.smoothScrollToPosition(data.size() - 1);
            moveToLastPosition = false;
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
        switch (item.getItemId()) {
            case R.id.notesFragmentMenuAdd:
                navigation.showFragment(NoteFragment.newInstance(), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateNoteData(NoteData noteData) {
                        data.addNoteData(noteData);
                        adapter.notifyItemInserted(data.size() - 1);
                        moveToLastPosition = true;
                    }
                });
                return true;
            case R.id.notesFragmentMenuClear:
                data.clearNoteData();
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.note_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = adapter.getMenuPosition();
        switch (item.getItemId()) {
            case R.id.noteMenuUpdate:
                navigation.showFragment(NoteFragment.newInstance(data.getNoteData(position)), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateNoteData(NoteData noteData) {
                        data.updateNoteData(position, noteData);
                        adapter.notifyItemChanged(position);
                    }
                });
                return true;
            case R.id.noteMenuDelete:
                data.deleteNoteData(position);
                adapter.notifyItemRemoved(position);
                return true;
        }
        return super.onContextItemSelected(item);
    }
}