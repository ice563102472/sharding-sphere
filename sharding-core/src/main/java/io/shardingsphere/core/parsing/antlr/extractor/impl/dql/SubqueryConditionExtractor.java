/*
 * Copyright 2016-2018 shardingsphere.io.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package io.shardingsphere.core.parsing.antlr.extractor.impl.dql;

import com.google.common.base.Optional;
import io.shardingsphere.core.parsing.antlr.extractor.OptionalSQLSegmentExtractor;
import io.shardingsphere.core.parsing.antlr.extractor.impl.FromWhereExtractor;
import io.shardingsphere.core.parsing.antlr.extractor.util.ExtractorUtils;
import io.shardingsphere.core.parsing.antlr.extractor.util.RuleName;
import io.shardingsphere.core.parsing.antlr.sql.segment.FromWhereSegment;
import io.shardingsphere.core.parsing.antlr.sql.segment.condition.SubqueryConditionSegment;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Collection;

/**
 * Subquery condition extractor.
 *
 * @author duhongjun
 */
public final class SubqueryConditionExtractor implements OptionalSQLSegmentExtractor {
    
    @Override
    public Optional<SubqueryConditionSegment> extract(final ParserRuleContext ancestorNode) {
        Collection<ParserRuleContext> suQueryNodes = ExtractorUtils.getAllDescendantNodes(ancestorNode, RuleName.SUBQUERY);
        SubqueryConditionSegment result = new SubqueryConditionSegment();
        FromWhereExtractor fromWhereExtractor = new FromWhereExtractor();
        for (ParserRuleContext each : suQueryNodes) {
            Optional<FromWhereSegment> condition = fromWhereExtractor.extract(each, ancestorNode);
            if (condition.isPresent()) {
                result.getOrConditions().add(condition.get().getConditions());
            }
        }
        return Optional.of(result);
    }
}
