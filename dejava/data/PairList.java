package dejava.data;

import dejava.data.*;
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
public class PairList {
    public EntPair Head, Tail, Curr;
    public int len;

    public PairList() {
        Head = Tail = null;
        len = 0;
    }


    public int add(EntPair pr) {
        return add(pr.key, pr.value);
    };
    public int add(entity key, entity value) {
        EntPair p;

        if (IsIn(key))
            return 0;
        if (len >= 32767)
            return 0;

        p = new EntPair();
        p.Before = p.Next = null;
        p.key = key;
        p.value = value;

        if (Head == null) {
            Head = Tail = p;
        } else {
            p.Before = Tail;
            Tail.Next = p;
            Tail = p;
        }

        len++;
        return 1;
    }

    public int DelOne(EntPair pr) {
        return DelOne((pr.key).GetName());
    }

    public int DelOne(entity key) {
        return DelOne(key.GetName());
    }

    public int DelOne(String key_name) {
        EntPair p;

        if (!IsIn(key_name))
            return 0;
        if (len <= 0)
            return 0;

        p = FindFirst(key_name);

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

        len--;
        return 1;
    }

    public boolean IsIn(EntPair pr) {
        return IsIn(pr.key.GetName());
    }

    public boolean IsIn(entity key) {
        return IsIn(key.GetName());
    }

    public boolean IsIn(String key_name) {
        if (FindFirst(key_name) != null)
            return true;
        return false;
    }

    public EntPair FindFirst(EntPair pr) {
        return FindFirst(pr.key.GetName());
    }

    public EntPair FindFirst(entity key) {
        return FindFirst(key.GetName());
    }

    public EntPair FindFirst(String key_name) {
        EntPair p;

        Curr = p = Head;

        if (p == null)
            return p;

        do {
            if (p == Tail)
                break;
            if (p.key.GetName() == key_name) {
                Curr = p.Next;
                return p;
            }
            p = p.Next;
        } while (true);

        if (p == Tail)
            Curr = null;
        if (p.key.GetName() == key_name)
            return p;
        return null;
    }

    public EntPair FindNext(EntPair pr) {
        return FindNext(pr.key.GetName());
    }

    public EntPair FindNext(entity key) {
        return FindNext(key.GetName());
    }

    public EntPair FindNext(String key_name) {
        EntPair p;

        p = Curr;

        if (p == null)
            return p;

        do {
            if (p == Tail)
                break;
            if (p.key.GetName() == key_name) {
                Curr = p.Next;
                return p;
            }
            p = p.Next;
        } while (true);

        if (p == Tail)
            Curr = null;
        if (p.key.GetName() == key_name)
            return p;
        return null;
    }

    public void SetOne(entity key, entity value) {
        SetOne(key.GetName(), value);
    }

    public void SetOne(String key_name, entity value) {
        EntPair p;
        if (!IsIn(key_name))
            return;
        p = FindFirst(key_name);
        p.value = value;
    }

    public entity GetValue(entity key) {
        return GetValue(key.GetName());
    }

    public entity GetValue(String key_name) {
        EntPair p;
        if (!IsIn(key_name))
            return null;
        p = FindFirst(key_name);
        if (p == null)
            return null;
        return p.value;
    }
}
