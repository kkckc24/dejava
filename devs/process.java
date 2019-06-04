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
public class process extends atomic {
    public String JobID;
    public double PTime;

    public process(String str) {
        super(str);
        SetName(str);
    }

    public void ExtTransitionFN(double Time, DevsMessage MSG) {
        System.out.print(name);
        System.out.print("(EXT) --> ");
        if (MSG.ContentPort().equals("in")) {
            if (Phase.equals("busy")) {
                Continue();
            } else {
                System.out.print(MSG.ContentValue());
                JobID = ((String) (MSG.ContentValue()));
                System.out.print(MSG.ContentPort());
                System.out.print(":");
                System.out.print(JobID);
                HoldIn("busy", PTime);
            }
        } else
            Continue();
    }

    public void IntTransitionFN() {
        System.out.print(name);
        System.out.print("(INT) --> ");
        if (Phase.equals("busy")) {
            Passivate();
        } else
            Continue();
    }

    public void OutputFN() {
        System.out.print(name);
        System.out.print("(OUT) --> ");

        if (Phase.equals("busy")) {
            MakeContent("out", JobID);
        } else {
            MakeContent();
        }
    }

    public void InitializeFN() {
        PTime = (double) 7.0;
        Passivate();
    }
}
