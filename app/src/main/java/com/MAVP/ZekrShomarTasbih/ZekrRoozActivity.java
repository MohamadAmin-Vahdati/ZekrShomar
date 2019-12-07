package com.MAVP.ZekrShomarTasbih;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.zekrshomar.R;

import java.util.Calendar;

public class ZekrRoozActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatTextView Shanbe , YekShanbe , DoShanbe , SeShanbe , ChaharShanbe , PanjShanbe , Jome ;
    private MyDialogs selectANDCounterDialog ;
    private RelativeLayout layout_ZekrRooz ;
    private int displaydensity ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zekr_rooz);

        initWallpaper();

        FindViews();
        SetListeners();

        selectANDCounterDialog = new MyDialogs();

        SetTypeFace();
        DayofWeek();

    }

    private void FindViews(){
        Shanbe = findViewById(R.id.btn_Shanbe);
        YekShanbe = findViewById(R.id.btn_YekShanbe);
        DoShanbe = findViewById(R.id.btn_DoShanbe);
        SeShanbe = findViewById(R.id.btn_SeShanbe);
        ChaharShanbe = findViewById(R.id.btn_ChaharShanbe);
        PanjShanbe = findViewById(R.id.btn_PanjShanbe);
        Jome = findViewById(R.id.btn_Jome);
        if (getResources().getBoolean(R.bool.isTablet)){
            findDisplayDensity();
        }
    }

    private void SetListeners(){
        Shanbe.setOnClickListener(this);
        YekShanbe.setOnClickListener(this);
        DoShanbe.setOnClickListener(this);
        SeShanbe.setOnClickListener(this);
        ChaharShanbe.setOnClickListener(this);
        PanjShanbe.setOnClickListener(this);
        Jome.setOnClickListener(this);
    }

    private void DayofWeek(){
        Calendar calendar = Calendar.getInstance();
        switch (calendar.get(Calendar.DAY_OF_WEEK)){
            case Calendar.SATURDAY :
                Shanbe.setTextColor(getResources().getColor(R.color.DayOfWeek));
                break;
            case Calendar.SUNDAY:
                YekShanbe.setTextColor(getResources().getColor(R.color.DayOfWeek));
                break;
            case Calendar.MONDAY :
                DoShanbe.setTextColor(getResources().getColor(R.color.DayOfWeek));
                break;
            case Calendar.TUESDAY :
                SeShanbe.setTextColor(getResources().getColor(R.color.DayOfWeek));
                break;
            case Calendar.WEDNESDAY :
                ChaharShanbe.setTextColor(getResources().getColor(R.color.DayOfWeek));
                break;
            case Calendar.THURSDAY :
                PanjShanbe.setTextColor(getResources().getColor(R.color.DayOfWeek));
                break;
            case Calendar.FRIDAY :
                Jome.setTextColor(getResources().getColor(R.color.DayOfWeek));
                break;
        }
    }

    private void SetTypeFace(){
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/B Mitra Bold_YasDL.com_0.ttf");
        Shanbe.setTypeface(typeface);
        YekShanbe.setTypeface(typeface);
        DoShanbe.setTypeface(typeface);
        SeShanbe.setTypeface(typeface);
        ChaharShanbe.setTypeface(typeface);
        PanjShanbe.setTypeface(typeface);
        Jome.setTypeface(typeface);
    }

    private void initWallpaper(){
        SharedPreferences sharedPreferences = getSharedPreferences("Wallpaper",MODE_PRIVATE);
        layout_ZekrRooz = findViewById(R.id.layout_ZekrRooz);
        layout_ZekrRooz.setBackgroundResource(sharedPreferences.getInt("WallpaperID",R.drawable.bg_1));
    }
    private void ChangeForTablets(int width , int fontSize){
        Shanbe.setTextSize(fontSize);
        YekShanbe.setTextSize(fontSize);
        DoShanbe.setTextSize(fontSize);
        SeShanbe.setTextSize(fontSize);
        ChaharShanbe.setTextSize(fontSize);
        PanjShanbe.setTextSize(fontSize);
        Jome.setTextSize(fontSize);

        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) Shanbe.getLayoutParams();
        params1.width = width ;
        Shanbe.setLayoutParams(params1);

        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) YekShanbe.getLayoutParams();
        params2.width = width ;
        YekShanbe.setLayoutParams(params2);

        LinearLayout.LayoutParams params3 = (LinearLayout.LayoutParams) DoShanbe.getLayoutParams();
        params3.width = width ;
        DoShanbe.setLayoutParams(params3);

        LinearLayout.LayoutParams params4 = (LinearLayout.LayoutParams) SeShanbe.getLayoutParams();
        params4.width = width ;
        SeShanbe.setLayoutParams(params4);

        LinearLayout.LayoutParams params5 = (LinearLayout.LayoutParams) ChaharShanbe.getLayoutParams();
        params5.width = width ;
        ChaharShanbe.setLayoutParams(params5);

        LinearLayout.LayoutParams params6 = (LinearLayout.LayoutParams) PanjShanbe.getLayoutParams();
        params6.width = width ;
        PanjShanbe.setLayoutParams(params4);

        LinearLayout.LayoutParams params7 = (LinearLayout.LayoutParams) Jome.getLayoutParams();
        params7.width = width ;
        Jome.setLayoutParams(params5);

    }

    private void findDisplayDensity(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        displaydensity = displayMetrics.densityDpi ;
        int width , fontSize ;

        if (displaydensity<=160){
            width = 310 ;
            fontSize = 22 ;
        }else if (displaydensity<=213){
            width = 350 ;
            fontSize = 23 ;
        } else {
            width = 590 ;
            fontSize = 23 ;
        }
        ChangeForTablets(width,fontSize);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_Shanbe :
                selectANDCounterDialog.ShowSelectDialog(this , 1,displaydensity);
                break;
            case R.id.btn_YekShanbe :
                selectANDCounterDialog.ShowSelectDialog(this , 2,displaydensity);
                break;
            case R.id.btn_DoShanbe :
                selectANDCounterDialog.ShowSelectDialog(this , 3,displaydensity);
                break;
            case R.id.btn_SeShanbe :
                selectANDCounterDialog.ShowSelectDialog(this , 4,displaydensity);
                break;
            case R.id.btn_ChaharShanbe :
                selectANDCounterDialog.ShowSelectDialog(this , 5,displaydensity);
                break;
            case R.id.btn_PanjShanbe :
                selectANDCounterDialog.ShowSelectDialog(this , 6,displaydensity);
                break;
            case R.id.btn_Jome :
                selectANDCounterDialog.ShowSelectDialog(this , 7,displaydensity);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ZekrRoozActivity.this,MainActivity.class));
        finish();
        super.onBackPressed();
    }
}
