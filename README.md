android-KRTimer
=================

Simplely use timer and implement block methods.

``` java
import pers.kalvar.tools.timer.*;

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
```

## Version

V0.9 beta

## License

MIT.
