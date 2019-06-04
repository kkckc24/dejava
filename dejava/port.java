package dejava;



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
 */// 연결 정보 확인
public class port {
    public PortPair Head, Tail, Curr;

    public port() {
        Head = Tail = Curr = null;
    }

    /**
     * 커플링을 추가
     * @param from entity
     * @param to entity
     * @param FPort String
     * @param TPort String
     */
    public boolean addCoupling(entity from, entity to, String FPort, String TPort) {
        PortPair temp;
        temp = new PortPair();
        if (temp == null)
            return false;

        temp.fromModel = from;
        temp.toModel = to;
        temp.fromPort = FPort;
        temp.toPort = TPort;
        temp.next = null;
        temp.before = null;

        if (Head == null) {
            Head = temp;
            Tail = temp;
        } else {
            Tail.next = temp;
            temp.before = Tail;
            Tail = temp;
        }
        return true;
    }

    public int DelFromPort(String Port) {
        entity p;

        p = FindFirstfromModel(Port);
        if (p==null)
            return 0;

        do {
            DelCurrentCoupling();
            p = FindnextfromModel(Port);
        } while (p==null);

        return 1;
    }

    public int DelToPort(String Port) {
        entity p;

        p = FindFirsttoModel(Port);
        if (p==null)
            return 0;

        do {
            DelCurrentCoupling();
            p = FindnexttoModel(Port);
        } while (p==null);

        return 1;
    }

    public void DelCurrentCoupling() {
        PortPair p;

        if (Curr == null)
            ;
        else
        if (Curr == Head) {
            if (Head == Tail) {
                Head = Tail = null;
            } else {
                Head.next.before = null;
                Head = Head.next;
            }

            Curr = Head;
        } else
        if (Curr == Tail) {
            if (Head == Tail) {
                Head = Tail = null;
            } else {
                Tail.before.next = null;
                Tail = Tail.before;
            }
            Curr = Tail;
        } else {
            p = Curr.next;
            p.before = Curr.before;
            Curr.before.next = p;
            Curr = p;
        }
    }

    public void DelAllCoupling() {
        PortPair p, q;

        if (Head == null)
            return;

        p = q = Head;

        while (p != Tail) {
            p = p.next;
            q = p.before;
        }


        Head = Tail = Curr = null;
    }

    public int IsInFromPort(String Port) {
        entity T;

        T = FindFirstfromModel(Port);
        if (T==null)
            return 1;
        else
            return 0;
    }

    public int IsInToPort(String Port) {
        entity T;

        T = FindFirsttoModel(Port);
        if (T==null)
            return 1;
        else
            return 0;
    }

    public entity GetfromModel(String Port) {
        if (Curr == null)
            return null;
        return Curr.fromModel;
    }

    public entity GetoModel(String Port) {
        if (Curr == null)
            return null;
        return Curr.toModel;
    }

    public entity FindFirstfromModel(String Port) {
        Curr = Head;
        return FindnextfromModel(Port);
    }

    public entity FindFirsttoModel(String Port) {
        Curr = Head;
        return FindnexttoModel(Port);
    }

    public entity FindnextfromModel(String Port) {
        PortPair p;

        if (Curr == null)
            return null;

        do {
            if (Curr.fromPort.equals(Port))
            //if (Curr.fromPort == Port)
                break;
            if (Curr == Tail) {
                Curr = null;
                return null;
            } else
                Curr = Curr.next;
        } while (true);

        p = Curr;
        if (Curr == Tail)
            Curr = null;
        else
            Curr = Curr.next;
        return p.fromModel;
    }

    public entity FindFirstFromtoModel(String Port) {
        Curr = Head;
        return FindnextFromtoModel(Port);
    }

    public entity FindnextFromtoModel(String Port) {
        PortPair p;

        if (Curr == null)
            return null;

        do {
            if (Curr.fromPort.equals(Port))
//            if (Curr.fromPort == Port)
                break;
            if (Curr == Tail) {
                Curr = null;
                return null;
            } else
                Curr = Curr.next;
        } while (true);

        p = Curr;
        if (Curr == Tail)
            Curr = null;
        else
            Curr = Curr.next;
        return p.toModel;
    }

    public entity FindnexttoModel(String Port) {
        PortPair p;

        if (Curr == null)
            return null;

        do {
            if (Curr.toPort.equals(Port))
//            if (Curr.toPort == Port)
                break;
            if (Curr == Tail) {
                Curr = null;
                return null;
            } else
                Curr = Curr.next;
        } while (true);

        p = Curr;
        if (Curr == Tail)
            Curr = null;
        else
            Curr = Curr.next;
        return p.toModel;
    }

    public PortPair FindFirstPair(String Port) {
        Curr = Head;
        return FindnextPair(Port);
    }

    public PortPair FindnextPair(String Port) {
        PortPair p;

        if (Curr == null)
            return null;

        do {
//            if (Curr.fromPort == Port)
            if (Curr.fromPort.equals(Port))
                break;
            if (Curr == Tail) {
                Curr = null;
                return null;
            } else
                Curr = Curr.next;
        } while (true);

        p = Curr;
        if (Curr == Tail)
            Curr = null;
        else
            Curr = Curr.next;
        return p;
    }


}
