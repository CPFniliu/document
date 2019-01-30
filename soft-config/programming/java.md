# config

## 配置可执行jar

新建 reg 文件, 更改路径, 复制下面的内容, 执行

```reg
Windows Registry Editor Version 5.00

[HKEY_CLASSES_ROOT\Applications\javaw.exe]
"IsHostApp"=""

[HKEY_CLASSES_ROOT\Applications\javaw.exe\shell]

[HKEY_CLASSES_ROOT\Applications\javaw.exe\shell\open]

[HKEY_CLASSES_ROOT\Applications\javaw.exe\shell\open\command]
@="\"D:\\programing\\SDK\\jdk1.8.0_181\\bin\\javaw.exe\" -jar \"%1\""
```