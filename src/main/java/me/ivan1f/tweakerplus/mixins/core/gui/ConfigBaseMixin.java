package me.ivan1f.tweakerplus.mixins.core.gui;

import com.google.common.base.Joiner;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.options.ConfigBase;
import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ConfigBase.class)
public abstract class ConfigBaseMixin {
    @Shadow(remap = false)
    private String comment;

    @ModifyArg(
            method = "getComment",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/malilib/util/StringUtils;getTranslatedOrFallback(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;"
            ),
            index = 0,
            remap = false
    )
    private String tweakerPlusConfigCommentIsTheTranslationKey(String key) {
        if (TweakerPlusConfigs.hasConfig((IConfigBase) this)) {
            key = this.comment;
        }
        return key;
    }

    @Inject(method = "getComment", at = @At("TAIL"), cancellable = true, remap = false)
    private void appendModRequirementHeader$tweakerplus(CallbackInfoReturnable<String> cir) {
        TweakerPlusConfigs.getOptionFromConfig((IConfigBase) this).ifPresent(tweakerPlusOption -> {
            List<String> footers = tweakerPlusOption.getModRelationsFooter();
            if (!footers.isEmpty()) {
                cir.setReturnValue(cir.getReturnValue() + "\n" + Joiner.on("\n").join(footers));
            }
        });
    }
}
