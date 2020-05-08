#include <jni.h>
#include <string>
#include <android/log.h>
#include <unistd.h>
#include <sys/file.h>

const char *TAG = "name";

extern "C"
JNIEXPORT void JNICALL
Java_com_yly_remotebinder_FileLocker_init(JNIEnv *env, jobject thiz) {
    __android_log_print(ANDROID_LOG_INFO, TAG, "init");
}



extern "C"
JNIEXPORT void JNICALL
Java_com_yly_remotebinder_FileLocker_fileLockerDaemon(
        JNIEnv *env,
        jobject thiz,
        jstring origin_path,
        jstring daemon_path) {

    pid_t pid;

    if ((pid = fork()) < 0) {
        __android_log_print(ANDROID_LOG_ERROR, TAG, "fork proc failed");
        exit(-1);
    } else if (pid == 0) {
        //child proc

        if ((pid = fork()) < 0) {
            __android_log_print(ANDROID_LOG_ERROR, TAG, "child proc fork proc failed");
            exit(-1);
        } else if (pid >= 0) {
            exit(0);
        }

        //孤儿进程,锁定Origin文件
        char *c_daemon_path = const_cast<char *>(env->GetStringUTFChars(daemon_path, 0));

        int fd_daemon = open(c_daemon_path, O_RDONLY);

        flock(fd_daemon, LOCK_EX);

        sleep(3);

        char *c_origin_path = const_cast<char *>(env->GetStringUTFChars(origin_path, 0));

        int fd_origin = open(c_origin_path, O_RDONLY);

        flock(fd_origin, LOCK_EX);

        jclass cls = env->GetObjectClass(thiz);
        jmethodID cb_method = env->GetMethodID(cls, "originDead", "()V");
        env->CallVoidMethod(thiz, cb_method);

    }

}




extern "C"
JNIEXPORT void JNICALL
Java_com_yly_remotebinder_FileLocker_fileLockerOrigin(JNIEnv *env, jobject thiz,
                                                      jstring origin_path, jstring daemon_path) {

    pid_t pid;

    if ((pid = fork()) < 0) {
        __android_log_print(ANDROID_LOG_ERROR, TAG, "fork proc failed");
        exit(-1);
    } else if (pid == 0) {
        //child proc

        if ((pid = fork()) < 0) {
            __android_log_print(ANDROID_LOG_ERROR, TAG, "child proc fork proc failed");
            exit(-1);
        } else if (pid >= 0) {
            exit(0);
        }
        //孤儿进程,锁定Origin文件

        char *c_daemon_path = const_cast<char *>(env->GetStringUTFChars(daemon_path, 0));
        char *c_origin_path = const_cast<char *>(env->GetStringUTFChars(origin_path, 0));
        int fd = open(c_origin_path, O_RDONLY);
        flock(fd, LOCK_EX);

        sleep(3);

        int fd_deamon = open(c_daemon_path, O_RDONLY);

        flock(fd_deamon, LOCK_EX);

        jclass cls = env->GetObjectClass(thiz);
        jmethodID cb_method = env->GetMethodID(cls, "deamonDead", "()V");
        env->CallVoidMethod(thiz, cb_method);
    }

}