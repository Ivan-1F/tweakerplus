package me.ivan1f.tweakerplus.compat.modmenu;

//#if MC >= 11800
//$$ import com.terraformersmc.modmenu.api.ConfigScreenFactory;
//$$ import com.terraformersmc.modmenu.api.ModMenuApi;
//#else
import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
//#endif
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
