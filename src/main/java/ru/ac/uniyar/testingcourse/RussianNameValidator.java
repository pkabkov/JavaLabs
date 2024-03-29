package ru.ac.uniyar.testingcourse;

/**
 * Класс для валидации ФИО.
 * Разрешёнными считаются ФИО, удовлетворящие следующим требованиям:
 * <ul>
 * <li>состоят из двух или трёх элементов, разделённых пробелом (фамилия, имя,
 * отчество; отчество не обязательно);
 * <li>включают только русские буквы;
 * <li>каждый элемент начинается с прописной буквы, остальные буквы — строчные;
 * <li>фамилия и имя могут быть двойными (разделяются одним дефисом; после
 * дефиса первая буква прописная).
 * </ul>
 */
public class RussianNameValidator {
    public RussianNameValidator() {
        
    }
    
    /** 
     * Валидация ФИО пользователя.
     * @param name ФИО, которые надо проверить
     * @return true, если имя корректно, иначе — false
     */
    public boolean validate(String name) {
        return name.matches("[А-ЯË][а-яë]*(-[А-ЯË][а-яë]*)? [А-ЯË][а-яë]*(-[А-ЯË][а-яë]*)?( [А-ЯË][а-яë]*)?");
    }
}
