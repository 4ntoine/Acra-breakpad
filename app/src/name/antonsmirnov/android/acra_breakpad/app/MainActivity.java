package name.antonsmirnov.android.acra_breakpad.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import name.antonsmirnov.android.acra_breakpad.NativeExceptionHandler;

public class MainActivity extends Activity {

    private NativeExceptionHandler exceptionHandler = new NativeExceptionHandler();

    private Button testCatchButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // allocate and start handle native exceptions
        exceptionHandler.init(null);

        setContentView(R.layout.main);

        bindControls();
        initControls();
    }

    private void bindControls() {
        testCatchButton = (Button) findViewById(R.id.Main_testCatch);
    }

    private void initControls() {
        testCatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testCatch();
            }
        });
    }

    private void testCatch() {
        NativeLib lib = new NativeLib();

        // crash
        int result = lib.native_func("temp1", null, new Integer(5));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // release and stop handle native exceptions
        exceptionHandler.deinit();
    }
}
