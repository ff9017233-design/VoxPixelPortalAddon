package com.voxpixel.portal;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

public class PortalListener implements Listener {

    private final VoxPixelPortalAddon addon;

    public PortalListener(VoxPixelPortalAddon addon) {
        this.addon = addon;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPortalEnter(PlayerPortalEvent event) {

        if (!event.getFrom().getWorld().getName()
                .equalsIgnoreCase("bskyblock_world")) {
            return;
        }

        World hub = Bukkit.getWorld("vox_hub");

        if (hub == null) {
            addon.logError("VOXPIXEL Portal: vox_hub world is not loaded!");
            event.setCancelled(true);
            return;
        }

        event.setTo(hub.getSpawnLocation());
    }
}
