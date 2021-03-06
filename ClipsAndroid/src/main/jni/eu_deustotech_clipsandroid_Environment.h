/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class eu_deustotech_clipsandroid_Environment */

#ifndef _Included_eu_deustotech_clipsandroid_Environment
#define _Included_eu_deustotech_clipsandroid_Environment
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    getCLIPSVersion
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_eu_deustotech_clipsandroid_Environment_getCLIPSVersion
  (JNIEnv *, jclass);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    createEnvironment
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_eu_deustotech_clipsandroid_Environment_createEnvironment
  (JNIEnv *, jobject);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    clear
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_eu_deustotech_clipsandroid_Environment_clear
  (JNIEnv *, jobject, jlong);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    reset
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_eu_deustotech_clipsandroid_Environment_reset
  (JNIEnv *, jobject, jlong);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    load
 * Signature: (JLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_eu_deustotech_clipsandroid_Environment_load
  (JNIEnv *, jobject, jlong, jstring);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    loadFacts
 * Signature: (JLjava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_eu_deustotech_clipsandroid_Environment_loadFacts
  (JNIEnv *, jobject, jlong, jstring);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    watch
 * Signature: (JLjava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_eu_deustotech_clipsandroid_Environment_watch
  (JNIEnv *, jobject, jlong, jstring);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    unwatch
 * Signature: (JLjava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_eu_deustotech_clipsandroid_Environment_unwatch
  (JNIEnv *, jobject, jlong, jstring);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    run
 * Signature: (JJ)J
 */
JNIEXPORT jlong JNICALL Java_eu_deustotech_clipsandroid_Environment_run
  (JNIEnv *, jobject, jlong, jlong);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    eval
 * Signature: (JLjava/lang/String;)Leu/deustotech/clipsandroid/PrimitiveValue;
 */
JNIEXPORT jobject JNICALL Java_eu_deustotech_clipsandroid_Environment_eval
  (JNIEnv *, jobject, jlong, jstring);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    build
 * Signature: (JLjava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_eu_deustotech_clipsandroid_Environment_build
  (JNIEnv *, jobject, jlong, jstring);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    assertString
 * Signature: (JLjava/lang/String;)Leu/deustotech/clipsandroid/FactAddressValue;
 */
JNIEXPORT jobject JNICALL Java_eu_deustotech_clipsandroid_Environment_assertString
  (JNIEnv *, jobject, jlong, jstring);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    factIndex
 * Signature: (Leu/deustotech/clipsandroid/Environment;JJ)J
 */
JNIEXPORT jlong JNICALL Java_eu_deustotech_clipsandroid_Environment_factIndex
  (JNIEnv *, jclass, jobject, jlong, jlong);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    getFactSlot
 * Signature: (Leu/deustotech/clipsandroid/Environment;JJLjava/lang/String;)Leu/deustotech/clipsandroid/PrimitiveValue;
 */
JNIEXPORT jobject JNICALL Java_eu_deustotech_clipsandroid_Environment_getFactSlot
  (JNIEnv *, jclass, jobject, jlong, jlong, jstring);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    makeInstance
 * Signature: (JLjava/lang/String;)Leu/deustotech/clipsandroid/InstanceAddressValue;
 */
JNIEXPORT jobject JNICALL Java_eu_deustotech_clipsandroid_Environment_makeInstance
  (JNIEnv *, jobject, jlong, jstring);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    getInstanceName
 * Signature: (Leu/deustotech/clipsandroid/Environment;JJ)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_eu_deustotech_clipsandroid_Environment_getInstanceName
  (JNIEnv *, jclass, jobject, jlong, jlong);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    directGetSlot
 * Signature: (Leu/deustotech/clipsandroid/Environment;JJLjava/lang/String;)Leu/deustotech/clipsandroid/PrimitiveValue;
 */
JNIEXPORT jobject JNICALL Java_eu_deustotech_clipsandroid_Environment_directGetSlot
  (JNIEnv *, jclass, jobject, jlong, jlong, jstring);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    destroyEnvironment
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_eu_deustotech_clipsandroid_Environment_destroyEnvironment
  (JNIEnv *, jobject, jlong);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    commandLoop
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_eu_deustotech_clipsandroid_Environment_commandLoop
  (JNIEnv *, jobject, jlong);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    getInputBuffer
 * Signature: (J)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_eu_deustotech_clipsandroid_Environment_getInputBuffer
  (JNIEnv *, jobject, jlong);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    setInputBuffer
 * Signature: (JLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_eu_deustotech_clipsandroid_Environment_setInputBuffer
  (JNIEnv *, jobject, jlong, jstring);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    getInputBufferCount
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_eu_deustotech_clipsandroid_Environment_getInputBufferCount
  (JNIEnv *, jobject, jlong);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    setInputBufferCount
 * Signature: (JJ)J
 */
JNIEXPORT jlong JNICALL Java_eu_deustotech_clipsandroid_Environment_setInputBufferCount
  (JNIEnv *, jobject, jlong, jlong);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    expandInputBuffer
 * Signature: (JC)V
 */
JNIEXPORT void JNICALL Java_eu_deustotech_clipsandroid_Environment_expandInputBuffer
  (JNIEnv *, jobject, jlong, jchar);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    appendInputBuffer
 * Signature: (JLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_eu_deustotech_clipsandroid_Environment_appendInputBuffer
  (JNIEnv *, jobject, jlong, jstring);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    inputBufferContainsCommand
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_eu_deustotech_clipsandroid_Environment_inputBufferContainsCommand
  (JNIEnv *, jobject, jlong);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    commandLoopOnceThenBatch
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_eu_deustotech_clipsandroid_Environment_commandLoopOnceThenBatch
  (JNIEnv *, jobject, jlong);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    printBanner
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_eu_deustotech_clipsandroid_Environment_printBanner
  (JNIEnv *, jobject, jlong);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    printPrompt
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_eu_deustotech_clipsandroid_Environment_printPrompt
  (JNIEnv *, jobject, jlong);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    addRouter
 * Signature: (JLjava/lang/String;ILeu/deustotech/clipsandroid/Router;)Z
 */
JNIEXPORT jboolean JNICALL Java_eu_deustotech_clipsandroid_Environment_addRouter
  (JNIEnv *, jobject, jlong, jstring, jint, jobject);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    incrementFactCount
 * Signature: (Leu/deustotech/clipsandroid/Environment;JJ)V
 */
JNIEXPORT void JNICALL Java_eu_deustotech_clipsandroid_Environment_incrementFactCount
  (JNIEnv *, jobject, jobject, jlong, jlong);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    decrementFactCount
 * Signature: (Leu/deustotech/clipsandroid/Environment;JJ)V
 */
JNIEXPORT void JNICALL Java_eu_deustotech_clipsandroid_Environment_decrementFactCount
  (JNIEnv *, jobject, jobject, jlong, jlong);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    incrementInstanceCount
 * Signature: (Leu/deustotech/clipsandroid/Environment;JJ)V
 */
JNIEXPORT void JNICALL Java_eu_deustotech_clipsandroid_Environment_incrementInstanceCount
  (JNIEnv *, jobject, jobject, jlong, jlong);

/*
 * Class:     eu_deustotech_clipsandroid_Environment
 * Method:    decrementInstanceCount
 * Signature: (Leu/deustotech/clipsandroid/Environment;JJ)V
 */
JNIEXPORT void JNICALL Java_eu_deustotech_clipsandroid_Environment_decrementInstanceCount
  (JNIEnv *, jobject, jobject, jlong, jlong);

#ifdef __cplusplus
}
#endif
#endif
