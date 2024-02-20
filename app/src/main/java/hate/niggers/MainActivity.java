package hate.niggers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button button;
    ListView listView;
    List<String> items;
    ArrayAdapter<String> itemsAdapter;
    private static final String PREFS_NAME = "ashbduhasgfdhuga";
    private static final String KEY_LIST = "kljashdkhjas";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.saveButton);
        editText = findViewById(R.id.editText);
        listView = findViewById(R.id.listView);
        items = new ArrayList<>();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        itemsAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                builder.setMessage("Czy chcesz usunac to zadanie?")
                        .setCancelable(false)
                        .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                items.remove(i);
                                itemsAdapter.notifyDataSetChanged();
                                saveData();
                            }
                        })
                        .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Usuwanie zadania");
                alert.show();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();
                items.add(text);
                itemsAdapter.notifyDataSetChanged();
                editText.setText("");
                saveData();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveData();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> set = new HashSet<>(items);
        editor.putStringSet(KEY_LIST, set);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> set = sharedPreferences.getStringSet(KEY_LIST, null);
        if (set != null) {
            Set<String> a = new HashSet<>();
            a.addAll(items);
            a.addAll(set);
            items.clear();
            items.addAll(a);
            itemsAdapter.notifyDataSetChanged();
        }
    }
}