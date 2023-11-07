package ua.terra.menu.page

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import ua.terra.menu.Task
import ua.terra.menu.event.MenuClickEvent
import ua.terra.menu.icon.IIcon
import ua.terra.menu.menu.IMenu
import ua.terra.menu.menuIcon
import ua.terra.menu.updater.IconUpdater

interface IPage {
    val index: Int
    val menu: IMenu
    val icons: MutableMap<Int, IIcon>

    val inventory: Inventory

    val emptySlots: MutableList<Int>

    val dynamicItems: MutableSet<IconUpdater>

    val updater: Task

    fun InventoryClickEvent.clickEvent() {
        if (whoClicked !is Player) return
        if (this@IPage.inventory !== clickedInventory) return
        val icon = icons[slot] ?: return
        val event = MenuClickEvent(
            menu,
            icon,
            inventory,
            whoClicked as Player,
            click,
            action,
            cursor,
            slotType,
            slot
        )
        event.callEvent()

        if (!icon.accessor.clickValid(this@IPage,event)){
            isCancelled = true
            return
        }

        icon.clicks.forEach {
            it(this@IPage, event)
        }
        isCancelled = event.isCancelled
    }

    fun update() {
        icons.forEach { (index, icon) ->
            if (!icon.accessor.visionValid(this, icon)) {
                inventory.setItem(index, null)
            } else {
                inventory.setItem(index, icon.stack)
            }
        }
    }

    fun update(index: Int) {
        val icon = icons[index]
        if (icon?.accessor?.visionValid(this, icon) != true) {
            inventory.setItem(index, null)
        } else {
            inventory.setItem(index, icon.stack)
        }
    }

    fun addIcon(
        stack: ItemStack,
        updater: IconUpdater? = null,
        event: (IPage, MenuClickEvent) -> Unit = { _, e -> e.isCancelled = true }
    ) {
        addIcon(menuIcon(-1, stack) { click(event); addUpdater(updater) })
    }

    fun addIcon(icon: IIcon) {
        val emptySlot = emptySlots.sorted()
            .getOrElse(0) { throw ArrayIndexOutOfBoundsException("That menu page is already fully!") }
        setIcon(emptySlot, icon)
    }

    fun setIcon(index: Int, icon: IIcon) {
        icon.slot = index
        icons[index] = icon

        icons[index]?.iconUpdaters?.forEach {
            dynamicItems.add(it)
        }

        if (icon.stack.amount > 0) emptySlots.remove(index)
        else if (!emptySlots.contains(index)) emptySlots.add(index)
    }

    fun replaceIcon(index: Int, icon: IIcon) {
        icons[index]?.iconUpdaters?.forEach {
            dynamicItems.remove(it)
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
    ) {
        setIcon(menuIcon(index, stack) { click(event); addUpdater(updater) })
    }

    fun getIcon(index: Int) = icons[index]

    fun fillItem(stack: ItemStack, vararg slots: Int) {
        slots.forEach {
            setIcon(it, stack)
        }
    }

    fun fillItem(stack: ItemStack, range: IntRange) {
        fillItem(stack, *range.toList().toIntArray())
    }

    fun hasNextPage(): Boolean
    fun hasPrevPage(): Boolean
}