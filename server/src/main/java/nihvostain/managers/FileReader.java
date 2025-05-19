package nihvostain.managers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import common.model.StudyGroups;

import java.io.*;

/**
 * Класс для чтения из файла
 */
public class FileReader {

    /**
     * @param fileName имя файла для чтения
     * @return массив учебных групп
     * @throws JsonProcessingException ошибка десериализации
     */
    public StudyGroups fromXML(String fileName) throws JsonProcessingException {

        StudyGroups studyGroups = new StudyGroups();

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION); // Добавляет XML-декларацию
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT); // Включает отступы и переносы строк

        File file = new File(fileName);
        String ansS = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))){
            String s = reader.readLine();

            if (s != null){
                do {
                    s = s.trim();
                    ansS += s;
                    s = reader.readLine();

                } while (s!= null);
            }

        } catch (IOException | SecurityException e) {
            System.out.println("ошибка чтения файла");
        }
        studyGroups = xmlMapper.readValue(ansS, StudyGroups.class);
        return studyGroups;
    }
}

