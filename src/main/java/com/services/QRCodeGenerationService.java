package com.services;

import com.data.entities.Menu;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.Hashtable;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Slf4j
public class QRCodeGenerationService {

    @Value("${spring.filesDirectory}")
    private String filesDirectory;

    @Value("${spring.qrCodesDirectory}")
    private String qrCodesDirectory;

    @Value("${spring.domainName}")
    private String domainName;

    @Value("${spring.qrcodeScannerEndpoint}")
    private String scanEndpoint;

    @Value("${spring.qrcodeMethod}")
    private String qrcodeMethod;

    private final RestaurantService restaurantService;
    private final MenuService menuService;

    private final URLGenerationService urlGenerationService;

    public ResponseEntity<HttpStatus> generateQRCode(String name, String city) {

        String url = generateURL(name, city);

        Menu menu = restaurantService.findRestaurant(name, city).getMenu();
        if (Objects.isNull(menu)) {
            log.error("menu is not found");
            return null;
        }

        int size = 250;
        String fileType = "PDF";

        try {
            File menuFile = new File(menu.getFilePath());
            //read uploaded menu into bytes
            FileInputStream uploadedMenu = new FileInputStream(menuFile);
            byte[] menuBytes = new byte[(int) menuFile.length()];
            uploadedMenu.read(menuBytes);
            //generate qr image
            createQRImage(menu.getQRCodePath(), url, size, fileType);
            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    private void createQRImage(String qrcodePath, String qrCodeText, int size, String fileType)
            throws WriterException, IOException {
        // Create the ByteMatrix for the QR-Code that encodes the given String
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix byteMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
        // Make the BufferedImage that are to hold the QRCode
        int matrixWidth = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        // Paint and save the image using the ByteMatrix
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }

        convertImageToPdfAndSave(image, qrcodePath);

        return;
    }

    private PDDocument convertImageToPdfAndSave(BufferedImage image, String qrcodePath) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        // Load the image to be added to the PDF
        PDImageXObject pdImage = JPEGFactory.createFromImage(document, image);

        // Start a new content stream which will "hold" the to be created content
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Draw the image in the PDF
        contentStream.drawImage(pdImage, 60, 60);

        // Close the content stream
        contentStream.close();

        // Save the PDF
        document.save(qrcodePath);

        // Close the document
        document.close();

        return document;
    }

    public String generateURL(String name, String city) {

        StringBuilder urlBuilder = new StringBuilder();

        urlBuilder.append("http://").append(this.domainName).append(this.qrcodeMethod)
                .append(scanEndpoint).append("?menuName=").append(name + "_" + city);

        return urlBuilder.toString();
    }

}
