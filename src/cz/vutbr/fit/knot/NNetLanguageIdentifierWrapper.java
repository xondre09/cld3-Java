package cz.vutbr.fit.knot;

/**
 * Wrapper of C++ class NNetLanguageIdentifier from project CLD3 (see https://github.com/google/cld3).
 *
 * @author Karel Ondřej
 */

public class NNetLanguageIdentifierWrapper {

    /* Load library with JNI interface. */
    static {
        System.loadLibrary("forcld3");
    }

    /**
     * Information about predicted language.
     */
    public static class Result {
        public String language = NNetLanguageIdentifierWrapper.getUnknown();
        public float probability = 0.f;     //< Language probability.
        public boolean is_reliable = false; //< If the prediction is reliable
        public float proportion = 0.f;      //< Proportion of bytes of predicted languages ​​in the text. For findLanguage always 1.f.

        public Result(String language, float probability, boolean is_reliable, float proportion) {
            this.language = language;
            this.probability = probability;
            this.is_reliable = is_reliable;
            this.proportion = proportion;
        }
    }

    private long identifierCppPtr;  //< C++ pointer to an instance of the NNetLanguageIdentifier class as a long data type.

    /**
     * Constructor.
     */
    public NNetLanguageIdentifierWrapper() {
        identifierCppPtr = this.newNNetLanguageIdentifier();
    }
    /**
     * Constructor.
     *
     * @param min_num_bytes Minimum number of bytes to make prediction.
     * @param max_num_bytes Maximum number of bytes to make prediction.
     */
    public NNetLanguageIdentifierWrapper(int min_num_bytes, int max_num_bytes) {
        identifierCppPtr = this.newNNetLanguageIdentifier(min_num_bytes, max_num_bytes);
    }

    /**
     * Releases resources.
     */
    public void dispose() {
        if(identifierCppPtr != 0) {
            this.deleteNNetLanguageIdentifier(identifierCppPtr);
            identifierCppPtr = 0;
        }
    }

    /**
     * Find the most probability language for input text with addition information (see Result).
     *
     * @param cppPtr C++ pointer to an instance of the NNetLanguageIdentifier class as a long data type.
     * @param text Input text to process.
     * @return Language of input text.
     */
    public native Result findLanguage(String text);

    /**
     * Find the top num_langs most frequent languages for input text. Only the first MaxNumInputBytesToConsider (see getMaxNumInputBytesToConsider) bytes is processed.
     *
     * @param cppPtr C++ pointer to an instance of the NNetLanguageIdentifier class as a long data type.
     * @param text Input text to process.
     * @param num_langs Required number of languages.
     * @return Array of the most frequent languages of input text.
     */
    public native Result[] findTopNMostFreqLangs(String text, int num_langs);

    // ===========================
    //       GET CONSTANTS
    // ===========================

    /**
     * Returns the constant of an unknown language or a failing prediction.
     *
     * @return Value of constant NNetLanguageIdentifier::kUnknown.
     */
    public static native String getUnknown();

    /**
     * Returns default minimum number of bytes to make prediction.
     *
     * @return Value of constant NNetLanguageIdentifier::kMinNumBytesToConsider.
     */
    public static native int getMinNumBytesToConsider();

    /**
     * Returns default maximum number of bytes to make prediction.
     *
     * @return Value of constant NNetLanguageIdentifier::kMaxNumBytesToConsider.
     */
    public static native int getMaxNumBytesToConsider();

    /**
     * Returns maximum number of input bytes to process.
     *
     * @return Value of constant NNetLanguageIdentifier::kMaxNumInputBytesToConsider.
     */
    public static native int getMaxNumInputBytesToConsider();

    /**
     * Returns reliable thresholds for prediction. If probability greater than or equal to this threshold are prediction marked as reliable.
     *
     * @return Value of constant NNetLanguageIdentifier::kReliabilityThreshold.
     */
    public static native float getReliabilityThreshold();

    /**
     * Retuns reliability threshold for Croatian (hr) and Bosnian (bs) languages.
     *
     * @return Value of constant NNetLanguageIdentifier::kReliabilityThreshold.
     */
    public static native float getReliabilityHrBsThreshold();

    // ==============================================
    //       C++ CONSTRUCTORS AND DESTRUCTOR
    // ==============================================

    /**
     * Constructor of C++ class NNetLanguageIdentifier.
     *
     * @return C++ pointer to an instance of the NNetLanguageIdentifier class as a long data type.
     */
    private static native long newNNetLanguageIdentifier();

    /**
     * Constructor of C++ class NNetLanguageIdentifier.
     *
     * @param min_num_bytes Minimum number of bytes to make prediction.
     * @param max_num_bytes Maximum number of bytes to make prediction.
     * @return C++ pointer to an instance of the NNetLanguageIdentifier class as a long data type.
     */
    private static native long newNNetLanguageIdentifier(int min_num_bytes, int max_num_bytes);

    /**
     * Destructor of C++ class NNetLanguageIdentifier.
     *
     * @param identifierCppPtr C++ pointer to an instance of the NNetLanguageIdentifier class as a long data type.
     */
    private static native void deleteNNetLanguageIdentifier(long identifierCppPtr);
}
