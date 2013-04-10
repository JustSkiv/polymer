import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: skiv
 * Date: 10.04.13
 * Time: 7:19
 * To change this template use File | Settings | File Templates.
 */
public class Polymer {
  public int maxLength, currentLenght = 0;
  public int[][] coordinates;

  public Polymer(int tLength) {
    maxLength = tLength;
    coordinates = new int[maxLength][3];

    coordinates[0] = new int[]{0, 0, 0};
    coordinates[1] = new int[]{0, 2, 4};

    currentLenght = 1;

    //System.out.println(Arrays.deepToString(coordinates));
    //System.out.println(coordinates.length);
  }

  /**
   * Строит одно звено по номеру (номера для удобства построения циклом)
   *
   * @param direction В какую сторону строить (0-5: x, -x, y, -y, z, -z)
   * @param from      Точка от которой будем строить
   */
  public void buildByNumber(int direction, int[] from) {
    //Если понадобиться размещение построение из произвольной точки цепи,
    //то просто передавать значение position через параметр функции

    int[] newPoint = from;

    int position = currentLenght;
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

    coordinates[position] = newPoint;

    currentLenght++;

  }

  /**
   * Строит все возможное множество цепочек заданной длины
   *
   * @param lenght Максимальная длина цепочек
   * @return
   */
  public static ArrayList buildWholeSet(int lenght) {
    Polymer foundation = new Polymer(lenght);

    ArrayList result = buildSetFromPoint(foundation[0], lenght);

  }



  /**
   * Строит все возможное множество цепочек заданной длины из указанной точки
   *
   * @param point Точка, из которой начинается построение
   * @param length Длина
   * @return
   */
  public static ArrayList buildSetFromPoint(int[] point, int length) {

  }
}
