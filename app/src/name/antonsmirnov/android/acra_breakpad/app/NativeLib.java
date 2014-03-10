package name.antonsmirnov.android.acra_breakpad.app;

import java.util.List;

/**
 * Test native lib
 */
public class NativeLib {

    static {
        System.loadLibrary("native");
    }

    /**
     * error function
     */
    public native static int native_func(String tmp1, List<String> tmp2, Object tmp3);
}
