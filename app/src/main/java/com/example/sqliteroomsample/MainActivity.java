package com.example.sqliteroomsample;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.example.sqliteroomsample.room.data.AppDatabase;
import com.example.sqliteroomsample.room.data.VaccineStatus;
import com.example.sqliteroomsample.room.data.VaccineStatusDao;
import com.example.sqliteroomsample.view.MyRvAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv;
    Button btnAddStatus;
    VaccineStatusDao vaccineStatusDao;
    ArrayList<MyRvAdapter.ItemData> itemDataArrayList = new ArrayList<>();
    MyRvAdapter myRvAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase appDatabase =
                Room.databaseBuilder(this, AppDatabase.class, "UserDataBase").build();
        vaccineStatusDao = appDatabase.vaccineStatusDao();

        initButton();
        initRvView();
        syncUI();
    }

    private void syncUI(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<VaccineStatus> statusList = getVaccineStatus();
                Log.i("statusList:", statusList.toString());
            }
        });
        thread.start();
    }

    private List<VaccineStatus> getVaccineStatus(){
        return vaccineStatusDao.getAll();
    }

    private void initButton(){
        btnAddStatus = findViewById(R.id.btn_add_status);
        btnAddStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInsertDialog();
            }
        });
    }
    private void initRvView(){
        rv = findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        rv.setLayoutManager(linearLayoutManager);
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(rv);

        Thread rvInitThread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<VaccineStatus> vaccineStatusList = vaccineStatusDao.getAll();
                itemDataArrayList.clear();
                for(int i = 0;i < vaccineStatusList.size();i++){
                    Boolean vaccinated = vaccineStatusList.get(i).vaccinated;
                    String note = vaccineStatusList.get(i).note;
                    if(vaccinated){
                        itemDataArrayList.add(new MyRvAdapter.ItemData(
                                        AppCompatResources.getDrawable(MainActivity.this, R.mipmap.a1_xxxhdpi_7),
                                        note,
                                        "已接種疫苗",
                                        AppCompatResources.getDrawable(MainActivity.this, R.drawable.button_background_green),
                                        getColor(R.color.blue)
                                )
                        );
                    } else {
                        itemDataArrayList.add(new MyRvAdapter.ItemData(
                                        AppCompatResources.getDrawable(MainActivity.this, R.mipmap.a1_xxxhdpi_8),
                                        note,
                                        "未接種疫苗",
                                        AppCompatResources.getDrawable(MainActivity.this, R.drawable.button_background_red),
                                        getColor(R.color.red)

                                )
                        );
                    }

                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myRvAdapter = new MyRvAdapter();
                        myRvAdapter.setItemDataArrayList(itemDataArrayList);

                        rv.setAdapter(myRvAdapter);
                    }
                });

            }
        });

        rvInitThread.start();
    }

    private void showInsertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View addVaccineStatuView = View.inflate(this, R.layout.add_vaccine_status, null);
        Switch swVaccinated = addVaccineStatuView.findViewById(R.id.sw_vaccinated);
        EditText etNote = addVaccineStatuView.findViewById(R.id.et_note);

        builder.setView(addVaccineStatuView);
        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Boolean vaccinated = swVaccinated.isChecked();
                String note = etNote.getText().toString();

                VaccineStatus status = new VaccineStatus();
                status.vaccinated = vaccinated;
                status.note = note;

                Thread insertThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        vaccineStatusDao.insertVaccineStatus(status);

                        List<VaccineStatus> vaccineStatusList = vaccineStatusDao.getAll();
                        itemDataArrayList.clear();
                        for(int i = 0;i < vaccineStatusList.size();i++){
                            Boolean vaccinated = vaccineStatusList.get(i).vaccinated;
                            String note = vaccineStatusList.get(i).note;
                            if(vaccinated){
                                itemDataArrayList.add(new MyRvAdapter.ItemData(
                                                AppCompatResources.getDrawable(MainActivity.this, R.mipmap.a1_xxxhdpi_7),
                                                note,
                                                "已接種疫苗",
                                                AppCompatResources.getDrawable(MainActivity.this, R.drawable.button_background_green),
                                                getColor(R.color.blue)
                                        )
                                );
                            } else {
                                itemDataArrayList.add(new MyRvAdapter.ItemData(
                                                AppCompatResources.getDrawable(MainActivity.this, R.mipmap.a1_xxxhdpi_8),
                                                note,
                                                "未接種疫苗",
                                                AppCompatResources.getDrawable(MainActivity.this, R.drawable.button_background_red),
                                                getColor(R.color.red)

                                        )
                                );
                            }

                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                myRvAdapter.notifyDataSetChanged();
                            }
                        });

                    }
                });
                insertThread.start();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}