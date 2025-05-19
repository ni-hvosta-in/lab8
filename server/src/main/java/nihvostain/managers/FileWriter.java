package nihvostain.managers;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import common.model.StudyGroups;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Класс записи в файл
 */
public class FileWriter {

    private StudyGroups studyGroups;

    public FileWriter(StudyGroups studyGroups){
        this.studyGroups = studyGroups;

    }

    /**
     * Конвертация studyGroups в xml и запись в файл
     */
    public void toXML(String fileName){

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION); // Добавляет XML-декларацию
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT); // Включает отступы и переносы строк
        try {
            String xml = xmlMapper.writeValueAsString(studyGroups);
            File file = new File(fileName);
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                fileOutputStream.write(xml.getBytes());
            }
        } catch (IOException e) {
            System.out.println("невозможность серелизации");
        }
    }
}
