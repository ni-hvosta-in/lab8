package nihvostain.commands;

import nihvostain.managers.CollectionManager;
import nihvostain.managers.Communication;
import nihvostain.utility.Command;
import common.managers.*;
import common.model.*;
import common.utility.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Команда группировки вывода по семестру
 */
public class GroupCountingBySemesterEnum implements Command {

    private final CollectionManager collectionManager;
    private final Communication communication;
    public GroupCountingBySemesterEnum(CollectionManager collectionManager, Communication communication) {
        this.collectionManager = collectionManager;
        this.communication = communication;
    }

    /**
     * @param request запрос с клиента
     */
    @Override
    public RequestObj execute(Request request) throws IOException {

        List<StudyGroup> studyGroups = new ArrayList<>(collectionManager.getStudyGroupList().values());
        Collections.sort(studyGroups);
        String ans = "";
        ArrayList<Integer> a = new ArrayList<>();
        a.add(1);
        if (studyGroups.isEmpty()) {
            ans = "Коллекция пуста";
        } else {

            if (studyGroups.size() == 1) {
                ans += studyGroups.get(0);
            }
            else {
                ans = IntStream.range(0, studyGroups.size() - 1).mapToObj(x -> {
                    String s = "";
                    s += studyGroups.get(x) + "\n";
                    if (studyGroups.get(x).getSemesterEnum() != studyGroups.get(x + 1).getSemesterEnum()) {
                        s += "\nКол-во групп " + studyGroups.get(x).getSemesterEnum().getSem() + " семестра равно " + a.get(0) + "\n";
                        a.set(0, 1);
                    } else {
                        a.set(0, a.get(0) + 1);
                    }
                    return s;
                }).collect(Collectors.joining());
            }
            /*
            for (int i = 0; i < studyGroups.size() - 1; i++) {
                ans += studyGroups.get(i) + "\n";
                if (studyGroups.get(i).getSemesterEnum() != studyGroups.get(i + 1).getSemesterEnum()) {
                    ans += "\nКол-во групп " + studyGroups.get(i).getSemesterEnum().getSem() + " семестра равно " + k + "\n";
                } else {
                    k += 1;
                }
            }

             */
            if (studyGroups.size() ==  1) {
                ans += "\nКол-во групп " + studyGroups.get(0).getSemesterEnum().getSem() + " семестра равно " + 1;
            } else if (studyGroups.get(studyGroups.size() - 2).getSemesterEnum() != studyGroups.get(studyGroups.size() - 1).getSemesterEnum()) {
                    ans += studyGroups.get(studyGroups.size() - 1);
                    ans += "\nКол-во групп " + studyGroups.get(studyGroups.size() - 1).getSemesterEnum().getSem() + " семестра равно " + 1;
            } else {
                ans += studyGroups.get(studyGroups.size() - 1);
                ans += "\nКол-во групп " + studyGroups.get(studyGroups.size() - 1).getSemesterEnum().getSem() + " семестра равно " + (a.get(0));
            }
        }
        System.out.println(ans);
        return new RequestObj(ans);

    }

    /**
     * @return описание команды
     */
    @Override
    public String description() {
        return "group_counting_by_semester_enum : сгруппировать элементы коллекции по значению поля semesterEnum," +
                " вывести количество элементов в каждой группе";
    }

    /**
     * @return класс, который будет вводиться
     */
    @Override
    public TypeOfElement getElementType() {
        return TypeOfElement.PRIMITIVE;
    }

    /**
     * @return размер массива аргументов
     */
    @Override
    public Integer getNeededArgsLen() {
        return 0;
    }

    @Override
    public Integer getNeededParamLen() {
        return 0;
    }

    @Override
    public InvalidParamMessage isValidParam(ArrayList<String> params) {
        return InvalidParamMessage.TRUE;
    }

    @Override
    public boolean skipValidateField() {
        return false;
    }
}
