package name.antonsmirnov.android.acra_breakpad;

import android.app.Application;
import org.acra.ACRA;

import java.io.File;

/**
 * Native exception handler
 */
public class NativeExceptionHandler {

    static {
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
        ACRA.getErrorReporter().putCustomData("minidump", e.getReport());
        ACRA.getErrorReporter().handleException(e);
    }

    /**
     * To be initialized once in Application.onCreate()
     */
    public boolean init(Application app) {
        setReportsDirectory(new File(app.getCacheDir(), "reports"));
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
