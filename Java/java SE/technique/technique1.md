#### for(;;)和while(true)的区别

一直知道for(;;)和while(true)都是无限循环，今天搜了下原理

while VS. for 
　　在编程中，我们常常需要用到无限循环，常用的两种方法是while (1) 和 for (；；)。这两种方法效果完全一样，但那一种更好呢？让我们看看它们编译后的代码：
    编译前              编译后 
    while (1)；         mov eax,1  
                                              test eax,eax 
                                               je foo+23h
                                               jmp foo+18h


        编译前              编译后 
    for (；；)；          jmp foo+23h 　　
    一目了然，for (；；)指令少，不占用寄存器，而且没有判断跳转，比while (1)好。 