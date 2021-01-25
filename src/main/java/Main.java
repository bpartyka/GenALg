import java.util.*;

public class Main {

    public static final int numberOfCities = 9;
    public static final int x = 9;
    public static final int y = 9;
    public static final int carCapacity = 1000;
    public static final int numberOfDrivers = 2;
    private static int numberOfEpoc = 500;
    private static int numberOfPairOfCar = 20;
//    chromosom ilosc^

    public static void main(String[] args) throws Exception {

        int[][] matrixOfDistances = Solutions.matrixOfDistances(x, y);
        HashMap<Integer, City> hashMapOfCity = Solutions.hashMapOfCity(x);

//        wybor id miast ktore ma kierowca


        int numberOfCityPerDriver = (numberOfCities - 1) / numberOfDrivers;
        List<LinkedList<HashMap<Integer, City>>> listaListKierowcowZmiastami = new LinkedList<>();
        for (int zz = 0; zz < numberOfPairOfCar; zz++) {
            List<HashMap<Integer, City>> listOfDrivers = new LinkedList<>();
            HashMap<Integer, City> hashMapOfCityClone = (HashMap<Integer, City>) hashMapOfCity.clone();
            for (int i = 0; i < numberOfDrivers; i++) {
                HashMap<Integer, City> driver = new HashMap<>();
                driver.put(0, hashMapOfCity.get(0));
                int coutner = 0;

                while (coutner < numberOfCityPerDriver) {
                    int number = Solutions.selectIdOfCity(numberOfCities);
                    if (hashMapOfCityClone.containsKey(number) && number != 0) {

                        coutner++;
                        driver.put(number, hashMapOfCity.get(number));
                        hashMapOfCityClone.remove(number);
                    }
                }
                listOfDrivers.add(driver);
            }
            listaListKierowcowZmiastami.add((LinkedList<HashMap<Integer, City>>) listOfDrivers);
        }
        List<ArrayList<Integer>> listOfResult1 = new ArrayList<>();
        List<List<ArrayList<Integer>>> listOfResult = new ArrayList<>();
        for (int zz = 0; zz < numberOfEpoc; zz++) {
            System.out.println("Epoka " + zz);
            ArrayList<Chromosomes> listOfChromosomes = new ArrayList<Chromosomes>();


            for (int z = 0; z < numberOfPairOfCar; z++) {
                System.out.println();
                System.out.println("Para nr " + (z + 1));

                int sumOfDistanceForAllCar = 0;
                List<ArrayList<Integer>> lists = new LinkedList<ArrayList<Integer>>();
                for (int i = 0; i < numberOfDrivers; i++) {
                    if (zz == 0) {
                        System.out.println("");
                        System.out.println(listaListKierowcowZmiastami.get(z));
                        listOfResult1.add(Solutions.initPopulation(listaListKierowcowZmiastami.get(z).get(i), matrixOfDistances, x, carCapacity));
                        System.out.println("Sum after init    " + Solutions.sumDistance(listOfResult1.get(z), matrixOfDistances));

                    }

//                    ArrayList<Integer> mutation = Solutions.mutation(listOfResult.get(z), hashMapOfCity, matrixOfDistances, numberOfCities);
//                    System.out.println("Sum after mutation    " + Solutions.sumDistance(mutation, matrixOfDistances));
//
//                    sumOfDistanceForAllCar += Solutions.sumDistance(mutation, matrixOfDistances);
//
//
//                    lists.add(mutation);
                }
                listOfResult.add(listOfResult1);
                int realsum = 0;
                for (int i = 0; i < numberOfDrivers; i++) {
                    System.out.println("Before mutation " + listOfResult.get(z).get(i));
                    ArrayList<Integer> mutation = Solutions.mutation(listOfResult.get(z).get(i), hashMapOfCity, matrixOfDistances, numberOfCities);
                    System.out.println("Sum after mutation    " + Solutions.sumDistance(mutation, matrixOfDistances));

                    sumOfDistanceForAllCar += Solutions.sumDistance(mutation, matrixOfDistances);

                    realsum += Solutions.sumDistance(mutation, matrixOfDistances);
                    lists.add(mutation);
                }
                System.out.println("Suma pary " + realsum);
                Chromosomes chromosomes = new Chromosomes(lists, sumOfDistanceForAllCar);
                listOfChromosomes.add(chromosomes);
            }


//
//        ruletka:
            listOfResult = Solutions.ruletteRule(listOfChromosomes);


        }

        List<ArrayList<Integer>> resultList=new ArrayList<>();
        Integer minSum=999999999;
        for (int i = 0; i < numberOfPairOfCar; i++) {
//            System.out.println("Para  " + i);
            int suma = 0;
            for (int j = 0; j < numberOfDrivers; j++) {
//                System.out.println("Dystans kierowcy " + j);
//                System.out.println(listaListKierowcowZmiastami.get(z));
//                listOfResult1.add(Solutions.initPopulation(listaListKierowcowZmiastami.get(z).get(i), matrixOfDistances, x, carCapacity));
//                System.out.println("Sum kierowcy  " + j + " " + Solutions.sumDistance(listOfResult.get(i).get(j), matrixOfDistances));
                suma += Solutions.sumDistance(listOfResult.get(i).get(j), matrixOfDistances);
            }
//            System.out.println("Suma " + suma);

            if (suma<minSum) {
                resultList= new ArrayList<>();
                resultList.add(listOfResult.get(i).get(0));
                resultList.add(listOfResult.get(i).get(1));
                minSum=suma;
            }
//                    ArrayList<Integer> mutation = Solutions.mutation(listOfResult.get(z), hashMapOfCity, matrixOfDistances, numberOfCities);
//                    System.out.println("Sum after mutation    " + Solutions.sumDistance(mutation, matrixOfDistances));
//
//                    sumOfDistanceForAllCar += Solutions.sumDistance(mutation, matrixOfDistances);
//
//
//                    lists.add(mutation);
        }
        System.out.println();
        System.out.println("Wynik "+minSum);
        System.out.println("Trasa " +resultList);
    }
}
