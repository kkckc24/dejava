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
public class entstr extends digraph implements DEVSDEF {
    public model CurrentItem;
    public double Clock, LimitClock, CClock;
    public List ChildList;

    public entstr(String EName) {
        super(EName);
        SetClassName("ENTSTR");
        SetName(EName);
        ChildList = new List();
        ChildList.AddModel(EName, this);
        CurrentItem = this;
        SetClock((double) 0.0);
        LimitClock = INF;
        CClock = 0.0;
    }

    public void SetLimit(double T) {
        LimitClock = T;
    }

    public double SetClock(double T) {
        Clock = T;
        return T;
    }

    public double GetClock() {
        return Clock;
    }

    /**
     * Clock과 인수로 받은 T를 더해서 Clock을 반환하는 함수이다. 프로그램에서 사용되는 것을 찾지 못했다.
     * @param T double
     * @return double
     */
    public double AddClock(double T) {
        if ((Clock == INF) || (T == INF))
            return SetClock(INF);
        Clock += T;
        if (Clock < 0)
            return SetClock(INF);
        return GetClock();
    }

    /**
     * 이 함수는 단순히 SetCurrentItem이라고 다 쓰기가 귀찮아서 이렇게 만든 부분으로 예상된다. 프로그램에서 사용되는 것을 찾지 못했다.
     * @param Curr model
     * @return boolean
     */
    public boolean SCI(model Curr) {
        return SetCurrentItem(Curr);
    }

    /**
     * CurrentItem을 MODELS형으로 받은 인수로 지정을 한다. true을 반환함으로 기능을 마친다.
     * 이것의 의미는 Block Diagram 상에서 제일 바깥쪽에 있는 것을 지정할 때 사용되는 함수이다.
     * 이것을 먼저 정의하고 그 밑에 구조들을 계속적으로 정의하게 된다.
     * @param Curr model
     * @return boolean
     */
    public boolean SetCurrentItem(model Curr) {
        CurrentItem = Curr;
        return true;
    }

    public int SCI(String Curr) {
        return SetCurrentItem(Curr);
    }

    /**
     * CurrentItem을 MODELS형으로 지정하기 위해서 String으로 받은 인수를
     * List 클래스의 GetModel함수를 사용하여
     * MODELS형으로 바꾼 다음에 CurrentItem을 지정하게 된다.
     * @param Curr String
     * @return int
     */
    public int SetCurrentItem(String Curr) {
        model p;
        p = ChildList.GetModel(Curr);
        if (p == null)
            return 0;
        CurrentItem = p;
        return 1;
    }

    /**
     * item을 추가하는 함수이다. item의 의미는 하나의 프로세서가 될 수도 있고 아니면 EF가 될 수도 있다.
     * 전체적인 동작과정은 지금 추가하려는 item(CurrentItem)이 this인 경우와
     * 아닌 경우가 있지만 수행과정은 동일하고 리턴 부분만 차이가 있다.
     *  수행과정
     *  * 인수로 받은 MODELS *M을 AddChild 한다.
     *  * AddModel(M->GetName(),M)함수로 모델을 추가한다.
     *  * 인수로 받은 MODELS *M의 부모로 CurrentItem을 셋팅하게 된다.
     * @param M model
     * @return int
     */
    public boolean AddItem(model M) {
        if (M == null)
            System.out.println("null pointer assigned");

//        ASSERT(M != null);
        if (CurrentItem == this) {
            if (((entstr) CurrentItem).IsDevsChild(M))
                return false;
            ((entstr) CurrentItem).AddChild(M);
            ChildList.AddModel(M.GetName(), M);
            M.SetParent(CurrentItem);
            return true;
        } else {
            if (((coupled) CurrentItem).IsDevsChild(M))
                return false;
            ((coupled) CurrentItem).AddChild(M);
            ChildList.AddModel(M.GetName(), M);
            M.SetParent(CurrentItem);
            return true;
        }
    }

    public boolean AddCouple(model Fmodel, model Tmodel, String FPort, String TPort) {
        CurrentItem.addCoupling(Fmodel, Tmodel, FPort, TPort);
        return true;
    }

    public boolean AddCouple(String Fmodel, String Tmodel, String FPort,
                         String TPort) {
        model F, T;

        F = ChildList.GetModel(Fmodel);
        T = ChildList.GetModel(Tmodel);

        if ((F == null) || (T == null))
            return false;

        AddCouple(F, T, FPort, TPort);
        return true;
    }

    public void Initialize() {
        Children.AllInitial();
        Clock = 0.0;
    }

    /**
     * DEJAVA 시작 Entry Point
     */
    public void Restart() {
        double Time;
        ListElement P;

        Initialize();
        Clock = (double) 0.0;

        do {
            StepStart();
            Time = Children.GetMinTime();
            SetClock(Time);
            if (Time == INF)
                break;
            if (Time > LimitClock)
                break;
            P = Children.FindFirstList(Time);
            if (P == null)
                break;
        } while (true);
    }

    public void StepStart() {
        double Time, TTime;
        ListElement P;
        // Children중 최소의 waitTime을 찾는다.
        CClock = Children.GetMinTime();
        TTime = CClock;

        do {
            Time = Children.GetMinTime();
            // 클럭과 최소의 waittime이 다르면
            if (CClock != Time)
                break;
            // 클럭 설정
            SetClock(Time);
            // 클럭이 INF면
            if (Time == INF)
                break;
            // 클럭이 limit보다 크면
            if (Time > LimitClock)
                break;
            P = Children.FindFirstList(Time);
            // 널이면
            if (P == null)
                break;
            // Internal Transition 발생시킨다
            P.Model.IntTransition(Time);
        } while (true);
        System.out.println("One step ended...");
        // 아웃풋 FN 발생시킨다. (단지 Printout)
        OutputFN(TTime);
    }

    public void Inject(String P, Object V) {
        OutMessage.MakeContent(P, V);
        super.Inject(OutMessage, (double) 0.0);
    }

    public void DoneInit(model M, double T) {
        Children.SetOne(M, T);
    }

    public void Done(model M, double T) {
        Children.SetOne(M, T);
    }

    public void IntTransition() {}

    public void Output(double T) {}

    public void Output() {}

    public void Initialmodel() {}

    public boolean OutputFN(double clk) {
        System.out.println("Global Clock (Root): " + clk);
        return true;
    }

    public String strOfChild() {
        return Children.showElement();
    }

    public void showElement(String _item) {
        SetCurrentItem(_item);
        int num;
        String str="";
        if (CurrentItem == this) {
            num=((entstr) CurrentItem).NumOfChild();
            str=((coupled) CurrentItem).strOfChild();
//           num=((entstr) CurrentItem).NumOfChild();
        } else {
           num=((coupled) CurrentItem).NumOfChild();
           str=((coupled) CurrentItem).strOfChild();
        }
        System.out.println("Size : "+num);
        System.out.println(str);
    }
}
