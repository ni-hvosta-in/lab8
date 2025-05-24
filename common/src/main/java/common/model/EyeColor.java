package common.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Перечисление всех цветов глаз
 */
public enum EyeColor {
    GREEN("зеленый"),
    BLACK("черный"),
    YELLOW("желтый"),
    BROWN("коричневый");

    private final String col;

    EyeColor (String col){
        this.col = col;
    }

    /**
     * @return словарь название цвета + элемент из enum'а
     */
    static public Map <String, EyeColor> getColors(){

        Map <String, EyeColor> colorMap = new HashMap<>();
        colorMap.put(GREEN.col, GREEN);
        colorMap.put(BLACK.col, BLACK);
        colorMap.put(YELLOW.col, YELLOW);
        colorMap.put(BROWN.col, BROWN);

        return colorMap;
    }

    public String getColor() {
        return col;
    }
    static public Map <String, EyeColor> getColorsFromDB(){
        Map <String, EyeColor> colorMap = new HashMap<>();
        colorMap.put(GREEN.toString(), GREEN);
        colorMap.put(BLACK.toString(), BLACK);
        colorMap.put(YELLOW.toString(), YELLOW);
        colorMap.put(BROWN.toString(), BROWN);
        return colorMap;
    }
}