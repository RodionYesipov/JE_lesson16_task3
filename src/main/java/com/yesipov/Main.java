package com.yesipov;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 Написать метод:
 public int uniqueMorseRepresentations(String[] words) {
 }

 Unique Morse Code Words
 International Morse Code defines a standard encoding where each letter is mapped to a series of dots and dashes, as follows: "a" maps to ".-", "b" maps to "-...", "c" maps to "-.-.", and so on.
 For convenience, the full table for the 26 letters of the English alphabet is given below:
 [".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."]
 Now, given a list of words, each word can be written as a concatenation of the Morse code of each letter. For example, "cba" can be written as "-.-..--...", (which is the concatenation "-.-." + "-..." + ".-"). We'll call such a concatenation, the transformation of a word.
 Return the number of different transformations among all words we have.

 Example:

 Input: words = ["gin", "zen", "gig", "msg"]

 Output: 2
 Explanation:
 The transformation of each word is:
 "gin" -> "--...-."
 "zen" -> "--...-."
 "gig" -> "--...--."
 "msg" -> "--...--."
 There are 2 different transformations, "--...-." and "--...--.".
**/


public class Main {
    final static Map<Character, String> MORSEMAP = deserializeToMap("morseCatalog.txt");

    private static Map<Character, String> deserializeToMap(String fileName) {
        try {
            String json = FileUtils.readFileToString(new File(fileName), "utf-8");
            Gson gson = new Gson();
            List morseList = new ArrayList<>();
            morseList = gson.fromJson(json, List.class);

            return morseListToMap(morseList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    ///
    private static Map<Character, String> morseListToMap(List<String> morseList) {
        Map<Character, String> morseMap = new HashMap<>();
        Character firstLetter = 'a';

        for (int i = 0; i < morseList.size(); i++) {
            morseMap.put((char) (firstLetter + i), morseList.get(i));
        }

        return morseMap;
    }

    ///
    private static String wordToMorse(String word) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char letter : word.toCharArray()) {
            stringBuilder.append(MORSEMAP.get(letter));
        }
        return stringBuilder.toString();
    }

    ///
    private static List<String> wordsToMorseList(List<String> inputWords) {
        List<String> outputMorseList = new ArrayList<>();
        for (String word : inputWords) {
            outputMorseList.add(wordToMorse(word));
        }

        return outputMorseList;
    }

    ///
    public static int uniqueMorseRepresentations(String[] words) {
        List<String> inputWords = new ArrayList<>(Arrays.asList(words));
        return (int) wordsToMorseList(inputWords).stream().distinct().count();
    }

    //
    public static void main(String[] args) {
        String[] inputWords = {"gin", "zen", "gig", "msg"};

        System.out.println("Words array:\n" + wordsToMorseList(Arrays.asList(inputWords)));

        System.out.println("Transformations count:\n" + uniqueMorseRepresentations(inputWords));


    }
}
