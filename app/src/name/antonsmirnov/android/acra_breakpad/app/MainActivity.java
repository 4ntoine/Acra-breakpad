package name.antonsmirnov.android.acra_breakpad.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button testCatchButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        // release
        App.get().getExceptionHandler().deinit();
    }
}
