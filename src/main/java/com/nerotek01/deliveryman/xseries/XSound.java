/*      */ package com.nerotek01.deliveryman.xseries;
/*      */ 
/*      */ import com.google.common.base.Enums;
/*      */ import com.google.common.base.Optional;
/*      */ import com.google.common.base.Strings;
/*      */ import com.google.common.cache.Cache;
/*      */ import com.google.common.cache.CacheBuilder;
/*      */ import java.util.Arrays;
/*      */ import java.util.EnumSet;
/*      */ import java.util.Locale;
/*      */ import java.util.Objects;
/*      */ import java.util.Optional;
/*      */ import java.util.concurrent.CompletableFuture;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import java.util.regex.Pattern;
/*      */ import javax.annotation.Nonnull;
/*      */ import javax.annotation.Nullable;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.lang.Validate;
/*      */ import org.apache.commons.lang.WordUtils;
/*      */ import org.bukkit.Instrument;
/*      */ import org.bukkit.Location;
/*      */ import org.bukkit.Note;
/*      */ import org.bukkit.Sound;
/*      */ import org.bukkit.entity.Entity;
/*      */ import org.bukkit.entity.Player;
/*      */ import org.bukkit.plugin.Plugin;
/*      */ import org.bukkit.plugin.java.JavaPlugin;
/*      */ import org.bukkit.scheduler.BukkitRunnable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public enum XSound
/*      */ {
/*   43 */   AMBIENT_CAVE(new String[] { "AMBIENCE_CAVE" }),
/*   44 */   AMBIENT_UNDERWATER_ENTER(new String[0]),
/*   45 */   AMBIENT_UNDERWATER_EXIT(new String[0]),
/*   46 */   AMBIENT_UNDERWATER_LOOP(new String[] { "AMBIENT_UNDERWATER_EXIT" }),
/*   47 */   AMBIENT_UNDERWATER_LOOP_ADDITIONS(new String[] { "AMBIENT_UNDERWATER_EXIT" }),
/*   48 */   AMBIENT_UNDERWATER_LOOP_ADDITIONS_RARE(new String[] { "AMBIENT_UNDERWATER_EXIT" }),
/*   49 */   AMBIENT_UNDERWATER_LOOP_ADDITIONS_ULTRA_RARE(new String[] { "AMBIENT_UNDERWATER_EXIT" }),
/*   50 */   BLOCK_ANVIL_BREAK(new String[] { "ANVIL_BREAK" }),
/*   51 */   BLOCK_ANVIL_DESTROY(new String[0]),
/*   52 */   BLOCK_ANVIL_FALL(new String[0]),
/*   53 */   BLOCK_ANVIL_HIT(new String[] { "BLOCK_ANVIL_FALL" }),
/*   54 */   BLOCK_ANVIL_LAND(new String[] { "ANVIL_LAND" }),
/*   55 */   BLOCK_ANVIL_PLACE(new String[] { "BLOCK_ANVIL_FALL" }),
/*   56 */   BLOCK_ANVIL_STEP(new String[] { "BLOCK_ANVIL_FALL" }),
/*   57 */   BLOCK_ANVIL_USE(new String[] { "ANVIL_USE" }),
/*   58 */   BLOCK_BAMBOO_BREAK(new String[0]),
/*   59 */   BLOCK_BAMBOO_FALL(new String[0]),
/*   60 */   BLOCK_BAMBOO_HIT(new String[0]),
/*   61 */   BLOCK_BAMBOO_PLACE(new String[0]),
/*   62 */   BLOCK_BAMBOO_SAPLING_BREAK(new String[0]),
/*   63 */   BLOCK_BAMBOO_SAPLING_HIT(new String[0]),
/*   64 */   BLOCK_BAMBOO_SAPLING_PLACE(new String[0]),
/*   65 */   BLOCK_BAMBOO_STEP(new String[0]),
/*   66 */   BLOCK_BARREL_CLOSE(new String[0]),
/*   67 */   BLOCK_BARREL_OPEN(new String[0]),
/*   68 */   BLOCK_BEACON_ACTIVATE(new String[0]),
/*   69 */   BLOCK_BEACON_AMBIENT(new String[0]),
/*   70 */   BLOCK_BEACON_DEACTIVATE(new String[] { "BLOCK_BEACON_AMBIENT" }),
/*   71 */   BLOCK_BEACON_POWER_SELECT(new String[] { "BLOCK_BEACON_AMBIENT" }),
/*   72 */   BLOCK_BEEHIVE_DRIP(new String[0]),
/*   73 */   BLOCK_BEEHIVE_ENTER(new String[0]),
/*   74 */   BLOCK_BEEHIVE_EXIT(new String[0]),
/*   75 */   BLOCK_BEEHIVE_SHEAR(new String[0]),
/*   76 */   BLOCK_BEEHIVE_WORK(new String[0]),
/*   77 */   BLOCK_BELL_RESONATE(new String[0]),
/*   78 */   BLOCK_BELL_USE(new String[0]),
/*   79 */   BLOCK_BLASTFURNACE_FIRE_CRACKLE(new String[0]),
/*   80 */   BLOCK_BREWING_STAND_BREW(new String[0]),
/*   81 */   BLOCK_BUBBLE_COLUMN_BUBBLE_POP(new String[0]),
/*   82 */   BLOCK_BUBBLE_COLUMN_UPWARDS_AMBIENT(new String[0]),
/*   83 */   BLOCK_BUBBLE_COLUMN_UPWARDS_INSIDE(new String[0]),
/*   84 */   BLOCK_BUBBLE_COLUMN_WHIRLPOOL_AMBIENT(new String[0]),
/*   85 */   BLOCK_BUBBLE_COLUMN_WHIRLPOOL_INSIDE(new String[0]),
/*   86 */   BLOCK_CAMPFIRE_CRACKLE(new String[0]),
/*   87 */   BLOCK_CHEST_CLOSE(new String[] { "CHEST_CLOSE", "ENTITY_CHEST_CLOSE" }),
/*   88 */   BLOCK_CHEST_LOCKED(new String[0]),
/*   89 */   BLOCK_CHEST_OPEN(new String[] { "CHEST_OPEN", "ENTITY_CHEST_OPEN" }),
/*   90 */   BLOCK_CHORUS_FLOWER_DEATH(new String[0]),
/*   91 */   BLOCK_CHORUS_FLOWER_GROW(new String[0]),
/*   92 */   BLOCK_COMPARATOR_CLICK(new String[0]),
/*   93 */   BLOCK_COMPOSTER_EMPTY(new String[0]),
/*   94 */   BLOCK_COMPOSTER_FILL(new String[0]),
/*   95 */   BLOCK_COMPOSTER_FILL_SUCCESS(new String[0]),
/*   96 */   BLOCK_COMPOSTER_READY(new String[0]),
/*   97 */   BLOCK_CONDUIT_ACTIVATE(new String[0]),
/*   98 */   BLOCK_CONDUIT_AMBIENT(new String[0]),
/*   99 */   BLOCK_CONDUIT_AMBIENT_SHORT(new String[0]),
/*  100 */   BLOCK_CONDUIT_ATTACK_TARGET(new String[0]),
/*  101 */   BLOCK_CONDUIT_DEACTIVATE(new String[0]),
/*  102 */   BLOCK_CORAL_BLOCK_BREAK(new String[0]),
/*  103 */   BLOCK_CORAL_BLOCK_FALL(new String[0]),
/*  104 */   BLOCK_CORAL_BLOCK_HIT(new String[0]),
/*  105 */   BLOCK_CORAL_BLOCK_PLACE(new String[0]),
/*  106 */   BLOCK_CORAL_BLOCK_STEP(new String[0]),
/*  107 */   BLOCK_CROP_BREAK(new String[0]),
/*  108 */   BLOCK_DISPENSER_DISPENSE(new String[0]),
/*  109 */   BLOCK_DISPENSER_FAIL(new String[0]),
/*  110 */   BLOCK_DISPENSER_LAUNCH(new String[0]),
/*  111 */   BLOCK_ENCHANTMENT_TABLE_USE(new String[0]),
/*  112 */   BLOCK_ENDER_CHEST_CLOSE(new String[0]),
/*  113 */   BLOCK_ENDER_CHEST_OPEN(new String[0]),
/*  114 */   BLOCK_END_GATEWAY_SPAWN(new String[0]),
/*  115 */   BLOCK_END_PORTAL_FRAME_FILL(new String[0]),
/*  116 */   BLOCK_END_PORTAL_SPAWN(new String[0]),
/*  117 */   BLOCK_FENCE_GATE_CLOSE(new String[0]),
/*  118 */   BLOCK_FENCE_GATE_OPEN(new String[0]),
/*  119 */   BLOCK_FIRE_AMBIENT(new String[] { "FIRE" }),
/*  120 */   BLOCK_FIRE_EXTINGUISH(new String[] { "FIZZ" }),
/*  121 */   BLOCK_FURNACE_FIRE_CRACKLE(new String[0]),
/*  122 */   BLOCK_GLASS_BREAK(new String[] { "GLASS" }),
/*  123 */   BLOCK_GLASS_FALL(new String[0]),
/*  124 */   BLOCK_GLASS_HIT(new String[0]),
/*  125 */   BLOCK_GLASS_PLACE(new String[0]),
/*  126 */   BLOCK_GLASS_STEP(new String[0]),
/*  127 */   BLOCK_GRASS_BREAK(new String[] { "DIG_GRASS" }),
/*  128 */   BLOCK_GRASS_FALL(new String[0]),
/*  129 */   BLOCK_GRASS_HIT(new String[0]),
/*  130 */   BLOCK_GRASS_PLACE(new String[0]),
/*  131 */   BLOCK_GRASS_STEP(new String[] { "STEP_GRASS" }),
/*  132 */   BLOCK_GRAVEL_BREAK(new String[] { "DIG_GRAVEL" }),
/*  133 */   BLOCK_GRAVEL_FALL(new String[0]),
/*  134 */   BLOCK_GRAVEL_HIT(new String[0]),
/*  135 */   BLOCK_GRAVEL_PLACE(new String[0]),
/*  136 */   BLOCK_GRAVEL_STEP(new String[] { "STEP_GRAVEL" }),
/*  137 */   BLOCK_GRINDSTONE_USE(new String[0]),
/*  138 */   BLOCK_HONEY_BLOCK_BREAK(new String[0]),
/*  139 */   BLOCK_HONEY_BLOCK_FALL(new String[0]),
/*  140 */   BLOCK_HONEY_BLOCK_HIT(new String[0]),
/*  141 */   BLOCK_HONEY_BLOCK_PLACE(new String[0]),
/*  142 */   BLOCK_HONEY_BLOCK_SLIDE(new String[0]),
/*  143 */   BLOCK_HONEY_BLOCK_STEP(new String[0]),
/*  144 */   BLOCK_IRON_DOOR_CLOSE(new String[0]),
/*  145 */   BLOCK_IRON_DOOR_OPEN(new String[0]),
/*  146 */   BLOCK_IRON_TRAPDOOR_CLOSE(new String[0]),
/*  147 */   BLOCK_IRON_TRAPDOOR_OPEN(new String[0]),
/*  148 */   BLOCK_LADDER_BREAK(new String[0]),
/*  149 */   BLOCK_LADDER_FALL(new String[0]),
/*  150 */   BLOCK_LADDER_HIT(new String[0]),
/*  151 */   BLOCK_LADDER_PLACE(new String[0]),
/*  152 */   BLOCK_LADDER_STEP(new String[] { "STEP_LADDER" }),
/*  153 */   BLOCK_LANTERN_BREAK(new String[0]),
/*  154 */   BLOCK_LANTERN_FALL(new String[0]),
/*  155 */   BLOCK_LANTERN_HIT(new String[0]),
/*  156 */   BLOCK_LANTERN_PLACE(new String[0]),
/*  157 */   BLOCK_LANTERN_STEP(new String[0]),
/*  158 */   BLOCK_LAVA_AMBIENT(new String[] { "LAVA" }),
/*  159 */   BLOCK_LAVA_EXTINGUISH(new String[0]),
/*  160 */   BLOCK_LAVA_POP(new String[] { "LAVA_POP" }),
/*  161 */   BLOCK_LEVER_CLICK(new String[0]),
/*  162 */   BLOCK_LILY_PAD_PLACE(new String[] { "BLOCK_WATERLILY_PLACE" }),
/*  163 */   BLOCK_METAL_BREAK(new String[0]),
/*  164 */   BLOCK_METAL_FALL(new String[0]),
/*  165 */   BLOCK_METAL_HIT(new String[0]),
/*  166 */   BLOCK_METAL_PLACE(new String[0]),
/*  167 */   BLOCK_METAL_PRESSURE_PLATE_CLICK_OFF(new String[] { "BLOCK_METAL_PRESSUREPLATE_CLICK_OFF" }),
/*  168 */   BLOCK_METAL_PRESSURE_PLATE_CLICK_ON(new String[] { "BLOCK_METAL_PRESSUREPLATE_CLICK_ON" }),
/*  169 */   BLOCK_METAL_STEP(new String[0]),
/*  170 */   BLOCK_NETHER_WART_BREAK(new String[0]),
/*  171 */   BLOCK_NOTE_BLOCK_BANJO(new String[0]),
/*  172 */   BLOCK_NOTE_BLOCK_BASEDRUM(new String[] { "NOTE_BASS_DRUM", "BLOCK_NOTE_BASEDRUM" }),
/*  173 */   BLOCK_NOTE_BLOCK_BASS(new String[] { "NOTE_BASS", "BLOCK_NOTE_BASS" }),
/*  174 */   BLOCK_NOTE_BLOCK_BELL(new String[] { "BLOCK_NOTE_BELL" }),
/*  175 */   BLOCK_NOTE_BLOCK_BIT(new String[0]),
/*  176 */   BLOCK_NOTE_BLOCK_CHIME(new String[] { "BLOCK_NOTE_CHIME" }),
/*  177 */   BLOCK_NOTE_BLOCK_COW_BELL(new String[0]),
/*  178 */   BLOCK_NOTE_BLOCK_DIDGERIDOO(new String[0]),
/*  179 */   BLOCK_NOTE_BLOCK_FLUTE(new String[] { "BLOCK_NOTE_FLUTE" }),
/*  180 */   BLOCK_NOTE_BLOCK_GUITAR(new String[] { "NOTE_BASS_GUITAR", "BLOCK_NOTE_GUITAR" }),
/*  181 */   BLOCK_NOTE_BLOCK_HARP(new String[] { "NOTE_PIANO", "BLOCK_NOTE_HARP" }),
/*  182 */   BLOCK_NOTE_BLOCK_HAT(new String[] { "NOTE_STICKS", "BLOCK_NOTE_HAT" }),
/*  183 */   BLOCK_NOTE_BLOCK_IRON_XYLOPHONE(new String[0]),
/*  184 */   BLOCK_NOTE_BLOCK_PLING(new String[] { "NOTE_PLING", "BLOCK_NOTE_PLING" }),
/*  185 */   BLOCK_NOTE_BLOCK_SNARE(new String[] { "NOTE_SNARE_DRUM", "BLOCK_NOTE_SNARE" }),
/*  186 */   BLOCK_NOTE_BLOCK_XYLOPHONE(new String[] { "BLOCK_NOTE_XYLOPHONE" }),
/*  187 */   BLOCK_PISTON_CONTRACT(new String[] { "PISTON_RETRACT" }),
/*  188 */   BLOCK_PISTON_EXTEND(new String[] { "PISTON_EXTEND" }),
/*  189 */   BLOCK_PORTAL_AMBIENT(new String[] { "PORTAL" }),
/*  190 */   BLOCK_PORTAL_TRAVEL(new String[] { "PORTAL_TRAVEL" }),
/*  191 */   BLOCK_PORTAL_TRIGGER(new String[] { "PORTAL_TRIGGER" }),
/*  192 */   BLOCK_PUMPKIN_CARVE(new String[0]),
/*  193 */   BLOCK_REDSTONE_TORCH_BURNOUT(new String[0]),
/*  194 */   BLOCK_SAND_BREAK(new String[] { "DIG_SAND" }),
/*  195 */   BLOCK_SAND_FALL(new String[0]),
/*  196 */   BLOCK_SAND_HIT(new String[0]),
/*  197 */   BLOCK_SAND_PLACE(new String[0]),
/*  198 */   BLOCK_SAND_STEP(new String[] { "STEP_SAND" }),
/*  199 */   BLOCK_SCAFFOLDING_BREAK(new String[0]),
/*  200 */   BLOCK_SCAFFOLDING_FALL(new String[0]),
/*  201 */   BLOCK_SCAFFOLDING_HIT(new String[0]),
/*  202 */   BLOCK_SCAFFOLDING_PLACE(new String[0]),
/*  203 */   BLOCK_SCAFFOLDING_STEP(new String[0]),
/*  204 */   BLOCK_SHULKER_BOX_CLOSE(new String[0]),
/*  205 */   BLOCK_SHULKER_BOX_OPEN(new String[0]),
/*  206 */   BLOCK_SLIME_BLOCK_BREAK(new String[] { "BLOCK_SLIME_BREAK" }),
/*  207 */   BLOCK_SLIME_BLOCK_FALL(new String[] { "BLOCK_SLIME_FALL" }),
/*  208 */   BLOCK_SLIME_BLOCK_HIT(new String[] { "BLOCK_SLIME_HIT" }),
/*  209 */   BLOCK_SLIME_BLOCK_PLACE(new String[] { "BLOCK_SLIME_PLACE" }),
/*  210 */   BLOCK_SLIME_BLOCK_STEP(new String[] { "BLOCK_SLIME_STEP" }),
/*  211 */   BLOCK_SMOKER_SMOKE(new String[0]),
/*  212 */   BLOCK_SNOW_BREAK(new String[] { "DIG_SNOW" }),
/*  213 */   BLOCK_SNOW_FALL(new String[0]),
/*  214 */   BLOCK_SNOW_HIT(new String[0]),
/*  215 */   BLOCK_SNOW_PLACE(new String[0]),
/*  216 */   BLOCK_SNOW_STEP(new String[] { "STEP_SNOW" }),
/*  217 */   BLOCK_STONE_BREAK(new String[] { "DIG_STONE" }),
/*  218 */   BLOCK_STONE_BUTTON_CLICK_OFF(new String[0]),
/*  219 */   BLOCK_STONE_BUTTON_CLICK_ON(new String[0]),
/*  220 */   BLOCK_STONE_FALL(new String[0]),
/*  221 */   BLOCK_STONE_HIT(new String[0]),
/*  222 */   BLOCK_STONE_PLACE(new String[0]),
/*  223 */   BLOCK_STONE_PRESSURE_PLATE_CLICK_OFF(new String[] { "BLOCK_STONE_PRESSUREPLATE_CLICK_OFF" }),
/*  224 */   BLOCK_STONE_PRESSURE_PLATE_CLICK_ON(new String[] { "BLOCK_STONE_PRESSUREPLATE_CLICK_ON" }),
/*  225 */   BLOCK_STONE_STEP(new String[] { "STEP_STONE" }),
/*  226 */   BLOCK_SWEET_BERRY_BUSH_BREAK(new String[0]),
/*  227 */   BLOCK_SWEET_BERRY_BUSH_PLACE(new String[0]),
/*  228 */   BLOCK_TRIPWIRE_ATTACH(new String[0]),
/*  229 */   BLOCK_TRIPWIRE_CLICK_OFF(new String[0]),
/*  230 */   BLOCK_TRIPWIRE_CLICK_ON(new String[0]),
/*  231 */   BLOCK_TRIPWIRE_DETACH(new String[0]),
/*  232 */   BLOCK_WATER_AMBIENT(new String[] { "WATER" }),
/*  233 */   BLOCK_WET_GRASS_BREAK(new String[0]),
/*  234 */   BLOCK_WET_GRASS_FALL(new String[0]),
/*  235 */   BLOCK_WET_GRASS_HIT(new String[0]),
/*  236 */   BLOCK_WET_GRASS_PLACE(new String[] { "BLOCK_WET_GRASS_HIT" }),
/*  237 */   BLOCK_WET_GRASS_STEP(new String[] { "BLOCK_WET_GRASS_HIT" }),
/*  238 */   BLOCK_WOODEN_BUTTON_CLICK_OFF(new String[] { "WOOD_CLICK", "BLOCK_WOOD_BUTTON_CLICK_OFF" }),
/*  239 */   BLOCK_WOODEN_BUTTON_CLICK_ON(new String[] { "WOOD_CLICK", "BLOCK_WOOD_BUTTON_CLICK_ON" }),
/*  240 */   BLOCK_WOODEN_DOOR_CLOSE(new String[] { "DOOR_CLOSE" }),
/*  241 */   BLOCK_WOODEN_DOOR_OPEN(new String[] { "DOOR_OPEN" }),
/*  242 */   BLOCK_WOODEN_PRESSURE_PLATE_CLICK_OFF(new String[] { "BLOCK_WOOD_PRESSUREPLATE_CLICK_OFF" }),
/*  243 */   BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON(new String[] { "BLOCK_WOOD_PRESSUREPLATE_CLICK_ON" }),
/*  244 */   BLOCK_WOODEN_TRAPDOOR_CLOSE(new String[0]),
/*  245 */   BLOCK_WOODEN_TRAPDOOR_OPEN(new String[0]),
/*  246 */   BLOCK_WOOD_BREAK(new String[] { "DIG_WOOD" }),
/*  247 */   BLOCK_WOOD_FALL(new String[0]),
/*  248 */   BLOCK_WOOD_HIT(new String[0]),
/*  249 */   BLOCK_WOOD_PLACE(new String[0]),
/*  250 */   BLOCK_WOOD_STEP(new String[] { "STEP_WOOD" }),
/*  251 */   BLOCK_WOOL_BREAK(new String[] { "DIG_WOOL", "BLOCK_CLOTH_BREAK" }),
/*  252 */   BLOCK_WOOL_FALL(new String[0]),
/*  253 */   BLOCK_WOOL_HIT(new String[] { "BLOCK_WOOL_FALL" }),
/*  254 */   BLOCK_WOOL_PLACE(new String[] { "BLOCK_WOOL_FALL" }),
/*  255 */   BLOCK_WOOL_STEP(new String[] { "STEP_WOOL", "BLOCK_CLOTH_STEP" }),
/*  256 */   ENCHANT_THORNS_HIT(new String[0]),
/*  257 */   ENTITY_ARMOR_STAND_BREAK(new String[] { "ENTITY_ARMORSTAND_BREAK" }),
/*  258 */   ENTITY_ARMOR_STAND_FALL(new String[] { "ENTITY_ARMORSTAND_FALL" }),
/*  259 */   ENTITY_ARMOR_STAND_HIT(new String[] { "ENTITY_ARMORSTAND_HIT" }),
/*  260 */   ENTITY_ARMOR_STAND_PLACE(new String[] { "ENTITY_ARMORSTAND_PLACE" }),
/*  261 */   ENTITY_ARROW_HIT(new String[] { "ARROW_HIT" }),
/*  262 */   ENTITY_ARROW_HIT_PLAYER(new String[0]),
/*  263 */   ENTITY_ARROW_SHOOT(new String[] { "SHOOT_ARROW" }),
/*  264 */   ENTITY_BAT_AMBIENT(new String[] { "BAT_IDLE" }),
/*  265 */   ENTITY_BAT_DEATH(new String[] { "BAT_DEATH" }),
/*  266 */   ENTITY_BAT_HURT(new String[] { "BAT_HURT" }),
/*  267 */   ENTITY_BAT_LOOP(new String[] { "BAT_LOOP" }),
/*  268 */   ENTITY_BAT_TAKEOFF(new String[] { "BAT_TAKEOFF" }),
/*  269 */   ENTITY_BEE_DEATH(new String[0]),
/*  270 */   ENTITY_BEE_HURT(new String[0]),
/*  271 */   ENTITY_BEE_LOOP(new String[0]),
/*  272 */   ENTITY_BEE_LOOP_AGGRESSIVE(new String[0]),
/*  273 */   ENTITY_BEE_POLLINATE(new String[0]),
/*  274 */   ENTITY_BEE_STING(new String[0]),
/*  275 */   ENTITY_BLAZE_AMBIENT(new String[] { "BLAZE_BREATH" }),
/*  276 */   ENTITY_BLAZE_BURN(new String[0]),
/*  277 */   ENTITY_BLAZE_DEATH(new String[] { "BLAZE_DEATH" }),
/*  278 */   ENTITY_BLAZE_HURT(new String[] { "BLAZE_HIT" }),
/*  279 */   ENTITY_BLAZE_SHOOT(new String[0]),
/*  280 */   ENTITY_BOAT_PADDLE_LAND(new String[0]),
/*  281 */   AMBIENT_BASALT_DELTAS_ADDITIONS(new String[0]),
/*  282 */   AMBIENT_BASALT_DELTAS_LOOP(new String[0]),
/*  283 */   AMBIENT_BASALT_DELTAS_MOOD(new String[0]),
/*  284 */   AMBIENT_CRIMSON_FOREST_ADDITIONS(new String[0]),
/*  285 */   AMBIENT_CRIMSON_FOREST_LOOP(new String[0]),
/*  286 */   AMBIENT_CRIMSON_FOREST_MOOD(new String[0]),
/*  287 */   AMBIENT_NETHER_WASTES_ADDITIONS(new String[0]),
/*  288 */   AMBIENT_NETHER_WASTES_LOOP(new String[0]),
/*  289 */   AMBIENT_NETHER_WASTES_MOOD(new String[0]),
/*  290 */   AMBIENT_SOUL_SAND_VALLEY_ADDITIONS(new String[0]),
/*  291 */   AMBIENT_SOUL_SAND_VALLEY_LOOP(new String[0]),
/*  292 */   AMBIENT_SOUL_SAND_VALLEY_MOOD(new String[0]),
/*  293 */   ENTITY_BOAT_PADDLE_WATER(new String[0]),
/*  294 */   ENTITY_CAT_AMBIENT(new String[] { "CAT_MEOW" }),
/*  295 */   ENTITY_CAT_BEG_FOR_FOOD(new String[0]),
/*  296 */   AMBIENT_WARPED_FOREST_ADDITIONS(new String[0]),
/*  297 */   AMBIENT_WARPED_FOREST_LOOP(new String[0]),
/*  298 */   AMBIENT_WARPED_FOREST_MOOD(new String[0]),
/*  299 */   BLOCK_ANCIENT_DEBRIS_BREAK(new String[0]),
/*  300 */   BLOCK_ANCIENT_DEBRIS_FALL(new String[0]),
/*  301 */   BLOCK_ANCIENT_DEBRIS_HIT(new String[0]),
/*  302 */   BLOCK_ANCIENT_DEBRIS_PLACE(new String[0]),
/*  303 */   BLOCK_ANCIENT_DEBRIS_STEP(new String[0]),
/*  304 */   BLOCK_BASALT_BREAK(new String[0]),
/*  305 */   BLOCK_BASALT_FALL(new String[0]),
/*  306 */   BLOCK_BASALT_HIT(new String[0]),
/*  307 */   BLOCK_BASALT_PLACE(new String[0]),
/*  308 */   BLOCK_BASALT_STEP(new String[0]),
/*  309 */   BLOCK_BONE_BLOCK_BREAK(new String[0]),
/*  310 */   BLOCK_BONE_BLOCK_FALL(new String[0]),
/*  311 */   BLOCK_BONE_BLOCK_HIT(new String[0]),
/*  312 */   BLOCK_BONE_BLOCK_PLACE(new String[0]),
/*  313 */   BLOCK_BONE_BLOCK_STEP(new String[0]),
/*  314 */   BLOCK_CHAIN_BREAK(new String[0]),
/*  315 */   BLOCK_CHAIN_FALL(new String[0]),
/*  316 */   BLOCK_CHAIN_HIT(new String[0]),
/*  317 */   BLOCK_CHAIN_PLACE(new String[0]),
/*  318 */   BLOCK_CHAIN_STEP(new String[0]),
/*  319 */   BLOCK_FUNGUS_BREAK(new String[0]),
/*  320 */   BLOCK_FUNGUS_FALL(new String[0]),
/*  321 */   BLOCK_FUNGUS_HIT(new String[0]),
/*  322 */   BLOCK_FUNGUS_PLACE(new String[0]),
/*  323 */   BLOCK_FUNGUS_STEP(new String[0]),
/*  324 */   BLOCK_LODESTONE_BREAK(new String[0]),
/*  325 */   BLOCK_LODESTONE_FALL(new String[0]),
/*  326 */   BLOCK_LODESTONE_HIT(new String[0]),
/*  327 */   BLOCK_LODESTONE_PLACE(new String[0]),
/*  328 */   BLOCK_LODESTONE_STEP(new String[0]),
/*  329 */   BLOCK_NETHERITE_BLOCK_BREAK(new String[0]),
/*  330 */   BLOCK_NETHERITE_BLOCK_FALL(new String[0]),
/*  331 */   BLOCK_NETHERITE_BLOCK_HIT(new String[0]),
/*  332 */   BLOCK_NETHERITE_BLOCK_PLACE(new String[0]),
/*  333 */   BLOCK_NETHERITE_BLOCK_STEP(new String[0]),
/*  334 */   BLOCK_NETHERRACK_BREAK(new String[0]),
/*  335 */   BLOCK_NETHERRACK_FALL(new String[0]),
/*  336 */   BLOCK_NETHERRACK_HIT(new String[0]),
/*  337 */   BLOCK_NETHERRACK_PLACE(new String[0]),
/*  338 */   BLOCK_NETHERRACK_STEP(new String[0]),
/*  339 */   BLOCK_NETHER_BRICKS_BREAK(new String[0]),
/*  340 */   BLOCK_NETHER_BRICKS_FALL(new String[0]),
/*  341 */   BLOCK_NETHER_BRICKS_HIT(new String[0]),
/*  342 */   BLOCK_NETHER_BRICKS_PLACE(new String[0]),
/*  343 */   BLOCK_NETHER_BRICKS_STEP(new String[0]),
/*  344 */   BLOCK_NETHER_GOLD_ORE_BREAK(new String[0]),
/*  345 */   BLOCK_NETHER_GOLD_ORE_FALL(new String[0]),
/*  346 */   BLOCK_NETHER_GOLD_ORE_HIT(new String[0]),
/*  347 */   BLOCK_NETHER_GOLD_ORE_PLACE(new String[0]),
/*  348 */   BLOCK_NETHER_GOLD_ORE_STEP(new String[0]),
/*  349 */   BLOCK_NETHER_ORE_BREAK(new String[0]),
/*  350 */   BLOCK_NETHER_ORE_FALL(new String[0]),
/*  351 */   BLOCK_NETHER_ORE_HIT(new String[0]),
/*  352 */   BLOCK_NETHER_ORE_PLACE(new String[0]),
/*  353 */   BLOCK_NETHER_ORE_STEP(new String[0]),
/*  354 */   BLOCK_NETHER_SPROUTS_BREAK(new String[0]),
/*  355 */   BLOCK_NETHER_SPROUTS_FALL(new String[0]),
/*  356 */   BLOCK_NETHER_SPROUTS_HIT(new String[0]),
/*  357 */   BLOCK_NETHER_SPROUTS_PLACE(new String[0]),
/*  358 */   BLOCK_NETHER_SPROUTS_STEP(new String[0]),
/*  359 */   BLOCK_NYLIUM_BREAK(new String[0]),
/*  360 */   BLOCK_NYLIUM_FALL(new String[0]),
/*  361 */   BLOCK_NYLIUM_HIT(new String[0]),
/*  362 */   BLOCK_NYLIUM_PLACE(new String[0]),
/*  363 */   BLOCK_NYLIUM_STEP(new String[0]),
/*  364 */   BLOCK_RESPAWN_ANCHOR_AMBIENT(new String[0]),
/*  365 */   BLOCK_RESPAWN_ANCHOR_CHARGE(new String[0]),
/*  366 */   BLOCK_RESPAWN_ANCHOR_DEPLETE(new String[0]),
/*  367 */   BLOCK_RESPAWN_ANCHOR_SET_SPAWN(new String[0]),
/*  368 */   BLOCK_ROOTS_BREAK(new String[0]),
/*  369 */   BLOCK_ROOTS_FALL(new String[0]),
/*  370 */   BLOCK_ROOTS_HIT(new String[0]),
/*  371 */   BLOCK_ROOTS_PLACE(new String[0]),
/*  372 */   BLOCK_ROOTS_STEP(new String[0]),
/*  373 */   BLOCK_SHROOMLIGHT_BREAK(new String[0]),
/*  374 */   BLOCK_SHROOMLIGHT_FALL(new String[0]),
/*  375 */   BLOCK_SHROOMLIGHT_HIT(new String[0]),
/*  376 */   BLOCK_SHROOMLIGHT_PLACE(new String[0]),
/*  377 */   BLOCK_SHROOMLIGHT_STEP(new String[0]),
/*  378 */   BLOCK_SMITHING_TABLE_USE(new String[0]),
/*  379 */   BLOCK_SOUL_SAND_BREAK(new String[0]),
/*  380 */   BLOCK_SOUL_SAND_FALL(new String[0]),
/*  381 */   BLOCK_SOUL_SAND_HIT(new String[0]),
/*  382 */   BLOCK_SOUL_SAND_PLACE(new String[0]),
/*  383 */   BLOCK_SOUL_SAND_STEP(new String[0]),
/*  384 */   BLOCK_SOUL_SOIL_BREAK(new String[0]),
/*  385 */   BLOCK_SOUL_SOIL_FALL(new String[0]),
/*  386 */   BLOCK_SOUL_SOIL_HIT(new String[0]),
/*  387 */   BLOCK_SOUL_SOIL_PLACE(new String[0]),
/*  388 */   BLOCK_SOUL_SOIL_STEP(new String[0]),
/*  389 */   BLOCK_STEM_BREAK(new String[0]),
/*  390 */   BLOCK_STEM_FALL(new String[0]),
/*  391 */   BLOCK_STEM_HIT(new String[0]),
/*  392 */   BLOCK_STEM_PLACE(new String[0]),
/*  393 */   BLOCK_STEM_STEP(new String[0]),
/*  394 */   BLOCK_VINE_STEP(new String[0]),
/*  395 */   BLOCK_WART_BLOCK_BREAK(new String[0]),
/*  396 */   BLOCK_WART_BLOCK_FALL(new String[0]),
/*  397 */   BLOCK_WART_BLOCK_HIT(new String[0]),
/*  398 */   BLOCK_WART_BLOCK_PLACE(new String[0]),
/*  399 */   BLOCK_WART_BLOCK_STEP(new String[0]),
/*  400 */   ENTITY_DONKEY_EAT(new String[0]),
/*  401 */   ENTITY_FOX_TELEPORT(new String[0]),
/*  402 */   ENTITY_HOGLIN_AMBIENT(new String[0]),
/*  403 */   ENTITY_HOGLIN_ANGRY(new String[0]),
/*  404 */   ENTITY_HOGLIN_ATTACK(new String[0]),
/*  405 */   ENTITY_HOGLIN_CONVERTED_TO_ZOMBIFIED(new String[0]),
/*  406 */   ENTITY_HOGLIN_DEATH(new String[0]),
/*  407 */   ENTITY_HOGLIN_HURT(new String[0]),
/*  408 */   ENTITY_HOGLIN_RETREAT(new String[0]),
/*  409 */   ENTITY_HOGLIN_STEP(new String[0]),
/*  410 */   ENTITY_MULE_EAT(new String[0]),
/*  411 */   ENTITY_MULE_ANGRY(new String[0]),
/*  412 */   ENTITY_PARROT_IMITATE_HOGLIN(new String[0]),
/*  413 */   ENTITY_PARROT_IMITATE_PIGLIN(new String[0]),
/*  414 */   ENTITY_PARROT_IMITATE_ZOGLIN(new String[0]),
/*  415 */   ENTITY_PIGLIN_ADMIRING_ITEM(new String[0]),
/*  416 */   ENTITY_PIGLIN_AMBIENT(new String[0]),
/*  417 */   ENTITY_PIGLIN_ANGRY(new String[0]),
/*  418 */   ENTITY_PIGLIN_CELEBRATE(new String[0]),
/*  419 */   ENTITY_PIGLIN_CONVERTED_TO_ZOMBIFIED(new String[0]),
/*  420 */   ENTITY_PIGLIN_DEATH(new String[0]),
/*  421 */   ENTITY_PIGLIN_HURT(new String[0]),
/*  422 */   ENTITY_PIGLIN_JEALOUS(new String[0]),
/*  423 */   ENTITY_PIGLIN_RETREAT(new String[0]),
/*  424 */   ENTITY_PIGLIN_STEP(new String[0]),
/*  425 */   ENTITY_SNOW_GOLEM_SHEAR(new String[0]),
/*  426 */   ENTITY_STRIDER_AMBIENT(new String[0]),
/*  427 */   ENTITY_STRIDER_DEATH(new String[0]),
/*  428 */   ENTITY_STRIDER_EAT(new String[0]),
/*  429 */   ENTITY_STRIDER_HAPPY(new String[0]),
/*  430 */   ENTITY_STRIDER_HURT(new String[0]),
/*  431 */   ENTITY_STRIDER_RETREAT(new String[0]),
/*  432 */   ENTITY_STRIDER_SADDLE(new String[0]),
/*  433 */   ENTITY_STRIDER_STEP(new String[0]),
/*  434 */   ENTITY_STRIDER_STEP_LAVA(new String[0]),
/*  435 */   ENTITY_ZOGLIN_AMBIENT(new String[0]),
/*  436 */   ENTITY_ZOGLIN_ANGRY(new String[0]),
/*  437 */   ENTITY_ZOGLIN_ATTACK(new String[0]),
/*  438 */   ENTITY_ZOGLIN_DEATH(new String[0]),
/*  439 */   ENTITY_ZOGLIN_HURT(new String[0]),
/*  440 */   ENTITY_ZOGLIN_STEP(new String[0]),
/*  441 */   BLOCK_WEEPING_VINES_BREAK(new String[0]),
/*  442 */   BLOCK_WEEPING_VINES_FALL(new String[0]),
/*  443 */   BLOCK_WEEPING_VINES_HIT(new String[0]),
/*  444 */   BLOCK_WEEPING_VINES_PLACE(new String[0]),
/*  445 */   BLOCK_WEEPING_VINES_STEP(new String[0]),
/*  446 */   BLOCK_GILDED_BLACKSTONE_BREAK(new String[0]),
/*  447 */   BLOCK_GILDED_BLACKSTONE_FALL(new String[0]),
/*  448 */   BLOCK_GILDED_BLACKSTONE_HIT(new String[0]),
/*  449 */   BLOCK_GILDED_BLACKSTONE_PLACE(new String[0]),
/*  450 */   BLOCK_GILDED_BLACKSTONE_STEP(new String[0]),
/*  451 */   ENTITY_CAT_DEATH(new String[0]),
/*  452 */   ENTITY_CAT_EAT(new String[0]),
/*  453 */   ENTITY_CAT_HISS(new String[] { "CAT_HISS" }),
/*  454 */   ENTITY_CAT_HURT(new String[] { "CAT_HIT" }),
/*  455 */   ENTITY_CAT_PURR(new String[] { "CAT_PURR" }),
/*  456 */   ENTITY_CAT_PURREOW(new String[] { "CAT_PURREOW" }),
/*  457 */   ENTITY_CAT_STRAY_AMBIENT(new String[0]),
/*  458 */   ENTITY_CHICKEN_AMBIENT(new String[] { "CHICKEN_IDLE" }),
/*  459 */   ENTITY_CHICKEN_DEATH(new String[0]),
/*  460 */   ENTITY_CHICKEN_EGG(new String[] { "CHICKEN_EGG_POP" }),
/*  461 */   ENTITY_CHICKEN_HURT(new String[] { "CHICKEN_HURT" }),
/*  462 */   ENTITY_CHICKEN_STEP(new String[] { "CHICKEN_WALK" }),
/*  463 */   ENTITY_COD_AMBIENT(new String[0]),
/*  464 */   ENTITY_COD_DEATH(new String[0]),
/*  465 */   ENTITY_COD_FLOP(new String[0]),
/*  466 */   ENTITY_COD_HURT(new String[0]),
/*  467 */   ENTITY_COW_AMBIENT(new String[] { "COW_IDLE" }),
/*  468 */   ENTITY_COW_DEATH(new String[0]),
/*  469 */   ENTITY_COW_HURT(new String[] { "COW_HURT" }),
/*  470 */   ENTITY_COW_MILK(new String[0]),
/*  471 */   ENTITY_COW_STEP(new String[] { "COW_WALK" }),
/*  472 */   ENTITY_CREEPER_DEATH(new String[] { "CREEPER_DEATH" }),
/*  473 */   ENTITY_CREEPER_HURT(new String[0]),
/*  474 */   ENTITY_CREEPER_PRIMED(new String[] { "CREEPER_HISS" }),
/*  475 */   ENTITY_DOLPHIN_AMBIENT(new String[0]),
/*  476 */   ENTITY_DOLPHIN_AMBIENT_WATER(new String[0]),
/*  477 */   ENTITY_DOLPHIN_ATTACK(new String[0]),
/*  478 */   ENTITY_DOLPHIN_DEATH(new String[0]),
/*  479 */   ENTITY_DOLPHIN_EAT(new String[0]),
/*  480 */   ENTITY_DOLPHIN_HURT(new String[0]),
/*  481 */   ENTITY_DOLPHIN_JUMP(new String[0]),
/*  482 */   ENTITY_DOLPHIN_PLAY(new String[0]),
/*  483 */   ENTITY_DOLPHIN_SPLASH(new String[0]),
/*  484 */   ENTITY_DOLPHIN_SWIM(new String[0]),
/*  485 */   ENTITY_DONKEY_AMBIENT(new String[] { "DONKEY_IDLE" }),
/*  486 */   ENTITY_DONKEY_ANGRY(new String[] { "DONKEY_ANGRY" }),
/*  487 */   ENTITY_DONKEY_CHEST(new String[0]),
/*  488 */   ENTITY_DONKEY_DEATH(new String[] { "DONKEY_DEATH" }),
/*  489 */   ENTITY_DONKEY_HURT(new String[] { "DONKEY_HIT" }),
/*  490 */   ENTITY_DRAGON_FIREBALL_EXPLODE(new String[] { "ENTITY_ENDERDRAGON_FIREBALL_EXPLODE" }),
/*  491 */   ENTITY_DROWNED_AMBIENT(new String[0]),
/*  492 */   ENTITY_DROWNED_AMBIENT_WATER(new String[0]),
/*  493 */   ENTITY_DROWNED_DEATH(new String[0]),
/*  494 */   ENTITY_DROWNED_DEATH_WATER(new String[0]),
/*  495 */   ENTITY_DROWNED_HURT(new String[0]),
/*  496 */   ENTITY_DROWNED_HURT_WATER(new String[0]),
/*  497 */   ENTITY_DROWNED_SHOOT(new String[0]),
/*  498 */   ENTITY_DROWNED_STEP(new String[0]),
/*  499 */   ENTITY_DROWNED_SWIM(new String[0]),
/*  500 */   ENTITY_EGG_THROW(new String[0]),
/*  501 */   ENTITY_ELDER_GUARDIAN_AMBIENT(new String[0]),
/*  502 */   ENTITY_ELDER_GUARDIAN_AMBIENT_LAND(new String[0]),
/*  503 */   ENTITY_ELDER_GUARDIAN_CURSE(new String[0]),
/*  504 */   ENTITY_ELDER_GUARDIAN_DEATH(new String[0]),
/*  505 */   ENTITY_ELDER_GUARDIAN_DEATH_LAND(new String[0]),
/*  506 */   ENTITY_ELDER_GUARDIAN_FLOP(new String[0]),
/*  507 */   ENTITY_ELDER_GUARDIAN_HURT(new String[0]),
/*  508 */   ENTITY_ELDER_GUARDIAN_HURT_LAND(new String[0]),
/*  509 */   ENTITY_ENDERMAN_AMBIENT(new String[] { "ENDERMAN_IDLE", "ENTITY_ENDERMEN_AMBIENT" }),
/*  510 */   ENTITY_ENDERMAN_DEATH(new String[] { "ENDERMAN_DEATH", "ENTITY_ENDERMEN_DEATH" }),
/*  511 */   ENTITY_ENDERMAN_HURT(new String[] { "ENDERMAN_HIT", "ENTITY_ENDERMEN_HURT" }),
/*  512 */   ENTITY_ENDERMAN_SCREAM(new String[] { "ENDERMAN_SCREAM", "ENTITY_ENDERMEN_SCREAM" }),
/*  513 */   ENTITY_ENDERMAN_STARE(new String[] { "ENDERMAN_STARE", "ENTITY_ENDERMEN_STARE" }),
/*  514 */   ENTITY_ENDERMAN_TELEPORT(new String[] { "ENDERMAN_TELEPORT", "ENTITY_ENDERMEN_TELEPORT" }),
/*  515 */   ENTITY_ENDERMITE_AMBIENT(new String[0]),
/*  516 */   ENTITY_ENDERMITE_DEATH(new String[0]),
/*  517 */   ENTITY_ENDERMITE_HURT(new String[0]),
/*  518 */   ENTITY_ENDERMITE_STEP(new String[0]),
/*  519 */   ENTITY_ENDER_DRAGON_AMBIENT(new String[] { "ENDERDRAGON_WINGS", "ENTITY_ENDERDRAGON_AMBIENT" }),
/*  520 */   ENTITY_ENDER_DRAGON_DEATH(new String[] { "ENDERDRAGON_DEATH", "ENTITY_ENDERDRAGON_DEATH" }),
/*  521 */   ENTITY_ENDER_DRAGON_FLAP(new String[] { "ENDERDRAGON_WINGS", "ENTITY_ENDERDRAGON_FLAP" }),
/*  522 */   ENTITY_ENDER_DRAGON_GROWL(new String[] { "ENDERDRAGON_GROWL", "ENTITY_ENDERDRAGON_GROWL" }),
/*  523 */   ENTITY_ENDER_DRAGON_HURT(new String[] { "ENDERDRAGON_HIT", "ENTITY_ENDERDRAGON_HURT" }),
/*  524 */   ENTITY_ENDER_DRAGON_SHOOT(new String[] { "ENTITY_ENDERDRAGON_SHOOT" }),
/*  525 */   ENTITY_ENDER_EYE_DEATH(new String[0]),
/*  526 */   ENTITY_ENDER_EYE_LAUNCH(new String[] { "ENTITY_ENDER_EYE_DEATH", "ENTITY_ENDEREYE_DEATH" }),
/*  527 */   ENTITY_ENDER_PEARL_THROW(new String[] { "ENTITY_ENDERPEARL_THROW" }),
/*  528 */   ENTITY_EVOKER_AMBIENT(new String[] { "ENTITY_EVOCATION_ILLAGER_AMBIENT" }),
/*  529 */   ENTITY_EVOKER_CAST_SPELL(new String[] { "ENTITY_EVOCATION_ILLAGER_CAST_SPELL" }),
/*  530 */   ENTITY_EVOKER_CELEBRATE(new String[0]),
/*  531 */   ENTITY_EVOKER_DEATH(new String[] { "ENTITY_EVOCATION_ILLAGER_DEATH" }),
/*  532 */   ENTITY_EVOKER_FANGS_ATTACK(new String[] { "ENTITY_EVOCATION_FANGS_ATTACK" }),
/*  533 */   ENTITY_EVOKER_HURT(new String[] { "ENTITY_EVOCATION_ILLAGER_HURT" }),
/*  534 */   ENTITY_EVOKER_PREPARE_ATTACK(new String[] { "ENTITY_EVOCATION_ILLAGER_PREPARE_ATTACK" }),
/*  535 */   ENTITY_EVOKER_PREPARE_SUMMON(new String[] { "ENTITY_EVOCATION_ILLAGER_PREPARE_SUMMON" }),
/*  536 */   ENTITY_EVOKER_PREPARE_WOLOLO(new String[] { "ENTITY_EVOCATION_ILLAGER_PREPARE_WOLOLO" }),
/*  537 */   ENTITY_EXPERIENCE_BOTTLE_THROW(new String[0]),
/*  538 */   ENTITY_EXPERIENCE_ORB_PICKUP(new String[] { "ORB_PICKUP" }),
/*  539 */   ENTITY_FIREWORK_ROCKET_BLAST(new String[] { "FIREWORK_BLAST", "ENTITY_FIREWORK_BLAST" }),
/*  540 */   ENTITY_FIREWORK_ROCKET_BLAST_FAR(new String[] { "FIREWORK_BLAST2", "ENTITY_FIREWORK_BLAST_FAR" }),
/*  541 */   ENTITY_FIREWORK_ROCKET_LARGE_BLAST(new String[] { "FIREWORK_LARGE_BLAST", "ENTITY_FIREWORK_LARGE_BLAST" }),
/*  542 */   ENTITY_FIREWORK_ROCKET_LARGE_BLAST_FAR(new String[] { "FIREWORK_LARGE_BLAST2", "ENTITY_FIREWORK_LARGE_BLAST_FAR" }),
/*  543 */   ENTITY_FIREWORK_ROCKET_LAUNCH(new String[] { "FIREWORK_LAUNCH", "ENTITY_FIREWORK_LAUNCH" }),
/*  544 */   ENTITY_FIREWORK_ROCKET_SHOOT(new String[0]),
/*  545 */   ENTITY_FIREWORK_ROCKET_TWINKLE(new String[] { "FIREWORK_TWINKLE", "ENTITY_FIREWORK_TWINKLE" }),
/*  546 */   ENTITY_FIREWORK_ROCKET_TWINKLE_FAR(new String[] { "FIREWORK_TWINKLE2", "ENTITY_FIREWORK_TWINKLE_FAR" }),
/*  547 */   ENTITY_FISHING_BOBBER_RETRIEVE(new String[0]),
/*  548 */   ENTITY_FISHING_BOBBER_SPLASH(new String[] { "SPLASH2", "ENTITY_BOBBER_SPLASH" }),
/*  549 */   ENTITY_FISHING_BOBBER_THROW(new String[] { "ENTITY_BOBBER_THROW" }),
/*  550 */   ENTITY_FISH_SWIM(new String[0]),
/*  551 */   ENTITY_FOX_AGGRO(new String[0]),
/*  552 */   ENTITY_FOX_AMBIENT(new String[0]),
/*  553 */   ENTITY_FOX_BITE(new String[0]),
/*  554 */   ENTITY_FOX_DEATH(new String[0]),
/*  555 */   ENTITY_FOX_EAT(new String[0]),
/*  556 */   ENTITY_FOX_HURT(new String[0]),
/*  557 */   ENTITY_FOX_SCREECH(new String[0]),
/*  558 */   ENTITY_FOX_SLEEP(new String[0]),
/*  559 */   ENTITY_FOX_SNIFF(new String[0]),
/*  560 */   ENTITY_FOX_SPIT(new String[0]),
/*  561 */   ENTITY_GENERIC_BIG_FALL(new String[] { "FALL_BIG" }),
/*  562 */   ENTITY_GENERIC_BURN(new String[0]),
/*  563 */   ENTITY_GENERIC_DEATH(new String[0]),
/*  564 */   ENTITY_GENERIC_DRINK(new String[] { "DRINK" }),
/*  565 */   ENTITY_GENERIC_EAT(new String[] { "EAT" }),
/*  566 */   ENTITY_GENERIC_EXPLODE(new String[] { "EXPLODE" }),
/*  567 */   ENTITY_GENERIC_EXTINGUISH_FIRE(new String[0]),
/*  568 */   ENTITY_GENERIC_HURT(new String[0]),
/*  569 */   ENTITY_GENERIC_SMALL_FALL(new String[] { "FALL_SMALL" }),
/*  570 */   ENTITY_GENERIC_SPLASH(new String[] { "SPLASH" }),
/*  571 */   ENTITY_GENERIC_SWIM(new String[] { "SWIM" }),
/*  572 */   ENTITY_GHAST_AMBIENT(new String[] { "GHAST_MOAN" }),
/*  573 */   ENTITY_GHAST_DEATH(new String[] { "GHAST_DEATH" }),
/*  574 */   ENTITY_GHAST_HURT(new String[] { "GHAST_SCREAM2" }),
/*  575 */   ENTITY_GHAST_SCREAM(new String[] { "GHAST_SCREAM" }),
/*  576 */   ENTITY_GHAST_SHOOT(new String[] { "GHAST_FIREBALL" }),
/*  577 */   ENTITY_GHAST_WARN(new String[] { "GHAST_CHARGE" }),
/*  578 */   ENTITY_GUARDIAN_AMBIENT(new String[0]),
/*  579 */   ENTITY_GUARDIAN_AMBIENT_LAND(new String[0]),
/*  580 */   ENTITY_GUARDIAN_ATTACK(new String[0]),
/*  581 */   ENTITY_GUARDIAN_DEATH(new String[0]),
/*  582 */   ENTITY_GUARDIAN_DEATH_LAND(new String[0]),
/*  583 */   ENTITY_GUARDIAN_FLOP(new String[0]),
/*  584 */   ENTITY_GUARDIAN_HURT(new String[0]),
/*  585 */   ENTITY_GUARDIAN_HURT_LAND(new String[0]),
/*  586 */   ENTITY_HORSE_AMBIENT(new String[] { "HORSE_IDLE" }),
/*  587 */   ENTITY_HORSE_ANGRY(new String[] { "HORSE_ANGRY" }),
/*  588 */   ENTITY_HORSE_ARMOR(new String[] { "HORSE_ARMOR" }),
/*  589 */   ENTITY_HORSE_BREATHE(new String[] { "HORSE_BREATHE" }),
/*  590 */   ENTITY_HORSE_DEATH(new String[] { "HORSE_DEATH" }),
/*  591 */   ENTITY_HORSE_EAT(new String[0]),
/*  592 */   ENTITY_HORSE_GALLOP(new String[] { "HORSE_GALLOP" }),
/*  593 */   ENTITY_HORSE_HURT(new String[] { "HORSE_HIT" }),
/*  594 */   ENTITY_HORSE_JUMP(new String[] { "HORSE_JUMP" }),
/*  595 */   ENTITY_HORSE_LAND(new String[] { "HORSE_LAND" }),
/*  596 */   ENTITY_HORSE_SADDLE(new String[] { "HORSE_SADDLE" }),
/*  597 */   ENTITY_HORSE_STEP(new String[] { "HORSE_SOFT" }),
/*  598 */   ENTITY_HORSE_STEP_WOOD(new String[] { "HORSE_WOOD" }),
/*  599 */   ENTITY_HOSTILE_BIG_FALL(new String[] { "FALL_BIG" }),
/*  600 */   ENTITY_HOSTILE_DEATH(new String[0]),
/*  601 */   ENTITY_HOSTILE_HURT(new String[0]),
/*  602 */   ENTITY_HOSTILE_SMALL_FALL(new String[] { "FALL_SMALL" }),
/*  603 */   ENTITY_HOSTILE_SPLASH(new String[] { "SPLASH" }),
/*  604 */   ENTITY_HOSTILE_SWIM(new String[] { "SWIM" }),
/*  605 */   ENTITY_HUSK_AMBIENT(new String[0]),
/*  606 */   ENTITY_HUSK_CONVERTED_TO_ZOMBIE(new String[0]),
/*  607 */   ENTITY_HUSK_DEATH(new String[0]),
/*  608 */   ENTITY_HUSK_HURT(new String[0]),
/*  609 */   ENTITY_HUSK_STEP(new String[0]),
/*  610 */   ENTITY_ILLUSIONER_AMBIENT(new String[] { "ENTITY_ILLUSION_ILLAGER_AMBIENT" }),
/*  611 */   ENTITY_ILLUSIONER_CAST_SPELL(new String[] { "ENTITY_ILLUSION_ILLAGER_CAST_SPELL" }),
/*  612 */   ENTITY_ILLUSIONER_DEATH(new String[] { "ENTITY_ILLUSIONER_CAST_DEATH", "ENTITY_ILLUSION_ILLAGER_DEATH" }),
/*  613 */   ENTITY_ILLUSIONER_HURT(new String[] { "ENTITY_ILLUSION_ILLAGER_HURT" }),
/*  614 */   ENTITY_ILLUSIONER_MIRROR_MOVE(new String[] { "ENTITY_ILLUSION_ILLAGER_MIRROR_MOVE" }),
/*  615 */   ENTITY_ILLUSIONER_PREPARE_BLINDNESS(new String[] { "ENTITY_ILLUSION_ILLAGER_PREPARE_BLINDNESS" }),
/*  616 */   ENTITY_ILLUSIONER_PREPARE_MIRROR(new String[] { "ENTITY_ILLUSION_ILLAGER_PREPARE_MIRROR" }),
/*  617 */   ENTITY_IRON_GOLEM_ATTACK(new String[] { "IRONGOLEM_THROW", "ENTITY_IRONGOLEM_ATTACK" }),
/*  618 */   ENTITY_IRON_GOLEM_DAMAGE(new String[0]),
/*  619 */   ENTITY_IRON_GOLEM_DEATH(new String[] { "IRONGOLEM_DEATH", "ENTITY_IRONGOLEM_DEATH" }),
/*  620 */   ENTITY_IRON_GOLEM_HURT(new String[] { "IRONGOLEM_HIT", "ENTITY_IRONGOLEM_HURT" }),
/*  621 */   ENTITY_IRON_GOLEM_REPAIR(new String[0]),
/*  622 */   ENTITY_IRON_GOLEM_STEP(new String[] { "IRONGOLEM_WALK", "ENTITY_IRONGOLEM_STEP" }),
/*  623 */   ENTITY_ITEM_BREAK(new String[] { "ITEM_BREAK" }),
/*  624 */   ENTITY_ITEM_FRAME_ADD_ITEM(new String[] { "ENTITY_ITEMFRAME_ADD_ITEM" }),
/*  625 */   ENTITY_ITEM_FRAME_BREAK(new String[] { "ENTITY_ITEMFRAME_BREAK" }),
/*  626 */   ENTITY_ITEM_FRAME_PLACE(new String[] { "ENTITY_ITEMFRAME_PLACE" }),
/*  627 */   ENTITY_ITEM_FRAME_REMOVE_ITEM(new String[] { "ENTITY_ITEMFRAME_REMOVE_ITEM" }),
/*  628 */   ENTITY_ITEM_FRAME_ROTATE_ITEM(new String[] { "ENTITY_ITEMFRAME_ROTATE_ITEM" }),
/*  629 */   ENTITY_ITEM_PICKUP(new String[] { "ITEM_PICKUP" }),
/*  630 */   ENTITY_LEASH_KNOT_BREAK(new String[] { "ENTITY_LEASHKNOT_BREAK" }),
/*  631 */   ENTITY_LEASH_KNOT_PLACE(new String[] { "ENTITY_LEASHKNOT_PLACE" }),
/*  632 */   ENTITY_LIGHTNING_BOLT_IMPACT(new String[] { "AMBIENCE_THUNDER", "ENTITY_LIGHTNING_IMPACT" }),
/*  633 */   ENTITY_LIGHTNING_BOLT_THUNDER(new String[] { "AMBIENCE_THUNDER", "ENTITY_LIGHTNING_THUNDER" }),
/*  634 */   ENTITY_LINGERING_POTION_THROW(new String[0]),
/*  635 */   ENTITY_LLAMA_AMBIENT(new String[0]),
/*  636 */   ENTITY_LLAMA_ANGRY(new String[0]),
/*  637 */   ENTITY_LLAMA_CHEST(new String[0]),
/*  638 */   ENTITY_LLAMA_DEATH(new String[0]),
/*  639 */   ENTITY_LLAMA_EAT(new String[0]),
/*  640 */   ENTITY_LLAMA_HURT(new String[0]),
/*  641 */   ENTITY_LLAMA_SPIT(new String[0]),
/*  642 */   ENTITY_LLAMA_STEP(new String[0]),
/*  643 */   ENTITY_LLAMA_SWAG(new String[0]),
/*  644 */   ENTITY_MAGMA_CUBE_DEATH(new String[] { "ENTITY_MAGMACUBE_DEATH" }),
/*  645 */   ENTITY_MAGMA_CUBE_DEATH_SMALL(new String[] { "ENTITY_SMALL_MAGMACUBE_DEATH" }),
/*  646 */   ENTITY_MAGMA_CUBE_HURT(new String[] { "ENTITY_MAGMACUBE_HURT" }),
/*  647 */   ENTITY_MAGMA_CUBE_HURT_SMALL(new String[] { "ENTITY_SMALL_MAGMACUBE_HURT" }),
/*  648 */   ENTITY_MAGMA_CUBE_JUMP(new String[] { "MAGMACUBE_JUMP", "ENTITY_MAGMACUBE_JUMP" }),
/*  649 */   ENTITY_MAGMA_CUBE_SQUISH(new String[] { "MAGMACUBE_WALK", "ENTITY_MAGMACUBE_SQUISH" }),
/*  650 */   ENTITY_MAGMA_CUBE_SQUISH_SMALL(new String[] { "MAGMACUBE_WALK2", "ENTITY_SMALL_MAGMACUBE_SQUISH" }),
/*  651 */   ENTITY_MINECART_INSIDE(new String[] { "MINECART_INSIDE" }),
/*  652 */   ENTITY_MINECART_RIDING(new String[] { "MINECART_BASE" }),
/*  653 */   ENTITY_MOOSHROOM_CONVERT(new String[0]),
/*  654 */   ENTITY_MOOSHROOM_EAT(new String[0]),
/*  655 */   ENTITY_MOOSHROOM_MILK(new String[0]),
/*  656 */   ENTITY_MOOSHROOM_SHEAR(new String[0]),
/*  657 */   ENTITY_MOOSHROOM_SUSPICIOUS_MILK(new String[0]),
/*  658 */   ENTITY_MULE_AMBIENT(new String[0]),
/*  659 */   ENTITY_MULE_CHEST(new String[] { "ENTITY_MULE_AMBIENT" }),
/*  660 */   ENTITY_MULE_DEATH(new String[] { "ENTITY_MULE_AMBIENT" }),
/*  661 */   ENTITY_MULE_HURT(new String[] { "ENTITY_MULE_AMBIENT" }),
/*  662 */   ENTITY_OCELOT_AMBIENT(new String[0]),
/*  663 */   ENTITY_OCELOT_DEATH(new String[0]),
/*  664 */   ENTITY_OCELOT_HURT(new String[0]),
/*  665 */   ENTITY_PAINTING_BREAK(new String[0]),
/*  666 */   ENTITY_PAINTING_PLACE(new String[0]),
/*  667 */   ENTITY_PANDA_AGGRESSIVE_AMBIENT(new String[0]),
/*  668 */   ENTITY_PANDA_AMBIENT(new String[0]),
/*  669 */   ENTITY_PANDA_BITE(new String[0]),
/*  670 */   ENTITY_PANDA_CANT_BREED(new String[0]),
/*  671 */   ENTITY_PANDA_DEATH(new String[0]),
/*  672 */   ENTITY_PANDA_EAT(new String[0]),
/*  673 */   ENTITY_PANDA_HURT(new String[0]),
/*  674 */   ENTITY_PANDA_PRE_SNEEZE(new String[0]),
/*  675 */   ENTITY_PANDA_SNEEZE(new String[0]),
/*  676 */   ENTITY_PANDA_STEP(new String[0]),
/*  677 */   ENTITY_PANDA_WORRIED_AMBIENT(new String[0]),
/*  678 */   ENTITY_PARROT_AMBIENT(new String[0]),
/*  679 */   ENTITY_PARROT_DEATH(new String[0]),
/*  680 */   ENTITY_PARROT_EAT(new String[0]),
/*  681 */   ENTITY_PARROT_FLY(new String[0]),
/*  682 */   ENTITY_PARROT_HURT(new String[0]),
/*  683 */   ENTITY_PARROT_IMITATE_BLAZE(new String[0]),
/*  684 */   ENTITY_PARROT_IMITATE_CREEPER(new String[0]),
/*  685 */   ENTITY_PARROT_IMITATE_DROWNED(new String[0]),
/*  686 */   ENTITY_PARROT_IMITATE_ELDER_GUARDIAN(new String[0]),
/*  687 */   ENTITY_PARROT_IMITATE_ENDERMAN(new String[0]),
/*  688 */   ENTITY_PARROT_IMITATE_ENDERMITE(new String[0]),
/*  689 */   ENTITY_PARROT_IMITATE_ENDER_DRAGON(new String[0]),
/*  690 */   ENTITY_PARROT_IMITATE_EVOKER(new String[0]),
/*  691 */   ENTITY_PARROT_IMITATE_GHAST(new String[0]),
/*  692 */   ENTITY_PARROT_IMITATE_GUARDIAN(new String[0]),
/*  693 */   ENTITY_PARROT_IMITATE_HUSK(new String[0]),
/*  694 */   ENTITY_PARROT_IMITATE_ILLUSIONER(new String[0]),
/*  695 */   ENTITY_PARROT_IMITATE_MAGMA_CUBE(new String[0]),
/*  696 */   ENTITY_PARROT_IMITATE_PHANTOM(new String[0]),
/*  697 */   ENTITY_PARROT_IMITATE_PILLAGER(new String[0]),
/*  698 */   ENTITY_PARROT_IMITATE_POLAR_BEAR(new String[0]),
/*  699 */   ENTITY_PARROT_IMITATE_RAVAGER(new String[0]),
/*  700 */   ENTITY_PARROT_IMITATE_SHULKER(new String[0]),
/*  701 */   ENTITY_PARROT_IMITATE_SILVERFISH(new String[0]),
/*  702 */   ENTITY_PARROT_IMITATE_SKELETON(new String[0]),
/*  703 */   ENTITY_PARROT_IMITATE_SLIME(new String[0]),
/*  704 */   ENTITY_PARROT_IMITATE_SPIDER(new String[0]),
/*  705 */   ENTITY_PARROT_IMITATE_STRAY(new String[0]),
/*  706 */   ENTITY_PARROT_IMITATE_VEX(new String[0]),
/*  707 */   ENTITY_PARROT_IMITATE_VINDICATOR(new String[0]),
/*  708 */   ENTITY_PARROT_IMITATE_WITCH(new String[0]),
/*  709 */   ENTITY_PARROT_IMITATE_WITHER(new String[0]),
/*  710 */   ENTITY_PARROT_IMITATE_WITHER_SKELETON(new String[0]),
/*  711 */   ENTITY_PARROT_IMITATE_WOLF(new String[0]),
/*  712 */   ENTITY_PARROT_IMITATE_ZOMBIE(new String[0]),
/*  713 */   ENTITY_PARROT_IMITATE_ZOMBIE_VILLAGER(new String[0]),
/*  714 */   ENTITY_PARROT_STEP(new String[0]),
/*  715 */   ENTITY_PHANTOM_AMBIENT(new String[0]),
/*  716 */   ENTITY_PHANTOM_BITE(new String[0]),
/*  717 */   ENTITY_PHANTOM_DEATH(new String[0]),
/*  718 */   ENTITY_PHANTOM_FLAP(new String[0]),
/*  719 */   ENTITY_PHANTOM_HURT(new String[0]),
/*  720 */   ENTITY_PHANTOM_SWOOP(new String[0]),
/*  721 */   ENTITY_PIG_AMBIENT(new String[] { "PIG_IDLE" }),
/*  722 */   ENTITY_PIG_DEATH(new String[] { "PIG_DEATH" }),
/*  723 */   ENTITY_PIG_HURT(new String[0]),
/*  724 */   ENTITY_PIG_SADDLE(new String[] { "ENTITY_PIG_HURT" }),
/*  725 */   ENTITY_PIG_STEP(new String[] { "PIG_WALK" }),
/*  726 */   ENTITY_PILLAGER_AMBIENT(new String[0]),
/*  727 */   ENTITY_PILLAGER_CELEBRATE(new String[0]),
/*  728 */   ENTITY_PILLAGER_DEATH(new String[0]),
/*  729 */   ENTITY_PILLAGER_HURT(new String[0]),
/*  730 */   ENTITY_PLAYER_ATTACK_CRIT(new String[0]),
/*  731 */   ENTITY_PLAYER_ATTACK_KNOCKBACK(new String[0]),
/*  732 */   ENTITY_PLAYER_ATTACK_NODAMAGE(new String[0]),
/*  733 */   ENTITY_PLAYER_ATTACK_STRONG(new String[] { "SUCCESSFUL_HIT" }),
/*  734 */   ENTITY_PLAYER_ATTACK_SWEEP(new String[0]),
/*  735 */   ENTITY_PLAYER_ATTACK_WEAK(new String[0]),
/*  736 */   ENTITY_PLAYER_BIG_FALL(new String[] { "FALL_BIG" }),
/*  737 */   ENTITY_PLAYER_BREATH(new String[0]),
/*  738 */   ENTITY_PLAYER_BURP(new String[] { "BURP" }),
/*  739 */   ENTITY_PLAYER_DEATH(new String[0]),
/*  740 */   ENTITY_PLAYER_HURT(new String[] { "HURT_FLESH" }),
/*  741 */   ENTITY_PLAYER_HURT_DROWN(new String[0]),
/*  742 */   ENTITY_PLAYER_HURT_ON_FIRE(new String[0]),
/*  743 */   ENTITY_PLAYER_HURT_SWEET_BERRY_BUSH(new String[0]),
/*  744 */   ENTITY_PLAYER_LEVELUP(new String[] { "LEVEL_UP" }),
/*  745 */   ENTITY_PLAYER_SMALL_FALL(new String[] { "FALL_SMALL" }),
/*  746 */   ENTITY_PLAYER_SPLASH(new String[] { "SLASH" }),
/*  747 */   ENTITY_PLAYER_SPLASH_HIGH_SPEED(new String[] { "SPLASH" }),
/*  748 */   ENTITY_PLAYER_SWIM(new String[] { "SWIM" }),
/*  749 */   ENTITY_POLAR_BEAR_AMBIENT(new String[0]),
/*  750 */   ENTITY_POLAR_BEAR_AMBIENT_BABY(new String[] { "ENTITY_POLAR_BEAR_BABY_AMBIENT" }),
/*  751 */   ENTITY_POLAR_BEAR_DEATH(new String[0]),
/*  752 */   ENTITY_POLAR_BEAR_HURT(new String[0]),
/*  753 */   ENTITY_POLAR_BEAR_STEP(new String[0]),
/*  754 */   ENTITY_POLAR_BEAR_WARNING(new String[0]),
/*  755 */   ENTITY_PUFFER_FISH_AMBIENT(new String[0]),
/*  756 */   ENTITY_PUFFER_FISH_BLOW_OUT(new String[0]),
/*  757 */   ENTITY_PUFFER_FISH_BLOW_UP(new String[0]),
/*  758 */   ENTITY_PUFFER_FISH_DEATH(new String[0]),
/*  759 */   ENTITY_PUFFER_FISH_FLOP(new String[0]),
/*  760 */   ENTITY_PUFFER_FISH_HURT(new String[0]),
/*  761 */   ENTITY_PUFFER_FISH_STING(new String[0]),
/*  762 */   ENTITY_RABBIT_AMBIENT(new String[0]),
/*  763 */   ENTITY_RABBIT_ATTACK(new String[0]),
/*  764 */   ENTITY_RABBIT_DEATH(new String[0]),
/*  765 */   ENTITY_RABBIT_HURT(new String[0]),
/*  766 */   ENTITY_RABBIT_JUMP(new String[0]),
/*  767 */   ENTITY_RAVAGER_AMBIENT(new String[0]),
/*  768 */   ENTITY_RAVAGER_ATTACK(new String[0]),
/*  769 */   ENTITY_RAVAGER_CELEBRATE(new String[0]),
/*  770 */   ENTITY_RAVAGER_DEATH(new String[0]),
/*  771 */   ENTITY_RAVAGER_HURT(new String[0]),
/*  772 */   ENTITY_RAVAGER_ROAR(new String[0]),
/*  773 */   ENTITY_RAVAGER_STEP(new String[0]),
/*  774 */   ENTITY_RAVAGER_STUNNED(new String[0]),
/*  775 */   ENTITY_SALMON_AMBIENT(new String[0]),
/*  776 */   ENTITY_SALMON_DEATH(new String[0]),
/*  777 */   ENTITY_SALMON_FLOP(new String[0]),
/*  778 */   ENTITY_SALMON_HURT(new String[] { "ENTITY_SALMON_FLOP" }),
/*  779 */   ENTITY_SHEEP_AMBIENT(new String[] { "SHEEP_IDLE" }),
/*  780 */   ENTITY_SHEEP_DEATH(new String[0]),
/*  781 */   ENTITY_SHEEP_HURT(new String[0]),
/*  782 */   ENTITY_SHEEP_SHEAR(new String[] { "SHEEP_SHEAR" }),
/*  783 */   ENTITY_SHEEP_STEP(new String[] { "SHEEP_WALK" }),
/*  784 */   ENTITY_SHULKER_AMBIENT(new String[0]),
/*  785 */   ENTITY_SHULKER_BULLET_HIT(new String[0]),
/*  786 */   ENTITY_SHULKER_BULLET_HURT(new String[0]),
/*  787 */   ENTITY_SHULKER_CLOSE(new String[0]),
/*  788 */   ENTITY_SHULKER_DEATH(new String[0]),
/*  789 */   ENTITY_SHULKER_HURT(new String[0]),
/*  790 */   ENTITY_SHULKER_HURT_CLOSED(new String[0]),
/*  791 */   ENTITY_SHULKER_OPEN(new String[0]),
/*  792 */   ENTITY_SHULKER_SHOOT(new String[0]),
/*  793 */   ENTITY_SHULKER_TELEPORT(new String[0]),
/*  794 */   ENTITY_SILVERFISH_AMBIENT(new String[] { "SILVERFISH_IDLE" }),
/*  795 */   ENTITY_SILVERFISH_DEATH(new String[] { "SILVERFISH_KILL" }),
/*  796 */   ENTITY_SILVERFISH_HURT(new String[] { "SILVERFISH_HIT" }),
/*  797 */   ENTITY_SILVERFISH_STEP(new String[] { "SILVERFISH_WALK" }),
/*  798 */   ENTITY_SKELETON_AMBIENT(new String[] { "SKELETON_IDLE" }),
/*  799 */   ENTITY_SKELETON_DEATH(new String[] { "SKELETON_DEATH" }),
/*  800 */   ENTITY_SKELETON_HORSE_AMBIENT(new String[] { "HORSE_SKELETON_IDLE" }),
/*  801 */   ENTITY_SKELETON_HORSE_AMBIENT_WATER(new String[0]),
/*  802 */   ENTITY_SKELETON_HORSE_DEATH(new String[] { "HORSE_SKELETON_DEATH" }),
/*  803 */   ENTITY_SKELETON_HORSE_GALLOP_WATER(new String[0]),
/*  804 */   ENTITY_SKELETON_HORSE_HURT(new String[] { "HORSE_SKELETON_HIT" }),
/*  805 */   ENTITY_SKELETON_HORSE_JUMP_WATER(new String[0]),
/*  806 */   ENTITY_SKELETON_HORSE_STEP_WATER(new String[0]),
/*  807 */   ENTITY_SKELETON_HORSE_SWIM(new String[0]),
/*  808 */   ENTITY_SKELETON_HURT(new String[] { "SKELETON_HURT" }),
/*  809 */   ENTITY_SKELETON_SHOOT(new String[0]),
/*  810 */   ENTITY_SKELETON_STEP(new String[] { "SKELETON_WALK" }),
/*  811 */   ENTITY_SLIME_ATTACK(new String[] { "SLIME_ATTACK" }),
/*  812 */   ENTITY_SLIME_DEATH(new String[0]),
/*  813 */   ENTITY_SLIME_DEATH_SMALL(new String[0]),
/*  814 */   ENTITY_SLIME_HURT(new String[0]),
/*  815 */   ENTITY_SLIME_HURT_SMALL(new String[] { "ENTITY_SMALL_SLIME_HURT" }),
/*  816 */   ENTITY_SLIME_JUMP(new String[] { "SLIME_WALK" }),
/*  817 */   ENTITY_SLIME_JUMP_SMALL(new String[] { "SLIME_WALK2", "ENTITY_SMALL_SLIME_SQUISH" }),
/*  818 */   ENTITY_SLIME_SQUISH(new String[] { "SLIME_WALK2" }),
/*  819 */   ENTITY_SLIME_SQUISH_SMALL(new String[] { "ENTITY_SMALL_SLIME_SQUISH" }),
/*  820 */   ENTITY_SNOWBALL_THROW(new String[0]),
/*  821 */   ENTITY_SNOW_GOLEM_AMBIENT(new String[] { "ENTITY_SNOWMAN_AMBIENT" }),
/*  822 */   ENTITY_SNOW_GOLEM_DEATH(new String[] { "ENTITY_SNOWMAN_DEATH" }),
/*  823 */   ENTITY_SNOW_GOLEM_HURT(new String[] { "ENTITY_SNOWMAN_HURT" }),
/*  824 */   ENTITY_SNOW_GOLEM_SHOOT(new String[] { "ENTITY_SNOWMAN_SHOOT" }),
/*  825 */   ENTITY_SPIDER_AMBIENT(new String[] { "SPIDER_IDLE" }),
/*  826 */   ENTITY_SPIDER_DEATH(new String[] { "SPIDER_DEATH" }),
/*  827 */   ENTITY_SPIDER_HURT(new String[0]),
/*  828 */   ENTITY_SPIDER_STEP(new String[] { "SPIDER_WALK" }),
/*  829 */   ENTITY_SPLASH_POTION_BREAK(new String[0]),
/*  830 */   ENTITY_SPLASH_POTION_THROW(new String[0]),
/*  831 */   ENTITY_SQUID_AMBIENT(new String[0]),
/*  832 */   ENTITY_SQUID_DEATH(new String[0]),
/*  833 */   ENTITY_SQUID_HURT(new String[0]),
/*  834 */   ENTITY_SQUID_SQUIRT(new String[0]),
/*  835 */   ENTITY_STRAY_AMBIENT(new String[0]),
/*  836 */   ENTITY_STRAY_DEATH(new String[0]),
/*  837 */   ENTITY_STRAY_HURT(new String[0]),
/*  838 */   ENTITY_STRAY_STEP(new String[0]),
/*  839 */   ENTITY_TNT_PRIMED(new String[] { "FUSE" }),
/*  840 */   ENTITY_TROPICAL_FISH_AMBIENT(new String[0]),
/*  841 */   ENTITY_TROPICAL_FISH_DEATH(new String[0]),
/*  842 */   ENTITY_TROPICAL_FISH_FLOP(new String[] { "ENTITY_TROPICAL_FISH_DEATH" }),
/*  843 */   ENTITY_TROPICAL_FISH_HURT(new String[0]),
/*  844 */   ENTITY_TURTLE_AMBIENT_LAND(new String[0]),
/*  845 */   ENTITY_TURTLE_DEATH(new String[0]),
/*  846 */   ENTITY_TURTLE_DEATH_BABY(new String[0]),
/*  847 */   ENTITY_TURTLE_EGG_BREAK(new String[0]),
/*  848 */   ENTITY_TURTLE_EGG_CRACK(new String[0]),
/*  849 */   ENTITY_TURTLE_EGG_HATCH(new String[0]),
/*  850 */   ENTITY_TURTLE_HURT(new String[0]),
/*  851 */   ENTITY_TURTLE_HURT_BABY(new String[0]),
/*  852 */   ENTITY_TURTLE_LAY_EGG(new String[0]),
/*  853 */   ENTITY_TURTLE_SHAMBLE(new String[0]),
/*  854 */   ENTITY_TURTLE_SHAMBLE_BABY(new String[0]),
/*  855 */   ENTITY_TURTLE_SWIM(new String[0]),
/*  856 */   ENTITY_VEX_AMBIENT(new String[0]),
/*  857 */   ENTITY_VEX_CHARGE(new String[0]),
/*  858 */   ENTITY_VEX_DEATH(new String[0]),
/*  859 */   ENTITY_VEX_HURT(new String[0]),
/*  860 */   ENTITY_VILLAGER_AMBIENT(new String[] { "VILLAGER_IDLE" }),
/*  861 */   ENTITY_VILLAGER_CELEBRATE(new String[0]),
/*  862 */   ENTITY_VILLAGER_DEATH(new String[] { "VILLAGER_DEATH" }),
/*  863 */   ENTITY_VILLAGER_HURT(new String[] { "VILLAGER_HIT" }),
/*  864 */   ENTITY_VILLAGER_NO(new String[] { "VILLAGER_NO" }),
/*  865 */   ENTITY_VILLAGER_TRADE(new String[] { "VILLAGER_HAGGLE", "ENTITY_VILLAGER_TRADING" }),
/*  866 */   ENTITY_VILLAGER_WORK_ARMORER(new String[0]),
/*  867 */   ENTITY_VILLAGER_WORK_BUTCHER(new String[0]),
/*  868 */   ENTITY_VILLAGER_WORK_CARTOGRAPHER(new String[0]),
/*  869 */   ENTITY_VILLAGER_WORK_CLERIC(new String[0]),
/*  870 */   ENTITY_VILLAGER_WORK_FARMER(new String[0]),
/*  871 */   ENTITY_VILLAGER_WORK_FISHERMAN(new String[0]),
/*  872 */   ENTITY_VILLAGER_WORK_FLETCHER(new String[0]),
/*  873 */   ENTITY_VILLAGER_WORK_LEATHERWORKER(new String[0]),
/*  874 */   ENTITY_VILLAGER_WORK_LIBRARIAN(new String[0]),
/*  875 */   ENTITY_VILLAGER_WORK_MASON(new String[0]),
/*  876 */   ENTITY_VILLAGER_WORK_SHEPHERD(new String[0]),
/*  877 */   ENTITY_VILLAGER_WORK_TOOLSMITH(new String[0]),
/*  878 */   ENTITY_VILLAGER_WORK_WEAPONSMITH(new String[0]),
/*  879 */   ENTITY_VILLAGER_YES(new String[] { "VILLAGER_YES" }),
/*  880 */   ENTITY_VINDICATOR_AMBIENT(new String[] { "ENTITY_VINDICATION_ILLAGER_AMBIENT" }),
/*  881 */   ENTITY_VINDICATOR_CELEBRATE(new String[0]),
/*  882 */   ENTITY_VINDICATOR_DEATH(new String[] { "ENTITY_VINDICATION_ILLAGER_DEATH" }),
/*  883 */   ENTITY_VINDICATOR_HURT(new String[] { "ENTITY_VINDICATION_ILLAGER_HURT" }),
/*  884 */   ENTITY_WANDERING_TRADER_AMBIENT(new String[0]),
/*  885 */   ENTITY_WANDERING_TRADER_DEATH(new String[0]),
/*  886 */   ENTITY_WANDERING_TRADER_DISAPPEARED(new String[0]),
/*  887 */   ENTITY_WANDERING_TRADER_DRINK_MILK(new String[0]),
/*  888 */   ENTITY_WANDERING_TRADER_DRINK_POTION(new String[0]),
/*  889 */   ENTITY_WANDERING_TRADER_HURT(new String[0]),
/*  890 */   ENTITY_WANDERING_TRADER_NO(new String[0]),
/*  891 */   ENTITY_WANDERING_TRADER_REAPPEARED(new String[0]),
/*  892 */   ENTITY_WANDERING_TRADER_TRADE(new String[0]),
/*  893 */   ENTITY_WANDERING_TRADER_YES(new String[0]),
/*  894 */   ENTITY_WITCH_AMBIENT(new String[0]),
/*  895 */   ENTITY_WITCH_CELEBRATE(new String[0]),
/*  896 */   ENTITY_WITCH_DEATH(new String[0]),
/*  897 */   ENTITY_WITCH_DRINK(new String[0]),
/*  898 */   ENTITY_WITCH_HURT(new String[0]),
/*  899 */   ENTITY_WITCH_THROW(new String[0]),
/*  900 */   ENTITY_WITHER_AMBIENT(new String[] { "WITHER_IDLE" }),
/*  901 */   ENTITY_WITHER_BREAK_BLOCK(new String[0]),
/*  902 */   ENTITY_WITHER_DEATH(new String[] { "WITHER_DEATH" }),
/*  903 */   ENTITY_WITHER_HURT(new String[] { "WITHER_HURT" }),
/*  904 */   ENTITY_WITHER_SHOOT(new String[] { "WITHER_SHOOT" }),
/*  905 */   ENTITY_WITHER_SKELETON_AMBIENT(new String[0]),
/*  906 */   ENTITY_WITHER_SKELETON_DEATH(new String[0]),
/*  907 */   ENTITY_WITHER_SKELETON_HURT(new String[0]),
/*  908 */   ENTITY_WITHER_SKELETON_STEP(new String[0]),
/*  909 */   ENTITY_WITHER_SPAWN(new String[] { "WITHER_SPAWN" }),
/*  910 */   ENTITY_WOLF_AMBIENT(new String[] { "WOLF_BARK" }),
/*  911 */   ENTITY_WOLF_DEATH(new String[] { "WOLF_DEATH" }),
/*  912 */   ENTITY_WOLF_GROWL(new String[] { "WOLF_GROWL" }),
/*  913 */   ENTITY_WOLF_HOWL(new String[] { "WOLF_HOWL" }),
/*  914 */   ENTITY_WOLF_HURT(new String[] { "WOLF_HURT" }),
/*  915 */   ENTITY_WOLF_PANT(new String[] { "WOLF_PANT" }),
/*  916 */   ENTITY_WOLF_SHAKE(new String[] { "WOLF_SHAKE" }),
/*  917 */   ENTITY_WOLF_STEP(new String[] { "WOLF_WALK" }),
/*  918 */   ENTITY_WOLF_WHINE(new String[] { "WOLF_WHINE" }),
/*  919 */   ENTITY_ZOMBIE_AMBIENT(new String[] { "ZOMBIE_IDLE" }),
/*  920 */   ENTITY_ZOMBIE_ATTACK_IRON_DOOR(new String[] { "ZOMBIE_METAL" }),
/*  921 */   ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR(new String[] { "ZOMBIE_WOOD", "ENTITY_ZOMBIE_ATTACK_DOOR_WOOD" }),
/*  922 */   ENTITY_ZOMBIE_BREAK_WOODEN_DOOR(new String[] { "ZOMBIE_WOODBREAK", "ENTITY_ZOMBIE_BREAK_DOOR_WOOD" }),
/*  923 */   ENTITY_ZOMBIE_CONVERTED_TO_DROWNED(new String[0]),
/*  924 */   ENTITY_ZOMBIE_DEATH(new String[] { "ZOMBIE_DEATH" }),
/*  925 */   ENTITY_ZOMBIE_DESTROY_EGG(new String[0]),
/*  926 */   ENTITY_ZOMBIE_HORSE_AMBIENT(new String[] { "HORSE_ZOMBIE_IDLE" }),
/*  927 */   ENTITY_ZOMBIE_HORSE_DEATH(new String[] { "HORSE_ZOMBIE_DEATH" }),
/*  928 */   ENTITY_ZOMBIE_HORSE_HURT(new String[] { "HORSE_ZOMBIE_HIT" }),
/*  929 */   ENTITY_ZOMBIE_HURT(new String[] { "ZOMBIE_HURT" }),
/*  930 */   ENTITY_ZOMBIE_INFECT(new String[] { "ZOMBIE_INFECT" }),
/*  931 */   ITEM_ARMOR_EQUIP_NETHERITE(new String[0]),
/*  932 */   ITEM_LODESTONE_COMPASS_LOCK(new String[0]),
/*  933 */   MUSIC_DISC_PIGSTEP(new String[0]),
/*      */   
/*  935 */   ENTITY_ZOMBIFIED_PIGLIN_AMBIENT(new String[] { "ZOMBE_PIG_IDLE", "ENTITY_ZOMBIE_PIG_AMBIENT", "ENTITY_ZOMBIE_PIGMAN_AMBIENT" }),
/*  936 */   ENTITY_ZOMBIFIED_PIGLIN_ANGRY(new String[] { "ZOMBIE_PIG_ANGRY", "ENTITY_ZOMBIE_PIG_ANGRY", "ENTITY_ZOMBIE_PIGMAN_ANGRY" }),
/*  937 */   ENTITY_ZOMBIFIED_PIGLIN_DEATH(new String[] { "ZOMBIE_PIG_DEATH", "ENTITY_ZOMBIE_PIG_DEATH", "ENTITY_ZOMBIE_PIGMAN_DEATH" }),
/*  938 */   ENTITY_ZOMBIFIED_PIGLIN_HURT(new String[] { "ZOMBIE_PIG_HURT", "ENTITY_ZOMBIE_PIG_HURT", "ENTITY_ZOMBIE_PIGMAN_HURT" }),
/*  939 */   ENTITY_ZOMBIE_STEP(new String[] { "ZOMBIE_WALK" }),
/*  940 */   ENTITY_ZOMBIE_VILLAGER_AMBIENT(new String[0]),
/*  941 */   ENTITY_ZOMBIE_VILLAGER_CONVERTED(new String[] { "ZOMBIE_UNFECT" }),
/*  942 */   ENTITY_ZOMBIE_VILLAGER_CURE(new String[] { "ZOMBIE_REMEDY" }),
/*  943 */   ENTITY_ZOMBIE_VILLAGER_DEATH(new String[0]),
/*  944 */   ENTITY_ZOMBIE_VILLAGER_HURT(new String[0]),
/*  945 */   ENTITY_ZOMBIE_VILLAGER_STEP(new String[0]),
/*  946 */   EVENT_RAID_HORN(new String[0]),
/*  947 */   ITEM_ARMOR_EQUIP_CHAIN(new String[0]),
/*  948 */   ITEM_ARMOR_EQUIP_DIAMOND(new String[0]),
/*  949 */   ITEM_ARMOR_EQUIP_ELYTRA(new String[0]),
/*  950 */   ITEM_ARMOR_EQUIP_GENERIC(new String[0]),
/*  951 */   ITEM_ARMOR_EQUIP_GOLD(new String[0]),
/*  952 */   ITEM_ARMOR_EQUIP_IRON(new String[0]),
/*  953 */   ITEM_ARMOR_EQUIP_LEATHER(new String[0]),
/*  954 */   ITEM_ARMOR_EQUIP_TURTLE(new String[0]),
/*  955 */   ITEM_AXE_STRIP(new String[0]),
/*  956 */   ITEM_BOOK_PAGE_TURN(new String[0]),
/*  957 */   ITEM_BOOK_PUT(new String[0]),
/*  958 */   ITEM_BOTTLE_EMPTY(new String[0]),
/*  959 */   ITEM_BOTTLE_FILL(new String[0]),
/*  960 */   ITEM_BOTTLE_FILL_DRAGONBREATH(new String[0]),
/*  961 */   ITEM_BUCKET_EMPTY(new String[0]),
/*  962 */   ITEM_BUCKET_EMPTY_FISH(new String[0]),
/*  963 */   ITEM_BUCKET_EMPTY_LAVA(new String[0]),
/*  964 */   ITEM_BUCKET_FILL(new String[0]),
/*  965 */   ITEM_BUCKET_FILL_FISH(new String[0]),
/*  966 */   ITEM_BUCKET_FILL_LAVA(new String[0]),
/*  967 */   ITEM_CHORUS_FRUIT_TELEPORT(new String[0]),
/*  968 */   ITEM_CROP_PLANT(new String[0]),
/*  969 */   ITEM_CROSSBOW_HIT(new String[0]),
/*  970 */   ITEM_CROSSBOW_LOADING_END(new String[0]),
/*  971 */   ITEM_CROSSBOW_LOADING_MIDDLE(new String[0]),
/*  972 */   ITEM_CROSSBOW_LOADING_START(new String[0]),
/*  973 */   ITEM_CROSSBOW_QUICK_CHARGE_1(new String[0]),
/*  974 */   ITEM_CROSSBOW_QUICK_CHARGE_2(new String[0]),
/*  975 */   ITEM_CROSSBOW_QUICK_CHARGE_3(new String[0]),
/*  976 */   ITEM_CROSSBOW_SHOOT(new String[0]),
/*  977 */   ITEM_ELYTRA_FLYING(new String[0]),
/*  978 */   ITEM_FIRECHARGE_USE(new String[0]),
/*  979 */   ITEM_FLINTANDSTEEL_USE(new String[] { "FIRE_IGNITE" }),
/*  980 */   ITEM_HOE_TILL(new String[0]),
/*  981 */   ITEM_HONEY_BOTTLE_DRINK(new String[0]),
/*  982 */   ITEM_NETHER_WART_PLANT(new String[0]),
/*  983 */   ITEM_SHIELD_BLOCK(new String[0]),
/*  984 */   ITEM_SHIELD_BREAK(new String[0]),
/*  985 */   ITEM_SHOVEL_FLATTEN(new String[0]),
/*  986 */   ITEM_SWEET_BERRIES_PICK_FROM_BUSH(new String[0]),
/*  987 */   ITEM_TOTEM_USE(new String[0]),
/*  988 */   ITEM_TRIDENT_HIT(new String[0]),
/*  989 */   ITEM_TRIDENT_HIT_GROUND(new String[0]),
/*  990 */   ITEM_TRIDENT_RETURN(new String[0]),
/*  991 */   ITEM_TRIDENT_RIPTIDE_1(new String[0]),
/*  992 */   ITEM_TRIDENT_RIPTIDE_2(new String[] { "ITEM_TRIDENT_RIPTIDE_1" }),
/*  993 */   ITEM_TRIDENT_RIPTIDE_3(new String[] { "ITEM_TRIDENT_RIPTIDE_1" }),
/*  994 */   ITEM_TRIDENT_THROW(new String[0]),
/*  995 */   ITEM_TRIDENT_THUNDER(new String[0]),
/*  996 */   MUSIC_CREATIVE(new String[0]),
/*  997 */   MUSIC_CREDITS(new String[0]),
/*  998 */   MUSIC_DISC_11(new String[] { "RECORD_11" }),
/*  999 */   MUSIC_DISC_13(new String[] { "RECORD_13" }),
/* 1000 */   MUSIC_DISC_BLOCKS(new String[] { "RECORD_BLOCKS" }),
/* 1001 */   MUSIC_DISC_CAT(new String[] { "RECORD_CAT" }),
/* 1002 */   MUSIC_DISC_CHIRP(new String[] { "RECORD_CHIRP" }),
/* 1003 */   MUSIC_DISC_FAR(new String[] { "RECORD_FAR" }),
/* 1004 */   MUSIC_DISC_MALL(new String[] { "RECORD_MALL" }),
/* 1005 */   MUSIC_DISC_MELLOHI(new String[] { "RECORD_MELLOHI" }),
/* 1006 */   MUSIC_DISC_STAL(new String[] { "RECORD_STAL" }),
/* 1007 */   MUSIC_DISC_STRAD(new String[] { "RECORD_STRAD" }),
/* 1008 */   MUSIC_DISC_WAIT(new String[] { "RECORD_WAIT" }),
/* 1009 */   MUSIC_DISC_WARD(new String[] { "RECORD_WARD" }),
/* 1010 */   MUSIC_DRAGON(new String[0]),
/* 1011 */   MUSIC_END(new String[0]),
/* 1012 */   MUSIC_GAME(new String[0]),
/* 1013 */   MUSIC_MENU(new String[0]),
/* 1014 */   MUSIC_NETHER_BASALT_DELTAS(new String[] { "MUSIC_NETHER" }),
/* 1015 */   PARTICLE_SOUL_ESCAPE(new String[0]),
/* 1016 */   MUSIC_NETHER_CRIMSON_FOREST(new String[0]),
/* 1017 */   MUSIC_NETHER_NETHER_WASTES(new String[0]),
/* 1018 */   MUSIC_NETHER_SOUL_SAND_VALLEY(new String[0]),
/* 1019 */   MUSIC_NETHER_WARPED_FOREST(new String[0]),
/* 1020 */   MUSIC_UNDER_WATER(new String[0]),
/* 1021 */   UI_BUTTON_CLICK(new String[] { "CLICK" }),
/* 1022 */   UI_CARTOGRAPHY_TABLE_TAKE_RESULT(new String[0]),
/* 1023 */   UI_LOOM_SELECT_PATTERN(new String[0]),
/* 1024 */   UI_LOOM_TAKE_RESULT(new String[0]),
/* 1025 */   UI_STONECUTTER_SELECT_RECIPE(new String[0]),
/* 1026 */   UI_STONECUTTER_TAKE_RESULT(new String[0]),
/* 1027 */   UI_TOAST_CHALLENGE_COMPLETE(new String[0]),
/* 1028 */   UI_TOAST_IN(new String[0]),
/* 1029 */   UI_TOAST_OUT(new String[0]),
/* 1030 */   WEATHER_RAIN(new String[] { "AMBIENCE_RAIN" }),
/* 1031 */   WEATHER_RAIN_ABOVE(new String[0]);
/*      */   
/*      */   public static final EnumSet<XSound> VALUES;
/*      */   private static final Cache<XSound, Optional<Sound>> CACHE;
/*      */   private static final Pattern FORMAT_PATTERN;
/*      */   private static final Pattern DOUBLE_SPACE;
/*      */   private final String[] legacy;
/*      */   
/*      */   static {
/* 1040 */     VALUES = EnumSet.allOf(XSound.class);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1052 */     CACHE = CacheBuilder.newBuilder().expireAfterAccess(10L, TimeUnit.MINUTES).softValues().build();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1059 */     FORMAT_PATTERN = Pattern.compile("\\d+|\\W+");
/* 1060 */     DOUBLE_SPACE = Pattern.compile("  +");
/*      */   }
/*      */   
/*      */   XSound(String... legacy) {
/* 1064 */     this.legacy = legacy;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   private static String format(@Nonnull String name) {
/* 1077 */     return FORMAT_PATTERN.matcher(name
/* 1078 */         .trim().replace('-', '_').replace(' ', '_')).replaceAll("").toUpperCase(Locale.ENGLISH);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean contains(@Nonnull String sound) {
/* 1089 */     Validate.notEmpty(sound, "Cannot check for null or empty sound name");
/* 1090 */     sound = format(sound);
/*      */     
/* 1092 */     for (XSound sounds : VALUES) {
/* 1093 */       if (sounds.name().equals(sound) || sounds.anyMatchLegacy(sound)) return true; 
/* 1094 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public static Optional<XSound> matchXSound(@Nonnull String sound) {
/* 1106 */     Validate.notEmpty(sound, "Cannot match XSound of a null or empty sound name");
/* 1107 */     sound = format(sound);
/*      */     
/* 1109 */     for (XSound sounds : VALUES) {
/* 1110 */       if (sounds.name().equals(sound) || sounds.anyMatchLegacy(sound)) return Optional.of(sounds); 
/* 1111 */     }  return Optional.empty();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public static XSound matchXSound(@Nonnull Sound sound) {
/* 1124 */     Objects.requireNonNull(sound, "Cannot match XSound of a null sound");
/* 1125 */     return matchXSound(sound.name())
/* 1126 */       .<Throwable>orElseThrow(() -> new IllegalArgumentException("Unsupported Sound: " + sound.name()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public static CompletableFuture<Record> play(@Nullable Player player, @Nullable String sound) {
/* 1135 */     Objects.requireNonNull(player, "Cannot play sound to null player");
/* 1136 */     return parse(player, player.getLocation(), sound, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public static CompletableFuture<Record> play(@Nonnull Location location, @Nullable String sound) {
/* 1145 */     return parse((Player)null, location, sound, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public static CompletableFuture<Record> parse(@Nullable Player player, @Nonnull Location location, @Nullable String sound, boolean play) {
/* 1188 */     Objects.requireNonNull(player, "Cannot play sound to null location");
/* 1189 */     if (Strings.isNullOrEmpty(sound) || sound.equalsIgnoreCase("none")) return null;
/*      */     
/* 1191 */     return CompletableFuture.<Record>supplyAsync(() -> {
/*      */           String[] split = StringUtils.contains(sound, ',') ? StringUtils.split(StringUtils.deleteWhitespace(sound), ',') : StringUtils.split(DOUBLE_SPACE.matcher(sound).replaceAll(" "), ' ');
/*      */           String name = split[0];
/*      */           boolean playForEveryone = (player == null);
/*      */           if (!playForEveryone && StringUtils.startsWithIgnoreCase(name, "loc:")) {
/*      */             name = name.substring(4);
/*      */             playForEveryone = true;
/*      */           } 
/*      */           Optional<XSound> typeOpt = matchXSound(name);
/*      */           if (!typeOpt.isPresent()) {
/*      */             return null;
/*      */           }
/*      */           Sound type = ((XSound)typeOpt.get()).parseSound();
/*      */           if (type == null) {
/*      */             return null;
/*      */           }
/*      */           float volume = 1.0F;
/*      */           float pitch = 1.0F;
/*      */           try {
/*      */             if (split.length > 1) {
/*      */               volume = Float.parseFloat(split[1]);
/*      */               if (split.length > 2)
/*      */                 pitch = Float.parseFloat(split[2]); 
/*      */             } 
/* 1215 */           } catch (NumberFormatException numberFormatException) {}
/*      */           Record record = new Record(type, player, location, volume, pitch, playForEveryone);
/*      */           if (play) {
/*      */             record.play();
/*      */           }
/*      */           return record;
/* 1221 */         }).exceptionally(ex -> {
/*      */           System.err.println("Could not play sound for string: " + sound);
/*      */           ex.printStackTrace();
/*      */           return null;
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1235 */     return WordUtils.capitalize(name().replace('_', ' ').toLowerCase(Locale.ENGLISH));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public String[] getLegacy() {
/* 1246 */     return this.legacy;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Sound parseSound() {
/* 1258 */     Optional<Sound> cachedSound = (Optional<Sound>)CACHE.getIfPresent(this);
/* 1259 */     if (cachedSound != null) return (Sound)cachedSound.orNull();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1264 */     Optional<Sound> sound = Enums.getIfPresent(Sound.class, name());
/*      */     
/* 1266 */     if (!sound.isPresent()) {
/* 1267 */       for (String legacy : this.legacy) {
/* 1268 */         sound = Enums.getIfPresent(Sound.class, legacy);
/* 1269 */         if (sound.isPresent()) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     }
/* 1274 */     CACHE.put(this, sound);
/* 1275 */     return (Sound)sound.orNull();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSupported() {
/* 1291 */     return (parseSound() != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean anyMatchLegacy(@Nonnull String name) {
/* 1302 */     Validate.notEmpty(name, "Cannot check for legacy name for null or empty sound name");
/* 1303 */     return Arrays.<String>asList(this.legacy).contains(format(name));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playRepeatedly(JavaPlugin plugin, final Entity entity, final float volume, final float pitch, final int repeat, int delay) {
/* 1319 */     Objects.requireNonNull(plugin, "Cannot play repeating sound from null plugin");
/* 1320 */     Objects.requireNonNull(entity, "Cannot play repeating sound at null location");
/*      */     
/* 1322 */     Validate.isTrue((repeat > 0), "Cannot repeat playing sound " + repeat + " times");
/* 1323 */     Validate.isTrue((delay > 0), "Delay ticks must be at least 1");
/*      */     
/* 1325 */     (new BukkitRunnable() {
/* 1326 */         int repeating = repeat;
/*      */ 
/*      */         
/*      */         public void run() {
/* 1330 */           XSound.this.play(entity.getLocation(), volume, pitch);
/* 1331 */           if (this.repeating-- == 0) cancel(); 
/*      */         }
/* 1333 */       }).runTaskTimer((Plugin)plugin, 0L, delay);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playAscendingNote(@Nonnull JavaPlugin plugin, @Nonnull final Player player, @Nonnull final Entity playTo, final Instrument instrument, final int ascendLevel, int delay) {
/* 1349 */     Objects.requireNonNull(player, "Cannot play note from null player");
/* 1350 */     Objects.requireNonNull(playTo, "Cannot play note to null entity");
/*      */     
/* 1352 */     Validate.isTrue((ascendLevel > 0), "Note ascend level cannot be lower than 1");
/* 1353 */     Validate.isTrue((ascendLevel <= 7), "Note ascend level cannot be greater than 7");
/* 1354 */     Validate.isTrue((delay > 0), "Delay ticks must be at least 1");
/*      */     
/* 1356 */     (new BukkitRunnable() {
/* 1357 */         int repeating = ascendLevel;
/*      */ 
/*      */         
/*      */         public void run() {
/* 1361 */           player.playNote(playTo.getLocation(), instrument, Note.natural(1, Note.Tone.values()[ascendLevel - this.repeating]));
/* 1362 */           if (this.repeating-- == 0) cancel(); 
/*      */         }
/* 1364 */       }).runTaskTimerAsynchronously((Plugin)plugin, 0L, delay);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void play(@Nonnull Entity entity) {
/* 1374 */     play(entity, 1.0F, 1.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void play(@Nonnull Entity entity, float volume, float pitch) {
/* 1386 */     Objects.requireNonNull(entity, "Cannot play sound to a null entity");
/* 1387 */     if (entity instanceof Player) {
/* 1388 */       Sound sound = parseSound();
/* 1389 */       if (sound == null)
/* 1390 */         return;  ((Player)entity).playSound(entity.getLocation(), sound, volume, pitch);
/*      */     } else {
/* 1392 */       play(entity.getLocation(), volume, pitch);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void play(@Nonnull Location location) {
/* 1403 */     play(location, 1.0F, 1.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void play(@Nonnull Location location, float volume, float pitch) {
/* 1415 */     Objects.requireNonNull(location, "Cannot play sound to null location");
/* 1416 */     Sound sound = parseSound();
/* 1417 */     if (sound == null)
/* 1418 */       return;  location.getWorld().playSound(location, sound, volume, pitch);
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Record
/*      */   {
/*      */     public final Sound sound;
/*      */     
/*      */     public final Player player;
/*      */     
/*      */     public final Location location;
/*      */     
/*      */     public final float volume;
/*      */     public final float pitch;
/*      */     public final boolean playAtLocation;
/*      */     
/*      */     public Record(Sound sound, Player player, Location location, float volume, float pitch, boolean playAtLocation) {
/* 1435 */       this.sound = sound;
/* 1436 */       this.player = player;
/* 1437 */       this.location = location;
/* 1438 */       this.volume = volume;
/* 1439 */       this.pitch = pitch;
/* 1440 */       this.playAtLocation = playAtLocation;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void play() {
/* 1449 */       play((this.player == null) ? this.location : this.player.getLocation());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void play(Location updatedLocation) {
/* 1459 */       if (this.playAtLocation) { this.location.getWorld().playSound(updatedLocation, this.sound, this.volume, this.pitch); }
/* 1460 */       else if (this.player.isOnline()) { this.player.playSound(updatedLocation, this.sound, this.volume, this.pitch); }
/*      */     
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\Nerotek\Desktop\DeliveryMan.jar!\io\github\Leonardo0013YT\DeliveryMan\xseries\XSound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */