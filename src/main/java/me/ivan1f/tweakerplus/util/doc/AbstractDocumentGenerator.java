package me.ivan1f.tweakerplus.util.doc;

import fi.dy.masa.malilib.util.StringUtils;
import me.ivan1f.tweakerplus.TweakerPlusMod;
import me.ivan1f.tweakerplus.util.FileUtil;
//import net.minecraft.text.TranslatableText;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Consumer;

public abstract class AbstractDocumentGenerator {
    protected final StringBuffer buffer = new StringBuffer();
    protected final Consumer<String> writeln = line -> this.buffer.append(line).append("\n");
    protected String language = DocumentGeneration.DEFAULT_LANGUAGE;

    public AbstractDocumentGenerator() {
        this.prepare();
    }

    public String tr(String key, Object... args) {
        return StringUtils.translate("tweakerplus.doc_gen.text." + key, args);
    }

    public void setLanguage(String language) {
        this.language = language;
        this.buffer.setLength(0);
        this.prepare();
    }

    public void prepare() {
        this.writeHeader();
        this.writeLanguageSwitcher();
    }

    public void writeLanguageSwitcher() {
        if (this.getLanguage().equals("en_us")) {
            this.writeln.accept(String.format("**English** | [中文](%s)", this.getFileName("zh_cn")));
        } else {
            this.writeln.accept(String.format("[English](%s) | **中文**", this.getFileName("en_us")));
        }
        this.writeln.accept("");
    }

    public String getLanguage() {
        return language;
    }

    abstract public void generate();

    public String getFileName() {
        return this.getFileName(this.getLanguage());
    }

    abstract public String getHeader();
    private void writeHeader() {
        this.writeln.accept("# " + this.getHeader());
        this.writeln.accept("");
    }

    abstract public String getFileName(String lang);

    public void toFile(Path path) {
        try {
            FileUtil.writeToFile(path, this.buffer.toString());
        } catch (IOException e) {
            TweakerPlusMod.LOGGER.error(e);
        }
    }
}
