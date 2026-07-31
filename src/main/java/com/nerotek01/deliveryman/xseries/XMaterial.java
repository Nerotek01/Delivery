/*      */ package com.nerotek01.deliveryman.xseries;
/*      */ import com.google.common.base.Enums;
/*      */ import com.google.common.cache.Cache;
/*      */ import com.google.common.cache.CacheBuilder;
/*      */ import com.google.common.collect.ImmutableMap;
/*      */ import com.google.common.collect.ImmutableSet;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.common.collect.UnmodifiableIterator;
/*      */ import java.util.ArrayList;
/*      */ import java.util.EnumSet;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Optional;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import java.util.regex.Pattern;
/*      */ import javax.annotation.Nonnull;
/*      */ import javax.annotation.Nullable;
/*      */ import org.apache.commons.lang.Validate;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.lang.WordUtils;
/*      */ import org.bukkit.Bukkit;
/*      */ import org.bukkit.Material;
/*      */ import org.bukkit.inventory.ItemStack;
/*      */ 
/*      */ public enum XMaterial {
/*   23 */   ACACIA_BOAT(new String[] { "BOAT_ACACIA" }),
/*   24 */   ACACIA_BUTTON(new String[] { "WOOD_BUTTON" }),
/*   25 */   ACACIA_DOOR(new String[] { "ACACIA_DOOR_ITEM" }),
/*   26 */   ACACIA_FENCE,
/*   27 */   ACACIA_FENCE_GATE,
/*   28 */   ACACIA_LEAVES(new String[] { "LEAVES_2" }),
/*   29 */   ACACIA_LOG(new String[] { "LOG_2" }),
/*   30 */   ACACIA_PLANKS(4, new String[] { "WOOD" }),
/*   31 */   ACACIA_PRESSURE_PLATE(new String[] { "WOOD_PLATE" }),
/*   32 */   ACACIA_SAPLING(4, new String[] { "SAPLING" }),
/*   33 */   ACACIA_SIGN(new String[] { "SIGN" }),
/*   34 */   ACACIA_SLAB(4, new String[] { "WOOD_STEP", "WOODEN_SLAB", "WOOD_DOUBLE_STEP" }),
/*   35 */   ACACIA_STAIRS,
/*   36 */   ACACIA_TRAPDOOR(new String[] { "TRAP_DOOR" }),
/*   37 */   ACACIA_WALL_SIGN(new String[] { "SIGN_POST", "WALL_SIGN" }),
/*   38 */   ACACIA_WOOD(new String[] { "LOG_2" }),
/*   39 */   ACTIVATOR_RAIL,
/*   40 */   AIR,
/*   41 */   ALLIUM(2, new String[] { "RED_ROSE" }),
/*   42 */   ANCIENT_DEBRIS(new String[] { "1.16" }),
/*   43 */   ANDESITE(5, new String[] { "STONE" }),
/*   44 */   ANDESITE_SLAB,
/*   45 */   ANDESITE_STAIRS,
/*   46 */   ANDESITE_WALL,
/*   47 */   ANVIL,
/*   48 */   APPLE,
/*   49 */   ARMOR_STAND,
/*   50 */   ARROW,
/*   51 */   ATTACHED_MELON_STEM(7, new String[] { "MELON_STEM" }),
/*   52 */   ATTACHED_PUMPKIN_STEM(7, new String[] { "PUMPKIN_STEM" }),
/*   53 */   AZURE_BLUET(3, new String[] { "RED_ROSE" }),
/*   54 */   BAKED_POTATO,
/*   55 */   BAMBOO(new String[] { "1.14", "SUGAR_CANE", "" }),
/*   56 */   BAMBOO_SAPLING(new String[] { "1.14" }),
/*   57 */   BARREL(new String[] { "1.14", "CHEST", "" }),
/*   58 */   BARRIER,
/*   59 */   BASALT(new String[] { "1.16" }),
/*   60 */   BAT_SPAWN_EGG(65, new String[] { "MONSTER_EGG" }),
/*   61 */   BEACON,
/*   62 */   BEDROCK,
/*   63 */   BEEF(new String[] { "RAW_BEEF" }),
/*   64 */   BEEHIVE(new String[] { "1.15" }),
/*   65 */   BEETROOT(new String[] { "BEETROOT_BLOCK" }),
/*   66 */   BEETROOTS(new String[] { "BEETROOT" }),
/*   67 */   BEETROOT_SEEDS,
/*   68 */   BEETROOT_SOUP,
/*   69 */   BEE_NEST(new String[] { "1.15" }),
/*   70 */   BEE_SPAWN_EGG(new String[] { "1.15" }),
/*   71 */   BELL(new String[] { "1.14" }),
/*   72 */   BIRCH_BOAT(new String[] { "BOAT_BIRCH" }),
/*   73 */   BIRCH_BUTTON(new String[] { "WOOD_BUTTON" }),
/*   74 */   BIRCH_DOOR(new String[] { "BIRCH_DOOR_ITEM" }),
/*   75 */   BIRCH_FENCE,
/*   76 */   BIRCH_FENCE_GATE,
/*   77 */   BIRCH_LEAVES(2, new String[] { "LEAVES" }),
/*   78 */   BIRCH_LOG(2, new String[] { "LOG" }),
/*   79 */   BIRCH_PLANKS(2, new String[] { "WOOD" }),
/*   80 */   BIRCH_PRESSURE_PLATE(new String[] { "WOOD_PLATE" }),
/*   81 */   BIRCH_SAPLING(2, new String[] { "SAPLING" }),
/*   82 */   BIRCH_SIGN(new String[] { "SIGN" }),
/*   83 */   BIRCH_SLAB(2, new String[] { "WOOD_STEP", "WOODEN_SLAB", "WOOD_DOUBLE_STEP" }),
/*   84 */   BIRCH_STAIRS(new String[] { "BIRCH_WOOD_STAIRS" }),
/*   85 */   BIRCH_TRAPDOOR(new String[] { "TRAP_DOOR" }),
/*   86 */   BIRCH_WALL_SIGN(new String[] { "SIGN_POST", "WALL_SIGN" }),
/*   87 */   BIRCH_WOOD(2, new String[] { "LOG" }),
/*   88 */   BLACKSTONE(new String[] { "1.16" }),
/*   89 */   BLACKSTONE_SLAB(new String[] { "1.16" }),
/*   90 */   BLACKSTONE_STAIRS(new String[] { "1.16" }),
/*   91 */   BLACKSTONE_WALL(new String[] { "1.16" }),
/*   92 */   BLACK_BANNER(new String[] { "BANNER", "STANDING_BANNER" }),
/*   93 */   BLACK_BED(15, new String[] { "BED_BLOCK", "BED" }),
/*   94 */   BLACK_CARPET(15, new String[] { "CARPET" }),
/*   95 */   BLACK_CONCRETE(15, new String[] { "CONCRETE" }),
/*   96 */   BLACK_CONCRETE_POWDER(15, new String[] { "CONCRETE_POWDER" }),
/*   97 */   BLACK_DYE(new String[] { "1.14", "INK_SACK" }),
/*   98 */   BLACK_GLAZED_TERRACOTTA(15, new String[] { "1.12", "HARD_CLAY", "STAINED_CLAY", "BLACK_TERRACOTTA" }),
/*   99 */   BLACK_SHULKER_BOX,
/*  100 */   BLACK_STAINED_GLASS(15, new String[] { "STAINED_GLASS" }),
/*  101 */   BLACK_STAINED_GLASS_PANE(15, new String[] { "STAINED_GLASS_PANE" }),
/*  102 */   BLACK_TERRACOTTA(15, new String[] { "HARD_CLAY", "STAINED_CLAY" }),
/*  103 */   BLACK_WALL_BANNER(new String[] { "WALL_BANNER" }),
/*  104 */   BLACK_WOOL(15, new String[] { "WOOL" }),
/*  105 */   BLAST_FURNACE(new String[] { "1.14", "FURNACE", "" }),
/*  106 */   BLAZE_POWDER,
/*  107 */   BLAZE_ROD,
/*  108 */   BLAZE_SPAWN_EGG(61, new String[] { "MONSTER_EGG" }),
/*  109 */   BLUE_BANNER(4, new String[] { "BANNER", "STANDING_BANNER" }),
/*  110 */   BLUE_BED(11, new String[] { "BED_BLOCK", "BED" }),
/*  111 */   BLUE_CARPET(11, new String[] { "CARPET" }),
/*  112 */   BLUE_CONCRETE(11, new String[] { "CONCRETE" }),
/*  113 */   BLUE_CONCRETE_POWDER(11, new String[] { "CONCRETE_POWDER" }),
/*  114 */   BLUE_DYE(4, new String[] { "INK_SACK", "LAPIS_LAZULI" }),
/*  115 */   BLUE_GLAZED_TERRACOTTA(11, new String[] { "1.12", "HARD_CLAY", "STAINED_CLAY", "BLUE_TERRACOTTA" }),
/*  116 */   BLUE_ICE(new String[] { "1.13", "PACKED_ICE", "" }),
/*  117 */   BLUE_ORCHID(1, new String[] { "RED_ROSE" }),
/*  118 */   BLUE_SHULKER_BOX,
/*  119 */   BLUE_STAINED_GLASS(11, new String[] { "STAINED_GLASS" }),
/*  120 */   BLUE_STAINED_GLASS_PANE(11, new String[] { "THIN_GLASS", "STAINED_GLASS_PANE" }),
/*  121 */   BLUE_TERRACOTTA(11, new String[] { "HARD_CLAY", "STAINED_CLAY" }),
/*  122 */   BLUE_WALL_BANNER(4, new String[] { "WALL_BANNER" }),
/*  123 */   BLUE_WOOL(11, new String[] { "WOOL" }),
/*  124 */   BONE,
/*  125 */   BONE_BLOCK,
/*  126 */   BONE_MEAL(15, new String[] { "INK_SACK" }),
/*  127 */   BOOK,
/*  128 */   BOOKSHELF,
/*  129 */   BOW,
/*  130 */   BOWL,
/*  131 */   BRAIN_CORAL(new String[] { "1.13" }),
/*  132 */   BRAIN_CORAL_BLOCK(new String[] { "1.13" }),
/*  133 */   BRAIN_CORAL_FAN(new String[] { "1.13" }),
/*  134 */   BRAIN_CORAL_WALL_FAN,
/*  135 */   BREAD,
/*  136 */   BREWING_STAND(new String[] { "BREWING_STAND_ITEM" }),
/*  137 */   BRICK(new String[] { "CLAY_BRICK" }),
/*  138 */   BRICKS(new String[] { "BRICK" }),
/*  139 */   BRICK_SLAB(4, new String[] { "STEP" }),
/*  140 */   BRICK_STAIRS,
/*  141 */   BRICK_WALL,
/*  142 */   BROWN_BANNER(3, new String[] { "BANNER", "STANDING_BANNER" }),
/*  143 */   BROWN_BED(12, new String[] { "BED_BLOCK", "BED" }),
/*  144 */   BROWN_CARPET(12, new String[] { "CARPET" }),
/*  145 */   BROWN_CONCRETE(12, new String[] { "CONCRETE" }),
/*  146 */   BROWN_CONCRETE_POWDER(12, new String[] { "CONCRETE_POWDER" }),
/*  147 */   BROWN_DYE(3, new String[] { "INK_SACK", "COCOA", "COCOA_BEANS" }),
/*  148 */   BROWN_GLAZED_TERRACOTTA(12, new String[] { "1.12", "HARD_CLAY", "STAINED_CLAY", "BROWN_TERRACOTTA" }),
/*  149 */   BROWN_MUSHROOM,
/*  150 */   BROWN_MUSHROOM_BLOCK(new String[] { "BROWN_MUSHROOM", "HUGE_MUSHROOM_1" }),
/*  151 */   BROWN_SHULKER_BOX,
/*  152 */   BROWN_STAINED_GLASS(12, new String[] { "STAINED_GLASS" }),
/*  153 */   BROWN_STAINED_GLASS_PANE(12, new String[] { "THIN_GLASS", "STAINED_GLASS_PANE" }),
/*  154 */   BROWN_TERRACOTTA(12, new String[] { "STAINED_CLAY" }),
/*  155 */   BROWN_WALL_BANNER(3, new String[] { "WALL_BANNER" }),
/*  156 */   BROWN_WOOL(12, new String[] { "WOOL" }),
/*  157 */   BUBBLE_COLUMN(new String[] { "1.13" }),
/*  158 */   BUBBLE_CORAL(new String[] { "1.13" }),
/*  159 */   BUBBLE_CORAL_BLOCK(new String[] { "1.13" }),
/*  160 */   BUBBLE_CORAL_FAN(new String[] { "1.13" }),
/*  161 */   BUBBLE_CORAL_WALL_FAN,
/*  162 */   BUCKET,
/*  163 */   CACTUS,
/*  164 */   CAKE(new String[] { "CAKE_BLOCK" }),
/*  165 */   CAMPFIRE(new String[] { "1.14" }),
/*  166 */   CARROT(new String[] { "CARROT_ITEM" }),
/*  167 */   CARROTS(new String[] { "CARROT" }),
/*  168 */   CARROT_ON_A_STICK(new String[] { "CARROT_STICK" }),
/*  169 */   CARTOGRAPHY_TABLE(new String[] { "1.14", "CRAFTING_TABLE", "" }),
/*  170 */   CARVED_PUMPKIN(1, new String[] { "1.13", "PUMPKIN", "" }),
/*  171 */   CAT_SPAWN_EGG,
/*  172 */   CAULDRON(new String[] { "CAULDRON_ITEM" }),
/*  173 */   CAVE_AIR(new String[] { "AIR" }),
/*  174 */   CAVE_SPIDER_SPAWN_EGG(59, new String[] { "MONSTER_EGG" }),
/*  175 */   CHAIN(new String[] { "1.16" }),
/*  176 */   CHAINMAIL_BOOTS,
/*  177 */   CHAINMAIL_CHESTPLATE,
/*  178 */   CHAINMAIL_HELMET,
/*  179 */   CHAINMAIL_LEGGINGS,
/*  180 */   CHAIN_COMMAND_BLOCK(new String[] { "COMMAND", "COMMAND_CHAIN" }),
/*  181 */   CHARCOAL(1, new String[] { "COAL" }),
/*  182 */   CHEST(new String[] { "LOCKED_CHEST" }),
/*  183 */   CHEST_MINECART(new String[] { "STORAGE_MINECART" }),
/*  184 */   CHICKEN(new String[] { "RAW_CHICKEN" }),
/*  185 */   CHICKEN_SPAWN_EGG(93, new String[] { "MONSTER_EGG" }),
/*  186 */   CHIPPED_ANVIL(1, new String[] { "ANVIL" }),
/*  187 */   CHISELED_NETHER_BRICKS(1, new String[] { "NETHER_BRICKS" }),
/*  188 */   CHISELED_POLISHED_BLACKSTONE(new String[] { "1.16", "POLISHED_BLACKSTONE" }),
/*  189 */   CHISELED_QUARTZ_BLOCK(1, new String[] { "QUARTZ_BLOCK" }),
/*  190 */   CHISELED_RED_SANDSTONE(1, new String[] { "RED_SANDSTONE" }),
/*  191 */   CHISELED_SANDSTONE(1, new String[] { "SANDSTONE" }),
/*  192 */   CHISELED_STONE_BRICKS(3, new String[] { "SMOOTH_BRICK" }),
/*  193 */   CHORUS_FLOWER(new String[] { "1.9" }),
/*  194 */   CHORUS_FRUIT(new String[] { "1.9" }),
/*  195 */   CHORUS_PLANT(new String[] { "1.9" }),
/*  196 */   CLAY,
/*  197 */   CLAY_BALL,
/*  198 */   CLOCK(new String[] { "WATCH" }),
/*  199 */   COAL,
/*  200 */   COAL_BLOCK,
/*  201 */   COAL_ORE,
/*  202 */   COARSE_DIRT(1, new String[] { "DIRT" }),
/*  203 */   COBBLESTONE,
/*  204 */   COBBLESTONE_SLAB(3, new String[] { "STEP" }),
/*  205 */   COBBLESTONE_STAIRS,
/*  206 */   COBBLESTONE_WALL(new String[] { "COBBLE_WALL" }),
/*  207 */   COBWEB(new String[] { "WEB" }),
/*  208 */   COCOA(new String[] { "1.15" }),
/*  209 */   COCOA_BEANS(3, new String[] { "INK_SACK", "COCOA" }),
/*  210 */   COD(new String[] { "RAW_FISH" }),
/*  211 */   COD_BUCKET(new String[] { "1.13", "BUCKET", "WATER_BUCKET", "" }),
/*  212 */   COD_SPAWN_EGG(new String[] { "1.13", "MONSTER_EGG", "" }),
/*  213 */   COMMAND_BLOCK(new String[] { "COMMAND" }),
/*  214 */   COMMAND_BLOCK_MINECART(new String[] { "COMMAND_MINECART" }),
/*  215 */   COMPARATOR(new String[] { "REDSTONE_COMPARATOR_OFF", "REDSTONE_COMPARATOR_ON", "REDSTONE_COMPARATOR" }),
/*  216 */   COMPASS,
/*  217 */   COMPOSTER(new String[] { "1.14", "CAULDRON", "" }),
/*  218 */   CONDUIT(new String[] { "1.13", "BEACON" }),
/*  219 */   COOKED_BEEF,
/*  220 */   COOKED_CHICKEN,
/*  221 */   COOKED_COD(new String[] { "COOKED_FISH" }),
/*  222 */   COOKED_MUTTON,
/*  223 */   COOKED_PORKCHOP(new String[] { "PORK", "GRILLED_PORK" }),
/*  224 */   COOKED_RABBIT,
/*  225 */   COOKED_SALMON(1, new String[] { "COOKED_FISH" }),
/*  226 */   COOKIE,
/*  227 */   CORNFLOWER(4, new String[] { "1.14", "BLUE_DYE", "" }),
/*  228 */   COW_SPAWN_EGG(92, new String[] { "MONSTER_EGG" }),
/*  229 */   CRACKED_NETHER_BRICKS(2, new String[] { "NETHER_BRICKS" }),
/*  230 */   CRACKED_POLISHED_BLACKSTONE_BRICKS(new String[] { "1.16", "POLISHED_BLACKSTONE_BRICKS" }),
/*  231 */   CRACKED_STONE_BRICKS(2, new String[] { "SMOOTH_BRICK" }),
/*  232 */   CRAFTING_TABLE(new String[] { "WORKBENCH" }),
/*  233 */   CREEPER_BANNER_PATTERN,
/*  234 */   CREEPER_HEAD(4, new String[] { "SKULL", "SKULL_ITEM" }),
/*  235 */   CREEPER_SPAWN_EGG(50, new String[] { "MONSTER_EGG" }),
/*  236 */   CREEPER_WALL_HEAD(4, new String[] { "SKULL", "SKULL_ITEM" }),
/*  237 */   CRIMSON_BUTTON(new String[] { "1.16" }),
/*  238 */   CRIMSON_DOOR(new String[] { "1.16" }),
/*  239 */   CRIMSON_FENCE(new String[] { "1.16" }),
/*  240 */   CRIMSON_FENCE_GATE(new String[] { "1.16" }),
/*  241 */   CRIMSON_FUNGUS(new String[] { "1.16" }),
/*  242 */   CRIMSON_HYPHAE(new String[] { "1.16" }),
/*  243 */   CRIMSON_NYLIUM(new String[] { "1.16" }),
/*  244 */   CRIMSON_PLANKS(new String[] { "1.16" }),
/*  245 */   CRIMSON_PRESSURE_PLATE(new String[] { "1.16" }),
/*  246 */   CRIMSON_ROOTS(new String[] { "1.16" }),
/*  247 */   CRIMSON_SIGN(new String[] { "1.16" }),
/*  248 */   CRIMSON_SLAB(new String[] { "1.16" }),
/*  249 */   CRIMSON_STAIRS(new String[] { "1.16" }),
/*  250 */   CRIMSON_STEM(new String[] { "1.16" }),
/*  251 */   CRIMSON_TRAPDOOR(new String[] { "1.16" }),
/*  252 */   CRIMSON_WALL_SIGN(new String[] { "1.16" }),
/*  253 */   CROSSBOW,
/*  254 */   CRYING_OBSIDIAN(new String[] { "1.16" }),
/*  255 */   CUT_RED_SANDSTONE(new String[] { "1.13" }),
/*  256 */   CUT_RED_SANDSTONE_SLAB(new String[] { "STONE_SLAB2" }),
/*  257 */   CUT_SANDSTONE(new String[] { "1.13" }),
/*  258 */   CUT_SANDSTONE_SLAB(new String[] { "STEP" }),
/*  259 */   CYAN_BANNER(6, new String[] { "BANNER", "STANDING_BANNER" }),
/*  260 */   CYAN_BED(9, new String[] { "BED_BLOCK", "BED" }),
/*  261 */   CYAN_CARPET(9, new String[] { "CARPET" }),
/*  262 */   CYAN_CONCRETE(9, new String[] { "CONCRETE" }),
/*  263 */   CYAN_CONCRETE_POWDER(9, new String[] { "CONCRETE_POWDER" }),
/*  264 */   CYAN_DYE(6, new String[] { "INK_SACK" }),
/*  265 */   CYAN_GLAZED_TERRACOTTA(9, new String[] { "1.12", "HARD_CLAY", "STAINED_CLAY", "CYAN_TERRACOTTA" }),
/*  266 */   CYAN_SHULKER_BOX,
/*  267 */   CYAN_STAINED_GLASS(9, new String[] { "STAINED_GLASS" }),
/*  268 */   CYAN_STAINED_GLASS_PANE(9, new String[] { "STAINED_GLASS_PANE" }),
/*  269 */   CYAN_TERRACOTTA(9, new String[] { "HARD_CLAY", "STAINED_CLAY" }),
/*  270 */   CYAN_WALL_BANNER(6, new String[] { "WALL_BANNER" }),
/*  271 */   CYAN_WOOL(9, new String[] { "WOOL" }),
/*  272 */   DAMAGED_ANVIL(2, new String[] { "ANVIL" }),
/*  273 */   DANDELION(new String[] { "YELLOW_FLOWER" }),
/*  274 */   DARK_OAK_BOAT(new String[] { "BOAT_DARK_OAK" }),
/*  275 */   DARK_OAK_BUTTON(new String[] { "WOOD_BUTTON" }),
/*  276 */   DARK_OAK_DOOR(new String[] { "DARK_OAK_DOOR_ITEM" }),
/*  277 */   DARK_OAK_FENCE,
/*  278 */   DARK_OAK_FENCE_GATE,
/*  279 */   DARK_OAK_LEAVES(4, new String[] { "LEAVES", "LEAVES_2" }),
/*  280 */   DARK_OAK_LOG(1, new String[] { "LOG", "LOG_2" }),
/*  281 */   DARK_OAK_PLANKS(5, new String[] { "WOOD" }),
/*  282 */   DARK_OAK_PRESSURE_PLATE(new String[] { "WOOD_PLATE" }),
/*  283 */   DARK_OAK_SAPLING(5, new String[] { "SAPLING" }),
/*  284 */   DARK_OAK_SIGN(new String[] { "SIGN" }),
/*  285 */   DARK_OAK_SLAB(5, new String[] { "WOOD_STEP", "WOODEN_SLAB", "WOOD_DOUBLE_STEP" }),
/*  286 */   DARK_OAK_STAIRS,
/*  287 */   DARK_OAK_TRAPDOOR(new String[] { "TRAP_DOOR" }),
/*  288 */   DARK_OAK_WALL_SIGN(new String[] { "SIGN_POST", "WALL_SIGN" }),
/*  289 */   DARK_OAK_WOOD(1, new String[] { "LOG", "LOG_2" }),
/*  290 */   DARK_PRISMARINE(1, new String[] { "PRISMARINE" }),
/*  291 */   DARK_PRISMARINE_SLAB(new String[] { "1.13" }),
/*  292 */   DARK_PRISMARINE_STAIRS(new String[] { "1.13" }),
/*  293 */   DAYLIGHT_DETECTOR(new String[] { "DAYLIGHT_DETECTOR_INVERTED" }),
/*  294 */   DEAD_BRAIN_CORAL(new String[] { "1.13" }),
/*  295 */   DEAD_BRAIN_CORAL_BLOCK(new String[] { "1.13" }),
/*  296 */   DEAD_BRAIN_CORAL_FAN(new String[] { "1.13" }),
/*  297 */   DEAD_BRAIN_CORAL_WALL_FAN(new String[] { "1.13" }),
/*  298 */   DEAD_BUBBLE_CORAL(new String[] { "1.13" }),
/*  299 */   DEAD_BUBBLE_CORAL_BLOCK(new String[] { "1.13" }),
/*  300 */   DEAD_BUBBLE_CORAL_FAN(new String[] { "1.13" }),
/*  301 */   DEAD_BUBBLE_CORAL_WALL_FAN(new String[] { "1.13" }),
/*  302 */   DEAD_BUSH,
/*  303 */   DEAD_FIRE_CORAL(new String[] { "1.13" }),
/*  304 */   DEAD_FIRE_CORAL_BLOCK(new String[] { "1.13" }),
/*  305 */   DEAD_FIRE_CORAL_FAN(new String[] { "1.13" }),
/*  306 */   DEAD_FIRE_CORAL_WALL_FAN(new String[] { "1.13" }),
/*  307 */   DEAD_HORN_CORAL(new String[] { "1.13" }),
/*  308 */   DEAD_HORN_CORAL_BLOCK(new String[] { "1.13" }),
/*  309 */   DEAD_HORN_CORAL_FAN(new String[] { "1.13" }),
/*  310 */   DEAD_HORN_CORAL_WALL_FAN(new String[] { "1.13" }),
/*  311 */   DEAD_TUBE_CORAL(new String[] { "1.13" }),
/*  312 */   DEAD_TUBE_CORAL_BLOCK(new String[] { "1.13" }),
/*  313 */   DEAD_TUBE_CORAL_FAN(new String[] { "1.13" }),
/*  314 */   DEAD_TUBE_CORAL_WALL_FAN(new String[] { "1.13" }),
/*  315 */   DEBUG_STICK(new String[] { "1.13", "STICK", "" }),
/*  316 */   DETECTOR_RAIL,
/*  317 */   DIAMOND,
/*  318 */   DIAMOND_AXE,
/*  319 */   DIAMOND_BLOCK,
/*  320 */   DIAMOND_BOOTS,
/*  321 */   DIAMOND_CHESTPLATE,
/*  322 */   DIAMOND_HELMET,
/*  323 */   DIAMOND_HOE,
/*  324 */   DIAMOND_HORSE_ARMOR(new String[] { "DIAMOND_BARDING" }),
/*  325 */   DIAMOND_LEGGINGS,
/*  326 */   DIAMOND_ORE,
/*  327 */   DIAMOND_PICKAXE,
/*  328 */   DIAMOND_SHOVEL(new String[] { "DIAMOND_SPADE" }),
/*  329 */   DIAMOND_SWORD,
/*  330 */   DIORITE(3, new String[] { "STONE" }),
/*  331 */   DIORITE_SLAB,
/*  332 */   DIORITE_STAIRS,
/*  333 */   DIORITE_WALL,
/*  334 */   DIRT,
/*  335 */   DISPENSER,
/*  336 */   DOLPHIN_SPAWN_EGG(new String[] { "1.13", "MONSTER_EGG", "" }),
/*  337 */   DONKEY_SPAWN_EGG(32, new String[] { "MONSTER_EGG" }),
/*  338 */   DRAGON_BREATH(new String[] { "DRAGONS_BREATH" }),
/*  339 */   DRAGON_EGG,
/*  340 */   DRAGON_HEAD(5, new String[] { "1.9", "SKULL", "SKULL_ITEM" }),
/*  341 */   DRAGON_WALL_HEAD(5, new String[] { "SKULL", "SKULL_ITEM" }),
/*  342 */   DRIED_KELP(new String[] { "1.13" }),
/*  343 */   DRIED_KELP_BLOCK(new String[] { "1.13" }),
/*  344 */   DROPPER,
/*  345 */   DROWNED_SPAWN_EGG(new String[] { "1.13", "MONSTER_EGG", "" }),
/*  346 */   EGG,
/*  347 */   ELDER_GUARDIAN_SPAWN_EGG(4, new String[] { "MONSTER_EGG" }),
/*  348 */   ELYTRA,
/*  349 */   EMERALD,
/*  350 */   EMERALD_BLOCK,
/*  351 */   EMERALD_ORE,
/*  352 */   ENCHANTED_BOOK,
/*  353 */   ENCHANTED_GOLDEN_APPLE(1, new String[] { "GOLDEN_APPLE" }),
/*  354 */   ENCHANTING_TABLE(new String[] { "ENCHANTMENT_TABLE" }),
/*  355 */   ENDERMAN_SPAWN_EGG(58, new String[] { "MONSTER_EGG" }),
/*  356 */   ENDERMITE_SPAWN_EGG(67, new String[] { "MONSTER_EGG" }),
/*  357 */   ENDER_CHEST,
/*  358 */   ENDER_EYE(new String[] { "EYE_OF_ENDER" }),
/*  359 */   ENDER_PEARL,
/*  360 */   END_CRYSTAL,
/*  361 */   END_GATEWAY(new String[] { "1.9" }),
/*  362 */   END_PORTAL(new String[] { "ENDER_PORTAL" }),
/*  363 */   END_PORTAL_FRAME(new String[] { "ENDER_PORTAL_FRAME" }),
/*  364 */   END_ROD(new String[] { "1.9", "BLAZE_ROD", "" }),
/*  365 */   END_STONE(new String[] { "ENDER_STONE" }),
/*  366 */   END_STONE_BRICKS(new String[] { "END_BRICKS" }),
/*  367 */   END_STONE_BRICK_SLAB(6, new String[] { "STEP" }),
/*  368 */   END_STONE_BRICK_STAIRS(new String[] { "SMOOTH_STAIRS" }),
/*  369 */   END_STONE_BRICK_WALL,
/*  370 */   EVOKER_SPAWN_EGG(34, new String[] { "MONSTER_EGG" }),
/*  371 */   EXPERIENCE_BOTTLE(new String[] { "EXP_BOTTLE" }),
/*  372 */   FARMLAND(new String[] { "SOIL" }),
/*  373 */   FEATHER,
/*  374 */   FERMENTED_SPIDER_EYE,
/*  375 */   FERN(1, new String[] { "LONG_GRASS" }),
/*  376 */   FILLED_MAP(new String[] { "MAP" }),
/*  377 */   FIRE,
/*  378 */   FIREWORK_ROCKET(new String[] { "FIREWORK" }),
/*  379 */   FIREWORK_STAR(new String[] { "FIREWORK_CHARGE" }),
/*  380 */   FIRE_CHARGE(new String[] { "FIREBALL" }),
/*  381 */   FIRE_CORAL(new String[] { "1.13" }),
/*  382 */   FIRE_CORAL_BLOCK(new String[] { "1.13" }),
/*  383 */   FIRE_CORAL_FAN(new String[] { "1.13" }),
/*  384 */   FIRE_CORAL_WALL_FAN,
/*  385 */   FISHING_ROD,
/*  386 */   FLETCHING_TABLE(new String[] { "1.14", "CRAFTING_TABLE", "" }),
/*  387 */   FLINT,
/*  388 */   FLINT_AND_STEEL,
/*  389 */   FLOWER_BANNER_PATTERN,
/*  390 */   FLOWER_POT(new String[] { "FLOWER_POT_ITEM" }),
/*  391 */   FOX_SPAWN_EGG(new String[] { "1.14" }),
/*  392 */   FROSTED_ICE(new String[] { "1.9", "PACKED_ICE", "" }),
/*  393 */   FURNACE(new String[] { "BURNING_FURNACE" }),
/*  394 */   FURNACE_MINECART(new String[] { "POWERED_MINECART" }),
/*  395 */   GHAST_SPAWN_EGG(56, new String[] { "MONSTER_EGG" }),
/*  396 */   GHAST_TEAR,
/*  397 */   GLASS,
/*  398 */   GLASS_BOTTLE,
/*  399 */   GLASS_PANE(new String[] { "THIN_GLASS" }),
/*  400 */   GLISTERING_MELON_SLICE(new String[] { "SPECKLED_MELON" }),
/*  401 */   GLOBE_BANNER_PATTERN,
/*  402 */   GLOWSTONE,
/*  403 */   GLOWSTONE_DUST,
/*  404 */   GOLDEN_APPLE,
/*  405 */   GOLDEN_AXE(new String[] { "GOLD_AXE" }),
/*  406 */   GOLDEN_BOOTS(new String[] { "GOLD_BOOTS" }),
/*  407 */   GOLDEN_CARROT,
/*  408 */   GOLDEN_CHESTPLATE(new String[] { "GOLD_CHESTPLATE" }),
/*  409 */   GOLDEN_HELMET(new String[] { "GOLD_HELMET" }),
/*  410 */   GOLDEN_HOE(new String[] { "GOLD_HOE" }),
/*  411 */   GOLDEN_HORSE_ARMOR(new String[] { "GOLD_BARDING" }),
/*  412 */   GOLDEN_LEGGINGS(new String[] { "GOLD_LEGGINGS" }),
/*  413 */   GOLDEN_PICKAXE(new String[] { "GOLD_PICKAXE" }),
/*  414 */   GOLDEN_SHOVEL(new String[] { "GOLD_SPADE" }),
/*  415 */   GOLDEN_SWORD(new String[] { "GOLD_SWORD" }),
/*  416 */   GOLD_BLOCK,
/*  417 */   GOLD_INGOT,
/*  418 */   GOLD_NUGGET,
/*  419 */   GOLD_ORE,
/*  420 */   GRANITE(1, new String[] { "STONE" }),
/*  421 */   GRANITE_SLAB,
/*  422 */   GRANITE_STAIRS,
/*  423 */   GRANITE_WALL,
/*  424 */   GRASS,
/*  425 */   GRASS_BLOCK(new String[] { "GRASS" }),
/*  426 */   GRASS_PATH,
/*  427 */   GRAVEL,
/*  428 */   GRAY_BANNER(8, new String[] { "BANNER", "STANDING_BANNER" }),
/*  429 */   GRAY_BED(7, new String[] { "BED_BLOCK", "BED" }),
/*  430 */   GRAY_CARPET(7, new String[] { "CARPET" }),
/*  431 */   GRAY_CONCRETE(7, new String[] { "CONCRETE" }),
/*  432 */   GRAY_CONCRETE_POWDER(7, new String[] { "CONCRETE_POWDER" }),
/*  433 */   GRAY_DYE(8, new String[] { "INK_SACK" }),
/*  434 */   GRAY_GLAZED_TERRACOTTA(7, new String[] { "1.12", "HARD_CLAY", "STAINED_CLAY", "GRAY_TERRACOTTA" }),
/*  435 */   GRAY_SHULKER_BOX,
/*  436 */   GRAY_STAINED_GLASS(7, new String[] { "STAINED_GLASS" }),
/*  437 */   GRAY_STAINED_GLASS_PANE(7, new String[] { "THIN_GLASS", "STAINED_GLASS_PANE" }),
/*  438 */   GRAY_TERRACOTTA(7, new String[] { "HARD_CLAY", "STAINED_CLAY" }),
/*  439 */   GRAY_WALL_BANNER(8, new String[] { "WALL_BANNER" }),
/*  440 */   GRAY_WOOL(7, new String[] { "WOOL" }),
/*  441 */   GREEN_BANNER(2, new String[] { "BANNER", "STANDING_BANNER" }),
/*  442 */   GREEN_BED(13, new String[] { "BED_BLOCK", "BED" }),
/*  443 */   GREEN_CARPET(13, new String[] { "CARPET" }),
/*  444 */   GREEN_CONCRETE(13, new String[] { "CONCRETE" }),
/*  445 */   GREEN_CONCRETE_POWDER(13, new String[] { "CONCRETE_POWDER" }),
/*  446 */   GREEN_DYE(2, new String[] { "INK_SACK", "CACTUS_GREEN" }),
/*  447 */   GREEN_GLAZED_TERRACOTTA(13, new String[] { "1.12", "HARD_CLAY", "STAINED_CLAY", "GREEN_TERRACOTTA" }),
/*  448 */   GREEN_SHULKER_BOX,
/*  449 */   GREEN_STAINED_GLASS(13, new String[] { "STAINED_GLASS" }),
/*  450 */   GREEN_STAINED_GLASS_PANE(13, new String[] { "THIN_GLASS", "STAINED_GLASS_PANE" }),
/*  451 */   GREEN_TERRACOTTA(13, new String[] { "HARD_CLAY", "STAINED_CLAY" }),
/*  452 */   GREEN_WALL_BANNER(2, new String[] { "WALL_BANNER" }),
/*  453 */   GREEN_WOOL(13, new String[] { "WOOL" }),
/*  454 */   GRINDSTONE(new String[] { "1.14", "ANVIL", "" }),
/*  455 */   GUARDIAN_SPAWN_EGG(68, new String[] { "MONSTER_EGG" }),
/*  456 */   GUNPOWDER(new String[] { "SULPHUR" }),
/*  457 */   HAY_BLOCK,
/*  458 */   HEART_OF_THE_SEA(new String[] { "1.13" }),
/*  459 */   HEAVY_WEIGHTED_PRESSURE_PLATE(new String[] { "IRON_PLATE" }),
/*  460 */   HOGLIN_SPAWN_EGG(new String[] { "1.16", "MONSTER_EGG" }),
/*  461 */   HONEYCOMB(new String[] { "1.15" }),
/*  462 */   HONEYCOMB_BLOCK(new String[] { "1.15" }),
/*  463 */   HONEY_BLOCK(new String[] { "1.15", "SLIME_BLOCK", "" }),
/*  464 */   HONEY_BOTTLE(new String[] { "1.15", "GLASS_BOTTLE", "" }),
/*  465 */   HOPPER,
/*  466 */   HOPPER_MINECART,
/*  467 */   HORN_CORAL(new String[] { "1.13" }),
/*  468 */   HORN_CORAL_BLOCK(new String[] { "1.13" }),
/*  469 */   HORN_CORAL_FAN(new String[] { "1.13" }),
/*  470 */   HORN_CORAL_WALL_FAN,
/*  471 */   HORSE_SPAWN_EGG(100, new String[] { "MONSTER_EGG" }),
/*  472 */   HUSK_SPAWN_EGG(23, new String[] { "MONSTER_EGG" }),
/*  473 */   ICE,
/*  474 */   INFESTED_CHISELED_STONE_BRICKS(5, new String[] { "MONSTER_EGGS", "SMOOTH_BRICK" }),
/*  475 */   INFESTED_COBBLESTONE(1, new String[] { "MONSTER_EGGS" }),
/*  476 */   INFESTED_CRACKED_STONE_BRICKS(4, new String[] { "MONSTER_EGGS", "SMOOTH_BRICK" }),
/*  477 */   INFESTED_MOSSY_STONE_BRICKS(3, new String[] { "MONSTER_EGGS" }),
/*  478 */   INFESTED_STONE(new String[] { "MONSTER_EGGS" }),
/*  479 */   INFESTED_STONE_BRICKS(2, new String[] { "MONSTER_EGGS", "SMOOTH_BRICK" }),
/*  480 */   INK_SAC(new String[] { "INK_SACK" }),
/*  481 */   IRON_AXE,
/*  482 */   IRON_BARS(new String[] { "IRON_FENCE" }),
/*  483 */   IRON_BLOCK,
/*  484 */   IRON_BOOTS,
/*  485 */   IRON_CHESTPLATE,
/*  486 */   IRON_DOOR(new String[] { "IRON_DOOR_BLOCK" }),
/*  487 */   IRON_HELMET,
/*  488 */   IRON_HOE,
/*  489 */   IRON_HORSE_ARMOR(new String[] { "IRON_BARDING" }),
/*  490 */   IRON_INGOT,
/*  491 */   IRON_LEGGINGS,
/*  492 */   IRON_NUGGET,
/*  493 */   IRON_ORE,
/*  494 */   IRON_PICKAXE,
/*  495 */   IRON_SHOVEL(new String[] { "IRON_SPADE" }),
/*  496 */   IRON_SWORD,
/*  497 */   IRON_TRAPDOOR,
/*  498 */   ITEM_FRAME,
/*  499 */   JACK_O_LANTERN,
/*  500 */   JIGSAW(new String[] { "1.14", "COMMAND_BLOCK", "STRUCTURE_BLOCK", "" }),
/*  501 */   JUKEBOX,
/*  502 */   JUNGLE_BOAT(new String[] { "BOAT_JUNGLE" }),
/*  503 */   JUNGLE_BUTTON(new String[] { "WOOD_BUTTON" }),
/*  504 */   JUNGLE_DOOR(new String[] { "JUNGLE_DOOR_ITEM" }),
/*  505 */   JUNGLE_FENCE,
/*  506 */   JUNGLE_FENCE_GATE,
/*  507 */   JUNGLE_LEAVES(3, new String[] { "LEAVES" }),
/*  508 */   JUNGLE_LOG(3, new String[] { "LOG" }),
/*  509 */   JUNGLE_PLANKS(3, new String[] { "WOOD" }),
/*  510 */   JUNGLE_PRESSURE_PLATE(new String[] { "WOOD_PLATE" }),
/*  511 */   JUNGLE_SAPLING(3, new String[] { "SAPLING" }),
/*  512 */   JUNGLE_SIGN(new String[] { "SIGN" }),
/*  513 */   JUNGLE_SLAB(3, new String[] { "WOOD_STEP", "WOODEN_SLAB", "WOOD_DOUBLE_STEP" }),
/*  514 */   JUNGLE_STAIRS(new String[] { "JUNGLE_WOOD_STAIRS" }),
/*  515 */   JUNGLE_TRAPDOOR(new String[] { "TRAP_DOOR" }),
/*  516 */   JUNGLE_WALL_SIGN(new String[] { "SIGN_POST", "WALL_SIGN" }),
/*  517 */   JUNGLE_WOOD(3, new String[] { "LOG" }),
/*  518 */   KELP(new String[] { "1.13" }),
/*  519 */   KELP_PLANT(new String[] { "1.13" }),
/*  520 */   KNOWLEDGE_BOOK(new String[] { "1.12", "BOOK" }),
/*  521 */   LADDER,
/*  522 */   LANTERN(new String[] { "1.14", "SEA_LANTERN", "" }),
/*  523 */   LAPIS_BLOCK,
/*  524 */   LAPIS_LAZULI(4, new String[] { "INK_SACK" }),
/*  525 */   LAPIS_ORE,
/*  526 */   LARGE_FERN(3, new String[] { "DOUBLE_PLANT" }),
/*  527 */   LAVA(new String[] { "STATIONARY_LAVA" }),
/*  528 */   LAVA_BUCKET,
/*  529 */   LEAD(new String[] { "LEASH" }),
/*  530 */   LEATHER,
/*  531 */   LEATHER_BOOTS,
/*  532 */   LEATHER_CHESTPLATE,
/*  533 */   LEATHER_HELMET,
/*  534 */   LEATHER_HORSE_ARMOR(new String[] { "1.14", "IRON_HORSE_ARMOR", "" }),
/*  535 */   LEATHER_LEGGINGS,
/*  536 */   LECTERN(new String[] { "1.14", "BOOKSHELF", "" }),
/*  537 */   LEVER,
/*  538 */   LIGHT_BLUE_BANNER(12, new String[] { "BANNER", "STANDING_BANNER" }),
/*  539 */   LIGHT_BLUE_BED(3, new String[] { "BED_BLOCK", "BED" }),
/*  540 */   LIGHT_BLUE_CARPET(3, new String[] { "CARPET" }),
/*  541 */   LIGHT_BLUE_CONCRETE(3, new String[] { "CONCRETE" }),
/*  542 */   LIGHT_BLUE_CONCRETE_POWDER(3, new String[] { "CONCRETE_POWDER" }),
/*  543 */   LIGHT_BLUE_DYE(12, new String[] { "INK_SACK" }),
/*  544 */   LIGHT_BLUE_GLAZED_TERRACOTTA(3, new String[] { "1.12", "HARD_CLAY", "STAINED_CLAY", "LIGHT_BLUE_TERRACOTTA" }),
/*  545 */   LIGHT_BLUE_SHULKER_BOX,
/*  546 */   LIGHT_BLUE_STAINED_GLASS(3, new String[] { "STAINED_GLASS" }),
/*  547 */   LIGHT_BLUE_STAINED_GLASS_PANE(3, new String[] { "THIN_GLASS", "STAINED_GLASS_PANE" }),
/*  548 */   LIGHT_BLUE_TERRACOTTA(3, new String[] { "STAINED_CLAY" }),
/*  549 */   LIGHT_BLUE_WALL_BANNER(12, new String[] { "WALL_BANNER", "BANNER", "STANDING_BANNER" }),
/*  550 */   LIGHT_BLUE_WOOL(3, new String[] { "WOOL" }),
/*  551 */   LIGHT_GRAY_BANNER(7, new String[] { "BANNER", "STANDING_BANNER" }),
/*  552 */   LIGHT_GRAY_BED(8, new String[] { "BED_BLOCK", "BED" }),
/*  553 */   LIGHT_GRAY_CARPET(8, new String[] { "CARPET" }),
/*  554 */   LIGHT_GRAY_CONCRETE(8, new String[] { "CONCRETE" }),
/*  555 */   LIGHT_GRAY_CONCRETE_POWDER(8, new String[] { "CONCRETE_POWDER" }),
/*  556 */   LIGHT_GRAY_DYE(7, new String[] { "INK_SACK" }),
/*  557 */   LIGHT_GRAY_GLAZED_TERRACOTTA(new String[] { "1.12", "HARD_CLAY", "STAINED_CLAY", "LIGHT_GRAY_TERRACOTTA", "SILVER_GLAZED_TERRACOTTA" }),
/*  558 */   LIGHT_GRAY_SHULKER_BOX(new String[] { "SILVER_SHULKER_BOX" }),
/*  559 */   LIGHT_GRAY_STAINED_GLASS(8, new String[] { "STAINED_GLASS" }),
/*  560 */   LIGHT_GRAY_STAINED_GLASS_PANE(8, new String[] { "THIN_GLASS", "STAINED_GLASS_PANE" }),
/*  561 */   LIGHT_GRAY_TERRACOTTA(8, new String[] { "HARD_CLAY", "STAINED_CLAY" }),
/*  562 */   LIGHT_GRAY_WALL_BANNER(7, new String[] { "WALL_BANNER" }),
/*  563 */   LIGHT_GRAY_WOOL(8, new String[] { "WOOL" }),
/*  564 */   LIGHT_WEIGHTED_PRESSURE_PLATE(new String[] { "GOLD_PLATE" }),
/*  565 */   LILAC(1, new String[] { "DOUBLE_PLANT" }),
/*  566 */   LILY_OF_THE_VALLEY(15, new String[] { "1.14", "WHITE_DYE", "" }),
/*  567 */   LILY_PAD(new String[] { "WATER_LILY" }),
/*  568 */   LIME_BANNER(10, new String[] { "BANNER", "STANDING_BANNER" }),
/*  569 */   LIME_BED(5, new String[] { "BED_BLOCK", "BED" }),
/*  570 */   LIME_CARPET(5, new String[] { "CARPET" }),
/*  571 */   LIME_CONCRETE(5, new String[] { "CONCRETE" }),
/*  572 */   LIME_CONCRETE_POWDER(5, new String[] { "CONCRETE_POWDER" }),
/*  573 */   LIME_DYE(10, new String[] { "INK_SACK" }),
/*  574 */   LIME_GLAZED_TERRACOTTA(5, new String[] { "1.12", "HARD_CLAY", "STAINED_CLAY", "LIME_TERRACOTTA" }),
/*  575 */   LIME_SHULKER_BOX,
/*  576 */   LIME_STAINED_GLASS(5, new String[] { "STAINED_GLASS" }),
/*  577 */   LIME_STAINED_GLASS_PANE(5, new String[] { "STAINED_GLASS_PANE" }),
/*  578 */   LIME_TERRACOTTA(5, new String[] { "HARD_CLAY", "STAINED_CLAY" }),
/*  579 */   LIME_WALL_BANNER(10, new String[] { "WALL_BANNER" }),
/*  580 */   LIME_WOOL(5, new String[] { "WOOL" }),
/*  581 */   LINGERING_POTION,
/*  582 */   LLAMA_SPAWN_EGG(103, new String[] { "MONSTER_EGG" }),
/*  583 */   LODESTONE(new String[] { "1.16" }),
/*  584 */   LOOM(new String[] { "1.14" }),
/*  585 */   MAGENTA_BANNER(13, new String[] { "BANNER", "STANDING_BANNER" }),
/*  586 */   MAGENTA_BED(2, new String[] { "BED_BLOCK", "BED" }),
/*  587 */   MAGENTA_CARPET(2, new String[] { "CARPET" }),
/*  588 */   MAGENTA_CONCRETE(2, new String[] { "CONCRETE" }),
/*  589 */   MAGENTA_CONCRETE_POWDER(2, new String[] { "CONCRETE_POWDER" }),
/*  590 */   MAGENTA_DYE(13, new String[] { "INK_SACK" }),
/*  591 */   MAGENTA_GLAZED_TERRACOTTA(2, new String[] { "1.12", "HARD_CLAY", "STAINED_CLAY", "MAGENTA_TERRACOTTA" }),
/*  592 */   MAGENTA_SHULKER_BOX,
/*  593 */   MAGENTA_STAINED_GLASS(2, new String[] { "STAINED_GLASS" }),
/*  594 */   MAGENTA_STAINED_GLASS_PANE(2, new String[] { "THIN_GLASS", "STAINED_GLASS_PANE" }),
/*  595 */   MAGENTA_TERRACOTTA(2, new String[] { "HARD_CLAY", "STAINED_CLAY" }),
/*  596 */   MAGENTA_WALL_BANNER(13, new String[] { "WALL_BANNER" }),
/*  597 */   MAGENTA_WOOL(2, new String[] { "WOOL" }),
/*  598 */   MAGMA_BLOCK(new String[] { "1.10", "MAGMA" }),
/*  599 */   MAGMA_CREAM,
/*  600 */   MAGMA_CUBE_SPAWN_EGG(62, new String[] { "MONSTER_EGG" }),
/*  601 */   MAP(new String[] { "EMPTY_MAP" }),
/*  602 */   MELON(new String[] { "MELON_BLOCK" }),
/*  603 */   MELON_SEEDS,
/*  604 */   MELON_SLICE(new String[] { "MELON" }),
/*  605 */   MELON_STEM,
/*  606 */   MILK_BUCKET,
/*  607 */   MINECART,
/*  608 */   MOJANG_BANNER_PATTERN,
/*  609 */   MOOSHROOM_SPAWN_EGG(96, new String[] { "MONSTER_EGG" }),
/*  610 */   MOSSY_COBBLESTONE,
/*  611 */   MOSSY_COBBLESTONE_SLAB(3, new String[] { "STEP" }),
/*  612 */   MOSSY_COBBLESTONE_STAIRS,
/*  613 */   MOSSY_COBBLESTONE_WALL(1, new String[] { "COBBLE_WALL", "COBBLESTONE_WALL" }),
/*  614 */   MOSSY_STONE_BRICKS(1, new String[] { "SMOOTH_BRICK" }),
/*  615 */   MOSSY_STONE_BRICK_SLAB(5, new String[] { "STEP" }),
/*  616 */   MOSSY_STONE_BRICK_STAIRS(new String[] { "SMOOTH_STAIRS" }),
/*  617 */   MOSSY_STONE_BRICK_WALL,
/*  618 */   MOVING_PISTON(new String[] { "PISTON_BASE", "PISTON_MOVING_PIECE" }),
/*  619 */   MULE_SPAWN_EGG(32, new String[] { "MONSTER_EGG" }),
/*  620 */   MUSHROOM_STEM(new String[] { "BROWN_MUSHROOM" }),
/*  621 */   MUSHROOM_STEW(new String[] { "MUSHROOM_SOUP" }),
/*  622 */   MUSIC_DISC_11(new String[] { "GOLD_RECORD" }),
/*  623 */   MUSIC_DISC_13(new String[] { "GREEN_RECORD" }),
/*  624 */   MUSIC_DISC_BLOCKS(new String[] { "RECORD_3" }),
/*  625 */   MUSIC_DISC_CAT(new String[] { "RECORD_4" }),
/*  626 */   MUSIC_DISC_CHIRP(new String[] { "RECORD_5" }),
/*  627 */   MUSIC_DISC_FAR(new String[] { "RECORD_6" }),
/*  628 */   MUSIC_DISC_MALL(new String[] { "RECORD_7" }),
/*  629 */   MUSIC_DISC_MELLOHI(new String[] { "RECORD_8" }),
/*  630 */   MUSIC_DISC_STAL(new String[] { "RECORD_9" }),
/*  631 */   MUSIC_DISC_STRAD(new String[] { "RECORD_10" }),
/*  632 */   MUSIC_DISC_WAIT(new String[] { "RECORD_11" }),
/*  633 */   MUSIC_DISC_WARD(new String[] { "RECORD_12" }),
/*  634 */   MUTTON,
/*  635 */   MYCELIUM(new String[] { "MYCEL" }),
/*  636 */   NAME_TAG,
/*  637 */   NAUTILUS_SHELL(new String[] { "1.13" }),
/*  638 */   NETHERITE_AXE(new String[] { "1.16" }),
/*  639 */   NETHERITE_BLOCK(new String[] { "1.16" }),
/*  640 */   NETHERITE_BOOTS(new String[] { "1.16" }),
/*  641 */   NETHERITE_CHESTPLATE(new String[] { "1.16" }),
/*  642 */   NETHERITE_HELMET(new String[] { "1.16" }),
/*  643 */   NETHERITE_HOE(new String[] { "1.16" }),
/*  644 */   NETHERITE_INGOT(new String[] { "1.16" }),
/*  645 */   NETHERITE_LEGGINGS(new String[] { "1.16" }),
/*  646 */   NETHERITE_PICKAXE(new String[] { "1.16" }),
/*  647 */   NETHERITE_SCRAP(new String[] { "1.16" }),
/*  648 */   NETHERITE_SHOVEL(new String[] { "1.16" }),
/*  649 */   NETHERITE_SWORD(new String[] { "1.16" }),
/*  650 */   NETHERRACK,
/*  651 */   NETHER_BRICK(new String[] { "NETHER_BRICK_ITEM" }),
/*  652 */   NETHER_BRICKS(new String[] { "NETHER_BRICK" }),
/*  653 */   NETHER_BRICK_FENCE(new String[] { "NETHER_FENCE" }),
/*  654 */   NETHER_BRICK_SLAB(6, new String[] { "STEP" }),
/*  655 */   NETHER_BRICK_STAIRS,
/*  656 */   NETHER_BRICK_WALL,
/*  657 */   NETHER_GOLD_ORE(new String[] { "1.16" }),
/*  658 */   NETHER_PORTAL(new String[] { "PORTAL" }),
/*  659 */   NETHER_QUARTZ_ORE(new String[] { "QUARTZ_ORE" }),
/*  660 */   NETHER_SPROUTS(new String[] { "1.16" }),
/*  661 */   NETHER_STAR,
/*  662 */   NETHER_WART(new String[] { "NETHER_WARTS", "NETHER_STALK" }),
/*  663 */   NETHER_WART_BLOCK,
/*  664 */   NOTE_BLOCK,
/*  665 */   OAK_BOAT(new String[] { "BOAT" }),
/*  666 */   OAK_BUTTON(new String[] { "WOOD_BUTTON" }),
/*  667 */   OAK_DOOR(new String[] { "WOOD_DOOR", "WOODEN_DOOR" }),
/*  668 */   OAK_FENCE(new String[] { "FENCE" }),
/*  669 */   OAK_FENCE_GATE(new String[] { "FENCE_GATE" }),
/*  670 */   OAK_LEAVES(new String[] { "LEAVES" }),
/*  671 */   OAK_LOG(new String[] { "LOG" }),
/*  672 */   OAK_PLANKS(new String[] { "WOOD" }),
/*  673 */   OAK_PRESSURE_PLATE(new String[] { "WOOD_PLATE" }),
/*  674 */   OAK_SAPLING(new String[] { "SAPLING" }),
/*  675 */   OAK_SIGN(new String[] { "SIGN" }),
/*  676 */   OAK_SLAB(new String[] { "WOOD_STEP", "WOODEN_SLAB", "WOOD_DOUBLE_STEP" }),
/*  677 */   OAK_STAIRS(new String[] { "WOOD_STAIRS" }),
/*  678 */   OAK_TRAPDOOR(new String[] { "TRAP_DOOR" }),
/*  679 */   OAK_WALL_SIGN(new String[] { "SIGN_POST", "WALL_SIGN" }),
/*  680 */   OAK_WOOD(new String[] { "LOG" }),
/*  681 */   OBSERVER,
/*  682 */   OBSIDIAN,
/*  683 */   OCELOT_SPAWN_EGG(98, new String[] { "MONSTER_EGG" }),
/*  684 */   ORANGE_BANNER(14, new String[] { "BANNER", "STANDING_BANNER" }),
/*  685 */   ORANGE_BED(1, new String[] { "BED_BLOCK", "BED" }),
/*  686 */   ORANGE_CARPET(1, new String[] { "CARPET" }),
/*  687 */   ORANGE_CONCRETE(1, new String[] { "CONCRETE" }),
/*  688 */   ORANGE_CONCRETE_POWDER(1, new String[] { "CONCRETE_POWDER" }),
/*  689 */   ORANGE_DYE(14, new String[] { "INK_SACK" }),
/*  690 */   ORANGE_GLAZED_TERRACOTTA(1, new String[] { "1.12", "HARD_CLAY", "STAINED_CLAY", "ORANGE_TERRACOTTA" }),
/*  691 */   ORANGE_SHULKER_BOX,
/*  692 */   ORANGE_STAINED_GLASS(1, new String[] { "STAINED_GLASS" }),
/*  693 */   ORANGE_STAINED_GLASS_PANE(1, new String[] { "STAINED_GLASS_PANE" }),
/*  694 */   ORANGE_TERRACOTTA(1, new String[] { "HARD_CLAY", "STAINED_CLAY" }),
/*  695 */   ORANGE_TULIP(5, new String[] { "RED_ROSE" }),
/*  696 */   ORANGE_WALL_BANNER(14, new String[] { "WALL_BANNER" }),
/*  697 */   ORANGE_WOOL(1, new String[] { "WOOL" }),
/*  698 */   OXEYE_DAISY(8, new String[] { "RED_ROSE" }),
/*  699 */   PACKED_ICE,
/*  700 */   PAINTING,
/*  701 */   PANDA_SPAWN_EGG(new String[] { "1.14" }),
/*  702 */   PAPER,
/*  703 */   PARROT_SPAWN_EGG(105, new String[] { "MONSTER_EGG" }),
/*  704 */   PEONY(5, new String[] { "DOUBLE_PLANT" }),
/*  705 */   PETRIFIED_OAK_SLAB(new String[] { "WOOD_STEP" }),
/*  706 */   PHANTOM_MEMBRANE(new String[] { "1.13" }),
/*  707 */   PHANTOM_SPAWN_EGG(new String[] { "1.13", "MONSTER_EGG", "" }),
/*  708 */   PIG_SPAWN_EGG(90, new String[] { "MONSTER_EGG" }),
/*  709 */   PILLAGER_SPAWN_EGG(new String[] { "1.14" }),
/*  710 */   PINK_BANNER(9, new String[] { "BANNER", "STANDING_BANNER" }),
/*  711 */   PINK_BED(6, new String[] { "BED_BLOCK", "BED" }),
/*  712 */   PINK_CARPET(6, new String[] { "CARPET" }),
/*  713 */   PINK_CONCRETE(6, new String[] { "CONCRETE" }),
/*  714 */   PINK_CONCRETE_POWDER(6, new String[] { "CONCRETE_POWDER" }),
/*  715 */   PINK_DYE(9, new String[] { "INK_SACK" }),
/*  716 */   PINK_GLAZED_TERRACOTTA(6, new String[] { "1.12", "HARD_CLAY", "STAINED_CLAY", "PINK_TERRACOTTA" }),
/*  717 */   PINK_SHULKER_BOX,
/*  718 */   PINK_STAINED_GLASS(6, new String[] { "STAINED_GLASS" }),
/*  719 */   PINK_STAINED_GLASS_PANE(6, new String[] { "THIN_GLASS", "STAINED_GLASS_PANE" }),
/*  720 */   PINK_TERRACOTTA(6, new String[] { "HARD_CLAY", "STAINED_CLAY" }),
/*  721 */   PINK_TULIP(7, new String[] { "RED_ROSE" }),
/*  722 */   PINK_WALL_BANNER(9, new String[] { "WALL_BANNER" }),
/*  723 */   PINK_WOOL(6, new String[] { "WOOL" }),
/*  724 */   PISTON(new String[] { "PISTON_BASE" }),
/*  725 */   PISTON_HEAD(new String[] { "PISTON_EXTENSION" }),
/*  726 */   PLAYER_HEAD(3, new String[] { "SKULL", "SKULL_ITEM" }),
/*  727 */   PLAYER_WALL_HEAD(3, new String[] { "SKULL", "SKULL_ITEM" }),
/*  728 */   PODZOL(2, new String[] { "DIRT" }),
/*  729 */   POISONOUS_POTATO,
/*  730 */   POLAR_BEAR_SPAWN_EGG(102, new String[] { "MONSTER_EGG" }),
/*  731 */   POLISHED_ANDESITE(6, new String[] { "STONE" }),
/*  732 */   POLISHED_ANDESITE_SLAB,
/*  733 */   POLISHED_ANDESITE_STAIRS,
/*  734 */   POLISHED_BASALT(new String[] { "1.16" }),
/*  735 */   POLISHED_BLACKSTONE(new String[] { "1.16" }),
/*  736 */   POLISHED_BLACKSTONE_BRICKS(new String[] { "1.16" }),
/*  737 */   POLISHED_BLACKSTONE_BRICK_SLAB(new String[] { "1.16" }),
/*  738 */   POLISHED_BLACKSTONE_BRICK_STAIRS(new String[] { "1.16" }),
/*  739 */   POLISHED_BLACKSTONE_BRICK_WALL(new String[] { "1.16" }),
/*  740 */   POLISHED_BLACKSTONE_BUTTON(new String[] { "1.16" }),
/*  741 */   POLISHED_BLACKSTONE_PRESSURE_PLATE(new String[] { "1.16" }),
/*  742 */   POLISHED_BLACKSTONE_SLAB(new String[] { "1.16" }),
/*  743 */   POLISHED_BLACKSTONE_STAIRS(new String[] { "1.16" }),
/*  744 */   POLISHED_BLACKSTONE_WALL(new String[] { "1.16" }),
/*  745 */   POLISHED_DIORITE(4, new String[] { "STONE" }),
/*  746 */   POLISHED_DIORITE_SLAB,
/*  747 */   POLISHED_DIORITE_STAIRS,
/*  748 */   POLISHED_GRANITE(2, new String[] { "STONE" }),
/*  749 */   POLISHED_GRANITE_SLAB,
/*  750 */   POLISHED_GRANITE_STAIRS,
/*  751 */   POPPED_CHORUS_FRUIT(new String[] { "CHORUS_FRUIT_POPPED" }),
/*  752 */   POPPY(new String[] { "RED_ROSE" }),
/*  753 */   PORKCHOP(new String[] { "PORK" }),
/*  754 */   POTATO(new String[] { "POTATO_ITEM" }),
/*  755 */   POTATOES(new String[] { "POTATO" }),
/*  756 */   POTION,
/*  757 */   POTTED_ACACIA_SAPLING(4, new String[] { "SAPLING", "FLOWER_POT" }),
/*  758 */   POTTED_ALLIUM(2, new String[] { "RED_ROSE", "FLOWER_POT" }),
/*  759 */   POTTED_AZURE_BLUET(3, new String[] { "RED_ROSE", "FLOWER_POT" }),
/*  760 */   POTTED_BAMBOO,
/*  761 */   POTTED_BIRCH_SAPLING(2, new String[] { "SAPLING", "FLOWER_POT" }),
/*  762 */   POTTED_BLUE_ORCHID(1, new String[] { "RED_ROSE", "FLOWER_POT" }),
/*  763 */   POTTED_BROWN_MUSHROOM(new String[] { "FLOWER_POT" }),
/*  764 */   POTTED_CACTUS(new String[] { "FLOWER_POT" }),
/*  765 */   POTTED_CORNFLOWER,
/*  766 */   POTTED_DANDELION(new String[] { "YELLOW_FLOWER", "FLOWER_POT" }),
/*  767 */   POTTED_DARK_OAK_SAPLING(5, new String[] { "SAPLING", "FLOWER_POT" }),
/*  768 */   POTTED_DEAD_BUSH(new String[] { "FLOWER_POT" }),
/*  769 */   POTTED_FERN(2, new String[] { "LONG_GRASS", "FLOWER_POT" }),
/*  770 */   POTTED_JUNGLE_SAPLING(3, new String[] { "SAPLING", "FLOWER_POT" }),
/*  771 */   POTTED_LILY_OF_THE_VALLEY,
/*  772 */   POTTED_OAK_SAPLING(new String[] { "SAPLING", "FLOWER_POT" }),
/*  773 */   POTTED_ORANGE_TULIP(5, new String[] { "RED_ROSE", "FLOWER_POT" }),
/*  774 */   POTTED_OXEYE_DAISY(8, new String[] { "RED_ROSE", "FLOWER_POT" }),
/*  775 */   POTTED_PINK_TULIP(7, new String[] { "RED_ROSE", "FLOWER_POT" }),
/*  776 */   POTTED_POPPY(new String[] { "RED_ROSE", "FLOWER_POT" }),
/*  777 */   POTTED_RED_MUSHROOM(new String[] { "FLOWER_POT" }),
/*  778 */   POTTED_RED_TULIP(4, new String[] { "RED_ROSE", "FLOWER_POT" }),
/*  779 */   POTTED_SPRUCE_SAPLING(1, new String[] { "SAPLING", "FLOWER_POT" }),
/*  780 */   POTTED_WHITE_TULIP(6, new String[] { "RED_ROSE", "FLOWER_POT" }),
/*  781 */   POTTED_WITHER_ROSE,
/*  782 */   POWERED_RAIL,
/*  783 */   PRISMARINE,
/*  784 */   PRISMARINE_BRICKS(2, new String[] { "PRISMARINE" }),
/*  785 */   PRISMARINE_BRICK_SLAB(4, new String[] { "STEP" }),
/*  786 */   PRISMARINE_BRICK_STAIRS(new String[] { "1.13" }),
/*  787 */   PRISMARINE_CRYSTALS,
/*  788 */   PRISMARINE_SHARD,
/*  789 */   PRISMARINE_SLAB(new String[] { "1.13" }),
/*  790 */   PRISMARINE_STAIRS(new String[] { "1.13" }),
/*  791 */   PRISMARINE_WALL,
/*  792 */   PUFFERFISH(3, new String[] { "RAW_FISH" }),
/*  793 */   PUFFERFISH_BUCKET(new String[] { "1.13", "BUCKET", "WATER_BUCKET", "" }),
/*  794 */   PUFFERFISH_SPAWN_EGG(new String[] { "1.13", "MONSTER_EGG", "" }),
/*  795 */   PUMPKIN,
/*  796 */   PUMPKIN_PIE,
/*  797 */   PUMPKIN_SEEDS,
/*  798 */   PUMPKIN_STEM,
/*  799 */   PURPLE_BANNER(5, new String[] { "BANNER", "STANDING_BANNER" }),
/*  800 */   PURPLE_BED(10, new String[] { "BED_BLOCK", "BED" }),
/*  801 */   PURPLE_CARPET(10, new String[] { "CARPET" }),
/*  802 */   PURPLE_CONCRETE(10, new String[] { "CONCRETE" }),
/*  803 */   PURPLE_CONCRETE_POWDER(10, new String[] { "CONCRETE_POWDER" }),
/*  804 */   PURPLE_DYE(5, new String[] { "INK_SACK" }),
/*  805 */   PURPLE_GLAZED_TERRACOTTA(10, new String[] { "1.12", "HARD_CLAY", "STAINED_CLAY", "PURPLE_TERRACOTTA" }),
/*  806 */   PURPLE_SHULKER_BOX,
/*  807 */   PURPLE_STAINED_GLASS(10, new String[] { "STAINED_GLASS" }),
/*  808 */   PURPLE_STAINED_GLASS_PANE(10, new String[] { "THIN_GLASS", "STAINED_GLASS_PANE" }),
/*  809 */   PURPLE_TERRACOTTA(10, new String[] { "HARD_CLAY", "STAINED_CLAY" }),
/*  810 */   PURPLE_WALL_BANNER(5, new String[] { "WALL_BANNER" }),
/*  811 */   PURPLE_WOOL(10, new String[] { "WOOL" }),
/*  812 */   PURPUR_BLOCK,
/*  813 */   PURPUR_PILLAR,
/*  814 */   PURPUR_SLAB(new String[] { "PURPUR_DOUBLE_SLAB" }),
/*  815 */   PURPUR_STAIRS,
/*  816 */   QUARTZ,
/*  817 */   QUARTZ_BLOCK,
/*  818 */   QUARTZ_PILLAR(2, new String[] { "QUARTZ_BLOCK" }),
/*  819 */   QUARTZ_SLAB(7, new String[] { "STEP" }),
/*  820 */   QUARTZ_STAIRS,
/*  821 */   RABBIT,
/*  822 */   RABBIT_FOOT,
/*  823 */   RABBIT_HIDE,
/*  824 */   RABBIT_SPAWN_EGG(101, new String[] { "MONSTER_EGG" }),
/*  825 */   RABBIT_STEW,
/*  826 */   RAIL(new String[] { "RAILS" }),
/*  827 */   RAVAGER_SPAWN_EGG(new String[] { "1.14" }),
/*  828 */   REDSTONE,
/*  829 */   REDSTONE_BLOCK,
/*  830 */   REDSTONE_LAMP(new String[] { "REDSTONE_LAMP_ON", "REDSTONE_LAMP_OFF" }),
/*  831 */   REDSTONE_ORE(new String[] { "GLOWING_REDSTONE_ORE" }),
/*  832 */   REDSTONE_TORCH(new String[] { "REDSTONE_TORCH_OFF", "REDSTONE_TORCH_ON" }),
/*  833 */   REDSTONE_WALL_TORCH,
/*  834 */   REDSTONE_WIRE,
/*  835 */   RED_BANNER(1, new String[] { "BANNER", "STANDING_BANNER" }),
/*  836 */   RED_BED(0, new String[] { "BED_BLOCK", "BED" }),
/*  837 */   RED_CARPET(14, new String[] { "CARPET" }),
/*  838 */   RED_CONCRETE(14, new String[] { "CONCRETE" }),
/*  839 */   RED_CONCRETE_POWDER(14, new String[] { "CONCRETE_POWDER" }),
/*  840 */   RED_DYE(0, new String[] { "INK_SACK", "ROSE_RED" }),
/*  841 */   RED_GLAZED_TERRACOTTA(14, new String[] { "1.12", "HARD_CLAY", "STAINED_CLAY", "RED_TERRACOTTA" }),
/*  842 */   RED_MUSHROOM,
/*  843 */   RED_MUSHROOM_BLOCK(new String[] { "RED_MUSHROOM", "HUGE_MUSHROOM_2" }),
/*  844 */   RED_NETHER_BRICKS(new String[] { "RED_NETHER_BRICK" }),
/*  845 */   RED_NETHER_BRICK_SLAB(4, new String[] { "STEP" }),
/*  846 */   RED_NETHER_BRICK_STAIRS,
/*  847 */   RED_NETHER_BRICK_WALL,
/*  848 */   RED_SAND(1, new String[] { "SAND" }),
/*  849 */   RED_SANDSTONE,
/*  850 */   RED_SANDSTONE_SLAB(new String[] { "STONE_SLAB2", "DOUBLE_STONE_SLAB2" }),
/*  851 */   RED_SANDSTONE_STAIRS,
/*  852 */   RED_SANDSTONE_WALL,
/*  853 */   RED_SHULKER_BOX,
/*  854 */   RED_STAINED_GLASS(14, new String[] { "STAINED_GLASS" }),
/*  855 */   RED_STAINED_GLASS_PANE(14, new String[] { "THIN_GLASS", "STAINED_GLASS_PANE" }),
/*  856 */   RED_TERRACOTTA(14, new String[] { "HARD_CLAY", "STAINED_CLAY" }),
/*  857 */   RED_TULIP(4, new String[] { "RED_ROSE" }),
/*  858 */   RED_WALL_BANNER(1, new String[] { "WALL_BANNER" }),
/*  859 */   RED_WOOL(14, new String[] { "WOOL" }),
/*  860 */   REPEATER(new String[] { "DIODE", "DIODE_BLOCK_ON", "DIODE_BLOCK_OFF" }),
/*  861 */   REPEATING_COMMAND_BLOCK(new String[] { "COMMAND", "COMMAND_REPEATING" }),
/*  862 */   RESPAWN_ANCHOR(new String[] { "1.16" }),
/*  863 */   ROSE_BUSH(4, new String[] { "DOUBLE_PLANT" }),
/*  864 */   ROTTEN_FLESH,
/*  865 */   SADDLE,
/*  866 */   SALMON(1, new String[] { "RAW_FISH" }),
/*  867 */   SALMON_BUCKET(new String[] { "1.13", "BUCKET", "WATER_BUCKET", "" }),
/*  868 */   SALMON_SPAWN_EGG(new String[] { "1.13", "MONSTER_EGG", "" }),
/*  869 */   SAND,
/*  870 */   SANDSTONE,
/*  871 */   SANDSTONE_SLAB(1, new String[] { "STEP", "STONE_SLAB", "DOUBLE_STEP" }),
/*  872 */   SANDSTONE_STAIRS,
/*  873 */   SANDSTONE_WALL,
/*  874 */   SCAFFOLDING(new String[] { "1.14", "SLIME_BLOCK", "" }),
/*  875 */   SCUTE(new String[] { "1.13" }),
/*  876 */   SEAGRASS(new String[] { "1.13", "GRASS", "" }),
/*  877 */   SEA_LANTERN,
/*  878 */   SEA_PICKLE(new String[] { "1.13" }),
/*  879 */   SHEARS,
/*  880 */   SHEEP_SPAWN_EGG(91, new String[] { "MONSTER_EGG" }),
/*  881 */   SHIELD,
/*  882 */   SHROOMLIGHT(new String[] { "1.16" }),
/*  883 */   SHULKER_BOX(new String[] { "PURPLE_SHULKER_BOX" }),
/*  884 */   SHULKER_SHELL,
/*  885 */   SHULKER_SPAWN_EGG(69, new String[] { "MONSTER_EGG" }),
/*  886 */   SILVERFISH_SPAWN_EGG(60, new String[] { "MONSTER_EGG" }),
/*  887 */   SKELETON_HORSE_SPAWN_EGG(28, new String[] { "MONSTER_EGG" }),
/*  888 */   SKELETON_SKULL(new String[] { "SKULL", "SKULL_ITEM" }),
/*  889 */   SKELETON_SPAWN_EGG(51, new String[] { "MONSTER_EGG" }),
/*  890 */   SKELETON_WALL_SKULL(new String[] { "SKULL", "SKULL_ITEM" }),
/*  891 */   SKULL_BANNER_PATTERN,
/*  892 */   SLIME_BALL,
/*  893 */   SLIME_BLOCK,
/*  894 */   SLIME_SPAWN_EGG(55, new String[] { "MONSTER_EGG" }),
/*  895 */   SMITHING_TABLE,
/*  896 */   SMOKER(new String[] { "1.14", "FURNACE", "" }),
/*  897 */   SMOOTH_QUARTZ(new String[] { "1.13", "QUARTZ", "" }),
/*  898 */   SMOOTH_QUARTZ_SLAB(7, new String[] { "STEP" }),
/*  899 */   SMOOTH_QUARTZ_STAIRS,
/*  900 */   SMOOTH_RED_SANDSTONE(2, new String[] { "RED_SANDSTONE" }),
/*  901 */   SMOOTH_RED_SANDSTONE_SLAB(new String[] { "STONE_SLAB2" }),
/*  902 */   SMOOTH_RED_SANDSTONE_STAIRS,
/*  903 */   SMOOTH_SANDSTONE(2, new String[] { "SANDSTONE" }),
/*  904 */   SMOOTH_SANDSTONE_SLAB(new String[] { "STEP" }),
/*  905 */   SMOOTH_SANDSTONE_STAIRS,
/*  906 */   SMOOTH_STONE(new String[] { "STEP" }),
/*  907 */   SMOOTH_STONE_SLAB(new String[] { "STEP" }),
/*  908 */   SNOW,
/*  909 */   SNOWBALL(new String[] { "SNOW_BALL" }),
/*  910 */   SNOW_BLOCK,
/*  911 */   SOUL_CAMPFIRE(new String[] { "1.16" }),
/*  912 */   SOUL_FIRE(new String[] { "1.16" }),
/*  913 */   SOUL_LANTERN(new String[] { "1.16" }),
/*  914 */   SOUL_SAND,
/*  915 */   SOUL_SOIL(new String[] { "1.16" }),
/*  916 */   SOUL_TORCH(new String[] { "1.16" }),
/*  917 */   SOUL_WALL_TORCH(new String[] { "1.16" }),
/*  918 */   SPAWNER(new String[] { "MOB_SPAWNER" }),
/*  919 */   SPECTRAL_ARROW(new String[] { "1.9", "ARROW", "" }),
/*  920 */   SPIDER_EYE,
/*  921 */   SPIDER_SPAWN_EGG(52, new String[] { "MONSTER_EGG" }),
/*  922 */   SPLASH_POTION,
/*  923 */   SPONGE,
/*  924 */   SPRUCE_BOAT(new String[] { "BOAT_SPRUCE" }),
/*  925 */   SPRUCE_BUTTON(new String[] { "WOOD_BUTTON" }),
/*  926 */   SPRUCE_DOOR(new String[] { "SPRUCE_DOOR_ITEM" }),
/*  927 */   SPRUCE_FENCE,
/*  928 */   SPRUCE_FENCE_GATE,
/*  929 */   SPRUCE_LEAVES(1, new String[] { "LEAVES" }),
/*  930 */   SPRUCE_LOG(1, new String[] { "LOG" }),
/*  931 */   SPRUCE_PLANKS(1, new String[] { "WOOD" }),
/*  932 */   SPRUCE_PRESSURE_PLATE(new String[] { "WOOD_PLATE" }),
/*  933 */   SPRUCE_SAPLING(1, new String[] { "SAPLING" }),
/*  934 */   SPRUCE_SIGN(new String[] { "SIGN" }),
/*  935 */   SPRUCE_SLAB(1, new String[] { "WOOD_STEP", "WOODEN_SLAB", "WOOD_DOUBLE_STEP" }),
/*  936 */   SPRUCE_STAIRS(new String[] { "SPRUCE_WOOD_STAIRS" }),
/*  937 */   SPRUCE_TRAPDOOR(new String[] { "TRAP_DOOR" }),
/*  938 */   SPRUCE_WALL_SIGN(new String[] { "SIGN_POST", "WALL_SIGN" }),
/*  939 */   SPRUCE_WOOD(1, new String[] { "LOG" }),
/*  940 */   SQUID_SPAWN_EGG(94, new String[] { "MONSTER_EGG" }),
/*  941 */   STICK,
/*  942 */   STICKY_PISTON(new String[] { "PISTON_BASE", "PISTON_STICKY_BASE" }),
/*  943 */   STONE,
/*  944 */   STONECUTTER(new String[] { "1.14" }),
/*  945 */   STONE_AXE,
/*  946 */   STONE_BRICKS(new String[] { "SMOOTH_BRICK" }),
/*  947 */   STONE_BRICK_SLAB(4, new String[] { "STEP", "STONE_SLAB", "DOUBLE_STEP" }),
/*  948 */   STONE_BRICK_STAIRS(new String[] { "SMOOTH_STAIRS" }),
/*  949 */   STONE_BRICK_WALL,
/*  950 */   STONE_BUTTON,
/*  951 */   STONE_HOE,
/*  952 */   STONE_PICKAXE,
/*  953 */   STONE_PRESSURE_PLATE(new String[] { "STONE_PLATE" }),
/*  954 */   STONE_SHOVEL(new String[] { "STONE_SPADE" }),
/*  955 */   STONE_SLAB(new String[] { "STEP", "DOUBLE_STEP" }),
/*  956 */   STONE_STAIRS,
/*  957 */   STONE_SWORD,
/*  958 */   STRAY_SPAWN_EGG(6, new String[] { "MONSTER_EGG" }),
/*  959 */   STRING,
/*  960 */   STRIPPED_ACACIA_LOG(new String[] { "LOG_2" }),
/*  961 */   STRIPPED_ACACIA_WOOD(new String[] { "LOG_2" }),
/*  962 */   STRIPPED_BIRCH_LOG(2, new String[] { "LOG" }),
/*  963 */   STRIPPED_BIRCH_WOOD(2, new String[] { "LOG" }),
/*  964 */   STRIPPED_CRIMSON_HYPHAE(new String[] { "1.16" }),
/*  965 */   STRIPPED_CRIMSON_STEM(new String[] { "1.16" }),
/*  966 */   STRIPPED_DARK_OAK_LOG(new String[] { "LOG" }),
/*  967 */   STRIPPED_DARK_OAK_WOOD(new String[] { "LOG" }),
/*  968 */   STRIPPED_JUNGLE_LOG(3, new String[] { "LOG" }),
/*  969 */   STRIPPED_JUNGLE_WOOD(3, new String[] { "LOG" }),
/*  970 */   STRIPPED_OAK_LOG(new String[] { "LOG" }),
/*  971 */   STRIPPED_OAK_WOOD(new String[] { "LOG" }),
/*  972 */   STRIPPED_SPRUCE_LOG(1, new String[] { "LOG" }),
/*  973 */   STRIPPED_SPRUCE_WOOD(1, new String[] { "LOG" }),
/*  974 */   STRIPPED_WARPED_HYPHAE(new String[] { "1.16" }),
/*  975 */   STRIPPED_WARPED_STEM(new String[] { "1.16" }),
/*  976 */   STRUCTURE_BLOCK,
/*  977 */   STRUCTURE_VOID(new String[] { "1.10", "", "BARRIER" }),
/*  978 */   SUGAR,
/*  979 */   SUGAR_CANE(new String[] { "SUGAR_CANE_BLOCK" }),
/*  980 */   SUNFLOWER(new String[] { "DOUBLE_PLANT" }),
/*  981 */   SUSPICIOUS_STEW(new String[] { "1.14", "MUSHROOM_STEW", "" }),
/*  982 */   SWEET_BERRIES(new String[] { "1.14" }),
/*  983 */   SWEET_BERRY_BUSH(new String[] { "1.14", "GRASS", "" }),
/*  984 */   TALL_GRASS(2, new String[] { "DOUBLE_PLANT" }),
/*  985 */   TALL_SEAGRASS(2, new String[] { "1.13", "TALL_GRASS", "" }),
/*  986 */   TARGET(new String[] { "1.16" }),
/*  987 */   TERRACOTTA(new String[] { "HARD_CLAY" }),
/*  988 */   TIPPED_ARROW(new String[] { "1.9", "ARROW", "" }),
/*  989 */   TNT,
/*  990 */   TNT_MINECART(new String[] { "EXPLOSIVE_MINECART" }),
/*  991 */   TORCH,
/*  992 */   TOTEM_OF_UNDYING(new String[] { "TOTEM" }),
/*  993 */   TRADER_LLAMA_SPAWN_EGG(103, new String[] { "1.14", "MONSTER_EGG", "" }),
/*  994 */   TRAPPED_CHEST,
/*  995 */   TRIDENT(new String[] { "1.13" }),
/*  996 */   TRIPWIRE,
/*  997 */   TRIPWIRE_HOOK,
/*  998 */   TROPICAL_FISH(2, new String[] { "RAW_FISH" }),
/*  999 */   TROPICAL_FISH_BUCKET(new String[] { "1.13", "BUCKET", "WATER_BUCKET" }),
/* 1000 */   TROPICAL_FISH_SPAWN_EGG(new String[] { "1.13", "MONSTER_EGG" }),
/* 1001 */   TUBE_CORAL(new String[] { "1.13" }),
/* 1002 */   TUBE_CORAL_BLOCK(new String[] { "1.13" }),
/* 1003 */   TUBE_CORAL_FAN(new String[] { "1.13" }),
/* 1004 */   TUBE_CORAL_WALL_FAN,
/* 1005 */   TURTLE_EGG(new String[] { "1.13", "EGG", "" }),
/* 1006 */   TURTLE_HELMET(new String[] { "1.13", "IRON_HELMET", "" }),
/* 1007 */   TURTLE_SPAWN_EGG(new String[] { "1.13", "CHICKEN_SPAWN_EGG", "" }),
/* 1008 */   TWISTING_VINES(new String[] { "1.16" }),
/* 1009 */   TWISTING_VINES_PLANT(new String[] { "1.16" }),
/* 1010 */   VEX_SPAWN_EGG(35, new String[] { "MONSTER_EGG" }),
/* 1011 */   VILLAGER_SPAWN_EGG(120, new String[] { "MONSTER_EGG" }),
/* 1012 */   VINDICATOR_SPAWN_EGG(36, new String[] { "MONSTER_EGG" }),
/* 1013 */   VINE,
/* 1014 */   VOID_AIR(new String[] { "AIR" }),
/* 1015 */   WALL_TORCH(new String[] { "TORCH" }),
/* 1016 */   WANDERING_TRADER_SPAWN_EGG(new String[] { "1.14", "VILLAGER_SPAWN_EGG", "" }),
/* 1017 */   WARPED_BUTTON(new String[] { "1.16" }),
/* 1018 */   WARPED_DOOR(new String[] { "1.16" }),
/* 1019 */   WARPED_FENCE(new String[] { "1.16" }),
/* 1020 */   WARPED_FENCE_GATE(new String[] { "1.16" }),
/* 1021 */   WARPED_FUNGUS(new String[] { "1.16" }),
/* 1022 */   WARPED_FUNGUS_ON_A_STICK(new String[] { "1.16" }),
/* 1023 */   WARPED_HYPHAE(new String[] { "1.16" }),
/* 1024 */   WARPED_NYLIUM(new String[] { "1.16" }),
/* 1025 */   WARPED_PLANKS(new String[] { "1.16" }),
/* 1026 */   WARPED_PRESSURE_PLATE(new String[] { "1.16" }),
/* 1027 */   WARPED_ROOTS(new String[] { "1.16" }),
/* 1028 */   WARPED_SIGN(new String[] { "1.16" }),
/* 1029 */   WARPED_SLAB(new String[] { "1.16" }),
/* 1030 */   WARPED_STAIRS(new String[] { "1.16" }),
/* 1031 */   WARPED_STEM(new String[] { "1.16" }),
/* 1032 */   WARPED_TRAPDOOR(new String[] { "1.16" }),
/* 1033 */   WARPED_WALL_SIGN(new String[] { "1.16" }),
/* 1034 */   WARPED_WART_BLOCK(new String[] { "1.16" }),
/* 1035 */   WATER(new String[] { "STATIONARY_WATER" }),
/* 1036 */   WATER_BUCKET,
/* 1037 */   WEEPING_VINES(new String[] { "1.16" }),
/* 1038 */   WEEPING_VINES_PLANT(new String[] { "1.16" }),
/* 1039 */   WET_SPONGE(1, new String[] { "SPONGE" }),
/* 1040 */   WHEAT(new String[] { "CROPS" }),
/* 1041 */   WHEAT_SEEDS(new String[] { "SEEDS" }),
/* 1042 */   WHITE_BANNER(15, new String[] { "BANNER", "STANDING_BANNER" }),
/* 1043 */   WHITE_BED(new String[] { "BED_BLOCK", "BED" }),
/* 1044 */   WHITE_CARPET(new String[] { "CARPET" }),
/* 1045 */   WHITE_CONCRETE(new String[] { "CONCRETE" }),
/* 1046 */   WHITE_CONCRETE_POWDER(new String[] { "CONCRETE_POWDER" }),
/* 1047 */   WHITE_DYE(15, new String[] { "1.14", "INK_SACK", "BONE_MEAL" }),
/* 1048 */   WHITE_GLAZED_TERRACOTTA(new String[] { "1.12", "HARD_CLAY", "STAINED_CLAY" }),
/* 1049 */   WHITE_SHULKER_BOX,
/* 1050 */   WHITE_STAINED_GLASS(new String[] { "STAINED_GLASS" }),
/* 1051 */   WHITE_STAINED_GLASS_PANE(new String[] { "THIN_GLASS", "STAINED_GLASS_PANE" }),
/* 1052 */   WHITE_TERRACOTTA(new String[] { "HARD_CLAY", "STAINED_CLAY", "TERRACOTTA" }),
/* 1053 */   WHITE_TULIP(6, new String[] { "RED_ROSE" }),
/* 1054 */   WHITE_WALL_BANNER(15, new String[] { "WALL_BANNER" }),
/* 1055 */   WHITE_WOOL(new String[] { "WOOL" }),
/* 1056 */   WITCH_SPAWN_EGG(66, new String[] { "MONSTER_EGG" }),
/* 1057 */   WITHER_ROSE(new String[] { "1.14", "BLACK_DYE", "" }),
/* 1058 */   WITHER_SKELETON_SKULL(1, new String[] { "SKULL", "SKULL_ITEM" }),
/* 1059 */   WITHER_SKELETON_SPAWN_EGG(5, new String[] { "MONSTER_EGG" }),
/* 1060 */   WITHER_SKELETON_WALL_SKULL(1, new String[] { "SKULL", "SKULL_ITEM" }),
/* 1061 */   WOLF_SPAWN_EGG(95, new String[] { "MONSTER_EGG" }),
/* 1062 */   WOODEN_AXE(new String[] { "WOOD_AXE" }),
/* 1063 */   WOODEN_HOE(new String[] { "WOOD_HOE" }),
/* 1064 */   WOODEN_PICKAXE(new String[] { "WOOD_PICKAXE" }),
/* 1065 */   WOODEN_SHOVEL(new String[] { "WOOD_SPADE" }),
/* 1066 */   WOODEN_SWORD(new String[] { "WOOD_SWORD" }),
/* 1067 */   WRITABLE_BOOK(new String[] { "BOOK_AND_QUILL" }),
/* 1068 */   WRITTEN_BOOK,
/* 1069 */   YELLOW_BANNER(11, new String[] { "BANNER", "STANDING_BANNER" }),
/* 1070 */   YELLOW_BED(4, new String[] { "BED_BLOCK", "BED" }),
/* 1071 */   YELLOW_CARPET(4, new String[] { "CARPET" }),
/* 1072 */   YELLOW_CONCRETE(4, new String[] { "CONCRETE" }),
/* 1073 */   YELLOW_CONCRETE_POWDER(4, new String[] { "CONCRETE_POWDER" }),
/* 1074 */   YELLOW_DYE(11, new String[] { "INK_SACK", "DANDELION_YELLOW" }),
/* 1075 */   YELLOW_GLAZED_TERRACOTTA(4, new String[] { "1.12", "HARD_CLAY", "STAINED_CLAY", "YELLOW_TERRACOTTA" }),
/* 1076 */   YELLOW_SHULKER_BOX,
/* 1077 */   YELLOW_STAINED_GLASS(4, new String[] { "STAINED_GLASS" }),
/* 1078 */   YELLOW_STAINED_GLASS_PANE(4, new String[] { "THIN_GLASS", "STAINED_GLASS_PANE" }),
/* 1079 */   YELLOW_TERRACOTTA(4, new String[] { "HARD_CLAY", "STAINED_CLAY" }),
/* 1080 */   YELLOW_WALL_BANNER(11, new String[] { "WALL_BANNER" }),
/* 1081 */   YELLOW_WOOL(4, new String[] { "WOOL" }),
/* 1082 */   ZOMBIE_HEAD(2, new String[] { "SKULL", "SKULL_ITEM" }),
/* 1083 */   ZOMBIE_HORSE_SPAWN_EGG(29, new String[] { "MONSTER_EGG" }),
/* 1084 */   ZOMBIE_PIGMAN_SPAWN_EGG(57, new String[] { "MONSTER_EGG" }),
/* 1085 */   ZOMBIE_SPAWN_EGG(54, new String[] { "MONSTER_EGG" }),
/* 1086 */   ZOMBIE_VILLAGER_SPAWN_EGG(27, new String[] { "MONSTER_EGG" }),
/* 1087 */   ZOMBIE_WALL_HEAD(2, new String[] { "SKULL", "SKULL_ITEM" }),
/* 1088 */   ZOMBIFIED_PIGLIN_SPAWN_EGG(54, new String[] { "MONSTER_EGG" }); public static final EnumSet<XMaterial> VALUES; private static final ImmutableSet<String> DAMAGEABLE; private static final ImmutableMap<XMaterial, XMaterial> duplicated; private static final Cache<String, XMaterial> NAME_CACHE; private static final Cache<XMaterial, Optional<Material>> PARSED_CACHE;
/*      */   static {
/* 1090 */     VALUES = EnumSet.allOf(XMaterial.class);
/*      */     
/* 1092 */     DAMAGEABLE = ImmutableSet.<String>builder()
/*      */         .add("HELMET", "CHESTPLATE", "LEGGINGS", "BOOTS", "SWORD", "AXE")
/*      */         .add("PICKAXE", "SHOVEL", "HOE", "ELYTRA", "TRIDENT", "HORSE_ARMOR", "BARDING")
/*      */         .add("SHEARS", "FLINT_AND_STEEL", "BOW", "FISHING_ROD", "CARROT_ON_A_STICK", "CARROT_STICK", "SPADE", "SHIELD")
/*      */         .build();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1101 */     duplicated = Maps.immutableEnumMap((Map)ImmutableMap.builder()
/* 1102 */         .put(MELON, MELON_SLICE)
/* 1103 */         .put(CARROT, CARROTS)
/* 1104 */         .put(POTATO, POTATOES)
/* 1105 */         .put(BEETROOT, BEETROOTS)
/* 1106 */         .put(BROWN_MUSHROOM, BROWN_MUSHROOM_BLOCK)
/* 1107 */         .put(BRICK, BRICKS)
/* 1108 */         .put(NETHER_BRICK, NETHER_BRICKS)
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1115 */         .put(DARK_OAK_DOOR, DARK_OAK_DOOR)
/* 1116 */         .put(ACACIA_DOOR, ACACIA_DOOR)
/* 1117 */         .put(BIRCH_DOOR, BIRCH_DOOR)
/* 1118 */         .put(JUNGLE_DOOR, JUNGLE_DOOR)
/* 1119 */         .put(SPRUCE_DOOR, SPRUCE_DOOR)
/*      */         
/* 1121 */         .build());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1127 */     NAME_CACHE = CacheBuilder.newBuilder().softValues().expireAfterAccess(15L, TimeUnit.MINUTES).build();
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
/* 1138 */     PARSED_CACHE = CacheBuilder.newBuilder().softValues().expireAfterAccess(10L, TimeUnit.MINUTES).concurrencyLevel(Runtime.getRuntime().availableProcessors()).build();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1146 */     FORMAT_PATTERN = Pattern.compile("\\W+");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1152 */     VERSION = getMajorVersion(Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3]);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1159 */     ISFLAT = supports(13);
/*      */   }
/*      */ 
/*      */   
/*      */   private static final Pattern FORMAT_PATTERN;
/*      */   
/*      */   private static final int VERSION;
/*      */   
/*      */   private static final boolean ISFLAT;
/*      */   
/*      */   private final byte data;
/*      */
/*      */   private final String[] legacy;
/*      */
/*      */   XMaterial() {
/*      */     this.data = 0;
/*      */     this.legacy = new String[0];
/*      */   }
/*      */
/*      */   XMaterial(String... legacy) {
/*      */     this.data = 0;
/*      */     this.legacy = legacy;
/*      */   }
/*      */
/*      */   XMaterial(int data, String... legacy) {
/* 1174 */     this.data = (byte)data;
/* 1175 */     this.legacy = legacy;
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
/*      */   public static boolean isNewVersion() {
/* 1200 */     return ISFLAT;
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
/*      */   public static boolean isOneEight() {
/* 1218 */     return !supports(9);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double getVersion() {
/* 1229 */     return VERSION;
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
/*      */   private static XMaterial requestOldXMaterial(@Nonnull String name, byte data) {
/* 1241 */     String holder = name + data;
/* 1242 */     XMaterial cache = (XMaterial)NAME_CACHE.getIfPresent(holder);
/* 1243 */     if (cache != null) return cache;
/*      */     
/* 1245 */     for (XMaterial material : VALUES) {
/*      */       
/* 1247 */       if ((data == -1 || data == material.data) && material.anyMatchLegacy(name)) {
/* 1248 */         NAME_CACHE.put(holder, material);
/* 1249 */         return material;
/*      */       } 
/*      */     } 
/*      */     
/* 1253 */     return null;
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
/*      */   public static boolean contains(@Nonnull String name) {
/* 1269 */     Validate.notEmpty(name, "Cannot check for null or empty material name");
/* 1270 */     name = format(name);
/*      */     
/* 1272 */     for (XMaterial materials : VALUES) {
/* 1273 */       if (materials.name().equals(name)) return true; 
/* 1274 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public static Optional<XMaterial> matchXMaterial(@Nonnull String name) {
/* 1285 */     Validate.notEmpty(name, "Cannot match a material with null or empty material name");
/* 1286 */     Optional<XMaterial> oldMatch = matchXMaterialWithData(name);
/* 1287 */     if (oldMatch.isPresent()) return oldMatch; 
/* 1288 */     return matchDefinedXMaterial(format(name), (byte)-1);
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
/*      */   private static Optional<XMaterial> matchXMaterialWithData(String name) {
/*      */     char[] arrayOfChar;
/*      */     int i;
/*      */     byte b;
/* 1308 */     for (arrayOfChar = new char[] { ',', ':' }, i = arrayOfChar.length, b = 0; b < i; ) { char separator = arrayOfChar[b];
/* 1309 */       int index = name.indexOf(separator);
/* 1310 */       if (index == -1) {
/*      */         b++; continue;
/* 1312 */       }  String mat = format(name.substring(0, index));
/* 1313 */       byte data = Byte.parseByte(StringUtils.deleteWhitespace(name.substring(index + 1)));
/* 1314 */       return matchDefinedXMaterial(mat, data); }
/*      */ 
/*      */     
/* 1317 */     return Optional.empty();
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
/*      */   public static XMaterial matchXMaterial(@Nonnull Material material) {
/* 1330 */     Objects.requireNonNull(material, "Cannot match null material");
/* 1331 */     return matchDefinedXMaterial(material.name(), (byte)-1)
/* 1332 */       .orElseThrow(() -> new IllegalArgumentException("Unsupported Material With No Bytes: " + material.name()));
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
/*      */   @Nonnull
/*      */   public static XMaterial matchXMaterial(@Nonnull ItemStack item) {
/* 1347 */     Objects.requireNonNull(item, "Cannot match null ItemStack");
/* 1348 */     String material = item.getType().name();
/* 1349 */     byte data = (byte)((ISFLAT || isDamageable(material)) ? 0 : item.getDurability());
/*      */     
/* 1351 */     return matchDefinedXMaterial(material, data)
/* 1352 */       .orElseThrow(() -> new IllegalArgumentException("Unsupported Material: " + material + " (" + data + ')'));
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
/*      */   @Nonnull
/*      */   public static Optional<XMaterial> matchDefinedXMaterial(@Nonnull String name, byte data) {
/* 1369 */     boolean duplicated = isDuplicated(name);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1374 */     if (data <= 0 && !duplicated) {
/*      */ 
/*      */       
/* 1377 */       Optional<XMaterial> optional = (Optional<XMaterial>)Enums.getIfPresent(XMaterial.class, name).transform(Optional::of).or(Optional.empty());
/* 1378 */       if (optional.isPresent()) return optional;
/*      */     
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1385 */     XMaterial xMat = requestOldXMaterial(name, data);
/* 1386 */     if (xMat == null) {
/*      */       
/* 1388 */       if (data > 0 && name.endsWith("MAP")) return Optional.of(FILLED_MAP); 
/* 1389 */       return Optional.empty();
/*      */     } 
/*      */     
/* 1392 */     if (!ISFLAT && duplicated && xMat.name().charAt(xMat.name().length() - 1) == 'S')
/*      */     {
/*      */ 
/*      */       
/* 1396 */       return (Optional<XMaterial>)Enums.getIfPresent(XMaterial.class, name).transform(Optional::of).or(Optional.empty());
/*      */     }
/* 1398 */     return Optional.ofNullable(xMat);
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
/*      */   private static boolean isDuplicated(@Nonnull String name) {
/* 1415 */     for (UnmodifiableIterator<Map.Entry<XMaterial, XMaterial>> unmodifiableIterator = XMaterial.duplicated.entrySet().iterator(); unmodifiableIterator.hasNext(); ) { Map.Entry<XMaterial, XMaterial> duplicated = unmodifiableIterator.next();
/* 1416 */       XMaterial material = duplicated.getKey();
/* 1417 */       if (material.name().equals(name) || material.anyMatchLegacy(name)) return true;  }
/*      */     
/* 1419 */     return false;
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
/*      */   @Nonnull
/*      */   public static Optional<XMaterial> matchXMaterial(int id, byte data) {
/* 1434 */     if (id < 0 || data < 0) return Optional.empty();
/*      */ 
/*      */     
/* 1437 */     for (XMaterial materials : VALUES) {
/* 1438 */       if (materials.data == data && materials.getId() == id) return Optional.of(materials); 
/* 1439 */     }  return Optional.empty();
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public static Optional<XMaterial> matchXMaterial(String material, short data) {
/* 1444 */     if (data < 0) return Optional.empty();
/*      */ 
/*      */     
/* 1447 */     for (XMaterial materials : VALUES) {
/* 1448 */       if (materials.data == data && materials.name().equals(material)) return Optional.of(materials); 
/* 1449 */     }  return Optional.empty();
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
/* 1462 */     return FORMAT_PATTERN.matcher(name
/* 1463 */         .trim().replace('-', '_').replace(' ', '_')).replaceAll("").toUpperCase(Locale.ENGLISH);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean supports(int version) {
/* 1474 */     return (VERSION >= version);
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
/*      */   public static String toWord(@Nonnull Material material) {
/* 1486 */     Objects.requireNonNull(material, "Cannot translate a null material to a word");
/* 1487 */     return toWord(material.name());
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
/*      */   @Nonnull
/*      */   private static String toWord(@Nonnull String name) {
/* 1507 */     return WordUtils.capitalize(name.replace('_', ' ').toLowerCase(Locale.ENGLISH));
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
/*      */   public static int getMajorVersion(@Nonnull String version) {
/* 1519 */     switch (version) {
/*      */       case "v1_8_R3":
/* 1521 */         return 8;
/*      */       case "v1_9_R1":
/*      */       case "v1_9_R2":
/* 1524 */         return 9;
/*      */       case "v1_10_R1":
/* 1526 */         return 10;
/*      */       case "v1_11_R1":
/* 1528 */         return 11;
/*      */       case "v1_12_R1":
/* 1530 */         return 12;
/*      */       case "v1_13_R1":
/*      */       case "v1_13_R2":
/* 1533 */         return 13;
/*      */       case "v1_14_R1":
/* 1535 */         return 14;
/*      */       case "v1_15_R1":
/* 1537 */         return 15;
/*      */       case "v1_16_R1":
/* 1539 */         return 16;
/*      */     } 
/* 1541 */     return 8;
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
/*      */   public static boolean isDamageable(@Nonnull String name) {
/* 1554 */     Objects.requireNonNull(name, "Material name cannot be null");
/* 1555 */     for (UnmodifiableIterator<String> unmodifiableIterator = DAMAGEABLE.iterator(); unmodifiableIterator.hasNext(); ) { String damageable = unmodifiableIterator.next();
/* 1556 */       if (name.contains(damageable)) return true;  }
/* 1557 */      return false;
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
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isOneOf(@Nonnull Material material, @Nullable List<String> materials) {
/* 1602 */     if (materials == null || materials.isEmpty()) return false; 
/* 1603 */     Objects.requireNonNull(material, "Cannot match materials with a null material");
/* 1604 */     String name = material.name();
/*      */     
/* 1606 */     for (String comp : materials) {
/* 1607 */       comp = comp.toUpperCase();
/* 1608 */       if (comp.startsWith("CONTAINS:")) {
/* 1609 */         comp = format(comp.substring(9));
/* 1610 */         if (name.contains(comp)) return true; 
/*      */         continue;
/*      */       } 
/* 1613 */       if (comp.startsWith("REGEX:")) {
/* 1614 */         comp = comp.substring(6);
/* 1615 */         if (name.matches(comp)) return true;
/*      */ 
/*      */         
/*      */         continue;
/*      */       } 
/* 1620 */       Optional<XMaterial> mat = matchXMaterial(comp);
/* 1621 */       if (mat.isPresent() && ((XMaterial)mat.get()).parseMaterial() == material) return true; 
/*      */     } 
/* 1623 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaterialVersion() {
/* 1634 */     if (this.legacy.length == 0) return 0; 
/* 1635 */     String version = this.legacy[0];
/* 1636 */     if (version.charAt(1) != '.') return 0;
/*      */     
/* 1638 */     return Integer.parseInt(version.substring(2));
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
/*      */   @Nonnull
/*      */   public ItemStack setType(@Nonnull ItemStack item) {
/* 1654 */     Objects.requireNonNull(item, "Cannot set material for null ItemStack");
/*      */     
/* 1656 */     item.setType(parseMaterial());
/* 1657 */     if (!ISFLAT && !isDamageable()) item.setDurability((short)this.data); 
/* 1658 */     return item;
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
/*      */   public boolean isOneOf(@Nullable List<String> materials) {
/* 1671 */     Material material = parseMaterial();
/* 1672 */     if (material == null) return false; 
/* 1673 */     return isOneOf(material, materials);
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
/*      */   private boolean anyMatchLegacy(@Nonnull String name) {
/* 1685 */     for (String legacy : this.legacy) {
/* 1686 */       if (legacy.isEmpty())
/* 1687 */         break;  if (name.equals(legacy)) return true; 
/*      */     } 
/* 1689 */     return false;
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
/*      */   public String toString() {
/* 1702 */     return toWord(name());
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
/*      */   public int getId() {
/* 1714 */     if (this.data != 0 || (this.legacy.length != 0 && Integer.parseInt(this.legacy[0].substring(2)) >= 13))
/* 1715 */       return -1; 
/* 1716 */     Material material = parseMaterial();
/* 1717 */     return (material == null) ? -1 : material.getId();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDuplicated() {
/* 1728 */     return duplicated.containsKey(this);
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
/*      */   public boolean isDamageable() {
/* 1740 */     return isDamageable(name());
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
/*      */   public byte getData() {
/* 1754 */     return this.data;
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
/*      */   public String[] getLegacy() {
/* 1767 */     return this.legacy;
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
/*      */   @Nullable
/*      */   public ItemStack parseItem() {
/* 1781 */     return parseItem(false);
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
/*      */   @Nullable
/*      */   public ItemStack parseItem(boolean suggest) {
/* 1796 */     Material material = parseMaterial(suggest);
/* 1797 */     if (material == null) return null; 
/* 1798 */     return ISFLAT ? new ItemStack(material) : new ItemStack(material, 1, (short)this.data);
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
/*      */   public Material parseMaterial() {
/* 1810 */     return parseMaterial(false);
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
/*      */   public Material parseMaterial(boolean suggest) {
/*      */     Material mat;
/* 1823 */     Optional<Material> cache = (Optional<Material>)PARSED_CACHE.getIfPresent(this);
/* 1824 */     if (cache != null) return cache.orElse(null);
/*      */ 
/*      */     
/* 1827 */     if (!ISFLAT && isDuplicated()) { mat = requestOldMaterial(suggest); }
/*      */     else
/* 1829 */     { mat = Material.getMaterial(name());
/* 1830 */       if (mat == null) mat = requestOldMaterial(suggest);
/*      */        }
/*      */     
/* 1833 */     if (mat != null) PARSED_CACHE.put(this, Optional.ofNullable(mat)); 
/* 1834 */     return mat;
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
/*      */   @Nullable
/*      */   private Material requestOldMaterial(boolean suggest) {
/* 1848 */     for (int i = this.legacy.length - 1; i >= 0; i--) {
/* 1849 */       String legacy = this.legacy[i];
/*      */ 
/*      */ 
/*      */       
/* 1853 */       if (i == 0 && legacy.charAt(1) == '.') return null;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1858 */       if (legacy.isEmpty()) {
/* 1859 */         if (suggest)
/*      */           continue; 
/*      */         break;
/*      */       } 
/* 1863 */       Material material = Material.getMaterial(legacy);
/* 1864 */       if (material != null) return material;  continue;
/*      */     } 
/* 1866 */     return null;
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
/*      */   public boolean isSimilar(@Nonnull ItemStack item) {
/* 1878 */     Objects.requireNonNull(item, "Cannot compare with null ItemStack");
/* 1879 */     if (item.getType() != parseMaterial()) return false; 
/* 1880 */     return (ISFLAT || isDamageable() || item.getDurability() == this.data);
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
/*      */   public List<String> getSuggestions() {
/* 1893 */     if (this.legacy.length == 0 || this.legacy[0].charAt(1) != '.') return new ArrayList<>(); 
/* 1894 */     List<String> suggestions = new ArrayList<>();
/* 1895 */     for (String legacy : this.legacy) {
/* 1896 */       if (legacy.isEmpty())
/* 1897 */         break;  suggestions.add(legacy);
/*      */     } 
/* 1899 */     return suggestions;
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
/*      */   public boolean isSupported() {
/* 1913 */     int version = getMaterialVersion();
/* 1914 */     if (version != 0) return supports(version);
/*      */     
/* 1916 */     Material material = Material.getMaterial(name());
/* 1917 */     if (material != null) return true; 
/* 1918 */     return (requestOldMaterial(false) != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFromNewSystem() {
/* 1929 */     return (this.legacy.length != 0 && Integer.parseInt(this.legacy[0].substring(2)) > 13);
/*      */   }
/*      */ }


/* Location:              C:\Users\Nerotek\Desktop\DeliveryMan.jar!\io\github\Leonardo0013YT\DeliveryMan\xseries\XMaterial.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */