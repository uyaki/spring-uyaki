package com.uyaki.redis.sentinel.base.util;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author uyaki
 * @date 2020/07/05
 */
public class ProtoStuffSerializerUtils {
    /**
     * 序列化对象
     *
     * @param <T> the type parameter
     * @param obj obj
     * @return 序列化结果 byte [ ]
     */
    public static <T> byte[] serialize(T obj) {
        if (obj == null) {
            throw new RuntimeException(" 序列化对象 (" + obj + ")!");
        }
        @SuppressWarnings("unchecked")
        Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(obj.getClass());
        LinkedBuffer buffer = LinkedBuffer.allocate(1024 * 1024);
        byte[] protoStuff = null;
        try {
            protoStuff = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception e) {
            throw new RuntimeException(" 序列化 (" + obj.getClass() + ") 对象 (" + obj + ") 发生异常！", e);
        } finally {
            buffer.clear();
        }
        return protoStuff;
    }

    /**
     * 反序列化对象
     *
     * @param <T>              the type parameter
     * @param paramArrayOfByte bytes
     * @param targetClass      反序列化对象class
     * @return 反序列化结果 t
     */
    public static <T> T deserialize(byte[] paramArrayOfByte, Class<T> targetClass) {
        if (paramArrayOfByte == null || paramArrayOfByte.length == 0) {
            throw new RuntimeException(" 反序列化对象发生异常，byte 序列为空！");
        }
        T instance = null;
        try {
            instance = targetClass.newInstance();
        } catch (InstantiationException e1) {
            throw new RuntimeException(" 反序列化过程中依据类型创建对象失败！", e1);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(" 反序列化过程中依据类型创建对象失败！", e2);
        }
        Schema<T> schema = RuntimeSchema.getSchema(targetClass);
        ProtostuffIOUtil.mergeFrom(paramArrayOfByte, instance, schema);
        return instance;
    }

    /**
     * 序列化列表
     *
     * @param <T>     the type parameter
     * @param objList 对象List
     * @return 序列化结果 byte [ ]
     */
    public static <T> byte[] serializeList(List<T> objList) {
        if (objList == null || objList.isEmpty()) {
            throw new RuntimeException(" 序列化对象列表 (" + objList + ") 参数异常！");
        }
        @SuppressWarnings("unchecked")
        Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(objList.get(0).getClass());
        LinkedBuffer buffer = LinkedBuffer.allocate(1024 * 1024);
        byte[] protoStuff = null;
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            ProtostuffIOUtil.writeListTo(bos, objList, schema, buffer);
            protoStuff = bos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(" 序列化对象列表 (" + objList + ") 发生异常！", e);
        } finally {
            buffer.clear();
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return protoStuff;
    }

    /**
     * 反序列化列表
     *
     * @param <T>              the type parameter
     * @param paramArrayOfByte 对象数组
     * @param targetClass      对象
     * @return 反序列化结果 list
     */
    public static <T> List<T> deserializeList(byte[] paramArrayOfByte, Class<T> targetClass) {
        if (paramArrayOfByte == null || paramArrayOfByte.length == 0) {
            throw new RuntimeException(" 反序列化对象发生异常，byte 序列为空！");
        }

        Schema<T> schema = RuntimeSchema.getSchema(targetClass);
        List<T> result = null;
        try {
            result = ProtostuffIOUtil.parseListFrom(new ByteArrayInputStream(paramArrayOfByte), schema);
        } catch (IOException e) {
            throw new RuntimeException(" 反序列化对象列表发生异常！", e);
        }
        return result;
    }
}
