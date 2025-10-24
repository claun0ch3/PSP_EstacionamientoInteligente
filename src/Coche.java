public class Coche extends Thread {
    private String nombre;
    private boolean vip;
    private Estacionamiento estacionamiento;

    public Coche(String nombre, boolean vip, Estacionamiento estacionamiento) {
        this.nombre = nombre;
        this.vip = vip;
        this.estacionamiento = estacionamiento;
    }

    public boolean esVip() {
        return vip;
    }

    @Override
    public void run() {
        boolean aparcado = estacionamiento.entrar(this);
        try {
            if (aparcado) {
                double numero = (Math.random()* 20 + 2);
                Thread.sleep((long) (numero * 1000));
                System.out.println(this + " ha salido");
            } else {
                System.out.println(this + " no ha logrado entrar");
                estacionamiento.cochesNoAparcadosCont++;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            estacionamiento.salir(this);
        }
    }

    @Override
    public String toString() {
        return " [" + vip + "] " + " " + nombre;
    }
}