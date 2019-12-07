package com.MAVP.ZekrShomarTasbih;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zekrshomar.R;

import java.util.ArrayList;
import java.util.List;

public class ZekrPagesActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView ;
    private List<Azkar> azkarList ;
    private RecyclerViewAdapter adapter ;
    private AzkarDbHelper dbHelper ;

    private ScrollView layout_ZekrPage ;

    private AppCompatButton btn_AddZekr ;

    private Dialog  AddZekrDialog;
    private AppCompatEditText  ET_inputZekr , ET_inputTranslate , ET_inputFullCount ;
    private AppCompatCheckBox  CB_UltimateAddZekr ;

    private int displaydensity ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zekr_pages);

        initWallpaper();

        recyclerView = findViewById(R.id.recyclerview);
        btn_AddZekr = findViewById(R.id.btn_AddZekr) ;
        recyclerView.setFocusable(false);

        dbHelper = new AzkarDbHelper(this);

        btn_AddZekr.setOnClickListener(this);

        changeForTablets();

        refreshDisplay();

    }

    private void initWallpaper(){
        SharedPreferences sharedPreferences = getSharedPreferences("Wallpaper",MODE_PRIVATE);
        layout_ZekrPage= findViewById(R.id.layout_ZekrPage);
        layout_ZekrPage.setBackgroundResource(sharedPreferences.getInt("WallpaperID",R.drawable.bg_1));
    }

    public void refreshDisplay (){
        azkarList= dbHelper.GetAzkarZekrPage() ;
        if (azkarList== null){
            azkarList = new ArrayList<>();
        }
        adapter = new RecyclerViewAdapter(azkarList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

}

    private void ShowDialog (){
        AddZekrDialog = new Dialog(this) ;
        final AppCompatTextView   TV_inputFullCountAddZekr ;
        AppCompatButton btn_ConfirmAddZekr ;

        AddZekrDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        AddZekrDialog.setContentView(R.layout.set_new_zekr);

        ET_inputZekr = AddZekrDialog.findViewById(R.id.ET_inputZekr);
        ET_inputTranslate = AddZekrDialog.findViewById(R.id.ET_inputTranslate);
        ET_inputFullCount = AddZekrDialog.findViewById(R.id.ET_inputFullCount);
        TV_inputFullCountAddZekr = AddZekrDialog.findViewById(R.id.TV_inputFullCountAddZekr);
        CB_UltimateAddZekr = AddZekrDialog.findViewById(R.id.CB_UltimateAddZekr);
        btn_ConfirmAddZekr = AddZekrDialog.findViewById(R.id.btn_ConfirmAddZekr);

        SetLengh();



        CB_UltimateAddZekr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked){
                    ET_inputFullCount.setEnabled(false);
                    ET_inputFullCount.setTextColor(Color.GRAY);
                    TV_inputFullCountAddZekr.setTextColor(Color.GRAY);
                }else {
                    ET_inputFullCount.setEnabled(true);
                    ET_inputFullCount.setTextColor(Color.BLACK);
                    TV_inputFullCountAddZekr.setTextColor(Color.BLACK);
                }
            }
        });

        btn_ConfirmAddZekr.setOnClickListener(this);

        changeDialogSize();
        AddZekrDialog.show();
    }

    private void AddnewZekr(){
        String inputZekr , inputTranslate , et_inputFullCount ;
        inputZekr = ET_inputZekr.getText().toString().trim() ;
        inputTranslate = ET_inputTranslate.getText().toString().trim() ;
        et_inputFullCount = ET_inputFullCount.getText().toString().trim() ;
        if (inputZekr.isEmpty()){
            Toast.makeText(this, "ذکر را وارد کنید", Toast.LENGTH_SHORT).show();
        }else if(!CB_UltimateAddZekr.isChecked() && et_inputFullCount.isEmpty()){
            Toast.makeText(this, "مقداری برای تعداد دوره وارد کنید", Toast.LENGTH_SHORT).show();
        } else {
            int inputFullCount ;
            if (CB_UltimateAddZekr.isChecked()){
                inputFullCount = -1 ;
            }else {
                inputFullCount = Integer.valueOf(et_inputFullCount);
            }
            if (inputFullCount==0){
                Toast.makeText(this, "مقداری غیر صفر برای تعداد دوره وارد کنید", Toast.LENGTH_SHORT).show();
            }else {
                Azkar new_azkar = new Azkar();
                new_azkar.setZekr(inputZekr);
                new_azkar.setTranslate(inputTranslate);
                new_azkar.setSound(0);
                new_azkar.setLastCount(0);
                new_azkar.setFullCount(inputFullCount);
                new_azkar.setSuggested(-1);
                dbHelper.InsertAzkar(new_azkar);
                refreshDisplay();
                AddZekrDialog.dismiss();
                Toast.makeText(this, "ذکر با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();

            }
        }




    }

    private void SetLengh(){

        int maxlengh ;
        if (displaydensity<160){
            maxlengh = 110 ;
        }else if (displaydensity<240){
            maxlengh = 120 ;
        }else if (displaydensity<320){
            maxlengh = 130 ;
        }else if (displaydensity<420){
            maxlengh = 145 ;
        } else {
            maxlengh = 165 ;
        }
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(maxlengh);
        ET_inputZekr.setFilters(filters);
        ET_inputTranslate.setFilters(filters);


    }

    private void changeForTablets(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        displaydensity = displayMetrics.densityDpi ;
        if (getResources().getBoolean(R.bool.isTablet)){
            btn_AddZekr.setTextSize(18);
            if (displaydensity<=213 && displaydensity>160){
                RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) btn_AddZekr.getLayoutParams();
                params1.setMargins(45,10,45,0);
                btn_AddZekr.setLayoutParams(params1);
            } else if(displaydensity>213 ){
                RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) btn_AddZekr.getLayoutParams();
                params1.setMargins(80,18,80,0);
                btn_AddZekr.setLayoutParams(params1);
            }
        }
    }



    @Override
    protected void onRestart() {
        super.onRestart();
        refreshDisplay();
    }

    private void changeDialogSize(){
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        if (AddZekrDialog.getWindow()!=null){
            AddZekrDialog.getWindow().setLayout((int) (0.94 * size.x), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_AddZekr:
                ShowDialog();
                break;
            case R.id.btn_ConfirmAddZekr:
                AddnewZekr();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ZekrPagesActivity.this,MainActivity.class));
        finish();
        super.onBackPressed();
    }
}
