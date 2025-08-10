package vn.flast.utils;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapUtils {
    private MapUtils() {}
    public static <T, K> Map<K, T> toIdentityMap(Collection<T> collection, Function<T, K> keyMapper) {
        return collection.stream()
        .collect(Collectors.toMap(keyMapper, Function.identity()));
    }

    public static <T, K, V> Map<K, V> mapKeyValue(
        Collection<T> collection,
        Function<T, K> keyMapper,
        Function<T, V> valueMapper
    ) {
        return collection.stream().collect(Collectors.toMap(keyMapper, valueMapper));
    }
}
