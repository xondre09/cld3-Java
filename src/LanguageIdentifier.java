/**
 * Třída detekce jazyka z textu.
 *
 * @author Karel Ondřej
 */

public class LanguageIdentifier extends NNetLanguageIdentifierWrapper {
    private long cppPtr;

    protected LanguageIdentifier(long cppPtr) {
        this.cppPtr = cppPtr;
    }

    public LanguageIdentifier() {
        this(newNNetLanguageIdentifier());
    }

    public LanguageIdentifier(int min_num_bytes, int max_num_bytes) {
        this(newNNetLanguageIdentifier(min_num_bytes, max_num_bytes));
    }

    public void dispose() {
        if(cppPtr != 0) {
            this.deleteNNetLanguageIdentifier(cppPtr);
            cppPtr = 0;
        }
    }

    public Result findLanguage(String text) {
        return findLanguage(cppPtr, text);
    }

    public Result[] findTopNMostFreqLangs(String text, int num_langs) {
        return findTopNMostFreqLangs(cppPtr, text, num_langs);
    }
}
