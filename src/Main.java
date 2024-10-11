import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> referencias = new ArrayList<>();
        referencias.add(1);
        referencias.add(2);
        referencias.add(3);
        referencias.add(4);
        referencias.add(5);
        referencias.add(6);
        referencias.add(7);

        Simulador simulador = new Simulador(3, referencias);
        simulador.iniciarSimulador();
    }
}
