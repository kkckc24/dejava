package dejava.data;

import dejava.*;
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
public class List {
    public List() {
        Head = Tail = Curr = null;
        Len = 0;
    }

    public ListElem Head, Tail, Curr;
    public int Len;

    public void AddModel(String N, model M) {
        if (IsIn(N)!=0)
            return;
        Curr = new ListElem();
        Curr.Name = N;
        Curr.Address = M;
        Curr.Before = null;
        Curr.Next = null;

        if (Head == null) {
            Head = Tail = Curr;
            Len = 1;
        } else {
            Tail.Next = Curr;
            Curr.Before = Tail;
            Tail = Curr;
            Len++;
        }
    }

    public  int Length() {
        return Len;
    }

    public boolean IsEmpty() {
        if (Len == 0)
            return true;
        return false;
    }

    public int IsIn(String N) {
        Curr = FindFirst(N);
        if (Curr == null)
            return 0;
        else
            return 1;
    }

    public int Delete(String N) {
        Curr = FindFirst(N);
        if (Curr == null)
            return 0;

        if (Len == 1) {

            Head = Tail = Curr = null;
            Len = 0;
        } else if (Curr == Head) {
            Head.Next.Before = null;
            Head = Head.Next;

            Len--;
        } else if (Curr == Tail) {
            Tail.Before.Next = null;
            Tail = Tail.Before;

            Len--;
        } else {
            Curr.Before.Next = Curr.Next;
            Curr.Next.Before = Curr.Before;

            Len--;
        }

        return 1;
    }

    public void DeleteAll() {
        ListElem p, q;

        if (IsEmpty())
            return;

        if (Length() == 1) {
            Head = Tail = Curr = null;
            Len = 0;
        } else {
            p = q = Head;

            do {
                q = p;
                p = p.Next;
                if (p == Tail)
                    break;
            } while (true);

            Len = 0;
        }
    }

    public ListElem FindFirst(String N) {
        ListElem p;
        if (IsEmpty())
            return null;

        p = Head;

        do {
//            System.out.println(p.Name+" "+N);
            if (p.Name.equals(N))
                return p;
            if (p == Tail)
                break;
            p = p.Next;
        } while (true);

        return null;
    }

    public model GetModel(String N) {
        Curr = FindFirst(N);
        if (Curr == null)
            return null;
        else
            return Curr.Address;
    }
/*
    public String showElement() {
            ListElem p;
            String str="";

            p = Head;

            if (p == null) ;

            do {
                str+=p.Name+"\n";
                if (p == Tail)
                    break;
                p = p.Next;
            } while (true);

            return str;
    }
*/
}
