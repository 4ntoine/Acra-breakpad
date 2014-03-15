package name.antonsmirnov.android.acra_breakpad;

/**
 * Native exception with stacktrace from native code
 */
public class NativeException extends RuntimeException {

    private String reportPath;

    public String getReportPath() {
        return reportPath;
    }

    public NativeException(String reportPath) {
        this.reportPath = reportPath;
    }
}
