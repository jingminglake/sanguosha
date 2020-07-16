import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;


public class MyPrinter {

    public static void printPDF(List<String> indexes, Map<String, List<QA>> m) {
        try {
            PDDocument doc = new PDDocument();
            for (String index : indexes) {
                List<QA> value = m.get(index);

                PDPage myPage = new PDPage(PDRectangle.LETTER);
                doc.addPage(myPage);
                PDPageContentStream cont = new PDPageContentStream(doc, myPage);
                String fontUrl = SanguoKillDoc.class.getClassLoader().getResource("arialuni.ttf").getPath();
                PDFont unicodeFont = PDType0Font.load(doc, new File(fontUrl));
                cont.setFont(unicodeFont, 12);
                cont.setLeading(14.5f);

                //Creating PDImageXObject object
                String imageBaseUrl = SanguoKillDoc.class.getClassLoader().getResource("cards").getPath();
                String imagePath = imageBaseUrl + "/" + index + ".png";
                System.out.println("imagePath" + imagePath);
                PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, doc);
                int iw = pdImage.getWidth();
                int ih = pdImage.getHeight();

                cont.drawImage(pdImage, 30, 580, iw / 3, ih / 3);
                printQA(doc, cont, value);
            }

            doc.save("src/main/resources/wwii.pdf");
            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printQA(PDDocument doc, PDPageContentStream cont, List<QA> qas) {
        int lineNum = 0;
        int cap = 38;
        try {
            cont.beginText();
            cont.newLineAtOffset(25, 550);
            for (QA qa : qas) {
                String q = qa.getQ().replaceAll("[\\s]", "");
                List<String> linesQ = generateMultiLines(q);
                String a = qa.getA().replaceAll("[\\s]", "");
                List<String> linesA = generateMultiLines(a);

                if (lineNum + linesQ.size() + linesA.size() >= cap) {
                    cont.endText();
                    cont.close();
                    lineNum = 0;
                    PDPage newPage = new PDPage(PDRectangle.LETTER);
                    doc.addPage(newPage);
                    cont = new PDPageContentStream(doc, newPage);
                    String fontUrl = SanguoKillDoc.class.getClassLoader().getResource("arialuni.ttf").getPath();
                    PDFont unicodeFont = PDType0Font.load(doc, new File(fontUrl));
                    cont.setFont(unicodeFont, 12);
                    cont.setLeading(14.5f);

                    cont.beginText();
                    cont.newLineAtOffset(25, 750);
                }

                cont.showText("Q：");
                for (String line : linesQ) {
                    cont.showText(line);
                    cont.newLine();
                }
                lineNum += linesQ.size();

                cont.showText("A：");
                for (String line : linesA) {
                    cont.showText(line);
                    cont.newLine();
                }
                lineNum += linesA.size();
                cont.newLine();
                lineNum++;
            }
            cont.endText();
            cont.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> generateMultiLines(String str) {
        List<String> lines = new ArrayList<String>();
        for (int i = 0; i < str.length(); i += 45) {
            lines.add(str.substring(i, Math.min(i + 45, str.length())));
        }
        return lines;
    }
}
