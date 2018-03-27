package ru.ac.uniyar.todolist;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.todoList) ListView todoListView;
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initListAdapter();
        getLoaderManager().initLoader(0, null, this);
    }

    private void initListAdapter() {
        String[] from = new String[] { "title", "description", "dueDate" };
        int[] to = new int[] { R.id.titleText, R.id.descriptionText, R.id.dueDateText };
        adapter = new SimpleCursorAdapter(this,
                R.layout.todo_item, null, from, to, 0);
        todoListView.setAdapter(adapter);
    }

    @OnClick(R.id.fab)
    public void onFABClick(View view) {
        Intent intent = new Intent(this, ToDoEditorActivity.class);
        startActivityForResult(intent, 1);
    }

    @OnItemClick(R.id.todoList)
    public void onListItemClicked(int position) {
        String itemId = String.valueOf(adapter.getItemId(position));
        Cursor cursor = getContentResolver().query(
                Uri.parse("content://ru.ac.uniyar.todolist.contentprovider/todos/" + itemId),
                null, null, null, null);
        cursor.moveToNext();
        Todo todo = new Todo(
                Integer.valueOf(cursor.getInt(cursor.getColumnIndex("_id"))),
                cursor.getString(cursor.getColumnIndex("title")),
                cursor.getString(cursor.getColumnIndex("description")),
                cursor.getString(cursor.getColumnIndex("dueDate")));
        cursor.close();
        Intent intent = new Intent(this, ToDoEditorActivity.class);
        intent.putExtra("todo", todo);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode != RESULT_OK) return;
            Todo todo = data.getParcelableExtra("todo");
            ContentValues cv = new ContentValues();
            cv.put("title", todo.getTitle());
            cv.put("description", todo.getDescription());
            cv.put("dueDate", todo.getDueDate());
            if (todo.isNew()) {
                getContentResolver().insert(
                        Uri.parse("content://ru.ac.uniyar.todolist.contentprovider/todos/"), cv);
            } else {
                getContentResolver().update(
                        Uri.parse("content://ru.ac.uniyar.todolist.contentprovider/todos/" +
                                todo.getId()), cv, null, null);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
            Uri.parse("content://ru.ac.uniyar.todolist.contentprovider/todos"),
            null, null, null, "dueDate desc");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
