package fr.kizafox.teeolympics.tools.tablist

import fr.kizafox.teeolympics.core.TeeOlympicsCore
import fr.kizafox.teeolympics.tools.tps.TLag
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt


class TList {
    companion object {
        fun load(instance: TeeOlympicsCore?, player: Player?, audience: Audience?) {
            object : BukkitRunnable() {
                override fun run() {
                    try {
                        val header: Component = Component.text("\n§6§lTeeOlympics\n\n")
                        val footer: Component = Component.text("\n§7Ping: §e${player!!.ping} §8- §7Heure: §a${LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))} §8- §7TPS: §e${(TLag.getTPS() * 100).roundToInt() / 100.0}\n")

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