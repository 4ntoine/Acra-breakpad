package name.antonsmirnov.android.acra_breakpad.app;

import android.app.Application;
import name.antonsmirnov.android.acra_breakpad.*;
import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

/**
 *
 */
@ReportsCrashes(
        formKey = "",
        resDialogTitle = R.string.resDialogTitle,
        resDialogText = R.string.resDialogText,
        mode = ReportingInteractionMode.DIALOG,
        mailTo = App.SUPPORT_EMAIL)
public class App extends Application {

    private static App _instance;

    public static App get() {
        return _instance;
    }

    private NativeExceptionHandler exceptionHandler = new NativeExceptionHandler();

    public NativeExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    public static final String SUPPORT_EMAIL = "test@domain.com";

    @Override
    public void onCreate() {
        super.onCreate();

        _instance = this;

        // init
        ACRA.init(this);
        exceptionHandler.init(this);
    }
}
