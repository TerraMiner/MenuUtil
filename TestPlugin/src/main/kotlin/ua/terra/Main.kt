package ua.terra

import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.plugin.java.JavaPlugin
import ua.terra.menu.*
import ua.terra.menu.menu.Menu
import ua.terra.util.command
import ua.terra.util.customName
import ua.terra.util.item

class Main : JavaPlugin() {
    override fun onEnable() {

        command("menu") { player, args ->

            val fillable = buildList {
                repeat(7) {
                    when (it) {
                        0 -> add(
                            menuIcon(it, item(Material.STONE) { customName = "$it" }) {
                                click { _, event ->
                                    event.whoClicked.sendMessage("Привет аболтус, ты нажал на камень на слоту ${slot}. inv: ${event.slot}")
                                    event.isCancelled = true
                                }
                            }
                        )

                        1 -> add(
                            menuIconSlider(0, 20, false, it, item(Material.CAT_SPAWN_EGG) { customName = "$it" }) {
                                click { _, event ->
                                    event.whoClicked.sendMessage("хахха, это главный слайд он автоматический")
                                    event.isCancelled = true
                                }

                                addSlides(
                                    item(Material.EGG) { customName = "$it" }.toMenuIcon {
                                        click { _, event ->
                                            event.whoClicked.sendMessage("хахха, это второй слайд он автоматический")
                                            event.isCancelled = true
                                        }
                                    }, item(Material.DIAMOND) { customName = "$it" }.toMenuIcon {
                                        click { _, event ->
                                            event.whoClicked.sendMessage("хахха, это третий слайд он автоматический")
                                            event.isCancelled = true
                                        }
                                    }
                                )
                            }
                        )

                        2 -> add(
                            menuIconSlider(
                                it, item(Material.SMALL_AMETHYST_BUD) { customName = "§dНажмите, чтобы вырастить кристалл!" },
                            ) {
                                click { _, event ->
                                    event.whoClicked.sendMessage("хахха, это главный слайд, но его можно переключать только вручную")
                                    event.isCancelled = true
                                }

                                times(1, false)

                                addSlides(item(Material.MEDIUM_AMETHYST_BUD) { customName = "§dНажмите, чтобы вырастить кристалл!" }.toMenuIcon {
                                    click { _, event ->
                                        event.whoClicked.sendMessage("хахха, это второй слайд, но его можно переключать только вручную")
                                        event.isCancelled = true
                                    }
                                }, item(Material.LARGE_AMETHYST_BUD) { customName = "§dНажмите, чтобы вырастить кристалл!" }.toMenuIcon {
                                    click { _, event ->
                                        event.whoClicked.sendMessage("хахха, это третий слайд, но его можно переключать только вручную")
                                        event.isCancelled = true
                                    }
                                }, item(Material.AMETHYST_CLUSTER) { customName = "§dНажмите, чтобы раздробить кристалл!" }.toMenuIcon {
                                    click { _, event ->
                                        event.whoClicked.sendMessage("хахха, это третий слайд, но его можно переключать только вручную")
                                        event.isCancelled = true
                                    }
                                }, item(Material.PURPLE_DYE) { customName = "§dНажмите, чтобы что? Вырастить кристалл!" }.toMenuIcon {
                                    click { _, event ->
                                        event.whoClicked.sendMessage("хахха, это третий слайд, но его можно переключать только вручную")
                                        event.isCancelled = true
                                    }
                                })
                            }
                        )

                        3 -> {
                            add(
                                confirmIcon(-1, item(Material.DIAMOND_PICKAXE) {
                                    customName = "§bPickaxe"
                                    editMeta { meta ->
                                        meta.lore(listOf("§eClick to purchase!".toComponent()))
                                    }
                                }, item(Material.LIME_DYE) {
                                    customName = "§aConfirm"
                                    editMeta { meta ->
                                        meta.lore(listOf(
                                            "§aClick LMB to confirm!".toComponent(),
                                            "§cClick RMB to cancel!".toComponent()
                                        ))
                                    }
                                }) { page, event ->
                                    event.whoClicked.sendMessage("You succesfull purchased that!!")
                                }
                            )
                        }

                        4 -> {
                            add(item(Material.REDSTONE) { customName = "§cShows only in creative!" }.toMenuIcon {
                                click { _, event ->
                                    event.whoClicked.sendMessage("You can see this item only in creative.")
                                    event.whoClicked.sendMessage("But you can click and see this text!")
                                    event.isCancelled = true
                                }

                                visibleAccess { page, icon ->
                                    page.menu.viewer.gameMode == GameMode.CREATIVE
                                }
                            })
                        }

                        5 -> {
                            add(item(Material.GLOWSTONE) { customName = "§cClicks only in creative!" }.toMenuIcon {
                                click { _, event ->
                                    event.whoClicked.sendMessage("You can click on this item only in creative.")
                                    event.whoClicked.sendMessage("But you can see this!")
                                    event.isCancelled = true
                                }

                                clickAccess { page, icon ->
                                    page.menu.viewer.gameMode == GameMode.CREATIVE
                                }
                            })
                        }
                        6 -> {
                            add(item(Material.GUNPOWDER) { customName = "§eShows and clicks only in creative!" }.toMenuIcon {
                                click { _, event ->
                                    event.whoClicked.sendMessage("You can click on this item only in creative.")
                                    event.whoClicked.sendMessage("But you can see this!")
                                    event.isCancelled = true
                                }

                                clickAccess { page, icon ->
                                    page.menu.viewer.gameMode == GameMode.CREATIVE
                                }
                            })
                        }
                    }
                }
            }


            Menu("Abeme", 6, player) {
                property {
                    addBottom()
                    addBounds()
                    addTop()
                    addButtons()
                }

                fillable.forEach {
                    addIcon(it)
                }
            }.openMenu()
        }
    }

    override fun onDisable() {

    }
}