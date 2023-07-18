package de.mdk.kotlinspringboot

import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route

@Route
class View : VerticalLayout() {
    init {
        add(DatePicker("Start date"));
    }
}