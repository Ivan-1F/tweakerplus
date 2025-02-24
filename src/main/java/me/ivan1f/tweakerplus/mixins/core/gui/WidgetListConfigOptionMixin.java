package me.ivan1f.tweakerplus.mixins.core.gui;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.ConfigButtonKeybind;
import fi.dy.masa.malilib.gui.interfaces.IKeybindConfigGui;
import fi.dy.masa.malilib.gui.widgets.*;
import fi.dy.masa.malilib.hotkeys.*;
import fi.dy.masa.malilib.util.StringUtils;
import me.ivan1f.tweakerplus.config.options.TweakerPlusIConfigBase;
import me.ivan1f.tweakerplus.gui.TweakerPlusConfigGui;
import me.ivan1f.tweakerplus.gui.TweakerPlusOptionLabel;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

//#if MC >= 11800
//$$ import fi.dy.masa.malilib.config.IConfigBoolean;
//$$ import fi.dy.masa.malilib.config.IConfigResettable;
//$$ import org.spongepowered.asm.mixin.injection.*;
//#else
import fi.dy.masa.malilib.config.IHotkeyTogglable;
import fi.dy.masa.malilib.config.gui.ConfigOptionChangeListenerButton;
import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;
import fi.dy.masa.malilib.gui.button.ConfigButtonBoolean;
import me.ivan1f.tweakerplus.config.TweakerPlusConfigs;
import me.ivan1f.tweakerplus.gui.HotkeyedBooleanResetListener;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.Objects;
//#endif

import java.util.function.Function;

@Mixin(value = WidgetConfigOption.class)
public abstract class WidgetListConfigOptionMixin extends WidgetConfigOptionBase<GuiConfigsBase.ConfigOptionWrapper> {
    @Shadow(remap = false)
    @Final
    protected IKeybindConfigGui host;

    //#if MC < 11800
    @Shadow(remap = false)
    @Final
    protected GuiConfigsBase.ConfigOptionWrapper wrapper;

    @Mutable
    @Shadow(remap = false)
    @Final
    protected KeybindSettings initialKeybindSettings;
    //#endif

    @Shadow(remap = false)
    protected abstract void addKeybindResetButton(int x, int y, IKeybind keybind, ConfigButtonKeybind buttonHotkey);

    @Unique
    private boolean initialBoolean;

    public WidgetListConfigOptionMixin(int x, int y, int width, int height, WidgetListConfigOptionsBase<?, ?> parent, GuiConfigsBase.ConfigOptionWrapper entry, int listIndex) {
        super(x, y, width, height, parent, entry, listIndex);
    }

    private boolean isTweakerPlusConfigGui() {
        return this.parent instanceof WidgetListConfigOptions && ((WidgetListConfigOptionsAccessor) this.parent).getParent() instanceof TweakerPlusConfigGui;
    }

    //#if MC < 11800
    /**
     * Stolen from malilib 1.18 v0.11.4
     * to make compact ConfigBooleanHotkeyed option panel works
     */
    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    private void initInitialState$tweakerplus(CallbackInfo ci) {
        if (isTweakerPlusConfigGui() && this.wrapper.getType() == GuiConfigsBase.ConfigOptionWrapper.Type.CONFIG) {
            IConfigBase config = wrapper.getConfig();
            if (config instanceof ConfigBooleanHotkeyed) {
                this.initialBoolean = ((ConfigBooleanHotkeyed) config).getBooleanValue();
                this.initialStringValue = ((ConfigBooleanHotkeyed) config).getKeybind().getStringValue();
                this.initialKeybindSettings = ((ConfigBooleanHotkeyed) config).getKeybind().getSettings();
            }
        }
    }
    //#endif

    private boolean showOriginalTextsThisTime;

    @ModifyArgs(
            method = "addConfigOption",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/malilib/gui/widgets/WidgetConfigOption;addLabel(IIIII[Ljava/lang/String;)V",
                    remap = false
            ),
            remap = false
    )
    private void useMyBetterOptionLabelForTweakerPlus(Args args, int x_, int y_, float zLevel, int labelWidth, int configWidth, IConfigBase config) {
        if (isTweakerPlusConfigGui()) {
            int x = args.get(0);
            int y = args.get(1);
            int width = args.get(2);
            int height = args.get(3);
            int textColor = args.get(4);
            String[] lines = args.get(5);
            if (lines == null || lines.length != 1) {
                return;
            }

            args.set(5, null);  // cancel original call

            Function<String, String> modifier = s -> s;
            if (config instanceof TweakerPlusIConfigBase) {
                modifier = ((TweakerPlusIConfigBase) config).getGuiDisplayLineModifier();
            }
            TweakerPlusOptionLabel label = new TweakerPlusOptionLabel(x, y, width, height, textColor, lines, new String[]{config.getName()}, modifier);
            this.addWidget(label);
            this.showOriginalTextsThisTime = label.shouldShowOriginalLines();
        } else {
            this.showOriginalTextsThisTime = false;
        }
    }

    @ModifyArg(
            method = "addConfigOption",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/malilib/gui/widgets/WidgetConfigOption;addConfigComment(IIIILjava/lang/String;)V",
                    remap = false
            ),
            index = 1,
            remap = false
    )
    private int tweaksCommentHeight_minY$tweakerplus(int y) {
        if (this.showOriginalTextsThisTime) {
            y -= 4;
        }
        return y;
    }

    @ModifyArg(
            method = "addConfigOption",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/malilib/gui/widgets/WidgetConfigOption;addConfigComment(IIIILjava/lang/String;)V",
                    remap = false
            ),
            index = 3,
            remap = false
    )
    private int tweaksCommentHeight_height$tweakerplus(int height) {
        if (this.showOriginalTextsThisTime) {
            height += 6;
        }
        return height;
    }

    @Inject(
            //#if MC >= 11800
            //$$ method = "addHotkeyConfigElements",
            //$$ at = @At(value = "HEAD"),
            //#else
            method = "addConfigOption",
            at = @At(
                    value = "FIELD",
                    target = "Lfi/dy/masa/malilib/config/ConfigType;BOOLEAN:Lfi/dy/masa/malilib/config/ConfigType;",
                    remap = false
            ),
            //#endif
            remap = false,
            cancellable = true
    )
    private void tweakerPlusCustomConfigGui(
            //#if MC >= 11800
            //$$ int x, int y, int configWidth, String configName, IHotkey config, CallbackInfo ci
            //#else
            int x, int y, float zLevel, int labelWidth, int configWidth, IConfigBase config, CallbackInfo ci
            //#endif
    ) {
        //#if MC >= 11800
        //$$ if (this.isTweakerPlusConfigGui()) {
        //$$     if ((config).getKeybind() instanceof KeybindMulti) {
        //$$         this.addButtonAndHotkeyWidgets$tweakerplus(x, y, configWidth, config);
        //$$         ci.cancel();
        //$$     }
        //$$ }
        //#else
        if (this.isTweakerPlusConfigGui() && config instanceof IHotkey) {
            boolean modified = true;
            if (config instanceof IHotkeyTogglable) {
                this.addBooleanAndHotkeyWidgets$tweakerplus(x, y, configWidth, (IHotkeyTogglable) config);
            } else if (((IHotkey) config).getKeybind() instanceof KeybindMulti) {
                this.addButtonAndHotkeyWidgets$tweakerplus(x, y, configWidth, (IHotkey) config);
            } else {
                modified = false;
            }
            if (modified) {
                ci.cancel();
            }
        }
        //#endif
    }

    private void addButtonAndHotkeyWidgets$tweakerplus(int x, int y, int configWidth, IHotkey config) {
        IKeybind keybind = config.getKeybind();

        int triggerBtnWidth = (configWidth - 24) / 2;
        ButtonGeneric triggerButton = new ButtonGeneric(
                x, y, triggerBtnWidth, 20,
                StringUtils.translate("tweakerplus.gui.trigger_button.text"),
                StringUtils.translate("tweakerplus.gui.trigger_button.hover", config.getName())
        );
        this.addButton(triggerButton, (button, mouseButton) -> {
            IHotkeyCallback callback = ((KeybindMultiAccessor) keybind).getCallback();
            KeyAction activateOn = keybind.getSettings().getActivateOn();
            if (activateOn == KeyAction.BOTH || activateOn == KeyAction.PRESS) {
                callback.onKeyAction(KeyAction.PRESS, keybind);
            }
            if (activateOn == KeyAction.BOTH || activateOn == KeyAction.RELEASE) {
                callback.onKeyAction(KeyAction.RELEASE, keybind);
            }
        });

        x += triggerBtnWidth + 2;
        configWidth -= triggerBtnWidth + 2 + 22;

        ConfigButtonKeybind keybindButton = new ConfigButtonKeybind(x, y, configWidth, 20, keybind, this.host);
        x += configWidth + 2;

        this.addWidget(new WidgetKeybindSettings(x, y, 20, 20, keybind, config.getName(), this.parent, this.host.getDialogHandler()));
        //#if MC >= 11800
        //$$ x += 22;
        //#else
        x += 24;
        //#endif

        this.addButton(keybindButton, this.host.getButtonPressListener());
        this.addKeybindResetButton(x, y, keybind, keybindButton);
    }

    //#if MC >= 11800
    //$$ @ModifyVariable(
    //$$         method = "addBooleanAndHotkeyWidgets",
    //$$         at = @At(
    //$$                 value = "STORE",
    //$$                 ordinal = 0
    //$$         ),
    //$$         ordinal = 3,
    //$$         remap = false
    //$$ )
    //$$ private int tweakerPlusDynamicBooleanButtonWidth(int booleanBtnWidth, int x, int y, int configWidth, IConfigResettable resettableConfig, IConfigBoolean booleanConfig, IKeybind keybind) {
    //$$     if (this.isTweakerPlusConfigGui()) {
    //$$         booleanBtnWidth = (configWidth - 24) / 2;
    //$$     }
    //$$     return booleanBtnWidth;
    //$$ }
    //#else
    private void addBooleanAndHotkeyWidgets$tweakerplus(int x, int y, int configWidth, IHotkeyTogglable config) {
        IKeybind keybind = config.getKeybind();

        int booleanBtnWidth = (configWidth - 24) / 2;
        ConfigButtonBoolean booleanButton = new ConfigButtonBoolean(x, y, booleanBtnWidth, 20, config);
        x += booleanBtnWidth + 2;
        configWidth -= booleanBtnWidth + 2 + 22;

        ConfigButtonKeybind keybindButton = new ConfigButtonKeybind(x, y, configWidth, 20, keybind, this.host);
        x += configWidth + 2;

        this.addWidget(new WidgetKeybindSettings(x, y, 20, 20, keybind, config.getName(), this.parent, this.host.getDialogHandler()));
        x += 24;

        ButtonGeneric resetButton = this.createResetButton(x, y, config);

        ConfigOptionChangeListenerButton booleanChangeListener = new ConfigOptionChangeListenerButton(config, resetButton, null);
        HotkeyedBooleanResetListener resetListener = new HotkeyedBooleanResetListener(config, booleanButton, keybindButton, resetButton, this.host);

        this.host.addKeybindChangeListener(resetListener);

        this.addButton(booleanButton, booleanChangeListener);
        this.addButton(keybindButton, this.host.getButtonPressListener());
        this.addButton(resetButton, resetListener);
    }

    /**
     * Stolen from malilib 1.18 v0.11.4
     * to make compact ConfigBooleanHotkeyed option panel works
     */
    @Inject(
            method = "wasConfigModified",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/malilib/gui/GuiConfigsBase$ConfigOptionWrapper;getConfig()Lfi/dy/masa/malilib/config/IConfigBase;",
                    ordinal = 0,
                    remap = false
            ),
            cancellable = true,
            remap = false
    )
    private void specialJudgeCustomConfigBooleanHotkeyed$tweakerplus(CallbackInfoReturnable<Boolean> cir) {
        IConfigBase config = this.wrapper.getConfig();
        if (config instanceof ConfigBooleanHotkeyed && TweakerPlusConfigs.hasConfig(config)) {
            ConfigBooleanHotkeyed booleanHotkey = (ConfigBooleanHotkeyed) config;
            IKeybind keybind = booleanHotkey.getKeybind();
            cir.setReturnValue(
                    this.initialBoolean != booleanHotkey.getBooleanValue() ||
                            !Objects.equals(this.initialStringValue, keybind.getStringValue()) ||
                            !Objects.equals(this.initialKeybindSettings, keybind.getSettings())
            );
        }
    }

    // some ocd alignment things xd

    @ModifyArg(
            method = "addConfigOption",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/malilib/gui/widgets/WidgetKeybindSettings;<init>(IIIILfi/dy/masa/malilib/hotkeys/IKeybind;Ljava/lang/String;Lfi/dy/masa/malilib/gui/widgets/WidgetListBase;Lfi/dy/masa/malilib/gui/interfaces/IDialogHandler;)V",
                    remap = false
            ),
            index = 0,
            remap = false
    )
    private int whyNotAlignTheHotkeyConfigButtonWidthWithOthers$tweakerplus(int x) {
        if (isTweakerPlusConfigGui()) {
            x += 1;
        }
        return x;
    }

    @ModifyArg(
            method = "addConfigOption",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/malilib/gui/button/ConfigButtonKeybind;<init>(IIIILfi/dy/masa/malilib/hotkeys/IKeybind;Lfi/dy/masa/malilib/gui/interfaces/IKeybindConfigGui;)V",
                    remap = false
            ),
            index = 2,
            remap = false
    )
    private int whyNotAlignTheHotkeySetterButtonWidthWithOthers$tweakerplus(int width) {
        if (isTweakerPlusConfigGui()) {
            width += 3;
        }
        return width;
    }
    //#endif
}
