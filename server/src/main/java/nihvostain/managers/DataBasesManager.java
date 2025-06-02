package nihvostain.managers;

import common.model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DataBasesManager {
    private final Connection connection;
    public DataBasesManager(String url, String user, String password) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
    }

    public boolean checkLogin(String login) throws SQLException {
        String sql = "select login from PasswordUsers where login = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, login);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }
    public String getLoginByKey(String key) throws SQLException {
        String sql = "select login from StudyGroups where key = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, key);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getString("login");
    }
    public boolean allowModification(String key, String login) throws SQLException {
        String sql = "select login from StudyGroups where key = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, key);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getString("login").equals(login);
    }

    public void removeKey(String key) throws SQLException {
        String sql = "delete from StudyGroups where key = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, key);
        statement.executeUpdate();
    }

    public boolean checkPassword(String login, String password) throws SQLException {
        String sql = "select password from PasswordUsers where login = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, login);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("password").equals(password);
        } else {
            return false;
        }
    }

    public void insertUser (String login, String password) throws SQLException {
        String sql = "insert into PasswordUsers (login, password) values (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, login);
        statement.setString(2, password);
        statement.executeUpdate();
    }

    public void updateStudyGroupKey (String key, StudyGroup studyGroup) throws SQLException {

        String sql = "update StudyGroups set name = ?, x = ?, y = ?, creationDate = ?, studentsCount = ?, formOfEducation = ?, semesterEnum = ?, nameP = ?, birthday = ?, passportID = ?, eyeColor = ?, hairColor = ? where key = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, studyGroup.getName());
        statement.setLong(2, studyGroup.getCoordinates().getX());
        statement.setFloat(3, studyGroup.getCoordinates().getY());
        statement.setTimestamp(4, Timestamp.valueOf(studyGroup.getCreationDate()));
        statement.setLong(5, studyGroup.getStudentsCount());
        statement.setString(6, studyGroup.getFormOfEducation().toString());
        statement.setString(7, studyGroup.getSemesterEnum().toString());

        if (studyGroup.getGroupAdmin() != null) {
            statement.setString(8, studyGroup.getGroupAdmin().getName());
            if (studyGroup.getGroupAdmin().getBirthday() != null) {
                statement.setString(9, studyGroup.getGroupAdmin().getBirthday().toString());
            } else {
                statement.setString(9, null);
            }
            statement.setString(10, studyGroup.getGroupAdmin().getPassportID());
            if (studyGroup.getGroupAdmin().getEyeColor() != null) {
                statement.setString(11, studyGroup.getGroupAdmin().getEyeColor().toString());
            } else {
                statement.setString(11, null);
            }
            statement.setString(12, studyGroup.getGroupAdmin().getHairColor().toString());

        } else {
            statement.setString(8, null);
            statement.setString(9, null);
            statement.setString(10, null);
            statement.setString(11, null);
            statement.setString(12, null);
        }
        statement.setString(13, key);
        statement.executeUpdate();
    }

    public long insertStudyGroup (String key, StudyGroup studyGroup, String login) throws SQLException {
        String sql = "insert into StudyGroups (key, name, x, y, creationDate, studentsCount," +
                " formOfEducation, semesterEnum, nameP, birthday, passportID, eyeColor, hairColor, login)" +
                "  values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, key);
        statement.setString(2, studyGroup.getName());
        statement.setLong(3, studyGroup.getCoordinates().getX());
        statement.setFloat(4, studyGroup.getCoordinates().getY());
        statement.setTimestamp(5, Timestamp.valueOf(studyGroup.getCreationDate()));
        statement.setLong(6, studyGroup.getStudentsCount());
        statement.setString(7, studyGroup.getFormOfEducation().toString());
        statement.setString(8, studyGroup.getSemesterEnum().toString());

        if (studyGroup.getGroupAdmin() != null) {
            statement.setString(9, studyGroup.getGroupAdmin().getName());
            if (studyGroup.getGroupAdmin().getBirthday() != null) {
                statement.setString(10, studyGroup.getGroupAdmin().getBirthday().toString());
            } else {
                statement.setString(10, null);
            }
            statement.setString(11, studyGroup.getGroupAdmin().getPassportID());
            if (studyGroup.getGroupAdmin().getEyeColor() != null) {
                statement.setString(12, studyGroup.getGroupAdmin().getEyeColor().toString());
            } else {
                statement.setString(12, null);
            }
            statement.setString(13, studyGroup.getGroupAdmin().getHairColor().toString());

        } else {
            statement.setString(9, null);
            statement.setString(10, null);
            statement.setString(11, null);
            statement.setString(12, null);
            statement.setString(13, null);
        }

        statement.setString(14, login);
        ResultSet res = statement.executeQuery();
        res.next();
        return res.getLong("id");

    }

    public Map<String, StudyGroup> parseStudyGroups () throws SQLException {
        Map<String, StudyGroup> studyGroups = new LinkedHashMap<>();
        String sql = "select * from StudyGroups";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String key = resultSet.getString("key");
            long id = resultSet.getLong("id");
            StudyGroup.addId(id);
            String name = resultSet.getString("name");
            int x = resultSet.getInt("x");
            float y = resultSet.getFloat("y");
            LocalDateTime creationDate = resultSet.getTimestamp("creationDate").toLocalDateTime();
            long studentsCount = resultSet.getLong("studentsCount");
            FormOfEducation formOfEducation = FormOfEducation.getFormOfEducationFromDB().get(resultSet.getString("formOfEducation"));
            SemesterEnum semesterEnum = SemesterEnum.getSemesterFromDB().get(resultSet.getString("semesterEnum"));
            Person person = null;
            String nameP = resultSet.getString("nameP");
            if (nameP != null) {
                ZonedDateTime birthday;
                try {
                    birthday = ZonedDateTime.parse(resultSet.getString("birthday"));
                } catch (NullPointerException e) {
                    birthday = null;
                }
                String passportID = resultSet.getString("passportID");
                Person.addPassportID(passportID);
                EyeColor eyeColor = EyeColor.getColorsFromDB().get(resultSet.getString("eyeColor"));
                HairColor hairColor = HairColor.getColorsFromDB().get(resultSet.getString("hairColor"));
                person = new Person(nameP, birthday, passportID, eyeColor, hairColor);
            }
            Coordinates coordinates = new Coordinates(x, y);
            StudyGroup studyGroup = new StudyGroup(id, name, coordinates, creationDate, studentsCount, formOfEducation, semesterEnum, person);
            System.out.println(studyGroup);
            studyGroups.put(key, studyGroup);

        }
        return studyGroups;
    }

}
