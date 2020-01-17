package org.cisco.ciscoutil.cu.config.condition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.cisco.ciscoutil.cu.util.CommonSpringConditionUtil;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class JavaMelodyConfigurationCondition implements Condition {
	private static Logger LOGGER = LoggerFactory.getLogger(JavaMelodyConfigurationCondition.class);

	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		LOGGER.info("creating java melody configuration condition...");
		return CommonSpringConditionUtil.isConditionSatisfied(context, "java.melody.disabled", true) ? false : true;
	}
}
