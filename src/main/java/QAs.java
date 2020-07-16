import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QAs {
    public static Map<String, List<QA>> QAContent = new HashMap<String, List<QA>>();
    static {
        String fileUrl = SanguoKillDoc.class.getClassLoader().getResource("QAs_utf8").getPath();
        String fileContent;
        String encoding = "UTF-8";
        File file = new File(fileUrl);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileContent = new String(filecontent, encoding);
            //System.out.println(fileContent);

            String[] QAs = fileContent.split("\\|");
            for (int i = 1; i < QAs.length; i++) {
                String QA = QAs[i];
                String topic = QA.substring(QA.indexOf("【") + 1, QA.indexOf("】"));
                //System.out.println(topic);
                QA = QA.substring(QA.indexOf("】") + 1).trim();
                //sSystem.out.println(QA);
                String[] lists = QA.split("\\[Q\\]");
                List<QA> QAlist = new ArrayList<QA>();
                for (int j = 0; j < lists.length; j++) {
                    if (lists[j].length() > 0) {
                        String[] pair = lists[j].split("\\[A\\]");
//                        System.out.println(lists[j] + "===" + pair.length);
//                        System.out.println("list:" + j + " " + pair[0] + "+++" + pair[1]);
                        QA qa = new QA(pair[0].trim(), pair[1].trim());
                        QAlist.add(qa);
                    }
                }
                QAContent.put(topic, QAlist);
            }
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
        }

    }
}
