package uk.serverplus.joineffects;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;

import java.time.Duration;

public class TitleUtil {

    public static void send(Player player, String title, String subTitle) {
        player.showTitle(Title.title(translate(title), translate(subTitle)));
    }

    public static void send(Player player, String title, String subTitle, long fadeIn, long display, long fadeOut) {
        player.showTitle(Title.title(translate(title), translate(subTitle), Title.Times.of(Duration.ofMillis(fadeIn), Duration.ofMillis(display), Duration.ofMillis(fadeOut))));
    }

    public static void clear(Player player) {
        send(player, "&r", "&r");
    }

    public static Component translate(String raw) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(raw);
    }

}
