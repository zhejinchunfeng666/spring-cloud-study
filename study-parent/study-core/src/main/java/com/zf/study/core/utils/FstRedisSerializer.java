package com.zf.study.core.utils;

import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.*;

/**
 * Redis本身没有存取对象的功能，而是有存取byte数据的功能，我们可以对要存取的对象进行序列化后，进行操作
 * <p>
 *      优点 				缺点
 * Java 序列化 兼容性最佳		无侵入性 速度慢，体积大
 * Kryo 速度快 				侵入性强
 * FST  速度快，无侵入性       不同平台序列化的结果不同(新版本已解决)
 * <p>
 * java 序列化可参考 {@link org.springframework.util.SerializationUtils}
 *
 */
public class FstRedisSerializer implements RedisSerializer<Object> {

    /**
     * fst 序列化
     *
     * @param o Java对象
     * @return 二进制数据
     */
    @Override
    public byte[] serialize(Object o) {
        if (o == null) {
            return null;
        }
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             ObjectOutput fout = new FSTObjectOutput(out)) {
            fout.writeObject(o);
            fout.flush();
            return out.toByteArray();
        } catch (IOException e) {
            throw new SerializationException("Cannot serialize", e);
        }
    }

    /**
     * fst 反序列化
     *
     * @param bytes 二进制数据
     * @return Java对象
     */
    @Override
    public Object deserialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try (ObjectInput in = new FSTObjectInput(new ByteArrayInputStream(bytes))) {
            return in.readObject();
        } catch (Exception e) {
            throw new SerializationException("Cannot deserialize", e);
        }
    }
}