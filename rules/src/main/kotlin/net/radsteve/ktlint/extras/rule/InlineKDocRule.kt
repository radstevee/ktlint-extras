package net.radsteve.ktlint.extras.rule

import com.pinterest.ktlint.rule.engine.core.api.AutocorrectDecision
import com.pinterest.ktlint.rule.engine.core.api.ElementType
import com.pinterest.ktlint.rule.engine.core.api.children
import com.pinterest.ktlint.rule.engine.core.api.parent
import com.pinterest.ktlint.rule.engine.core.api.remove
import com.pinterest.ktlint.rule.engine.core.api.replaceWith
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement

/** Rule that flattens one-line KDoc comments to inline KDoc comments, like this one. */
public class InlineKDocRule : ExtraRule("inline-kdoc") {
  override fun beforeVisitChildNodes(
    node: ASTNode,
    emit: (Int, String, Boolean) -> AutocorrectDecision,
  ) {
    if (node.elementType != ElementType.KDOC_START) {
      return
    }

    // First up, we need to make sure we're actually in a KDoc node.
    val kdoc = node.parent(ElementType.KDOC) ?: return
    val children = kdoc.children().toList()

    // Then find all the actual lines of documentation.
    val sections = children.filter { child -> child.elementType == ElementType.KDOC_SECTION }

    // If we have multiple or no lines of comments, we skip this.
    if (sections.size > 1 || sections.isEmpty()) {
      return
    }

    var isInline = true

    children.forEachIndexed { idx, child ->
      val previousNode = children.getOrNull(idx - 1)
      val nextNode = children.getOrNull(idx + 1)

      // If there is a whitespace terminator before and after this child,
      // that means that this is **not** an inline KDoc comment.
      if (
        previousNode?.elementType == ElementType.WHITE_SPACE &&
        nextNode?.elementType == ElementType.WHITE_SPACE
      ) {
        isInline = false
      }
    }

    if (isInline) {
      return
    }

    val decision = emit(node.startOffset, "KDoc comment can be inlined onto one line", true)
    if (decision != AutocorrectDecision.ALLOW_AUTOCORRECT) {
      return
    }

    // We first remove all whitespace terminators.
    children
      .filter { child ->
        child.elementType == ElementType.WHITE_SPACE
      }.forEach { whitespace ->
        whitespace.psi.delete()
      }

    val section = sections.first()
    val sectionChild = section.firstChildNode

    // Then we remove the one leading trailing asterisk from the second line.
    sectionChild
      .takeIf { section ->
        section.elementType == ElementType.KDOC_LEADING_ASTERISK
      }
      ?.remove()

    // And append a space onto the end of the doc comment.
    val leaf = sectionChild as LeafPsiElement
    val newLeaf = LeafPsiElement(leaf.elementType, section.lastChildNode.text + " ")
    section.lastChildNode.replaceWith(newLeaf)
  }
}
