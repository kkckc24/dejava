package devs;

import dejava.*;
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
public class Simparc {
    entstr EFP;
    public Simparc() throws Exception {
        EFP=new entstr("ef-p");
        // 커플 정보 저장
/*
        EFP.AddItem(new process("p"));
	EFP.AddItem(new digraph("ef"));
        EFP.AddCouple("p",    "ef",    "out",    "in");
	EFP.AddCouple("ef",   "p",     "out",    "in");
        EFP.SetCurrentItem("ef");
        EFP.AddItem(new genr("genr"));
	EFP.AddItem(new Transd("transd"));
        EFP.AddCouple("ef",      "transd",    "in",    "solved");
        EFP.AddCouple("transd",  "genr",      "out",   "stop");
        EFP.AddCouple("genr",    "ef",        "out",   "out");
	EFP.AddCouple("genr",    "transd",    "out",   "arriv");
*/
       EFP.AddItem(new digraph("ef"));
       EFP.AddItem(new digraph("p_di"));
       EFP.AddCouple("p_di", "ef", "out", "in");
       EFP.AddCouple("ef", "p_di", "out", "in");
       EFP.AddItem(new digraph("p_di2"));
       EFP.AddCouple("p_di2", "ef", "out", "in");
       EFP.AddCouple("ef", "p_di2", "out", "in");
       EFP.SetCurrentItem("ef");
       EFP.AddItem(new genr("genr"));
       EFP.AddItem(new Transd("transd"));
       EFP.AddCouple("ef", "transd", "in", "solved");
       EFP.AddCouple("transd", "genr", "out", "stop");
       EFP.AddCouple("genr", "ef", "out", "out");
       EFP.AddCouple("genr", "transd", "out", "arriv");
       EFP.SetCurrentItem("p_di");
       EFP.AddItem(new process("p"));
       EFP.AddCouple("p_di", "p", "in", "in");
       EFP.AddCouple("p", "p_di", "out", "out");
       EFP.SetCurrentItem("p_di2");
       EFP.AddItem(new process("p"));
       EFP.AddCouple("p_di2", "p", "in", "in");
       //EFP.AddCouple("p", "p_di2", "out", "out");
       EFP.Restart();
   }

    public static void main(String[] args) throws Exception {
        Simparc simparc = new Simparc();
    }
}
