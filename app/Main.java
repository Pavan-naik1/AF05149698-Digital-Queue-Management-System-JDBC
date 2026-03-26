package app;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        QueueSystem qs = new QueueSystem();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1.Add Patient\n2.View Queue\n3.Serve Next Patient\n4.View Last Served Patient\n5.Exit");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1: qs.addPatient(); break;
                case 2: qs.viewQueue(); break;
                case 3: qs.serveNext(); break;
                case 4: qs.lastServed(); break;
                case 5:
                    sc.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}