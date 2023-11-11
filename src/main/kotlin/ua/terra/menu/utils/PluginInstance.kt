package ua.terra.menu.utils

import org.bukkit.plugin.java.JavaPlugin
import ua.terra.menu.menu.Menu

val Plugin by lazy { JavaPlugin.getProvidingPlugin(Menu::class.java) }