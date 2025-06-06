package nihvostain.managers;

import com.fasterxml.jackson.core.JsonProcessingException;
import common.exceptions.ExistingKeyException;
import common.model.Person;
import common.model.StudyGroup;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * Менеджер коллекций
 */
public class CollectionManager {  //receiver

    private Map <String, StudyGroup> studyGroupList = new LinkedHashMap<>();
    final LocalDateTime initializationDate = LocalDateTime.now();
    private final ReentrantLock lock = new ReentrantLock();

    public Map <String, StudyGroup> getStudyGroupList() {
        return studyGroupList;
    }
    public void setStudyGroupList(Map <String,StudyGroup> studyGroupList) {
        this.studyGroupList = studyGroupList;
    }

    /**
     * Предварительная загрузка коллекции из файла
     */
    public void load(String fileName){

        boolean flag = false;
        try {
            studyGroupList = new FileReader().fromXML(fileName).getStudyGroupList();
            Iterator<StudyGroup> iterator = studyGroupList.values().iterator();
            while (iterator.hasNext()){
                StudyGroup st = iterator.next();
                if (!st.isValidateClass()){
                    System.out.println("Элемент из xml файла не валиден, поэтому он пропущен");
                    System.out.println("Пропущенный элемент: ");
                    System.out.println(st);
                    System.out.println();
                    iterator.remove();
                } else {

                    Person.addPassportID(st.getGroupAdmin().getPassportID());
                    StudyGroup.addId(st.getId());
                    flag = true;
                }
            }
            for (Map.Entry<String, StudyGroup> pair : studyGroupList.entrySet()){
                if (pair.getValue().getGroupAdmin().getName() == null){
                    studyGroupList.get(pair.getKey()).setGroupAdmin(null);
                }
            }

        } catch (JsonProcessingException e) {
            System.out.println("невозможность десереализации");
        } catch (NullPointerException e){
            System.out.println("Отсутствует переменная окружения с именем MY_VAR");
        }

        if (!flag){
            StudyGroup.addId(-1L);
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream("id.txt")) {
            fileOutputStream.write(Collections.max(StudyGroup.getIdList()).toString().getBytes());
        } catch (IOException e){
            System.out.println("ошибка в работе с id.txt при загрузке из файла");
        }
    }

    public void loadDb(DataBasesManager dataBasesManager) throws SQLException {
        studyGroupList = dataBasesManager.parseStudyGroups();
    }
    /**
     * информация о коллекции
     */
    public String info(){

        String info = "";
        lock.lock();
        info += "Collection type: " + studyGroupList.getClass().getName() + "\n";
        lock.unlock();
        info += "Initialization date: " + initializationDate+ "\n";
        info += "Number of items in collection: " + studyGroupList.size();
        return info;
    }

    /**
     * Добавление учебной группы в коллекцию по ключу
     * @param key ключ
     * @param studyGroup учебная группа
     * @throws ExistingKeyException существующий ключ
     */
    public void insert(String key, StudyGroup studyGroup) throws ExistingKeyException {
        lock.lock();
        if (!studyGroupList.containsKey(key)){
            studyGroup.generateFields();
            studyGroupList.put(key, studyGroup);
        } else {
            throw new ExistingKeyException();
        }
        lock.unlock();

    }


    /**
     * Обновление значения группы по ключу
     * @param key ключ
     * @param studyGroup учебная группа
     */
    public void updateStudyGroup(String key, StudyGroup studyGroup){
        lock.lock();
        studyGroupList.put(key, studyGroup);
        lock.unlock();
    }

    /**
     * Удаление группы по ключу
     * @param key ключ
     */
    public void removeKey(String key){
        lock.lock();
        studyGroupList.remove(key);
        lock.unlock();
    }

    /**
     * Отчистит коллекцию
     */
    public void clear(){
        lock.lock();
        studyGroupList.clear();
        lock.unlock();
        Person.removeAllPassportIDs();

    }
    public Map<String, StudyGroup> getSortedStudyGroupList() {
        lock.lock();
        try {
            return studyGroupList.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (oldVal, newVal) -> oldVal,
                            LinkedHashMap::new));
        } finally {
            lock.unlock();
        }
    }

}
