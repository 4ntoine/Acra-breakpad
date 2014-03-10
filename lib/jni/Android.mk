LOCAL_PATH := $(call my-dir)

# symlink 'breakpad' to breakpad directory
include $(LOCAL_PATH)/breakpad/android/google_breakpad/Android.mk

# set local path
LOCAL_PATH := /Users/asmirnov/Documents/dev/src/Acra-breakpad/lib/jni/

include $(CLEAR_VARS)
LOCAL_MODULE    := native_exception_handler
LOCAL_SRC_FILES := native_exception_handler.cpp
LOCAL_LDLIBS := -L$(SYSROOT)/usr/lib -llog

# link to breakpad
LOCAL_STATIC_LIBRARIES += breakpad_client

# ARM (for getting full stacktrace)
#LOCAL_CFLAGS := -funwind-tables -Wl,--no-merge-exidx-entries

include $(BUILD_SHARED_LIBRARY)