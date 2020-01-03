package com.to.aboomy.bannersample.util;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.to.aboomy.bannersample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * auth aboom
 * date 2019-11-18
 */
public class ArrayStringItemSelectDialog extends InnerDialog {

    private int choose = -1;
    private OnItemClickListener onItemClickListener;
    private ArrayList<String> valueStrings;

    public ArrayStringItemSelectDialog(@NonNull Context context) {
        super(context);
        gravity = Gravity.BOTTOM;
        width = WindowManager.LayoutParams.MATCH_PARENT;
        height = WindowManager.LayoutParams.WRAP_CONTENT;

        setContentView(R.layout.item_map_item);

        findViewById(R.id.tv_bottom_select_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView recyclerView = findViewById(R.id.rv_bottom_select);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MyAdapter adapter = new MyAdapter(valueStrings);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                choose = position;
                onItemClickListener.onItemClick(position, valueStrings.get(position));
                dismiss();
            }
        });
    }

    public ArrayStringItemSelectDialog setValueStrings(List<String> valueStrings) {
        this.valueStrings = new ArrayList<>(valueStrings);
        return this;
    }

    public ArrayStringItemSelectDialog setChoose(int choose){
        this.choose = choose;
        return this;
    }


    public ArrayStringItemSelectDialog setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    private class MyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        MyAdapter(ArrayList<String> valueStrings) {
            super(R.layout.rest_widget_item_bottom_select, valueStrings);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            TextView textView = helper.getView(R.id.tv_bottom_select_content);
            textView.setText(item);
            textView.setTextColor(choose == helper.getAdapterPosition() ? Color.RED : Color.parseColor("#333333"));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, String value);
    }
}
