package nihvostain.managers.gui;

import common.model.Coordinates;
import common.model.StudyGroup;
import common.model.StudyGroupWithKey;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphView {
    private final Stage stage;
    private final Canvas canvas;
    private TextArea resultLabel;
    private final int canvasW = 800;
    private final int canvasH = 600;
    private final GraphicsContext gc ;
    private List<StudyGroupWithKey> studyGroups;
    private final Map<String, AnimatedStudyGroup> animatedGroups = new HashMap<>();
    public GraphView() {

        stage = new Stage();
        stage.setTitle("Graph View");

        canvas = new Canvas(canvasW, canvasH);
        canvas.setOnMouseClicked(event -> {
            double x = event.getX();
            double y = event.getY();
            StudyGroupWithKey studyGroupWithKey = getStudyGroupByCoordinates(x, y);
            if (studyGroupWithKey != null) {
                resultLabel.setText(studyGroupWithKey.getStudyGroup().toString());
            } else {
                resultLabel.clear();
            }
        });

        StackPane root = new StackPane(canvas);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        gc = canvas.getGraphicsContext2D();
    }

    public void show() {
        stage.show();
    }

    private class AnimatedStudyGroup {
        private StudyGroupWithKey studyGroup;
        float currentX, currentY;
        float targetX, targetY;

        public AnimatedStudyGroup(StudyGroupWithKey studyGroup) {
            this.studyGroup = studyGroup;
            this.currentX = 0;
            this.currentY = 0;
            targetX = calculateX(studyGroup.getCoordinates().getX());
            targetY = calculateY(studyGroup.getCoordinates().getY());
        }

        public boolean update() {
            float speed = 4f;
            float dx = targetX - currentX;
            float dy = targetY - currentY;
            float dist = (float) Math.sqrt(dx * dx + dy * dy);

            if (dist <= speed) {
                currentX = targetX;
                currentY = targetY;
                return false;
            }

            float vx = dx / dist * speed;
            float vy = dy / dist * speed;
            currentX += vx;
            currentY += vy;
            //еще кто-то движется
            return true;
        }

        public void setTargetX(float targetX) {
            this.targetX = targetX;
        }
        public void setTargetY(float targetY) {
            this.targetY = targetY;
        }
        public void setStudyGroup(StudyGroupWithKey studyGroup) {
            this.studyGroup = studyGroup;
        }
    }

    private StudyGroupWithKey getStudyGroupByCoordinates(double x, double y) {
        for (StudyGroupWithKey studyGroupWithKey : studyGroups) {
            float sGX = calculateX(studyGroupWithKey.getCoordinates().getX());
            float sGY = calculateY(studyGroupWithKey.getCoordinates().getY());
            float r = studyGroupWithKey.getStudentsCount()/2f;
            if (sGX + r >= x && sGX - r <= x && sGY + r >= y && sGY - r <= y) {
                return studyGroupWithKey;
            }
        }
        return null;
    }
    public void drawStudyGroups(List<StudyGroupWithKey> studyGroups) {
        this.studyGroups = studyGroups;
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawAxes(gc);
        for (StudyGroupWithKey studyGroup : studyGroups) {
            String key = studyGroup.getKey();
            if (animatedGroups.containsKey(key)) { //если такой ключ есть, то есть меняем координаты
                animatedGroups.get(key).setTargetX(calculateX(studyGroup.getCoordinates().getX()));
                animatedGroups.get(key).setTargetY(calculateY(studyGroup.getCoordinates().getY()));
                animatedGroups.get(key).setStudyGroup(studyGroup);
            } else { //если такого ключа нет, просто добавляем в мапу
                animatedGroups.put(key, new AnimatedStudyGroup(studyGroup));
            }
        }
        // Удалим отсутствующие
        animatedGroups.keySet().removeIf(oldKey ->
                studyGroups.stream().noneMatch(g -> g.getKey().equals(oldKey))
        );

        animateAll();
    }

    private void animateAll() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.clearRect(0, 0, canvasW, canvasH);
                drawAxes(gc);

                boolean animInProc = false;

                for (AnimatedStudyGroup anim : animatedGroups.values()) {
                    if (anim.update()){
                        animInProc = true;
                    }

                    drawStudyGroup(gc, anim.studyGroup.getStudyGroup(), anim.studyGroup.getLogin(), anim.currentX, anim.currentY);

                }

                if (!animInProc) {
                    stop();
                }
            }
        };

        timer.start();
    }

    private void drawStudyGroup(GraphicsContext gc, StudyGroup studyGroup, String login, float centerX, float centerY) {
        float radius = studyGroup.getStudentsCount() / 2f;
        float ovalX = centerX - radius;
        float ovalY = centerY - radius;
        float diameter = radius * 2;

        gc.setStroke(getColorByLogin(login));
        gc.setLineWidth(4);
        gc.strokeOval(ovalX, ovalY, diameter, diameter);
    }


    private Color getColorByLogin(String login) {
        int loginHash = login.hashCode();
        int r = (loginHash & 0xFF0000) >> 16;
        int g = (loginHash & 0x00FF00) >> 8;
        int b =  loginHash & 0x0000FF;
        return Color.rgb(r, g, b);
    }

    private void drawAxes(GraphicsContext gc) {

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        double centerX = calculateX(0);
        double centerY = calculateY(0);

        gc.strokeLine(centerX, canvasH, centerX, 0);

        gc.strokeLine(0, centerY, canvasW, centerY);

    }

    private float calculateX(float x) {
        return canvasW / 2f + x;
    }
    private float calculateY(float y) {
        return canvasH / 2f - y;
    }

    public void setResultLabel(TextArea resultLabel) {
        this.resultLabel = resultLabel;
    }
}
