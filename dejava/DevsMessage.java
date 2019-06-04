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
public class DevsMessage {
    public String port;
    public Object value;

    public void MakeContent(String _port, Object _value) {
        port=_port;
        value=_value;
    }

    public void MakeContent() {
        port="";
        value=null;
    }

    public String ContentPort() {
        return port;
    }

    public boolean ContentPort(String _name) {
        if(port.equals(_name)) return true;
        else return false;
    }

    public Object ContentValue() {
        return value;
    }

    public DevsMessage() {
    }
}
