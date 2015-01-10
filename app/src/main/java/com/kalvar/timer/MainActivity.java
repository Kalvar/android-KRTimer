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

        //New
        final KRTimer krTimer = new KRTimer();
        krTimer.processBlock  = new KRTimer.ProcessBlock()
        {
            @Override
            public void running()
            {
                System.out.println("ProcessBlock Running");
                krTimer.testStopEvent();
            }

            @Override
            public void done(Boolean success)
            {
                System.out.println("ProcessBlock Done");
            }
        };
        krTimer.start();

        //Singleton
        final KRTimer sharedInstance = KRTimer.sharedInstance();
        sharedInstance.processBlock = new KRTimer.ProcessBlock()
        {
            @Override
            public void running()
            {
                System.out.println("ff.ProcessBlock Running");
                sharedInstance.testStopEvent();
            }

            @Override
            public void done(Boolean success)
            {
                System.out.println("ff.ProcessBlock Done");
            }
        };

        sharedInstance.start();

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
