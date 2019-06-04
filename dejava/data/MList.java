package dejava.data;

import dejava.*;
import java.util.ArrayList;
import java.util.Iterator;

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
 */public class MList implements DEVSDEF {
    ListElement Head, Tail, Curr;
    int NumOfElement;
    public MList() {
        Head = Tail = null;
        NumOfElement = 0;
    }

    public int GetLength() {
        return NumOfElement;
    }

    public boolean AddOne(model M) {
        return AddOne(M, INF);
    }

    public boolean AddOne(model M, double T) {
        ListElement p;

        if (IsIn(M))
            return false;
        if (NumOfElement >= 32767)
            return false;

        p = new ListElement();
        p.Before = p.Next = null;
        p.WaitTime = T;
        p.Model = M;

        if (Head == null) {
            Head = Tail = p;
        } else {
            p.Before = Tail;
            Tail.Next = p;
            Tail = p;
        }

        NumOfElement++;
        return true;
    }

    public int DelOne(model M) {
        return DelOne(M.GetName());
    }

    public int DelOne(String M) {
        ListElement p;

        if (!IsIn(M))
            return 0;
        if (NumOfElement <= 0)
            return 0;

        p = FindFirstList(M);

        if (p == Head) {
            if (Head == Tail) {

                Head = Tail = null;
            } else {
                Head = p.Next;
                Head.Before = null;

            }
        } else if (p == Tail) {
            if (Head == Tail) {

                Head = Tail = null;
            } else {
                Tail = p.Before;
                Tail.Next = null;

            }
        } else {
            p.Before.Next = p.Next;
            p.Next.Before = p.Before;

        }

        NumOfElement--;
        return 1;
    }

    public void DelAll() {
        ListElement p, q;

        p = q = Head;

        if (p == null)
            return;

        do {
            if (p == Tail)
                break;
            p = p.Next;

            q = p;
        } while (true);

        NumOfElement = 0;
    }

    public boolean IsIn(model M) {
        return IsIn(M.GetName());
    }

    public boolean IsIn(String M) {
        if (FindFirstList(M)!=null)
            return true;
        return false;
    }

    public ListElement FindFirstList(model M) {
        return FindFirstList(M.GetName());
    }

    public ListElement FindFirstList(String MName) {
        ListElement p;

        Curr = p = Head;

        if (p == null)
            return p;

        do {
            if (p == Tail)
                break;
            if (p.Model.GetName().equals(MName)) {
//            if (p.Model.GetName() == MName) {
                Curr = p.Next;
                return p;
            }
            p = p.Next;
        } while (true);

        if (p == Tail)
            Curr = null;
        if (p.Model.GetName().equals(MName))
//        if (p.Model.GetName() == MName)
            return p;
        return null;
    }

    public ListElement FindNextList(model M) {
        return FindNextList(M.GetName());
    }

    public ListElement FindNextList(String MName) {
        ListElement p;

        p = Curr;

        if (p == null)
            return p;

        do {
            if (p == Tail)
                break;
            if (p.Model.GetName().equals(MName)) {
//            if (p.Model.GetName() == MName) {
                Curr = p.Next;
                return p;
            }
            p = p.Next;
        } while (true);

        if (p == Tail)
            Curr = null;
        if (p.Model.GetName().equals(MName))
//        if (p.Model.GetName() == MName)
            return p;
        return null;
    }

    public ListElement FindFirstList(double Time) {
        Curr = Head;
        return FindNextList(Time);
    }

    public ListElement FindNextList(double Time) {
        ListElement p;

        p = Curr;

        if (p == null)
            return p;

        do {
            if (p == Tail)
                break;
            if (p.WaitTime == Time) {
                Curr = p.Next;
                return p;
            }
            p = p.Next;
        } while (true);

        if (p == Tail)
            Curr = null;
        if (p.WaitTime == Time)
            return p;
        return null;

    }


    public void SetOne(model M, double T) {
        SetOne(M.GetName(), T);
    }

    public void SetOne(String M, double T) {
        ListElement p;
        if (!IsIn(M))
            return;
        p = FindFirstList(M);
        p.WaitTime = T;
    }

    public double GetOne(model M) {
        return GetOne(M.GetName());
    }

    public double GetOne(String M) {
        ListElement p;
        if (!IsIn(M))
            return INF;
        p = FindFirstList(M);
        if (p == null)
            return INF;
        return p.WaitTime;
    }

    public double GetMinTime() {
        ListElement p;
        double Min = INF;

        p = Head;

        if (p == null)
            return INF;

        do {
            if (p.WaitTime < Min)
                Min = p.WaitTime;

            if (p == Tail) break;
            p = p.Next;
        } while (true);

        return Min;
    }

    public void AllInitial() {
        ListElement p;
        p = Head;
        if (p == null)
            return;
        do {
            p.Model.InitialModel();

            if (p == Tail) break;
            p = p.Next;
        } while (true);
    }

    public String showElement() {
        ListElement p;
        String str="";

        p = Head;

        if (p == null) ;

        do {
            str+=p.Model.name+"\n";
            if (p == Tail)
                break;
            p = p.Next;
        } while (true);

        return str;
    }

}
