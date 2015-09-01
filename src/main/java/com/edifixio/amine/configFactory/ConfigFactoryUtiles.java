package com.edifixio.amine.configFactory;

public class ConfigFactoryUtiles {

	public static int inherited(Class<?> superClass, Class<?> subClass) {

		if (subClass.equals(superClass))
			return 0;

		int i = 1;

		while (!subClass.equals(Object.class)) {
			if (subClass.getSuperclass().equals(superClass)) {
				return i;
			}
			i++;
			subClass = subClass.getSuperclass();
		}
		return -1;
	}
}
