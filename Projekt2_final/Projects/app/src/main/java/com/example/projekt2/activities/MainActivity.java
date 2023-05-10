package com.example.projekt2.activities;

// TODO: zmien akcje na floating button
//

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.projekt2.R;
import com.example.projekt2.adapters.ElementListAdapter;
import com.example.projekt2.dataBase.Element;
import com.example.projekt2.viewModels.ElementViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements ElementListAdapter.onItemClickListener2 {

    private ElementListAdapter adapter;
    private ElementViewModel viewModel;
    private FloatingActionButton createElement;
    private ActivityResultLauncher<Intent> insertActivityResultLauncher;
    private ActivityResultLauncher<Intent> editActivityResultLauncher;
    public static final String INTENT_EDIT = "edit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createElement = findViewById(R.id.fabMain);
        createElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddRecordActivity.class);
                insertActivityResultLauncher.launch(intent);
            }
        });

        adapter = new ElementListAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.lista_kontaktow);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(ElementViewModel.class);

        viewModel.getAllElements().observe(this, elements -> {
            adapter.setElementList(elements);
        });

        ItemTouchHelper.SimpleCallback touchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int adapterPosition = viewHolder.getAdapterPosition();
                Element el = viewModel.getAllElements().getValue().get(adapterPosition);
                viewModel.deleteElement(el);

            }
        };

        ItemTouchHelper touchHelper = new ItemTouchHelper(touchHelperCallback);
        touchHelper.attachToRecyclerView(recyclerView);

        insertActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            //w <> typ wyniku - tutaj ActivityResult, może
            //też być Uri
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult( ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        Bundle x = data.getExtras();
                        Element element = new Element(
                                x.getString(AddRecordActivity.INTENT_MANUFACTURER),
                                x.getString(AddRecordActivity.INTENT_MODEL),
                                x.getString(AddRecordActivity.INTENT_VERSION),
                                x.getString(AddRecordActivity.INTENT_WEBSITE));
                        viewModel.insertElement(element);
                    }
                }
            }
        );

        editActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                //w <> typ wyniku - tutaj ActivityResult, może
                //też być Uri
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult( ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            Bundle x = data.getExtras();
                            Element element = new Element(
                                    x.getLong(AddRecordActivity.INTENT_ID),
                                    data.getStringExtra(AddRecordActivity.INTENT_MANUFACTURER),
                                    data.getStringExtra(AddRecordActivity.INTENT_MODEL),
                                    data.getStringExtra(AddRecordActivity.INTENT_VERSION),
                                    data.getStringExtra(AddRecordActivity.INTENT_WEBSITE));
                            viewModel.update(element);
                        }
                    }
                }
        );


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_record:
                Intent intent = new Intent(MainActivity.this, AddRecordActivity.class);
                insertActivityResultLauncher.launch(intent);
                break;
            case R.id.clear_data:
                viewModel.deleteAll();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onItemClickListener2(Element element) {
        Intent intent = new Intent(MainActivity.this, AddRecordActivity.class);
        intent.putExtra(INTENT_EDIT, "1");
        intent.putExtra(AddRecordActivity.INTENT_ID, element.getId());
        intent.putExtra(AddRecordActivity.INTENT_MANUFACTURER, element.getManufacturer());
        intent.putExtra(AddRecordActivity.INTENT_MODEL, element.getModel());
        intent.putExtra(AddRecordActivity.INTENT_VERSION, element.getVersion());
        intent.putExtra(AddRecordActivity.INTENT_WEBSITE, element.getWebsite());
        editActivityResultLauncher.launch(intent);
    }
}