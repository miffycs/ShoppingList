// https://stackoverflow.com/questions/27293960/swipe-to-dismiss-for-recyclerview

package com.miffycs.shoppinglist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private Recycler_View_Adapter adapter;
    List<Item> itemsList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        itemsList = new ArrayList<Item>();
        // set custom test data with method
        itemsList = fill_with_data();
        adapter = new Recycler_View_Adapter(itemsList, getApplication());

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        final SwipeController swipeController = new SwipeController(new SwipeControllerActions(){
            @Override
            public void onRightClicked(int position) {
                adapter.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, adapter.getItemCount());
            }
        });
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_sort) {
            final String[] sorting_method = {"Sort by name", "Sort by price"};
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose a sorting method:");
            builder.setSingleChoiceItems(sorting_method, -1, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    switch(which) {
                        case 0:
                            // sort by comparing item names
                            Collections.sort(itemsList, new sortByName());
                            recyclerView.setAdapter(adapter);
                            break;
                        case 1:
                            // sort by comparing item names
                            Collections.sort(itemsList, new sortByPrice());
                            recyclerView.setAdapter(adapter);
                            break;
                    }
                    Toast.makeText(MainActivity.this,
                            "List sorted according to choice!",
                            Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            builder.show();
            recyclerView.setAdapter(adapter);
            return true;
        }
        if (id == R.id.action_clear) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Clear Entire List");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    itemsList.clear();
                    recyclerView.setAdapter(adapter);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
            return true;
        }
        if (id == R.id.action_add) {

            LayoutInflater factory = LayoutInflater.from(this);
            final View view_item_add = factory.inflate(R.layout.dialog_add_item, null);

            final EditText item_name = (EditText) view_item_add.findViewById(R.id.item_name);
            final EditText item_price = (EditText) view_item_add.findViewById(R.id.item_price);
            final EditText item_desc = (EditText) view_item_add.findViewById(R.id.item_desc);

            item_name.setText("", TextView.BufferType.EDITABLE);
            item_price.setText("", TextView.BufferType.EDITABLE);
            item_desc.setText("", TextView.BufferType.EDITABLE);

            final AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setView(view_item_add);

            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (item_name.getText().toString().isEmpty() ||
                            item_price.getText().toString().isEmpty() ||
                            item_desc.getText().toString().isEmpty()) {
                        Toast.makeText(MainActivity.this,
                                "Item not added!! \nPlease fill any empty fields!!",
                                Toast.LENGTH_LONG).show();
                    } else {
                        itemsList.add(new Item(item_name.getText().toString(),
                                Double.parseDouble(item_price.getText().toString()),
                                item_desc.getText().toString()));
                        Toast.makeText(MainActivity.this,
                                "Item added!",
                                Toast.LENGTH_SHORT).show();
                        recyclerView.setAdapter(adapter);
                    }
                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alert.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    static class sortByName implements Comparator<Item> {
        @Override
        public int compare(Item i1, Item i2) {
            return i1.name.compareTo(i2.name);
        }
    }

    static class sortByPrice implements Comparator<Item> {
        @Override
        public int compare(Item i1, Item i2) { return Double.compare(i1.price, i2.price); }
    }

    // Create a list of Item objects
    public List<Item> fill_with_data() {

        List<Item> data = new ArrayList<>();

        data.add(new Item("Apples", 1.111111));
        data.add(new Item("Oranges",3.33333));
        data.add(new Item("Banana",6.6666666));
        data.add(new Item("Peach",4.444444));
        data.add(new Item("Cucumbers",5.555555));

        return data;
    }

}
