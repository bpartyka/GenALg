import java.util.ArrayList;
import java.util.List;

public class Chromosomes {
    List <ArrayList<Integer>> listOfRoad;
    int sumOfDistances;

    public Chromosomes(List<ArrayList<Integer>> listOfRoad, int sumOfDistances) {
        this.listOfRoad = listOfRoad;
        this.sumOfDistances = sumOfDistances;
    }

    public List<ArrayList<Integer>> getListOfRoad() {
        return listOfRoad;
    }

    public void setListOfRoad(List<ArrayList<Integer>> listOfRoad) {
        this.listOfRoad = listOfRoad;
    }

    public int getSumOfDistances() {
        return sumOfDistances;
    }

    public void setSumOfDistances(int sumOfDistances) {
        this.sumOfDistances = sumOfDistances;
    }

    @Override
    public String toString() {
        return "Chromosomes{" +
                "listOfRoad=" + listOfRoad +
                ", sumOfDistances=" + sumOfDistances +
                '}';
    }
}
