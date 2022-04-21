package me.ivan1f.tweakerplus.mixins.core.doc;

import fi.dy.masa.malilib.config.options.ConfigBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ConfigBase.class)
public interface ConfigBaseAccessor {
    @Accessor(value = "comment", remap = false)
    String getCommentKey();
}
