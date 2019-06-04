package dejava;

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

public class digraph extends coupled {
    public ListElement p; // list element

    public digraph() {
        SetClassName("Digraph");
    }

    /**
     * 생성자
     * @param EName String
     */
    public digraph(String EName) {
        super(EName);
        SetClassName("Digraph");
    }

    /**
     * Internal Transation Function
     * @param Time double
     */
    public void IntTransition(double Time) {
        if (Time != GetNextEventTime())
            return;

        SetLastEventTime(Time);
        p = Children.FindFirstList(Time);
        if (p == null)
            return;

        do {
            p.Model.IntTransition(Time);
            p = Children.FindNextList(Time);
            if (p == null)
                break;
        } while (true);
    }

    /**
     * Mlist로부터 가져온다.
     * @return double
     */
    public double GetMinTime() {
        return Children.GetMinTime();
    }

    public void Inject(DevsMessage MSG) {
        Inject(this, MSG, GetNextEventTime());
    }

    public void Inject(DevsMessage MSG, double T) {
        Inject(this, MSG, T);
    }

    public void Inject(model from, DevsMessage MSG, double Time) {
        String PPort;
        PortPair PPair, temp;

        if ((from == this) && (_parent != null))
            _parent.Inject(this, MSG, Time);

        PPort = MSG.ContentPort();
        PPair = _port.FindFirstPair(PPort);
        temp = _port.Curr;

        do {
            if (PPair == null)
                break;
            OutMessage.MakeContent(PPair.toPort, MSG.ContentValue());
            if ((PPair.fromModel == from) || (from == _parent))
                ((model) (PPair.toModel)).Inject(this, OutMessage, Time);
            _port.Curr = temp;
            PPair = _port.FindnextPair(PPort);
            temp = _port.Curr;
        } while (true);
    }

//    public void IntTransition() {}

    public void InitialModel() {
        Children.AllInitial();
        if (_parent != null)
            _parent.DoneInit(this, GetMinTime());
        SetNextEventTime(GetMinTime());
    }

    // Done시 Child로 메시지 전달
    public void Done(model m, double T) {
        Children.SetOne(m, T);
        SetNextEventTime(GetMinTime());
        if (_parent != null)
            _parent.Done(this, GetMinTime());
    }

    public void DoneInit(model m, double T) {
        Children.SetOne(m, T);
    }

    public void Output() {}
    public void Output(double Time) {}
}
