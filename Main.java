import java.util.ArrayList;
import java.util.Arrays;

public class Main {

  public static void main(String[] args) {
    int[] basicCoordinates = new int[]{0, 0, 0};


    ArrayList<ArrayList<int[]>> polymers = Polymer.getRandomPolymersSet(basicCoordinates, 8, 4000, false);

    for (int i = 0; i < polymers.size(); i++) {
      //System.out.println("\nPolymer №: " + i);

      for (int[] point : polymers.get(i)) {
        //System.out.println(Arrays.toString(point) + " ");

      }

    }

    System.out.println("Polymers getEnergyInManyPolymers: " + Polymer.getEnergyInManyPolymers(polymers));
    System.out.println("Polymers Related average count: " + Polymer.getEnergyAverageInManyPolymers(polymers));
    System.out.println("Polymers getDistributionOfDistancesByEnergyInManyPolymers: " + Polymer.getDistributionOfDistancesByEnergyInManyPolymers(polymers));
    System.out.println("Polymers NotIntersected count: " + Polymer.getNotIntersectedCountInManyPolymers(polymers));
    System.out.println("Polymers average radius: " + Polymer.getAverageRadius(polymers));
    System.out.println("Polymers count: " + polymers.size());

    /*for (int i = 0; i < 15; i++) {
      ArrayList<ArrayList<int[]>> polymers2 = Polymer.getRandomPolymersSet(basicCoordinates, i, 10000);

      System.out.println(i + "\t" + Polymer.getRelatedAverageInManyPolymers(polymers2));
    }*/

    /*ArrayList<int[]> tPolymer = Polymer.getRandomPolymer(basicCoordinates, 4);

    for (int[] point : tPolymer) {
      System.out.println(Arrays.toString(point) + " ");

    }*/

    //ArrayList<ArrayList<int[]>> polymers = Polymer.buildWholeSet(basicCoordinates, 5);

    /*System.out.println("Polymers ints count: " + Polymer.getNotIntersectedCountInManyPolymers(polymers));
    System.out.println("Polymers average radius: " + Polymer.getAverageRadius(polymers));
    System.out.println("Polymers count: " + polymers.size());*/



    /*for (int i = 0; i < polymers.size(); i++) {
      System.out.println("\nPolymer №: " + i);

      for (int[] point : polymers.get(i)) {
        System.out.println(Arrays.toString(point) + " ");

      }

    }*/


      /*for (int[] point : polymers.get(34)) {
        System.out.println(Arrays.toString(point) + " ");

      }*/


    //ArrayList<int[]> polymer = Polymer.getFoundation(basicCoordinates);

    /*for(Iterator<int[]> i = polymer.iterator(); i.hasNext(); ) {
      int[] item = i.next();
      int[] test = new int[]{1,3,4};
      System.out.println(Arrays.toString(test));
    }*/


    /* polymer = Polymer.buildPointByDirectionNumber(polymer, 0, 0);
    for (int[] point : polymer) {
      System.out.println(Arrays.toString(point) + "\n");

    }*/
  }
}
