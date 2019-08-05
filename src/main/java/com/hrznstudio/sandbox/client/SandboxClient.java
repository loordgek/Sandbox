package com.hrznstudio.sandbox.client;

import com.hrznstudio.sandbox.SandboxCommon;
import com.hrznstudio.sandbox.event.EventDispatcher;
import com.hrznstudio.sandbox.util.Log;
import net.minecraft.client.MinecraftClient;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.scheduler.Schedulers;

public class SandboxClient extends SandboxCommon {
    public static SandboxClient INSTANCE;

    private SandboxClient() {
        INSTANCE = this;
    }

    public static SandboxClient constructAndSetup() {
        SandboxClient client = new SandboxClient();
        client.setup();
        return client;
    }

    @Override
    protected void setup() {
        CONTENT_LIST.clear();
        //Init client engine
//        MinecraftClient.getInstance().setOverlay(new LoadingOverlay(
//                MinecraftClient.getInstance(),
//                new AddonLoadingMonitor(),
//                () -> {
//                },
//                false
//        ));
        Log.info("Setting up Clientside Sandbox environment");
        dispatcher = new EventDispatcher(
                EmitterProcessor.create(),
                Schedulers.immediate() // Client events are blocking
        );
        MinecraftClient.getInstance().reloadResourcesConcurrently();
//        Gamemode currentMode = VanillaGamemodes.SURVIVAL;
//        DiscordRPC.discordUpdatePresence(new DiscordRichPresence.Builder("In Private Session")
//                .setBigImage(currentMode.getRichImage().orElse("logo"), String.format("Playing %s", currentMode.getDisplayName().orElse(currentMode.getName())))
//                .setSecrets("wah", "")
//                .setParty("wah2", 5, 12)
//                .setStartTimestamps(System.currentTimeMillis() / 1000)
//                .setDetails("Playing on 'world'")
//                .build()
//        );
    }

    @Override
    public void shutdown() {
        super.shutdown();
        INSTANCE = null;
    }
}