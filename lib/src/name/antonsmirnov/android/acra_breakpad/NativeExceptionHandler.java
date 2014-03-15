package name.antonsmirnov.android.acra_breakpad;

import android.app.Application;
import android.os.Environment;
import android.os.SystemClock;
import org.acra.ACRA;

import java.io.File;

/**
 * Native exception handler
 */
public class NativeExceptionHandler {

    static {
        System.loadLibrary("gnustl_shared");
        System.loadLibrary("native_exception_handler");
    }

    private native void nativeSetReportsDirectory(String directory);

    private File reportDirectory;

    private void setReportsDirectory(File directory) {
        this.reportDirectory = directory;

        if (!directory.exists())
            directory.mkdirs();

        nativeSetReportsDirectory(reportDirectory.getAbsolutePath());
    }

    /**
     * If invoked from native code on crash
     */
    public static void handleException(NativeException e) {
        // pass as binary file attachment
        ACRA.getConfig().setCrashDumpFile(e.getReportPath());

        ACRA.getErrorReporter().handleException(e);
        SystemClock.sleep(1000); // to let ACRA show dialog (otherwise ACRA dialog will not be shown)
    }

    /**
     * To be initialized once in Application.onCreate()
     */
    public boolean init(Application app) {
        // make sure report file is stored in public available storage to make it available for email app
        setReportsDirectory(Environment.getExternalStorageDirectory());
        return true;
    }

    private native void nativeRelease();

    /**
     * Have to be invoked before app quit
     */
    public void deinit() {
        nativeRelease();
    }
}
