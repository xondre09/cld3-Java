/**
 * JNI interface to C++ class NNetLanguageIdentifier (see (https://github.com/google/cld3).
 *
 * @autor Karel Ondřej
 */

#include "NNetLanguageIdentifierWrapper.h"
#include "nnet_language_identifier.h"

#define RESULT_CLASS_NAME "cz/vutbr/fit/knot/NNetLanguageIdentifierWrapper$Result"
#define IDENTIFIER_CPP_PTR "identifierCppPtr"

using chrome_lang_id::NNetLanguageIdentifier;

JNIEXPORT jobject JNICALL Java_cz_vutbr_fit_knot_NNetLanguageIdentifierWrapper_findLanguage
  (JNIEnv *env, jobject thisObject, jstring text)
{
    // get pointer to NNetLanguageIdentifier instace
    jclass cls = env->GetObjectClass(thisObject);
    jfieldID fid = env->GetFieldID(cls, IDENTIFIER_CPP_PTR, "J");
    NNetLanguageIdentifier* cppPtr = (NNetLanguageIdentifier*)env->GetLongField(thisObject, fid);

    const char *org = env->GetStringUTFChars(text, NULL);
    const NNetLanguageIdentifier::Result result = cppPtr->FindLanguage(org);
    env->ReleaseStringUTFChars(text, org);
    jstring language = env->NewStringUTF(result.language.c_str());

    // New result object
    jclass result_class = env->FindClass(RESULT_CLASS_NAME);
    if (!result_class) return NULL;
    jmethodID result_init = env->GetMethodID(result_class, "<init>", "(Ljava/lang/String;FZF)V");
    if (!result_init) return NULL;
    return env->NewObject(result_class, result_init, language, result.probability, result.is_reliable, result.proportion);
}

JNIEXPORT jobjectArray JNICALL Java_cz_vutbr_fit_knot_NNetLanguageIdentifierWrapper_findTopNMostFreqLangs
  (JNIEnv *env, jobject thisObject, jstring text, jint num_langs)
{
    // get pointer to NNetLanguageIdentifier instace
    jclass cls = env->GetObjectClass(thisObject);
    jfieldID fid = env->GetFieldID(cls, IDENTIFIER_CPP_PTR, "J");
    NNetLanguageIdentifier* cppPtr = (NNetLanguageIdentifier*)env->GetLongField(thisObject, fid);

    const char *org = env->GetStringUTFChars(text, NULL);
    const std::vector<NNetLanguageIdentifier::Result> results = cppPtr->FindTopNMostFreqLangs(org, num_langs);
    env->ReleaseStringUTFChars(text, org);

    jclass result_class = env->FindClass(RESULT_CLASS_NAME);
    jmethodID result_init = env->GetMethodID(result_class, "<init>", "(Ljava/lang/String;FZF)V");

    // Create and fill array of results
    jobjectArray array = env->NewObjectArray(results.size(), result_class, NULL);
    for (size_t i = 0; i < results.size(); i++) {
        jstring language = env->NewStringUTF(results[i].language.c_str());
        jobject item = env->NewObject(result_class, result_init, language, results[i].probability, results[i].is_reliable, results[i].proportion);
        env->SetObjectArrayElement(array, i, item);
        env->DeleteLocalRef(item);
    }
  return array;
}

JNIEXPORT jstring JNICALL Java_cz_vutbr_fit_knot_NNetLanguageIdentifierWrapper_getUnknown
  (JNIEnv *env, jclass cls)
{
    (void*)env;
    (void*)cls;
    return env->NewStringUTF(NNetLanguageIdentifier::kUnknown);
}

JNIEXPORT jint JNICALL Java_cz_vutbr_fit_knot_NNetLanguageIdentifierWrapper_getMinNumBytesToConsider
  (JNIEnv *env, jclass cls)
{
    (void*)env;
    (void*)cls;
    return (jint)NNetLanguageIdentifier::kMinNumBytesToConsider;
}

JNIEXPORT jint JNICALL Java_cz_vutbr_fit_knot_NNetLanguageIdentifierWrapper_getMaxNumBytesToConsider
  (JNIEnv *env, jclass cls)
{
    (void*)env;
    (void*)cls;
    return (jint)NNetLanguageIdentifier::kMaxNumBytesToConsider;
}

JNIEXPORT jint JNICALL Java_cz_vutbr_fit_knot_NNetLanguageIdentifierWrapper_getMaxNumInputBytesToConsider
  (JNIEnv *env, jclass cls)
{
    (void*)env;
    (void*)cls;
    return (jint)NNetLanguageIdentifier::kMaxNumInputBytesToConsider;
}

JNIEXPORT jfloat JNICALL Java_cz_vutbr_fit_knot_NNetLanguageIdentifierWrapper_getReliabilityThreshold
  (JNIEnv *env, jclass cls)
{
    (void*)env;
    (void*)cls;
    return (jfloat)NNetLanguageIdentifier::kReliabilityThreshold;
}

JNIEXPORT jfloat JNICALL Java_cz_vutbr_fit_knot_NNetLanguageIdentifierWrapper_getReliabilityHrBsThreshold
  (JNIEnv *env, jclass cls)
{
    (void*)env;
    (void*)cls;
    return (jfloat)NNetLanguageIdentifier::kReliabilityHrBsThreshold;
}

JNIEXPORT jlong JNICALL Java_cz_vutbr_fit_knot_NNetLanguageIdentifierWrapper_newNNetLanguageIdentifier__
  (JNIEnv *env,  jclass cls)
{
    (void*)env;
    (void*)cls;
    return (jlong)(new NNetLanguageIdentifier());
}

JNIEXPORT jlong JNICALL Java_cz_vutbr_fit_knot_NNetLanguageIdentifierWrapper_newNNetLanguageIdentifier__II
  (JNIEnv *env, jclass cls, jint min_num_bytes, jint max_num_bytes)
{
    (void*)env;
    (void*)cls;
    return (jlong)(new NNetLanguageIdentifier(min_num_bytes, max_num_bytes));
}

JNIEXPORT void JNICALL Java_cz_vutbr_fit_knot_NNetLanguageIdentifierWrapper_deleteNNetLanguageIdentifier
  (JNIEnv *env, jclass cls, jlong cppPtr)
{
    (void*)env;
    (void*)cls;
    delete (NNetLanguageIdentifier*)cppPtr;
}
