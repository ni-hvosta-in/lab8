package common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import common.utility.FieldsStudyGroup;
import common.utility.ValidateClass;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Класс учебной группы
 */
public class StudyGroup implements Comparable <StudyGroup>, ValidateClass, Serializable {

    //static private ArrayList<Long> idList = new ArrayList<>();
    @JacksonXmlProperty(localName = "id")
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @JacksonXmlProperty(localName = "name")
    private String name; //Поле не может быть null, Строка не может быть пустой

    @JacksonXmlProperty(localName = "coordinates")
    private Coordinates coordinates; //Поле не может быть null

    @JacksonXmlProperty(localName = "creationDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @JacksonXmlProperty(localName = "studentsCount")
    private long studentsCount; //Значение поля должно быть больше 0

    @JacksonXmlProperty(localName = "formOfEducation")
    private FormOfEducation formOfEducation; //Поле не может быть null

    @JacksonXmlProperty(localName = "semesterEnum")
    private SemesterEnum semesterEnum; //Поле не может быть null

    @JacksonXmlProperty(localName = "groupAdmin")
    private Person groupAdmin; //Поле может быть null

    static private ArrayList <Long> idList = new ArrayList<>();

    /**
     * Массив вводимых полей
     */
    private static FieldsStudyGroup[] fields;

    /**
     * @param name имя
     * @param coordinates координаты
     * @param studentsCount кол-во студентов
     * @param formOfEducation форма обучения
     * @param semesterEnum семестр
     * @param groupAdmin староста
     */
    public StudyGroup (long id, String name, Coordinates coordinates, LocalDateTime creationDate,
                       long studentsCount, FormOfEducation formOfEducation,
                       SemesterEnum semesterEnum, Person groupAdmin){
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.studentsCount = studentsCount;
        this.formOfEducation = formOfEducation;
        this.semesterEnum = semesterEnum;
        this.groupAdmin = groupAdmin;
    }

    /**
     * Конструктор без старосты
     */
    public StudyGroup (long id, String name, Coordinates coordinates,
                       long studentsCount, FormOfEducation formOfEducation,
                       SemesterEnum semesterEnum){
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDateTime.now();
        this.studentsCount = studentsCount;
        this.formOfEducation = formOfEducation;
        this.semesterEnum = semesterEnum;
    }

    /**
     * @param args массив строк, в котором хранятся значения всех полей класса
     */
    public StudyGroup (ArrayList<String> args){
        creationDate = LocalDateTime.now();
        name = args.get(0);
        Integer x = Integer.parseInt(args.get(1));
        float y = Float.parseFloat(args.get(2));
        coordinates = new Coordinates(x,y);
        studentsCount = Long.parseLong(args.get(3));
        formOfEducation = FormOfEducation.getFormOfEducation().get(args.get(4));
        semesterEnum = SemesterEnum.getSemester().get(args.get(5));

        String nameP = args.get(6);
        if (nameP == null){
            groupAdmin = null;
        } else {

            ZonedDateTime birthday;
            if (args.get(7) == null){
                birthday = null;
            } else {
                birthday = ZonedDateTime.parse(args.get(7));
            }

            String passportID = args.get(8);

            EyeColor eyeColor;
            if (args.get(9) == null){
                eyeColor = null;
            } else {
                eyeColor = EyeColor.getColors().get(args.get(9));
            }

            HairColor hairColor = HairColor.getColors().get(args.get(10));
            groupAdmin = new Person(nameP, birthday, passportID, eyeColor, hairColor);
        }
    }

    /**
     * Конструктор по умолчанию для сериализации
     */
    public StudyGroup(){
        this.creationDate = LocalDateTime.now();
    }

    public StudyGroup(Long id, ArrayList<String> args) {
        this.id = id;
        this.creationDate = LocalDateTime.now();
        name = args.get(0);
        Integer x = Integer.parseInt(args.get(1));
        float y = Float.parseFloat(args.get(2));
        coordinates = new Coordinates(x,y);
        studentsCount = Long.parseLong(args.get(3));
        formOfEducation = FormOfEducation.getFormOfEducation().get(args.get(4));
        semesterEnum = SemesterEnum.getSemester().get(args.get(5));

        String nameP = args.get(6);
        if (nameP == null){
            groupAdmin = null;
        } else {

            ZonedDateTime birthday;
            if (args.get(7) == null){
                birthday = null;
            } else {
                birthday = ZonedDateTime.parse(args.get(7));
            }

            String passportID = args.get(8);

            EyeColor eyeColor;
            if (args.get(9) == null){
                eyeColor = null;
            } else {
                eyeColor = EyeColor.getColors().get(args.get(9));
            }

            HairColor hairColor = HairColor.getColors().get(args.get(10));
            groupAdmin = new Person(nameP, birthday, passportID, eyeColor, hairColor);
        }
    }

    public void generateFields(){
        //this.id = generateIdFromFile();
        this.creationDate = LocalDateTime.now();
    }

    public static FieldsStudyGroup[] getFields() {
        return fields;
    }

    public static void setFields(FieldsStudyGroup[] fields) {
        StudyGroup.fields = fields;
    }

    /**
     * @return id группы сгенерированное при помощи файла id
     */
    private long generateIdFromFile(){

        long id;
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("id.txt")))) {
            String s = reader.readLine();
            id = Long.parseLong(s) + 1;
        } catch (IOException e){
            System.out.println("ошибка в чтении из файла id.txt");

            if (!idList.isEmpty()) {
                id = Collections.max(idList) + 1;
            } else {
                id = 0;
            }

        }
        idList.add(id);

        try (FileOutputStream fileOutputStream = new FileOutputStream("id.txt")) {
            fileOutputStream.write(Long.toString(id).getBytes());
        } catch (IOException e){
            System.out.println("ошибка в работе с id.txt");
        }

        return id;
    }

    /**
     * Добавляет id в массив id, для хранения всех id
     * @param id id
     */
    public static void addId(Long id) {
        StudyGroup.idList.add(id);
    }

    public static ArrayList<Long> getIdList(){
        return StudyGroup.idList;
    }
    /**
     * Возвращает текстовое представление класса
     * @return текстовое представление учебной группы
     */
    @Override
    public String toString() {
        String info = "";
        info += "id: " + id + "\n";
        info += "name: " + name + "\n";
        info += coordinates;
        info += "Creation Date: " + creationDate.toString().substring(8,10) + "-" +
                creationDate.toString().substring(5,7) + "-" + creationDate.toString().substring(0,4) + creationDate.toString().substring(10,creationDate.toString().length()) + "\n";
        info += "Number of students: " + studentsCount + "\n";
        info += "Form of education: " + formOfEducation.getForm() + "\n";
        info += "Semester: " + semesterEnum.getSem() + "\n";
        if (groupAdmin == null){
            info += "No admin";
        } else {
            info += "Group admin: " + groupAdmin.getName() + "\n";
            info += "Group admin birthday: " + groupAdmin.getBirthday() + "\n";
            info += "Group admin passport: " + groupAdmin.getPassportID() + "\n";
            info += "Group admin eyeColor: " + groupAdmin.getEyeColor() + "\n";
            info += "Group admin hairColor: " + groupAdmin.getHairColor();
        }
        return info;
    }


    /**
     * @param o the object to be compared.
     * @return результат сравнения по семестрам, именам и id
     */
    @Override
    public int compareTo(StudyGroup o) {

        if (this.getSemesterEnum().getSem().compareTo(o.getSemesterEnum().getSem())>0){
            return 1;
        } else if (this.getSemesterEnum().getSem().compareTo(o.getSemesterEnum().getSem())<0) {
            return -1;
        } else {
            return this.name.compareTo(o.name);
        }
    }


    /**
     * Возвращает id
     * @return id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Возвращает имя
     * @return имя
     */
    public String getName() {
        return this.name;
    }

    /**
     * Возвращает старосту
     * @return староста
     */
    public Person getGroupAdmin() {
        return this.groupAdmin;
    }

    /**
     * Возвращает семестр
     * @return семестр
     */
    public SemesterEnum getSemesterEnum(){
        return this.semesterEnum;
    }

    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    public LocalDateTime getCreationDate() {
        return this.creationDate;
    }

    public long getStudentsCount() {
        return this.studentsCount;
    }

    public FormOfEducation getFormOfEducation() {
        return this.formOfEducation;
    }

    public void setGroupAdmin(Person groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * @return валидно ли Id
     */
    private boolean isIdValidate(){

        if (!idList.isEmpty()) {
            return !idList.contains(this.id) & this.id >= 0;
        } else {
            return true;
        }

    }

    /**
     * @return валидно ли имя
     */
    private boolean isNameValidate(){
        return this.name != null & this.name != "";
    }

    /**
     * @return валидны ли координаты
     */
    private boolean isCoordinateValidate(){
        return this.coordinates.isValidateClass();
    }

    /**
     * @return валидна ли дата создания
     */
    private boolean isCreationDateValidate(){
        return this.creationDate != null;
    }

    /**
     * @return валидно ли число студентов
     */
    private boolean isStudentsCountValidate(){
        return this.studentsCount > 0;
    }

    /**
     * @return валидна ли форма обучения
     */
    private boolean isFormOfEducationValidate(){
        return this.formOfEducation != null;
    }

    /**
     * @return валиден ли семестр
     */
    private boolean isSemesterValidate(){
        return this.semesterEnum != null;
    }

    /**
     * @return валиден ли староста
     */
    private boolean isGroupAdminValidate(){
        return this.groupAdmin.isValidateClass();
    }

    /**
     * @return валиден ли класс
     */
    @JsonIgnore
    @Override
    public boolean isValidateClass() {
        return isIdValidate() & isNameValidate() & isCoordinateValidate() & isCreationDateValidate() &
                isStudentsCountValidate() & isFormOfEducationValidate() & isSemesterValidate() & isGroupAdminValidate() ;
    }

    public void setID(long id) {
        this.id = id;
    }

    public boolean equals(StudyGroup o) {
        return this.id.equals(o.getId()) && this.coordinates.getX().equals(o.getCoordinates().getX()) && this.coordinates.getY() == o.getCoordinates().getY();
    }
}