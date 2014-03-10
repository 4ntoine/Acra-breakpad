LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE    := native
LOCAL_SRC_FILES := native_lib.cpp
LOCAL_LDLIBS := -L$(SYSROOT)/usr/lib -llog
#LOCAL_CPP_FEATURES += exceptions

# ARM (coffeecatch)
#LOCAL_CFLAGS := -funwind-tables -Wl,--no-merge-exidx-entries

include $(BUILD_SHARED_LIBRARY)