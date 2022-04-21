package me.ivan1f.tweakerplus.util.doc;

import com.google.common.collect.ImmutableList;
import me.ivan1f.tweakerplus.TweakerPlusMod;
import me.ivan1f.tweakerplus.util.FabricUtil;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.LanguageDefinition;
import net.minecraft.client.resource.language.LanguageManager;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DocumentGeneration {
    public static final String DEFAULT_LANGUAGE = "en_us";
    private static final Path DOC_DIRECTORY = FabricLoader.getInstance().getGameDir().getParent().resolve("docs");
    public static final List<AbstractDocumentGenerator> GENERATORS = ImmutableList.of(
            new ConfigDocumentGenerator()
    );
    private static final IndexGenerator indexGenerator = new IndexGenerator();
    private static final List<String> languages = ImmutableList.of("en_us", "zh_cn");
    private static CompletableFuture<Void> completableFuture = null;

    public static IndexGenerator getIndexGenerator() {
        return indexGenerator;
    }

    private static CompletableFuture<Void> setLanguage(String lang) {
        TweakerPlusMod.LOGGER.info("Setting client language to {}", lang);
        LanguageManager languageManager = MinecraftClient.getInstance().getLanguageManager();
        LanguageDefinition languageDefinition = languageManager.getLanguage(lang);
        if (languageDefinition != languageManager.getLanguage()) {
            languageManager.setLanguage(languageDefinition);
            return MinecraftClient.getInstance().reloadResources();
        }
        return CompletableFuture.allOf();
    }

    private static CompletableFuture<Void> generateDocument(String lang) {
        return setLanguage(lang).thenRun(() -> {
            TweakerPlusMod.LOGGER.info("Generating TweakerPlus documents of language " + lang);
            indexGenerator.setLanguage(lang);
            for (AbstractDocumentGenerator generator : GENERATORS) {
                generator.setLanguage(lang);
                generator.generate();
                generator.toFile(DOC_DIRECTORY.resolve(generator.getFileName()));
                TweakerPlusMod.LOGGER.info("Generated: " + DOC_DIRECTORY.resolve(generator.getFileName()));
            }
            TweakerPlusMod.LOGGER.info("The index of language " + lang + " has been generated at " + DOC_DIRECTORY.resolve(indexGenerator.getFileName()));
            indexGenerator.toFile(DOC_DIRECTORY.resolve(indexGenerator.getFileName()));
        });
    }

    @SuppressWarnings("unchecked")
    public static void generateDocuments(boolean exit) {
        if (completableFuture == null) {
            completableFuture = new CompletableFuture<>();  // assign to any value first to prevent hotkey spam

            TweakerPlusMod.LOGGER.info("Generating doc...");
            String prevLang = MinecraftClient.getInstance().options.language;

            CompletableFuture<Void>[] futures = new CompletableFuture[languages.size() + 1];
            for (int i = 0; i < futures.length; i++) {
                futures[i] = new CompletableFuture<>();
            }
            for (int i = 0; i < languages.size(); i++) {
                final int finalI = i;
                futures[i].thenRun(
                        () -> generateDocument(languages.get(finalI)).thenRun(
                                () -> futures[finalI + 1].complete(null)
                        )
                );
            }
            futures[languages.size()].thenRun(() -> {
                TweakerPlusMod.LOGGER.info("Restoring language back to {}", prevLang);
                setLanguage(prevLang).thenRun(() -> {
                    completableFuture.complete(null);
                    completableFuture = null;
                    TweakerPlusMod.LOGGER.info("Doc generating done");
                    if (exit) {
                        TweakerPlusMod.LOGGER.info("Stopping Minecraft client");
                        MinecraftClient.getInstance().scheduleStop();
                    }
                });
            });

            futures[0].complete(null);  // start the logic
        }
    }

    public static void onClientInitFinished() {
        // -Dtweakerplus.gen_doc=true
        if (FabricUtil.isDevelopmentEnvironment() && "true".equals(System.getProperty(TweakerPlusMod.MOD_ID + ".gen_doc"))) {
            TweakerPlusMod.LOGGER.info("Starting TweakerPlus automatic doc generating");
            generateDocuments(true);
        }
    }
}
