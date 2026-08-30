package com.kokolex.vaadinexamplar.util

import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.GridVariant

fun <T : Any?> Grid<T>.setDefaults() {
    setWidthFull()
    isAllRowsVisible = true
    selectionMode = Grid.SelectionMode.NONE
    applyStripedTheme()
    addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT)
}

fun <T : Any?> Grid<T>.applyStripedTheme() {
    this.addThemeVariants(
        GridVariant.LUMO_WRAP_CELL_CONTENT,
        GridVariant.LUMO_ROW_STRIPES,
        GridVariant.LUMO_COLUMN_BORDERS
    )
}