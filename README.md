# Java8Action
## 目录
 - [为什么要关心java8](#为什么要关心java8)
    - [java8的主要变化](#java8的主要变化)
        - [流处理](#流处理)
        - [用行为参数化把代码传递给方法](#用行为参数化把代码传递给方法)
        - [并行与共享的可变数据](#并行与共享的可变数据)
    - [Java中的函数](#Java中的函数)
        - [方法引用](#方法引用)
        - [lambda-匿名函数](#lambda-匿名函数)
    - [流](#流)
    - [默认方法](#默认方法)
 - [通过行为参数化传递代码](#通过行为参数化传递代码)
 - [Lambda表达式](#Lambda表达式)

## 为什么要关心java8
### java8的主要变化
#### 流处理
Java 8在java.util.stream中添加了一个Stream API；Stream<T>就是一系列T类型的项目。你现在可以把它看成一种比较花哨的迭代器。
  
现在你可以在一个更高的抽象层次上写Java8程序了：思路变成了把这样的流变成那样的流（就像写数据库查询语句时的那种思路），而不是一次只处理一个项目。

另一个好处是，Java8可以透明地把输入的不相关部分拿到几个CPU内核上去分别执行你的Stream操作流水线——这是几乎免费的并行，用不着去费劲搞Thread了。

就像汽车组装流水线一样，汽车排队进入加工站，每个加工站会接收、修改汽车，然后将之传递给下一站做进一步的处理。尽管流水线实际上是一个序列，但不同加工站的运行一般是并行的。

#### 用行为参数化把代码传递给方法
Java 8中增加的另一个编程概念是通过API来传递代码的能力。这听起来实在太抽象了。

比方说，你有一堆发票代码，格式类似于2013UK0001、2014US0002……前四位数代表年份，接下来两个字母代表国家，最后四位是客户的代码。你可能想按照年份、客户代码，甚至国家来对发票进行排序。你真正想要的是，能够给sort命令一个参数让用户定义顺序：给sort命令传递一段独立代码。

那么，直接套在Java上，你是要让sort方法利用自定义的顺序进行比较。你可以写一个compareUsingCustomerId来比较两张发票的代码，但是在Java 8之前，你没法把这个方法传给另一个方法。你可以创建一个Comparator对象，将之传递给sort方法，但这不但啰嗦，而且让“重复使用现有行为”的思想变得不那么清楚了。

Java 8增加了把方法（你的代码）作为参数传递给另一个方法的能力。我们把这一概念称为行为参数化。它的重要之处在哪儿呢？Stream API就是构建在通过传递代码使操作行为实现参数化的思想上的，当把compareUsingCustomerId传进去，你就把sort的行为参数化了。

#### 并行与共享的可变数据
第三个编程概念更隐晦一点，它来自我们前面讨论流处理能力时说的“几乎免费的并行”。

你需要放弃什么吗？你可能需要对传给流方法的行为的写法稍作改变。这些改变可能一开始会让你感觉有点儿不舒服，但一旦习惯了你就会爱上它们。

你的行为必须能够同时对不同的输入安全地执行。一般情况下这就意味着，你写代码时**不能访问共享的可变数据**。这些函数有时被称为“纯函数”或“无副作用函数”或“无状态函数”，这一点我们会在后续详细讨论。前面说的并行只有在假定你的代码的多个副本可以独立工作时才能进行。

Java 8的流实现并行比Java现有的线程API更容易，因此，虽然可以使用synchronized来打破“不能有共享的可变数据”这一规则，但这相当于是在和整个体系作对，因为它使所有围绕这一规则做出的优化都失去意义了。在多个处理器内核之间使用synchronized，其代价往往比你预期的要大得多，因为同步迫使代码按照顺序执行，而这与并行处理的宗旨相悖。

这两个要点（**没有共享的可变数据，将方法和函数即代码传递给其他方法的能力**）是我们平常所说的函数式编程范式的基石。“不能有共享的可变数据”的要求意味着，一个方法是可以通过它将参数值转换为结果的方式完全描述的；换句话说，这个方法的行为就像一个数学函数，没有可见的副作用。

### Java中的函数
Java 8中新增了函数——值的一种新形式。有了它，Java8可以进行多核处理器上的并行编程。

我们首先来展示一下作为值的函数本身的有用之处。想想Java程序可能操作的值吧。首先有原始值，比如42（int类型）和3.14（double类型）。其次，值可以是对象（更严格地说是对象的引用）。获得对象的唯一途径是利用new，也许是通过工厂方法或库函数实现的；对象引用指向类的一个实例。例子包括"abc"（String类型），new Integer(1111)（Integer类型），以及new HashMap<Integer,String>(100)的结果——它显然调用了HashMap的构造函数。甚至数组也是对象。那么有什么问题呢？

为了帮助回答这个问题，我们要注意到，编程语言的整个目的就在于操作值，要是按照历史上编程语言的传统，这些值因此被称为一等值（或一等公民，这个术语是从20世纪60年代美国民权运动中借用来的）。编程语言中的其他结构也许有助于我们表示值的结构，但在程序执行期间不能传递，因而是二等公民。也就是说二等公民（方法和类）是服务于一等公民（值）的。

前面所说的值是Java中的一等公民，但其他很多Java概念（如方法和类等）则是二等公民。用方法来定义类很不错，类还可以实例化来产生值，但方法和类本身都不是值。这又有什么关系呢？还真有，人们发现，在运行时传递方法能将方法变成一等公民。这在编程中非常有用，因此Java 8的设计者把这个功能加入到了Java中。顺便说一下，你可能会想，让类等其他二等公民也变成一等公民可能也是个好主意。有很多语言，如Smalltalk和JavaScript，都探索过这条路。

#### 方法引用
Java 8的第一个新功能是方法引用。

比方说，你想要筛选一个目录中的所有隐藏文件。你需要编写一个方法，然后给它一个File，它就会告诉你文件是不是隐藏的。我们可以把它看作一个函数，接受一个File，返回一个布尔值。但要用它做筛选，你需要把它包在一个FileFilter对象里，然后传递给File.listFiles
方法，如下所示：
```java
File[] hiddenFiles = new File(".").listFiles(new FileFilter() { 
 public boolean accept(File file) { 
    return file.isHidden(); 
 }});
```
真够啰嗦的，如今在Java 8里，你可以把代码重写成这个样子：
```java
File[] hiddenFiles = new File(".").listFiles(File::isHidden);
```
你已经有了函数isHidden，因此只需用Java8的**方法引用::语法**（即“把这个方法作为值”）将其传给listFiles方法；请注意，我们也开始用函数代表方法了。

好处是代码现在读起来更接近问题的陈述了。方法不再是二等值了。与用对象引用传递对象类似（对象引用是用new创建的），在Java 8里写下File::isHidden的时候，你就创建了一个方法引用，你同样可以传递它

#### Lambda——匿名函数
除了允许（命名）函数成为一等值外，Java 8还体现了更广义的将函数作为值的思想，包括Lambda(匿名函数）

> 使用这些概念的程序为函数式编程风格，**这句话的意思是“编写把函数作为一等值来传递的程序”。**

但要是Lambda的长度多于几行（它的行为也不是一目了然）的话，那你还是应该用方法引用来指向一个有描述性名称的方法，而不是使用匿名的Lambda。你应该以代码的清晰度为准绳。

### 流
几乎每个Java应用都会制造和处理集合。但集合用起来并不总是那么理想。比方说，你需要从一个列表中筛选金额较高的交易，然后按货币分组。你需要写一大堆套路化的代码来实现这个数据处理命令，如下所示：
```java
Map<Currency, List<Transaction>> transactionsByCurrencies = new HashMap<>(); 
for (Transaction transaction : transactions) { 
    if(transaction.getPrice() > 1000){ 
        Currency currency = transaction.getCurrency(); 
        List<Transaction> transactionsForCurrency = transactionsByCurrencies.get(currency); 
        if (transactionsForCurrency == null) { 
            transactionsForCurrency = new ArrayList<>(); 
            transactionsByCurrencies.put(currency, transactionsForCurrency); 
        } 
        transactionsForCurrency.add(transaction); 
    } 
}
```
此外，我们很难一眼看出来这些代码是做什么的，因为有好几个嵌套的控制流指令。有了Stream API，你现在可以这样解决这个问题了：
```java
import static java.util.stream.Collectors.toList; 
Map<Currency, List<Transaction>> transactionsByCurrencies = 
    transactions.stream() 
                .filter((Transaction t) -> t.getPrice() > 1000) 
                .collect(groupingBy(Transaction::getCurrency));
```

和Collection API相比，Stream API处理数据的方式非常不同。用集合的话，你得自己去做迭代的过程。你得用for-each循环一个个去迭代元素，然后再处理元素。我们把这种数据迭代的方法称为外部迭代。相反，有了Stream API，你根本用不着操心循环的事情。数据处理完全是在库内部进行的。我们把这种思想叫作内部迭代

使用集合的另一个头疼的地方是，想想看，要是你的交易量非常庞大，你要怎么处理这个巨大的列表呢？单个CPU根本搞不定这么大量的数据，但你很可能已经有了一台多核电脑。理想的情况下，你可能想让这些CPU内核共同分担处理工作，以缩短处理时间。理论上来说，要是你有
八个核，那并行起来，处理数据的速度应该是单核的八倍。

>经典的Java程序只能利用其中一个核，其他核的处理能力都浪费了。很多公司利用计算集群（用高速网络连接起来的多台计算机）来高效处理海量数据。Java8提供了新的编程风格，可更好地利用这样的计算机。
Google的搜索引擎就是一个无法在单台计算机上运行的代码的例子。它要读取互联网上的每个页面并建立索引，将每个互联网网页上出现的每个词都映射到包含该词的网址上。然后，如果你用多个单词进行搜索，软件就可以快速利用索引，给你一个包含这些词的网页集合。
想想看，你会如何在Java中实现这个算法，哪怕是比Google小的引擎也需要你利用计算机上所有的核。

##### 多线程并非易事
通过多线程代码来利用并行（使用先前Java版本中的ThreadAPI）并非易事。线程可能会同时访问并更新共享变量。因此，如果没有协调好②，数据可能会被意外改变。

Java 8用Stream API（java.util.stream）解决了这两个问题：**集合处理时的套路和晦涩、难以利用多核**。
这样设计的第一个原因是，有许多反复出现的数据处理模式，如果在库中有这些就会很方便：根据标准筛选数据（比如较重的苹果），提取数据（例如抽取列表中每个苹果的重量字段），或给数据分组（例如，将一个数字列表分组，奇数和偶数分别列表）等。

第二个原因是，这类操作常常可以并行化。在两个CPU上筛选列表，可以让一个CPU处理列表的前一半，第二个CPU处理后一半，这称为分支步骤。CPU随后对各自的半个列表做筛选。最后，一个CPU会把两个结果合并起来（Google搜索这么快就与此紧密相关，当然他们用的CPU远远不止两个了）。CPU并行处理如下图：
![cpu并行](http://clevercoder.cn/github/image/TIM%E6%88%AA%E5%9B%BE20190829181343.png)
顺序处理：
```java
import static java.util.stream.Collectors.toList; 
List<Apple> heavyApples = inventory.stream()
            .filter((Apple a) -> a.getWeight() > 150) 
            .collect(toList());
```
并行处理：
```java
import static java.util.stream.Collectors.toList; 
List<Apple> heavyApples = inventory.parallelStream()
            .filter((Apple a) -> a.getWeight() > 150) 
            .collect(toList());
```
到这里，我们只是说新的Stream API和Java现有的集合API的行为差不多：它们都能够访问数据项目的序列。不过，现在最好记得，Collection主要是为了存储和访问数据，而Stream则主要用于描述对数据的计算。这里的关键点在于，Stream允许并提倡并行处理一个Stream中的元素。虽然可能乍看上去有点儿怪，但筛选一个Collection的最快方法常常是将其转换为Stream，进行并行处理，然后再转换回List

>Java中的并行与无共享可变状态
大家都说Java里面并行很难，而且和synchronized相关的玩意儿都容易出问题。那Java8里面有什么“灵丹妙药”呢？
事实上有两个。首先，库会负责分块，即把大的流分成几个小的流，以便并行处理。
其次，流提供的这个几乎免费的并行，只有在传递给filter之类的库方法的方法不会互动（比方说有可变的共享对象）时才能工作。
函数式编程中的函数的主要意思是“把函数作为一等值”，不过它也常常隐含着第二层意思，即“执行时在元素之间无互动”。
即作为一等值的函数不能操作共享变量

### 默认方法
Java 8中加入默认方法主要是为了支持库设计师，让他们能够写出更容易改进的接口。这一方法很重要，因为你会在接口中遇到越来越多的默认方法。举个例子吧：
```java
List<Apple> heavyApples1 = inventory.stream()
        .filter((Apple a) -> a.getWeight() > 150) 
        .collect(toList()); 
List<Apple> heavyApples2 = inventory.parallelStream()
        .filter((Apple a) -> a.getWeight() > 150) 
        .collect(toList());
 ```
这里有个问题：在Java 8之前，List<T>并没有stream或parallelStream方法，它实现的Collection<T>接口也没有，没有这些方法，这些代码
就不能编译。

换作你自己的接口的话，最简单的解决方案就是让Java8的设计者把stream方法加入Collection接口，并加入ArrayList类的实现。可要是这样做，对用户来说就是噩梦了。有很多的替代集合框架都用CollectionAPI实现了接口。但给接口加入一个新方法，意味着所有的实体类都必须为其提供一个实现。语言设计者没法控制Collections所有现有的实现，这下你就进退两难了：你如何改变已发布的接口而不破坏已有的实现呢？

Java 8的解决方法就是打破最后一环——接口如今可以包含实现类没有提供实现的方法签名了！那谁来实现它呢？缺失的方法主体随接口提供了（因此就有了默认实现），而不是由实现类提供。

在Java 8里，你现在可以直接对List调用sort方法。它是用Java8 List接口中如下所示的默认方法实现的，它会调用Collections.sort静态方法：
```java
default void sort(Comparator<? super E> c) { 
    Collections.sort(this, c); 
} 
```
这意味着List的任何实体类都不需要显式实现sort，而在以前的Java版本中，除非提供了sort的实现，否则这些实体类在重新编译时都会失败。一个类可以实现多个接口，如果在好几个接口里有多个默认实现，是否意味着Java中有了某种形式的多重继承？是的，在某种程度上是这样。但是后面我们会谈到Java 8用一些限制来避免出现类似于C++中臭名昭著的菱形继承问题。


