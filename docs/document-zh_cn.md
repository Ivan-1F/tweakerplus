# 文档

[English](document.md) | **中文**

## 功能

TweakerPlus提供的新功能

### 限制世界修改 (limitWorldModification)

阻止任何形式的玩家交互

 - 类型: `可开关型热键`
 - 默认值: *无快捷键*, `false`
 - 分类: `功能`
 - 配置类型: `工具`

### 立即重生 (immediatelyRespawn)

死亡后立刻重生，不显示死亡界面

 - 类型: `可开关型热键`
 - 默认值: *无快捷键*, `false`
 - 分类: `功能`
 - 配置类型: `工具`

### 自动交易-总开关 (tweakpAutoTrade)

自动交易相关功能的总开关，影响：

- tweakpTradeEverything

- tweakpAutoTradeThrowOutput

- tweakpAutoTradeEverything

- 交易界面的配方选择器

 - 类型: `可开关型热键`
 - 默认值: *无快捷键*, `false`
 - 分类: `功能`
 - 配置类型: `工具`
 - 模组约束:
   - 依赖模组:
     - Item Scroller (`itemscroller`)

### 交易全部物品 (tweakpTradeEverything)

将背包中的所有物品用于村民交易

 - 类型: `热键`
 - 默认值: *无快捷键*
 - 分类: `功能`
 - 配置类型: `热键`
 - 模组约束:
   - 依赖模组:
     - Item Scroller (`itemscroller`)

### 自动交易-储存配方 (tweakpAutoTradeStoreRecipe)

将鼠标悬停在交易输出槽位上时存储交易配方

悬停在空槽位上将清空选中的以储存配方

 - 类型: `热键`
 - 默认值: `BUTTON_3`
 - 分类: `功能`
 - 配置类型: `热键`
 - 模组约束:
   - 依赖模组:
     - Item Scroller (`itemscroller`)

### 自动交易-扔出输出 (tweakpAutoTradeThrowOutput)

强制将自动交易得到的输出扔出背包，而不是放入背包

 - 类型: `可开关型热键`
 - 默认值: *无快捷键*, `false`
 - 分类: `功能`
 - 配置类型: `通用`
 - 模组约束:
   - 依赖模组:
     - Item Scroller (`itemscroller`)

### 自动交易全部物品 (tweakpAutoTradeEverything)

打开交易界面后，自动交易全部物品

然后关闭界面

 - 类型: `可开关型热键`
 - 默认值: *无快捷键*, `false`
 - 分类: `功能`
 - 配置类型: `工具`
 - 模组约束:
   - 依赖模组:
     - Item Scroller (`itemscroller`)

## MC修改

对Minecraft已有内容修改

### 玩家列表缩放 (playerListScale)

将玩家列表按照给定参数进行缩放显示

 - 类型: `实数`
 - 默认值: `1.0`
 - 分类: `MC修改`
 - 配置类型: `通用`
 - 最小值: `0.001`
 - 最大值: `2.0`

### 禁用南瓜头覆盖渲染 (disablePumpkinOverlay)

禁用玩家佩戴南瓜头时的覆盖渲染

 - 类型: `可开关型热键`
 - 默认值: *无快捷键*, `false`
 - 分类: `MC修改`
 - 配置类型: `禁用`

### 禁用地狱门覆盖渲染 (disablePortalOverlay)

禁用玩家在地狱门中时的覆盖渲染

 - 类型: `可开关型热键`
 - 默认值: *无快捷键*, `false`
 - 分类: `MC修改`
 - 配置类型: `禁用`

### 禁用凋灵生成音效 (disableWitherSpawnSound)

禁用凋灵在召唤后生命值回满时发出的世界中所有玩家都能听到的音效

 - 类型: `可开关型热键`
 - 默认值: *无快捷键*, `false`
 - 分类: `MC修改`
 - 配置类型: `禁用`

### 禁用气泡柱渲染 (disableBubbleColumnRendering)

阻止气泡柱粒子效果的渲染

 - 类型: `可开关型热键`
 - 默认值: *无快捷键*, `false`
 - 分类: `MC修改`
 - 配置类型: `禁用`

### 忽略资源包不兼容 (resourcePackIncompatibleIgnored)

忽略资源包界面资源包不兼容时的警告信息

 - 类型: `布尔值`
 - 默认值: `false`
 - 分类: `MC修改`
 - 配置类型: `通用`

## 模组修改

对模组已有内容修改

### xaero地图创造玩家无死亡标记 (xmapNoDeathWaypointForCreative)

阻止xaero地图为创造玩家创建死亡标记

 - 类型: `布尔值`
 - 默认值: `false`
 - 分类: `模组修改`
 - 配置类型: `通用`
 - 模组约束:
   - 依赖模组:
     - Xaero's Minimap (`xaerominimap`)

### 全局左对齐标题 (leftAlignTitleGlobally)

将左对齐的标题应用到所有使用Malilib的配置界面

 - 类型: `布尔值`
 - 默认值: `false`
 - 分类: `模组修改`
 - 配置类型: `通用`

### 将原点打包入原理图 (bundleOriginInSchematic)

在保存原理图时将原点信息打包入文件

在加载原理图时，若文件中含有原点信息，则将放置移动至原点

（加载后使用 '创建放置' 按钮仍会将放置创建在玩家处）

原理图浏览器右侧面板中会追加显示原点信息

 - 类型: `布尔值`
 - 默认值: `false`
 - 分类: `模组修改`
 - 配置类型: `通用`
 - 模组约束:
   - 依赖模组:
     - Litematica (`litematica`)

### 禁用轻松放置缓存 (disableEasyPlaceModeCache)

litematica轻松放置功能将不会缓存方块放置

 - 类型: `可开关型热键`
 - 默认值: *无快捷键*, `false`
 - 分类: `模组修改`
 - 配置类型: `禁用`
 - 模组约束:
   - 依赖模组:
     - Litematica (`litematica`)

## 配置

与TweakerPlus相关的配置

### 隐藏禁用的选项 (hideDisabledOptions)

在配置界面中隐藏因模组约束关系不被满足而被禁用的选项

 - 类型: `布尔值`
 - 默认值: `false`
 - 分类: `配置`
 - 配置类型: `通用`

### 打开TweakerPlus配置界面 (openTweakerPlusConfigGui)

打开TweakerPlus的配置界面

 - 类型: `热键`
 - 默认值: `P,C`
 - 分类: `配置`
 - 配置类型: `热键`

### TweakerPlus调试模式 (tweakerPlusDebugMode)

TweakerPlus的调试模式

当启用时，调试用参数选项以及当前游戏版本不支持的选项会被显示

以及调试相关的功能会被启用

 - 类型: `可开关型热键`
 - 默认值: *无快捷键*, `false`
 - 分类: `配置`
 - 配置类型: `工具`

### TweakerPlus调试用Int (tweakerPlusDebugInt)

用于调试TweakerPlus的Int

 - 类型: `整数`
 - 默认值: `0`
 - 分类: `配置`
 - 配置类型: `通用`
 - 最小值: `-1000`
 - 最大值: `1000`

### TweakerPlus调试用Double (tweakerPlusDebugDouble)

用于调试TweakerPlus的Double

 - 类型: `整数`
 - 默认值: `0`
 - 分类: `配置`
 - 配置类型: `通用`
 - 最小值: `-1`
 - 最大值: `1`

### GUI调试 (screenDebug)

启用时，GUI开启时将输出其标题与类名至日志

 - 类型: `布尔值`
 - 默认值: `false`
 - 分类: `配置`
 - 配置类型: `通用`

