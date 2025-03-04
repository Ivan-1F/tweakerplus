package me.ivan1f.tweakerplus.compat.modmenu;

//#if MC >= 11600
//$$ import com.terraformersmc.modmenu.api.ConfigScreenFactory;
//$$ import com.terraformersmc.modmenu.api.ModMenuApi;
//#elseif MC >= 11500
import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
//#else
//$$ import io.github.prospector.modmenu.api.ModMenuApi;
//$$ import me.ivan1f.tweakerplus.TweakerPlusMod;
//$$ import net.minecraft.client.gui.screen.Screen;
//$$ import java.util.function.Function;
//#endif

import me.ivan1f.tweakerplus.gui.TweakerPlusConfigGui;

//#if MC < 11500
//$$ @SuppressWarnings("deprecation")
//#endif
public class ModMenuApiImpl implements ModMenuApi {
    //#if MC < 11500
    //$$ public String getModId() {
    //$$ 	return TweakerPlusMod.MOD_ID;
    //$$ }
    //#endif

    @Override
    //#if MC >= 11500
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
    //#else
    //$$ public Function<Screen, ? extends Screen> getConfigScreenFactory() {
    //#endif
        return (screen) -> {
            TweakerPlusConfigGui gui = new TweakerPlusConfigGui();
            gui.setParent(screen);
            return gui;
        };
    }
}
