#include <jni.h>
#include <string>
#include <android/log.h>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_yly_remotebinder_MainActivity_stringFromJNI(JNIEnv *env, jobject thiz) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
