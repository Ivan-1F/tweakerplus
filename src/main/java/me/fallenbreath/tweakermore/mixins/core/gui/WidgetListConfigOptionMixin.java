package me.fallenbreath.tweakermore.mixins.core.gui;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.ConfigButtonKeybind;
import fi.dy.masa.malilib.gui.interfaces.IKeybindConfigGui;
import fi.dy.masa.malilib.gui.widgets.*;
import fi.dy.masa.malilib.hotkeys.*;
import fi.dy.masa.malilib.util.StringUtils;
import me.fallenbreath.tweakermore.config.TweakerMoreConfigs;
import me.fallenbreath.tweakermore.config.options.TweakerMoreIConfigBase;
import me.fallenbreath.tweakermore.gui.TweakerMoreConfigGui;
import me.fallenbreath.tweakermore.gui.TweakerMoreOptionLabel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.function.Function;

@Mixin(WidgetConfigOption.class)
public abstract class WidgetListConfigOptionMixin extends WidgetConfigOptionBase<GuiConfigsBase.ConfigOptionWrapper>
{
	@Shadow(remap = false) @Final protected IKeybindConfigGui host;

	@Shadow(remap = false) protected abstract void addKeybindResetButton(int x, int y, IKeybind keybind, ConfigButtonKeybind buttonHotkey);

	@Unique
	private boolean initialBoolean;

	public WidgetListConfigOptionMixin(int x, int y, int width, int height, WidgetListConfigOptionsBase<?, ?> parent, GuiConfigsBase.ConfigOptionWrapper entry, int listIndex)
	{
		super(x, y, width, height, parent, entry, listIndex);
	}

	private boolean isTweakerMoreConfigGui()
	{
		return this.parent instanceof WidgetListConfigOptions && ((WidgetListConfigOptionsAccessor)this.parent).getParent() instanceof TweakerMoreConfigGui;
	}

	@ModifyVariable(
			method = "addConfigOption",
			at = @At("HEAD"),
			argsOnly = true,
			index = 4,
			remap = false
	)
	private int rightAlignedConfigPanel(int labelWidth, int x, int y, float zLevel, int labelWidth_, int configWidth, IConfigBase config)
	{
		if (isTweakerMoreConfigGui())
		{
			labelWidth = this.width - configWidth - 59;
		}
		return labelWidth;
	}

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
	private void useMyBetterOptionLabelForTweakerMore(Args args, int x_, int y_, float zLevel, int labelWidth, int configWidth, IConfigBase config)
	{
		if (isTweakerMoreConfigGui() || TweakerMoreConfigs.APPLY_TWEAKERMORE_OPTION_LABEL_GLOBALLY.getBooleanValue())
		{
			int x = args.get(0);
			int y = args.get(1);
			int width = args.get(2);
			int height = args.get(3);
			int textColor = args.get(4);
			String[] lines = args.get(5);
			if (lines.length != 1)
			{
				return;
			}

			args.set(5, null);  // cancel original call

			Function<String, String> modifier = s -> s;
			if (config instanceof TweakerMoreIConfigBase && !((TweakerMoreIConfigBase)config).isEnabled())
			{
				modifier = TweakerMoreIConfigBase::modifyDisabledOptionLabelLine;
			}
			TweakerMoreOptionLabel label = new TweakerMoreOptionLabel(x, y, width, height, textColor, lines, new String[]{config.getName()}, modifier);
			this.addWidget(label);
			this.showOriginalTextsThisTime = label.shouldShowOriginalLines();
		}
		else
		{
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
	private int tweaksCommentHeight_minY(int y)
	{
		if (this.showOriginalTextsThisTime)
		{
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
	private int tweaksCommentHeight_height(int height)
	{
		if (this.showOriginalTextsThisTime)
		{
			height += 6;
		}
		return height;
	}

	@Inject(
			method = "addHotkeyConfigElements",
			at = @At(value = "HEAD"),
			remap = false,
			cancellable = true
	)
	private void tweakerMoreCustomConfigGui(int x, int y, int configWidth, String configName, IHotkey config, CallbackInfo ci)
	{
		if (this.isTweakerMoreConfigGui())
		{
			if ((config).getKeybind() instanceof KeybindMulti)
			{
				this.addButtonAndHotkeyWidgets(x, y, configWidth, config);
				ci.cancel();
			}
		}
	}

	private void addButtonAndHotkeyWidgets(int x, int y, int configWidth, IHotkey config)
	{
		IKeybind keybind = config.getKeybind();

		int triggerBtnWidth = 60;
		ButtonGeneric triggerButton = new ButtonGeneric(
				x, y, triggerBtnWidth, 20,
				StringUtils.translate("tweakermore.gui.trigger_button.text"),
				StringUtils.translate("tweakermore.gui.trigger_button.hover", config.getName())
		);
		IHotkeyCallback callback = ((KeybindMultiAccessor)keybind).getCallback();
		this.addButton(triggerButton, (button, mouseButton) -> {
			KeyAction activateOn = keybind.getSettings().getActivateOn();
			if (activateOn == KeyAction.BOTH || activateOn == KeyAction.PRESS)
			{
				callback.onKeyAction(KeyAction.PRESS, keybind);
			}
			if (activateOn == KeyAction.BOTH || activateOn == KeyAction.RELEASE)
			{
				callback.onKeyAction(KeyAction.RELEASE, keybind);
			}
		});

		x += triggerBtnWidth + 2;
		configWidth -= triggerBtnWidth + 2 + 22;

		ConfigButtonKeybind keybindButton = new ConfigButtonKeybind(x, y, configWidth, 20, keybind, this.host);
		x += configWidth + 2;

		this.addWidget(new WidgetKeybindSettings(x, y, 20, 20, keybind, config.getName(), this.parent, this.host.getDialogHandler()));
		x += 22;

		this.addButton(keybindButton, this.host.getButtonPressListener());
		this.addKeybindResetButton(x, y, keybind, keybindButton);
	}
}
