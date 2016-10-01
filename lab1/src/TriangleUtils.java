/**
 * Created by nikita.kuzin on 10/1/16.
 */



/*
* Пример запуска:
* > triangle.exe 5 5 5
* Равносторонний
*
* > triangle.exe
* Укажите длины сторон в качестве параметров. Формат ввода: triangle.exe a b c
* */

public class TriangleUtils {

    public enum TriangleType {
        NOT_TRIANGLE,
        ISOSCELES,
        EQUILATERAL,
        ORDINARY
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Укажите длины сторон в качестве параметров. Формат ввода: triangle.exe a b c");
            return;
        }
        try {
            Double[] sidesFromString = getSidesFromString(args);
            switch (getType(sidesFromString[0], sidesFromString[1], sidesFromString[2])) {
                case NOT_TRIANGLE:
                    System.out.println("Это не треугольник");
                    break;
                case EQUILATERAL:
                    System.out.println("Это равносторонний треугольник");
                    break;
                case ISOSCELES:
                    System.out.println("Это равнобедренный треугольник");
                    break;
                default:
                    System.out.println("Это обычный треугольник");
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Double[] getSidesFromString(String[] sides) throws Exception {
        String startErrMsg = "Некорректн(-ая/-ые) длин(-а/-ы): \"";
        String endErrMsg = "\". Длина должна быть положительным числом.";
        String errSides = "";
        Double[] output = new Double[3];
        for (int i = 0; i < sides.length; i++) {
            String side = sides[i];
            try {
                output[i] = Double.valueOf(side);
                if (output[i] < 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                errSides += side + "; ";
            }
        }
        if (!errSides.isEmpty()) {

            throw new Exception(startErrMsg + errSides.substring(0, errSides.length() - 2) + endErrMsg);
        }
        return output;
    }

    public static TriangleType getType(double side1, double side2, double side3) {
        if (!isTriangle(side1, side2, side3)) {
            return TriangleType.NOT_TRIANGLE;
        } else if (isEquilateral(side1, side2, side3)) {
            return TriangleType.EQUILATERAL;
        } else if (isIsosceles(side1, side2, side3)) {
            return TriangleType.ISOSCELES;
        }
        return TriangleType.ORDINARY;
    }

    private static boolean isTriangle(double side1, double side2, double side3) {
        return side1 < side2 + side3 && side2 < side1 + side3 && side3 < side1 + side2;
    }

    private static boolean isIsosceles(double side1, double side2, double side3) {
        return side1 == side2 || side2 == side3 || side1 == side3;
    }

    private static boolean isEquilateral(double side1, double side2, double side3) {
        return side1 == side2 && side2 == side3;
    }
}
