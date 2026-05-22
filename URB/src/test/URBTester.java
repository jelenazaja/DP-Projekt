package test;

import URBComponents.URBProces;
import components.Linker;
import components.ListenerThread;

public class URBTester {
    public static void main(String[] args) throws Exception {
        //svi procesi braodcastaju svoje poruke
        String baseName = args[0];
        int myId = Integer.parseInt(args[1]);
        int numProc = Integer.parseInt(args[2]);

        Linker linker = new Linker(baseName, myId, numProc);

        //TODO: tu možeš staviti onako po argumentu ulaznom odabir koji URB algoritam koristimo
        URBProces urb = new URBProces(linker);

        //pokrenem listener dretve
        for (int i = 0; i < numProc; i++)
            if (i != myId)
                new ListenerThread(i, urb).start();

        //proces broadcasta svoju poruku
        urb.URB_Broadcast("Poruka od procesa " + myId);
        if (myId == 1) {
            System.out.println("P1 crashao!");
            System.exit(0);  // simuliraj ccrash - ovako nekako?
        }
    }
}
