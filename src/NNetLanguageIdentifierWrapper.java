/**
 * Konektor C++ třídy NNetLanguageIdentifier.
 *
 * @author Karel Ondřej
 */

public class NNetLanguageIdentifierWrapper {
    /* Výsledek detekce jazyka */
    public static class Result {
        public String language = NNetLanguageIdentifierWrapper.getUnknownLanguage();
        public float probability = 0.f;
        public boolean is_reliable = false;
        public float proportion = 0.f;

        public Result(String language, float probability, boolean is_reliable, float proportion) {
            this.language = language;
            this.probability = probability;
            this.is_reliable = is_reliable;
            this.proportion = proportion;
        }
    }

    static {
        System.loadLibrary("forcld3");
    }

    /* Vrací konstantu pro neznámý jazyk */
    public static native String getUnknownLanguage();

    /* Konstruktor NNetLanguageIdentifier. */
    protected static native long newNNetLanguageIdentifier();

    /* Konstruktor NNetLanguageIdentifier. */
    protected static native long newNNetLanguageIdentifier(int min_num_bytes, int max_num_bytes);

    /* Destruktor NNetLanguageIdentifier */
    protected static native void deleteNNetLanguageIdentifier(long cppPtr);

    /* Identifikace jazyka ze zadaného textu. */
    protected static native Result findLanguage(long cppPtr, String text);

    /* Identifikace nejpravděpodobnějších jazyků ze zadaného textu. */
    protected static native Result[] findTopNMostFreqLangs(long cppPtr, String text, int num_langs);
}
