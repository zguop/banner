package com.to.aboomy.bannersample.viewpager2.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.bumptech.glide.Glide;
import com.to.aboomy.bannersample.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * auth aboom
 * date 2020/6/6
 */
public class FragmentAdapter extends FragmentStateAdapter {

    private List<Integer> source = new ArrayList<>();

    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public FragmentAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ImageFragment.newInstance(source.get(position));
    }

    @Override
    public int getItemCount() {
        return source.size();
    }

    public void addData(@NonNull Collection<Integer> newData) {
        source.addAll(newData);
        notifyItemRangeInserted(source.size() - newData.size(), newData.size());

    }

    public static class ImageFragment extends Fragment {

        private static final String SOURCE = "source";

        static Fragment newInstance(int source) {
            ImageFragment fragment = new ImageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(SOURCE, source);
            fragment.setArguments(bundle);
            return fragment;
        }

        private int source;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle arguments = getArguments();
            if (arguments != null) {
                source = arguments.getInt(SOURCE);
            }
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.item_image, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            ImageView img = view.findViewById(R.id.img);
            Glide.with(this)
                    .load(source)
                    .into((img));
        }
    }

}
