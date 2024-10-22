package vn.flast.utils;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class CopyProperty {

	public static String[] getNullPropertyNames (Object source) {
	    final BeanWrapper src = new BeanWrapperImpl(source);
	    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
	    Set<String> emptyNames = new HashSet<>();
	    for(java.beans.PropertyDescriptor pd : pds) {
	        Object srcValue = src.getPropertyValue(pd.getName());
	        if (srcValue == null){
				emptyNames.add(pd.getName());
			}
	    }
	    String[] result = new String[emptyNames.size()];
	    return emptyNames.toArray(result);
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
}
