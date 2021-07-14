package ru.geekbrains.mymenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        initButtonNotes();
        initButtonAdd();
        initButtonSettings();
    }

    private void initButtonSettings() {
        AppCompatButton button = findViewById(R.id.buttonMainActivitySettings);
        button.setOnClickListener(v -> showFragment(new SettingsFragment()));
    }

    private void initButtonAdd() {
        AppCompatButton button = findViewById(R.id.buttonMainActivityAddNote);
        button.setOnClickListener(v -> showFragment(new AddFragment()));
    }

    private void initButtonNotes() {
        AppCompatButton button = findViewById(R.id.buttonMainActivityNotes);
        button.setOnClickListener(v -> showFragment(new NotesFragment()));
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}