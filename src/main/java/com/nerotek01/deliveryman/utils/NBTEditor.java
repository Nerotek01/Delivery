/*      */ package com.nerotek01.deliveryman.utils;
/*      */ 
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.UUID;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import javax.annotation.Nonnull;
/*      */ import org.bukkit.Bukkit;
/*      */ import org.bukkit.Location;
/*      */ import org.bukkit.Material;
/*      */ import org.bukkit.block.Block;
/*      */ import org.bukkit.entity.Entity;
/*      */ import org.bukkit.inventory.ItemStack;
/*      */ import org.bukkit.inventory.meta.ItemMeta;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class NBTEditor
/*      */ {
/*      */   private static final Map<String, Class<?>> classCache;
/*      */   private static final Map<String, Method> methodCache;
/*      */   private static final Map<Class<?>, Constructor<?>> constructorCache;
/*      */   private static final Map<Class<?>, Class<?>> NBTClasses;
/*      */   private static final Map<Class<?>, Field> NBTTagFieldCache;
/*   33 */   private static final String VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
/*   34 */   private static final MinecraftVersion LOCAL_VERSION = MinecraftVersion.get(VERSION); private static Field NBTListData;
/*      */   private static Field NBTCompoundMap;
/*      */   static {
/*   36 */     classCache = new HashMap<>();
/*      */     try {
/*   38 */       classCache.put("NBTBase", Class.forName("net.minecraft.server." + VERSION + ".NBTBase"));
/*   39 */       classCache.put("NBTTagCompound", Class.forName("net.minecraft.server." + VERSION + ".NBTTagCompound"));
/*   40 */       classCache.put("NBTTagList", Class.forName("net.minecraft.server." + VERSION + ".NBTTagList"));
/*   41 */       classCache.put("MojangsonParser", Class.forName("net.minecraft.server." + VERSION + ".MojangsonParser"));
/*      */       
/*   43 */       classCache.put("ItemStack", Class.forName("net.minecraft.server." + VERSION + ".ItemStack"));
/*   44 */       classCache.put("CraftItemStack", Class.forName("org.bukkit.craftbukkit." + VERSION + ".inventory.CraftItemStack"));
/*   45 */       classCache.put("CraftMetaSkull", Class.forName("org.bukkit.craftbukkit." + VERSION + ".inventory.CraftMetaSkull"));
/*      */       
/*   47 */       classCache.put("Entity", Class.forName("net.minecraft.server." + VERSION + ".Entity"));
/*   48 */       classCache.put("CraftEntity", Class.forName("org.bukkit.craftbukkit." + VERSION + ".entity.CraftEntity"));
/*   49 */       classCache.put("EntityLiving", Class.forName("net.minecraft.server." + VERSION + ".EntityLiving"));
/*      */       
/*   51 */       classCache.put("CraftWorld", Class.forName("org.bukkit.craftbukkit." + VERSION + ".CraftWorld"));
/*   52 */       classCache.put("CraftBlockState", Class.forName("org.bukkit.craftbukkit." + VERSION + ".block.CraftBlockState"));
/*   53 */       classCache.put("BlockPosition", Class.forName("net.minecraft.server." + VERSION + ".BlockPosition"));
/*   54 */       classCache.put("TileEntity", Class.forName("net.minecraft.server." + VERSION + ".TileEntity"));
/*   55 */       classCache.put("World", Class.forName("net.minecraft.server." + VERSION + ".World"));
/*   56 */       classCache.put("IBlockData", Class.forName("net.minecraft.server." + VERSION + ".IBlockData"));
/*      */       
/*   58 */       classCache.put("TileEntitySkull", Class.forName("net.minecraft.server." + VERSION + ".TileEntitySkull"));
/*      */       
/*   60 */       classCache.put("GameProfile", Class.forName("com.mojang.authlib.GameProfile"));
/*   61 */       classCache.put("Property", Class.forName("com.mojang.authlib.properties.Property"));
/*   62 */       classCache.put("PropertyMap", Class.forName("com.mojang.authlib.properties.PropertyMap"));
/*   63 */     } catch (ClassNotFoundException e) {
/*   64 */       e.printStackTrace();
/*      */     } 
/*      */     
/*   67 */     NBTClasses = new HashMap<>();
/*      */     try {
/*   69 */       NBTClasses.put(Byte.class, Class.forName("net.minecraft.server." + VERSION + ".NBTTagByte"));
/*   70 */       NBTClasses.put(Boolean.class, Class.forName("net.minecraft.server." + VERSION + ".NBTTagByte"));
/*   71 */       NBTClasses.put(String.class, Class.forName("net.minecraft.server." + VERSION + ".NBTTagString"));
/*   72 */       NBTClasses.put(Double.class, Class.forName("net.minecraft.server." + VERSION + ".NBTTagDouble"));
/*   73 */       NBTClasses.put(Integer.class, Class.forName("net.minecraft.server." + VERSION + ".NBTTagInt"));
/*   74 */       NBTClasses.put(Long.class, Class.forName("net.minecraft.server." + VERSION + ".NBTTagLong"));
/*   75 */       NBTClasses.put(Short.class, Class.forName("net.minecraft.server." + VERSION + ".NBTTagShort"));
/*   76 */       NBTClasses.put(Float.class, Class.forName("net.minecraft.server." + VERSION + ".NBTTagFloat"));
/*   77 */       NBTClasses.put(Class.forName("[B"), Class.forName("net.minecraft.server." + VERSION + ".NBTTagByteArray"));
/*   78 */       NBTClasses.put(Class.forName("[I"), Class.forName("net.minecraft.server." + VERSION + ".NBTTagIntArray"));
/*   79 */     } catch (ClassNotFoundException e) {
/*   80 */       e.printStackTrace();
/*      */     } 
/*      */     
/*   83 */     methodCache = new HashMap<>();
/*      */     try {
/*   85 */       methodCache.put("get", getNMSClass("NBTTagCompound").getMethod("get", new Class[] { String.class }));
/*   86 */       methodCache.put("set", getNMSClass("NBTTagCompound").getMethod("set", new Class[] { String.class, getNMSClass("NBTBase") }));
/*   87 */       methodCache.put("hasKey", getNMSClass("NBTTagCompound").getMethod("hasKey", new Class[] { String.class }));
/*   88 */       methodCache.put("setIndex", getNMSClass("NBTTagList").getMethod("a", new Class[] { int.class, getNMSClass("NBTBase") }));
/*   89 */       if (LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_14)) {
/*   90 */         methodCache.put("getTypeId", getNMSClass("NBTBase").getMethod("getTypeId", new Class[0]));
/*   91 */         methodCache.put("add", getNMSClass("NBTTagList").getMethod("add", new Class[] { int.class, getNMSClass("NBTBase") }));
/*      */       } else {
/*   93 */         methodCache.put("add", getNMSClass("NBTTagList").getMethod("add", new Class[] { getNMSClass("NBTBase") }));
/*      */       } 
/*   95 */       methodCache.put("size", getNMSClass("NBTTagList").getMethod("size", new Class[0]));
/*      */       
/*   97 */       if (LOCAL_VERSION == MinecraftVersion.v1_8) {
/*   98 */         methodCache.put("listRemove", getNMSClass("NBTTagList").getMethod("a", new Class[] { int.class }));
/*      */       } else {
/*  100 */         methodCache.put("listRemove", getNMSClass("NBTTagList").getMethod("remove", new Class[] { int.class }));
/*      */       } 
/*  102 */       methodCache.put("remove", getNMSClass("NBTTagCompound").getMethod("remove", new Class[] { String.class }));
/*      */       
/*  104 */       if (LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_13)) {
/*  105 */         methodCache.put("getKeys", getNMSClass("NBTTagCompound").getMethod("getKeys", new Class[0]));
/*      */       } else {
/*  107 */         methodCache.put("getKeys", getNMSClass("NBTTagCompound").getMethod("c", new Class[0]));
/*      */       } 
/*      */       
/*  110 */       methodCache.put("hasTag", getNMSClass("ItemStack").getMethod("hasTag", new Class[0]));
/*  111 */       methodCache.put("getTag", getNMSClass("ItemStack").getMethod("getTag", new Class[0]));
/*  112 */       methodCache.put("setTag", getNMSClass("ItemStack").getMethod("setTag", new Class[] { getNMSClass("NBTTagCompound") }));
/*  113 */       methodCache.put("asNMSCopy", getNMSClass("CraftItemStack").getMethod("asNMSCopy", new Class[] { ItemStack.class }));
/*  114 */       methodCache.put("asBukkitCopy", getNMSClass("CraftItemStack").getMethod("asBukkitCopy", new Class[] { getNMSClass("ItemStack") }));
/*      */       
/*  116 */       methodCache.put("getEntityHandle", getNMSClass("CraftEntity").getMethod("getHandle", new Class[0]));
/*  117 */       if (LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_16)) {
/*  118 */         methodCache.put("getEntityTag", getNMSClass("Entity").getMethod("save", new Class[] { getNMSClass("NBTTagCompound") }));
/*  119 */         methodCache.put("setEntityTag", getNMSClass("Entity").getMethod("load", new Class[] { getNMSClass("NBTTagCompound") }));
/*      */       } else {
/*  121 */         methodCache.put("getEntityTag", getNMSClass("Entity").getMethod("c", new Class[] { getNMSClass("NBTTagCompound") }));
/*  122 */         methodCache.put("setEntityTag", getNMSClass("Entity").getMethod("f", new Class[] { getNMSClass("NBTTagCompound") }));
/*      */       } 
/*      */       
/*  125 */       methodCache.put("save", getNMSClass("ItemStack").getMethod("save", new Class[] { getNMSClass("NBTTagCompound") }));
/*      */       
/*  127 */       if (LOCAL_VERSION.lessThanOrEqualTo(MinecraftVersion.v1_10)) {
/*  128 */         methodCache.put("createStack", getNMSClass("ItemStack").getMethod("createStack", new Class[] { getNMSClass("NBTTagCompound") }));
/*  129 */       } else if (LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_13)) {
/*  130 */         methodCache.put("createStack", getNMSClass("ItemStack").getMethod("a", new Class[] { getNMSClass("NBTTagCompound") }));
/*      */       } 
/*      */       
/*  133 */       if (LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_16)) {
/*  134 */         methodCache.put("setTileTag", getNMSClass("TileEntity").getMethod("load", new Class[] { getNMSClass("IBlockData"), getNMSClass("NBTTagCompound") }));
/*  135 */         methodCache.put("getType", getNMSClass("World").getMethod("getType", new Class[] { getNMSClass("BlockPosition") }));
/*  136 */       } else if (LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_12)) {
/*  137 */         methodCache.put("setTileTag", getNMSClass("TileEntity").getMethod("load", new Class[] { getNMSClass("NBTTagCompound") }));
/*      */       } else {
/*  139 */         methodCache.put("setTileTag", getNMSClass("TileEntity").getMethod("a", new Class[] { getNMSClass("NBTTagCompound") }));
/*      */       } 
/*  141 */       methodCache.put("getTileEntity", getNMSClass("World").getMethod("getTileEntity", new Class[] { getNMSClass("BlockPosition") }));
/*  142 */       methodCache.put("getWorldHandle", getNMSClass("CraftWorld").getMethod("getHandle", new Class[0]));
/*      */       
/*  144 */       methodCache.put("setGameProfile", getNMSClass("TileEntitySkull").getMethod("setGameProfile", new Class[] { getNMSClass("GameProfile") }));
/*  145 */       methodCache.put("getProperties", getNMSClass("GameProfile").getMethod("getProperties", new Class[0]));
/*  146 */       methodCache.put("getName", getNMSClass("Property").getMethod("getName", new Class[0]));
/*  147 */       methodCache.put("getValue", getNMSClass("Property").getMethod("getValue", new Class[0]));
/*  148 */       methodCache.put("values", getNMSClass("PropertyMap").getMethod("values", new Class[0]));
/*  149 */       methodCache.put("put", getNMSClass("PropertyMap").getMethod("put", new Class[] { Object.class, Object.class }));
/*      */       
/*  151 */       methodCache.put("loadNBTTagCompound", getNMSClass("MojangsonParser").getMethod("parse", new Class[] { String.class }));
/*  152 */     } catch (Exception e) {
/*  153 */       e.printStackTrace();
/*      */     } 
/*      */     
/*      */     try {
/*  157 */       methodCache.put("getTileTag", getNMSClass("TileEntity").getMethod("save", new Class[] { getNMSClass("NBTTagCompound") }));
/*  158 */     } catch (NoSuchMethodException exception) {
/*      */       try {
/*  160 */         methodCache.put("getTileTag", getNMSClass("TileEntity").getMethod("b", new Class[] { getNMSClass("NBTTagCompound") }));
/*  161 */       } catch (Exception exception2) {
/*  162 */         exception2.printStackTrace();
/*      */       } 
/*  164 */     } catch (Exception exception) {
/*  165 */       exception.printStackTrace();
/*      */     } 
/*      */     
/*      */     try {
/*  169 */       methodCache.put("setProfile", getNMSClass("CraftMetaSkull").getDeclaredMethod("setProfile", new Class[] { getNMSClass("GameProfile") }));
/*  170 */       ((Method)methodCache.get("setProfile")).setAccessible(true);
/*  171 */     } catch (NoSuchMethodException noSuchMethodException) {}
/*      */ 
/*      */ 
/*      */     
/*  175 */     constructorCache = new HashMap<>();
/*      */     try {
/*  177 */       constructorCache.put(getNBTTag(Byte.class), getNBTTag(Byte.class).getDeclaredConstructor(new Class[] { byte.class }));
/*  178 */       constructorCache.put(getNBTTag(Boolean.class), getNBTTag(Boolean.class).getDeclaredConstructor(new Class[] { byte.class }));
/*  179 */       constructorCache.put(getNBTTag(String.class), getNBTTag(String.class).getDeclaredConstructor(new Class[] { String.class }));
/*  180 */       constructorCache.put(getNBTTag(Double.class), getNBTTag(Double.class).getDeclaredConstructor(new Class[] { double.class }));
/*  181 */       constructorCache.put(getNBTTag(Integer.class), getNBTTag(Integer.class).getDeclaredConstructor(new Class[] { int.class }));
/*  182 */       constructorCache.put(getNBTTag(Long.class), getNBTTag(Long.class).getDeclaredConstructor(new Class[] { long.class }));
/*  183 */       constructorCache.put(getNBTTag(Float.class), getNBTTag(Float.class).getDeclaredConstructor(new Class[] { float.class }));
/*  184 */       constructorCache.put(getNBTTag(Short.class), getNBTTag(Short.class).getDeclaredConstructor(new Class[] { short.class }));
/*  185 */       constructorCache.put(getNBTTag(Class.forName("[B")), getNBTTag(Class.forName("[B")).getDeclaredConstructor(new Class[] { Class.forName("[B") }));
/*  186 */       constructorCache.put(getNBTTag(Class.forName("[I")), getNBTTag(Class.forName("[I")).getDeclaredConstructor(new Class[] { Class.forName("[I") }));
/*      */ 
/*      */       
/*  189 */       for (Constructor<?> cons : constructorCache.values()) {
/*  190 */         cons.setAccessible(true);
/*      */       }
/*      */       
/*  193 */       constructorCache.put(getNMSClass("BlockPosition"), getNMSClass("BlockPosition").getConstructor(new Class[] { int.class, int.class, int.class }));
/*      */       
/*  195 */       constructorCache.put(getNMSClass("GameProfile"), getNMSClass("GameProfile").getConstructor(new Class[] { UUID.class, String.class }));
/*  196 */       constructorCache.put(getNMSClass("Property"), getNMSClass("Property").getConstructor(new Class[] { String.class, String.class }));
/*      */       
/*  198 */       if (LOCAL_VERSION == MinecraftVersion.v1_11 || LOCAL_VERSION == MinecraftVersion.v1_12) {
/*  199 */         constructorCache.put(getNMSClass("ItemStack"), getNMSClass("ItemStack").getConstructor(new Class[] { getNMSClass("NBTTagCompound") }));
/*      */       }
/*  201 */     } catch (Exception e) {
/*  202 */       e.printStackTrace();
/*      */     } 
/*      */     
/*  205 */     NBTTagFieldCache = new HashMap<>();
/*      */     try {
/*  207 */       for (Class<?> clazz : NBTClasses.values()) {
/*  208 */         Field data = clazz.getDeclaredField("data");
/*  209 */         data.setAccessible(true);
/*  210 */         NBTTagFieldCache.put(clazz, data);
/*      */       } 
/*  212 */     } catch (Exception e) {
/*  213 */       e.printStackTrace();
/*      */     } 
/*      */     
/*      */     try {
/*  217 */       NBTListData = getNMSClass("NBTTagList").getDeclaredField("list");
/*  218 */       NBTListData.setAccessible(true);
/*  219 */       NBTCompoundMap = getNMSClass("NBTTagCompound").getDeclaredField("map");
/*  220 */       NBTCompoundMap.setAccessible(true);
/*  221 */     } catch (Exception e) {
/*  222 */       e.printStackTrace();
/*      */     } 
/*      */   }
/*      */   private static Class<?> getNBTTag(Class<?> primitiveType) {
/*  227 */     if (NBTClasses.containsKey(primitiveType))
/*  228 */       return NBTClasses.get(primitiveType); 
/*  229 */     return primitiveType;
/*      */   }
/*      */   
/*      */   private static Object getNBTVar(Object object) {
/*  233 */     if (object == null) {
/*  234 */       return null;
/*      */     }
/*  236 */     Class<?> clazz = object.getClass();
/*      */     try {
/*  238 */       if (NBTTagFieldCache.containsKey(clazz)) {
/*  239 */         return ((Field)NBTTagFieldCache.get(clazz)).get(object);
/*      */       }
/*  241 */     } catch (Exception exception) {
/*  242 */       exception.printStackTrace();
/*      */     } 
/*  244 */     return null;
/*      */   }
/*      */   
/*      */   private static Method getMethod(String name) {
/*  248 */     return methodCache.containsKey(name) ? methodCache.get(name) : null;
/*      */   }
/*      */   
/*      */   private static Constructor<?> getConstructor(Class<?> clazz) {
/*  252 */     return constructorCache.containsKey(clazz) ? constructorCache.get(clazz) : null;
/*      */   }
/*      */   
/*      */   private static Class<?> getNMSClass(String name) {
/*  256 */     if (classCache.containsKey(name)) {
/*  257 */       return classCache.get(name);
/*      */     }
/*      */     
/*      */     try {
/*  261 */       return Class.forName("net.minecraft.server." + VERSION + "." + name);
/*  262 */     } catch (ClassNotFoundException e) {
/*  263 */       e.printStackTrace();
/*  264 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static String getMatch(String string, String regex) {
/*  269 */     Pattern pattern = Pattern.compile(regex);
/*  270 */     Matcher matcher = pattern.matcher(string);
/*  271 */     if (matcher.find()) {
/*  272 */       return matcher.group(1);
/*      */     }
/*  274 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Object createItemStack(Object compound) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
/*  279 */     if (LOCAL_VERSION == MinecraftVersion.v1_11 || LOCAL_VERSION == MinecraftVersion.v1_12) {
/*  280 */       return getConstructor(getNMSClass("ItemStack")).newInstance(new Object[] { compound });
/*      */     }
/*  282 */     return getMethod("createStack").invoke((Object)null, new Object[] { compound });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getVersion() {
/*  291 */     return VERSION;
/*      */   }
/*      */   
/*      */   public static MinecraftVersion getMinecraftVersion() {
/*  295 */     return LOCAL_VERSION;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ItemStack getHead(String skinURL) {
/*  305 */     Material material = Material.getMaterial("SKULL_ITEM");
/*  306 */     if (material == null) {
/*  307 */       material = Material.getMaterial("PLAYER_HEAD");
/*      */     }
/*  309 */     ItemStack head = new ItemStack(material, 1, (short)3);
/*  310 */     if (skinURL == null || skinURL.isEmpty()) {
/*  311 */       return head;
/*      */     }
/*  313 */     ItemMeta headMeta = head.getItemMeta();
/*  314 */     Object profile = null;
/*      */     try {
/*  316 */       profile = getConstructor(getNMSClass("GameProfile")).newInstance(new Object[] { UUID.randomUUID(), null });
/*  317 */       Object propertyMap = getMethod("getProperties").invoke(profile, new Object[0]);
/*  318 */       Object textureProperty = getConstructor(getNMSClass("Property")).newInstance(new Object[] { "textures", skinURL });
/*  319 */       getMethod("put").invoke(propertyMap, new Object[] { "textures", textureProperty });
/*  320 */     } catch (IllegalAccessException|IllegalArgumentException|InvocationTargetException|InstantiationException e1) {
/*  321 */       e1.printStackTrace();
/*      */     } 
/*      */     
/*  324 */     if (methodCache.containsKey("setProfile")) {
/*      */       try {
/*  326 */         getMethod("setProfile").invoke(headMeta, new Object[] { profile });
/*  327 */       } catch (IllegalAccessException|IllegalArgumentException|InvocationTargetException e) {
/*  328 */         e.printStackTrace();
/*      */       } 
/*      */     } else {
/*  331 */       Field profileField = null;
/*      */       try {
/*  333 */         profileField = headMeta.getClass().getDeclaredField("profile");
/*  334 */       } catch (NoSuchFieldException|SecurityException e) {
/*  335 */         e.printStackTrace();
/*      */       } 
/*  337 */       profileField.setAccessible(true);
/*      */       try {
/*  339 */         profileField.set(headMeta, profile);
/*  340 */       } catch (IllegalArgumentException|IllegalAccessException e) {
/*  341 */         e.printStackTrace();
/*      */       } 
/*      */     } 
/*  344 */     head.setItemMeta(headMeta);
/*  345 */     return head;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getTexture(ItemStack head) {
/*      */     Field profileField;
/*  355 */     ItemMeta meta = head.getItemMeta();
/*      */     
/*      */     try {
/*  358 */       profileField = meta.getClass().getDeclaredField("profile");
/*  359 */     } catch (NoSuchFieldException|SecurityException e) {
/*  360 */       e.printStackTrace();
/*  361 */       return "";
/*      */     } 
/*  363 */     profileField.setAccessible(true);
/*      */     try {
/*  365 */       Object profile = profileField.get(meta);
/*  366 */       if (profile == null) {
/*  367 */         return "";
/*      */       }
/*      */       
/*  370 */       Collection<Object> properties = (Collection<Object>)getMethod("values").invoke(getMethod("getProperties").invoke(profile, new Object[0]), new Object[0]);
/*  371 */       for (Object prop : properties) {
/*  372 */         if ("textures".equals(getMethod("getName").invoke(prop, new Object[0]))) {
/*  373 */           return (String)getMethod("getValue").invoke(prop, new Object[0]);
/*      */         }
/*      */       } 
/*  376 */       return "";
/*  377 */     } catch (IllegalArgumentException|IllegalAccessException|SecurityException|InvocationTargetException e) {
/*  378 */       e.printStackTrace();
/*  379 */       return "";
/*      */     } 
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
/*      */   private static Object getItemTag(ItemStack item, Object... keys) {
/*      */     try {
/*  393 */       return getTag(getCompound(item), keys);
/*  394 */     } catch (IllegalAccessException|IllegalArgumentException|InvocationTargetException e) {
/*  395 */       e.printStackTrace();
/*  396 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static Object getCompound(ItemStack item) {
/*  402 */     if (item == null) {
/*  403 */       return null;
/*      */     }
/*      */     try {
/*  406 */       Object stack = null;
/*  407 */       stack = getMethod("asNMSCopy").invoke((Object)null, new Object[] { item });
/*      */       
/*  409 */       Object tag = null;
/*      */       
/*  411 */       if (getMethod("hasTag").invoke(stack, new Object[0]).equals(Boolean.valueOf(true))) {
/*  412 */         tag = getMethod("getTag").invoke(stack, new Object[0]);
/*      */       } else {
/*  414 */         tag = getNMSClass("NBTTagCompound").newInstance();
/*      */       } 
/*      */       
/*  417 */       return tag;
/*  418 */     } catch (Exception exception) {
/*  419 */       exception.printStackTrace();
/*  420 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static NBTCompound getItemNBTTag(ItemStack item, Object... keys) {
/*  425 */     if (item == null) {
/*  426 */       return null;
/*      */     }
/*      */     try {
/*  429 */       Object stack = null;
/*  430 */       stack = getMethod("asNMSCopy").invoke((Object)null, new Object[] { item });
/*      */       
/*  432 */       Object tag = getNMSClass("NBTTagCompound").newInstance();
/*      */       
/*  434 */       tag = getMethod("save").invoke(stack, new Object[] { tag });
/*      */       
/*  436 */       return getNBTTag(tag, keys);
/*  437 */     } catch (Exception exception) {
/*  438 */       exception.printStackTrace();
/*  439 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static ItemStack setItemTag(ItemStack item, Object value, Object... keys) {
/*  444 */     if (item == null) {
/*  445 */       return null;
/*      */     }
/*      */     try {
/*  448 */       Object stack = getMethod("asNMSCopy").invoke((Object)null, new Object[] { item });
/*      */       
/*  450 */       Object tag = null;
/*      */       
/*  452 */       if (getMethod("hasTag").invoke(stack, new Object[0]).equals(Boolean.valueOf(true))) {
/*  453 */         tag = getMethod("getTag").invoke(stack, new Object[0]);
/*      */       } else {
/*  455 */         tag = getNMSClass("NBTTagCompound").newInstance();
/*      */       } 
/*      */       
/*  458 */       if (keys.length == 0 && value instanceof NBTCompound) {
/*  459 */         tag = ((NBTCompound)value).tag;
/*      */       } else {
/*  461 */         setTag(tag, value, keys);
/*      */       } 
/*      */       
/*  464 */       getMethod("setTag").invoke(stack, new Object[] { tag });
/*  465 */       return (ItemStack)getMethod("asBukkitCopy").invoke((Object)null, new Object[] { stack });
/*  466 */     } catch (Exception exception) {
/*  467 */       exception.printStackTrace();
/*  468 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static ItemStack getItemFromTag(NBTCompound compound) {
/*  473 */     if (compound == null) {
/*  474 */       return null;
/*      */     }
/*      */     try {
/*  477 */       Object tag = compound.tag;
/*  478 */       Object count = getTag(tag, new Object[] { "Count" });
/*  479 */       Object id = getTag(tag, new Object[] { "id" });
/*  480 */       if (count == null || id == null) {
/*  481 */         return null;
/*      */       }
/*  483 */       if (count instanceof Byte && id instanceof String) {
/*  484 */         return (ItemStack)getMethod("asBukkitCopy").invoke((Object)null, new Object[] { createItemStack(tag) });
/*      */       }
/*  486 */       return null;
/*  487 */     } catch (Exception exception) {
/*  488 */       exception.printStackTrace();
/*  489 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static Object getEntityTag(Entity entity, Object... keys) {
/*      */     try {
/*  495 */       return getTag(getCompound(entity), keys);
/*  496 */     } catch (IllegalAccessException|IllegalArgumentException|InvocationTargetException e) {
/*  497 */       e.printStackTrace();
/*  498 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static Object getCompound(Entity entity) {
/*  503 */     if (entity == null) {
/*  504 */       return entity;
/*      */     }
/*      */     try {
/*  507 */       Object NMSEntity = getMethod("getEntityHandle").invoke(entity, new Object[0]);
/*      */       
/*  509 */       Object tag = getNMSClass("NBTTagCompound").newInstance();
/*      */       
/*  511 */       getMethod("getEntityTag").invoke(NMSEntity, new Object[] { tag });
/*      */       
/*  513 */       return tag;
/*  514 */     } catch (Exception exception) {
/*  515 */       exception.printStackTrace();
/*  516 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void setEntityTag(Entity entity, Object value, Object... keys) {
/*  521 */     if (entity == null) {
/*      */       return;
/*      */     }
/*      */     try {
/*  525 */       Object NMSEntity = getMethod("getEntityHandle").invoke(entity, new Object[0]);
/*      */       
/*  527 */       Object tag = getNMSClass("NBTTagCompound").newInstance();
/*      */       
/*  529 */       getMethod("getEntityTag").invoke(NMSEntity, new Object[] { tag });
/*      */       
/*  531 */       if (keys.length == 0 && value instanceof NBTCompound) {
/*  532 */         tag = ((NBTCompound)value).tag;
/*      */       } else {
/*  534 */         setTag(tag, value, keys);
/*      */       } 
/*      */       
/*  537 */       getMethod("setEntityTag").invoke(NMSEntity, new Object[] { tag });
/*  538 */     } catch (Exception exception) {
/*  539 */       exception.printStackTrace();
/*      */       return;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static Object getBlockTag(Block block, Object... keys) {
/*      */     try {
/*  546 */       return getTag(getCompound(block), keys);
/*  547 */     } catch (IllegalAccessException|IllegalArgumentException|InvocationTargetException e) {
/*  548 */       e.printStackTrace();
/*  549 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static Object getCompound(Block block) {
/*      */     try {
/*  555 */       if (block == null || !getNMSClass("CraftBlockState").isInstance(block.getState())) {
/*  556 */         return null;
/*      */       }
/*  558 */       Location location = block.getLocation();
/*      */       
/*  560 */       Object blockPosition = getConstructor(getNMSClass("BlockPosition")).newInstance(new Object[] { Integer.valueOf(location.getBlockX()), Integer.valueOf(location.getBlockY()), Integer.valueOf(location.getBlockZ()) });
/*      */       
/*  562 */       Object nmsWorld = getMethod("getWorldHandle").invoke(location.getWorld(), new Object[0]);
/*      */       
/*  564 */       Object tileEntity = getMethod("getTileEntity").invoke(nmsWorld, new Object[] { blockPosition });
/*      */       
/*  566 */       Object tag = getNMSClass("NBTTagCompound").newInstance();
/*      */       
/*  568 */       getMethod("getTileTag").invoke(tileEntity, new Object[] { tag });
/*      */       
/*  570 */       return tag;
/*  571 */     } catch (Exception exception) {
/*  572 */       exception.printStackTrace();
/*  573 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void setBlockTag(Block block, Object value, Object... keys) {
/*      */     try {
/*  579 */       if (block == null || !getNMSClass("CraftBlockState").isInstance(block.getState())) {
/*      */         return;
/*      */       }
/*  582 */       Location location = block.getLocation();
/*      */       
/*  584 */       Object blockPosition = getConstructor(getNMSClass("BlockPosition")).newInstance(new Object[] { Integer.valueOf(location.getBlockX()), Integer.valueOf(location.getBlockY()), Integer.valueOf(location.getBlockZ()) });
/*      */       
/*  586 */       Object nmsWorld = getMethod("getWorldHandle").invoke(location.getWorld(), new Object[0]);
/*      */       
/*  588 */       Object tileEntity = getMethod("getTileEntity").invoke(nmsWorld, new Object[] { blockPosition });
/*      */       
/*  590 */       Object tag = getNMSClass("NBTTagCompound").newInstance();
/*      */       
/*  592 */       getMethod("getTileTag").invoke(tileEntity, new Object[] { tag });
/*      */       
/*  594 */       if (keys.length == 0 && value instanceof NBTCompound) {
/*  595 */         tag = ((NBTCompound)value).tag;
/*      */       } else {
/*  597 */         setTag(tag, value, keys);
/*      */       } 
/*      */       
/*  600 */       if (LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_16)) {
/*  601 */         getMethod("setTileTag").invoke(tileEntity, new Object[] { getMethod("getType").invoke(nmsWorld, new Object[] { blockPosition }), tag });
/*      */       } else {
/*  603 */         getMethod("setTileTag").invoke(tileEntity, new Object[] { tag });
/*      */       } 
/*  605 */     } catch (Exception exception) {
/*  606 */       exception.printStackTrace();
/*      */       return;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static Object getValue(Object object, Object... keys) {
/*  612 */     if (object instanceof ItemStack)
/*  613 */       return getItemTag((ItemStack)object, keys); 
/*  614 */     if (object instanceof Entity)
/*  615 */       return getEntityTag((Entity)object, keys); 
/*  616 */     if (object instanceof Block)
/*  617 */       return getBlockTag((Block)object, keys); 
/*  618 */     if (object instanceof NBTCompound) {
/*      */       try {
/*  620 */         return getTag(((NBTCompound)object).tag, keys);
/*  621 */       } catch (IllegalAccessException|IllegalArgumentException|InvocationTargetException e) {
/*  622 */         e.printStackTrace();
/*  623 */         return null;
/*      */       } 
/*      */     }
/*  626 */     throw new IllegalArgumentException("Object provided must be of type ItemStack, Entity, Block, or NBTCompound!");
/*      */   }
/*      */ 
/*      */   
/*      */   public static String getString(Object object, Object... keys) {
/*  631 */     Object result = getValue(object, keys);
/*  632 */     return (result instanceof String) ? (String)result : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getInt(Object object, Object... keys) {
/*  643 */     Object result = getValue(object, keys);
/*  644 */     return (result instanceof Integer) ? ((Integer)result).intValue() : 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double getDouble(Object object, Object... keys) {
/*  655 */     Object result = getValue(object, keys);
/*  656 */     return (result instanceof Double) ? ((Double)result).doubleValue() : 0.0D;
/*      */   }
/*      */   
/*      */   public static long getLong(Object object, Object... keys) {
/*  660 */     Object result = getValue(object, keys);
/*  661 */     return (result instanceof Long) ? ((Long)result).longValue() : 0L;
/*      */   }
/*      */   
/*      */   public static float getFloat(Object object, Object... keys) {
/*  665 */     Object result = getValue(object, keys);
/*  666 */     return (result instanceof Float) ? ((Float)result).floatValue() : 0.0F;
/*      */   }
/*      */   
/*      */   public static short getShort(Object object, Object... keys) {
/*  670 */     Object result = getValue(object, keys);
/*  671 */     return (result instanceof Short) ? ((Short)result).shortValue() : 0;
/*      */   }
/*      */   
/*      */   public static byte getByte(Object object, Object... keys) {
/*  675 */     Object result = getValue(object, keys);
/*  676 */     return (result instanceof Byte) ? ((Byte)result).byteValue() : 0;
/*      */   }
/*      */   
/*      */   public static boolean getBoolean(Object object, Object... keys) {
/*  680 */     return (getByte(object, keys) == 1);
/*      */   }
/*      */   
/*      */   public static boolean contains(Object object, Object... keys) {
/*  684 */     Object result = getValue(object, keys);
/*  685 */     return (result != null);
/*      */   }
/*      */   
/*      */   public static Collection<String> getKeys(Object object, Object... keys) {
/*      */     Object compound;
/*  690 */     if (object instanceof ItemStack) {
/*  691 */       compound = getCompound((ItemStack)object);
/*  692 */     } else if (object instanceof Entity) {
/*  693 */       compound = getCompound((Entity)object);
/*  694 */     } else if (object instanceof Block) {
/*  695 */       compound = getCompound((Block)object);
/*  696 */     } else if (object instanceof NBTCompound) {
/*  697 */       compound = ((NBTCompound)object).tag;
/*      */     } else {
/*  699 */       throw new IllegalArgumentException("Object provided must be of type ItemStack, Entity, Block, or NBTCompound!");
/*      */     } 
/*      */     
/*      */     try {
/*  703 */       NBTCompound nbtCompound = getNBTTag(compound, keys);
/*      */       
/*  705 */       Object tag = nbtCompound.tag;
/*  706 */       if (getNMSClass("NBTTagCompound").isInstance(tag)) {
/*  707 */         return (Collection<String>)getMethod("getKeys").invoke(tag, new Object[0]);
/*      */       }
/*  709 */       return null;
/*      */     
/*      */     }
/*  712 */     catch (IllegalAccessException|IllegalArgumentException|InvocationTargetException e) {
/*  713 */       e.printStackTrace();
/*      */ 
/*      */       
/*  716 */       return null;
/*      */     } 
/*      */   }
/*      */   public static int getSize(Object object, Object... keys) {
/*      */     Object compound;
/*  721 */     if (object instanceof ItemStack) {
/*  722 */       compound = getCompound((ItemStack)object);
/*  723 */     } else if (object instanceof Entity) {
/*  724 */       compound = getCompound((Entity)object);
/*  725 */     } else if (object instanceof Block) {
/*  726 */       compound = getCompound((Block)object);
/*  727 */     } else if (object instanceof NBTCompound) {
/*  728 */       compound = ((NBTCompound)object).tag;
/*      */     } else {
/*  730 */       throw new IllegalArgumentException("Object provided must be of type ItemStack, Entity, Block, or NBTCompound!");
/*      */     } 
/*      */     
/*      */     try {
/*  734 */       NBTCompound nbtCompound = getNBTTag(compound, keys);
/*  735 */       if (getNMSClass("NBTTagCompound").isInstance(nbtCompound.tag))
/*  736 */         return getKeys(nbtCompound, new Object[0]).size(); 
/*  737 */       if (getNMSClass("NBTTagList").isInstance(nbtCompound.tag)) {
/*  738 */         return ((Integer)getMethod("size").invoke(nbtCompound.tag, new Object[0])).intValue();
/*      */       }
/*  740 */     } catch (IllegalAccessException|IllegalArgumentException|InvocationTargetException e) {
/*  741 */       e.printStackTrace();
/*  742 */       return 0;
/*      */     } 
/*      */     
/*  745 */     throw new IllegalArgumentException("Value is not a compound or list!");
/*      */   }
/*      */   
/*      */   public static <T> T set(T object, Object value, Object... keys) {
/*  749 */     if (object instanceof ItemStack)
/*  750 */       return (T)setItemTag((ItemStack)object, value, keys); 
/*  751 */     if (object instanceof Entity) {
/*  752 */       setEntityTag((Entity)object, value, keys);
/*  753 */     } else if (object instanceof Block) {
/*  754 */       setBlockTag((Block)object, value, keys);
/*  755 */     } else if (object instanceof NBTCompound) {
/*      */       try {
/*  757 */         setTag(((NBTCompound)object).tag, value, keys);
/*  758 */       } catch (InstantiationException|IllegalAccessException|IllegalArgumentException|InvocationTargetException e) {
/*  759 */         e.printStackTrace();
/*      */       } 
/*      */     } else {
/*  762 */       throw new IllegalArgumentException("Object provided must be of type ItemStack, Entity, Block, or NBTCompound!");
/*      */     } 
/*  764 */     return object;
/*      */   }
/*      */   
/*      */   private static void setTag(Object tag, Object value, Object... keys) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
/*      */     Object notCompound;
/*  769 */     if (value != null) {
/*  770 */       if (value instanceof NBTCompound) {
/*  771 */         notCompound = ((NBTCompound)value).tag;
/*  772 */       } else if (getNMSClass("NBTTagList").isInstance(value) || getNMSClass("NBTTagCompound").isInstance(value)) {
/*  773 */         notCompound = value;
/*      */       } else {
/*  775 */         if (value instanceof Boolean) {
/*  776 */           value = Byte.valueOf((byte)((((Boolean)value).booleanValue() == true) ? 1 : 0));
/*      */         }
/*  778 */         notCompound = getConstructor(getNBTTag(value.getClass())).newInstance(new Object[] { value });
/*      */       } 
/*      */     } else {
/*  781 */       notCompound = null;
/*      */     } 
/*      */     
/*  784 */     Object compound = tag;
/*  785 */     for (int index = 0; index < keys.length - 1; index++) {
/*  786 */       Object key = keys[index];
/*  787 */       Object oldCompound = compound;
/*  788 */       if (key instanceof Integer) {
/*  789 */         compound = ((List)NBTListData.get(compound)).get(((Integer)key).intValue());
/*  790 */       } else if (key != null) {
/*  791 */         compound = getMethod("get").invoke(compound, new Object[] { key });
/*      */       } 
/*  793 */       if (compound == null || key == null) {
/*  794 */         if (keys[index + 1] == null || keys[index + 1] instanceof Integer) {
/*  795 */           compound = getNMSClass("NBTTagList").newInstance();
/*      */         } else {
/*  797 */           compound = getNMSClass("NBTTagCompound").newInstance();
/*      */         } 
/*  799 */         if (oldCompound.getClass().getSimpleName().equals("NBTTagList")) {
/*  800 */           if (LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_14)) {
/*  801 */             getMethod("add").invoke(oldCompound, new Object[] { getMethod("size").invoke(oldCompound, new Object[0]), compound });
/*      */           } else {
/*  803 */             getMethod("add").invoke(oldCompound, new Object[] { compound });
/*      */           } 
/*      */         } else {
/*  806 */           getMethod("set").invoke(oldCompound, new Object[] { key, compound });
/*      */         } 
/*      */       } 
/*      */     } 
/*  810 */     if (keys.length > 0) {
/*  811 */       Object lastKey = keys[keys.length - 1];
/*  812 */       if (lastKey == null) {
/*  813 */         if (LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_14)) {
/*  814 */           getMethod("add").invoke(compound, new Object[] { getMethod("size").invoke(compound, new Object[0]), notCompound });
/*      */         } else {
/*  816 */           getMethod("add").invoke(compound, new Object[] { notCompound });
/*      */         } 
/*  818 */       } else if (lastKey instanceof Integer) {
/*  819 */         if (notCompound == null) {
/*  820 */           getMethod("listRemove").invoke(compound, new Object[] { Integer.valueOf(((Integer)lastKey).intValue()) });
/*      */         } else {
/*  822 */           getMethod("setIndex").invoke(compound, new Object[] { Integer.valueOf(((Integer)lastKey).intValue()), notCompound });
/*      */         }
/*      */       
/*  825 */       } else if (notCompound == null) {
/*  826 */         getMethod("remove").invoke(compound, new Object[] { lastKey });
/*      */       } else {
/*  828 */         getMethod("set").invoke(compound, new Object[] { lastKey, notCompound });
/*      */       }
/*      */     
/*      */     }
/*  832 */     else if (notCompound != null) {
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   private static NBTCompound getNBTTag(Object tag, Object... keys) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
/*  838 */     Object compound = tag;
/*      */     
/*  840 */     for (Object key : keys) {
/*  841 */       if (compound == null)
/*  842 */         return null; 
/*  843 */       if (getNMSClass("NBTTagCompound").isInstance(compound)) {
/*  844 */         compound = getMethod("get").invoke(compound, new Object[] { key });
/*  845 */       } else if (getNMSClass("NBTTagList").isInstance(compound)) {
/*  846 */         compound = ((List)NBTListData.get(compound)).get(((Integer)key).intValue());
/*      */       } 
/*      */     } 
/*  849 */     return new NBTCompound(compound);
/*      */   }
/*      */   
/*      */   private static Object getTag(Object tag, Object... keys) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
/*  853 */     if (keys.length == 0) {
/*  854 */       return getTags(tag);
/*      */     }
/*      */     
/*  857 */     Object notCompound = tag;
/*      */     
/*  859 */     for (Object key : keys) {
/*  860 */       if (notCompound == null)
/*  861 */         return null; 
/*  862 */       if (getNMSClass("NBTTagCompound").isInstance(notCompound)) {
/*  863 */         notCompound = getMethod("get").invoke(notCompound, new Object[] { key });
/*  864 */       } else if (getNMSClass("NBTTagList").isInstance(notCompound)) {
/*  865 */         notCompound = ((List)NBTListData.get(notCompound)).get(((Integer)key).intValue());
/*      */       } else {
/*  867 */         return getNBTVar(notCompound);
/*      */       } 
/*      */     } 
/*  870 */     if (notCompound == null)
/*  871 */       return null; 
/*  872 */     if (getNMSClass("NBTTagList").isInstance(notCompound))
/*  873 */       return getTags(notCompound); 
/*  874 */     if (getNMSClass("NBTTagCompound").isInstance(notCompound)) {
/*  875 */       return getTags(notCompound);
/*      */     }
/*  877 */     return getNBTVar(notCompound);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Object getTags(Object tag) {
/*  883 */     Map<Object, Object> tags = new HashMap<>();
/*      */     try {
/*  885 */       if (getNMSClass("NBTTagCompound").isInstance(tag)) {
/*  886 */         Map<String, Object> tagCompound = (Map<String, Object>)NBTCompoundMap.get(tag);
/*  887 */         for (String key : tagCompound.keySet()) {
/*  888 */           Object value = tagCompound.get(key);
/*  889 */           if (getNMSClass("NBTTagEnd").isInstance(value)) {
/*      */             continue;
/*      */           }
/*  892 */           tags.put(key, getTag(value, new Object[0]));
/*      */         } 
/*  894 */       } else if (getNMSClass("NBTTagList").isInstance(tag)) {
/*  895 */         List<Object> tagList = (List<Object>)NBTListData.get(tag);
/*  896 */         for (int index = 0; index < tagList.size(); index++) {
/*  897 */           Object value = tagList.get(index);
/*  898 */           if (!getNMSClass("NBTTagEnd").isInstance(value))
/*      */           {
/*      */             
/*  901 */             tags.put(Integer.valueOf(index), getTag(value, new Object[0])); } 
/*      */         } 
/*      */       } else {
/*  904 */         return getNBTVar(tag);
/*      */       } 
/*  906 */       return tags;
/*  907 */     } catch (Exception e) {
/*  908 */       e.printStackTrace();
/*  909 */       return tags;
/*      */     } 
/*      */   }
/*      */   
/*      */   public enum MinecraftVersion {
/*  914 */     v1_8("1_8", 0),
/*  915 */     v1_9("1_9", 1),
/*  916 */     v1_10("1_10", 2),
/*  917 */     v1_11("1_11", 3),
/*  918 */     v1_12("1_12", 4),
/*  919 */     v1_13("1_13", 5),
/*  920 */     v1_14("1_14", 6),
/*  921 */     v1_15("1_15", 7),
/*  922 */     v1_16("1_16", 8),
/*  923 */     v1_17("1_17", 9),
/*  924 */     v1_18("1_18", 10),
/*  925 */     v1_19("1_19", 11);
/*      */     
/*      */     private int order;
/*      */     private String key;
/*      */     
/*      */     MinecraftVersion(String key, int v) {
/*  931 */       this.key = key;
/*  932 */       this.order = v;
/*      */     }
/*      */     
/*      */     public static MinecraftVersion get(String v) {
/*  936 */       for (MinecraftVersion k : values()) {
/*  937 */         if (v.contains(k.key)) {
/*  938 */           return k;
/*      */         }
/*      */       } 
/*  941 */       return null;
/*      */     }
/*      */     
/*      */     public boolean greaterThanOrEqualTo(MinecraftVersion other) {
/*  945 */       return (this.order >= other.order);
/*      */     }
/*      */     
/*      */     public boolean lessThanOrEqualTo(MinecraftVersion other) {
/*  949 */       return (this.order <= other.order);
/*      */     }
/*      */   }
/*      */   
/*      */   public static final class NBTCompound {
/*      */     protected final Object tag;
/*      */     
/*      */     protected NBTCompound(@Nonnull Object tag) {
/*  957 */       this.tag = tag;
/*      */     }
/*      */     
/*      */     public static NBTCompound fromJson(String json) {
/*      */       try {
/*  962 */         return new NBTCompound(NBTEditor.getMethod("loadNBTTagCompound").invoke((Object)null, new Object[] { json }));
/*  963 */       } catch (IllegalAccessException|IllegalArgumentException|InvocationTargetException e) {
/*  964 */         e.printStackTrace();
/*  965 */         return null;
/*      */       } 
/*      */     }
/*      */     
/*      */     public void set(Object value, Object... keys) {
/*      */       try {
/*  971 */         NBTEditor.setTag(this.tag, value, keys);
/*  972 */       } catch (Exception e) {
/*  973 */         e.printStackTrace();
/*      */       } 
/*      */     }
/*      */     
/*      */     public String toJson() {
/*  978 */       return this.tag.toString();
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  983 */       return this.tag.toString();
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  988 */       return this.tag.hashCode();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object obj) {
/*  993 */       if (this == obj)
/*  994 */         return true; 
/*  995 */       if (obj == null)
/*  996 */         return false; 
/*  997 */       if (getClass() != obj.getClass())
/*  998 */         return false; 
/*  999 */       NBTCompound other = (NBTCompound)obj;
/* 1000 */       if (this.tag == null) {
/* 1001 */         if (other.tag != null)
/* 1002 */           return false; 
/* 1003 */       } else if (!this.tag.equals(other.tag)) {
/* 1004 */         return false;
/* 1005 */       }  return true;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\Nerotek\Desktop\DeliveryMan.jar!\io\github\Leonardo0013YT\DeliveryMa\\utils\NBTEditor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */