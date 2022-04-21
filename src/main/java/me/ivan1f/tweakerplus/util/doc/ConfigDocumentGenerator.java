package me.ivan1f.tweakerplus.util.doc;

import com.google.common.collect.Lists;
import fi.dy.masa.malilib.util.StringUtils;
import me.ivan1f.tweakerplus.config.Config;
import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import me.ivan1f.tweakerplus.config.TweakerPlusOption;
import me.ivan1f.tweakerplus.util.condition.ModPredicate;
import me.ivan1f.tweakerplus.util.condition.ModRestriction;

import java.util.List;

import static me.ivan1f.tweakerplus.util.doc.MarkdownUtil.inlineCode;

public class ConfigDocumentGenerator extends AbstractDocumentGenerator {
    public void generate() {
        for (Config.Category category : Config.Category.values()) {
            DocumentGeneration.getIndexGenerator().startNewSection(String.format("[%s](%s)", category.getDisplayName(), this.getFileName() + "#" + category.getDisplayName().replace(" ", "-").toLowerCase()));
            this.writeln.accept("## " + category.getDisplayName());
            this.writeln.accept("");
            this.writeln.accept(category.getDescription());
            this.writeln.accept("");
            List<TweakerPlusOption> options = Lists.newArrayList(TweakerPlusConfigs.getOptions(category));
            for (TweakerPlusOption option : options) {
                ConfigFormatter formatter = new ConfigFormatter(option);
                this.writeln.accept("### " + formatter.getName());
                this.writeln.accept("");
                this.writeln.accept(formatter.getComment());
                this.writeln.accept("");
                this.writeln.accept(String.format(" - %s: %s", this.tr("type"), inlineCode(formatter.getType())));
                this.writeln.accept(String.format(" - %s: %s", this.tr("default_value"), formatter.getDefaultValue()));
                this.writeln.accept(String.format(" - %s: %s", this.tr("category"), inlineCode(formatter.getCategory())));
                formatter.getMinValue().ifPresent(min -> writeln.accept(String.format(" - %s: `%s`", tr("minimum_value"), min)));
                formatter.getMaxValue().ifPresent(max -> writeln.accept(String.format(" - %s: `%s`", tr("maximum_value"), max)));

                List<ModRestriction> modRestrictions = option.getModRestrictions();
                if (!modRestrictions.isEmpty()) {
                    writeln.accept(String.format("- %s:", tr("mod_restrictions")));
                    boolean first = true;
                    for (ModRestriction modRestriction : modRestrictions) {
                        if (!first) {
                            writeln.accept("");
                            writeln.accept(String.format("  *%s*", StringUtils.translate("tweakerplus.gui.mod_relation_footer.or")));
                            writeln.accept("");
                        }
                        first = false;
                        if (!modRestriction.getRequirements().isEmpty()) {
                            writeln.accept(String.format("  - %s:", tr("requirements")));
                            modRestriction.getRequirements().forEach(req -> writeln.accept(String.format("    - %s", prettyPredicate(req))));
                        }
                        if (!modRestriction.getConflictions().isEmpty()) {
                            writeln.accept(String.format("  - %s:", tr("conflictions")));
                            modRestriction.getConflictions().forEach(cfl -> writeln.accept(String.format("    - %s", prettyPredicate(cfl))));
                        }
                    }
                }

                this.writeln.accept("");
                DocumentGeneration.getIndexGenerator().accept(String.format("[%s](%s)", formatter.getNameSimple(), formatter.getLink(this.getLanguage())));
            }
            DocumentGeneration.getIndexGenerator().acceptRaw("");
        }
    }

    private static String prettyPredicate(ModPredicate modPredicate) {
        String ret = String.format("%s (`%s`)", StringUtils.translate("tweakerplus.util.mod." + modPredicate.modId), modPredicate.modId);
        String predicate = modPredicate.getVersionPredicatesString();
        if (!predicate.isEmpty()) {
            ret += " " + inlineCode(predicate);
        }
        return ret;
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
