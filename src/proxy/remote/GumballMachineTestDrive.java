package proxy.remote;

import java.rmi.Naming;

public class GumballMachineTestDrive {

    public static void main(String[] args) {
        GumballMachine gumballMachine = null;
        int count = 0;
        if(args.length < 2) {
            System.out.println("GumballMachine <name> <inventory>");
            System.exit(1);
        }

        try{
            count = Integer.parseInt(args[1]);
            gumballMachine = new GumballMachine(args[0],count);
            Naming.bind("//" + args[0] + "/gumballmachine", gumballMachine);//用gumballmachine的名字发布GumballMachine的sub
            GumballMonitor monitor = new GumballMonitor(gumballMachine);
            monitor.report();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
