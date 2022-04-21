package me.ivan1f.tweakerplus.util.doc;

import java.util.List;
import java.util.stream.Collectors;

public class MarkdownUtil {
    public static String inlineCode(String text) {
        return "`" + text + "`";
    }

    public static List<String> inlineCode(List<String> text) {
        return text.stream().map(MarkdownUtil::inlineCode).collect(Collectors.toList());
    }

    public static String italic(String text) {
        return "*" + text + "*";
    }
}
