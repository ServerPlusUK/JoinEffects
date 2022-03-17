package uk.serverplus.joineffects;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class JoinEffects extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        Bukkit.getScheduler().runTaskLater(this, () -> {
            boolean titleEnabled = getConfig().getBoolean("title.enabled", false);
            if (titleEnabled) {
                try {
                    String title = getConfig().getString("title.title", "Failed to load");
                    String subtitle = getConfig().getString("title.subtitle", "Check console?");

                    long fadeIn = getConfig().getLong("title.fadeIn", 1000);
                    long stay = getConfig().getLong("title.stay", 2000);
                    long fadeOut = getConfig().getLong("title.fadeOut", 1000);

                    TitleUtil.send(player, title, subtitle, fadeIn, stay, fadeOut);
                } catch (Exception error) {
                    error.printStackTrace();
                }
            }

            boolean soundEnabled = getConfig().getBoolean("sound.enabled", false);
            if (soundEnabled) {
                Sound sound = Sound.valueOf(getConfig().getString("sound.id", "ENTITY_VILLAGER_NO"));
                player.playSound(player.getLocation(), sound, 1, 1);

                boolean chain = getConfig().getBoolean("sound.chain.enabled", false);
                if (chain) {
                    long delaySeconds = getConfig().getLong("sound.chain.delay") / 1000L;
                    Sound chainSound = Sound.valueOf(getConfig().getString("sound.chain.id", "ENTITY_VILLAGER_NO"));
                    Bukkit.getScheduler().runTaskLater(this, () -> {
                        if (player == null) player.playSound(player.getLocation(), chainSound, 1, 1);
                    }, delaySeconds * 20);
                }
            }
        }, 10);
    }

}
