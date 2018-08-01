package cz.vutbr.fit.knot;

/*
 * Copyright 2018 Karel Ondřej.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Wrapper of C++ class NNetLanguageIdentifier from project CLD3 (see https://github.com/google/cld3).
 *
 * @author Karel Ondřej
 */

public class NNetLanguageIdentifierWrapper {
    /* Load library with JNI interface. */
    static {
        final String nativeLib = "forcld3";
        final String nativeLibName = System.mapLibraryName(nativeLib);
    
        try {
            System.loadLibrary(nativeLib);
        } catch (UnsatisfiedLinkError exception) {
            // inspired by https://github.com/adamheinrich/native-utils
            try {
                // create a temporary directory
                File tmpDir = new File(System.getProperty("java.io.tmpdir"), "native-" + nativeLib + "-" + System.nanoTime());
                if (!tmpDir.mkdir()) throw new IOException("Failed to create temporary directory.");
                tmpDir.deleteOnExit();
                // copy the shared library to the temporary directory
                File tmpFile = new File(tmpDir, nativeLibName);
                InputStream srcFile = NNetLanguageIdentifierWrapper.class.getResourceAsStream("/" + nativeLibName);
                Files.copy(srcFile, tmpFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                tmpFile.deleteOnExit();
                // load the shared library
                System.load(tmpFile.getAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (NullPointerException e) {
                throw new RuntimeException("File '" + nativeLibName + "' was not found inside JAR.");
            }
        }
    }

    /**
     * Information about predicted language.
     */
    public static class Result {
        public String language = NNetLanguageIdentifierWrapper.getUnknown();
        public float probability = 0.f;    //< Language probability.
        public boolean isReliable = false; //< If the prediction is reliable
        public float proportion = 0.f;     //< Proportion of bytes of predicted languages in the text. For findLanguage always 1.f.

        public Result(String language, float probability, boolean isReliable, float proportion) {
            this.language = language;
            this.probability = probability;
            this.isReliable = isReliable;
            this.proportion = proportion;
        }
    }

    private long identifierCppPtr;  //< C++ pointer to an instance of the NNetLanguageIdentifier class as a long data type.

    /**
     * Constructor.
     */
    public NNetLanguageIdentifierWrapper() {
        identifierCppPtr = NNetLanguageIdentifierWrapper.newNNetLanguageIdentifier();
    }
    /**
     * Constructor.
     *
     * @param minNumBytes Minimum number of bytes to make prediction.
     * @param maxNumBytes Maximum number of bytes to make prediction.
     */
    public NNetLanguageIdentifierWrapper(int minNumBytes, int maxNumBytes) {
        identifierCppPtr = NNetLanguageIdentifierWrapper.newNNetLanguageIdentifier(minNumBytes, maxNumBytes);
    }

    /**
     * Releases resources.
     */
    public void dispose() {
        if(identifierCppPtr != 0) {
            NNetLanguageIdentifierWrapper.deleteNNetLanguageIdentifier(identifierCppPtr);
            identifierCppPtr = 0;
        }
    }

    /**
     * Find the most probability language for input text with addition information (see Result).
     *
     * @param text Input text to process.
     * @return Language of input text.
     */
    public native Result findLanguage(String text);

    /**
     * Find the top num_langs most frequent languages for input text. Only the first MaxNumInputBytesToConsider (see getMaxNumInputBytesToConsider) bytes is processed.
     *
     * @param text Input text to process.
     * @param numLangs Required number of languages.
     * @return Array of the most frequent languages of input text.
     */
    public native Result[] findTopNMostFreqLangs(String text, int numLangs);

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

    /**
     * Constructor of C++ class NNetLanguageIdentifier.
     *
     * @return C++ pointer to an instance of the NNetLanguageIdentifier class as a long data type.
     */
    private static native long newNNetLanguageIdentifier();

    /**
     * Constructor of C++ class NNetLanguageIdentifier.
     *
     * @param minNumBytes Minimum number of bytes to make prediction.
     * @param maxNumBytes Maximum number of bytes to make prediction.
     * @return C++ pointer to an instance of the NNetLanguageIdentifier class as a long data type.
     */
    private static native long newNNetLanguageIdentifier(int minNumBytes, int maxNumBytes);

    /**
     * Destructor of C++ class NNetLanguageIdentifier.
     *
     * @param identifierCppPtr C++ pointer to an instance of the NNetLanguageIdentifier class as a long data type.
     */
    private static native void deleteNNetLanguageIdentifier(long identifierCppPtr);
}
