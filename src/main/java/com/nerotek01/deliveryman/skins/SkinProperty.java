package com.nerotek01.deliveryman.skins;

public class SkinProperty {

    private final String name;
    public final String value;
    public final String signature;

    public SkinProperty(String value, String signature) {
        this("textures", value, signature);
    }

    public SkinProperty(String name, String value, String signature) {
        this.name = name;
        this.value = value;
        this.signature = signature;
    }

    public String getName() { return name; }
    public String getSignature() { return signature; }
    public String getValue() { return value; }
}
