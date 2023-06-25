package fr.kizafox.teeolympics.managers.commands

import fr.kizafox.teeolympics.core.TeeOlympicsCore
import fr.kizafox.teeolympics.managers.commands.handler.SubCommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.TextColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class MapCommand(private var instance: TeeOlympicsCore) : SubCommand("map") {

    fun TeeOlympicsCore(instance: TeeOlympicsCore) {
        this.instance = instance
    }

    override fun perform(sender: CommandSender?, cmd: Command?, label: String?, args: Array<String>?) {
        if (sender !is Player) {
            sender!!.sendMessage(Component.text("§cSeulement un joueur peut utiliser cette commande!"))
            return
        }

        val component: TextComponent = Component.text()
            .content("${this.instance.getPlugin()!!.pluginMeta.website}")
            .color(TextColor.color(0x18E1AC))
            .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, "${this.instance.getPlugin()!!.pluginMeta.website}"))
            .build()

        sender.sendMessage(component)
    }
}