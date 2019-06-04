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
 */
public abstract class atomic extends model implements DEVSDEF {
    public double Sigma;
    public double ElapsedTime;
    public String Phase;

    public atomic() {
        super();
        initalize();
    }

    public atomic(String EName) {
        super(EName);
        initalize();
    }

    public atomic(String EName, model PModel) {
        super(EName, PModel);
        initalize();
    }

    // add at. 2005.06.27
    public boolean Phase(String _name) {
        if(Phase.equals(_name)) return true;
        else return false;
    }
    //

    public void initalize() {
        SetClassName("Atomic");
        ElapsedTime = (double) 0.0;
        SetLastEventTime((double) 0.0);
        SetNextEventTime(INF);
    }

    public void InitialModel() {
        InitializeFN();
        SetNextEventTime(Sigma);
        if(_parent!=null) _parent.DoneInit((model)this,GetNextEventTime());
    }

    public void HoldIn(String P, double S) {
        Phase = P;
        Sigma = S;
    }

    public void HoldIn(String P) {
            Phase = P;
            Sigma = 0;
    }

    // Passivate
    public void Passivate() {
        HoldIn("passive", INF);
    }

    // Continue
    public void Continue() {
        HoldIn(Phase, SubTime(Sigma, ElapsedTime));
    }

    public double AddTime(double T1, double T2) {
        if ((T1 == INF) || (T2 == INF))
            return INF;
        return T1 + T2;
    }

    // 시간 빼주기
    public double SubTime(double T1, double T2) {
        if ((T1 == INF) || (T2 == INF))
            return INF;
        // 절대값으로 반환
        return Math.abs(T1 - T2);
    }

    public void MakeContent() {
        return;
    }

    public void MakeContent(String port, Object MSG) {
        String OPort;
        OutMessage.MakeContent(port, MSG);
        if (_parent != null) {
            OutMessage.MakeContent(port, MSG);
            _parent.Inject(this, OutMessage, GetNextEventTime());
        }
        return;
    }

    public void Inject(DevsMessage MSG, double Time) {
        if ((Time < GetLastEventTime()) || (Time > GetNextEventTime()))
            return;
        SetInMessage(MSG);
        ElapsedTime = Time - GetLastEventTime();
        ExtTransitionFN(ElapsedTime, MSG);
        SetLastEventTime(Time);
        SetNextEventTime(AddTime(GetLastEventTime(), Sigma));

        if (_parent!=null) _parent.Done((model)this,GetNextEventTime());
    }

    public void Done(model p, double Time) {
        return;
    }


    public void DoneInit(model P, double Time){ return; }

    public void Inject(model s, DevsMessage MSG, double Time) {
        Inject(MSG, Time);
    }

    public void Inject(DevsMessage MSG) {
        Inject(MSG, GetNextEventTime());
    }
    public void Output(){ Output(GetNextEventTime()); }
    public void Output(double Time){
         double Temp = GetNextEventTime();
         SetNextEventTime(Time);
         OutputFN();
         SetNextEventTime(Temp);
     }

    public void IntTransition(){ IntTransition(GetNextEventTime()); }
    public void IntTransition(double Time){
              if (Time != GetNextEventTime()) return;
              // 함수에서 정의된 OutputFunction 실행
              Output(Time);
              // 함수에서 정의된 IntTransitionFN 실행
              IntTransitionFN();
              // 이전 이벤트 발생시간 Set
              SetLastEventTime(Time);
              // 다음 이벤트 발생시간 Set
              SetNextEventTime(AddTime(GetLastEventTime(),Sigma));
              // Done 발생
              if (_parent!=null) _parent.Done((model)this,GetNextEventTime());
}

    public abstract void IntTransitionFN();
    public abstract void OutputFN();
    public abstract void InitializeFN();
    public abstract void ExtTransitionFN(double time, DevsMessage msg);
}
