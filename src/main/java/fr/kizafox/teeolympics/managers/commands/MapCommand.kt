package fr.kizafox.teeolympics.managers.commands

import fr.kizafox.teeolympics.core.TeeOlympicsCore
import fr.kizafox.teeolympics.managers.commands.handler.SubCommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.awt.Desktop
import java.net.URI

class MapCommand(private var instance: TeeOlympicsCore) : SubCommand("map") {

    fun TeeOlympicsCore(instance: TeeOlympicsCore) {
        this.instance = instance
    }

    override fun perform(sender: CommandSender?, cmd: Command?, label: String?, args: Array<String>?) {
        if (sender !is Player) {
            sender!!.sendMessage(Component.text("§cSeulement un joueur peut utiliser cette commande!"))
            return
        }

        Desktop.getDesktop().browse(URI("${this.instance.getPlugin()!!.pluginMeta.website}"))
        sender.sendMessage(Component.text("§a§oUne page internet vers le site vient d'être chargé!"))
    }
}