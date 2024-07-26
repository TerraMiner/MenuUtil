package ua.terra.menu.utils

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.plugin.java.JavaPlugin
import ua.terra.menu.menu.Menu

val Plugin by lazy { JavaPlugin.getProvidingPlugin(Menu::class.java) }
val MiniSerializer by lazy { MiniMessage.miniMessage() }