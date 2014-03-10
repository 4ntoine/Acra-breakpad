#include "native_exception_handler.h"

#include "client/linux/handler/exception_handler.h"
#include "client/linux/handler/minidump_descriptor.h"

#include <android/log.h>

// debug/release (comment for release)
#define DEBUG 1

void debug(const char *format, ... ) {
#ifdef DEBUG
    va_list argptr;
    va_start(argptr, format);
    __android_log_vprint(ANDROID_LOG_ERROR, "NATIVE_EXCEPTION_HANDLER", format, argptr);
    va_end(argptr);
#endif
}

static jclass ExceptionHandlerClass = NULL;
static jmethodID ExceptionHandlerMethod;

static jclass ExceptionClass = NULL;
static jmethodID ExceptionConstructor;

static JavaVM *vm;

bool breakpad_callback(const google_breakpad::MinidumpDescriptor& descriptor, void* context, bool succeeded) {
  const char * c_path = descriptor.path();
  debug("Dump path: %s\n", c_path);

  JNIEnv* env = NULL;

  if (vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6) != JNI_OK)
  {
    debug("Failed to get the environment");
    return false;
  }

  debug("attaching thread ...");
  vm->AttachCurrentThread(&env, NULL);

  debug("handle exception");

  // create exception object
  jstring j_path = env->NewStringUTF(c_path);
  jobject j_exception = env->NewObject(ExceptionClass, ExceptionConstructor, j_path);
  env->ReleaseStringUTFChars(j_path, c_path);
  env->DeleteLocalRef(j_path);

  // handle exception
  env->CallStaticVoidMethod(ExceptionHandlerClass, ExceptionHandlerMethod, j_exception);
  env->DeleteLocalRef(j_exception);

  debug("exception handled");

  return succeeded;
}

JNIEXPORT void JNICALL Java_name_antonsmirnov_android_acra_1breakpad_NativeExceptionHandler_nativeSetReportsDirectory
  (JNIEnv *env, jobject self, jstring j_reportsDirectory)
{
    debug("init breakpad");

    const char *c_reportsDirectory = env->GetStringUTFChars(j_reportsDirectory, 0);

    debug("set reports directory: %s\n", c_reportsDirectory);
    std::string str_reportsDirectory(c_reportsDirectory);
    env->ReleaseStringUTFChars(j_reportsDirectory, c_reportsDirectory);

    google_breakpad::MinidumpDescriptor descriptor(str_reportsDirectory);
    google_breakpad::ExceptionHandler eh(descriptor, NULL, breakpad_callback, NULL, true, -1);

    debug("init breakpad done");
}

void bind(JNIEnv *env)
{
  ExceptionHandlerClass = (jclass)env->NewGlobalRef(env->FindClass("name/antonsmirnov/android/acra_breakpad/NativeExceptionHandler"));
  if (ExceptionHandlerClass == NULL)
    debug("ExceptionHandlerClass not bound");

  ExceptionHandlerMethod = env->GetStaticMethodID(ExceptionHandlerClass, "handleException", "(Lname/antonsmirnov/android/acra_breakpad/NativeException;)V");
  if (ExceptionHandlerMethod == NULL)
    debug("ExceptionHandlerMethod not bound");

  ExceptionClass = (jclass)env->NewGlobalRef(env->FindClass("name/antonsmirnov/android/acra_breakpad/NativeException"));
  if (ExceptionClass == NULL)
    debug("ExceptionClass not bound");

  ExceptionConstructor = env->GetMethodID(ExceptionClass, "<init>", "(Ljava/lang/String;)V");
  if (ExceptionConstructor == NULL)
    debug("ExceptionConstructor not bound");
}

void unbind(JNIEnv *env)
{
  if (ExceptionHandlerClass != NULL)
    env->DeleteGlobalRef(ExceptionHandlerClass);

  ExceptionConstructor = NULL;
  ExceptionHandlerMethod = NULL;

  if (ExceptionClass != NULL)
    env->DeleteGlobalRef(ExceptionClass);

  ExceptionConstructor = NULL;
}

// happens when library is loaded
jint JNI_OnLoad(JavaVM* aVm, void* aReserved)
{
    debug("JNI_OnLoad()");

    JNIEnv *env;

    if (aVm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6) != JNI_OK)
    {
        debug("Failed to get the environment");
        return -1;
    }

    bind(env);
    vm = aVm;
    
    return JNI_VERSION_1_6;
}

void JNI_OnUnload(JavaVM *aVm, void *reserved)
{
    debug("JNI_OnUnload()");

    JNIEnv *env;
    if (aVm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6) != JNI_OK)
    {
        debug("Failed to get the environment");
        return;
    }

    unbind(env);
    vm = NULL;
}