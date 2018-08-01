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

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Karel Ondřej
 */
public class NNetLanguageIdentifierWrapperTest {
    
    static private NNetLanguageIdentifierWrapper identifier;
    
    public NNetLanguageIdentifierWrapperTest() { }
    
    @BeforeClass
    public static void setUpClass() {
        identifier = new NNetLanguageIdentifierWrapper(0, 1000);
    }
    
    @AfterClass
    public static void tearDownClass() {
        identifier.dispose();
    }

    /**
     * Test of dispose and findLanguage methods, of class NNetLanguageIdentifierWrapper.
     */
    @Test(expected = NullPointerException.class)
    public void testDisposeFindLanguage() {
        NNetLanguageIdentifierWrapper instance = new NNetLanguageIdentifierWrapper();
        instance.dispose();
        instance.findLanguage("");
    }

    /**
     * Test of dispose and findTopNMostFreqLangs methods, of class NNetLanguageIdentifierWrapper.
     */
    @Test(expected = NullPointerException.class)
    public void testDisposeFindTopNMostFreqLangs() {
        NNetLanguageIdentifierWrapper instance = new NNetLanguageIdentifierWrapper();
        instance.dispose();
        instance.findTopNMostFreqLangs("", 3);
    }
    /**
     * Test of findLanguage method, of class NNetLanguageIdentifierWrapper.
     */
    @Test
    public void testFindLanguage() {
        String text = "This text is written in English.";
        NNetLanguageIdentifierWrapper.Result result = identifier.findLanguage(text);
        assertTrue(result.isReliable);
        assertEquals("en", result.language);
    }

    /**
     * Test of findTopNMostFreqLangs method, of class NNetLanguageIdentifierWrapper.
     */
    @Test
    public void testFindTopNMostFreqLangs() {
        String text = "This piece of text is in English. Този текст е на Български.";
        int numLangs = 3;
        String[] expResult = {"bg", "en", NNetLanguageIdentifierWrapper.getUnknown()};
        NNetLanguageIdentifierWrapper.Result[] result = identifier.findTopNMostFreqLangs(text, numLangs);

        assertEquals(expResult[0], result[0].language);
        assertEquals(expResult[1], result[1].language);
        assertEquals(expResult[2], result[2].language);
    }

    /**
     * Test of getUnknown method, of class NNetLanguageIdentifierWrapper.
     */
    @Test
    public void testGetUnknown() {
        String expResult = "und";
        String result = NNetLanguageIdentifierWrapper.getUnknown();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMinNumBytesToConsider method, of class NNetLanguageIdentifierWrapper.
     */
    @Test
    public void testGetMinNumBytesToConsider() {
        int expResult = 140;
        int result = NNetLanguageIdentifierWrapper.getMinNumBytesToConsider();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMaxNumBytesToConsider method, of class NNetLanguageIdentifierWrapper.
     */
    @Test
    public void testGetMaxNumBytesToConsider() {
        int expResult = 700;
        int result = NNetLanguageIdentifierWrapper.getMaxNumBytesToConsider();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMaxNumInputBytesToConsider method, of class NNetLanguageIdentifierWrapper.
     */
    @Test
    public void testGetMaxNumInputBytesToConsider() {
        int expResult = 10000;
        int result = NNetLanguageIdentifierWrapper.getMaxNumInputBytesToConsider();
        assertEquals(expResult, result);
    }

    /**
     * Test of getReliabilityThreshold method, of class NNetLanguageIdentifierWrapper.
     */
    @Test
    public void testGetReliabilityThreshold() {
        float expResult = 0.7F;
        float result = NNetLanguageIdentifierWrapper.getReliabilityThreshold();
        assertEquals(expResult, result, 1.e-10);
    }

    /**
     * Test of getReliabilityHrBsThreshold method, of class NNetLanguageIdentifierWrapper.
     */
    @Test
    public void testGetReliabilityHrBsThreshold() {
        float expResult = 0.5F;
        float result = NNetLanguageIdentifierWrapper.getReliabilityHrBsThreshold();
        assertEquals(expResult, result, 1.e-10);
    }
}
