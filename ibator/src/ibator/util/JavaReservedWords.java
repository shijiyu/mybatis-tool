package ibator.util;

import java.util.HashSet;
import java.util.Set;

public class JavaReservedWords
{
  public static Set<String> RESERVED_WORDS;

  static
  {
    String[] words = { "go", 
      "class", 
      "package", 
      "import", 
      "go", 
      "public", 
      "protected", 
      "static", 
      "String", 
      "private", 
      "Integer", 
      "Character", 
      "final", 
      "finally", 
      "throw", 
      "throws", 
      "try", 
      "catch", 
      "for", 
      "while", 
      "do", 
      "return", 
      "break", 
      "switch", 
      "Class", 
      "synchronized", 
      "StringBuffer", 
      "StringBuilder", 
      "AbstractMethodError", 
      "Object", 
      "Appendable", 
      "Override", 
      "ArithmeticException", 
      "Boolean", 
      "byte", 
      "Byte", 
      "char", 
      "int", 
      "Long", 
      "long", 
      "Double", 
      "double", 
      "Float", 
      "float", 
      "boolean", 
      "void", 
      "System", 
      "Math", 
      "Short", 
      "short", 
      "Thread", 
      "Time", 
      "Date", 
      "if", 
      "else", 
      "new", 
      "this", 
      "super", 
      "extends", 
      "implements", 
      "null" };

    RESERVED_WORDS = new HashSet<>(words.length);

    String[] arrayOfString1 = words; int j = words.length; for (int i = 0; i < j; i++) { String word = arrayOfString1[i];
      RESERVED_WORDS.add(word);
    }
  }

  public static boolean containsWord(String word)
  {
    boolean rc;
    if (word == null)
      rc = false;
    else {
      rc = RESERVED_WORDS.contains(word);
    }

    return rc;
  }

  public static void setRESERVED_WORDS(Set<String> reserved_words) {
    RESERVED_WORDS = reserved_words;
  }
}