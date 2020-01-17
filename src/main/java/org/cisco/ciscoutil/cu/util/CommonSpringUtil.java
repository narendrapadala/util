package org.cisco.ciscoutil.cu.util;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Component
public class CommonSpringUtil implements ApplicationListener<ContextRefreshedEvent> {
	private static Logger LOGGER = LoggerFactory.getLogger(CommonSpringUtil.class);

	private Map<String, List<Class<?>>> annotationTypeNameToBeansMap = Maps.newHashMap();
	private Map<String, List<Class<?>>> annotationTypeNameToClassesMap = Maps.newHashMap();

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private CommonUtilProperties properties;


	public void onApplicationEvent(ContextRefreshedEvent event) {
		LOGGER.info("Context initialization complete in spring util...");
		setAnnotationTypeNameToBeansMap();
		setAnnotationTypeNameToClassesMap();
	}

	public <T extends Annotation> Map<String, T> getBeanNameToAnnotationMap(Class<T> annotation) {
//		LOGGER.info("Get bean name to annotation map from annotated class: " + annotation.getSimpleName());
		validateAnnotation(annotation);
		Map<String, T> response = Maps.newHashMap();
		String annotationName = annotation.getName();
		List<Class<?>> beans = annotationTypeNameToBeansMap.get(annotationName);
		if (CollectionUtils.isEmpty(beans)) {
			throw new IllegalStateException("No beans found for annotation: " + annotationName);
		}
		for (Class<?> obj : beans) {
			response.put(firstCharacterSmall(obj.getSimpleName()), obj.getAnnotation(annotation));
		}
		return response;
	}

	public <T extends Annotation> Map<Class<?>, T> getClassToAnnotationMap(Class<T> annotation)
			throws ClassNotFoundException, IOException {
//		LOGGER.info("Get class(non-bean) name to annotation map from annotated class: " + annotation.getSimpleName());
		validateAnnotation(annotation);
		Map<Class<?>, T> response = Maps.newHashMap();
		String annotationName = annotation.getName();
		List<Class<?>> classes = annotationTypeNameToClassesMap.get(annotationName);
		if (CollectionUtils.isEmpty(classes)) {
			throw new IllegalStateException("No classes found for annotation: " + annotationName);
		}
		for (Class<?> obj : classes) {
			response.put(obj, obj.getAnnotation(annotation));
		}
		return response;
	}

	public Class<?> getBeanClass(String beanName) {
//		LOGGER.info("Get bean class from bean name...");
		validateBeanName(beanName);
		return appContext.getBean(firstCharacterSmall(beanName)).getClass();
	}

	private void validateBeanName(String beanName) {
		if (StringUtils.isEmpty(beanName)) {
			throw new IllegalArgumentException("Bean name can't be empty!");
		}
	}

	private void setAnnotationTypeNameToClassesMap() {
		String basePackageName = properties.getBasePackageName();
		Class<?>[] classes;
		try {
			classes = getClasses(basePackageName);
			for (Class<?> class1 : classes) {
				for (Annotation annotation : class1.getAnnotations()) {
					String annotationTypeName = annotation.annotationType().getName();
					List<Class<?>> annotatedClasses = annotationTypeNameToClassesMap.get(annotationTypeName);
					annotatedClasses = (List<Class<?>>) (CollectionUtils.isEmpty(annotatedClasses) ? Lists.newArrayList()
							: annotatedClasses);
					annotatedClasses.add(class1);
					annotationTypeNameToClassesMap.put(annotationTypeName, annotatedClasses);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error occured while creating annotationTypeNameToClassesMap...", e);
			throw new IllegalStateException(e);
		}
	}

	private void setAnnotationTypeNameToBeansMap() {
		String[] beanNames = appContext.getBeanDefinitionNames();
		for (String beanName : beanNames) {
			Object obj = appContext.getBean(beanName);
			if (obj != null) { // in cases where beans are created via @Bean annotation, obj will be null
				Class<?> objClazz = obj.getClass();
				if (AopUtils.isAopProxy(obj)) {
					objClazz = AopUtils.getTargetClass(obj);
					for (Annotation annotation : objClazz.getAnnotations()) {
						String annotationType = annotation.annotationType().getName();
						List<Class<?>> annotatedBeans = annotationTypeNameToBeansMap.get(annotationType);
						annotatedBeans = (List<Class<?>>) (CollectionUtils.isEmpty(annotatedBeans) ? Lists.newArrayList()
								: annotatedBeans);
						annotatedBeans.add(objClazz);
						annotationTypeNameToBeansMap.put(annotationType, annotatedBeans);
					}
				}
			}
		}
	}

	private Class<?>[] getClasses(String basePackageName) {
		List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
		classLoadersList.add(ClasspathHelper.contextClassLoader());
		classLoadersList.add(ClasspathHelper.staticClassLoader());

		Reflections reflections = new Reflections(basePackageName, new SubTypesScanner(false));
		Set<Class<? extends Object>> classes = reflections.getSubTypesOf(Object.class);
		return classes.toArray(new Class[classes.size()]);
	}

	public String firstCharacterSmall(String simpleName) {
		return simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
	}

	private <T> void validateAnnotation(Class<T> annotation) {
		if (annotation == null) {
			throw new IllegalArgumentException("annotation can't be empty!");
		}
	}
}
