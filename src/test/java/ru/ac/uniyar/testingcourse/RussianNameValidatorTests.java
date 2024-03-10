package ru.ac.uniyar.testingcourse;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

public class RussianNameValidatorTests {
    RussianNameValidator validator = new RussianNameValidator();

    static String createPartOfRussianName(char letter) {
        return String.valueOf(letter).toUpperCase() + letter;
    }

    static ArrayList<String> createListOfPartOfRussianNames() {
        ArrayList<String> tinyNames = new ArrayList<>();
        char[] alphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя".toCharArray();
        for (char letter : alphabet) {
            tinyNames.add(createPartOfRussianName(letter));
        }
        return tinyNames;
    }

    static ArrayList<String> verifiedTinyRussianNames() {
        ArrayList<String> listOfNameParts = createListOfPartOfRussianNames();
        ArrayList<String> listOfTinyVerifiedNames = new ArrayList<>();
        for (String part : listOfNameParts) {
            listOfTinyVerifiedNames.add(part + "-" + part + " " + part + " " + part);
            listOfTinyVerifiedNames.add(part + " " + part + "-" + part + " " + part);
            listOfTinyVerifiedNames.add(part + "-" + part + " " + part + "-" + part + " " + part);
            listOfTinyVerifiedNames.add(part + " " + part + " " + part);
            listOfTinyVerifiedNames.add(part + " " + part);
            listOfTinyVerifiedNames.add(part + "-" + part + " " + part);
            listOfTinyVerifiedNames.add(part + " " + part + "-" + part);
            listOfTinyVerifiedNames.add(part + "-" + part + " " + part + "-" + part);
        }
        return listOfTinyVerifiedNames;
    }

    /**
     * Проверка:
     * проверка имен на валидность с:
     * всеми русскими буквами (Прописная + строчная),
     * разными расстановками дефисов,
     * существованием/отсутсвием отчества
     * <p>
     * Действие:
     * создать объект RussianNameValidator validator
     * составить список из 33 пар букв русского алфавита, где первая буква - прописная, вторая - строчная;
     * составить слова со всеми возможными шаблонами (*-* * *;* *-* *;*-* *-* *;* * *;* *; *-* *;* *-*;*-* *-*), где * - одна из пар;
     * проверить каждое имя через validator.validate
     * <p>
     * Ожидание:
     * validator.validate выдаст True на всех парах
     */
    @ParameterizedTest
    @MethodSource("verifiedTinyRussianNames")
    void testVerifiedNames(String verifiedName) {
        assertThat(validator.validate(verifiedName)).isTrue();
    }

    static ArrayList<String> namesWithSmallRussianLetters() {
        char[] alphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя".toCharArray();
        ArrayList<String> listOfNamesWithSmallRussianLetters = new ArrayList<>();
        for (char letter : alphabet) {
            listOfNamesWithSmallRussianLetters.add(letter + "-" + letter + " " + letter + " " + letter);
            listOfNamesWithSmallRussianLetters.add(letter + " " + letter + "-" + letter + " " + letter);
            listOfNamesWithSmallRussianLetters.add(letter + "-" + letter + " " + letter + "-" + letter + " " + letter);
            listOfNamesWithSmallRussianLetters.add(letter + " " + letter + " " + letter);
            listOfNamesWithSmallRussianLetters.add(letter + " " + letter);
            listOfNamesWithSmallRussianLetters.add(letter + "-" + letter + " " + letter);
            listOfNamesWithSmallRussianLetters.add(letter + " " + letter + "-" + letter);
            listOfNamesWithSmallRussianLetters.add(letter + "-" + letter + " " + letter + "-" + letter);
        }
        return listOfNamesWithSmallRussianLetters;
    }

    /**
     * Проверка:
     * Проверка имен, составленных только из строчных букв
     * <p>
     * Действие:
     * создать объект RussianNameValidator validator
     * составить список прописных букв русского алфавита
     * составить слова со всеми возможными шаблонами (*-* * *;* *-* *;*-* *-* *;* * *;* *; *-* *;* *-*;*-* *-*), где * - каждая буква
     * проверить каждое имя через validator.validate
     * <p>
     * Ожидание:
     * validator.validate выдаст False на всех именах
     */
    @ParameterizedTest
    @MethodSource("namesWithSmallRussianLetters")
    void testFailedNamesWithSmallRussianLetters(String failedName) {
        assertThat(validator.validate(failedName)).isFalse();
    }

    static String createPartOfEnglishName(char letter) {
        return String.valueOf(letter).toUpperCase() + letter;
    }

    static ArrayList<String> createListOfPartOfEnglishNames() {
        ArrayList<String> tinyNames = new ArrayList<>();
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        for (char letter : alphabet) {
            tinyNames.add(createPartOfEnglishName(letter));
        }
        return tinyNames;
    }

    static ArrayList<String> failedRussianNamesWithEnglishLetters() {
        ArrayList<String> listOfNameParts = createListOfPartOfEnglishNames();
        ArrayList<String> listOfTinyFailedEnglishNames = new ArrayList<>();
        for (String part : listOfNameParts) {
            listOfTinyFailedEnglishNames.add(part + "бб" + " " + "Бб" + " " + "Бб");
            listOfTinyFailedEnglishNames.add("Бб" + " " + part + "бб" + " " + "Бб");
            listOfTinyFailedEnglishNames.add("Бб" + " " + "Бб" + " " + part + "бб");
        }
        return listOfTinyFailedEnglishNames;
    }

    /**
     * Проверка:
     * проверка имен с латиницей и кириллицей
     * <p>
     * Действие:
     * создать объект RussianNameValidator validator
     * составить список из 26 пар букв английского алфавита, где первая буква - прописная, вторая - строчная;
     * составить слова по шаблонам (*бб Бб Бб;*Бб *бб Бб;Бб Бб *бб), где * - одна из пар
     * проверить каждое имя через validator.validate
     * <p>
     * Ожидание:
     * validator.validate выдаст False на всех именах
     */
    @ParameterizedTest
    @MethodSource("failedRussianNamesWithEnglishLetters")
    void testFailedRussianNamesWithEnglishLetters(String failedName) {
        assertThat(validator.validate(failedName)).isFalse();
    }

    static ArrayList<String> russianNamesWithIncorrectUseOfHyphens() {
        ArrayList<String> listOfRussianNamesWithIncorrectUseOfHyphens = new ArrayList<>();
        for (String part : verifiedTinyRussianNames()) {
            listOfRussianNamesWithIncorrectUseOfHyphens.add(part + "-" + part + "-" + part + " " + part);
            listOfRussianNamesWithIncorrectUseOfHyphens.add(part + " " + part + "-" + part + "-" + part);
            listOfRussianNamesWithIncorrectUseOfHyphens.add(part + " " + part + " " + part + "-" + part);
        }
        return listOfRussianNamesWithIncorrectUseOfHyphens;
    }

    /**
     * Проверка:
     * проверка имен с русскими буквами с неправльно поставленным дефисом
     * <p>
     * Действие:
     * создать объект RussianNameValidator validator
     * создать список из 33 пар букв русского алфавита, где первая буква - прописная, вторая - строчная;
     * создать список имен по шаблону (*-*-* *;* *-*-*;* * *-*), где * - одна из пар
     * проверить каждое имя через validator.validate
     * <p>
     * Ожидание:
     * validator.validate выдаст False на всех именах
     */
    @ParameterizedTest
    @MethodSource("russianNamesWithIncorrectUseOfHyphens")
    void testFailedNamesWithIncorrectUseOfHyphens(String failedName) {
        assertThat(validator.validate(failedName)).isFalse();
    }

    static ArrayList<String> russianNamesWithDigits() {
        char[] digits = "0123456789".toCharArray();
        ArrayList<String> listOfRussianNamesWithDigits = new ArrayList<>();
        for (char digit : digits) {
            listOfRussianNamesWithDigits.add(digit + " " + "Аа" + " " + "Аа");
            listOfRussianNamesWithDigits.add("Аa" + " " + digit + " " + "Аа");
            listOfRussianNamesWithDigits.add("Аa" + " " + "Аа" + " " + digit);
        }
        return listOfRussianNamesWithDigits;
    }

    /**
     * Проверка:
     * проверка имен с русскими буквами и цифрами
     * <p>
     * Действие:
     * создать объект RussianNameValidator validator
     * создать список цифр
     * создать список имен по шаблону (* Аа Аа;Аа * Аа;Аа Аа *), где * - каждая цифра
     * проверить каждое имя через validator.validate
     * <p>
     * Ожидание:
     * validator.validate выдаст False на всех именах
     */
    @ParameterizedTest
    @MethodSource("russianNamesWithDigits")
    void testFailedRussianNamesWithDigits(String failedName) {
        assertThat(validator.validate(failedName)).isFalse();
    }

    static ArrayList<String> russianNamesWithWrongPunctuation() {
        char[] punctuation = "!\"#$%&'()*+,./:;<=>?@[\\]^_`{|}~".toCharArray();
        ArrayList<String> listOfRussianNamesWithWrongPunctuation = new ArrayList<>();
        for (char symb : punctuation) {
            listOfRussianNamesWithWrongPunctuation.add("Бб" + symb + "-" + "Бб" + " " + "Бб");
            listOfRussianNamesWithWrongPunctuation.add("Бб" + " " + "Бб" + symb + "-" + "Бб");
        }
        return listOfRussianNamesWithWrongPunctuation;
    }

    /**
     * Проверка:
     * проверка имен из русских букв с пунктуационными знаками, кроме -
     * <p>
     * Действие:
     * создать объект RussianNameValidator validator
     * создать список пунктуационными знаков, кроме -
     * создать список имен по шаблону (Бб*-Бб Бб;Бб Бб*-Бб), где * - каждый знак из списка
     * проверить каждое имя через validator.validate
     * <p>
     * Ожидание:
     * validator.validate выдаст False на всех именах
     */
    @ParameterizedTest
    @MethodSource("russianNamesWithWrongPunctuation")
    void testFailedRussianNamesWithWrongPunctuation(String failedName) {
        assertThat(validator.validate(failedName)).isFalse();
    }

    static ArrayList<String> russianNamesWithTwoUppercase() {
        char[] alphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя".toUpperCase().toCharArray();
        ArrayList<String> listOfPartOfRussianNames = createListOfPartOfRussianNames();
        ArrayList<String> listOfRussianNamesWithTwoUppercase = new ArrayList<>();
        for (int i = 0; i < alphabet.length; i++) {
            String part = alphabet[i] + listOfPartOfRussianNames.get(i);
            listOfRussianNamesWithTwoUppercase.add(part + " " + part + " " + part);
        }
        return listOfRussianNamesWithTwoUppercase;
    }

    /**
     * Проверка:
     * проверка имен из русских букв на наличие двух прописынх букв в имени
     * <p>
     * Действие:
     * создать объект RussianNameValidator validator
     * создать список имен, шаблона **+ **+ **+, где * - прописная буква, + - строчная
     * проверить каждое имя через validator.validate
     * <p>
     * Ожидание:
     * validator.validate выдаст False на всех именах
     */
    @ParameterizedTest
    @MethodSource("russianNamesWithTwoUppercase")
    void testFailedRussianNamesWithTwoUppercase(String failedName) {
        assertThat(validator.validate(failedName)).isFalse();
    }
}
