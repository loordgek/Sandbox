package com.hrznstudio.sandbox.client;

import com.hrznstudio.sandbox.SandboxCommon;
import com.hrznstudio.sandbox.api.util.Side;
import com.hrznstudio.sandbox.client.overlay.LoadingOverlay;
import com.hrznstudio.sandbox.event.EventDispatcher;
import com.hrznstudio.sandbox.loader.SandboxLoader;
import com.hrznstudio.sandbox.util.Log;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Pair;

import java.nio.file.Path;
import java.util.List;

public class SandboxClient extends SandboxCommon {
    public static SandboxClient INSTANCE;
    private SandboxLoader loader;

    private SandboxClient() {
        INSTANCE = this;
    }

    public static SandboxClient constructAndSetup() {
        SandboxClient client = new SandboxClient();
        client.setup();
        return client;
    }

    @Override
    public Side getSide() {
        return Side.CLIENT;
    }

    @Override
    protected void setup() {
        //Init client engine
//        MinecraftClient.getInstance().setOverlay(new LoadingOverlay(
//                MinecraftClient.getInstance(),
//                new AddonLoadingMonitor(),
//                () -> {
//                },
//                false
//        ));
        Log.info("Setting up Clientside Sandbox environment");
        dispatcher = new EventDispatcher();
//        MinecraftClient.getInstance().reloadResourcesConcurrently();
        DiscordRPC.discordUpdatePresence(new DiscordRichPresence.Builder("In Private Session")
                .setBigImage("gm_debug", String.format("Playing %s", "Debug"))
                .setSecrets("wah", "")
                .setParty("wah2", 5, 12)
                .setStartTimestamps(System.currentTimeMillis() / 1000)
                .setDetails("Playing on 'world'")
                .build()
        );
    }

    @Override
    public void shutdown() {
        INSTANCE = null;
    }

    public void open(String prefix, List<Pair<String, String>> addons) {
        MinecraftClient.getInstance().setOverlay(new LoadingOverlay(
                MinecraftClient.getInstance(),
                prefix,
                addons
        ));
    }

    public void load(List<Path> addons) {
        loader = new SandboxLoader(this, addons);
        loader.load();
        MinecraftClient.getInstance().worldRenderer.reload();
        MinecraftClient.getInstance().reloadResourcesConcurrently();
    }
}