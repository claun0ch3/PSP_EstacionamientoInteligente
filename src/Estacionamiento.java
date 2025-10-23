import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Estacionamiento {
    private final int capacidadMaxima = 5;
    private final Semaphore semaforo = new Semaphore(5);
    private final ArrayList<Coche> cochesAparcados = new ArrayList<>();

    synchronized boolean entrar (Coche coche) {
        if (cochesAparcados.size() < capacidadMaxima) {
            cochesAparcados.add(coche);
            return true;
        } else {
            try {
                if (semaforo.tryAcquire(5, TimeUnit.SECONDS)) {
                    if (coche.esVip()) {
                        desalojarCocheNormal(coche);
                        cochesAparcados.add(coche);
                        return true;
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return false;
    }

    
    public synchronized void salir (Coche coche) {
        cochesAparcados.remove(coche);
        semaforo.release();
    }
    void desalojarCocheNormal (Coche cocheVip) {
        if (cochesAparcados.size() < capacidadMaxima) {
            for (int i = 0; i < cochesAparcados.size(); i++) {
                if (!cochesAparcados.get(i).esVip()) {
                    salir(cochesAparcados.get(i));
                }
            }
        }
    }
}