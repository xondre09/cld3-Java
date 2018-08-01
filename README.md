# Cld3-Java
Cld3-Java is a wrapper of [CLD3](https://github.com/google/cld3) for Java.

## Installation
### Debian/Ubuntu
```
sudo aptitude install protobuf-compiler libprotobuf-dev cmake
ant jar
```
## Example
```java
NNetLanguageIdentifierWrapper identifier = new NNetLanguageIdentifierWrapper(0, 1000);

String text = "This text is written in English.";
NNetLanguageIdentifierWrapper.Result result = identifier.findLanguage(text);

System.out.println("Text: " + text);
System.out.println("  language:    " + result.language);
System.out.println("  probability: " + result.probability);
System.out.println("  reliable:    " + result.is_reliable);
System.out.println("  proportion:  " + result.proportion);

text = "This text is written in English. Text napsaný v češtině";
NNetLanguageIdentifierWrapper.Result[] results = identifier.findTopNMostFreqLangs(text, 2);

identifier.dispose();
```
