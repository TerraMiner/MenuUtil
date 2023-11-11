package ua.terra.menu.property.shape.pattern

import ua.terra.menu.icon.IIcon
import ua.terra.menu.property.PageProperty
import ua.terra.menu.property.shape.IPageShape

class PatternPageShape(override val property: PageProperty) : IPageShape {

    private val aliasMap = mutableMapOf<Char, IIcon>()

    private val pattern = mutableListOf<String>()

    fun alias(key: Char, value: IIcon): PatternPageShape {
        aliasMap[key] = value
        return this
    }

    fun alias(key: String, value: IIcon): PatternPageShape {
        return alias(key[0],value)
    }

    fun pattern(vararg row: String) {
        pattern.clear()
        pattern.addAll(row)
    }

    fun pattern(rows: Collection<String>) {
        pattern(*rows.toTypedArray())
    }

    override fun setup() {
        pattern.forEachIndexed y@{ y, row ->
            if (y >= property.menu.sizeY) return@y
            if (row.isEmpty()) return@y
            row.forEachIndexed x@{ x, key ->
                if (x >= property.menu.sizeX) return@y
                property.setIcon(property.getIndex(x, y),aliasMap[key] ?: return@x)
            }
        }
    }

    init {
        alias("<",property.prevButton.icon)
        alias(">",property.nextButton.icon)
    }

}