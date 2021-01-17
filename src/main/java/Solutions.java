import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Solutions {


    public static int selectIdOfCity(int numberOfCities) {

        Random generator = new Random();

        return generator.nextInt(numberOfCities) ;
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

        Map<Integer,City> availableCities= (Map<Integer, City>) hashmapOfCities.clone();

        while (hashmapOfCities.size()>1) {

            //        wartosci od krakowa do krakowa:
            int CurrSumOfDemands = 0;
            int randNumber;
            while (hashmapOfCities.size()>1) {
                randNumber = selectIdOfCity(numberOfCities);

//                System.out.println("Wylosowano: " +randNumber);
                if (hashmapOfCities.containsKey(randNumber)) {
                    City currentCity = hashmapOfCities.get(randNumber);

                    if (randNumber!=0 &&CurrSumOfDemands <= carCapacity && CurrSumOfDemands + currentCity.demendForGoods <= carCapacity && !availableCities.isEmpty()) {
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
                        int lastIndexCity = citiesIdOfRoad.get(citiesIdOfRoad.size()-2);
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
                        int lastIndexCity = citiesIdOfRoad.get(citiesIdOfRoad.size()-2);
                        int curentRandomCityIndex = 0;
                        int distance = matrixOfDistances[lastIndexCity][curentRandomCityIndex];
                        ALLSUMOFDISTANCES += distance;
//                        System.out.println("Obecny pelen dystans w krk: " +ALLSUMOFDISTANCES);


                        CurrSumOfDemands = 0;
                        availableCities= (Map<Integer, City>) hashmapOfCities.clone();
                    }
                }
            }

        }
        System.out.println("Dystans: "+ALLSUMOFDISTANCES);
        System.out.println(Arrays.toString(citiesIdOfRoad.toArray()));

        return citiesIdOfRoad;
    }



}
