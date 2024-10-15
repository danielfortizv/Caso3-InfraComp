import java.util.ArrayList;
import java.util.Scanner;

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

        /*
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el numero de marcos de pagina: ");
        int marcos = scanner.nextInt();


        ArrayList<Integer> referencias = new ArrayList<>();
        System.out.println("Ingrese el numero de referencias: ");
        int numReferencias = scanner.nextInt();

        for(int i = 0; i < numReferencias; i++){
            referencias.add((int) Math.random() * 10);
        }


        Simulador simulador = new Simulador(marcos, referencias);
        simulador.iniciarSimulador();
        */ 

    }
}
