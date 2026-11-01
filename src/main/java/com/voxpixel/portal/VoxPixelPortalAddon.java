package com.voxpixel.portal;

import world.bentobox.bentobox.api.addons.Addon;

public class VoxPixelPortalAddon extends Addon {

    @Override
    public void onEnable() {
        registerListener(new PortalListener(this));
        log("VOXPIXEL Portal Addon enabled!");
    }

    @Override
    public void onDisable() {
        log("VOXPIXEL Portal Addon disabled!");
    }
}
