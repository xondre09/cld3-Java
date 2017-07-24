#include <iostream>
#include <map>
#include "NNetLanguageIdentifierWrapper.h"
#include "nnet_language_identifier.h"

using chrome_lang_id::NNetLanguageIdentifier;

JNIEXPORT jstring JNICALL Java_NNetLanguageIdentifierWrapper_getUnknownLanguage
  (JNIEnv *env, jclass cls)
{
    (void*)cls;
    return env->NewStringUTF(NNetLanguageIdentifier::kUnknown);
}

JNIEXPORT jlong JNICALL Java_NNetLanguageIdentifierWrapper_newNNetLanguageIdentifier__
  (JNIEnv *env, jclass cls)
{
    (void*) env;
    (void*) cls;
    return (jlong)(new NNetLanguageIdentifier());
}

JNIEXPORT jlong JNICALL Java_NNetLanguageIdentifierWrapper_newNNetLanguageIdentifier__II
  (JNIEnv *env, jclass cls, jint min_num_bytes, jint max_num_bytes)
{
    (void*) env;
    (void*) cls;
    return (jlong)(new NNetLanguageIdentifier(min_num_bytes, max_num_bytes));
}

JNIEXPORT void JNICALL Java_NNetLanguageIdentifierWrapper_deleteNNetLanguageIdentifier
  (JNIEnv *env, jclass cls, jlong cppPtr)
{
    (void*) env;
    (void*) cls;
    delete (NNetLanguageIdentifier*)cppPtr;
}

JNIEXPORT jobject JNICALL Java_NNetLanguageIdentifierWrapper_findLanguage
  (JNIEnv *env, jclass cls, jlong cppPtr, jstring text)
{
    (void*) cls;
    const char *org = env->GetStringUTFChars(text, NULL);
    const NNetLanguageIdentifier::Result result = ((NNetLanguageIdentifier*)cppPtr)->FindLanguage(org);
    env->ReleaseStringUTFChars(text, org);

    jclass result_class = env->FindClass("NNetLanguageIdentifierWrapper$Result");
    jmethodID result_init = env->GetMethodID(result_class, "<init>", "(Ljava/lang/String;FZF)V");

    jstring language = env->NewStringUTF(result.language.c_str());
    return env->NewObject(result_class, result_init, language, result.probability, result.is_reliable, result.proportion);
}

JNIEXPORT jobjectArray JNICALL Java_NNetLanguageIdentifierWrapper_findTopNMostFreqLangs
  (JNIEnv *env, jclass cls, jlong cppPtr, jstring text, jint num_langs)
  {
      (void*) cls;
      const char *org = env->GetStringUTFChars(text, NULL);
      const std::vector<NNetLanguageIdentifier::Result> results = ((NNetLanguageIdentifier*)cppPtr)->FindTopNMostFreqLangs(org, num_langs);
      env->ReleaseStringUTFChars(text, org);

      jclass result_class = env->FindClass("NNetLanguageIdentifierWrapper$Result");
      jmethodID result_init = env->GetMethodID(result_class, "<init>", "(Ljava/lang/String;FZF)V");

      jobjectArray array = env->NewObjectArray(results.size(), result_class, NULL);

      for (size_t i = 0; i < results.size(); i++) {
          jstring language = env->NewStringUTF(results[i].language.c_str());
          jobject item = env->NewObject(result_class, result_init, language, results[i].probability, results[i].is_reliable, results[i].proportion);
          env->SetObjectArrayElement(array, i, item);
          env->DeleteLocalRef(item);
      }
      return array;
  }
