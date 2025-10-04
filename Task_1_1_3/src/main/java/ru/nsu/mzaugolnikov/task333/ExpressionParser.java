package ru.nsu.mzaugolnikov.task333;

/**
 * Преобразование строк в математические выражения.
 */
public class ExpressionParser {

    /**
     * Основной метод для парсинга строки в Expression.
     *
     * @param input строка с математическим выраж
     * @return объект Expression
     */
    public static Expression parse(String input) {
        // удаляем пробелы
        String s = input.replace(" ", "");
        return parseExpr(s);
    }

    /**
     * Вспомогательный метод для рекурсивного парсинга.
     *
     * @param s строка без пробелов и внешних скобок
     * @return объект Expression
     */
    private static Expression parseExpr(String s) {
        // убираем внешние скобки
        while (s.startsWith("(") && s.endsWith(")") && check(s.substring(1, s.length() - 1))) {
            s = s.substring(1, s.length() - 1);
        }

        // ищем + или -
        int level = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c == ')') {
                level++;
            } else if (c == '(') {
                level--;
            } else if (level == 0 && (c == '+' || c == '-')) {
                String left = s.substring(0, i);
                String right = s.substring(i + 1);
                if (c == '+') {
                    return new Add(parseExpr(left), parseExpr(right));
                } else {
                    return new Sub(parseExpr(left), parseExpr(right));
                }
            }
        }

        // ищем * или /
        level = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c == ')') {
                level++;
            } else if (c == '(') {
                level--;
            } else if (level == 0 && (c == '*' || c == '/')) {
                String left = s.substring(0, i);
                String right = s.substring(i + 1);
                if (c == '*') {
                    return new Mult(parseExpr(left), parseExpr(right));
                } else {
                    return new Div(parseExpr(left), parseExpr(right));
                }
            }
        }

        // если это число
        try {
            double val = Double.parseDouble(s);
            return new Number(val);
        } catch (Exception e) {
            return new Variable(s);
        }
    }

    /**
     * Проверка на балансированность строки.
     *
     * @param s строка
     * @return true/false соотвестветсвенно
     */
    static boolean check(String s) {
        int b = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                b++;
            }
            if (c == ')') {
                b--;
            }
            if (b < 0) {
                return false;
            }
        }
        return b == 0;
    }
}
