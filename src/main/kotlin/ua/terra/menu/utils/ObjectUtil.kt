package ua.terra.menu.utils

inline fun <reified C> Any?.safeCast() = this as? C