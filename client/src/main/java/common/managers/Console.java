package common.managers;

/**
 * Класс консоли
 */
public class Console {
    /**
     * @param o объект для вывода
     * @param flag флаг
     */
   public static void write(Object o, boolean flag){
        if (!flag){
            System.out.print(o);
        }
   }

    /**
     * @param o объект для вывода
     * @param flag флаг
     */
    public static void writeln(Object o, boolean flag){
        if (!flag){
            System.out.println(o);
        }
    }
}
