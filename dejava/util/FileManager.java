package dejava.util;

import java.io.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: ISLAB (Hangkong Univ)</p>
 * @author kkckc (kkckc@korea.com)
 * @version 1.0
 */

public class FileManager {
  private String basepath;

  public FileManager() {
  }

  public FileManager(String _path) {
        basepath=_path; // �⺻ ȭ�� ���� ���
  }

  public void Write(String _file, String _data){
    try {
      FileWriter fw;
      if (basepath == null) {
        fw = new FileWriter(_file);
      }
      else fw = new FileWriter(basepath + _file);
      fw.write(_data);
      fw.flush();
      fw.close();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void Write(String _path, String _file, String _data){
    try {
      FileWriter fw = new FileWriter(_path+_file);
      fw.write(_data);
      fw.flush();
      fw.close();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void Append(String _file, String _data) {
    RandomAccessFile output = null;
//    System.out.println(str1 + "|" + str2);
    try {
//ȭ���� �������� �������� ȭ���� �����.
      output = new RandomAccessFile(_file, "rw");
    }
    catch (Exception e) {
      System.out.println("Coudn't open " + _file);
    }
    try {
//ȭ���� ���� ã�´�.
      output.seek(output.length());
//ȭ�Ͽ� ���� �߰�
      output.writeBytes(_data + "\n");
    }
    catch (Exception e1) {
      System.out.println("Error appending");
    }
  }

}
