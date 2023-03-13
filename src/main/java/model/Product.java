package model;

public class Product {
    private int id;
    private String nume_produs;
    private int pret;
    private int cantitate;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", nume_produs='" + nume_produs + '\'' +
                ", pret=" + pret +
                ", cantitate=" + cantitate +
                '}';
    }
public Product()
{}

    public Product(int id, String nume_produs, int pret, int cantitate) {
        this.id = id;
        this.nume_produs = nume_produs;
        this.pret = pret;
        this.cantitate = cantitate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume_produs() {
        return nume_produs;
    }

    public void setNume_produs(String nume_produs) {
        this.nume_produs = nume_produs;
    }

    public int getPret() {
        return pret;
    }

    public void setPret(int pret) {
        this.pret = pret;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }
}
