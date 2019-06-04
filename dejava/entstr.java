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
     * Clock�� �μ��� ���� T�� ���ؼ� Clock�� ��ȯ�ϴ� �Լ��̴�. ���α׷����� ���Ǵ� ���� ã�� ���ߴ�.
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
     * �� �Լ��� �ܼ��� SetCurrentItem�̶�� �� ���Ⱑ �����Ƽ� �̷��� ���� �κ����� ����ȴ�. ���α׷����� ���Ǵ� ���� ã�� ���ߴ�.
     * @param Curr model
     * @return boolean
     */
    public boolean SCI(model Curr) {
        return SetCurrentItem(Curr);
    }

    /**
     * CurrentItem�� MODELS������ ���� �μ��� ������ �Ѵ�. true�� ��ȯ������ ����� ��ģ��.
     * �̰��� �ǹ̴� Block Diagram �󿡼� ���� �ٱ��ʿ� �ִ� ���� ������ �� ���Ǵ� �Լ��̴�.
     * �̰��� ���� �����ϰ� �� �ؿ� �������� ��������� �����ϰ� �ȴ�.
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
     * CurrentItem�� MODELS������ �����ϱ� ���ؼ� String���� ���� �μ���
     * List Ŭ������ GetModel�Լ��� ����Ͽ�
     * MODELS������ �ٲ� ������ CurrentItem�� �����ϰ� �ȴ�.
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
     * item�� �߰��ϴ� �Լ��̴�. item�� �ǹ̴� �ϳ��� ���μ����� �� ���� �ְ� �ƴϸ� EF�� �� ���� �ִ�.
     * ��ü���� ���۰����� ���� �߰��Ϸ��� item(CurrentItem)�� this�� ����
     * �ƴ� ��찡 ������ ��������� �����ϰ� ���� �κи� ���̰� �ִ�.
     *  �������
     *  * �μ��� ���� MODELS *M�� AddChild �Ѵ�.
     *  * AddModel(M->GetName(),M)�Լ��� ���� �߰��Ѵ�.
     *  * �μ��� ���� MODELS *M�� �θ�� CurrentItem�� �����ϰ� �ȴ�.
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
     * DEJAVA ���� Entry Point
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
        // Children�� �ּ��� waitTime�� ã�´�.
        CClock = Children.GetMinTime();
        TTime = CClock;

        do {
            Time = Children.GetMinTime();
            // Ŭ���� �ּ��� waittime�� �ٸ���
            if (CClock != Time)
                break;
            // Ŭ�� ����
            SetClock(Time);
            // Ŭ���� INF��
            if (Time == INF)
                break;
            // Ŭ���� limit���� ũ��
            if (Time > LimitClock)
                break;
            P = Children.FindFirstList(Time);
            // ���̸�
            if (P == null)
                break;
            // Internal Transition �߻���Ų��
            P.Model.IntTransition(Time);
        } while (true);
        System.out.println("One step ended...");
        // �ƿ�ǲ FN �߻���Ų��. (���� Printout)
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
