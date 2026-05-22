package URBComponents;

import components.Linker;
import components.Msg;

import java.util.HashSet;
import java.util.Set;

public class URBProces extends components.Process {
    //ovo je onaj middleware na onoj slici, urbtester je application layer
    //linker je network layer

    private Set<String> vecDobivenePoruke = new HashSet<>();

    public URBProces(Linker linker) {
        super(linker);
    }

    public void URB_Broadcast(String m) {
        String fullMsg = myId + ":" + m;
        if (myId == 1) {
            //saljem samo p0 i onda crasham
            comm.sendMsg(0, "MSG", fullMsg);
            System.out.println("P1 crashhao nakond djelomicnog broadcasta!");
            System.exit(0);
        }
        Msg poruka = new Msg(myId, myId, "MSG", fullMsg);
        handleMsg(poruka, myId, "MSG");
    }

    public void URB_Deliver(String m) {
        System.out.println("Proces " + myId + " deliver-ao: " + m);
    }

    public synchronized void handleMsg (Msg m, int src, String tag) {
        String content = m.getMessage().replace("#", "").trim();
        String msgId = content;
        if (vecDobivenePoruke.add(msgId)) {
            for (int j = 0; j < N; j++) {
                if (j != myId && j != src) {
                    comm.sendMsg(j, "MSG", content);
                }
            }
            URB_Deliver(content);
        }
    }
}
