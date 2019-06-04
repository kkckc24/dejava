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

public class entity {
    public String name;
    public String className;
    public int GettableChildren;

    public void SetName(String _name) {
        this.name=_name;
    }

    public void SetClassName(String _className) {
        this.className=_className;
    }

    public String GetName() {
        return this.name;
    }

    public String GetClassName() {
        return this.className;
    }

    /**
     * √ ±‚»≠
     */
    public entity() {
        name=null;
        className=null;
    }
}
