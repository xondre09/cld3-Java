# Cld3-Java
Cld3-Java is a JNI connector of CLD3.

## Installation
### Debian
```
sudo aptitude install protobuf-compiler libprotobuf-dev cmake
mkdir build
cd build
cmake ..
make
```
## Examples
```java
LanguageIdentifier identifier = new LanguageIdentifier(0, 1000);

String text = "This text is written in English.";
LanguageIdentifier.Result result = identifier.findLanguage(text);

System.out.println("Text: " + text);
System.out.println("  language:    " + result.language);
System.out.println("  probability: " + result.probability);
System.out.println("  reliable:    " + result.is_reliable);
System.out.println("  proportion:  " + result.proportion);

String text = "This text is written in English. Text napsaný v češtině";
LanguageIdentifier.Result[] results = identifier.findTopNMostFreqLangs(text, 3);

identifier.dispose();
```
