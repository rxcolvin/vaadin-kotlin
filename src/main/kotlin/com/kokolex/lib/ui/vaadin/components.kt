package com.kokolex.lib.ui.vaadin

import com.github.mvysny.karibudsl.v10.KComposite
import com.github.mvysny.karibudsl.v10.VaadinDsl
import com.github.mvysny.karibudsl.v10.init
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.textfield.EmailField
import com.vaadin.flow.component.textfield.TextArea
import com.vaadin.flow.component.textfield.TextField


@VaadinDsl
fun (@VaadinDsl HasComponents).name_UI(label: String? = null, block: (@VaadinDsl TextField).() -> Unit = {}): TextField
        = init(TextField(label), block)


@VaadinDsl
fun (@VaadinDsl HasComponents).email_UI(label: String? = null, block: (@VaadinDsl EmailField).() -> Unit = {}): EmailField
        = init(EmailField(label), block)

@VaadinDsl
fun (@VaadinDsl HasComponents).notes_UI(label: String? = null, block: (@VaadinDsl TextArea).() -> Unit = {}): TextArea
        = init(TextArea(label), block)

@VaadinDsl
public fun (@VaadinDsl HasComponents).localDate_UI(label: String? = null, block: (@VaadinDsl DatePicker).() -> Unit = {}): DatePicker
        = init(DatePicker(label), block)

