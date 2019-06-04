package devs;

import dejava.*;
import dejava.data.*;
/**
 * <p>Title: DEJAVA</p>
 *
 * <p>Description: DEVS JAVA</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Hankuk Avation Univ (Islab)</p>
 *
 * @author kkckc (kkckc@korea.com)
 * @version 1.0
 */
public class genr extends atomic {
    int InterArrivalTime;
    int Count;

    public genr(String EName) {
        super(EName);
//        super.SetName("genr");
//        SetName("genr");
    }

    public void ExtTransitionFN(double E, DevsMessage X){
            System.out.print(name); System.out.print("(EXT) --> :");
            System.out.print(X.ContentPort()); System.out.print(": ");
            String GENRMessage="When: "+AddTime(GetLastEventTime(),E);
            System.out.println(GENRMessage);

            if (X.ContentPort().equals("stop")) Passivate();
     }

    public void IntTransitionFN(){
            System.out.print(name); System.out.print("(INT) --> ");
            String GENRMessage="Sigma: "+Sigma+" When: "+AddTime(GetLastEventTime(),Sigma);
            System.out.println(GENRMessage);
            if (Phase.equals("busy")) { HoldIn("busy", InterArrivalTime); }
            else { Passivate(); }
     }

    public void OutputFN(){
            String O;

            System.out.print(name); System.out.print("(OUT) --> ");
            String GENRMessage="Phase : "+Phase+" Sigma: "+Sigma+" When: "+GetNextEventTime();
            System.out.println(GENRMessage);

            if (Phase.equals("busy")){
                     O = "Job-"+Count++;
                     MakeContent("out",O);
            }
            else MakeContent();
    }

    public void InitializeFN(){
            InterArrivalTime = 3;
            Count = 0;

            HoldIn("busy",0.0);
    }

}
