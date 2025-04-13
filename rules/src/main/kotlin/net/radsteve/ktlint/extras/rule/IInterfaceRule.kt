package net.radsteve.ktlint.extras.rule

import com.pinterest.ktlint.rule.engine.core.api.AutocorrectDecision
import com.pinterest.ktlint.rule.engine.core.api.ElementType
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/** Rule that emits a style violation when an interface declaration is prefixed with 'I'. */
public class IInterfaceRule : ExtraRule("no-i-interface") {
  override fun beforeVisitChildNodes(
    node: ASTNode,
    emit: (Int, String, Boolean) -> AutocorrectDecision,
  ) {
    // Interfaces are just classes in the AST.
    if (node.elementType != ElementType.CLASS) {
      return
    }

    val ident = node.findChildByType(ElementType.IDENTIFIER) ?: return
    val name = ident.text

    if (name.getOrNull(0) == 'I' && name.getOrNull(1)?.isUpperCase() == true) {
      emit(node.startOffset, "Interface name prefixed with 'I'. " +
              "Consider coming up with a better name or suppressing this", false)
    }
  }
}
