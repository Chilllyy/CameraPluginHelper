package me.chillywilly.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import me.chillywilly.CameraPluginHelper;
import me.chillywilly.CameraPluginHelperClient;
import me.chillywilly.util.packets.rx.CompanionCheckPacket;
import me.chillywilly.util.packets.rx.ScreenshotPacket;
import me.chillywilly.util.packets.tx.CompanionFoundPacket;
import me.chillywilly.util.packets.tx.ScreenshotTakenPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Screenshot;
import net.minecraft.client.gui.screens.Overlay;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.codec.StreamCodec;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetManager {
    public static void init() {
        //Receive
		PayloadTypeRegistry.clientboundPlay().register(ScreenshotPacket.TYPE, ScreenshotPacket.CODEC);
		PayloadTypeRegistry.clientboundPlay().register(CompanionCheckPacket.TYPE, StreamCodec.unit(new CompanionCheckPacket()));

		//Send
		PayloadTypeRegistry.serverboundPlay().register(CompanionFoundPacket.TYPE, StreamCodec.unit(new CompanionFoundPacket()));
		PayloadTypeRegistry.serverboundPlay().register(ScreenshotTakenPacket.TYPE, StreamCodec.unit(new ScreenshotTakenPacket()));

        
        ClientPlayNetworking.registerGlobalReceiver(CompanionCheckPacket.TYPE, (payload, context) -> {
            context.client().execute(() -> {
                CameraPluginHelper.LOGGER.info("Received Companion request!");

                CameraPluginHelper.LOGGER.info("Sending Response now!");

                ClientPlayNetworking.send(new CompanionFoundPacket());
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(ScreenshotPacket.TYPE, (payload, context) -> {
            context.client().execute(() -> {
                String url = payload.URL();
                int auth = payload.auth();
                CameraPluginHelper.LOGGER.info("Received Screenshot Packet!: " + payload.URL() + " with auth " + payload.auth());
                Minecraft instance = Minecraft.getInstance();

                Screen screen = instance.screen;
                Overlay overlay = instance.getOverlay();
                boolean pause = instance.options.pauseOnLostFocus;
                boolean hideUI = instance.options.hideGui;

                instance.options.pauseOnLostFocus = false;
                instance.options.hideGui = true;
                instance.setScreen(null);
                instance.setOverlay(null);

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        context.client().execute(() -> {
                            Screenshot.takeScreenshot(Minecraft.getInstance().getMainRenderTarget(), callback -> {
                                File file = new File(Minecraft.getInstance().gameDirectory, "screenshots" + File.separator + "camera-companion");
                                file.mkdirs();
                                File file2 = NetManager.getScreenshotFile(file);
                                try {
                                    callback.writeToFile(file2);
                                    String endpoint = url + "/up_post";

                                    uploadFile(file2, endpoint, auth);
                                } catch(IOException e) {
                                    CameraPluginHelper.LOGGER.warn("Unable to upload screenshot or save file {}", e.getMessage(), e);
                                } finally {
                                    callback.close();
                                    instance.setScreen(screen);
                                    instance.setOverlay(overlay);
                                    instance.options.pauseOnLostFocus = pause;
                                    instance.options.hideGui = hideUI;
                                }
                            });
                        });
                    }
                }, 250);
            });
        });
    }

    public static void uploadFile(File file, String URL, int auth) throws IOException {
        CameraPluginHelper.LOGGER.info("Uploading file to URL: " + URL + " with auth code " + auth);
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
                file.delete();
                ClientPlayNetworking.send(new ScreenshotTakenPacket());
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
