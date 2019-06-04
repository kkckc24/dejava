package dejava;

import java.util.TreeSet;

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
public abstract class model extends entity {
    // port에 대한 정보
    public model _parent;
    public port _port=new port();
    public DevsMessage InMessage = new DevsMessage();
    public DevsMessage OutMessage = new DevsMessage();

    public double LastEventTime;
    public double NextEventTime;
    /**
     * Coupling 정보를 연결한다. Ext input 정보
     */
    public void addCoupling(entity from, entity to, String FPort, String TPort) {
        _port.addCoupling(from, to, FPort, TPort);
    }

    public model() {
        SetClassName("MODELS");
        SetName("");
        _parent = null;
        // 포트 초기화
    }

    public model(String EName) {
        SetClassName("MODELS");
        SetName(EName);
        _parent = null;
    }

    public model(String EName, model PModel) {
        SetClassName("MODELS");
        SetName(EName);
        _parent = PModel;
    }

    public void SetNextEventTime(double NTIME) {
        NextEventTime = NTIME;
    }

    public void SetLastEventTime(double LTIME) {
        LastEventTime = LTIME;
    }

    public double GetNextEventTime() {
        return NextEventTime;
    }

    public double GetLastEventTime() {
        return LastEventTime;
    }

    public void SetInMessage(DevsMessage MSG) {
        InMessage = MSG;
    }

    public DevsMessage GetOutMessage() {
        return OutMessage;
    }

    public void SetOutMessage(DevsMessage MSG) {
        OutMessage = MSG;
    }

    public void SetParent(model p) {
        _parent = p;
    }

    public model GetParent() {
        return _parent;
    }

    public abstract void IntTransition(double Time) ;
    public abstract void Inject(DevsMessage MSG) ;
    public abstract void Inject(DevsMessage MSG, double Time) ;
    public abstract void Inject(model s, DevsMessage MSG, double Time) ;
    public abstract void InitialModel() ;
    public abstract void Output() ;
    public abstract void Output(double T) ;
    public abstract void Done(model s, double T);
    public abstract void DoneInit(model s, double T);
}
