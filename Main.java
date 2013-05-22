import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Main {


  public static void main(String[] args) {
    int[] basicCoordinates = new int[]{0, 0, 0};

    ArrayList<ArrayList<int[]>> polymers = Polymer.getRandomPolymersSet(basicCoordinates, 8, 10000, true);


    for (int i = 0; i < polymers.size(); i++) {
      //System.out.println("\nPolymer №: " + i);

      for (int[] point : polymers.get(i)) {
        //System.out.println(Arrays.toString(point) + " ");

      }

    }

    //System.out.println("Polymers getEnergyInManyPolymers: " + Polymer.getEnergyInManyPolymers(polymers));
    //System.out.println("Polymers Related average count: " + Polymer.getEnergyAverageInManyPolymers(polymers));
    System.out.println("Polymers getDistributionByEnergyInManyPolymers: " + Polymer.getDistributionByEnergyInManyPolymers(polymers));
    System.out.println("Polymers NotIntersected count: " + Polymer.getNotIntersectedCountInManyPolymers(polymers));
    System.out.println("Polymers average radius: " + Polymer.getAverageRadius(polymers));
    System.out.println("Polymers count: " + polymers.size());

    /*for (int i = 1; i <= 100; i += 10) {
      ArrayList<ArrayList<int[]>> polymers2 = Polymer.getRandomPolymersSet(basicCoordinates, 8, i * 1000, false);

      *//*float aRadius = Polymer.getAverageRadius(polymers2);*//*
      double wsaw, w0, relation, relation2, a, gamma, z, z2, n;

      wsaw = Polymer.getNotIntersectedCountInManyPolymers(polymers2);
      w0 = i * 1000;
      gamma = (7.0 / 6.0);
      z2 = 4.68;
      a = 1.17;
      n = 8;
      z = 6;
      relation = wsaw / w0;
      relation2 = a * Math.pow(z2 / z, n) * Math.pow(n, gamma - 1);

      System.out.println(w0 + "\t" + relation + "\t" + relation2);
    }*/

    ArrayList<int[]> maxEnergyPolymer = Polymer.getPolymerWithMaxEnergy(polymers);
    ArrayList<int[]> minEnergyPolymer = Polymer.getPolymerWithMinEnergy(polymers);
    System.out.println(Polymer.getEnergyInOnePolymer(maxEnergyPolymer));
    System.out.println(Polymer.getEnergyInOnePolymer(minEnergyPolymer));
    try {
      Polymer.exportToVrml(maxEnergyPolymer);
    } catch (Exception e) {
      e.printStackTrace();
    }


    //Map<Integer, Integer> distribution = Polymer.getDistributionByEnergyInManyPolymers(polymers);



    /*try {
      FileWriter fileWriter = new FileWriter("/home/skiv/СПбГУ/Курсовая/2/test/testFile");

      PrintWriter out = new PrintWriter(fileWriter);

      for (Map.Entry entry : distribution.entrySet()) {

        out.println("Polymers average radius: " + Polymer.getAverageRadius(polymers));
        //out.println(entry.getKey() + "\t" + entry.getValue());
      }

//      out.print("Hello ");
//      out.println("world");
      out.close();
    } catch (IOException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }*/




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
