package net.radsteve.ktlint.extras.rule

import com.pinterest.ktlint.rule.engine.core.api.AutocorrectDecision
import com.pinterest.ktlint.rule.engine.core.api.ElementType
import com.pinterest.ktlint.rule.engine.core.api.children
import com.pinterest.ktlint.rule.engine.core.api.parent
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * Rule that filters out implicit 'it' value parameters.
 * This rule only emits a style violation if the parameter
 * is actually used. Unused parameters will do nothing.
 * This rule will not emit a style violation if the parameter
 * name is explicitly set to 'it'.
 *
 * This rule is **not** auto-correctable.
 */
public class NoImplicitItRule : ExtraRule("no-implicit-it") {
  override fun beforeVisitChildNodes(
    node: ASTNode,
    emit: (offset: Int, canCorrect: String, canBeAutoCorrected: Boolean) -> AutocorrectDecision,
  ) {
    // We only search for 'it' parameters that are actually used.
    if (node.elementType != ElementType.REFERENCE_EXPRESSION) {
      return
    }

    // Find the function literal we're in.
    val fn = node.parent(ElementType.FUNCTION_LITERAL) ?: return

    // We only search for value parameters, not calls.
    if (node.treeParent.elementType == ElementType.CALL_EXPRESSION) {
      return
    }

    val params = fn.findChildByType(ElementType.VALUE_PARAMETER_LIST)?.children() ?: emptySequence()

    // We only search for implicit 'it' values, this is explicit.
    if (params.any { node -> node.text == "it" }) {
      return
    }

    // Now we make sure it's actually named 'it'.
    if (node.text == "it") {
      emit(node.startOffset, "Implicit 'it' used", false)
    }
  }
}
