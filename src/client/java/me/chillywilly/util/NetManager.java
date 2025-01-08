package me.chillywilly.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import me.chillywilly.CameraPluginHelper;
import me.chillywilly.CameraPluginHelperClient;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.util.ScreenshotRecorder;
import net.minecraft.util.Util;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetManager {
    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(NetConst.SCREENSHOT_PACKET_ID, (client, handler, buf, responseSender) -> {
            byte[] data = buf.readByteArray();
            String url = new String(data, StandardCharsets.UTF_8);
            int auth = buf.readInt();
            CameraPluginHelperClient.taking_screenshot = true;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    client.execute(() -> {
                        NativeImage image = ScreenshotRecorder.takeScreenshot(MinecraftClient.getInstance().getFramebuffer());
                        File file = new File(MinecraftClient.getInstance().runDirectory, "screenshots" + File.separator + "camera-companion");
                        file.mkdirs();
                        File file2 = NetManager.getScreenshotFile(file);
        
                        Util.getIoWorkerExecutor().execute(() -> {
                            try {
                                image.writeTo(file2);
                                String endpoint = url + "/up_post";

                                
                                try {
                                    CameraPluginHelperClient.taking_screenshot = false;
                                    uploadFile(file2, endpoint, auth);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            
                            } catch (Exception e) {
                                CameraPluginHelper.LOGGER.info("Couldn't save screenshot: ", e);
                            } finally {
                                image.close();
                            }
                        });
                    });
                }
            }, 250);
        });

        ClientPlayNetworking.registerGlobalReceiver(NetConst.CHECK_FOR_COMPANION_ID, (client, handler, buf, responseSender) -> {
            CameraPluginHelper.LOGGER.info("Received companion check request, responding now");

            //sends response packet so the server knows that the client has the mod
            ClientPlayNetworking.send(NetConst.COMPANION_FOUND_ID, PacketByteBufs.empty());
        });
    }

    public static void uploadFile(File file, String URL, int auth) throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(file, MediaType.parse("image/png"));
        MultipartBody requestBody = new MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .addFormDataPart("files", file.getName(), body)
        .addFormDataPart("auth", String.valueOf(auth))
        .build();

        Request request = new Request.Builder()
        .url(URL)
        .post(requestBody)
        .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                CameraPluginHelper.LOGGER.info("Successfully uploaded image: " + file.getName());
            } else {
                CameraPluginHelper.LOGGER.warn("Upload Failed: " + response.code());
            }
        }
    }

    public static File getScreenshotFile(File directory) {
        String string = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date());
        int i = 1;
        File file;
        while ((file = new File(directory, string + (i == 1 ? "" : "_" + i) + ".png")).exists()) {
            i++;
        }
        return file;
    }
}
