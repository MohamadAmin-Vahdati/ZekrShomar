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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private AppCompatTextView ZekrRooz , Tasbihat , ZekrShomar , SalavatShomar , SelectWallpaperPage ;
    private RelativeLayout layout_MainActivity ;
    private int displaydensity ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWallpaper();
        FindViews();
        SetTypeFace();
        SetListeners();


    }

    private void initWallpaper(){
        SharedPreferences sharedPreferences = getSharedPreferences("Wallpaper",MODE_PRIVATE);
        layout_MainActivity = findViewById(R.id.layout_MainActivity);
        layout_MainActivity.setBackgroundResource(sharedPreferences.getInt("WallpaperID",R.drawable.bg_1));
    }

    private void FindViews(){
        ZekrRooz = findViewById(R.id.btn_ZekrRooz);
        Tasbihat = findViewById(R.id.btn_Tasbihat);
        ZekrShomar = findViewById(R.id.btn_ZekrShomar);
        SalavatShomar = findViewById(R.id.btn_SalavatShomar);
        SelectWallpaperPage = findViewById(R.id.btn_SelectWallpaperPage);
        if (getResources().getBoolean(R.bool.isTablet)){
            findDisplayDensity();
        }
    }

    private void SetTypeFace(){
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/B Mitra Bold_YasDL.com_0.ttf");
        ZekrRooz.setTypeface(typeface);
        Tasbihat.setTypeface(typeface);
        ZekrShomar.setTypeface(typeface);
        SalavatShomar.setTypeface(typeface);
        SelectWallpaperPage.setTypeface(typeface);
    }

    private void SetListeners(){
        ZekrShomar.setOnClickListener(this);
        Tasbihat.setOnClickListener(this);
        ZekrRooz.setOnClickListener(this);
        SalavatShomar.setOnClickListener(this);
        SelectWallpaperPage.setOnClickListener(this);
    }

    private void ChangeForTablets(int width , int fontSize){
        ZekrRooz.setTextSize(fontSize);
        Tasbihat.setTextSize(fontSize);
        ZekrShomar.setTextSize(fontSize);
        SalavatShomar.setTextSize(fontSize);
        SelectWallpaperPage.setTextSize(fontSize);

        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) ZekrRooz.getLayoutParams();
        params1.width = width ;
        ZekrRooz.setLayoutParams(params1);

        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) Tasbihat.getLayoutParams();
        params2.width = width ;
        Tasbihat.setLayoutParams(params2);

        LinearLayout.LayoutParams params3 = (LinearLayout.LayoutParams) ZekrShomar.getLayoutParams();
        params3.width = width ;
        ZekrShomar.setLayoutParams(params3);

        LinearLayout.LayoutParams params4 = (LinearLayout.LayoutParams) SalavatShomar.getLayoutParams();
        params4.width = width ;
        SalavatShomar.setLayoutParams(params4);

        LinearLayout.LayoutParams params5 = (LinearLayout.LayoutParams) SelectWallpaperPage.getLayoutParams();
        params5.width = width ;
        SelectWallpaperPage.setLayoutParams(params5);

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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ZekrRooz :
                Intent intentRooz = new Intent(MainActivity.this, ZekrRoozActivity.class);
                startActivity(intentRooz);
                finish();
                break;
            case R.id.btn_Tasbihat :
                Intent intentTasbihat = new Intent(MainActivity.this, CounterPageActivity.class);
                intentTasbihat.putExtra("ID",8);
                startActivity(intentTasbihat);
                finish();
                break;
            case R.id.btn_ZekrShomar :
                Intent intentZekr = new Intent(MainActivity.this,ZekrPagesActivity.class);
                startActivity(intentZekr);
                finish();
                break;
            case R.id.btn_SalavatShomar:
                MyDialogs selectANDCounterDialog = new MyDialogs();
                selectANDCounterDialog.ShowSelectDialog(this , 7 ,displaydensity );
                break;
            case R.id.btn_SelectWallpaperPage :
                Intent intentSelectWallpaperPage = new Intent(MainActivity.this,SelectBacgroundActivity.class);
                startActivity(intentSelectWallpaperPage);
                finish();
                break;
        }
    }

}
