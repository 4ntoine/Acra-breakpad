package name.antonsmirnov.android.acra_breakpad;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Native exception with stacktrace from native code
 */
public class NativeException extends RuntimeException {

    private String reportPath;
    private String report;

    public String getReportPath() {
        return reportPath;
    }

    public String getReport() {
        return report;
    }

    public NativeException(String reportPath) {
        this.reportPath = reportPath;
        report = readReport();
    }

    private String readReport() {
        try {
            StringBuilder sb = new StringBuilder();
            FileReader fileReader = new FileReader(reportPath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }

            return sb.toString();
        } catch (Throwable t) {
            // ignored
            return null;
        }
    }
}
