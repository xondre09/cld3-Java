class Example {
    public static void main(String[] args) {
        run();
    }

    public static void run() {

        LanguageIdentifier tmp = new LanguageIdentifier(0, 1000);

        System.out.println("----------------------------------");
        System.out.println("Test findLanguage(text)");
        System.out.println("----------------------------------\n");

        String[] texts = {"This text is written in English.", "Този текст е на Български.", "Text in deutscher Sprache verfasst.", "Tento text je napsaný v češtině.", "Tento text je napísaný v slovenčine."};

        for(String str : texts) {
            NNetLanguageIdentifierWrapper.Result r = tmp.findLanguage(str);
            System.out.println("Text: " + str);
            System.out.println("  language:    " + r.language);
            System.out.println("  probability: " + r.probability);
            System.out.println("  reliable:    " + r.is_reliable);
            System.out.println("  proportion:  " + r.proportion);
            System.out.println();
        }

        System.out.println("-----------------------------------");
        System.out.println("Test findTopNMostFreqLangs(text, 3)");
        System.out.println("-----------------------------------\n");

        String text = "This piece of text is in English. Този текст е на Български.";
        LanguageIdentifier.Result[] results = tmp.findTopNMostFreqLangs(text, 3);

        System.out.println("Text: " + text);
        for(LanguageIdentifier.Result r : results) {
            System.out.println("  language:    " + r.language);
            System.out.println("  probability: " + r.probability);
            System.out.println("  reliable:    " + r.is_reliable);
            System.out.println("  proportion:  " + r.proportion);
            System.out.println();
        }

        System.out.println("Neznámý jazyk: " + LanguageIdentifier.getUnknownLanguage());

        tmp.dispose();
    }

}
