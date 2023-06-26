package fr.kizafox.teeolympics.tools.tablist

import fr.kizafox.teeolympics.core.TeeOlympicsCore
import fr.kizafox.teeolympics.tools.tps.TLag
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class TList {
    companion object {
        fun load(instance: TeeOlympicsCore?, player: Player?, audience: Audience?) {
            object : BukkitRunnable() {
                override fun run() {
                    try {
                        val header: Component = Component.text("§6§lTeeOlympics")
                        val footer: Component = Component.text("§7Ping: §e${player!!.ping} §8- §7Heure: §a${LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))} §8- §7TPS: §e${TLag.getLag()}")

                        audience!!.sendPlayerListHeaderAndFooter(header, footer)
                    } catch (e: NoSuchFieldException) {
                        e.printStackTrace()
                    } catch (e: IllegalAccessException) {
                        e.printStackTrace()
                    }
                }
            }.runTaskTimer(instance!!.getPlugin()!!, 0, 20)
        }
    }
}