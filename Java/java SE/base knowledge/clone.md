#### 深克隆 & 浅克隆

浅复制的对象中所有值都包含着与原来对象变量相同的值, 换言之, 浅复制仅仅当前复制对象的值, 而不复制它所引用的对象.
深复制: 基本类型保持与原来一至, 引用对象同时进行拷贝. 换言之, 深复制出来的对象的变量与原来对象中的变量无关

#### 深复制的三种方式

1. 序列化 和 反序列化
```java
    public Object deepClone() throws IOException, ClassNotFoundException {
        Object object;
        ByteArrayOutputStream bios = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bios)) {
            oos.writeObject(this);
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(bios.toByteArray());
        try (ObjectInputStream bis = new ObjectInputStream(bais)) {
            object = bis.readObject();
        }
        return object;
    }
```

2. json
   先将对象转换为json, 再将json转换为对象.

3. 手动深拷贝
   先对对象进行拷贝, 再逐个对对象中的引用型变量的对象进行拷贝并将拷贝后的对象付给引用变量.

###### 小结

深复制方式 | 效率 | 需要接口
- | -
序列化反序列化 | 效率最慢 | Serializable
json | 效率比较慢, 但比序列化方式快 | Serializable
手动深拷贝 | 效率最快 |  Cloneable