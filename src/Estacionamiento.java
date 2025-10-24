import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Estacionamiento {
    private final int capacidadMax = 5;
    private final Semaphore semaphore = new Semaphore(5);
    private final ArrayList<Coche> cochesAparcados = new ArrayList<>();
    int cochesAparcadosCont = 0;
    int cochesNoAparcadosCont = 0;

    synchronized boolean entrar (Coche coche) {
        try{
            if (semaphore.tryAcquire(5, TimeUnit.SECONDS)) {
                synchronized (cochesAparcados) {
                    cochesAparcados.add(coche);
                    if (coche.esVip()) {
                        System.out.println(coche + " Ha entrado un coche vip");
                    } else {
                        System.out.println(coche + " Ha entrado un coche");
                    }
                    cochesAparcadosCont++;
                    return true;
                }
            } else {
                if (coche.esVip()) {
                    desalojarCocheNormal(coche);
                    if (semaphore.tryAcquire(1, TimeUnit.SECONDS)){
                        synchronized (cochesAparcados) {
                            cochesAparcados.add(coche);
                            System.out.println(coche+ " Ha entrado un coche vip");
                            cochesAparcadosCont++;
                        }
                    }
                    return true;
                }
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    void salir(Coche coche) {
        synchronized (cochesAparcados) {
            if(cochesAparcados.remove(coche))
                semaphore.release();
        }
    }

    void desalojarCocheNormal(Coche cocheVip) {
        synchronized (cochesAparcados) {
            for (int i = 0; i < cochesAparcados.size(); i++) {
                Coche c = cochesAparcados.get(i);
                if (!c.esVip()) {
                    salir(c);
                    System.out.println("Se ha echado a " + c + " para que entre " + cocheVip);
                    return;
                }
            }
            System.out.println(cocheVip + " no pudo desalojar a nadie (todos son VIPs)");
        }
    }

    void contadores() {
        System.out.println("Han aparcado: " + cochesAparcadosCont);
        System.out.println("NO han aparcado: " + cochesNoAparcadosCont);
    }
}