package com.melon.myapp;

/**
 * Created by melon on 2017/8/7.
 * Email 530274554@qq.com
 */

public class ChainOfResponsibilityDemo {
    public static void main(String[] args) {
        AbstractLogger chainLogger = getChainLogger();

        chainLogger.logMessage(AbstractLogger.DEBUG, "Hello");
        chainLogger.logMessage(AbstractLogger.ERROR, "Hello");
        chainLogger.logMessage(AbstractLogger.INFO, "Hello");
        chainLogger.logMessage(AbstractLogger.DEFAULT, "Hello");
    }

    private static AbstractLogger getChainLogger() {
        AbstractLogger infoLogger = new InfoLogger(AbstractLogger.INFO);
        AbstractLogger debugLogger = new DebugLogger(AbstractLogger.DEBUG);
        AbstractLogger errorLogger = new ErrorLogger(AbstractLogger.ERROR);

        errorLogger.setNextLogger(debugLogger);
        debugLogger.setNextLogger(infoLogger);

        return errorLogger;
    }
}

abstract class AbstractLogger {
    public static int INFO = 1;
    public static int DEBUG = 2;
    public static int ERROR = 3;
    public static int DEFAULT = 0;

    protected int level;

    //责任链中的下一个元素
    protected AbstractLogger nextLogger;

    public void setNextLogger(AbstractLogger nextLogger) {
        this.nextLogger = nextLogger;
    }

    public void logMessage(int level, String message) {
        if (this.level <= level) {
            write(message);
        } else if (nextLogger != null) {
            nextLogger.logMessage(level, message);
        } else {
            System.out.println("没人处理");
        }
    }

    abstract protected void write(String message);
}

class InfoLogger extends AbstractLogger {
    public InfoLogger(int level) {
        this.level = level;
    }

    @Override
    protected void write(String message) {
        System.out.println("info: " + message);
    }
}

class DebugLogger extends AbstractLogger {
    public DebugLogger(int level) {
        this.level = level;
    }

    @Override
    protected void write(String message) {
        System.out.println("debug: " + message);
    }
}

class ErrorLogger extends AbstractLogger {
    public ErrorLogger(int level) {
        this.level = level;
    }

    @Override
    protected void write(String message) {
        System.out.println("error: " + message);
    }
}
