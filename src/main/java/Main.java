import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static final int numberOfCities = 9;
    public static final int x = 9;
    public static final int y = 9;
    public static final int carCapacity = 1000;
    public static final int numberOfDrivers = 2;

    public static void main(String[] args) throws Exception {

        int[][] matrixOfDistances = Solutions.matrixOfDistances(x, y);
        HashMap<Integer, City> hashMapOfCity = Solutions.hashMapOfCity(x);

//       for (int i =0 ; i< 4; i++) {
//           System.out.println();
//           System.out.println(hashMapOfCity.get(i));
//       }


//        wybor id miast ktore ma kierowca

        List<HashMap<Integer, City>> listOfDrivers = new LinkedList<>();

        int numberOfCityPerDriver = (numberOfCities-1) / numberOfDrivers;
        HashMap<Integer, City> hashMapOfCityClone = (HashMap<Integer, City>) hashMapOfCity.clone();
        for (int i = 0; i < numberOfDrivers; i++) {
            HashMap<Integer, City> driver = new HashMap<>();
            driver.put(0,hashMapOfCity.get(0));
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



        for (int i = 0; i < numberOfDrivers; i++) {
            System.out.println(listOfDrivers.get(i));
            Solutions.initPopulation(listOfDrivers.get(i), matrixOfDistances, x, carCapacity);
        }

    }
}
