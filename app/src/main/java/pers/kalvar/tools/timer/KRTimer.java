/*
 * @ Notice
 *  - package have to front import method.
 * */
package pers.kalvar.tools.timer;
//Android official packages
import android.os.Handler;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kalvar on 15/1/10.
 */
public class KRTimer
{
    /*
    * @ Notes
    *   - synchronized = to lock the resource ( functions, parameters never be outside resource used when it onRunning. )
    *   - Implements block methods same as iOS' block usage that has 2 theories to do :
    *     - 1). public abstract static class : this called Inner Class
    *     - 2). public interface : this is not Inner Class
    * */
    //Customized block method
    private abstract static class SyncListener
    {
        /*
        * @ Notes
        *   - Command + N -> choose Override ... -> choose onRunning() to be override implementation.
        * */
        //Optional implementation which likes @optional of iOS delegate.
        public void onSync()
        {
            //... Do something
        }
    }

    public interface StatusListener
    {
        public void onRunning();
        public void onFinished(boolean success);
    }

    private static SyncListener mSyncListener;
    private static StatusListener mStatusListener;

    private static boolean mIsPause      = false;

    //Amount of time in milliseconds before first execution.
    public static long delayInterval     = 0;
    public static long repeatInterval    = 0;
    public static int times              = 0;

    private static Timer mTimer          = null;
    private static TimerTask mTimerTask  = null;
    final static Handler mTimerHandler   = new Handler();
    final static Runnable mTimerRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            if(times > 999999)
            {
                //To avoid the memory leak issue.
                times = 0;
            }
            ++times;

            if( false == mIsPause && null != mStatusListener )
            {
                mStatusListener.onRunning();
            }
        }
    };

    private static Runnable mEasyRunnable = new Runnable()
    {
        @Override
        public void run() {
            if( null != mSyncListener )
            {
                mSyncListener.onSync();
            }
            mTimerHandler.postDelayed(mEasyRunnable, repeatInterval);
        }
    };

    public KRTimer()
    {
        mSyncListener   = null;
        mStatusListener = null;
        delayInterval   = 0;
        repeatInterval  = 1000;
    }

    private static KRTimer singletonTimer = null;
    public static KRTimer sharedInstance()
    {
        if (singletonTimer == null)
        {
            synchronized(KRTimer.class)
            {
                if(singletonTimer == null)
                {
                    singletonTimer = new KRTimer();
                }
            }
        }
        return singletonTimer;
    }

    public static void start(StatusListener listener)
    {
        mIsPause        = false;
        mStatusListener = null;

        if ( null == mTimer )
        {
            mTimer = new Timer();
        }

        if ( null == mTimerTask )
        {
            mTimerTask = new TimerTask()
            {
                @Override
                public void run()
                {
                    if( false == mIsPause)
                    {
                        mTimerHandler.post(mTimerRunnable);
                    }
                }
            };
        }

        mStatusListener = listener;

        if( null != mTimer && null != mTimerTask )
        {
            //delayInterval = repeatInterval;
            mTimer.schedule(mTimerTask, delayInterval, repeatInterval);
        }

    }

    public static void stop()
    {
        if ( null != mTimer )
        {
            mTimer.cancel();
            mTimer = null;
        }

        if ( null != mTimerTask )
        {
            mTimerTask.cancel();
            mTimerTask = null;
        }

        mIsPause = true;
        times    = 0;

        if( mStatusListener != null )
        {
            mStatusListener.onFinished(true);
        }

    }

    public static void refresh(StatusListener listener)
    {
        stop();
        start(listener);
    }

    /**
     * Ez serial methods are simulating the Google designed methods to implement the Runnable run in mainUiThread to directly handle simple something.
     * */
    public static void ezStart(int startAfterInterval, StatusListener listener)
    {
        if( null != mTimerHandler )
        {
            mTimerHandler.postDelayed(mEasyRunnable, startAfterInterval);
        }
    }

    public static void ezRefresh(int startAfterInterval, StatusListener listener)
    {
        ezStop();
        if( null != mTimerHandler )
        {
            mTimerHandler.postDelayed(mEasyRunnable, startAfterInterval);
        }
    }

    public static void ezStop()
    {
        if( null != mTimerHandler )
        {
            mTimerHandler.removeCallbacks(mTimerRunnable);
        }
    }

    public static void setSyncListener(SyncListener listener)
    {
        mSyncListener = listener;
    }

    public static void setStatusListener(StatusListener listener)
    {
        mStatusListener = listener;
    }

    /**
     * @ Unit Test
     * */
    public static void testStop()
    {
        if( times > 5 )
        {
            stop();
        }
    }

}