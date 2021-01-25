import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Solutions {


    public static int selectIdOfCity(int numberOfCities) {

        Random generator = new Random();

        return generator.nextInt(numberOfCities);
    }


    public static int[][] matrixOfDistances(int x, int y) throws FileNotFoundException {
        Scanner input = new Scanner(new File("array.txt"));
        int m = x;
        int n = y;
        int[][] a = new int[m][n];
        while (input.hasNextLine()) {
            for (int j = 0; j < m; j++) {
                for (int i = 0; i < n; i++) {
                    try {//    System.out.println("number is ");
                        a[i][j] = input.nextInt();
//                        System.out.print(" " + a[i][j]);
                    } catch (java.util.NoSuchElementException e) {
                        // e.printStackTrace();
                    }
                }

            }
        }

        return a;
    }

    public static HashMap<Integer, City> hashMapOfCity(int numberOfCIties) throws FileNotFoundException {
        Scanner input = new Scanner(new File("cities.txt"));
        int n = numberOfCIties;
        int m = 2;
        HashMap<Integer, City> hashMap = new HashMap<>();
        while (input.hasNextLine()) {
            for (int j = 0; j < n; j++) {
                String cityName = "";
                for (int i = 0; i < m; i++) {
                    try {//    System.out.println("number is ");


                        if (i == 1) {
                            int value = Integer.parseInt(input.next());
                            hashMap.put(j, new City(value, cityName, j));

                        } else {
                            cityName = String.valueOf(input.next());
                        }

                    } catch (java.util.NoSuchElementException e) {
                        // e.printStackTrace();
                    }
                }

            }
        }
        return hashMap;

    }


    public static ArrayList<Integer> initPopulation(HashMap<Integer, City> hashmapOfCities, int[][] matrixOfDistances, int numberOfCities, int carCapacity) {
//        wylosuj numer ktorego nie ma w drodze z hashmapy
//        dodaj do tavlicy, dopoki <carCapacity
//        wroc do KRK
//        powtarzaj dopoki wszystkie miasta nie beda zajete
//
//        wartosci na cala trase:
        ArrayList<Integer> citiesIdOfRoad = new ArrayList<>();
        int ALLSUMOFDISTANCES = 0;
        citiesIdOfRoad.add(0);

        Map<Integer, City> availableCities = (Map<Integer, City>) hashmapOfCities.clone();

        while (hashmapOfCities.size() > 1) {

            //        wartosci od krakowa do krakowa:
            int CurrSumOfDemands = 0;
            int randNumber;
            while (hashmapOfCities.size() > 1) {
                randNumber = selectIdOfCity(numberOfCities);

//                System.out.println("Wylosowano: " +randNumber);
                if (hashmapOfCities.containsKey(randNumber)) {
                    City currentCity = hashmapOfCities.get(randNumber);

                    if (randNumber != 0 && CurrSumOfDemands <= carCapacity && CurrSumOfDemands + currentCity.demendForGoods <= carCapacity && !availableCities.isEmpty()) {
//                usuwamy z hashmapy to miasto
//                dodajemy id do listy
//                dodajemy posiadany zasob do CurrSumOfDemands

                        hashmapOfCities.remove(randNumber);
//                      tablica pomocnicza
                        availableCities.remove(randNumber);

                        citiesIdOfRoad.add(randNumber);
                        CurrSumOfDemands += currentCity.demendForGoods;

//                        System.out.println("Obecna kwota: " +CurrSumOfDemands);

//              dodajemy do ALLSUMOFDISTANCES dystans z miasta do miasta
                        int lastIndexCity = citiesIdOfRoad.get(citiesIdOfRoad.size() - 2);
                        int curentRandomCityIndex = randNumber;
                        int distance = matrixOfDistances[lastIndexCity][curentRandomCityIndex];
                        ALLSUMOFDISTANCES += distance;
//                        System.out.println("Obecny pelen dystans: " +ALLSUMOFDISTANCES);
                    } else {
                        availableCities.remove(randNumber);
                    }
                    if (availableCities.isEmpty()) {
//                        gdy tablica pomocnicza pusta wracamy do krakowa
//                        System.out.println("Wracamy do krk");
                        citiesIdOfRoad.add(0);
                        int lastIndexCity = citiesIdOfRoad.get(citiesIdOfRoad.size() - 2);
                        int curentRandomCityIndex = 0;
                        int distance = matrixOfDistances[lastIndexCity][curentRandomCityIndex];
                        ALLSUMOFDISTANCES += distance;
//                        System.out.println("Obecny pelen dystans w krk: " +ALLSUMOFDISTANCES);


                        CurrSumOfDemands = 0;
                        availableCities = (Map<Integer, City>) hashmapOfCities.clone();
                    }
                }
            }

        }
        if (!citiesIdOfRoad.get(citiesIdOfRoad.size() - 1).equals(0)) {
            int id = citiesIdOfRoad.get(citiesIdOfRoad.size() - 1);
            int distance = matrixOfDistances[id][0];
            ALLSUMOFDISTANCES += distance;
            citiesIdOfRoad.add(0);
        }
        System.out.println("After initPopulation " + Arrays.toString(citiesIdOfRoad.toArray()));

        return citiesIdOfRoad;
    }


    public static ArrayList<Integer> mutation(ArrayList<Integer> listOfElements, HashMap<Integer, City> hashmapOfCities, int[][] matrixOfDistances, int numberOfCities) {

        int size = listOfElements.size();

        int numberOfZeroCity = (int) listOfElements.stream().filter(x -> x.equals(0)).count();
        HashMap<Integer, City> hashmapOfCitiesClone = (HashMap<Integer, City>) hashmapOfCities.clone();

        ArrayList<ArrayList<Integer>> listOfList = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> list = new ArrayList<>();
        list.add(0);
        if (numberOfZeroCity <= 2) {
            return listOfElements;
        }
        for (int i = 0; i < size; i++) {

            if (listOfElements.get(i) == 0 && i != 0) {
                listOfList.add(list);

                list = new ArrayList<>();
                list.add(0);

            }
            if (i != 0 && listOfElements.get(i) != 0) {
                list.add(listOfElements.get(i));
            }


        }

        for (int i = 0; i < listOfList.size(); i++) {
            if (i != listOfList.size() - 1) {


                for (long stop = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(5); stop > System.nanoTime(); ) {
                    Random generator = new Random();

                    int value = generator.nextInt(numberOfCities);
                    Random generator2 = new Random();

                    int value2 = generator2.nextInt(numberOfCities);

                    if (value != 0 && value2 != 0 && hashmapOfCities.get(value).getDemendForGoods() == hashmapOfCities.get(value2).getDemendForGoods() && listOfList.get(i).contains(value) && listOfList.get(i + 1).contains(value2)) {
                        int index1 = listOfList.get(i).indexOf(value);
                        int index2 = listOfList.get(i + 1).indexOf(value2);
//                        System.out.println("index1 "+ index1+ " index2 "+ index2 + " va1 "+ value+ " va2 "+value2);
                        listOfList.get(i).remove(index1);
                        listOfList.get(i).add(index1, value2);
                        listOfList.get(i + 1).remove(index2);
                        listOfList.get(i + 1).add(index2, value);
                    }
                }


            }

        }

        ArrayList<Integer> result = new ArrayList<>();

        for (int i = 0; i < listOfList.size(); i++) {

            for (int j = 0; j < listOfList.get(i).size(); j++) {
                result.add(listOfList.get(i).get(j));
            }
        }
        result.add(0);
        System.out.println("After mutation " + result);
        return result;

    }

    static public int sumDistance(ArrayList<Integer> listOfElements, int[][] matrixOfDistances) {
        int ALLSUMOFDISTANCES = 0;
        for (int i = 1; i < listOfElements.size() ; i++) {
            int index1 = listOfElements.get(i - 1);
            int index2 = listOfElements.get(i);

            int distance = matrixOfDistances[index1][index2];
            ALLSUMOFDISTANCES += distance;
        }
        return ALLSUMOFDISTANCES;
    }


    public static List<List<ArrayList<Integer>>> ruletteRule(ArrayList<Chromosomes> listOfChromosomes) {

        List<List<ArrayList<Integer>>> result = new ArrayList<>();
        double sumOfAll = 0;
        int realSum=0;
        HashMap<Integer, Range> hashMap = new HashMap<Integer, Range>();
        for (int j = 0; j < listOfChromosomes.size(); j++) {
            realSum+=(listOfChromosomes.get(j).getSumOfDistances());
            Double value = 1.0 / (listOfChromosomes.get(j).getSumOfDistances());
            sumOfAll += value;
            if (j == 0) {
                hashMap.put(j, new Range(0.0, 1.0 / listOfChromosomes.get(j).getSumOfDistances()));
            } else {
                hashMap.put(j, new Range(1.0 / listOfChromosomes.get(j - 1).getSumOfDistances(), 1.0 / listOfChromosomes.get(j).getSumOfDistances()));
            }
        }
//        System.out.println("Sum all:" + realSum);
        System.out.println("List chromo:" + listOfChromosomes);
        System.out.println("List chromo size:" + listOfChromosomes.size());
        while (result.size() < listOfChromosomes.size()) {
            Random r = new Random();
            double randomValue = 0.0 + (sumOfAll - 0.0) * r.nextDouble();
            for (int j = 0; j < hashMap.size(); j++) {
                Double min = hashMap.get(j).getMin();
                Double max = hashMap.get(j).getMax();
                if (randomValue > min && randomValue <= max ) {
                    result.add(listOfChromosomes.get(j).listOfRoad);
                }
                if (result.size() >= listOfChromosomes.size()) {
                    System.out.println("Ruletka1 " + result);
                    return result;
                }
            }
        }


        System.out.println("Ruletka " + result);

        return result;

    }


}
