#include <jni.h>
#include "library.h"

JNIEXPORT jint JNICALL Java_library_add(JNIEnv *env, jobject obj, jint a, jint b) {
    return a + b;
}

JNIEXPORT jint JNICALL Java_library_subtract(JNIEnv *env, jobject obj, jint a, jint b) {
    return a - b;
}

JNIEXPORT jint JNICALL Java_library_multiply(JNIEnv *env, jobject obj, jint a, jint b) {
    return a * b;
}

JNIEXPORT jdouble JNICALL Java_library_divide(JNIEnv *env, jobject obj, jint a, jint b) {
    if (b == 0) return 0;
    return (double)a / b;
}
