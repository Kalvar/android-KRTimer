android-KRTimer
=================

Simplely use timer and implement block methods.

``` java
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
```

## Version

V0.8 beta

## License

MIT.
