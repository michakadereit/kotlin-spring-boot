package de.mdk.kotlinspringboot.view

import com.vaadin.flow.component.messages.MessageInput
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route
import io.nats.client.Nats
import java.time.Duration

@Route("")
class PublishNatsMessage : VerticalLayout() {

    private val subject: String = "vaadin.input"

    init {
        val natsMessage = MessageInput()
        natsMessage.addSubmitListener { event ->
            publish(event.value)
        }
        add(natsMessage);
    }

    private fun publish(message: String) {
        try {
            Nats.connect().use { nc ->
                nc.publish(subject, message.toByteArray())
                nc.flush(Duration.ofSeconds(5L));
            }
        } catch (exp: Exception) {
            throw IllegalArgumentException(exp)
        }
    }
}