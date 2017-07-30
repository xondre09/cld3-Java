/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class NNetLanguageIdentifierWrapper */

#ifndef _Included_NNetLanguageIdentifierWrapper
#define _Included_NNetLanguageIdentifierWrapper
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     NNetLanguageIdentifierWrapper
 * Method:    findLanguage
 * Signature: (Ljava/lang/String;)LNNetLanguageIdentifierWrapper/Result;
 */
JNIEXPORT jobject JNICALL Java_NNetLanguageIdentifierWrapper_findLanguage
  (JNIEnv *, jobject, jstring);

/*
 * Class:     NNetLanguageIdentifierWrapper
 * Method:    findTopNMostFreqLangs
 * Signature: (Ljava/lang/String;I)[LNNetLanguageIdentifierWrapper/Result;
 */
JNIEXPORT jobjectArray JNICALL Java_NNetLanguageIdentifierWrapper_findTopNMostFreqLangs
  (JNIEnv *, jobject, jstring, jint);

/*
 * Class:     NNetLanguageIdentifierWrapper
 * Method:    getUnknown
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_NNetLanguageIdentifierWrapper_getUnknown
  (JNIEnv *, jclass);

/*
 * Class:     NNetLanguageIdentifierWrapper
 * Method:    getMinNumBytesToConsider
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_NNetLanguageIdentifierWrapper_getMinNumBytesToConsider
  (JNIEnv *, jclass);

/*
 * Class:     NNetLanguageIdentifierWrapper
 * Method:    getMaxNumBytesToConsider
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_NNetLanguageIdentifierWrapper_getMaxNumBytesToConsider
  (JNIEnv *, jclass);

/*
 * Class:     NNetLanguageIdentifierWrapper
 * Method:    getMaxNumInputBytesToConsider
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_NNetLanguageIdentifierWrapper_getMaxNumInputBytesToConsider
  (JNIEnv *, jclass);

/*
 * Class:     NNetLanguageIdentifierWrapper
 * Method:    getReliabilityThreshold
 * Signature: ()F
 */
JNIEXPORT jfloat JNICALL Java_NNetLanguageIdentifierWrapper_getReliabilityThreshold
  (JNIEnv *, jclass);

/*
 * Class:     NNetLanguageIdentifierWrapper
 * Method:    getReliabilityHrBsThreshold
 * Signature: ()F
 */
JNIEXPORT jfloat JNICALL Java_NNetLanguageIdentifierWrapper_getReliabilityHrBsThreshold
  (JNIEnv *, jclass);

/*
 * Class:     NNetLanguageIdentifierWrapper
 * Method:    newNNetLanguageIdentifier
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_NNetLanguageIdentifierWrapper_newNNetLanguageIdentifier__
  (JNIEnv *, jclass);

/*
 * Class:     NNetLanguageIdentifierWrapper
 * Method:    newNNetLanguageIdentifier
 * Signature: (II)J
 */
JNIEXPORT jlong JNICALL Java_NNetLanguageIdentifierWrapper_newNNetLanguageIdentifier__II
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     NNetLanguageIdentifierWrapper
 * Method:    deleteNNetLanguageIdentifier
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_NNetLanguageIdentifierWrapper_deleteNNetLanguageIdentifier
  (JNIEnv *, jclass, jlong);

#ifdef __cplusplus
}
#endif
#endif
