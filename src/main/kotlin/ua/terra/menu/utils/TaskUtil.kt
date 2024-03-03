package ua.terra.menu.utils

import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitTask

typealias MenuTask = BukkitTask

fun every(delay: Int, period: Int, action: () -> Unit) =
    Bukkit.getScheduler().runTaskTimer(Plugin, Runnable(action), delay.toLong(), period.toLong())