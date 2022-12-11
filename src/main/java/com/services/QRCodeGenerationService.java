package com.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.Hashtable;


@Service
@RequiredArgsConstructor
public class QRCodeGenerationService {

    @Value("${spring.filesDirectory}")
    private String filesDirectory;

    @Value("${spring.qrCodesDirectory}")
    private String qrCodesDirectory;

    private final URLGenerationService urlGenerationService;

    public byte[] generateQRCode(byte[] menu) {

        String token = urlGenerationService.generateToken();
        String url = urlGenerationService.generateURL(token);

        String qrCodePath = qrCodesDirectory + "/" + token + ".pdf";
        String menuPath = filesDirectory + "/" + token + ".pdf";

        int size = 250;
        String fileType = "pdf";


        try {
            File qrFile = new File(qrCodePath);
            File menuFile = new File(menuPath);
            FileOutputStream fileOutput = new FileOutputStream(menuFile);
            fileOutput.write(menu);

            return createQRImage(qrFile, url, size, fileType);

        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] createQRImage(File qrFile, String qrCodeText, int size, String fileType)
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
        ImageIO.write(image, fileType, qrFile);

        return Files.readAllBytes(qrFile.toPath());
    }

}
