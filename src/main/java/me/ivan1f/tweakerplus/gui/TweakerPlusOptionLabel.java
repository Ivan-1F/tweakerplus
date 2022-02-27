package me.ivan1f.tweakerplus.gui;

import fi.dy.masa.malilib.gui.widgets.WidgetLabel;
import fi.dy.masa.malilib.util.StringUtils;
import me.ivan1f.tweakerplus.TweakerPlusMod;
import me.ivan1f.tweakerplus.util.StringUtil;
import me.ivan1f.tweakerplus.mixins.core.gui.WidgetLabelMixin;

import java.util.Arrays;
import java.util.function.Function;

/**
 * Only works perfectly with 1 line tho
 * See {@link WidgetLabelMixin} for more details
 */
public class TweakerPlusOptionLabel extends WidgetLabel {
    public static final double TRANSLATION_SCALE = 0.65;
    private final String[] originalLines;
    private final boolean showOriginalLines;

    public TweakerPlusOptionLabel(int x, int y, int width, int height, int textColor, String[] displayLines, String[] originalLines, Function<String, String> lineModifier) {
        super(x, y, width, height, textColor, displayLines);
        this.originalLines = originalLines;
        boolean showOriginalLines = false;
        for (int i = 0; i < this.originalLines.length; i++) {
            String linesToDisplay = this.labels.get(i);
            if (!this.originalLines[i].equals(StringUtil.removeFormattingCode(linesToDisplay))) {
                showOriginalLines = true;
            }
            this.labels.set(i, lineModifier.apply(linesToDisplay));
        }
        this.showOriginalLines = showOriginalLines;
        if (this.showOriginalLines != willShowOriginalLines(displayLines, originalLines)) {
            TweakerPlusMod.LOGGER.warn("Inconsistent showOriginalLines result: {} {}", this.showOriginalLines, willShowOriginalLines(displayLines, originalLines));
        }
    }

    public static boolean willShowOriginalLines(String[] displayLines, String[] originalLines) {
        return !Arrays.equals(
                originalLines,
                Arrays.stream(displayLines).
                        map(StringUtils::translate).
                        map(StringUtil::removeFormattingCode).
                        toArray(String[]::new)
        );
    }

    public String[] getOriginalLines() {
        return this.originalLines;
    }

    public boolean shouldShowOriginalLines() {
        return this.showOriginalLines;
    }
}
