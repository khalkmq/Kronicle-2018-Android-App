package com.example.everb.kronicle.Timer;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.design.widget.FloatingActionButton;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.ViewGroup;
        import android.view.View;
        import android.support.annotation.Nullable;
        import java.util.ArrayList;
        import java.util.List;
        import android.os.AsyncTask;
        import android.support.v7.widget.AppCompatTextView;
        import android.support.v7.widget.DefaultItemAnimator;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;

        import com.example.everb.kronicle.R;

public class FragmentTimer extends Fragment {

    View view;

    private FragmentTimer activity = FragmentTimer.this;
    private AppCompatTextView titleTimer;
    private RecyclerView recyclerViewTimer;
    private List<TimerData> listTimer;
    private RecyclerViewAdapterTimer timerAdapter;
    private TimerDatabase timerDatabase;

    FloatingActionButton fabTimer;

    // Constructor
    public FragmentTimer() {}

    // onCreate Function
    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        view = inflater.inflate(R.layout.fragment_timer,container,false);

        fabTimer = view.findViewById(R.id.fab_timer);

        fabTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_timer = new Intent(getActivity(), TimerSettings.class);
                startActivity(intent_timer);
            }
        });

        initViews();
        initObjects();

        return view;
    }

    private void initViews() {
        titleTimer = view.findViewById(R.id.title_tri);
        recyclerViewTimer = view.findViewById(R.id.recycler_view_timer);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listTimer = new ArrayList<>();
        timerAdapter = new RecyclerViewAdapterTimer(listTimer);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerViewTimer.setLayoutManager(mLayoutManager);
        recyclerViewTimer.setItemAnimator(new DefaultItemAnimator());
        recyclerViewTimer.setHasFixedSize(true);
        recyclerViewTimer.setAdapter(timerAdapter);
        timerDatabase = new TimerDatabase(getActivity());
        getDataFromSQLite();
    }

    /**
     * This method is to fetch all user records from SQLite
     */
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listTimer.clear();
                listTimer.addAll(timerDatabase.getAllTimers());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                timerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }




















}
