package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;

public class JPAConvertToPDF {
    public static void main(String[] args) {

        String jpgFilePath = "/Users/spin.liao/Documents/1717652704755.jpg";
        String pdfFilePath = "/Users/spin.liao/Documents/1717652704755.pdf";

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDImageXObject pdImage = PDImageXObject.createFromFile(jpgFilePath, document);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Scale image to fit the page
            float scale = Math.min(page.getMediaBox().getWidth() / pdImage.getWidth(),
                    page.getMediaBox().getHeight() / pdImage.getHeight());

            float x = (page.getMediaBox().getWidth() - pdImage.getWidth() * scale) / 2;
            float y = (page.getMediaBox().getHeight() - pdImage.getHeight() * scale) / 2;

            contentStream.drawImage(pdImage, x, y, pdImage.getWidth() * scale, pdImage.getHeight() * scale);
            contentStream.close();

            document.save(new File(pdfFilePath));
            System.out.println("PDF created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}