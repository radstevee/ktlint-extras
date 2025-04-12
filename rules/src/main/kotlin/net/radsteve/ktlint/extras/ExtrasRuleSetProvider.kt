package net.radsteve.ktlint.extras

import com.pinterest.ktlint.cli.ruleset.core.api.RuleSetProviderV3
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider
import com.pinterest.ktlint.rule.engine.core.api.RuleSetId
import net.radsteve.ktlint.extras.rule.InlineKDocRule
import net.radsteve.ktlint.extras.rule.NoImplicitItRule

public class ExtrasRuleSetProvider : RuleSetProviderV3(RuleSetId(ID)) {
  override fun getRuleProviders(): Set<RuleProvider> {
    return setOf(
      RuleProvider(::NoImplicitItRule),
      RuleProvider(::InlineKDocRule),
    )
  }
}
