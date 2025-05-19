package common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import common.utility.FieldsPerson;
import common.utility.ValidateClass;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;

/**
 * Класс человек
 */
public class Person implements Comparable<Person>, ValidateClass, Serializable {

    @JacksonXmlProperty(localName = "name")
    private String name; //Поле не может быть null, Строка не может быть пустой

    @JacksonXmlProperty(localName = "data")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private ZonedDateTime birthday; //Поле может быть null

    @JacksonXmlProperty(localName = "passportID")
    private String passportID; //Длина строки должна быть не меньше 5, Значение этого поля должно быть уникальным, Поле не может быть null

    @JacksonXmlProperty(localName = "eyeColor")
    private EyeColor eyeColor; //Поле может быть null

    @JacksonXmlProperty(localName = "hairColor")
    private HairColor hairColor; //Поле не может быть null

    /**
     * Массив паспортов
     */
    static private ArrayList<String> passportIDList = new ArrayList<>();

    /**
     * Массив вводимых полей
     */
    private static FieldsPerson[] fields;

    /**
     * Конструктор с аргументом в виде массива строк
     * @param args массив строк, в котором хранятся значения всех полей класса
     */
    public Person(ArrayList<String> args){

        this.name = args.get(0);

        if (args.get(1) == null){
            this.birthday = null;
        } else {
            this.birthday = ZonedDateTime.parse(args.get(1));
        }

        this.passportID = args.get(2);

        try {
            passportIDList.add(this.passportID);
        } catch (NullPointerException e) {
            passportIDList = new ArrayList<>();
            passportIDList.add(this.passportID);
        }

        if (args.get(3) == null){
            this.eyeColor = null;
        } else {
            this.eyeColor = EyeColor.getColors().get(args.get(3));
        }
        this.hairColor = HairColor.getColors().get(args.get(4));
    }

    /**
     * @param name имя
     * @param birthday дата рождения
     * @param passportID паспорт
     * @param eyeColor цвет глаз
     * @param hairColor цвет волос
     */
    public Person (String name, ZonedDateTime birthday, String passportID, EyeColor eyeColor, HairColor hairColor){
        this.name = name;
        this.birthday = birthday;
        this.passportID = passportID;
        try {
            passportIDList.add(this.passportID);
        } catch (NullPointerException e){
            passportIDList = new ArrayList<>();
            passportIDList.add(this.passportID);
        }
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
    }

    /**
     * Конструктор по умолчанию для сериализации
     */
    public Person (){}

    /**
     * Вернет массив с паспортами
     * @return массив паспортов
     */
    public static ArrayList<String> getPassportIDList() {
        return passportIDList;
    }

    /**
     * Добавит паспорт в массив паспортов
     * @param passportID паспорт
     */
    public static void addPassportID(String passportID) {
        Person.passportIDList.add(passportID);
    }
    public static void removePassportID(String passportID){
        Person.passportIDList.remove(passportID);
    }
    public static void removeAllPassportIDs(){
        Person.passportIDList.clear();
    }
    public static FieldsPerson[] getFields() {
        return fields;
    }

    public static void setFields(FieldsPerson[] fields) {
        Person.fields = fields;
    }

    /**
     * Возвращает имя
     * @return имя
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает паспорт
     * @return паспорт
     */
    public String getPassportID(){
        return passportID;
    }

    public ZonedDateTime getBirthday() {
        return birthday;
    }

    public EyeColor getEyeColor() {
        return eyeColor;
    }

    public HairColor getHairColor() {
        return hairColor;
    }

    /**
     * @param o the object to be compared.
     * @return результат сравнения по именам
     */
    @Override
    public int compareTo(Person o) {

        if (o == null){
            return -1;
        } else {
            return this.getName().compareTo(o.getName());
        }
    }

    /**
     * @return валидно ли имя
     */
    boolean isNameValidate(){
        return this.name != null;
    }

    /**
     * @return валиден ли паспорт
     */
    boolean isPassportValidate(){
        if (this.passportID != null){
            return this.passportID.length() >= 5 & !passportIDList.contains(this.passportID);
        }
        return false;
    }

    /**
     * @return валиден ли цвет волос
     */
    boolean isHairColorValidate(){
        return this.hairColor != null;
    }

    /**
     * @param birthdayRus перевод даты из русского формата
     * @return дата в международном формате
     */
    public static String convertBirthdayFromRus(String birthdayRus){
        return birthdayRus.substring(6, 10) + "-" + birthdayRus.substring(3, 5) + "-"
                + birthdayRus.substring(0, 2) + birthdayRus.substring(10, birthdayRus.length());
    }

    /**
     * @return валиден ли класс
     */
    @JsonIgnore
    @Override
    public boolean isValidateClass() {
        return (isNameValidate() &
                isPassportValidate() & isHairColorValidate()) | (!isNameValidate() &
                !isPassportValidate() & !isHairColorValidate());
    }

}