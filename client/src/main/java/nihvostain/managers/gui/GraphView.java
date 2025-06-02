package nihvostain.managers.gui;

import common.model.StudyGroup;
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
    public void drawStudyGroups(List<StudyGroup> studyGroups) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (StudyGroup studyGroup : studyGroups) {
            drawStudyGroup(gc, studyGroup);
        }
    }

    private void drawStudyGroup(GraphicsContext gc, StudyGroup studyGroup) {
        gc.setFill(Color.RED);
        gc.fillOval(studyGroup.getCoordinates().getX(), studyGroup.getCoordinates().getY(), studyGroup.getStudentsCount(), studyGroup.getStudentsCount());
    }
}
