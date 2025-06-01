package vn.flast.utils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class CopyProperty {

	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		return Arrays.stream(src.getPropertyDescriptors())
				.map(pd -> pd.getName())
				.filter(name -> src.getPropertyValue(name) == null)
				.toArray(String[]::new);
	}

	public static void CopyNormal(Object src, Object target) {
		BeanUtils.copyProperties(src, target);
	}

	public static void CopyIgnoreNull(Object src, Object target) {
	    BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
	}

	public static void CopyIgnoreNull(Object src, Object target, String ...ignore) {
		BeanUtils.copyProperties(src, target, ArrayUtils.addAll(getNullPropertyNames(src), ignore));
	}

	public static <T, R> List<R> copyListIgnoreNull(List<T> sourceList, Supplier<R> targetSupplier) {
		return sourceList.stream().map(source -> {
			R target = targetSupplier.get();
			BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
			return target;
		}).collect(Collectors.toList());
	}

}
