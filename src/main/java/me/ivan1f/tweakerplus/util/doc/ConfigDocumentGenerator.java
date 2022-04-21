package me.ivan1f.tweakerplus.util.doc;

import com.google.common.collect.Lists;
import me.ivan1f.tweakerplus.config.Config;
import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import me.ivan1f.tweakerplus.config.TweakerPlusOption;

import java.util.List;

import static me.ivan1f.tweakerplus.util.doc.MarkdownUtil.inlineCode;

public class ConfigDocumentGenerator extends AbstractDocumentGenerator {
    public void generate() {
        for (Config.Category category: Config.Category.values()) {
            DocumentGeneration.getIndexGenerator().startNewSection(String.format("[%s](%s)", category.getDisplayName(), this.getFileName() + "#" + category.getDisplayName()));
            this.writeln.accept("## " + category.getDisplayName());
            this.writeln.accept("");
            List<TweakerPlusOption> options = Lists.newArrayList(TweakerPlusConfigs.getOptions(category));
            for (TweakerPlusOption option : options) {
                ConfigFormatter formatter = new ConfigFormatter(option);
                this.writeln.accept("### " + formatter.getName());
                this.writeln.accept("");
                this.writeln.accept(formatter.getComment());
                this.writeln.accept("");
                this.writeln.accept(String.format(" - %s: %s", this.tr("text.type"), inlineCode(formatter.getType())));
                this.writeln.accept(String.format(" - %s: %s", this.tr("text.default_value"), formatter.getDefaultValue()));
                this.writeln.accept(String.format(" - %s: %s", this.tr("text.category"), inlineCode(formatter.getCategory())));
                this.writeln.accept("");
                DocumentGeneration.getIndexGenerator().accept(String.format("[%s](%s)", formatter.getNameSimple(), formatter.getLink(this.getLanguage())));
            }
            DocumentGeneration.getIndexGenerator().acceptRaw("");
        }
    }

    @Override
    public String getHeader() {
        return this.tr("document");
    }

    @Override
    public String getFileName(String lang) {
        if (lang.equals(DocumentGeneration.DEFAULT_LANGUAGE)) {
            return "document.md";
        }
        return String.format("document-%s.md", lang);
    }
}
