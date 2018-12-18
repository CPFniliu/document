# 修改 keyBoard bindings

我本来是个atom忠实粉丝, 但是atom使用window10自带输入法在书写汉字时总是会出现首字母缺失的情况, 查了好久没法解决, 直接放弃了atom, 转而使用Vs code书写markdown, 但是使用Vs code时发现在左侧导航栏配置新建文件和文件夹没有快捷方式, 于是就想配置一个, 但是在百度上搜了好长时间, 却总是解决不了KeyBoard Shortcuts上面的when 属性, 最终访问的 Vs code 官方文档 解决的.

下面是相关文档是地址

https://code.visualstudio.com/docs/getstarted/keybindings

## 相应步骤

1. 点击左下角设置图标, 点击其中的选项KeyBoard Shortcuts.

   ![Vscode配置1](Vscode&#32;config01.png)

2. 输入 "explorer.new" 找到相应命令, 点击 explorer.newFolder 行配置上 "Shift + A", 快捷键. 但是这里还有个 When 属性, 如果放任不管的话, 软件会默认全局快捷键, 试想一下, 在你编辑的时候突然按了个 "Shift + A", 结果新建了个文件夹, 那真是太糟糕了, 所以 When 属性一定要解决.

   ![Vscode配置2](Vscode&#32;config02.png)

3. 在KeyBoard Shortcuts视图里, 有一个打开keybindings.json文件的链接, 点击可以打开该文件,

   ![Vscode配置3](Vscode&#32;config03.png)

   此时我们可以看到右侧文件的相应配置, 这就是我们刚刚配置的东西. 我们可以参照左边的格式配置 when 属性, 可是我们该怎么选择属性呢.

   ![Vscode配置4](Vscode&#32;config04.png)

4. 现在我们可以打开 Vs code 的官方文档 [Key Bindings for Visual Studio Code](https://code.visualstudio.com/docs/getstarted/keybindings), 这个是国外的网站, 纯英文. 可能打不开, 下面贴上文档里的关于 When 的介绍, 里面也是英文的, 不过作为使用Vs code的开发人员, 这点应该能看懂的, 实在不行也可以google翻译.
   ![Vscode-when&#32;clause&#32;contexts](Vscode-when&#32;clause&#32;contexts.png)

   鉴于我们要添加访问左侧 Explorer 时有效的快捷键, 因此可以选择Explorer contexts 中的 explorerResourceIsFolder 属性.

5. 最终, keybindings.json 文件内容就变成了

```json
// Place your key bindings in this file to override the defaults
[
   {
      "key": "a",
      "command": "explorer.newFile",
      "when": "explorerResourceIsFolder"
   },
   {
      "key": "shift+a",
      "command": "explorer.newFolder",
      "when": "explorerResourceIsFolder"
   }
]
```

   ![Vscode配置6](Vscode&#32;config06.png)