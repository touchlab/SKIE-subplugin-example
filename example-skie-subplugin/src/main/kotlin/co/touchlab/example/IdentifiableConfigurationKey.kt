package co.touchlab.example

import co.touchlab.skie.configuration.ConfigurationKey
import co.touchlab.skie.configuration.ConfigurationScope
import co.touchlab.skie.configuration.ConfigurationTarget
import co.touchlab.skie.configuration.findAnnotation

object IdentifiableConfigurationKey: ConfigurationKey<IdentifiableConfig?>, ConfigurationScope.Class {
    override fun deserialize(value: String?): IdentifiableConfig? {
        return when (value) {
            null -> null
            Identifiable.SELF_IDENTITY -> IdentifiableConfig(null)
            else -> IdentifiableConfig(value)
        }
    }

    override fun serialize(value: IdentifiableConfig?): String? {
        return when {
            value == null -> null
            value.propertyName == null -> Identifiable.SELF_IDENTITY
            else -> value.propertyName
        }
    }

    override val defaultValue: IdentifiableConfig? = null

    override fun hasAnnotationValue(configurationTarget: ConfigurationTarget): Boolean =
        configurationTarget.findAnnotation<Identifiable>() != null

    override fun getAnnotationValue(configurationTarget: ConfigurationTarget): IdentifiableConfig? =
        configurationTarget.findAnnotation<Identifiable>()?.let {
            deserialize(it.property)
        }
}
