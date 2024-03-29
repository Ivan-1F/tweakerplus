# Document

**English** | [中文](document-zh_cn.md)

## Feature

New features provided by TweakerPlus

### limitWorldModification

Prevent any player interaction

 - Type: `togglable hotkey`
 - Default value: *no hotkey*, `false`
 - Category: `Feature`
 - Config Type: `Tweak`

### immediatelyRespawn

Respawn immediately after death without showing the death screen

 - Type: `togglable hotkey`
 - Default value: *no hotkey*, `false`
 - Category: `Feature`
 - Config Type: `Tweak`

### tweakpAutoTrade

The main switch of auto trade functionalities including

- tweakpTradeEverything

- tweakpAutoTradeThrowOutput

- tweakpAutoTradeEverything

- the recipe selector in the merchant screen

 - Type: `togglable hotkey`
 - Default value: *no hotkey*, `false`
 - Category: `Feature`
 - Config Type: `Tweak`
 - Mod restrictions:
   - Required mods:
     - Item Scroller (`itemscroller`)

### tweakpTradeEverything

Use all items in the inventory to trade

 - Type: `hotkey`
 - Default value: *no hotkey*
 - Category: `Feature`
 - Config Type: `Hotkey`
 - Mod restrictions:
   - Required mods:
     - Item Scroller (`itemscroller`)

### tweakpAutoTradeStoreRecipe

Store a recipe while hovering over a trade output item

Hovering over an empty slot will clear the selected stored recipe

 - Type: `hotkey`
 - Default value: `BUTTON_3`
 - Category: `Feature`
 - Config Type: `Hotkey`
 - Mod restrictions:
   - Required mods:
     - Item Scroller (`itemscroller`)

### tweakpAutoTradeThrowOutput

Forced throw out the output after auto trading

instead of moving them to the inventory

 - Type: `togglable hotkey`
 - Default value: *no hotkey*, `false`
 - Category: `Feature`
 - Config Type: `Generic`
 - Mod restrictions:
   - Required mods:
     - Item Scroller (`itemscroller`)

### tweakpAutoTradeEverything

Automatically trade everything in the opened merchant screen

and close the screen

 - Type: `togglable hotkey`
 - Default value: *no hotkey*, `false`
 - Category: `Feature`
 - Config Type: `Tweak`
 - Mod restrictions:
   - Required mods:
     - Item Scroller (`itemscroller`)

## MC Tweaks

Tweaks on Minecraft

### playerListScale

Scale the player list hud with given factor

 - Type: `double`
 - Default value: `1.0`
 - Category: `MC Tweaks`
 - Config Type: `Generic`
 - Minimum value: `0.001`
 - Maximum value: `2.0`

### subtitlesScale

Scale the subtitles hud with given factor

 - Type: `double`
 - Default value: `1.0`
 - Category: `MC Tweaks`
 - Config Type: `Generic`
 - Minimum value: `0.001`
 - Maximum value: `2.0`

### debugHudScale

Scale the F3 debug hud with given factor

 - Type: `double`
 - Default value: `1.0`
 - Category: `MC Tweaks`
 - Config Type: `Generic`
 - Minimum value: `0.001`
 - Maximum value: `2.0`

### itemTooltipScale

Scale the item tooltip with given factor

 - Type: `double`
 - Default value: `1.0`
 - Category: `MC Tweaks`
 - Config Type: `Generic`
 - Minimum value: `0.001`
 - Maximum value: `2.0`

### disablePumpkinOverlay

Disable the overlay rendered when the player is equipped with a carved pumpkin

 - Type: `togglable hotkey`
 - Default value: *no hotkey*, `false`
 - Category: `MC Tweaks`
 - Config Type: `Disable`

### disablePortalOverlay

Disable the overlay rendered when the player is in the nether portal

 - Type: `togglable hotkey`
 - Default value: *no hotkey*, `false`
 - Category: `MC Tweaks`
 - Config Type: `Disable`

### disableWitherSpawnSound

Disable the wither spawned sound emitted when a wither fully reset its health after summoned

 - Type: `togglable hotkey`
 - Default value: *no hotkey*, `false`
 - Category: `MC Tweaks`
 - Config Type: `Disable`

### disableBubbleColumnRendering

Prevents rendering bubble column particles

 - Type: `togglable hotkey`
 - Default value: *no hotkey*, `false`
 - Category: `MC Tweaks`
 - Config Type: `Disable`

### resourcePackIncompatibleIgnored

Ignore the warning on the resource pack screen when the resource pack is incompatible

 - Type: `boolean`
 - Default value: `false`
 - Category: `MC Tweaks`
 - Config Type: `Generic`

### reloadGameOptions

Reload options.txt

 - Type: `hotkey`
 - Default value: *no hotkey*
 - Category: `MC Tweaks`
 - Config Type: `Hotkey`

### disablePortalTeleportSound

Disable the sound played when standing in the nether portal and being teleported through the portal

 - Type: `togglable hotkey`
 - Default value: *no hotkey*, `false`
 - Category: `MC Tweaks`
 - Config Type: `Disable`

### disableEndermanAngrySound

Disable the enderman angry sound

 - Type: `togglable hotkey`
 - Default value: *no hotkey*, `false`
 - Category: `MC Tweaks`
 - Config Type: `Disable`

### enchantedBookHint

Render all its enchantments on the enchantment book item

Texts may be blocked if there are too many enchantments

 - Type: `togglable hotkey`
 - Default value: *no hotkey*, `false`
 - Category: `MC Tweaks`
 - Config Type: `Generic`

## Mods Tweaks

Tweaks on mods

### xmapNoDeathWaypointForCreative

Stop creating death waypoints for creative players

 - Type: `boolean`
 - Default value: `false`
 - Category: `Mods Tweaks`
 - Config Type: `Generic`
 - Mod restrictions:
   - Required mods:
     - Xaero's Minimap (`xaerominimap`)

### leftAlignTitleGlobally

Apply the left aligned title to all config GUIs using malilib

 - Type: `boolean`
 - Default value: `false`
 - Category: `Mods Tweaks`
 - Config Type: `Generic`

### bundleOriginInSchematic

Bundle the origin in the schematic when saving it

When loading the schematic, if the origin info is present in the file, the placement will be moved to its origin

(Using 'Create placement' button after loading will still place the placement at the player)

The origin info will be appended at the right panel of the schematic browser

 - Type: `boolean`
 - Default value: `false`
 - Category: `Mods Tweaks`
 - Config Type: `Generic`
 - Mod restrictions:
   - Required mods:
     - Litematica (`litematica`)

### disableEasyPlaceModeCache

litematica easyPlaceMode will not cache block placements

 - Type: `togglable hotkey`
 - Default value: *no hotkey*, `false`
 - Category: `Mods Tweaks`
 - Config Type: `Disable`
 - Mod restrictions:
   - Required mods:
     - Litematica (`litematica`)

## Setting

Settings of TweakPlus itself

### hideDisabledOptions

Hide options which are disabled due to mod relations unsatisfied in the config GUI

 - Type: `boolean`
 - Default value: `false`
 - Category: `Setting`
 - Config Type: `Generic`

### openTweakerPlusConfigGui

Open the config GUI of TweakerPlus

 - Type: `hotkey`
 - Default value: `P,C`
 - Category: `Setting`
 - Config Type: `Hotkey`

### tweakerPlusDebugMode

Debug mode of TweakerPlus

When enabled, debug parameter options and options not supported by the current Minecraft version will be displayed

and debugging related functions will be enabled

 - Type: `togglable hotkey`
 - Default value: *no hotkey*, `false`
 - Category: `Setting`
 - Config Type: `Tweak`

### tweakerPlusDebugInt

A integer parameter for TweakerPlus debugging

 - Type: `integer`
 - Default value: `0`
 - Category: `Setting`
 - Config Type: `Generic`
 - Minimum value: `-1000`
 - Maximum value: `1000`

### tweakerPlusDebugDouble

A double parameter for TweakerPlus debugging

 - Type: `integer`
 - Default value: `0`
 - Category: `Setting`
 - Config Type: `Generic`
 - Minimum value: `-1`
 - Maximum value: `1`

### screenDebug

When enabled, the title and the class of the gui will be logged when it is opened

 - Type: `boolean`
 - Default value: `false`
 - Category: `Setting`
 - Config Type: `Generic`

