package fr.kizafox.teeolympics.tools.logger

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit

class TLogger {
    companion object {
        fun send(message: String?) {
            Bukkit.getConsoleSender().sendMessage(Component.text(message!!))
        }
    }
}