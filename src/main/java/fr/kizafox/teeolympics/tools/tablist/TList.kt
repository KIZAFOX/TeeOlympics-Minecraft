package fr.kizafox.teeolympics.tools.tablist

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TList {
    companion object {
        fun load(player: Audience?) {
            val header: Component = Component.text("§6§lTeeOlympics")
            val footer: Component = Component.text("§8- §7Heure: §a${LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))} §8-")

            player!!.sendPlayerListHeaderAndFooter(header, footer)
        }
    }
}