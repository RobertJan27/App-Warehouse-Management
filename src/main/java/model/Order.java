package model;

public class Order {
    private int id;
    private int id_client;
    private int id_produs;
    private String data;
    private int cantitate;

    public Order(int id, int id_client, int id_produs, String data, int cantitate) {
        this.id = id;
        this.id_client = id_client;
        this.id_produs = id_produs;
        this.data = data;
        this.cantitate = cantitate;
    }
    public Order(){}


    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", id_client=" + id_client +
                ", id_produs=" + id_produs +
                ", data='" + data + '\'' +
                ", cantitate=" + cantitate +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public int getId_produs() {
        return id_produs;
    }

    public void setId_produs(int id_produs) {
        this.id_produs = id_produs;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }
}
