public class Coche {
    private String nombre;
    private boolean vip;
    private Estacionamiento estacionamiento;

    public boolean esVip() {
        return true;
    }
    void run() {

    }

    @Override
    public String toString() {
        return " [" + vip + "] " + " " + nombre;
    }
}