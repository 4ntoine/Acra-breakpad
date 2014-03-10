#include "native_lib.h"

#include <android/log.h>

void debug(const char *format, ... ) {
    va_list argptr;
    va_start(argptr, format);
    __android_log_vprint(ANDROID_LOG_ERROR, "NATIVE_LIB", format, argptr);
    va_end(argptr);
}

JNIEXPORT jint JNICALL Java_name_antonsmirnov_android_acra_1breakpad_app_NativeLib_native_1func
  (JNIEnv *env, jclass self, jstring tmp1, jobject tmp2, jobject tmp3)
{
    debug("testing crash\n");

    char *ptr = 0;
    *ptr = '!'; // ERROR HERE!

    debug("unreachable\n");
}