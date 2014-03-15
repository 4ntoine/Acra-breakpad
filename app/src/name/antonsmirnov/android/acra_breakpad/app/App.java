package name.antonsmirnov.android.acra_breakpad.app;

import android.app.Application;
import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

/**
 * ACRA config
 */
@ReportsCrashes(
        // need to set custom report fields to add APPLICATION_LOG file
        customReportContent = {
            ReportField.REPORT_ID,
            ReportField.APP_VERSION_CODE,
            ReportField.APP_VERSION_NAME,
            ReportField.ANDROID_VERSION,
            ReportField.AVAILABLE_MEM_SIZE,
            ReportField.USER_COMMENT,
            ReportField.CRASH_DUMP,        // make sure you're using 4ntoine-acra or pull request merged to acra
        },
        formKey = "",
        resDialogTitle = R.string.resDialogTitle,
        resDialogText = R.string.resDialogText,
        resDialogCommentPrompt = R.string.resDialogComment, // required to show user comment field
        mode = ReportingInteractionMode.DIALOG,
        mailTo = App.SUPPORT_EMAIL)
public class App extends Application {

    public static final String SUPPORT_EMAIL = "test@domain.com";

    @Override
    public void onCreate() {
        super.onCreate();

        ACRA.init(this);
    }
}
