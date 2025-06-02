package nihvostain.managers.gui;

import common.model.StudyGroup;
import common.model.StudyGroupWithKey;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GraphView {
    private Stage stage;
    private Canvas canvas;
    public GraphView() {
        stage = new Stage();
        stage.setTitle("Graph View");

        canvas = new Canvas(1200, 900);
        StackPane root = new StackPane(canvas);

        Scene scene = new Scene(root);
        stage.setScene(scene);

    }

    public void show() {
        stage.show();
    }
    public void drawStudyGroups(List<StudyGroupWithKey> studyGroups) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (StudyGroupWithKey studyGroup : studyGroups) {
            drawStudyGroup(gc, studyGroup.getStudyGroup(), studyGroup.getLogin());
        }
    }

    private void drawStudyGroup(GraphicsContext gc, StudyGroup studyGroup, String login) {
        gc.setFill(getColorByLogin(login));
        gc.fillOval(studyGroup.getCoordinates().getX(), studyGroup.getCoordinates().getY(), studyGroup.getStudentsCount(), studyGroup.getStudentsCount());
    }

    private Color getColorByLogin(String login) {
        int loginHash = login.hashCode();
        int r = (loginHash & 0xFF0000) >> 16;
        int g = (loginHash & 0x00FF00) >> 8;
        int b =  loginHash & 0x0000FF;
        return Color.rgb(r, g, b);
    }
}
