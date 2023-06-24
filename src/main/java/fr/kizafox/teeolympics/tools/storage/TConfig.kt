package fr.kizafox.teeolympics.tools.storage

import org.bukkit.plugin.java.JavaPlugin

class TConfig {
    companion object {
        fun loadDefaultConfig(plugin: JavaPlugin?) {
            plugin!!.config.options().copyDefaults(true)
            plugin.saveConfig()
        }
    }
}