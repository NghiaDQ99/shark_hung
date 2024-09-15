#include <jni.h>
#include <string>
#include <iostream>
#include "constants.h.in"

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_myapplication_view_MainActivity_getAESKey(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF("AES_KEY");
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_myapplication_view_MainActivity_getAESIv(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF("AES_IV");
}