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

    public static final String SUPPORT_EMAIL = "test@domain.com";

    @Override
    public void onCreate() {
        super.onCreate();

        // init
        ACRA.init(this);
        new NativeExceptionHandler().init(this);
    }
}
