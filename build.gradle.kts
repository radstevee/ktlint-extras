plugins {
  alias(libs.plugins.kotlin)
  alias(libs.plugins.ktlint)
}

allprojects {
  version = rootProject.findProperty("version")!!
  group = "net.radsteve.ktlint.extras"

  repositories {
    mavenCentral()
  }

  apply(plugin = "kotlin")
  apply(plugin = "org.jlleitschuh.gradle.ktlint")

  kotlin {
    jvmToolchain(21)
    explicitApi()
  }

  ktlint {
    version.set(rootProject.libs.versions.ktlint.asProvider())

    additionalEditorconfig.set(
      mapOf(
        "insert_final_newline" to "true",
        "end_of_line" to "lf",
        "indent_size" to "2",
        "indent_style" to "space",
        "max_line_length" to "off",
        "ktlint_function_signature_body_expression_wrapping" to "default",
        "ktlint_code_style" to "intellij_idea",
        "ktlint_experimental" to "enabled",
        "ktlint_standard_multiline-expression-wrapping" to "disabled",
        "ktlint_standard_property-wrapping" to "disabled",
        "ktlint_standard_condition-wrapping" to "disabled",
        "ktlint_standard_function-expression-body" to "disabled",
        "ktlint_standard_if-else-bracing" to "enabled"
      )
    )
  }
}
