import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


public class SanguoKillDoc {

    public static void main(String[] args) {
        String[] cards = new String[] {"桃", "决斗",
                "过河拆桥", "顺手牵羊","万箭齐发","南蛮入侵",
                "桃园结义", "借刀杀人", "乐不思蜀", "闪电", "无懈可击",
                "雌雄双股剑", "青釭剑", "寒冰剑", "贯石斧",
                "青龙偃月刀", "丈八蛇矛",
                "银月枪", "方天画戟"};
        List<String> cardsList = Arrays.asList(cards);
        String[] heroes = new String[] {
                "刘备", "关羽", "赵云", "马超",
                "诸葛亮", "黄月英", "曹操",
                "郭嘉", "司马懿", "夏侯惇",
                "许褚", "张辽", "甄姬",
                "孙权", "甘宁", "周瑜",
                "陆逊", "黄盖","吕蒙",
                "大乔", "孙尚香", "貂蝉",
                "华佗", "小乔", "于吉", "周泰",
                "夏侯渊"
        };
        List<String> heroesList = Arrays.asList(heroes);
        Collections.sort(cardsList, new Comparator<String>() {
            @Override
            public int compare(String o1,String o2) {
                return Collator.getInstance(Locale.CHINESE).compare(o1,o2);
            }
        });

        Collections.sort(heroesList, new Comparator<String>() {
            @Override
            public int compare(String o1,String o2) {
                return Collator.getInstance(Locale.CHINESE).compare(o1,o2);
            }
        });

        System.out.println(cardsList);
        System.out.println(heroesList);

        Map<String, List<QA>> QADictionary = new HashMap<String, List<QA>>();
        for (String card : cardsList) {
            if (QAs.QAContent.containsKey(card)) {
                QADictionary.put(card, QAs.QAContent.get(card));
            } else {
                QADictionary.put(card, new ArrayList<QA>());
            }
        }
        for (String hero : heroesList) {
            if (QAs.QAContent.containsKey(hero)) {
                QADictionary.put(hero, QAs.QAContent.get(hero));
            } else {
                QADictionary.put(hero, new ArrayList<QA>());
            }
        }

        for (Map.Entry<String, List<QA>> entry : QAs.QAContent.entrySet()) {
            String topic = entry.getKey();
            List<QA> qas = entry.getValue();
            //System.out.println(topic);
            for (QA qa : qas) {
                String qaStr = qa.toString();
                if (qaStr != null) {
                    Set<String> tags = getAllTags(qaStr);
                    //System.out.println(tags);
                    for (String tag : tags) {
                        if (!tag.equals(topic) && QADictionary.containsKey(tag)) {
                            QADictionary.get(tag).add(qa);
                        }
                    }
                }
            }
        }

//
//        for (Map.Entry<String, List<QA>> entry : QADictionary.entrySet()) {
//            String topic = entry.getKey();
//            List<QA> qas = entry.getValue();
//            System.out.println(topic);
//            for (QA qa : qas) {
//                System.out.println(qa);
//            }
//        }
        List<String> indexes = new ArrayList<String>();
        indexes.addAll(cardsList);
        indexes.addAll(heroesList);
        MyPrinter.printPDF(indexes, QADictionary);
    }

    public static Set<String> getAllTags(String qaStr) {
        int fromIndex = 0;
        Set<String> tags = new HashSet<String>();
        while (fromIndex < qaStr.length()) {
            int start = qaStr.indexOf("【", fromIndex) + 1;
            if (start == 0) break;
            int end = qaStr.indexOf("】", fromIndex);
            String tag = qaStr.substring(start, end);
            tags.add(tag);
            fromIndex = end + 1;
        }
        return tags;
    }
}
