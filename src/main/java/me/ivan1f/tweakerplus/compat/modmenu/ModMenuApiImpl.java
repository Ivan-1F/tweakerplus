package me.ivan1f.tweakerplus.compat.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.ivan1f.tweakerplus.gui.TweakerPlusConfigGui;

public class ModMenuApiImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (screen) -> {
            TweakerPlusConfigGui gui = new TweakerPlusConfigGui();
            gui.setParent(screen);
            return gui;
        };
    }
}
