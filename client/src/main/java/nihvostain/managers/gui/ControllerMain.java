package nihvostain.managers.gui;

import common.exceptions.InputFromScriptException;
import common.exceptions.RecursionDepthExceededException;
import common.managers.Deserialize;
import common.managers.Request;
import common.managers.RequestObj;
import common.managers.ResponseStudyGroups;
import common.model.Person;
import common.model.StudyGroupWithKey;
import common.utility.TypeRequest;
import javafx.application.Platform;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nihvostain.commands.*;
import nihvostain.managers.Communication;
import nihvostain.managers.Invoker;
import nihvostain.utility.Command;

import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ControllerMain {
    public ChoiceBox<String> languageList;
    public ComboBox comboBox;
    public TextField filter;
    @FXML private Label loginLabel;
    @FXML private TextArea resultLabel = null;
    private Communication communication;
    private String login = "";
    private String password;
    private Command command;
    private final ContextMenu contextMenu = new ContextMenu();
    @FXML private TableView<StudyGroupWithKey> studyGroups;
    @FXML private TableColumn<StudyGroupWithKey, Integer> keyColumn;
    @FXML private TableColumn<StudyGroupWithKey, String> idColumn;
    @FXML private TableColumn<StudyGroupWithKey, String> nameColumn;
    @FXML private TableColumn<StudyGroupWithKey, String> xColumn;
    @FXML private TableColumn<StudyGroupWithKey, String> yColumn;
    @FXML private TableColumn<StudyGroupWithKey, String> creationDateColumn;
    @FXML private TableColumn<StudyGroupWithKey, String> studentsCountColumn;
    @FXML private TableColumn<StudyGroupWithKey, String> formOfEducationColumn;
    @FXML private TableColumn<StudyGroupWithKey, String> semesterEnumColumn;
    @FXML private TableColumn<StudyGroupWithKey, String> namePColumn;
    @FXML private TableColumn<StudyGroupWithKey, String> birthdayColumn;
    @FXML private TableColumn<StudyGroupWithKey, String> passportIdColumn;
    @FXML private TableColumn<StudyGroupWithKey, String> eyeColorColumn;
    @FXML private TableColumn<StudyGroupWithKey, String> hairColorColumn;
    private Locale currentLocale = new Locale("ru");
    private GraphView graphView = new GraphView();
    private ObservableList<StudyGroupWithKey> groups = FXCollections.observableArrayList();
    private List<StudyGroupWithKey> sG = new ArrayList<>();
    private List<StudyGroupWithKey> sGOld = new ArrayList<>();
    private TableColumn<StudyGroupWithKey, ?> sortColumn = keyColumn;
    private TableColumn.SortType sortType = TableColumn.SortType.ASCENDING;
    @FXML public void execute(ActionEvent actionEvent) throws IOException {

        Button clicked = (Button) actionEvent.getSource();
        command = Invoker.getCommands().get(clicked.getId());
        String comm = clicked.getId();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("nihvostain.managers.gui.local.GuiLabels", currentLocale);
        if (command.getNeededArgsLen() == 1){

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/onefieldset.fxml"));
            fxmlLoader.setResources(resourceBundle);
            Parent root = fxmlLoader.load();
            ControllerOneField controllerOneField = fxmlLoader.getController();
            controllerOneField.setFieldLabel(fxmlLoader.getResources().getString(command.getParamsName()[0]));
            controllerOneField.setCommand(command);
            controllerOneField.setResultLabel(resultLabel);
            showScene(root);
            comm = comm +" "+ controllerOneField.getFieldValue();

        } else if (command.getNeededArgsLen() > 1){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/12Fields.fxml"));
            fxmlLoader.setResources(resourceBundle);
            Parent root = fxmlLoader.load();
            Controller12Field controller = getPreparedController(fxmlLoader.getController());
            for (int i = 0; i < 12-command.getNeededArgsLen(); i++) {
                controller.removeFirstHBox();
            }
            if (command.getNeededArgsLen() == 5) {
                controller.setInputPerson(true);
            }
            showScene(root);
            comm = comm + " " + controller.getFieldValue();

        }
        Scanner scanner = new Scanner(comm);
        resultLabel.setEditable(false);
        System.out.println(comm);
        Invoker invoker = new Invoker(scanner, communication, login, password, resultLabel, resourceBundle);
        invoker.setFileFlag(true);
        try {
            try {
                invoker.scanning();
            } catch (RecursionDepthExceededException e) {
                resultLabel.setText(resourceBundle.getString(e.getMessage()));
            }
            updateData();
        } catch (InputFromScriptException | RuntimeException ignored) {

        } catch (IOException | ClassNotFoundException e) {
            resultLabel.setText(resourceBundle.getString("data.error"));
        } catch (TimeoutException e) {
            resultLabel.setText(resourceBundle.getString("server.timeOut"));
        }
    }
    @FXML public void edit() throws IOException {

        StudyGroupWithKey studyGroupWithKey = studyGroups.getSelectionModel().getSelectedItem();
        if (studyGroupWithKey != null ){
            updateStudyGroup(studyGroupWithKey);
        }
    }

    @FXML public void delete(){
        StudyGroupWithKey studyGroupWithKey = studyGroups.getSelectionModel().getSelectedItem();
        if (studyGroupWithKey != null){
            deleteStudyGroup(studyGroupWithKey);
        }
    }

    @FXML public void visualize(ActionEvent actionEvent){
        graphView.show();
    }

    @FXML private void initialize() {
        languageList.getItems().clear();
        languageList.getItems().addAll("Русский", "Slovenski", "Ελληνικά", "Español (PA)");

        switch (currentLocale.getLanguage()) {
            case "sl":
                languageList.setValue("Slovenski");
                break;
            case "el":
                languageList.setValue("Ελληνικά");
                break;
            case "es":
                languageList.setValue("Español (PA)");
                break;
            default:
                languageList.setValue("Русский");
        }

        languageList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.equals(oldVal)) {
                Locale locale;
                switch (newVal) {
                    case "Slovenski":
                        locale = new Locale("sl");
                        break;
                    case "Ελληνικά":
                        locale = new Locale("el");
                        break;
                    case "Español (PA)":
                        locale = new Locale("es", "PA");
                        break;
                    default:
                        locale = new Locale("ru");
                }
                reloadWindow(locale);
            }
        });
        filter.textProperty().addListener((observable, oldValue, newValue) -> {
            applyFilter();
        });
        loginLabel.setText(login);
        setupTableColumns();
        setSorting();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("nihvostain.managers.gui.local.GuiLabels", currentLocale);
        setContextMenu(resourceBundle);
        comboBox.getItems().addAll(resourceBundle.getString("id"), resourceBundle.getString("name"), resourceBundle.getString("x"), resourceBundle.getString("y"), resourceBundle.getString("studentsCount"),
                resourceBundle.getString("creationDate"), resourceBundle.getString("formOfEducation"), resourceBundle.getString("semesterEnum"), resourceBundle.getString("nameP"),
                resourceBundle.getString("birthday"), resourceBundle.getString("passportID"), resourceBundle.getString("eyeColor"), resourceBundle.getString("hairColor"));
        comboBox.getSelectionModel().selectFirst();
    }

    private void setupTableColumns() {

        NumberFormat integerFormatter = NumberFormat.getIntegerInstance(currentLocale);
        NumberFormat numberFormatter = NumberFormat.getNumberInstance(currentLocale);

        keyColumn.setCellValueFactory(new PropertyValueFactory<>("key"));

        idColumn.setCellValueFactory(data -> new SimpleStringProperty(integerFormatter.format(data.getValue().getId())));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        xColumn.setCellValueFactory(data -> new SimpleStringProperty(integerFormatter.format(data.getValue().getCoordinates().getX())));

        yColumn.setCellValueFactory(data -> new SimpleStringProperty(numberFormatter.format(data.getValue().getCoordinates().getY())));

        creationDateColumn.setCellValueFactory(cellData -> {
            LocalDateTime date = cellData.getValue().getStudyGroup().getCreationDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(currentLocale);
            String formatted = date.format(formatter);
            return new SimpleStringProperty(formatted);
        });

        studentsCountColumn.setCellValueFactory(data -> new SimpleStringProperty(integerFormatter.format(data.getValue().getStudentsCount())));

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
            ZonedDateTime birthday = admin != null ? admin.getBirthday() : null;

            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)
                    .withLocale(currentLocale);

            String formatted = birthday != null ? birthday.format(formatter) : "";
            return new SimpleStringProperty(formatted);
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
            items.setAll(sorted);
            return true;
        });
    }

    public void startUpdates() {
        Thread updateThread = new Thread(() -> {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("nihvostain.managers.gui.local.GuiLabels", currentLocale);
            while (true) {
                try {
                    updateData();
                    Thread.sleep(10000);
                }catch (IOException | ClassNotFoundException e) {
                    resultLabel.setText(resourceBundle.getString("data.error"));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (TimeoutException e) {
                    resultLabel.setText(resourceBundle.getString("server.timeOut"));
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
        if (loginLabel != null) {
            loginLabel.setText(login); // безопасно, если элемент уже загружен
        }
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TextArea getResultLabel() {
        return resultLabel;
    }

    private Controller12Field getPreparedController (Controller12Field controller) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("nihvostain.managers.gui.local.GuiLabels", currentLocale);
        System.out.println(command + command.getParamsName()[0]);
        controller.setNameFieldLabel(resourceBundle.getString(command.getParamsName()[0]));
        controller.setFormOfEducation();
        controller.setSemesterEnum();
        controller.setEyeColorEnum();
        controller.setHairColorEnum();
        controller.setLogin(login);
        controller.setPassword(password);
        controller.setCommunication(communication);
        controller.setCommand(command);
        controller.setResultLabel(resultLabel);
        controller.setResourceBundle(resourceBundle);
        return controller;
    }
    private void showScene(Parent root){
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Окно ввода");
        stage.initModality(Modality.APPLICATION_MODAL); // Блокирует родительское окно
        stage.showAndWait(); // Ожидание закрытия окна
    }
    private boolean isEqualsStudyGroups(List<StudyGroupWithKey> sG1, List<StudyGroupWithKey> sG2) {
        int ans = 0;

        if (sG1.size() != sG2.size()) {
            return false;
        } else {
            for (int i = 0; i < sG1.size(); i++) {

                if (!sG1.get(i).equals(sG2.get(i))) {
                    return false;
                } else {
                    ans++;
                }
            }
        }
        return ans == sG1.size();
    }
    private void updateStudyGroup(StudyGroupWithKey studyGroupWithKey) throws IOException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("nihvostain.managers.gui.local.GuiLabels", currentLocale);
        if (studyGroupWithKey.getLogin().equals(login)) {
            String comm = "update";
            command = Invoker.getCommands().get(comm);
            comm += " " + studyGroupWithKey.getId();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/12Fields.fxml"));
            fxmlLoader.setResources(resourceBundle);
            Parent root = fxmlLoader.load();
            Controller12Field controller = getPreparedController(fxmlLoader.getController());
            controller.removeFirstHBox();
            controller.setInputFromTableFlag(true);
            controller.setResourceBundle(resourceBundle);
            showScene(root);
            comm = comm + " " + controller.getFieldValue();
            Scanner scanner = new Scanner(comm);
            Invoker invoker = new Invoker(scanner, communication, login, password, resultLabel, resourceBundle);
            invoker.setFileFlag(true);
            try {
                invoker.scanning();
                updateData();
            } catch (InputFromScriptException | RecursionDepthExceededException | ClassNotFoundException |
                     TimeoutException ignored) {
            }
        } else {
            resultLabel.setText(resourceBundle.getString("alien"));
        }
    }
    private void deleteStudyGroup(StudyGroupWithKey studyGroupWithKey){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("nihvostain.managers.gui.local.GuiLabels", currentLocale);
        if (studyGroupWithKey.getLogin().equals(login)) {
            String key = studyGroupWithKey.getKey();
            ArrayList<String> args = new ArrayList<>();
            args.add(key);
            Command delete = new RemoveKeyCommand(communication, login, password);
            try {
                communication.send(delete.request(args).addUser(login, password).serialize());
                delete.request(args);
                byte[] message = communication.receive();
                resultLabel.setText(new Deserialize<RequestObj>(message).deserialize().getRequest().toString());
                updateData();
            } catch (IOException | ClassNotFoundException | TimeoutException e) {
                resultLabel.setText(e.getMessage());
            }
        } else {
            resultLabel.setText(resourceBundle.getString("alien"));
        }
    }
    private void updateData() throws IOException, TimeoutException, ClassNotFoundException {
        byte[] request = new Request(TypeRequest.REQUEST_STUDYGROUPS).addUser(login, password).serialize();
        communication.send(request);
        byte[] response = communication.receive();
        ResponseStudyGroups data = new Deserialize<ResponseStudyGroups>(response).deserialize();
        List<StudyGroupWithKey> newData = data.getStudyGroups();
        sG = newData;
        if (!isEqualsStudyGroups(sG, sGOld)) {
            Platform.runLater(() -> {
                // Сохраняем состояние сортировки перед обновлением
                // Обновляем данные
                graphView.drawStudyGroups(sG);
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

            sGOld = sG;
            System.out.println("обновил");
    }
        }

    public void setCurrentLocale(Locale currentLocale) {
        this.currentLocale = currentLocale;

    }

    private void reloadWindow(Locale locale) {
        System.out.println("обновляю" + locale);
        currentLocale = locale;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MainWindow.fxml"));
        ResourceBundle resourceBundle = ResourceBundle.getBundle("nihvostain.managers.gui.local.GuiLabels", locale);
        fxmlLoader.setResources(resourceBundle);

        if (graphView != null && graphView.getStage() != null && graphView.getStage().isShowing()) {
            graphView.getStage().close();
        }
        fxmlLoader.setControllerFactory(controllerClass -> {
            try {
                ControllerMain controllerMain = (ControllerMain) controllerClass.getDeclaredConstructor().newInstance();
                controllerMain.setCurrentLocale(locale);
                controllerMain.setLogin(login);
                controllerMain.setPassword(password);
                controllerMain.setCommunication(communication);
                controllerMain.startUpdates();
                return controllerMain;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Parent root;
        ControllerMain controllerMain;
        try {
            root = fxmlLoader.load();
            controllerMain = fxmlLoader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setContextMenu(resourceBundle);

        LinkedHashMap<String, Command> commands = new LinkedHashMap<>();
        commands.put("help", new HelpCommand(commands.values(), controllerMain.getResultLabel(), resourceBundle));
        commands.put("show", new ShowCommand(communication));
        commands.put("info", new InfoCommand(communication));
        commands.put("insert", new InsertCommand(communication, login, password));
        commands.put("update", new UpdateCommand(communication, login, password));
        commands.put("remove_key", new RemoveKeyCommand(communication, login, password));
        commands.put("clear", new ClearCommand(communication));
        commands.put("execute_script", new ExecuteScriptCommand(
                communication, login, password, controllerMain.getResultLabel(), resourceBundle));
        commands.put("exit", new ExitCommand(communication));
        commands.put("remove_lower", new RemoveLowerCommand(communication));
        commands.put("replace_if_greater", new ReplaceIfGreaterCommand(communication, login, password));
        commands.put("remove_greater_key", new RemoveGreaterKeyCommand(communication));
        commands.put("group_counting_by_semester_enum", new GroupCountingBySemesterEnum(communication));
        commands.put("filter_contains_name", new FilterContainsNameCommand(communication));
        commands.put("filter_greater_than_group_admin", new FilterGreaterThanGroupAdminCommand(communication));
        Invoker.setCommands(commands);

        Scene scene = new Scene(root);
        if (languageList.getScene() != null) {
            Stage stage = (Stage) languageList.getScene().getWindow();
            stage.setTitle("Main Window");
            stage.setScene(scene);
            stage.show();
        }
    }


    private void applyFilter() {
        String filterText = filter.getText();
        String selectedField = (String) comboBox.getSelectionModel().getSelectedItem();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("nihvostain.managers.gui.local.GuiLabels", currentLocale);
        if (filterText == null || filterText.isEmpty() || selectedField == null) {
            studyGroups.setItems(FXCollections.observableArrayList(sG));
            return;
        }

        Stream<StudyGroupWithKey> stream = sG.stream();
        String newValue = filterText;
        System.out.println(selectedField);
        selectedField = findKeyByValue(resourceBundle, selectedField);
        try {
            switch (selectedField) {
                case "id":
                    stream = stream.filter(group -> String.valueOf(group.getId()).contains(newValue));
                    break;

                case "name":
                    stream = stream.filter(group -> group.getName() != null && group.getName().contains(newValue));
                    break;

                case "x":
                    stream = stream.filter(group -> String.valueOf(group.getCoordinates().getX()).contains(newValue));
                    break;

                case "y":
                    stream = stream.filter(group -> String.valueOf(group.getCoordinates().getY()).contains(newValue));
                    break;

                case "studentsCount":
                    stream = stream.filter(group -> String.valueOf(group.getStudentsCount()).contains(newValue));
                    break;

                case "creationDate":
                    stream = stream.filter(group -> group.getStudyGroup().getCreationDate() != null &&
                            group.getStudyGroup().getCreationDate().toString().contains(newValue));
                    break;

                case "formOfEducation":
                    stream = stream.filter(group -> group.getStudyGroup().getFormOfEducation() != null &&
                            group.getStudyGroup().getFormOfEducation().toString().contains(newValue));
                    break;

                case "semesterEnum":
                    stream = stream.filter(group -> group.getStudyGroup().getSemesterEnum() != null &&
                            group.getStudyGroup().getSemesterEnum().toString().contains(newValue));
                    break;

                case "nameP":
                    stream = stream.filter(group -> {
                        Person admin = group.getStudyGroup().getGroupAdmin();
                        return admin != null && admin.getName() != null && admin.getName().contains(newValue);
                    });
                    break;

                case "birthday":
                    stream = stream.filter(group -> {
                        Person admin = group.getStudyGroup().getGroupAdmin();
                        return admin != null && admin.getBirthday() != null &&
                                admin.getBirthday().toString().contains(newValue);
                    });
                    break;

                case "passportID":
                    stream = stream.filter(group -> {
                        Person admin = group.getStudyGroup().getGroupAdmin();
                        return admin != null && admin.getPassportID() != null &&
                                admin.getPassportID().contains(newValue);
                    });
                    break;

                case "eyeColor":
                    stream = stream.filter(group -> {
                        Person admin = group.getStudyGroup().getGroupAdmin();
                        return admin != null && admin.getEyeColor() != null &&
                                admin.getEyeColor().toString().contains(newValue);
                    });
                    break;

                case "hairColor":
                    stream = stream.filter(group -> {
                        Person admin = group.getStudyGroup().getGroupAdmin();
                        return admin != null && admin.getHairColor() != null &&
                                admin.getHairColor().toString().contains(newValue);
                    });
                    break;

                default:
                    throw new IllegalArgumentException("Unknown filter field: " + selectedField);
            }

            List<StudyGroupWithKey> filteredList = stream.collect(Collectors.toList());
            studyGroups.setItems(FXCollections.observableArrayList(filteredList));

        } catch (NullPointerException ignored) {
        }
    }


    public String findKeyByValue(ResourceBundle bundle, String valueToFind) {
        for (String key : bundle.keySet()) {
            String value = bundle.getString(key);
            if (value == valueToFind) {
                return key;  // нашли ключ
            }
        }
        return null; // ключ не найден
    }

    private void setContextMenu(ResourceBundle resourceBundle) {
        graphView.getCanvas().setOnMouseClicked(event -> {
            double x = event.getX();
            double y = event.getY();
            contextMenu.hide();
            contextMenu.getItems().clear();
            StudyGroupWithKey studyGroupWithKey = graphView.getStudyGroupByCoordinates(x, y);
            if (studyGroupWithKey != null) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    if (event.getClickCount() == 1) {
                        resultLabel.setText(studyGroupWithKey.getStudyGroup().toString());
                    } else if (event.getClickCount() == 2) {
                        try {
                            updateStudyGroup(studyGroupWithKey);
                        } catch (IOException ignored) {
                        }
                    }
                } else if (event.getButton() == MouseButton.SECONDARY) {
                    MenuItem editItem = new MenuItem(resourceBundle.getString("edit"));
                    editItem.setOnAction(e -> {
                        try {
                            updateStudyGroup(studyGroupWithKey);
                        } catch (IOException ignored) {
                        }
                    });
                    MenuItem delItem = new MenuItem(resourceBundle.getString("delete"));
                    delItem.setOnAction(e -> {
                        deleteStudyGroup(studyGroupWithKey);
                    });
                    contextMenu.getItems().add(editItem);
                    contextMenu.getItems().add(delItem);
                    contextMenu.show(graphView.getCanvas(), event.getScreenX(), event.getScreenY());
                }
            } else {
                resultLabel.clear();
            }
        });
    }

    public ChoiceBox<String> getLanguageList() {
        return languageList;
    }
}
