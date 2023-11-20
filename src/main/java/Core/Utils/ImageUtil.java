package Core.Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.*;
import java.net.URL;

public class ImageUtil {
    public static boolean compareImage(File fileA, File fileB) {
        try {
            // take buffer data from both image files //
            BufferedImage biA = ImageIO.read(fileA);
            DataBuffer dbA = biA.getData().getDataBuffer();
            int sizeA = dbA.getSize();
            BufferedImage biB = ImageIO.read(fileB);
            DataBuffer dbB = biB.getData().getDataBuffer();
            int sizeB = dbB.getSize();
            // compare data-buffer objects //
            if (sizeA == sizeB) {
                for (int i = 0; i < sizeA; i++) {
                    if (dbA.getElem(i) != dbB.getElem(i)) {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Failed to compare image files ...");
            return false;
        }
    }

    public static void saveImageFromUrl(String imageUrl, String destination) {
//        (new ApiSteps()).login("valid");
//        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
//        requestSpecBuilder.setAccept("text/html, image/gif, image/jpeg, *; q=.2, /; q=.2");
//        StepsData stepsData = StepsData.getInstance();
//        Cookies cookies = stepsData.getLastCookies();
//        InputStream inputStream = given().when().spec(requestSpecBuilder.build())
//                .cookies(cookies)
//                .get(imageUrl).then().log().body().extract().asInputStream();
//        try {
//            byte[] buffer = new byte[inputStream.available()];
//            inputStream.read(buffer);
//            File targetFile = new File("/tmp/out.png");
//            OutputStream outputStream = new FileOutputStream(targetFile);
//            outputStream.write(buffer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            URL url = new URL(imageUrl);
            InputStream in = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1 != (n = in.read(buf))) {
                out.write(buf, 0, n);
            }
            byte[] response = out.toByteArray();
            FileOutputStream fos = new FileOutputStream(destination);
            fos.write(response);
            fos.close();
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
