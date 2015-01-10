/**
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
    *   - synchronized = to lock the resource ( functions, parameters never be outside resource used when it running. )
    *   - Implements block methods same as iOS' block usage that has 2 theories to do :
    *     - 1). public abstract static class : this called Inner Class
    *     - 2). public interface : this is not Inner Class
    * */
    //Customized block method
    public abstract static class ProcessBlock
    {
        /*
        * @ Notes
        *   - Command + N -> choose Override ... -> choose running() to be override implementation.
        * */
        //Optional implementation which likes @optional of iOS delegate.
        public void running()
        {
            //... Do something
        }

        public abstract void done(Boolean success);
    }

    //Not inner class to implement block method.
    public interface StatusBlock
    {
        public void finished(Boolean success);

    }

    //Interface and Inner Class both never need achieve " new ".
    public static ProcessBlock processBlock;
    public static StatusBlock statusBlock;

    public static long delayInterval     = 0;
    public static long repeatInterval    = 0;
    public static int times              = 0;

    final static Timer _timer            = new Timer();
    final static Handler _timerHandler   = new Handler();
    final static Runnable _timerRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            //Do something you wanna do.
            ++times;
            //System.out.println("times : " + _times);
            processBlock.running();

            //Unit test to test the stop additions.
            //_testStopEvent();
        }
    };

    final static TimerTask _timerTask = new TimerTask()
    {
        @Override
        public void run()
        {
            //Same as [NSTimer fire]
            _timerHandler.post(_timerRunnable);
        }
    };

    static void start(ProcessBlock block)
    {
        processBlock = block;
        _timer.schedule(_timerTask, delayInterval, repeatInterval);
    }

    //__construct without configs
    public KRTimer()
    {
        //System.out.println("__construct");
        processBlock   = null;
        statusBlock    = null;
        delayInterval  = 0;
        repeatInterval = 1000;
    }

    /*
    * @ Singleton
    *   - Design Pattern : http://openhome.cc/Gossip/DesignPattern/SingletonPattern.htm
    * */
    //Method 1,
    private static KRTimer singletonTimer = null;
    //private KRTimer(){}
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

    /*
    //Method 2, best simple use in smaller wasting resource class
    private static KRTimer singletonTimer = new KRTimer();
    //private KRTimer(){}
    public static KRTimer sharedInstance()
    {
        return singletonTimer;
    }
    //*/

    /*
    //Method 3, use in bigger wasting resource class
    private static KRTimer singletonTimer;
    //private KRTimer(){}
    public static synchronized KRTimer sharedInstance()
    {
        if(singletonTimer == null)
        {
            singletonTimer = new KRTimer();
        }
        return singletonTimer;
    }
    //*/

    /*
    //__construct with configs
    public KRTimer(StatusBlock block)
    {
        statusBlock = block;
    }
    */

    //If we used static decorate with named function that global variable parameters must be static decoration too.
    public static void start()
    {
        //( TimerTask, delay fire duration, repeat duration )
        System.out.println("Start Timer");
        start(processBlock);
    }

    public static void stop()
    {
        System.out.println("Stop Timer");
        times = 0;
        _timer.cancel();

        if( processBlock != null )
        {
            processBlock.done(true);
        }

        if( statusBlock != null )
        {
            statusBlock.finished(true);
        }

    }

    /*
    //Setter / Getter
    private String mString;
    public String getString()
    {
        return this.mString;
    }

    public void setString(String value)
    {
        mString = value;
    }
    */

    /**
     * @ Unit Test
     * */
    public static void testStopEvent()
    {
        if( times > 5 )
        {
            stop();
        }
    }
}
