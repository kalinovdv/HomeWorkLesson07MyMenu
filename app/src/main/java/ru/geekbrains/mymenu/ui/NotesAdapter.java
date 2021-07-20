package ru.geekbrains.mymenu.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ru.geekbrains.mymenu.NoteData;
import ru.geekbrains.mymenu.R;
import ru.geekbrains.mymenu.data.NotesSource;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private OnNoteClickListener noteClickListener;
    private NotesSource dataSource;

    public NotesAdapter(NotesSource dataSource) {
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder viewHolder, int position) {
        viewHolder.setData(dataSource.getNoteData(position));
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView discription;
        private TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.noteItemCardViewTitel);
            this.discription = itemView.findViewById(R.id.noteItemCardViewDiscription);
            this.date = itemView.findViewById(R.id.noteItemCardViewDate);

            this.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (noteClickListener != null) {
                        noteClickListener.onNoteClick(view, getAdapterPosition());
                    }
                }
            });
        }

        public void setData(NoteData noteData) {
            title.setText(noteData.getTitel());
            discription.setText(noteData.getText());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm, dd-EEEE-yyyy");
            String dateForm = simpleDateFormat.format(Calendar.getInstance().getTime());
            date.setText(dateForm);
        }
    }

    public void SetOnNoteClickListener(OnNoteClickListener noteClickListener) {
        this.noteClickListener = noteClickListener;
    }

    public interface OnNoteClickListener {
        void onNoteClick(View view, int position);
    }
}
