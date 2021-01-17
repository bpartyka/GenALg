public class City {

    int demendForGoods;
    String name;
    int id;

    public City(int demendForGoods, String name, int id) {
        this.demendForGoods = demendForGoods;
        this.name = name;
        this.id = id;
    }

    public int getDemendForGoods() {
        return demendForGoods;
    }

    public void setDemendForGoods(int demendForGoods) {
        this.demendForGoods = demendForGoods;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "City{" +
                "demendForGoods=" + demendForGoods +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
