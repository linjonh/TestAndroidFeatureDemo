package com.jaysen.testfeaturedemo.RecycleViewDragAndDrop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jaysen.testfeaturedemo.R;

/**
 * Created by jaysen.lin@foxmail.com on 2017/10/31.
 */

public class ScrollingActivity extends AppCompatActivity implements OnMoveAndSwipeListener {

    private MyItemTouchCallBack                itemTouchCallBack;
    private ItemTouchHelper                    itemTouchHelper;
    private RecyclerView.Adapter<MyViewHolder> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        RecyclerView recyclerView = findViewById(R.id.RCV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        itemTouchCallBack = new MyItemTouchCallBack();
        itemTouchCallBack.setOnMoveAndSwipeListener(this);
        itemTouchHelper = new ItemTouchHelper(itemTouchCallBack);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        adapter = new RecyclerView.Adapter<MyViewHolder>() {
            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = View.inflate(parent.getContext(), R.layout.item_layout, null);
                return new MyViewHolder(view);
            }

            @Override
            public void onBindViewHolder(final MyViewHolder holder, int position) {
                String text = "index: " + holder.getAdapterPosition();
                String s    = "@@@@@";
                String string = holder.textView.getContext().getString(R.string.exchange_description);
                Log.i(ScrollingActivity.class.getSimpleName(), string);
                string = String.format(string, s, s, s,"%","%");
                holder.textView.setText(string);
                holder.buttonMove.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
//                            Toast.makeText(ScrollingActivity.this, "onTouch", Toast.LENGTH_SHORT).show();
                            if (itemTouchHelper != null) {
//                                itemTouchCallBack.setmIsCanDrag(true);
                                itemTouchHelper.startDrag(holder);
                            }
                        }
                        return false;
                    }
                });
            }

            @Override
            public int getItemCount() {
                return 10;
            }
        };
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onMove(int preposition, int toPosition) {
        adapter.notifyItemMoved(preposition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        adapter.notifyItemRemoved(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Button   buttonMove;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            buttonMove = itemView.findViewById(R.id.buttonMove);
        }
    }
}
