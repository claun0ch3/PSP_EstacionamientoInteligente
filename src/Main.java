import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            Estacionamiento estacionamiento = new Estacionamiento();
            ArrayList<Coche> coches = new ArrayList<Coche>();
            for (int i = 1; i <= 10; i++) {
                Coche coche = new Coche("Coche " + i, false, estacionamiento);
                coches.add(coche);
            }

            for (int i = 11; i <= 15; i++) {
                Coche coche = new Coche("Coche " + i, true, estacionamiento);
                coches.add(coche);
            }

            for (Coche coche : coches) {
                coche.start();
            }
            for (Coche coche : coches) {
                coche.join();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}