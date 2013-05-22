import uk.org.iscream.vrmlgraph.*;

import java.util.*;

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


    if (direction < 3) newPoint[direction]++;
    else newPoint[5 - direction]--;

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
   * Возвращает энергию одного полимера
   *
   * @param polymer
   * @return
   */
  public static int getEnergyInOnePolymer(ArrayList<int[]> polymer) {
    int energy = 0;
    int epsilon = -1;
    for (int i = 0; i < polymer.size(); i++) {

      for (int j = i + 1; j < polymer.size(); j += 2) {

        int[] pointA = polymer.get(i);
        int[] pointB = polymer.get(j);

        if (Arrays.equals(pointA, pointB)) {
          continue;
        }

        int distance = getDistanceSquared(pointA, pointB);

        if (distance == 1) {
          energy += epsilon;
        }

      }

    }


    return energy;
  }

  /**
   * Возвращает сумму энергий полимеров набора
   *
   * @param polymers
   * @return
   */
  public static int getEnergyInManyPolymers(ArrayList<ArrayList<int[]>> polymers) {
    int result = 0;
    for (ArrayList<int[]> polymer : polymers) {
      result += Polymer.getEnergyInOnePolymer(polymer);
    }

    return result;
  }

  /**
   * Возвращает сумм квадратов расстояния между концами полимеров по энергиям
   *
   * @param polymers
   * @return
   */
  public static Map<Integer, Double> getDistributionOfDistancesByEnergyInManyPolymers(ArrayList<ArrayList<int[]>> polymers) {
    Map<Integer, Double> distribution = new HashMap<Integer, Double>();

    for (ArrayList<int[]> polymer : polymers) {
      int tEnergy = Polymer.getEnergyInOnePolymer(polymer);
      double tRadiusTotal = distribution.get(tEnergy) == null ? 0 : distribution.get(tEnergy);

      double radiusSquared = getRadiusSquared(polymer);

      distribution.put(tEnergy, (tRadiusTotal + radiusSquared));
    }


    return distribution;
  }

  /**
   * Возвращает распределение полимеров по энергиям
   *
   * @param polymers
   * @return
   */
  public static Map<Integer, Integer> getDistributionByEnergyInManyPolymers(ArrayList<ArrayList<int[]>> polymers) {
    Map<Integer, Integer> distribution = new HashMap<Integer, Integer>();

    for (ArrayList<int[]> polymer : polymers) {
      if (Polymer.isNotIntersected(polymer)) {

        int tEnergy = Polymer.getEnergyInOnePolymer(polymer);
        int tCount = distribution.get(tEnergy) == null ? 0 : distribution.get(tEnergy);

        distribution.put(tEnergy, ++tCount);
      }
    }


    return distribution;
  }

  /**
   * !!! НЕ РЕАЛИЗОВАНО (пока не требуется) !!!
   * Возвращает среднюю энергию набора полимеров
   *
   * @param polymers
   * @return
   */
  public static Map<Integer, Integer> getEnergyAverageInManyPolymers(ArrayList<ArrayList<int[]>> polymers) {
    Map<Integer, Integer> distribution;

    distribution = getDistributionByEnergyInManyPolymers(polymers);

    for (Map.Entry entry : distribution.entrySet()) {

    }

    return distribution;
  }

  /**
   * Возвращает квадрат расстояния между указанными узлами
   *
   * @param pointA
   * @param pointB
   * @return
   */
  public static int getDistanceSquared(int[] pointA, int[] pointB) {
    int x1 = pointA[0];
    int y1 = pointA[1];
    int z1 = pointA[2];

    int x2 = pointB[0];
    int y2 = pointB[1];
    int z2 = pointB[2];

    int dx = x1 - x2;
    int dy = y1 - y2;
    int dz = z1 - z2;

    int radiusSquared = (int) (Math.pow(dx, 2) + Math.pow(dy, 2) + Math.pow(dz, 2));


    return radiusSquared;
  }

  /**
   * Возвращает квадрат расстояния между первым и последним узлами полимера
   *
   * @param polymer
   * @return
   */
  public static double getRadiusSquared(ArrayList<int[]> polymer) {

    int[] firstPoint = polymer.get(0);

    int lastPointNumber = polymer.size() - 1;
    int[] lastPoint = polymer.get(lastPointNumber);

    double radiusSquared = getDistanceSquared(firstPoint, lastPoint);


    return radiusSquared;
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
      double radiusSquared = getRadiusSquared(polymer);

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
  public static ArrayList<int[]> getRandomPolymer(int[] foundationCoordinates, int length, boolean semiPhantom) {
    ArrayList<int[]> resultPolymer = Polymer.getFoundation(foundationCoordinates);

    final Random generator = new Random();

    int lastDirection = 0;

    for (int i = 1; i <= length; i++) {
      int dirsCount = 6;
      if (semiPhantom && i != 1) dirsCount = 5;
      int directionNumber = generator.nextInt(dirsCount);

      if (directionNumber >= (5 - lastDirection) && semiPhantom && i != 1) {
        directionNumber++;
      }

      resultPolymer = Polymer.buildPointByDirectionNumber(resultPolymer, resultPolymer.size() - 1, directionNumber);

      lastDirection = directionNumber;
    }


    return resultPolymer;
  }

  /**
   * Возвращает набор случайных полимеров
   *
   * @param foundationCoordinates
   * @param length
   * @param count
   * @returnНекоторые вопросы статистической теории биополимеров
   */
  public static ArrayList<ArrayList<int[]>> getRandomPolymersSet(int[] foundationCoordinates, int length, int count, boolean semiPhantom) {
    ArrayList<ArrayList<int[]>> resultPolymers = new ArrayList<ArrayList<int[]>>();

    for (int i = 0; i < count; i++) {
      ArrayList<int[]> tPolymer = Polymer.getRandomPolymer(foundationCoordinates, length, semiPhantom);
      resultPolymers.add(tPolymer);
    }

    return resultPolymers;
  }

  public static ArrayList<int[]> getPolymerWithMaxEnergy(ArrayList<ArrayList<int[]>> polymers) {
    int c = 0;
    int maxEnergyPolymerNumber = 0;
    int maxEnergy = Polymer.getEnergyInOnePolymer(polymers.get(0));

    ArrayList<int[]> polymer;
    for (int i = 1; i < polymers.size(); i++) {
      polymer = polymers.get(i);

      if (Polymer.isNotIntersected(polymer)) {
        int currentEnergy = Polymer.getEnergyInOnePolymer(polymer);

        if (currentEnergy > maxEnergy) {
          maxEnergy = currentEnergy;
          maxEnergyPolymerNumber = i;
        }
      }
    }

    return polymers.get(maxEnergyPolymerNumber);
  }

  public static ArrayList<int[]> getPolymerWithMinEnergy(ArrayList<ArrayList<int[]>> polymers) {
    int c = 0;
    int minEnergyPolymerNumber = 0;
    int minEnergy = Polymer.getEnergyInOnePolymer(polymers.get(0));

    ArrayList<int[]> polymer;
    for (int i = 1; i < polymers.size(); i++) {
      polymer = polymers.get(i);

      if (Polymer.isNotIntersected(polymer)) {
        int currentEnergy = Polymer.getEnergyInOnePolymer(polymer);

        if (currentEnergy < minEnergy) {
          minEnergy = currentEnergy;
          minEnergyPolymerNumber = i;
        }
      }
    }

    return polymers.get(minEnergyPolymerNumber);
  }

  public static void exportToVrml(ArrayList<int[]> polymer) throws Exception {
    final String _outputTextFile = "test.txt";
    final String _outputVRMLFile = "test.wrl";

    // Create a GraphData object to store our graph in.
    GraphData graph = new GraphData();

    // Create some nodes for our graph.
    Node nodeA = new Node("A", 0.0, 0.0, 0.0);
    Node nodeB = new Node("B", 1.0, 1.0, 1.0);
    Node nodeC = new Node("C", 1.0, 0.0, 1.0);

    Node[] nodes = new Node[polymer.size()];

    for (int i = 0; i < polymer.size(); i++) {


      int[] currentPoint = polymer.get(i);
      int x = currentPoint[0];
      int y = currentPoint[1];
      int z = currentPoint[2];

      nodes[i] = new Node("A", x, y, z);

    }

    // Now create some edges for our graph.
    // Edges merely keep a track of which nodes are connected.
    Edge edge1 = new Edge(nodeA, nodeB);
    Edge edge2 = new Edge(nodeB, nodeC);

    Edge[] edges = new Edge[polymer.size() - 1];
    for (int i = 0; i < polymer.size() - 1; i++) {
      edges[i] = new Edge(nodes[i], nodes[i + 1]);
    }

    // Now add these nodes and edges to the graph!
//    graph.addNode(nodeA);
//    graph.addNode(nodeB);
//    graph.addNode(nodeC);
//
//    graph.addEdge(edge1);
//    graph.addEdge(edge2);

    for (int i = 0; i < polymer.size() - 1; i++) {
      graph.addNode(nodes[i]);
      graph.addEdge(edges[i]);
    }
    graph.addNode(nodes[polymer.size() - 1]);

    // Perform 100 calculations to move the graph into a nice layout.
    NodeMover nodeMover = new NodeMover(graph);
    nodeMover.move(2);

    // Center the graph about (0,0,0).
    ViewCenterer viewCenterer = new ViewCenterer(graph);
    viewCenterer.center();

    System.out.println("Outputting Text to " + _outputTextFile);

    // Create a text file describing the newly located nodes.
    TextOutput textOutput = new TextOutput(graph);
    textOutput.writeToFile(_outputTextFile);

    System.out.println("Outputting VRML scene to " + _outputVRMLFile);

    // Create a 3-D VRML scene of the new graph.
    VRMLOutput vrmlOutput = new VRMLOutput(graph);
    vrmlOutput.setBackgroundColour("0 0 0");
    vrmlOutput.setEdgeColour("1 0.5 0.5");
    vrmlOutput.setNodeColour("0.5 0.5 1");
    vrmlOutput.writeToFile(_outputVRMLFile);

  }

}
