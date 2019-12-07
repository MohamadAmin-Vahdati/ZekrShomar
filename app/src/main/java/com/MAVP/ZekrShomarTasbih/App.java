package com.MAVP.ZekrShomarTasbih;

import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        new AssetsDatabaseHelper(getApplicationContext()).CheckDb();
    }
}
