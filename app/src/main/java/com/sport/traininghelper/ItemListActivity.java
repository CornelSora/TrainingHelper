package com.sport.traininghelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sport.traininghelper.dummy.DummyContent;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {

  /**
   * Whether or not the activity is in two-pane mode, i.e. running on a tablet
   * device.
   */
  private boolean mTwoPane;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_item_list);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    toolbar.setTitle(getTitle());

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        try {
          Context context = view.getContext();
          Intent intent = new Intent(context, ItemAddActivity.class);
          context.startActivity(intent);
        } catch (Exception e) {
          System.out.println(e.toString());
        }
       /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
          .setAction("Action", null).show();*/
      }
    });

    if (findViewById(R.id.item_detail_container) != null) {
      // The detail container view will be present only in the
      // large-screen layouts (res/values-w900dp).
      // If this view is present, then the
      // activity should be in two-pane mode.
      mTwoPane = true;
    }

    View recyclerView = findViewById(R.id.item_list);
    assert recyclerView != null;
    setupRecyclerView((RecyclerView) recyclerView);
  }

  @Override
  protected void onResume() {
    super.onResume();
    View recyclerView = findViewById(R.id.item_list);
    assert recyclerView != null;
    setupRecyclerView((RecyclerView) recyclerView);
  }

  private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
    try {
      List<TrainingContract> trainingContracts = TrainingMethods.getInstance().select();
      Utils.setTrainingContractList(trainingContracts);
      recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, trainingContracts, mTwoPane));
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  public static class SimpleItemRecyclerViewAdapter
    extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    private final ItemListActivity mParentActivity;
    private final List<TrainingContract> mValues;
    private final boolean mTwoPane;
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        TrainingContract item = (TrainingContract) view.getTag();
        if (mTwoPane) {
          Bundle arguments = new Bundle();
          arguments.putInt(ItemDetailFragment.ARG_ITEM_ID, item.getId());
          ItemDetailFragment fragment = new ItemDetailFragment();
          fragment.setArguments(arguments);
          mParentActivity.getSupportFragmentManager().beginTransaction()
            .replace(R.id.item_detail_container, fragment)
            .commit();
        } else {
          Context context = view.getContext();
          Intent intent = new Intent(context, ItemDetailActivity.class);
          intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, item.getId());

          context.startActivity(intent);
        }
      }
    };

    SimpleItemRecyclerViewAdapter(ItemListActivity parent,
                                  List<TrainingContract> items,
                                  boolean twoPane) {
      mValues = items;
      mParentActivity = parent;
      mTwoPane = twoPane;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_list_content, parent, false);
      return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
      holder.mIdView.setText(String.valueOf(mValues.get(position).getId()));
      holder.mContentView.setText(mValues.get(position).getTitle());

      holder.itemView.setTag(mValues.get(position));
      holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
      return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
      final TextView mIdView;
      final TextView mContentView;

      ViewHolder(View view) {
        super(view);
        mIdView = (TextView) view.findViewById(R.id.id_text);
        mContentView = (TextView) view.findViewById(R.id.content);
      }
    }
  }
}
