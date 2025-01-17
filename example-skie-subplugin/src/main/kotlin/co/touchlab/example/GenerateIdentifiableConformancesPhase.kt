package co.touchlab.example

import co.touchlab.skie.kir.element.KirProperty
import co.touchlab.skie.phases.SirPhase
import co.touchlab.skie.sir.element.SirClass
import co.touchlab.skie.sir.element.SirExtension
import co.touchlab.skie.sir.element.SirGetter
import co.touchlab.skie.sir.element.SirProperty

object GenerateIdentifiableConformancesPhase : SirPhase {
    context(SirPhase.Context)
    override suspend fun execute() {
        val identifiableProtocol = SirClass(
            kind = SirClass.Kind.Protocol,
            baseName = "Identifiable",
            parent = sirBuiltins.Swift.declarationParent,
            origin = sirBuiltins.Swift.origin
        )

        kirProvider.kotlinClasses.forEach { kirClass ->
            val identifiableConfig = kirClass.configuration[IdentifiableConfigurationKey]
                ?: return@forEach
            val identityProperty = identifiableConfig.propertyName?.let { propertyName ->
                kirClass.callableDeclarations
                    .filterIsInstance<KirProperty>()
                    .singleOrNull {
                        it.kotlinName == propertyName
                    }
                    ?: error("Property $propertyName not found in class ${kirClass.kotlinFqName}!")
            }
            val sirClass = kirClass.primarySirClass
            val sirIdentityProperty = identityProperty?.primarySirProperty

            namespaceProvider.getNamespaceFile(kirClass).apply {
                SirExtension(
                    classDeclaration = sirClass,
                    superTypes = listOf(
                        identifiableProtocol.defaultType
                    )
                ).apply {
                    if (sirIdentityProperty != null) {
                        SirProperty(
                            identifier = "id",
                            type = sirIdentityProperty.type
                        ).apply {
                            SirGetter().apply {
                                bodyBuilder.add {
                                    addStatement("return %L", sirIdentityProperty.identifier)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
