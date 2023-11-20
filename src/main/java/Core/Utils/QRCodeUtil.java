package Core.Utils;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class QRCodeUtil {
//    public static void createQRCode(String qrCodeData, String filePath,
//                                    String charset, Map hintMap, int qrCodeheight, int qrCodewidth)
//            throws WriterException, IOException {
//        BitMatrix matrix = new MultiFormatWriter().encode(
//                new String(qrCodeData.getBytes(charset), charset),
//                BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight);
//        MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath
//                .lastIndexOf('.') + 1), new File(filePath));
//    }

    public static String readQRCode(String filePath) throws IOException, NotFoundException {
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                new BufferedImageLuminanceSource(
                        ImageIO.read(Files.newInputStream(Paths.get(filePath))))));
        Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap);
        return qrCodeResult.getText();
    }
}
