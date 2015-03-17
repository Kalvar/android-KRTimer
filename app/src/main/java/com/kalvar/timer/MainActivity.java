package com.kalvar.timer;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import pers.kalvar.tools.timer.*;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("onCreate");

        //New an Object
        final KRTimer krTimer = new KRTimer();
        krTimer.start(new KRTimer.StatusListener() {
            @Override
            public void onRunning() {
                System.out.println("ProcessBlock Running");
            }

            @Override
            public void onFinished(boolean success) {
                System.out.println("ProcessBlock Done");
            }
        });

        //Singleton
        final KRTimer sharedInstance = KRTimer.sharedInstance();
        sharedInstance.start(new KRTimer.StatusListener() {
            @Override
            public void onRunning() {
                System.out.println("ff.ProcessBlock Running");
            }

            @Override
            public void onFinished(boolean success) {
                System.out.println("ff.ProcessBlock Done");
            }
        });

        sharedInstance.ezStart(3000, new KRTimer.StatusListener() {
            @Override
            public void onRunning() {
                // ...
            }

            @Override
            public void onFinished(boolean success) {
                // ...
            }
        });

        sharedInstance.ezStop();

        /*
        //The refresh method will stop the timer first, then run the timer after.
        sharedInstance.ezRefresh(3000, new KRTimer.StatusListener() {
            @Override
            public void onRunning() {
                // ...
            }

            @Override
            public void onFinished(boolean success) {
                // ...
            }
        });
        */

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
