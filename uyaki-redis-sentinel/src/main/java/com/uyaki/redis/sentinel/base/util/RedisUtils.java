package com.uyaki.redis.sentinel.base.util;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author uyaki
 * @date 2020/07/05
 */
@Component
public class RedisUtils {
    /**
     * 默认redisTemplate
     * 序列化 - Jackson2JsonRedisSerializer
     */
    private static RedisTemplate<String, Object> redisTemplate;

    /**
     * Sets redis template.
     *
     * @param redisTemplate the redis template
     */
    @Resource
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        RedisUtils.redisTemplate = redisTemplate;
    }

    /**
     * Pipeline list.
     *
     * @param consumer the consumer
     * @return the list
     */
    public static List<Object> pipeline(Consumer<RedisConnection> consumer) {
        return redisTemplate.executePipelined((RedisCallback<?>) (redisConnection) -> {
            redisConnection.openPipeline();
            consumer.accept(redisConnection);
            return null;
        });
    }
    /** -------------------key 相关操作 --------------------- */
    /**
     * 检查redis内是否存在指定key
     *
     * @param key key
     * @return 是否存在 boolean
     */
    public static Boolean exists(String key) {
        return Optional.ofNullable(redisTemplate.hasKey(key)).orElse(false);
    }

    /**
     * 删除 key
     *
     * @param key the key
     */
    public static void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 批量删除 key
     *
     * @param keys the keys
     */
    public static void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 序列化 key
     *
     * @param key the key
     * @return byte [ ]
     */
    public static byte[] dump(String key) {
        return redisTemplate.dump(key);
    }


    /**
     * 设置过期日期
     *
     * @param key  the key
     * @param date the date
     * @return boolean boolean
     */
    public static Boolean expireAt(String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }

    /**
     * 设置过期时间
     *
     * @param key      the key
     * @param timeout  the timeout
     * @param timeUnit the time unit
     * @return boolean boolean
     */
    public static Boolean expire(String key, long timeout, TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * 查找匹配的 key
     *
     * @param pattern the pattern
     * @return set set
     */
    public static Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 将当前数据库的 key 移动到给定的数据库 db 当中
     *
     * @param key     the key
     * @param dbIndex the db index
     * @return boolean boolean
     */
    public static Boolean move(String key, int dbIndex) {
        return redisTemplate.move(key, dbIndex);
    }

    /**
     * 移除 key 的过期时间，key 将持久保持
     *
     * @param key the key
     * @return boolean boolean
     */
    public static Boolean persist(String key) {
        return redisTemplate.persist(key);
    }

    /**
     * 返回 key 的剩余的过期时间
     *
     * @param key  the key
     * @param unit the unit
     * @return expire expire
     */
    public static Long getExpire(String key, TimeUnit unit) {
        return redisTemplate.getExpire(key, unit);
    }

    /**
     * 返回 key 的剩余的过期时间
     *
     * @param key the key
     * @return expire expire
     */
    public static Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 从当前数据库中随机返回一个 key
     *
     * @return string string
     */
    public static String randomKey() {
        return redisTemplate.randomKey();
    }

    /**
     * 修改 key 的名称
     *
     * @param oldKey the old key
     * @param newKey the new key
     */
    public static void rename(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * 仅当 newkey 不存在时，将 oldKey 改名为 newkey
     *
     * @param oldKey the old key
     * @param newKey the new key
     * @return boolean boolean
     */
    public static Boolean renameIfAbsent(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    /**
     * 返回 key 所储存的值的类型
     *
     * @param key the key
     * @return data type
     */
    public static DataType type(String key) {
        return redisTemplate.type(key);
    }


    /**- - - - - - - - - - - - - - - - - - - - -  String - - - - - - - - - - - - - - - - - - - -*/

    /**
     * 保存数据到redis
     *
     * @param key    key
     * @param object val
     */
    public static void set(String key, Object object) {
        redisTemplate.opsForValue().set(key, object);
    }

    /**
     * 保存数据到redis并设置过期时间（单位毫秒），自定义日期格式请使用{@link #set(String, Object, long, TimeUnit)}
     *
     * @param key          key
     * @param object       val
     * @param timeoutMilli 过期时间毫秒数
     */
    public static void set(String key, Object object, long timeoutMilli) {
        redisTemplate.opsForValue().set(key, object, timeoutMilli);
    }

    /**
     * 保存数据到redis并设置过期时间
     *
     * @param key      key
     * @param object   val
     * @param timeout  过期时间
     * @param timeUnit 过期时间单位
     */
    public static void set(String key, Object object, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, object, timeout, timeUnit);
    }

    /**
     * 返回 key 中字符串值的子字符
     *
     * @param key   the key
     * @param start the start
     * @param end   the end
     * @return range range
     */
    public static String getRange(String key, long start, long end) {
        return redisTemplate.opsForValue().get(key, start, end);
    }

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值 (old value)
     *
     * @param key   the key
     * @param value the value
     * @return and set
     */
    public static Object getAndSet(String key, String value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * 对 key 所储存的字符串值，获取指定偏移量上的位 (bit)
     *
     * @param key    the key
     * @param offset the offset
     * @return bit bit
     */
    public static Boolean getBit(String key, long offset) {
        return redisTemplate.opsForValue().getBit(key, offset);
    }

    /**
     * 批量获取
     *
     * @param keys the keys
     * @return list list
     */
    public static List<Object> multiGet(Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 设置 ASCII 码，字符串 'a' 的 ASCII 码是 97, 转为二进制是 '01100001', 此方法是将二进制第 offset 位值变为 value
     *
     * @param key    the key
     * @param offset 位置
     * @param value  值，true 为 1, false 为 0
     * @return bit bit
     */
    public static boolean setBit(String key, long offset, boolean value) {
        return redisTemplate.opsForValue().setBit(key, offset, value);
    }

    /**
     * 只有在 key 不存在时设置 key 的值
     *
     * @param key   the key
     * @param value the value
     * @return 之前已经存在返回 false, 不存在返回 true
     */
    public static Boolean setNx(String key, Objects value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 用 value 参数覆写给定 key 所储存的字符串值，从偏移量 offset 开始
     *
     * @param key    the key
     * @param value  the value
     * @param offset 从指定位置开始覆写
     */
    public static void setRange(String key, String value, long offset) {
        redisTemplate.opsForValue().set(key, value, offset);
    }

    /**
     * 批量添加
     *
     * @param maps the maps
     */
    public static void mSet(Map<String, String> maps) {
        redisTemplate.opsForValue().multiSet(maps);
    }

    /**
     * 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在
     *
     * @param maps the maps
     * @return 之前已经存在返回 false, 不存在返回 true
     */
    public static boolean mSetNx(Map<String, String> maps) {
        return redisTemplate.opsForValue().multiSetIfAbsent(maps);
    }

    /**
     * 获取字符串的长度
     *
     * @param key the key
     * @return long long
     */
    public static Long size(String key) {
        return redisTemplate.opsForValue().size(key);
    }

    /**
     * 获取数据，数字获取请使用{@link #getLong}
     *
     * @param <T> 返回类型
     * @param key key
     * @return val t
     */
    public static <T> T get(String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取数字，自动转换Integer为Long
     *
     * @param key key
     * @return val long
     */
    public static Long getLong(String key) {
        Object o = redisTemplate.opsForValue().get(key);
        if (o instanceof Integer) {
            return ((Integer) o).longValue();
        }
        return (Long) o;
    }

    /**
     * 自增 +1
     * <p> incr 一个不是 int 的 value 会返回错误，incr 一个不存在的 key，则设置 key 为 1 </p>
     * <p> 范围为 64 有符号，-9223372036854775808~9223372036854775807 </p>
     *
     * @param key key
     * @return 新值 long
     */
    public static Long incr(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    /**
     * 指定加数自增，可传入负数做自减
     * <p> 同 incr，加指定值 ，key 不存在时候会设置 key，并认为原来的 value 是 0 </p>
     *
     * @param key    key
     * @param change 变化量
     * @return 新值 long
     */
    public static Long incrBy(String key, Long change) {
        return redisTemplate.opsForValue().increment(key, change);
    }

    /**
     * 自减 -1
     * <p> 做的是减减操作，decr 一个不存在 key，则设置 key 为 - 1 </p>
     *
     * @param key key
     * @return 新值 long
     */
    public static Long decr(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    /**
     * 指定减数自减
     * <p> 同 decr，减指定值。decrby 完全是为了可读性，我们完全可以通过 incrby 一个负值来实现同样效果，反之一样。 </p>
     *
     * @param key    key
     * @param change 变化量
     * @return 新值 long
     */
    public static Long decrBy(String key, Long change) {
        return redisTemplate.opsForValue().decrement(key, change);
    }

    /**
     * 针对浮点数
     *
     * @param key       the key
     * @param increment the increment
     * @return double double
     */
    public static Double incrByFloat(String key, double increment) {
        return redisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * 追加到末尾
     *
     * @param key   the key
     * @param value the value
     * @return integer integer
     */
    public static Integer append(String key, String value) {
        return redisTemplate.opsForValue().append(key, value);
    }
/** -------------------hash 相关操作 ------------------------- */

    /**
     * 获取存储在哈希表中指定字段的值
     *
     * @param key   the key
     * @param field the field
     * @return object object
     */
    public static Object hGet(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 获取所有给定字段的值
     *
     * @param key the key
     * @return map map
     */
    public static Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 获取所有给定字段的值
     *
     * @param key    the key
     * @param fields the fields
     * @return list list
     */
    public static List<Object> hMultiGet(String key, Collection<Object> fields) {
        return redisTemplate.opsForHash().multiGet(key, fields);
    }

    /**
     * H set.
     *
     * @param key     the key
     * @param hashKey the hash key
     * @param value   the value
     */
    public static void hSet(String key, Object hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * H put all.
     *
     * @param key  the key
     * @param maps the maps
     */
    public static void hPutAll(String key, Map<String, Object> maps) {
        redisTemplate.opsForHash().putAll(key, maps);
    }

    /**
     * 仅当 hashKey 不存在时才设置
     *
     * @param key     the key
     * @param hashKey the hash key
     * @param value   the value
     * @return boolean boolean
     */
    public static Boolean hPutIfAbsent(String key, String hashKey, String value) {
        return redisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
    }

    /**
     * 删除一个或多个哈希表字段
     *
     * @param key    the key
     * @param fields the fields
     * @return long long
     */
    public static Long hDel(String key, String... fields) {
        return redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * 查看哈希表 key 中，指定的字段是否存在
     *
     * @param key   the key
     * @param field the field
     * @return boolean boolean
     */
    public static boolean hExists(String key, String field) {
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment
     *
     * @param key       the key
     * @param field     the field
     * @param increment the increment
     * @return long long
     */
    public static Long hIncrBy(String key, String field, long increment) {
        return redisTemplate.opsForHash().increment(key, field, increment);
    }

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment
     *
     * @param key   the key
     * @param field the field
     * @param delta the delta
     * @return double double
     */
    public static Double hIncrByFloat(String key, String field, double delta) {
        return redisTemplate.opsForHash().increment(key, field, delta);
    }

    /**
     * 获取所有哈希表中的字段
     *
     * @param key the key
     * @return set set
     */
    public static Set<Object> hKeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * 获取哈希表中字段的数量
     *
     * @param key the key
     * @return long long
     */
    public static Long hSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    /**
     * 获取哈希表中所有值
     *
     * @param key the key
     * @return list list
     */
    public static List<Object> hValues(String key) {
        return redisTemplate.opsForHash().values(key);
    }

    /**
     * 迭代哈希表中的键值对
     *
     * @param key     the key
     * @param options the options
     * @return cursor cursor
     */
    public static Cursor<Map.Entry<Object, Object>> hScan(String key, ScanOptions options) {
        return redisTemplate.opsForHash().scan(key, options);
    }

    /** ------------------------list 相关操作 ---------------------------- */

    /**
     * 通过索引获取列表中的元素
     *
     * @param key   the key
     * @param index the index
     * @return object object
     */
    public static Object lIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 获取列表指定范围内的元素
     *
     * @param key   the key
     * @param start 开始位置，0 是开始位置
     * @param end   结束位置，-1 返回所有
     * @return list list
     */
    public static List<Object> lRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 存储在 list 头部
     *
     * @param key   the key
     * @param value the value
     * @return long long
     */
    public static Long lLeftPush(String key, String value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * L left push all long.
     *
     * @param key   the key
     * @param value the value
     * @return long long
     */
    public static Long lLeftPushAll(String key, String... value) {
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * L left push all long.
     *
     * @param key   the key
     * @param value the value
     * @return long long
     */
    public static Long lLeftPushAll(String key, Collection<String> value) {
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * 当 list 存在的时候才加入
     *
     * @param key   the key
     * @param value the value
     * @return long long
     */
    public static Long lLeftPushIfPresent(String key, String value) {
        return redisTemplate.opsForList().leftPushIfPresent(key, value);
    }

    /**
     * 如果 pivot 存在，再 pivot 前面添加
     *
     * @param key   the key
     * @param pivot the pivot
     * @param value the value
     * @return long long
     */
    public static Long lLeftPush(String key, String pivot, String value) {
        return redisTemplate.opsForList().leftPush(key, pivot, value);
    }

    /**
     * L right push long.
     *
     * @param key   the key
     * @param value the value
     * @return long long
     */
    public static Long lRightPush(String key, String value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * L right push all long.
     *
     * @param key   the key
     * @param value the value
     * @return long long
     */
    public static Long lRightPushAll(String key, String... value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * L right push all long.
     *
     * @param key   the key
     * @param value the value
     * @return long long
     */
    public static Long lRightPushAll(String key, Collection<String> value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 为已存在的列表添加值
     *
     * @param key   the key
     * @param value the value
     * @return long long
     */
    public static Long lRightPushIfPresent(String key, String value) {
        return redisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    /**
     * 在 pivot 元素的右边添加值
     *
     * @param key   the key
     * @param pivot the pivot
     * @param value the value
     * @return long long
     */
    public static Long lRightPush(String key, String pivot, String value) {
        return redisTemplate.opsForList().rightPush(key, pivot, value);
    }

    /**
     * 通过索引设置列表元素的值
     *
     * @param key   the key
     * @param index 位置
     * @param value the value
     */
    public static void lSet(String key, long index, String value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 移出并获取列表的第一个元素
     *
     * @param key the key
     * @return 删除的元素 object
     */
    public static Object lLeftPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param key     the key
     * @param timeout 等待时间
     * @param unit    时间单位
     * @return object object
     */
    public static Object lBLeftPop(String key, long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().leftPop(key, timeout, unit);
    }

    /**
     * 移除并获取列表最后一个元素
     *
     * @param key the key
     * @return 删除的元素 object
     */
    public static Object lRightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param key     the key
     * @param timeout 等待时间
     * @param unit    时间单位
     * @return object object
     */
    public static Object lBRightPop(String key, long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().rightPop(key, timeout, unit);
    }

    /**
     * 移除列表的最后一个元素，并将该元素添加到另一个列表并返回
     *
     * @param sourceKey      the source key
     * @param destinationKey the destination key
     * @return object object
     */
    public static Object lRightPopAndLeftPush(String sourceKey, String destinationKey) {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey,
                destinationKey);
    }

    /**
     * 从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它； 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param sourceKey      the source key
     * @param destinationKey the destination key
     * @param timeout        the timeout
     * @param unit           the unit
     * @return object object
     */
    public static Object lBRightPopAndLeftPush(String sourceKey, String destinationKey,
                                               long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey,
                destinationKey, timeout, unit);
    }

    /**
     * 删除集合中值等于 value 得元素
     *
     * @param key   the key
     * @param index index=0, 删除所有值等于 value 的元素；index>0, 从头部开始删除第一个值等于 value 的元素；              index<0, 从尾部开始删除第一个值等于 value 的元素；
     * @param value the value
     * @return long long
     */
    public static Long lRemove(String key, long index, String value) {
        return redisTemplate.opsForList().remove(key, index, value);
    }

    /**
     * 裁剪 list
     *
     * @param key   the key
     * @param start the start
     * @param end   the end
     */
    public static void lTrim(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    /**
     * 获取列表长度
     *
     * @param key the key
     * @return long long
     */
    public static Long lLen(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /** --------------------set 相关操作 -------------------------- */

    /**
     * set 添加元素
     *
     * @param key    the key
     * @param values the values
     * @return long long
     */
    public static Long sAdd(String key, String... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * set 移除元素
     *
     * @param key    the key
     * @param values the values
     * @return long long
     */
    public static Long sRemove(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * 移除并返回集合的一个随机元素
     *
     * @param key the key
     * @return object object
     */
    public static Object sPop(String key) {
        return redisTemplate.opsForSet().pop(key);
    }

    /**
     * 将元素 value 从一个集合移到另一个集合
     *
     * @param key     the key
     * @param value   the value
     * @param destKey the dest key
     * @return boolean boolean
     */
    public static Boolean sMove(String key, String value, String destKey) {
        return redisTemplate.opsForSet().move(key, value, destKey);
    }

    /**
     * 获取集合的大小
     *
     * @param key the key
     * @return long long
     */
    public static Long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 判断集合是否包含 value
     *
     * @param key   the key
     * @param value the value
     * @return boolean boolean
     */
    public static Boolean sIsMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 获取两个集合的交集
     *
     * @param key      the key
     * @param otherKey the other key
     * @return set set
     */
    public static Set<Object> sIntersect(String key, String otherKey) {
        return redisTemplate.opsForSet().intersect(key, otherKey);
    }

    /**
     * 获取 key 集合与多个集合的交集
     *
     * @param key       the key
     * @param otherKeys the other keys
     * @return set set
     */
    public static Set<Object> sIntersect(String key, Collection<String> otherKeys) {
        return redisTemplate.opsForSet().intersect(key, otherKeys);
    }

    /**
     * key 集合与 otherKey 集合的交集存储到 destKey 集合中
     *
     * @param key      the key
     * @param otherKey the other key
     * @param destKey  the dest key
     * @return long long
     */
    public static Long sIntersectAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKey,
                destKey);
    }

    /**
     * key 集合与多个集合的交集存储到 destKey 集合中
     *
     * @param key       the key
     * @param otherKeys the other keys
     * @param destKey   the dest key
     * @return long long
     */
    public static Long sIntersectAndStore(String key, Collection<String> otherKeys,
                                          String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKeys,
                destKey);
    }

    /**
     * 获取两个集合的并集
     *
     * @param key       the key
     * @param otherKeys the other keys
     * @return set set
     */
    public static Set<Object> sUnion(String key, String otherKeys) {
        return redisTemplate.opsForSet().union(key, otherKeys);
    }

    /**
     * 获取 key 集合与多个集合的并集
     *
     * @param key       the key
     * @param otherKeys the other keys
     * @return set set
     */
    public static Set<Object> sUnion(String key, Collection<String> otherKeys) {
        return redisTemplate.opsForSet().union(key, otherKeys);
    }

    /**
     * key 集合与 otherKey 集合的并集存储到 destKey 中
     *
     * @param key      the key
     * @param otherKey the other key
     * @param destKey  the dest key
     * @return long long
     */
    public static Long sUnionAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * key 集合与多个集合的并集存储到 destKey 中
     *
     * @param key       the key
     * @param otherKeys the other keys
     * @param destKey   the dest key
     * @return long long
     */
    public static Long sUnionAndStore(String key, Collection<String> otherKeys,
                                      String destKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKeys, destKey);
    }

    /**
     * 获取两个集合的差集
     *
     * @param key      the key
     * @param otherKey the other key
     * @return set set
     */
    public static Set<Object> sDifference(String key, String otherKey) {
        return redisTemplate.opsForSet().difference(key, otherKey);
    }

    /**
     * 获取 key 集合与多个集合的差集
     *
     * @param key       the key
     * @param otherKeys the other keys
     * @return set set
     */
    public static Set<Object> sDifference(String key, Collection<String> otherKeys) {
        return redisTemplate.opsForSet().difference(key, otherKeys);
    }

    /**
     * key 集合与 otherKey 集合的差集存储到 destKey 中
     *
     * @param key      the key
     * @param otherKey the other key
     * @param destKey  the dest key
     * @return long long
     */
    public static Long sDifference(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKey,
                destKey);
    }

    /**
     * key 集合与多个集合的差集存储到 destKey 中
     *
     * @param key       the key
     * @param otherKeys the other keys
     * @param destKey   the dest key
     * @return long long
     */
    public static Long sDifference(String key, Collection<String> otherKeys,
                                   String destKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKeys,
                destKey);
    }

    /**
     * 获取集合所有元素
     *
     * @param key the key
     * @return members members
     */
    public static Set<Object> setMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 随机获取集合中的一个元素
     *
     * @param key the key
     * @return object object
     */
    public static Object sRandomMember(String key) {
        return redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * 随机获取集合中 count 个元素
     *
     * @param key   the key
     * @param count the count
     * @return list list
     */
    public static List<Object> sRandomMembers(String key, long count) {
        return redisTemplate.opsForSet().randomMembers(key, count);
    }

    /**
     * 随机获取集合中 count 个元素并且去除重复的
     *
     * @param key   the key
     * @param count the count
     * @return set set
     */
    public static Set<Object> sDistinctRandomMembers(String key, long count) {
        return redisTemplate.opsForSet().distinctRandomMembers(key, count);
    }

    /**
     * S scan cursor.
     *
     * @param key     the key
     * @param options the options
     * @return cursor cursor
     */
    public static Cursor<Object> sScan(String key, ScanOptions options) {
        return redisTemplate.opsForSet().scan(key, options);
    }

    /**------------------zSet 相关操作 --------------------------------*/

    /**
     * 添加元素，有序集合是按照元素的 score 值由小到大排列
     *
     * @param key   the key
     * @param value the value
     * @param score the score
     * @return boolean boolean
     */
    public static Boolean zAdd(String key, Object value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * Z add long.
     *
     * @param key    the key
     * @param values the values
     * @return long long
     */
    public static Long zAdd(String key, Set<ZSetOperations.TypedTuple<Object>> values) {
        return redisTemplate.opsForZSet().add(key, values);
    }

    /**
     * Z remove long.
     *
     * @param key    the key
     * @param values the values
     * @return long long
     */
    public static Long zRemove(String key, Object... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * 增加元素的 score 值，并返回增加后的值
     *
     * @param key   the key
     * @param value the value
     * @param delta the delta
     * @return double double
     */
    public static Double zIncrementScore(String key, Object value, double delta) {
        return redisTemplate.opsForZSet().incrementScore(key, value, delta);
    }

    /**
     * 返回元素在集合的排名，有序集合是按照元素的 score 值由小到大排列
     *
     * @param key   the key
     * @param value the value
     * @return 0 表示第一位
     */
    public static Long zRank(String key, Object value) {
        return redisTemplate.opsForZSet().rank(key, value);
    }

    /**
     * 返回元素在集合的排名，按元素的 score 值由大到小排列
     *
     * @param key   the key
     * @param value the value
     * @return long long
     */
    public static Long zReverseRank(String key, Object value) {
        return redisTemplate.opsForZSet().reverseRank(key, value);
    }

    /**
     * 获取集合的元素，从小到大排序
     *
     * @param key   the key
     * @param start 开始位置
     * @param end   结束位置，-1 查询所有
     * @return set set
     */
    public static Set<Object> zRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * 获取集合元素，并且把 score 值也获取
     *
     * @param key   the key
     * @param start the start
     * @param end   the end
     * @return set set
     */
    public static Set<ZSetOperations.TypedTuple<Object>> zRangeWithScores(String key, long start,
                                                                          long end) {
        return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    /**
     * 根据 Score 值查询集合元素
     *
     * @param key the key
     * @param min 最小值
     * @param max 最大值
     * @return set set
     */
    public static Set<Object> zRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * 根据 Score 值查询集合元素，从小到大排序
     *
     * @param key the key
     * @param min 最小值
     * @param max 最大值
     * @return set set
     */
    public static Set<ZSetOperations.TypedTuple<Object>> zRangeByScoreWithScores(String key,
                                                                                 double min, double max) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
    }

    /**
     * Z range by score with scores set.
     *
     * @param key   the key
     * @param min   the min
     * @param max   the max
     * @param start the start
     * @param end   the end
     * @return set set
     */
    public static Set<ZSetOperations.TypedTuple<Object>> zRangeByScoreWithScores(String key,
                                                                                 double min, double max, long start, long end) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max,
                start, end);
    }

    /**
     * 获取集合的元素，从大到小排序
     *
     * @param key   the key
     * @param start the start
     * @param end   the end
     * @return set set
     */
    public static Set<Object> zReverseRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * 获取集合的元素，从大到小排序，并返回 score 值
     *
     * @param key   the key
     * @param start the start
     * @param end   the end
     * @return set set
     */
    public static Set<ZSetOperations.TypedTuple<Object>> zReverseRangeWithScores(String key,
                                                                                 long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start,
                end);
    }

    /**
     * 根据 Score 值查询集合元素，从大到小排序
     *
     * @param key the key
     * @param min the min
     * @param max the max
     * @return set set
     */
    public static Set<Object> zReverseRangeByScore(String key, double min,
                                                   double max) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
    }

    /**
     * 根据 Score 值查询集合元素，从大到小排序
     *
     * @param key the key
     * @param min the min
     * @param max the max
     * @return set set
     */
    public static Set<ZSetOperations.TypedTuple<Object>> zReverseRangeByScoreWithScores(
            String key, double min, double max) {
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key,
                min, max);
    }

    /**
     * Z reverse range by score set.
     *
     * @param key   the key
     * @param min   the min
     * @param max   the max
     * @param start the start
     * @param end   the end
     * @return set set
     */
    public static Set<Object> zReverseRangeByScore(String key, double min,
                                                   double max, long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max,
                start, end);
    }

    /**
     * 根据 score 值获取集合元素数量
     *
     * @param key the key
     * @param min the min
     * @param max the max
     * @return long long
     */
    public static Long zCount(String key, double min, double max) {
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    /**
     * 获取集合大小
     *
     * @param key the key
     * @return long long
     */
    public static Long zSize(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    /**
     * 获取集合大小
     *
     * @param key the key
     * @return long long
     */
    public static Long zZCard(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    /**
     * 获取集合中 value 元素的 score 值
     *
     * @param key   the key
     * @param value the value
     * @return double double
     */
    public static Double zScore(String key, Object value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    /**
     * 移除指定索引位置的成员
     *
     * @param key   the key
     * @param start the start
     * @param end   the end
     * @return long long
     */
    public static Long zRemoveRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    /**
     * 根据指定的 score 值的范围来移除成员
     *
     * @param key the key
     * @param min the min
     * @param max the max
     * @return long long
     */
    public static Long zRemoveRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }

    /**
     * 获取 key 和 otherKey 的并集并存储在 destKey 中
     *
     * @param key      the key
     * @param otherKey the other key
     * @param destKey  the dest key
     * @return long long
     */
    public static Long zUnionAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * Z union and store long.
     *
     * @param key       the key
     * @param otherKeys the other keys
     * @param destKey   the dest key
     * @return long long
     */
    public static Long zUnionAndStore(String key, Collection<String> otherKeys,
                                      String destKey) {
        return redisTemplate.opsForZSet()
                .unionAndStore(key, otherKeys, destKey);
    }

    /**
     * 交集
     *
     * @param key      the key
     * @param otherKey the other key
     * @param destKey  the dest key
     * @return long long
     */
    public static Long zIntersectAndStore(String key, String otherKey,
                                          String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKey,
                destKey);
    }

    /**
     * 交集
     *
     * @param key       the key
     * @param otherKeys the other keys
     * @param destKey   the dest key
     * @return long long
     */
    public static Long zIntersectAndStore(String key, Collection<String> otherKeys,
                                          String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKeys,
                destKey);
    }

    /**
     * Z scan cursor.
     *
     * @param key     the key
     * @param options the options
     * @return cursor cursor
     */
    public static Cursor<ZSetOperations.TypedTuple<Object>> zScan(String key, ScanOptions options) {
        return redisTemplate.opsForZSet().scan(key, options);
    }

    /**
     * 获取 Redis List 序列化
     *
     * @param <T>         the type parameter
     * @param key         the key
     * @param targetClass the target class
     * @return list cache
     */
    public static <T> List<T> getListCache(final String key, Class<T> targetClass) {
        byte[] result = redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.get(key.getBytes());
            }
        });
        if (result == null) {
            return null;
        }
        return ProtoStuffSerializerUtils.deserializeList(result, targetClass);
    }

    /***
     * 将 List 放进缓存里面
     * @param <T>   the type parameter
     * @param key the key
     * @param objList the obj list
     * @param expireTime the expire time
     * @return boolean boolean
     */
    public static <T> boolean putListCacheWithExpireTime(String key, List<T> objList, final long expireTime) {
        final byte[] bkey = key.getBytes();
        final byte[] bvalue = ProtoStuffSerializerUtils.serializeList(objList);
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                connection.setEx(bkey, expireTime, bvalue);
                return true;
            }
        });
        return result;
    }
}
