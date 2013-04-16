import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: skiv
 * Date: 10.04.13
 * Time: 7:19
 * To change this template use File | Settings | File Templates.
 */
public class Polymer {

  /**
   * Строит одну точку по номеру направления (номера нужны для удобства построения циклом)
   *
   * @param polymer   Массив, описывающий полимер
   * @param fromPoint Точка от которой будем строить
   * @param direction В какую сторону строить (0-5: x, -x, y, -y, z, -z)
   */
  public static ArrayList<int[]> buildPointByDirectionNumber(ArrayList<int[]> polymer, int fromPoint, int direction) {
    //Если понадобиться размещение построение из произвольной точки цепи,
    //то просто передавать значение position через параметр функции
    //int position = polymer.size();

    ArrayList<int[]> tPolymer = new ArrayList<int[]>();
    tPolymer.addAll(polymer);

    int[] currentPoint = polymer.get(fromPoint);
    int[] newPoint = new int[3];
    System.arraycopy(currentPoint, 0, newPoint, 0, currentPoint.length);

    switch (direction) {
      case 0:
        newPoint[0]++;
        break;
      case 1:
        newPoint[0]--;
        break;
      case 2:
        newPoint[1]++;
        break;
      case 3:
        newPoint[1]--;
        break;
      case 4:
        newPoint[2]++;
        break;
      case 5:
        newPoint[2]--;
        break;
    }

    tPolymer.add(newPoint);

    return tPolymer;
  }

  /**
   * Строит все возможное множество цепочек заданной длины
   *
   * @param length Максимальная длина цепочек
   * @return
   */
  public static ArrayList<ArrayList<int[]>> buildWholeSet(int[] coordinates, int length) {
    ArrayList<int[]> foundation = Polymer.getFoundation(coordinates);

    ArrayList<ArrayList<int[]>> tResultPolymers = new ArrayList<ArrayList<int[]>>();

    ArrayList<ArrayList<int[]>> resultPolymers = buildSetPolymersFromPoint(foundation, 0, length, tResultPolymers);

    return resultPolymers;
  }


  /**
   * Достраивает все возможное множество цепочек заданной длины из указанной точки
   *
   * @param polymer
   * @param fromPoint Точка, из которой начинается построение
   * @param length    Длина
   * @return
   */
  public static ArrayList<ArrayList<int[]>> buildSetPolymersFromPoint(ArrayList<int[]> polymer, int fromPoint, int length, ArrayList<ArrayList<int[]>> resultPolymers) {

    if (polymer.size() - 1 != length) {
      for (int i = 0; i <= 5; i++) {
        ArrayList<int[]> tPolymer = new ArrayList<int[]>();
        tPolymer.addAll(polymer);

        tPolymer = Polymer.buildPointByDirectionNumber(tPolymer, fromPoint, i);

        Polymer.buildSetPolymersFromPoint(tPolymer, tPolymer.size() - 1, length, resultPolymers);
      }
    } else {
      resultPolymers.add(polymer);
    }

    return resultPolymers;
  }


  /**
   * Создает полимер, состоящий из одной точки (условно - "базовая точка")
   *
   * @param coordinates
   * @return
   */
  public static ArrayList<int[]> getFoundation(int[] coordinates) {
    ArrayList<int[]> foundation = new ArrayList<int[]>();
    foundation.add(coordinates);

    return foundation;
  }

  /**
   * Количество самопересечений в полимере
   *
   * @param polymer
   * @return
   */
  public static int getIntersectionsCountInOnePolymer(ArrayList<int[]> polymer) {
    int result = 0;

    for (int i = 0; i < polymer.size(); i++) {

      for (int j = i + 1; j < polymer.size(); j++) {

        int[] pointA = polymer.get(i);
        int[] pointB = polymer.get(j);

        if (Arrays.equals(pointA, pointB)) result++;

      }

    }


    return result;
  }

  /**
   * Общее количество самопересечений в наборе полимеров
   *
   * @param polymers
   * @return
   */
  public static int getIntersectionsCountInManyPolymers(ArrayList<ArrayList<int[]>> polymers) {
    int result = 0;
    for (ArrayList<int[]> polymer : polymers) {
      result += Polymer.getIntersectionsCountInOnePolymer(polymer);
    }

    return result;
  }

  /**
   * Проверяет, не является ли полимер самоНЕпересекающимся
   *
   * @param polymer
   * @return
   */
  public static boolean isNotIntersected(ArrayList<int[]> polymer) {
    boolean notIntersected = true;

    for (int i = 0; i < polymer.size(); i++) {

      for (int j = i + 1; j < polymer.size(); j++) {

        int[] pointA = polymer.get(i);
        int[] pointB = polymer.get(j);

        if (Arrays.equals(pointA, pointB)) {
          notIntersected = false;
          break;

        }
      }

      if (!notIntersected) break;

    }


    return notIntersected;
  }

  /**
   * Возвращает количество самоНЕпересекающихся полимеров из набора
   *
   * @param polymers
   * @return
   */
  public static int getNotIntersectedCountInManyPolymers(ArrayList<ArrayList<int[]>> polymers) {
    int result = 0;
    for (ArrayList<int[]> polymer : polymers) {
      if (Polymer.isNotIntersected(polymer))
        result++;
    }

    return result;
  }


  /**
   * Возвращает средний квадрат расстояния между первым и последним узлами полимеров из набора
   *
   * @param polymers
   * @return
   */
  public static float getAverageRadius(ArrayList<ArrayList<int[]>> polymers) {
    float result = 0;

    for (ArrayList<int[]> polymer : polymers) {
      int lastPointNumber = polymer.size() - 1;
      int[] lastPoint = polymer.get(lastPointNumber);

      int x = lastPoint[0];
      int y = lastPoint[0];
      int z = lastPoint[0];

      float radiusSquared = x * x + y * y + z * z;

      result += radiusSquared;
    }

    result = result / polymers.size();

    return result;
  }

  /**
   * Возвращает случайный полимер
   *
   * @param foundationCoordinates
   * @param length
   * @return
   */
  public static ArrayList<int[]> getRandomPolymer(int[] foundationCoordinates, int length) {
    ArrayList<int[]> resultPolymer = Polymer.getFoundation(foundationCoordinates);

    final Random generator = new Random();
    for (int i = 1; i < length; i++) {
      int directionNumber = generator.nextInt(5);

      resultPolymer = Polymer.buildPointByDirectionNumber(resultPolymer, resultPolymer.size() - 1, directionNumber);
    }


    return resultPolymer;
  }

  public static ArrayList<ArrayList<int[]>> getRandomPolymersSet(int[] foundationCoordinates, int length, int count) {
    ArrayList<ArrayList<int[]>> resultPolymers = new ArrayList<ArrayList<int[]>>();

    for (int i = 0; i < count; i++) {
      ArrayList<int[]> tPolymer = Polymer.getRandomPolymer(foundationCoordinates, length);
      resultPolymers.add(tPolymer);
    }

    return resultPolymers;
  }

}
