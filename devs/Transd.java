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
public class Transd extends atomic {
    public Transd(String EName) {
//        SetName(name);
        super(EName);
//        super.SetName(EName);
//        SetName(EName);
    }

    String JobID;
    double clock;
    JOBS Arrive=new JOBS();
    JOBS Solve=new JOBS() ;


    public void ExtTransitionFN(double E, DevsMessage X) {
        JobID = (String) X.ContentValue();
        clock += E;
        System.out.println("");
        System.out.print(name);
        System.out.print("(EXT) --> :");
        System.out.print(X.ContentPort());
        System.out.print(":");
        System.out.print(JobID);
        System.out.print(" at ");
        System.out.println(clock);

        if (Phase.equals("active")) {
//            System.out.println(X.ContentPort());
            if (X.ContentPort().equals("arriv")) {
                Arrive.Jobs[Arrive.Num].ID = JobID;
                Arrive.Jobs[Arrive.Num].Time = clock;
                Arrive.Num++;
            } else if (X.ContentPort().equals("solved")) {
                Solve.Jobs[Solve.Num].ID = JobID;
                Solve.Jobs[Solve.Num].Time = clock;
                Solve.Num++;
            }
        }
        Continue();
    }

    public void IntTransitionFN() {
        System.out.print(name);
        System.out.print("(INT) --> ");

        if (Phase.equals("active")) {
            PrintArrive();
            PrintSolve();
            Passivate();
        } else
            Continue();
    }

    public void OutputFN() {
        System.out.print(name);
        System.out.print("(OUT) --> ");
        if (Phase.equals("active"))
            MakeContent("out", null);
        else
            MakeContent();
    }

    public void InitializeFN() {
        clock = (double) 0.0;

        Arrive.Num = 0;
        Solve.Num = 0;

        HoldIn("active", (double) 100.0);
    }


    public void PrintArrive() {
        System.out.println("");
        System.out.println(
                "   ---------------------< Arrived Jobs >---------------------");

        for (int i = 0; i < Arrive.Num; i++) {
            System.out.print("(");
            System.out.print(Arrive.Jobs[i].ID);
            System.out.print(",");
            System.out.print(Arrive.Jobs[i].Time);
            System.out.print(") ");
        }
        System.out.println("");
    }

    public void PrintSolve() {
        System.out.println(
                "   ---------------------< Solved Jobs >---------------------");
        for (int i = 0; i < Solve.Num; i++) {
            System.out.print("(");
            System.out.print(Solve.Jobs[i].ID);
            System.out.print(",");
            System.out.print(Solve.Jobs[i].Time);
            System.out.print(") ");
        }
        System.out.println("");
    }
}
