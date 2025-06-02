package nihvostain.managers.gui;

import common.exceptions.InputFromScriptException;
import common.exceptions.RecursionDepthExceededException;
import common.managers.Deserialize;
import common.managers.Request;
import common.managers.ResponseStudyGroups;
import common.model.Person;
import common.model.StudyGroup;
import common.model.StudyGroupWithKey;
import common.utility.TypeRequest;
import javafx.application.Platform;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nihvostain.managers.Communication;
import nihvostain.managers.Invoker;
import nihvostain.utility.Command;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

public class ControllerMain {
    @FXML private Label loginLabel;
    @FXML private TextArea resultLabel;
    private Communication communication;
    private String login;
    private String password;
    private Command command;
    @FXML private TableView<StudyGroupWithKey> studyGroups;
    @FXML private TableColumn<StudyGroupWithKey, Integer> keyColumn;
    @FXML private TableColumn<StudyGroupWithKey, Long> idColumn;
    @FXML private TableColumn<StudyGroupWithKey, String> nameColumn;
    @FXML private TableColumn<StudyGroupWithKey, Integer> xColumn;
    @FXML private TableColumn<StudyGroupWithKey, Float> yColumn;
    @FXML private TableColumn<StudyGroupWithKey, String> creationDateColumn;
    @FXML private TableColumn<StudyGroupWithKey, Long> studentsCountColumn;
    @FXML private TableColumn<StudyGroupWithKey, String> formOfEducationColumn;
    @FXML private TableColumn<StudyGroupWithKey, String> semesterEnumColumn;
    @FXML private TableColumn<StudyGroupWithKey, String> namePColumn;
    @FXML private TableColumn<StudyGroupWithKey, String> birthdayColumn;
    @FXML private TableColumn<StudyGroupWithKey, String> passportIdColumn;
    @FXML private TableColumn<StudyGroupWithKey, String> eyeColorColumn;
    @FXML private TableColumn<StudyGroupWithKey, String> hairColorColumn;
    private ObservableList<StudyGroupWithKey> groups = FXCollections.observableArrayList();
    private TableColumn<StudyGroupWithKey, ?> sortColumn = keyColumn;
    private TableColumn.SortType sortType = TableColumn.SortType.ASCENDING;
    @FXML public void execute(ActionEvent actionEvent) throws RecursionDepthExceededException, IOException {

        Button clicked = (Button) actionEvent.getSource();
        System.out.println(clicked.getText());
        command = Invoker.getCommands().get(clicked.getText());
        String comm = clicked.getText();

        if (command.getNeededArgsLen() == 1){

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/onefieldset.fxml"));
            Parent root = fxmlLoader.load();
            ControllerOneField controllerOneField = fxmlLoader.getController();
            controllerOneField.setFieldLabel(command.getParamsName()[0]);
            controllerOneField.setCommand(command);
            controllerOneField.setResultLabel(resultLabel);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Окно ввода");
            stage.initModality(Modality.APPLICATION_MODAL); // Блокирует родительское окно
            stage.showAndWait(); // Ожидание закрытия окна
            comm = comm +" "+ controllerOneField.getFieldValue();

        } else if (command.getNeededArgsLen() == 12){

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/12Fields.fxml"));
            Parent root = fxmlLoader.load();
            Controller12Field controller = getPreparedController(fxmlLoader.getController());
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Окно ввода");
            stage.initModality(Modality.APPLICATION_MODAL); // Блокирует родительское окно
            stage.showAndWait(); // Ожидание закрытия окна
            comm = comm + " " + controller.getFieldValue();

        } else if (command.getNeededArgsLen() == 11){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/12Fields.fxml"));
            Parent root = fxmlLoader.load();
            Controller12Field controller = getPreparedController(fxmlLoader.getController());
            controller.removeFirstHBox();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Окно ввода");
            stage.initModality(Modality.APPLICATION_MODAL); // Блокирует родительское окно
            stage.showAndWait(); // Ожидание закрытия окна
            comm = comm + " " + controller.getFieldValue();

        } else if (command.getNeededArgsLen() == 5) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/12Fields.fxml"));
            Parent root = fxmlLoader.load();
            Controller12Field controller = getPreparedController(fxmlLoader.getController());
            controller.removeFirstHBox();
            controller.removeFirstHBox();
            controller.removeFirstHBox();
            controller.removeFirstHBox();
            controller.removeFirstHBox();
            controller.removeFirstHBox();
            controller.removeFirstHBox();
            controller.setInputPerson(true);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Окно ввода");
            stage.initModality(Modality.APPLICATION_MODAL); // Блокирует родительское окно
            stage.showAndWait(); // Ожидание закрытия окна
            comm = comm + " " + controller.getFieldValue();
        }
        Scanner scanner = new Scanner(comm);
        resultLabel.setEditable(false);
        System.out.println(comm);
        Invoker invoker = new Invoker(scanner, communication, login, password, resultLabel);
        invoker.setFileFlag(true);
        try {
            invoker.scanning();
        } catch (InputFromScriptException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML private void initialize() {
        setupTableColumns();
        setSorting();
    }

    private void setupTableColumns() {

        keyColumn.setCellValueFactory(new PropertyValueFactory<>("key"));

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        xColumn.setCellValueFactory(cell ->
                new SimpleIntegerProperty(cell.getValue().getCoordinates().getX()).asObject());

        yColumn.setCellValueFactory(cell ->
                new SimpleFloatProperty(cell.getValue().getCoordinates().getY()).asObject());

        creationDateColumn.setCellValueFactory(cellData -> {
            LocalDateTime date = cellData.getValue().getStudyGroup().getCreationDate();
            String formatted = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            return new SimpleStringProperty(formatted);
        });

        studentsCountColumn.setCellValueFactory(new PropertyValueFactory<>("studentsCount"));

        formOfEducationColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getStudyGroup().getFormOfEducation().toString()));

        semesterEnumColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getStudyGroup().getSemesterEnum().toString()));

        namePColumn.setCellValueFactory(cell -> {
            Person admin = cell.getValue().getStudyGroup().getGroupAdmin();
            return new SimpleStringProperty(admin != null ? admin.getName() : "");
        });

        birthdayColumn.setCellValueFactory(cell -> {
            Person admin = cell.getValue().getStudyGroup().getGroupAdmin();
            return new SimpleStringProperty(admin != null && admin.getBirthday() != null ?
                    admin.getBirthday().toString() : "");
        });

        passportIdColumn.setCellValueFactory(cell -> {
            Person admin = cell.getValue().getStudyGroup().getGroupAdmin();
            return new SimpleStringProperty(admin != null ? admin.getPassportID() : "");
        });

        eyeColorColumn.setCellValueFactory(cell -> {
            Person admin = cell.getValue().getStudyGroup().getGroupAdmin();
            return new SimpleStringProperty(admin != null && admin.getEyeColor() != null ?
                    admin.getEyeColor().toString() : "");
        });

        hairColorColumn.setCellValueFactory(cell -> {
            Person admin = cell.getValue().getStudyGroup().getGroupAdmin();
            return new SimpleStringProperty(admin != null && admin.getHairColor() != null ?
                    admin.getHairColor().toString() : "");
        });

    }

    private void setSorting(){
        keyColumn.setSortable(true);
        idColumn.setSortable(true);
        nameColumn.setSortable(true);
        xColumn.setSortable(true);
        yColumn.setSortable(true);
        creationDateColumn.setSortable(true);
        studentsCountColumn.setSortable(true);
        formOfEducationColumn.setSortable(true);
        semesterEnumColumn.setSortable(true);
        namePColumn.setSortable(true);
        birthdayColumn.setSortable(true);
        passportIdColumn.setSortable(true);
        eyeColorColumn.setSortable(true);
        hairColorColumn.setSortable(true);


        studyGroups.setSortPolicy(tv -> {
            ObservableList<StudyGroupWithKey> items = tv.getItems();
            Comparator<StudyGroupWithKey> comparator = null;
            if (tv.getSortOrder().isEmpty()) {
                sortType = null;
                return true;
            }

            TableColumn<StudyGroupWithKey, ?> sortColumn = tv.getSortOrder().get(0);
            this.sortColumn = sortColumn;
            this.sortType = sortColumn.getSortType();

            if (sortColumn == keyColumn) {
                comparator = Comparator.comparing(
                        StudyGroupWithKey::getKey,
                        Comparator.nullsLast(Comparator.naturalOrder())
                );
            } else if (sortColumn == idColumn) {
                comparator = Comparator.comparing(
                        StudyGroupWithKey::getId,
                        Comparator.nullsLast(Comparator.naturalOrder())
                );
            } else if (sortColumn == nameColumn) {
                comparator = Comparator.comparing(
                        StudyGroupWithKey::getName,
                        Comparator.nullsLast(Comparator.naturalOrder())
                );
            } else if (sortColumn == xColumn) {
                comparator = Comparator.comparing(
                        sg -> sg.getCoordinates() != null ? sg.getCoordinates().getX() : null,
                        Comparator.nullsLast(Comparator.naturalOrder())
                );
            } else if (sortColumn == yColumn) {
                comparator = Comparator.comparing(
                        sg -> sg.getCoordinates() != null ? sg.getCoordinates().getY() : null,
                        Comparator.nullsLast(Comparator.naturalOrder())
                );
            } else if (sortColumn == creationDateColumn) {
                comparator = Comparator.comparing(
                        StudyGroupWithKey::getCreationDate,
                        Comparator.nullsLast(Comparator.naturalOrder())
                );
            } else if (sortColumn == studentsCountColumn) {
                comparator = Comparator.comparing(
                        StudyGroupWithKey::getStudentsCount,
                        Comparator.nullsLast(Comparator.naturalOrder())
                );
            } else if (sortColumn == formOfEducationColumn) {
                comparator = Comparator.comparing(
                        StudyGroupWithKey::getFormOfEducation,
                        Comparator.nullsLast(Comparator.naturalOrder())
                );
            } else if (sortColumn == semesterEnumColumn) {
                comparator = Comparator.comparing(
                        StudyGroupWithKey::getSemesterEnum,
                        Comparator.nullsLast(Comparator.naturalOrder())
                );
            } else if (sortColumn == namePColumn) {
                comparator = Comparator.comparing(
                        sg -> sg.getGroupAdmin() != null ? sg.getGroupAdmin().getName() : null,
                        Comparator.nullsLast(Comparator.naturalOrder())
                );
            } else if (sortColumn == birthdayColumn) {
                comparator = Comparator.comparing(
                        sg -> sg.getGroupAdmin() != null ? sg.getGroupAdmin().getBirthday() : null,
                        Comparator.nullsLast(Comparator.naturalOrder())
                );
            } else if (sortColumn == passportIdColumn) {
                comparator = Comparator.comparing(
                        sg -> sg.getGroupAdmin() != null ? sg.getGroupAdmin().getPassportID() : null,
                        Comparator.nullsLast(Comparator.naturalOrder())
                );
            } else if (sortColumn == eyeColorColumn) {
                comparator = Comparator.comparing(
                        sg -> sg.getGroupAdmin() != null ? sg.getGroupAdmin().getEyeColor() : null,
                        Comparator.nullsLast(Comparator.naturalOrder())
                );
            } else if (sortColumn == hairColorColumn) {
                comparator = Comparator.comparing(
                        sg -> sg.getGroupAdmin() != null ? sg.getGroupAdmin().getHairColor() : null,
                        Comparator.nullsLast(Comparator.naturalOrder())
                );
            }

            if (sortColumn.getSortType() == TableColumn.SortType.DESCENDING) {
                comparator = comparator.reversed();
            }
            List<StudyGroupWithKey> sorted = tv.getItems()
                    .stream()
                    .sorted(comparator)
                    .collect(Collectors.toList());
            items.setAll(sorted);  // ⚠️ Ключевая строка: заменяем содержимое, не сам список
            return true;
        });
    }

    public void startUpdates() {
        Thread updateThread = new Thread(() -> {
            while (true) {
                try {
                    byte[] request = new Request(TypeRequest.REQUEST_STUDYGROUPS).addUser(login, password).serialize();
                    communication.send(request);
                    byte[] response = communication.receive();
                    ResponseStudyGroups data = new Deserialize<ResponseStudyGroups>(response).deserialize();
                    List<StudyGroupWithKey> newData = data.getStudyGroups();


                    groups.setAll(newData);
                    Platform.runLater(() -> {
                                // Сохраняем состояние сортировки перед обновлением
                                // Обновляем данные
                                groups.setAll(newData);
                                studyGroups.setItems(groups);

                                // Восстанавливаем сортировку

                                if (sortType != null) {
                                    studyGroups.getSortOrder().clear();
                                    studyGroups.getSortOrder().add(sortColumn);
                                    sortColumn.setSortType(sortType);
                                    studyGroups.sort();
                                }
                            });


                    System.out.println("обновил");
                    Thread.sleep(5000);
                }catch (IOException e) {
                    resultLabel.setText("Ошибка сериализации");
                } catch (ClassNotFoundException e) {
                    resultLabel.setText("Ошибка десериализации");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (TimeoutException e) {
                    resultLabel.setText("Сервер временно не доступен");
                }
            }
        });
        updateThread.setDaemon(true);
        updateThread.start();
    }

    public void setCommunication(Communication communication) {
        this.communication = communication;
    }

    public void setLogin(String login) {
        this.login = login;
        loginLabel.setText(login);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TextArea getResultLabel() {
        return resultLabel;
    }

    private Controller12Field getPreparedController (Controller12Field controller) {

        controller.setNameFieldLabel(command.getParamsName()[0]);
        controller.setFormOfEducation();
        controller.setSemesterEnum();
        controller.setEyeColorEnum();
        controller.setHairColorEnum();
        controller.setLogin(login);
        controller.setPassword(password);
        controller.setCommunication(communication);
        controller.setCommand(command);
        controller.setResultLabel(resultLabel);

        return controller;
    }
}
