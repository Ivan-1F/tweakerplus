package me.ivan1f.tweakerplus.impl.tweakerpVillagerAutoTrade;

import fi.dy.masa.malilib.interfaces.IStringValue;
import fi.dy.masa.malilib.util.StringUtils;

public enum TradeFailedReason implements IStringValue {
    LOCKED,
    OFFER_NOT_FOUND,
    LACK_OF_BUY_ITEMS;

    @Override
    public String getStringValue() {
        return StringUtils.translate("tweakerplus.config.tweakpAutoTrade.failed_reason." + this.name().toLowerCase());
    }
}
