package fr.kizafox.teeolympics.tools.tablist

import fr.kizafox.teeolympics.core.TeeOlympicsCore
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class TList {
    companion object {
        fun load(instance: TeeOlympicsCore?, player: Audience?) {
            object : BukkitRunnable() {
                override fun run() {
                    try {
                        val header: Component = Component.text("§6§lTeeOlympics")
                        val footer: Component = Component.text("§8§k-§r §7Heure: §a${LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))} §8§k-")

                        player!!.sendPlayerListHeaderAndFooter(header, footer)
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