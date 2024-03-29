package ua.terra.menu.page

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import ua.terra.menu.event.MenuClickEvent
import ua.terra.menu.icon.IIcon
import ua.terra.menu.icon.functional.IFuncIcon
import ua.terra.menu.menu.IMenu
import ua.terra.menu.updater.IconUpdater
import ua.terra.menu.utils.MenuTask
import ua.terra.menu.utils.funcIcon
import ua.terra.menu.utils.menuIcon
import ua.terra.menu.utils.safeCast

interface IPage : InventoryHolder {
    val index: Int
    val menu: IMenu
    val icons: MutableMap<Int, IIcon>

    val window: Inventory

    val emptySlots: MutableList<Int>

    val dynamicItems: MutableSet<IconUpdater>

    var updater: MenuTask

    var allowedClicksInMainInventory: Boolean

    override fun getInventory() = window

    fun InventoryClickEvent.clickEvent() {
        val player = whoClicked.safeCast<Player>() ?: return

        val page = clickedInventory?.holder?.safeCast<IPage>() ?: return

        if (page.index != index) return

        val icon = icons[slot]?.safeCast<IFuncIcon>() ?: return

        val event = MenuClickEvent(menu, page, icon, player, click, action, cursor, slotType, slot)

        Bukkit.getPluginManager().callEvent(event)

        isCancelled = event.isCancelled
    }

    fun update() {
        icons.keys.forEach {
            update(it)
        }
    }

    fun safetyUpdate() {
        icons.keys.forEach {
            safetyUpdate(it)
        }
    }

    fun safetyUpdate(index: Int) {
        icons[index]?.safeCast<IFuncIcon>()?.also {
            val check = it.accessor.visionValid(this, it)

            if (!it.accessor.isNeedUpdate) return

            val newStack = if (check) it.stack else it.accessor.hide

            inventory.setItem(index, newStack)
        }
    }

    fun update(index: Int) {
        val icon = icons[index]

        val newStack = icon?.let {
            val check = it.safeCast<IFuncIcon>()?.accessor?.visionValid(this, it) ?: true
            if (check) it.stack else null
        }
        inventory.setItem(index, newStack)
    }

    fun addIcon(
        stack: ItemStack,
        updater: IconUpdater? = null,
        event: (IPage, MenuClickEvent) -> Unit = { _, e -> e.isCancelled = true }
    ) {
        addIcon(funcIcon(-1, stack) { click(event); addUpdater(updater) })
    }

    fun addIcon(stack: ItemStack) {
        addIcon(menuIcon(-1, stack))
    }

    fun addIcon(icon: IIcon) {
        val emptySlot = emptySlots.sorted()
            .getOrElse(0) { throw ArrayIndexOutOfBoundsException("That menu page is already fully!") }
        setIcon(emptySlot, icon)
    }

    fun setIcon(index: Int, icon: IIcon) {

        icon.slot = index
        icons[index] = icon

        icon.safeCast<IFuncIcon>()?.iconUpdaters?.forEach {
            addUpdater(it)
        }

        if (icon.stack.amount > 0) emptySlots.remove(index)
        else if (!emptySlots.contains(index)) emptySlots.add(index)

        icon.afterSetup()
    }

    fun replaceIcon(index: Int, icon: IIcon) {
        icons[index]?.safeCast<IFuncIcon>()?.iconUpdaters?.forEach {
            removeUpdater(it)
        }
        icons.remove(icon.slot)
        if (!emptySlots.contains(index)) emptySlots.add(index)
        setIcon(index, icon)
    }

    fun replaceIcon(icon: IIcon) {
        replaceIcon(icon.slot, icon)
    }

    fun setIcon(icon: IIcon) {
        setIcon(icon.slot, icon)
    }

    fun setIcon(
        index: Int, stack: ItemStack,
        updater: IconUpdater? = null,
        event: (IPage, MenuClickEvent) -> Unit = { _, e -> e.isCancelled = true }
    ) { setIcon(funcIcon(index, stack) { click(event); addUpdater(updater) }) }

    fun setIcon(
        index: Int, stack: ItemStack,
    ) { setIcon(menuIcon(index, stack)) }

    fun getIcon(index: Int) = icons[index]

    fun addUpdater(iconUpdater: IconUpdater): Boolean {
        return dynamicItems.add(iconUpdater)
    }


    fun removeUpdater(iconUpdater: IconUpdater): Boolean {
        return dynamicItems.remove(iconUpdater)
    }


    fun fillItem(stack: ItemStack, vararg slots: Int) {
        slots.forEach {
            setIcon(it, stack)
        }
    }

    fun fillItem(stack: ItemStack, range: IntRange) {
        fillItem(stack, *range.toList().toIntArray())
    }

    fun hasNextPage() = index < menu.pageCount - 1

    fun hasPrevPage() = index > 0
}