package net.radsteve.ktlint.extras.rule

import com.pinterest.ktlint.rule.engine.core.api.Rule
import com.pinterest.ktlint.rule.engine.core.api.RuleAutocorrectApproveHandler
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import net.radsteve.ktlint.extras.ID

public abstract class ExtraRule(
  name: String,
) : Rule(
  ruleId = RuleId("$ID:$name"),
  about = About(
    maintainer = "Nils Krüger (radstevee)",
    repositoryUrl = "https://github.com/radstevee/ktlint-extras",
    issueTrackerUrl = "https://github.com/radstevee/ktlint-extras/issues",
  ),
),
  RuleAutocorrectApproveHandler
