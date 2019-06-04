package dejava;

import dejava.data.*;
import java.util.ArrayList;
/**
 * <p>Title: DEVS ISLAB</p>
 *
 * <p>Description: 연결정보 저장</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Hankuk Avation Univ</p>
 *
 * @author kkckc (kkckc@korea.com)
 * @version 1.0
 */
public class coupled extends model {
    public MList Children;
    public coupled() {
        GettableChildren = 1;
        return;
    }

    public coupled(String EName) {
        super(EName);
        Children=new MList();
        GettableChildren = 1;
        return;
    }

    public void AddChild(model _model) {
//        if(Children==null) Children=new MList();
        Children.AddOne(_model);
    }

    public void AddChild(model _model, double T) {
//        if(Children==null) Children=new MList();
        Children.AddOne(_model, T);
    }

    public boolean IsDevsChild(model _model) {
        return Children.IsIn(_model);
    }

    public int NumOfChild() {
        return Children.GetLength();
    }

    public String strOfChild() {
        return Children.showElement();
    }

    public void IntTransition(double Time) {}
    public void Inject(DevsMessage MSG) {}
    public void Inject(DevsMessage MSG, double Time) {}
    public void Inject(model s, DevsMessage MSG, double Time) {}
    public void InitialModel() {}
    public void Output(){}
    public void Output(double T) {}
    public void Done(model s, double T){}
    public void DoneInit(model s, double T){}
}
