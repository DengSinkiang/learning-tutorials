### 如何理解 Spring 的循环依赖

- 首先在 Spring Framework 整个体系当中，我们的一个Bean是由一个 BeanDefinition 来构建的，BeanDefinition 可以理解为 Spring Bean 的一个建模。然后我们要理解循环依赖的话，我们要先从 Spring Bean 的生命周期来说：一共大体的分为几步，首先我们的 Spring 容器启动扫描，把 Bean Name 变成我们 BeanDefinition，存到我们的 BeanDefinition 中，然后进行遍历，遍历完成之后，对我们的Spring这个BeanDefinition做一些基本的验证是否单例、是否抽象、是否懒加载等等之类的。验证完成之后，那么 Spring 就会去从它要开始实例化这个Bean之前，它会去我们这个容器中，我们的Spring 单例池当中获取一遍，看它存不存在就有没有被创建，如果没有被创建，再去看一下它们有没有存在我们的二级缓存中，有没有被提前暴露，如果都没有，那么我们代码会接着往下执行，创建 X 对象，创建 X 对象之后的话，会对这个 X 对象做一些初始化工作，比如哪些初始化工作：填充属性-在填充属性的过程中，它发现 X 依赖 Y，那么它就会走Y的生命周期流程，和 X 一样，首先它会去对 Y 做验证，然后判断 Y 有没有在单例池中，如果没有，再判断有没有被提前暴露，这个时候Y是没有被提前暴露的，那么Y也会接着往下执行，Y往下执行会把Y给实例化好，实例化好之后，会对Y做一些初始化工作，比如说把Y提前暴露、把Y做属性填充，那么当Y做属性填充的时候，它发现它要填充X，这个时候的话，发现X并没有被完整实例化好，所以不能填充。它要去怎么样呢，走一遍获取X或者创建X的流程，那么又会走刚刚第一次X流程，然后再走X流程过程中，它会发现我们的X已经被提前暴露，所以它可以拿到我们已经提前暴露好的ObjectFactory所产生的X对象，这样就完成了循环依赖。那么这个过程中，你会发现一个问题：Spring的循环依赖只支持单例。为什么只支持单例：因为你如果不支持单例的话，那么第一遍我们的X压根就不会走生命周期流程，因为我们的单例，在Spring容器初始化的时候，就回去走我们的Bean生命周期流程，而我们如果是原型的话，它一开始是不会走的，只有在用到的时候才会走Spring Bean的生命周期流程，所以说Spring的循环依赖是支持单例的，原型也是有办法解决的。然后XY只支持非构造方法的注入，如果说你的XY是通过一个特殊构造方法来注入的，来循环依赖，就是X依赖Y，Y依赖X都是通过X的构造方法，Y的构造方法把对应的X和Y传进去。肯定是不行的。就比如我在第一遍，我在实例化这个X时我需要Y，Y也没有，那么我去拿Y，创建Y的时候我需要X。因为它是构造方法嘛，它不能创建对象

### jwt 三段含义

- 头信息Header：描述JWT基本信息
- 载荷Payload：载荷（也可以叫载体）是具体的传输内容，包括一些标准属性
- 签名信息Signature：对头信息和载荷进行签名，保证传输过程中信息不被篡改

### 为什么HashMap桶长度超过8才会转换成红黑树

- https://notes.daiyuhe.com/bucket-convert-to-red-black-tree-when-8-size/

### redis String 和 hash的优缺点

### 线程和进程的区别

### 什么是死锁、怎么解决死锁

### 什么是事务

### springboot的好处

### 怎么获取线程的执行结果

- FutureTask.get()

### 多列索引

### 乐观锁与悲观锁

- 乐观锁(Optimistic Lock), 顾名思义，就是很乐观，每次去拿数据的时候都认为别人不会修改，所以不会上锁，但是在更新的时候会判断一下在此期间别人有没有去更新这个数据，可以使用版本号等机制
- 悲观锁(Pessimistic Lock), 顾名思义，就是很悲观，每次去拿数据的时候都认为别人会修改，所以每次在拿数据的时候都会上锁，这样别人想拿这个数据就会block直到它拿到锁

### Thread、Runnable、Callable区别

- Thread与Runnable区别
	- 避免单继承的局限，一个类可以继承多个接口
	- 适合于资源的共享
- Callable 和 Runnable 的区别
	- Callable 使用 call（）方法， Runnable 使用 run() 方法
	- call() 可以返回值， 而 run()方法不能返回
	- call() 可以抛出受检查的异常，比如 ClassNotFoundException， 而 run() 不能抛出受检查的异常
	- 执行 Callable 任务可以拿到一个 Future 对象，表示异步计算的结果。它提供了检查计算是否完成的方法，以等待计算的完成，并检索计算的结果。通过Future对象可以了解任务执行情况，可取消任务的执行，还可获取执行结果
	- Callable 的 get 方法会阻塞主线程来等待任务完成。FutureTask 非常适合用于耗时的计算，主线程可以在完成自己的任务后，再去获取结果
	- 加入线程池运行，Runnable 使用 ExecutorService 的 execute 方法，Callable 使用 submit 方法

### Java Future ForkJoinTask 是什么

### ThreadLocal是什么

- https://www.jianshu.com/p/fd416fac513b

### 遍历map的方式

- keySet方式 keySet先获取map的key，然后根据key获取对应的value
- values()方式 通过values()遍历所有的value,但不能遍历key
- map.entrySet()方式 循环map里面的每一对键值对，然后获取key和value
- 使用迭代器，获取key

### 遍历 List 的方式

- foreach 循环
- 下标递增(递减)循环
- 迭代器循环迭代

### excute（）和 submit（）的区别

- 接收的参数不一样
- execute(Runnable x) 没有返回值 submit 有返回值
- submit 方便 Exception 处理 通过捕获 Future.get 抛出的异常
- JDK5 往后，任务分两类：一类是实现了 Runnable 接口的类，一类是实现了 Callable 接口的类。两者都可以被 ExecutorService 执行，它们的区别是：execute(Runnable x) 没有返回值。可以执行任务，但无法判断任务是否成功完成。——实现Runnable接口，submit(Runnable x) 返回一个future。可以用这个future来判断任务是否成功完成。——实现Callable接口

### HashMap为啥链表长度大于8

### mysql 主从复制原理

- 数据库有个bin log二进制文件，记录了所有sql语句
- 我们的目标就是把主数据库的bin log文件的sql语句复制过来
- 让其在从数据库的redo log重做日志文件中再执行一次这些sql语句即可

### int（4）和 int（11）varchar和char区别

- int(M) M指示最大显示宽度 0003代表int（4）
- vachar存储可变长的字符串，比char更节省空间
- varchar需要使用1或者2个额外字节记录字符串长度
- char适合存储很短的字符串

### Integer 取值原因

- https://www.cnblogs.com/biehongli/p/12370693.html

### explain 字段意义

- https://segmentfault.com/a/1190000008131735

### char 和 vachar 的区别

### 布隆过滤器

### redis 数据类型

- string（字符串），hash（哈希），list（列表），set（集合）及zset(sorted set：有序集合)

### 缓存击穿

### 为啥 redis 单线程模型也能效率这么高

- 纯内存操作
- 核心是基于非阻塞的 IO 多路复用机制
- C 语言实现，一般来说，C 语言实现的程序“距离”操作系统更近，执行速度相对会更快
- 单线程反而避免了多线程的频繁上下文切换问题，预防了多线程可能产生的竞争问题

### mysql like 模糊

- ```
  '%a'     //以a结尾的数据
  'a%'     //以a开头的数据
  '%a%'    //含有a的数据
  '_a_'    //三位且中间字母是a的
  '_a'     //两位且结尾字母是a的
  'a_'     //两位且开头字母是a的
  ```

### hashmap的负载因子为什么是0.75

- 用0.75作为加载因子，每个碰撞位置的链表长度超过８个是几乎不可能的

### HashMap的默认长度为什么是16

- 不浪费资源的情况下同时保证hash算法的冲突少 2的幂等保证更均匀 寻址算法进行&运算

### ArrayList 的 contains 方法，自己实现一个 contains 方法

### 数据库 ER 图

### spring bean的注入过程

### 数据库表设计

### rabbitmq 原理，组成

### 基于redis怎么实现分布式锁

### mysql隔离级别，查看隔离级别命令

- 1.查看当前会话隔离级别:select @@tx_isolation;
- 2.查看系统当前隔离级别:select @@global.tx_isolation;

### Springboot原理，自己实现自动配置原理

### LockSupport

- https://baijiahao.baidu.com/s?id=1666548481761194849&wfr=spider&for=pc

### Maven 解决 jar 包冲突

- maven默认根据最短路径、最先声明加载 jar 包
- 我们可以借助 Maven Helper 插件中的 Dependency Analyzer 分析冲突的 jar 包，然后在对应标红版本的 jar 包上面点击 execlude，就可以将该 jar 包排除出去
- 或者手动在 pom.xml 中使用 <exclusion> 标签去排除冲突的 jar 包

### hashMap 的结构图 红黑树理解

### ArrayList 扩容

- ArrayList 扩容 1.5 倍

 - 如果 ArrayList 给定了特定初始容量，则此处需要根据实际情况确定是否调用 grow 方法，即有可能不需要扩容

 - 当第一次调用 add 方法时，集合长度变为 10 和 addAll 内容之间的较大值

 - 如果没有指定初始容量，第一次调用 add 则此处一定需要调用 grow 方法

 ### 什么是二进制日志 (binlog)

- binlog 是记录所有数据库表结构变更（例如 CREATE、ALTER TABLE…）以及表数据修改（INSERT、UPDATE、DELETE…）的二进制日志

### hashMap 的 put() 和 putAll()

- 当 hashmap 中的元素个数超过数组大小 * loadFactor 时，就会进行数组扩容，loadFactor 的默认值为 0.75，也就是说，默认情况下，数组大小为 16，那么当hashmap中元素个数超过 16 * 0.75 = 12 的时候，就把数组的大小扩展为 2 * 16 = 32
- 比如说，我们有 1000 个元素 new HashMap(1000), 但是理论上来讲 new HashMap(1024) 更合适，但是 new HashMap(1024) 还不是更合适的，因为 0.75*1000 < 1000, 也就是说为了让 0.75 * size > 1000, 我们必须这样 new HashMap(2048) 才最合适，既考虑了 & 的问题，也避免了 resize 的问题
- new HashMap(3),会生成一个 4 容量的 map，5->8、10->16

### Redis 主从加哨兵模式
- 主从：用于高并发必须开启 master node 的持久化(全量复制、增量复制、异步复制)
- 哨兵：用于高可用 选举算法：
  - 较低的 slave_priority 按照 slave 优先级进行排序，slave priority 越低，优先级就越高
  - 较大的 replication offset 如果 slave priority 相同，那么看 replication offset，哪个 slave 复制了越多的数据，offset 越靠后，优先级就越高
  - 较小的 runid 如果上面两个条件都相同，那么选择一个 runid 比较小的那个 slave

### MySQL 索引的数据结构：哈希索引、B Tree 索引

### Mysql 存储引擎

### 4G 的 html 文件怎么判断他的内容里都是合法的 html 标签，内存只有 2G

### 设计模式
- 策略模式解决 if else
  - https://www.iteye.com/blog/alaric-1920714

- 有一道 MySQL 的面试题，为什么MySQL的索引要使用 B+ 树而不是其它树形结构?比如B树？
  - 为什么不用 Hash 索引
    - 哈希值是无序的，不利于范围查找，不利于排序
  - 为啥不用平衡二叉树
    - 随着树的高度的增加，查找速度越来越慢
    - 回旋查找很慢，比如查找大于5的数非常多，回旋查找效率就很低
  - 为啥不用 B 树
    - 解决平衡二叉树的树的高度问题，树越矮，查找效率越高
    - 还是存在回旋查找的问题
  - 使用 B+ 树
    - 解决回旋查找的问题，范围查找效率高：**B-树只能依靠**繁琐的**中序遍历**，而**B+树只须要在链表上遍历**便可
    - 非叶子节点置只存储key，叶子节点存储key和value，value 指数据的地址
    - B+Tree: 数据存储在叶子节点，其他节点只存储索引信息----InnoDB
    - B-Tree: 数据存储在各个节点上----InnoDB
  
- Spring Cloud 里的 RPC 怎么实现的

- Spring Bean 相互依赖问题
	- Spring 只能解决单例模式下的 Setter 循环依赖
	- 一个可能的解决方法就是修改源代码，将某些构造器注入改为setter注入。另一个解决方法就是完全放弃构造器注入，只使用setter注入
	
- Linux 基本指令

- 主键索引和唯一索引的区别
	- 主键是一种约束，唯一索引是一种索引
	- 一张表只能有一个主键，但可以创建多个唯一索引
	- 主键创建后一定包含一个唯一索引，唯一索引并不一定是主键
	- 主键不能为 null，唯一索引可以为 null
	- 主键可以做为外键，唯一索引不行
	
- RabbitMQ 异步 上万个消息怎么消费到自己想要的

- go 的协程 

- 哪些地方应用 Redis 缓存，数据缓存一致性，假如删除缓存时发现缓存已经不见了，大量请求积压到 Mysql 怎么解决

### controller 和 service 和 dao 层是单例的吗？
- 是的 可以通过在配置文件的 bean 中添加 scope="prototype" 变成多例
### 线程安全
### != null 和 is not null 区别
- != null 查询结果为空，返回 0 行，没有语法错误
### ArrayList 和 LinkedList 的区别
### 线程安全集合有哪些
- CopyOnWriteArrayList 与 SynchronizedList
- HashTable、Vector、CurrentHashMap
### 怎么优化 SQL 语句
### 比如将 123 转换为 char 类型：SELECT CAST(123 AS CHAR) SELECT CONCAT(123,'')
### 将字符串转为数字：SELECT CONVERT('67',SIGNED)
### 自连接和子查询谁的效率高：自连接
### union all 和 union 的区别
- union all 直接将两个查询结果集合并
- union 把两个结果集合并后进行去重/distinct
### 大量数据插入 mysql 怎么保证数据不会重复
### redis是不是单线程的
- 是的 因为Redis是基于内存的操作，CPU不是Redis的瓶颈，Redis的瓶颈最有可能是机器内存的大小或者网络带宽
### redis 主从 + 哨兵模式
### Spring 事务的传播机制
### ThreadLocal 与 Synchronized区别
- 相同：ThreadLocal和线程同步机制都是为了解决多线程中相同变量的访问冲突问题
- Synchronized用于线程间的数据共享，而ThreadLocal则用于线程间的数据隔离
- 不同：Synchronized同步机制采用了“以时间换空间”的方式，仅提供一份变量，让不同的线程排队访问；而ThreadLocal采用了“以空间换时间”的方式，每一个线程都提供了一份变量，因此可以同时访问而互不影响
- https://www.cnblogs.com/xhyouyou/p/6932286.html
### Mybatis # 和 $ 的区别
- #{}是预编译处理，${}是字符串替换
### 线程池不允许使用 Executors 去创建，而是通过 ThreadPoolExecutor 的方式，这样
的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险。 说明:Executors 返回的线程池对象的弊端如下:
- 1)FixedThreadPool 和 SingleThreadPool:
允许的请求队列长度为 Integer.MAX_VALUE，可能会堆积大量的请求，从而导致 OOM 
- 2)CachedThreadPool 和 ScheduledThreadPool:
允许的创建线程数量为 Integer.MAX_VALUE，可能会创建大量的线程，从而导致 OOM
### tcp 三次握手、四次挥手、滑动窗口
### mysql 索引类型
### 怎么优化sql
### synchronized 和 Lock 的区别
### 聚簇索引和非聚簇索引的区别
### Java 垃圾回收算法
### MySQL 的隔离级别
### 一致性 hash 算法
### synchronized的锁升级
### hashmap 扩容
### MySQL 乐观锁和悲观锁的理解与使用
- https://www.jb51.net/article/180876.htm
### java 多线程中 sleep 和 wait 的4个区别
- sleep是线程中的方法，但是wait是Object中的方法
- sleep方法不会释放锁，但是wait会释放，而且会加入到等待队列中
- sleep方法不依赖于synchronized，但是wait需要依赖synchronized关键字
- sleep不需要被唤醒（休眠之后退出阻塞），但是wait需要（不指定时间需要被别人中断）
### 死锁、活锁与饥饿的区别
- https://segmentfault.com/a/1190000019168229?utm_source=tag-newest
### Java 内存模型
- 堆、方法区由所有线程共享的的数据区
- 虚拟机栈、本地方法栈和程序计数器是线程隔离的数据区
### Java的synchronized的静态同步方法和非静态同步方法的区别
- https://blog.csdn.net/sinat_34341162/article/details/83187172

### Spring 单例 Bean 是线程安全的吗？
### 聊聊对微服务的看法
### mysql索引为什么采用B+树
### 页分裂
### redisson 分布式锁续约，stop the world之后怎么保证继续监听续约
### rabbitmq的选型，为啥选它，RabbitMQ延迟最低，低在哪里
### 消息大量积压，怎么解决
### hashmap的容量为什么是2的幂次方，为啥不直接转红黑树，要先转链表
---
## JVM
- Java 垃圾回收
	- 可达性分析算法来判定对象是否存活
	- 标记-复制算法、标记-清除算法、标记-整理算法
- 类加载过程
	- 加载、验证：文件格式验证、元数据验证、字节码验证、符号引用验证 准备 解析：类和接口的解析、字段解析、方法解析 初始化
- 类加载器
	- Java 虚拟机角度：启动类加载器（bootStrap ClassLoader）、其他所有的类加载器
	- Java 开发人员角度：启动类加载器（bootStrap Class Loader）、扩展类加载器（Extension Class Loader）、应用程序类加载器（Application Class Loader）
- 类加载器的双亲委派模型
	- 当某个类加载器需要加载某个.class文件时，它首先把这个任务委托给他的上级类加载器，递归这个操作，如果上级的类加载器没有加载，自己才会去加载这个类
	- 好处
		- Java 中的类随着它的类加载器一起具备了一种带有优先级的层级关系
		- 例如：Java.lang.Object 类，可以保证Object类在程序的各种类加载环境中都是同一个类

## Java
- 策略模式
	- 抽象策略（Strategy）角色
	- 具体策略（ConcreteStrategy）角色
	- 场景（Context）角色：持有抽象策略类的引用
	- 诸葛亮的锦囊妙计来学习，在不同的场景下赵云打开不同的锦囊，便化险为夷，锦囊便是抽象策略角色，具体的锦囊里面的计策便是具体的策略角色，场景就是赵云，变化的处境选择具体策略的条件
- List、Set、Map 是否有序
	- List 中的元素都是有序的
	- Set 中的元素都是无序的，并且不能重复 LinkedHashSet 是有序的
	- Map 中的元素都是无序的 LinkedHashMap 是有序的
- 线程和进程的区别
	- 进程:是指一个内存中运行的应用程序，每个进程都有一个独立的内存空间，一个应用程序可以同时运行多个进程;进程也是程序的一次执行过程，是系统运行程序的基本单位;系统运行一个程序即是一个进程从创建、运行到消亡的过程
	- 线程:线程是进程中的一个执行单元，负责当前进程中程序的执行，一个进程中至少有一个线程。一个进程中是可以有多个线程的，这个应用程序也可以称之为多线程程序
	- 简而言之:一个程序运行后至少有一个进程，一个进程中可以包含多个线程
- Thread 和 Runnable 的区别
	- 如果一个类 继承了 Thread，则不适合资源共享。但是如果实现了 Runable 接口的话， 则很容易实现资源共享
- 重写 equal 和 hashcode
	- https://baijiahao.baidu.com/s?id=1620197502415557866&wfr=spider&for=pc
- hashMap底层实现原理
	- HashMap 通过 key 的 hashCode 经 过扰动函数处理过后得到 hash 值，然后通过判断当前元素存放的位置(这里的 n 指的是数组的长度)，如果当前位置存在元素的话，就判断该元素与要存入的元素的 hash 值以及 key 是否相同，如果相同的 话，直接覆盖，不相同就通过拉链法解决冲突
	- 使用扰动函数之后可以减少碰撞，扰动函数指的就是 HashMap 的 hash 方法
	- 所谓 “拉链法” 就是:将链表和数组相结合。也就是说创建一个链表数组，数组中每一格就是一个链表。若遇到哈希冲突，则将冲突的值加到链表中即可
	- jdk1.7 数组加链表
	- jdk1.8 数组加链表加红黑树
- HashMap 的长度为什么是2的幂次方
	- 为了能让 HashMap 存取高效，尽量较少碰撞，也就是要尽量把数据分配均匀
	- (n - 1) & hash 
- ArrayList 和 LinkedList 的区别
	- ArrayList Object 数组
	- LinkedList jdk1.6 是一个带有头结点的双向循环链表
	- LinkedList jdk1.7 之后使用的是不带头结点的普通的双向链表
- synchronized 和 Lock 的区别
	- 存在层次上：java 的关键字，在jvm层面
	- 存在层次上：java 接口
	- 锁的释放：以获取锁的代码块执行完毕释放，若是异常，jvm 则会让线程释放锁
	- 锁的释放：在 finally 中必须释放锁 lock.unlock，否则会造成死锁
	- 锁的阻塞：若是 A 线程获得锁，则 B 等待；若是 A 出现阻塞，则 B 一直处于阻塞
	- 锁的阻塞：分情况而定，线程可以尝试获得锁，线程可以不用一直等待，主要是参考 Lock 接口中的 lockInterruptibly() 方法
	- 锁状态：无法判断
	- 锁状态：可以判断，可以通过 trylock（）方法来得知当前线程是否获得锁
	- 锁类型：可重入、非公平、不可中断
	- 锁类型：可重入、公平（亦可非公平）、可中断；ReentrantLock 中默认是非公平锁的
- 谈谈 synchronized 和 ReenTrantLock 的区别
	- ReentrantLock 实现了 Lock 接口
	- ReentrantLock 和 synchronized 基本上都是排它锁，意味着这些锁在同一时刻只允许一个线程进行访问
	- 两者都是可重入锁。自己可以再次获取自己的内部锁。比如一个线程获得了某个对象的锁，此时 这个对象锁还没有释放，当其再次想要获取这个对象的锁的时候还是可以获取的，如果不可锁重入的话，就会造成死锁
	- synchronized 依赖于 JVM 而 ReenTrantLock 依赖于 API
	- ReenTrantLock 比 synchronized 增加了一些高级功能
		- 等待可中断、可实现公平锁、可实现选择性通知(锁可以绑定多个条件)
	- ReenTrantLock 提供了一种能够中断等待锁的线程的机制
	- ReenTrantLock 可以指定是公平锁还是非公平锁。而 synchronized 只能是非公平锁。所谓的公平锁就是先等待的线程先获得锁
- 利用 stream().forEach() 遍历集合
	- list.stream().forEach(item -> {System.out.print(item + " ");});
- 遍历 map
	- map.forEach((key, value) -> {
            System.out.println("Key: " + key + ", Value: " + value);
        });
- HashTable 和 ConcurrentHashMap 的比较
	- 底层数据结构: JDK1.7 的 ConcurrentHashMap 底层采用分段的数组 + 链表实现，JDK1.8 采用的数据结构跟 HashMap 1.8 的结构一样，数组+链表/红黑二叉树。Hashtable 和 JDK1.8 之前的 HashMap 的底层数据结构类似都是采用数组+链表 的形式，数组是 HashMap 的主体，链表则是主要为了解决哈希冲突而存在的
	- 实现线程安全的方式(重要): 1 在JDK1.7的时候，ConcurrentHashMap(分段锁) 对整个桶数组进行了分割分段 (Segment)，每一把锁只锁容器其中一部分数据，多线程访问容器里不同数据段的数据，就不会存在锁竞争，提高并发访问率。到了 JDK1.8 的时候已经摒弃了Segment的概念，而是直接用 Node 数组+链表+红黑树的数据结构来实现，并发控制使用 synchronized 和 CAS 来操作。(JDK1.6以后对 synchronized锁做了很多优化)整个看起来就像是优化过且线程安全的 HashMap，虽然在JDK1.8中还能看到 Segment 的数据结构，但是已经简化了属性，只是为了兼容旧版本;2 Hashtable(同一把锁):使用 synchronized 来保证线程安全，效率非常低下。当一个线程访问同步方法时，其他线程也访问同步方法，可能会进入阻塞或轮询状态，如使用 put 添加元素，另一个线程不能使用 put 添加元素，也不能使用 get，竞争会越来越激烈效率越低
- 线程池的好处：降低资源消耗、提高响应速度、提高线程的可管理性
- Java 线程池有哪几种及区别
	- newCachedThreadPool 创建一个可缓存线程池
	- newFixedThreadPool 创建一个定长线程池
	- newScheduledThreadPool 创建一个定时线程池
	- newSingleThreadExecutor 创建一个单线程化的线程池
	- newWorkStealingPool 创建一个拥有多个任务队列（以便减少连接数）的线程池
- ThreadPoolExecutor 的参数含义 需要注意哪些？
	- https://www.cnblogs.com/cdf-opensource-007/p/8769777.html
	- corePoolSize： 线程池核心线程数
	- maximumPoolSize：线程池最大数
	- workQueue： 线程池所使用的缓冲队列
	- handler： 线程池对拒绝任务的处理策略
- 当调用execute()方法添加一个任务时，线程池会做如下判断：如果正在运行的线程数量小于 corePoolSize，那么马上创建线程运行这个任务；如果正在运行的线程数量大于或等于 corePoolSize，那么将这个任务放入队列；如果这时候队列满了，而且正在运行的线程数量小于 maximumPoolSize，那么还是要创建线程运行这个任务；如果队列满了，而且正在运行的线程数量大于或等于 maximumPoolSize，执行拒绝策略（线程池默认的拒绝策略是抛异常）,那么线程池会抛出异常，告诉调用者“我不能再接受任务了”
- ThreadPoolExecutor handler 拒绝策略
	- //丢弃任务，并抛出 RejectedExecutionException 异常 
	- ThreadPoolExecutor.AbortPolicy
	- //忽略，什么都不发生，即抛弃任务就不管了
	- ThreadPoolExecutor.DiscardPolicy
	- //从队列中踢出最先进入队列（最后一个执行）的任务，然后尝试执行任务
	- ThreadPoolExecutor.DiscardOldestPolicy
	- //使用线程处理该任务
	- ThreadPoolExecutor.CallerRunsPolicy
- BIO、NIO、AIO 的区别
	- https://mp.weixin.qq.com/s/YIcXaH7AWLJbPjnTUwnlyQ
	- BIO 就是服务端创建一个ServerSocket， 然后就是客户端用一个Socket去连接服务端的那个ServerSocket， ServerSocket接收到了一个的连接请求就创建一个Socket和一个线程去跟那个Socket进行通讯
	- BIO 交互的方式是同步、阻塞方式 适用于连接数目比较小且固定的架构 一个连接一个线程
	- 缺点：每次一个客户端接入，都需要在服务端创建一个线程来服务这个客户端，这样大量客户端来的时候，就会造成服务端的线程数量可能达到了几千甚至几万，这样就可能会造成服务端过载过高，最后崩溃死掉
	- NIO 就是一个线程处理大量的客户端的请求，通过一个线程轮询大量的channel，每次就获取一批有事件的channel，然后对每个请求启动一个线程处理即可
	- NIO 交互的方式是同步、非阻塞方式 适用于连接数目多且连接比较短（轻操作）的架构 一个请求一个线程
	- AIO 每个连接发送过来的请求，都会绑定一个 Buffer，然后通知操作系统去完成异步的读，这个时间你就可以去做其他的事情，等到操作系统完成读之后，就会调用你的接口，给你操作系统异步读完的数据。这个时候你就可以拿到数据进行处理，将数据往回写，在往回写的过程，同样是给操作系统一个 Buffer，让操作系统去完成写，写完了来通知你。这俩个过程都有buffer存在，数据都是通过buffer来完成读写
	- AIO 交互的方式是异步、非阻塞方式 适用于连接数目多且连接比较长（重操作）的架构 一个有效请求一个线程
- 动态代理 怎么理解
	- 代理类在程序运行时创建的代理方式被成为动态代理
	- 1.定义接口和实现接口
	- 2.jdk动态代理的实现
- List 底层数组拷贝
	- 扩容：把原来的数组复制到另一个内存空间更大的数组中 当前容量的 1.5 倍进行扩容
	- 添加元素：把新元素添加到扩容以后的数组中
- new HashMap 的过程
- 单例代码编写
	- 已写
- List 实现树的创建遍历
	- 已写
- Java 读写锁
	- ReadWriteLock 接口，只有两个方法，分别是 readLock 和 writeLock
	- ReentrantReadWriteLock 实现了 ReadWriteLock 接口：具有与 ReentrantLock 类似的公平锁和非公平锁的实现：默认的支持非公平锁，对于二者而言，非公平锁的吞吐量优于公平锁、支持重入：读线程获取读锁之后能够再次获取读锁，写线程获取写锁之后能再次获取写锁，也可以获取读锁、锁能降级：遵循获取写锁、获取读锁在释放写锁的顺序，即写锁能够降级为读锁
	- 规则是可以共享读，但只能一个写，总结起来为：读读不互斥，读写互斥，写写互斥
	- 场景：读多写少
- List 实现树的创建遍历
- 死锁的四个条件 怎么解决死锁
	- 互斥条件
	- 循环等待条件
	- 请求与保持条件
	- 不可剥夺条件
	- 破坏其中一个条件（不能破坏互斥条件）
- Class.forName() 与 xxx.class 的区别
	- https://www.cnblogs.com/Marydon20170307/p/9815325.html
## Linux
- linux 查看磁盘空间
	- df -hl 
- Linux基本命令：去除重复行、显示最后两百行

	- sort -n $file | uniq

	- sort -n $file | awk '{if($0!=line)print; line=$0}'

	- sort -n $file | sed '$!N; /^\(.*\)\n\1$/!P; D'

	- tail -n 200 filename
## MySQL
- 索引的优缺点
	- 在经常需要搜索的列上、在经常使用where子句的列上、在经常需要排序的列上、在经常使用连接的列上
	- 将无序的数据变成相对有序的数据（就像查询目录一样）
	- 优点
		- 通过创建唯一性索引，可以保证数据库表中每一行数据的唯一性
		- 可以大大加快数据的检索速度，大大减少检索的数据量
		- 帮助服务器避免排序和临时表
		- 将随机 IO 变为 顺序 IO
	- 缺点
		- 当对表中的数据进行增加、删除和修改的时候，索引也要动态的维护，这样降低了索引的维护速度
		- 索引需要占物理空间，索引越多需要的物理空间就越大
		- 创建和维护索引需要耗费时间，随着数据量的增加而增加
- 三范式
	- 第一范式: 每个列都不可以再拆分. 第二范式: 非主键列完全依赖于主键,而不能是依赖于主键的一部分. 第三范式: 非主键列只依赖于主键,不依赖于其他非主键
- 聚簇索引
	- 是一种数据存储方式 
	- 在同一个结构中保存了B-Tree索引和数据行
	- 数据行实际上存放在索引的叶子页中，节点页只包含了索引列
	- 如果没有定义主键，InnoDB 会选择一个唯一的非空索引代替。如果没有这样的索引，InnoDB会隐式定义一个主键来作为聚簇索引
	- 优点
		- 可以把相关的数据保存在一起
		- 数据访问更快
	- 缺点
		- 插入速度严重依赖于插入顺序
		- 更新聚簇索引列的代价更高
		- 可能面临页分裂问题
		- 肯能导致全表扫描变慢
- 二级索引
	- 二级索引叶子结点保存的是行的主键值
	- 通过二级索引查找行，存储引擎需要找到二级索引的叶子节点获得对应的主键值，然后根据这个值去聚簇索引里查找到对应的行
	- 这里做了重复的工作，两次B-Tree查找。对于 InnoDB，自适应哈希索引能够减少这样的重复工作
- 覆盖索引
	- 如果一个索引包含（或者说覆盖）所有需要查询的字段的值
	- 由于 InnoDB 的聚簇索引，覆盖索引对InnoDB表特别有用。InnoDB 的二级索引在叶子结点保存了行的主键值，所以如果二级主键能够覆盖查询，则可以避免对主键索引的二次查询
- 联合索引
	- 如果我们是在 name 和 age 上分别创建单个索引的话，由于 mysql查询每次只能使用一个索引，所以虽然这样已经相对不做索引时全表扫描提高了很多效率，但是如果在 name、age 两列上创建复合索引的话将带来更高的效率。如果我们创建了 (name, age)的复合索引，那么其实相当于创建了(name)、(name,age) 两个索引，这被称为最左前缀原则
- MySQL 事务的特性：原子性、一致性、隔离性、持久性
- 并发事务可能带来：脏读、幻读、不可重复读、丢失修改
- MySQL 4 种隔离级别：读未提交、读已提交、可重复读、可串行化
- MySQL InnoDB 存储引擎的默认支持的隔离级别是 REPEATABLE-READ（可重读）
- 脏读、幻读、不可重复读
	- 脏读（Dirty read）: 当一个事务正在访问数据并且对数据进行了修改，而这种修改还没有提交到数据库中，这时另外一个事务也访问了这个数据，然后使用了这个数据
	- 不可重复度和幻读区别：不可重复读的重点是修改，幻读的重点在于新增或者删除
- MySQL 的存储引擎及区别
	- 是否支持行级锁 : MyISAM 只有表级锁，而InnoDB 支持行级锁和表级锁,默认为行级锁
	- MyISAM 不提供事务支持，InnoDB 提供事务支持事务
	- 是否支持外键：MyISAM 不支持，而 InnoDB 支持
- InnoDB 和 MyISAM 索引实现原理区别
	- 采用 B+ 树的数据索引结构
	- MyISAM：索引作为单独的文件储存起来，记录了数据的存放地址
	- InnoDB：索引的本身就是数据的一部分，因此，每一个叶子节点都储存了完整的数据

## 框架相关

- Spring 中的bean是线程安全的吗
  - https://www.cnblogs.com/myseries/p/11729800.html

- Springboot的自动配置原理
      - Spring Boot启动的时候会通过@EnableAutoConfiguration注解找到META-INF/spring.factories配置文件中的所有自动配置类，并对其进行加载，而这些自动配置类都是以AutoConfiguration结尾来命名的，它实际上就是一个JavaConfig形式的Spring容器配置类，它能通过以Properties结尾命名的类中取得在全局配置文件中配置的属性如：server.port，而XxxxProperties类是通过@ConfigurationProperties注解与全局配置文件中对应的属性进行绑定的，再通过@EnableConfigurationProperties注解把绑定的JavaBean 注入到Spring容器当中
- Spring Bean 的作用域
	- singleton、prototype、request、session、global-session
- Spring Bean 的生命周期
	- 实例化 -> 属性赋值 -> 初始化 -> 销毁
- Spring 事务中的隔离级别有哪几种
	- TransactionDefinition.ISOLATION_DEFAULT: 使用后端数据库默认的隔离级别，Mysql 默认采用的 REPEATABLE_READ 隔离级别 Oracle 默认采用的 READ_COMMITTED 隔离级别
	- TransactionDefinition.ISOLATION_READ_UNCOMMITTED: 最低的隔离级别，允许读取尚未提交的数据变更，可能会导致脏读、幻读或不可重复读
	- TransactionDefinition.ISOLATION_READ_COMMITTED: 允许读取并发事务已经提交的数据，可以阻止脏读，但是幻读或不可重复读仍有可能发生
	- TransactionDefinition.ISOLATION_REPEATABLE_READ: 对同一字段的多次读取结果都是一致的，除非数据是被本身事务自己所修改，可以阻止脏读和不可重复读，但幻读仍有可能发生
	- TransactionDefinition.ISOLATION_SERIALIZABLE: 最高的隔离级别，完全服从 ACID 的隔离级别。所有的事务依次逐个执行，这样事务之间就完全不可能产生干扰，也就是说，该级别可以防止脏读、不可重复读以及幻读。但是这将严重影响程序的性能。通常情况下也不会用到该级别
- Spring 事务中哪几种事务传播行为
	- 支持当前事务的情况
		- TransactionDefinition.PROPAGATION_REQUIRED： 如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务
		- TransactionDefinition.PROPAGATION_SUPPORTS： 如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行
		- TransactionDefinition.PROPAGATION_MANDATORY： 如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。（mandatory：强制性）
	- 不支持当前事务的情况
		- TransactionDefinition.PROPAGATION_REQUIRES_NEW： 创建一个新的事务，如果当前存在事务，则把当前事务挂起
		- TransactionDefinition.PROPAGATION_NOT_SUPPORTED： 以非事务方式运行，如果当前存在事务，则把当前事务挂起
		- TransactionDefinition.PROPAGATION_NEVER： 以非事务方式运行，如果当前存在事务，则抛出异常
	- 其他情况
		- TransactionDefinition.PROPAGATION_NESTED： 如果当前存在事务，则创建一个事务作为当前事务的嵌套事务来运行；如果当前没有事务，则该取值等价于 TransactionDefinition.PROPAGATION_REQUIRED
- Spring 应用的设计模式
	- 代理模式：Spring AOP 功能的实现
	- 单例模式：在 Spring 配置文件中定义的 Bean 默认为单例模式
	- 工厂模式：可以通过 BeanFactory 或 ApplicationContext 创建 bean 对象

- Java注解 @Resource 和 Spring 注解 @Autowired、@Qualifier 区别对比详解
	- @Resource 默认是按照名称来装配注入的，只有当找不到与名称匹配的 Bean 才会按照类型来装配注入
	- @Autowired 默认是按照类型装配注入的，如果想按照名称来转配注入，则需要结合 @Qualifier 一起使用
- Spring 中 BeanFactory 与 FactoryBean 的区别
	- BeanFactory 是一个接口，它是 Spring 中工厂的顶层规范，是 Spring IOC 容器的核心接口
		- 使用场景
			- 从 IOC 容器中获取 Bean
			- 检索 IOC 容器中是否包含指定的 Bean
			- 判断 Bean 是否为单例
	- FactoryBean 是一个 Bean，但又不仅仅是一个 Bean。它是一个能生产或修饰对象生成的工厂 Bean，类似于设计模式中的工厂模式和装饰器模式。它能在需要的时候生产一个对象，且不仅仅限于它自身，它能返回任何 Bean 的实例
		- 使用场景
			- 创建AOP的代理对象
	- 区别
		- 他们两个都是个工厂，但 FactoryBean 本质上还是一个 Bean，也归 BeanFactory 管理
		- BeanFactory 是 Spring 容器的顶层接口，FactoryBean 更类似于用户自定义的工厂接口
- 说说 BeanFactory 和 ApplicationContext 的区别？什么是延迟实例化，它的优缺点是什么
	- BeanFactory 是 Spring 里面最低层的接口，提供了最简单的容器的功能，只提供了实例化对象和获取对象的功能
	- 应用上下文，继承 BeanFactory 接口，它是 Spring 的一个更高级的容器，提供了更多的有用的功能（1) 国际化（MessageSource）2) 访问资源，如URL和文件（ResourceLoader）3) 载入多个（有继承关系）上下文 ，使得每一个上下文都专注于一个特定的层次，比如应用的web层 4) 消息发送、响应机制（ApplicationEventPublisher）5) AOP（拦截器）
	- 两者装载 Bean 的区别 
		- BeanFactory ：在启动时不会去实例化 Bean ，只有从容器中获取 Bean 时才会去实例化
		- ApplicationContext：在启动的时候就把所有的 Bean 全部实例化了 。 它还可以为 Bean 配置 lazy-init=true 来让 Bean 延迟实例化
	- 延迟实例化
		- 优点：应用启动时占用资源很少，对资源要求较高的应用，比较有优势
		- 缺点：速度会相对来说慢一些。而且有可能会出现空指针异常的错误，而且通过 Bean 工厂创建的 Bean 生命周期会简单一些。 所有的 Bean 在启动的时候都加载，系统运行的速度快，而且可以尽早的发现系统中的配置问题
		- 建议 Web 应用，在启动的时候就把所有的 Bean 都加载了
- Spring IOC 实现原理
	- 控制反转也叫依赖注入。利用 java 反射机制，将对象交给容器处理，你只需要在 Spring 配置文件中配置对应的 Bean 以及设置相关的属性，让 Spring 容器来生成类的实例对象以及管理对象
- Spring AOP 实现原理
	- 利用代理模式。一种是基于 JDK 的动态代理，一种是基于 CGLIB 的动态代理
	- 通过配置可以实现将业务逻辑和系统服务分离，业务逻辑只需要关心业务的处理而不去处理其他事情
- Spring中配置的 Bean 是在什么时候实例化的
	- 默认容器在加载的时候初始化 Bean，但是也可以通过设置lazy-init 属性来延迟加载
- 谈谈 IOC、AOP 和 DI 在项目开发中的应用场景
	- IOC：项目中的 Bean 都可以交给 Spring 容器来维护，这样 Bean 的创建及销毁以及生命周期都由 Spring 来处理
	- AOP：事务、日志、权限等
	- DI：比如 Sevice 层需要调用 Dao 层访问数据库，这时可以将 Dao 层的 Bean 给 Spring 管理，我们只需要在 Service 中定义对应的方法来接收由 Spring 负责注入的 Dao 层的 Bean 即可
- Spring 管理事务的方式
	- 编程式事务，在代码中硬编码（不推荐使用）
	- 声明式事务，在配置文件中配置
		- 基于 tx 和 aop 名字空间的 xml 配置文件
		- 基于 @Transactional 注解 
- Spring MVC 的流程
	- 客户端发送请求至前端控制器-> 前端控制器 DispatcherServlet 接受客户端请求 -> 找到处理器映射 HandlerMapping 解析请求对应的 Handler-> HandlerAdapter 会根据 Handler 来调用真正的处理器开处理请求，并处理相应的业务逻辑 -> 处理器返回一个模型视图 ModelAndView -> 视图解析器进行解析 -> 返回一个视图对象->前端控制器 DispatcherServlet 渲染数据(Model)->将得到视图对象返回给用户
- Spring MVC 常用注解及其作用
	- @RequestMapping 请求和方法映射
	- @RequestBody 接受传入的 JSON 数据
	- @Controller 标识控制层
	- @Service 标识业务层
	- @Repository 标识数据层
	- @Component 把 Bean 添加到 Spring 容器中
	- @Autowired 按照类型自动注入
	- @Resource 自定义注入，既可以按照类型注入，又可以按照名字注入
- Mybatis 编程步骤
	- A.创建 SQLSessionFactory
	- B.通过 SQLSessionFactory 创建 SQLSession
	- C.通过 SQLSession 执行数据库操作
	- D.调用 session.commit() 提交事务
	- E.调用 session.close() 关闭会话
- Mybatis 的一级缓存和二级缓存
	- 一级缓存是 SqlSession 级别的缓存，只要 SqlSession 没有 flush 或 close，它就存在
	- 二级缓存是 mapper 映射级别的缓存，多个 SqlSession 去操作同一个 Mapper 映射的 sql 语句，多个 SqlSession 可以共用二级缓存，二级缓存是跨 SqlSession 的
- Mybatis 的常用注解说明
	- @Insert:实现新增 
	- @Update:实现更新
	- @Delete:实现删除 
	- @Select:实现查询 
	- @Result:实现结果集封装
	- @Results:可以与@Result 一起使用，封装多个结果集 
	- @ResultMap:实现引用@Results 定义的封装
	- @One:实现一对一结果集封装 
	- @Many:实现一对多结果集封装 
	- @SelectProvider: 实现动态SQL映射 
	- @CacheNamespace:实现注解二级缓存的使用
- MyBatis 延迟加载
	- 延迟加载: 就是在需要用到数据时才进行加载，不需要用到数据时就不加载数据。延迟加载也称懒加载
		- 好处: 先从单表查询，需要时再从关联表去关联查询，大大提高数据库性能，因为查询单表要比关联查询多张表速度要快
		- 因为只有当需要用到数据时，才会进行数据库查询，这样在大批量数据查询时，因为查询工作也要消耗时间，所以可能造成用户等待时间变长，造成用户体验下降
	- 使用 assocation 实现延迟加载
	- 使用 collection 实现延迟加载
- MyBatis 的接口绑定，有哪些实现方式
	- 就是在 MyBatis 中任意定义接口,然后把接口里面的方法和 SQL 语句绑定, 我们直接调用接口方法就可以,这样比起原来了 SqlSession 提供的方法我们可以有更加灵活的选择和设置
	- 一种是通过注解绑定，就是在接口的方法上面加上
@Select、@Update 等注解，里面包含 Sql 语句来绑定
	- 一种就是通过 xml 里面写 SQL 来绑定, 在这种情况下,要指定 xml 映射文件里面的 namespace 必须
为接口的全路径名
	- 当 Sql 语句比较简单时候,用注解绑定, 当 SQL 语句比较复杂
时候,用 xml 绑定,一般用 xml 绑定的比较多
- Mybatis 基于注解的二级缓存
	- 在 SqlMapConfig 中开启二级缓存支持
		- < setting name="cacheEnabled" value="true" />
	- 在持久层接口中使用注解配置二级缓存
		- @CacheNamespace(blocking=true) // mybatis 基于注解方式实现配置二级缓存
- 谈谈你对 SSM 的理解，他们是如何协作完成功能开发的
	- Spring 负责协调，实现业务对象管理，也就是负责不同层面的衔接
	- Spring MVC 负责请求的转发和视图的管理
	- MyBatis 负责数据库的相关操作

## Redis
- 简单总结：
	- Redis 具备了一定的原子性，但不支持回滚。
	- Redis 不具备 ACID 中一致性的概念。(或者说 Redis 在设计时就无视这点)
	- Redis 具备隔离性
	- Redis 通过一定策略可以保证持久性
## 分布式锁

- 可靠的分布式锁，要具备以下几个特性
  - 互斥性。在任意时刻，只有一个客户端能持有锁
  - 不会发生死锁
  - 具有容错性
  - 解铃还须系铃人
  - 锁不能自己失效

- Redisson 分布式锁
  
    - 原理：https://www.cnblogs.com/kiko2014551511/p/11527108.html
- 加锁流程
  
    - 首先判断这个key是否存在，如果存在就匹配这个value，如果存在，它就会判断这是一个重入锁，ta就执行hincrby重入次数加1，并用pexpire设置失效时间，然后返回一个空值，加锁成功。如果value不匹配，说明锁被其他线程占用，用过pttl获取key的剩余时间并返回。如果key不存在，说明这个锁没有被占用，就会执行 hset key UUID + threadId 设置键值并初始化重入次数为1，并用pexpire设置失效时间，最后返回空值
- 解锁流程
    - 如果分布式锁的key存在，但是value不匹配，表示锁已经被其他线程占用，无权释放锁。那么直接返回空值
    - 如果value匹配，则就是当前线程占有分布式锁，那么将重入次数减1，重入次数减1之后如果大于0，表示分布式锁有被重入过，那么只能更新失效时间，还不能删除。重入次数减1后如果为0，这时就可以删除这个key，并发布解锁消息，返回1
    - 如果key不存在，广播锁释放消息（通知阻塞等待的线程或进程资源可用）
  - 没实现容错性缺陷：如果 master实例宕机的时候，可能导致多个客户端同时完成加锁，可使用红锁解决
    - https://blog.csdn.net/u012881584/article/details/105673035
    - RedLock算法思想，意思是不能只在一个redis实例上创建锁，应该是在多个redis实例上创建锁，**n / 2 + 1**，必须在大多数redis节点上都成功创建锁，才能算这个整体的RedLock加锁成功，避免说仅仅在一个redis实例上加锁而带来的问题
    - 基于RedLock思想，遍历所有的Redis客户端，然后依次加锁，最后统计成功的次数来判断是否加锁成功

- 基于 zookeeper 实现分布式锁
  
  - 首先一个客户端连接到这个 zk，然后它创建一个临时有序节点，判断它锁创建的节点是不是最小的，如果是最小的就获取到这个锁，然后执行自己的任务，执行完成之后这个节点自动删除，因为它是临时节点。它释放锁之后其他客户端就开始抢锁。如果不是最小的节点，那么它就获取不到这个锁，就会去watch 监听比自己小的前一个节点
- 基于 redis 的分布式锁
    - 没有实现容错性和锁不能自己失效
    - 思路：在 redis 中设置一个值表示加了锁，然后释放锁的时候就把这个key删除
    - SET anyLock unique_value NX PX 30000	
    - 这里设置的超时时间是30s，假如我超过30s都还没有完成业务逻辑的情况下，key会过期，其他线程有可能会获取到锁
- ###  redis 分布式锁和 zk 分布式锁的对比
  - redis 分布式锁，其实**需要自己不断去尝试获取锁**，比较消耗性能
  - zk 分布式锁，获取不到锁，注册个监听器即可，不需要不断主动尝试获取锁，性能开销较小
  - 如果是 redis 获取锁的那个客户端 出现 bug 挂了，那么只能等待超时时间之后才能释放锁；而 zk 的话，因为创建的是临时 znode，只要客户端挂了，znode 就没了，此时就自动释放锁

- 为什么要用缓存
	- 高性能：对于一些需要复杂操作耗时查出来的结果，且确定后面不怎么变化，但是有很多读请求，那么直接将查询出来的结果放在缓存中，后面直接读缓存就好
	- 高并发：缓存是走内存的，内存天然就支撑高并发。要是你有个系统，高峰期一秒钟过来的请求有 1 万，那一个 mysql 单机绝对会死掉。你这个时候就只能上缓存，把很多数据放缓存，别放 mysql
	
- 如何保证缓存与数据库的双写一致性
	- 读请求和写请求串行化，串到一个内存队列里去。串行化可以保证一定不会出现不一致的情况，但是它也会导致系统的吞吐量大幅度降低，用比正常情况下多几倍的机器去支撑线上的一个请求
	- 缓存 + 数据库读写的模式：读的时候，先读缓存，缓存没有的话，就读数据库，然后取出数据后放入缓存，同时返回响应
	
- 缓存雪崩
	- 对于系统 A，假设每天高峰期每秒 5000 个请求，本来缓存在高峰期可以扛住每秒 4000 个请求，但是缓存机器意外发生了全盘宕机。缓存挂了，此时 1 秒 5000 个请求全部落数据库，数据库必然扛不住，它会报一下警，然后就挂了。此时，如果没有采用什么特别的方案来处理这个故障，DBA 很着急，重启数据库，但是数据库立马又被新的流量给打死了
	- 解决方法
		- 事前：redis 高可用，主从+哨兵，redis cluster，避免全盘崩溃
		- 事中：本地 ehcache 缓存 + hystrix 限流 & 降级，避免 MySQL 被打死
		- 事后：redis 持久化，一旦重启，自动从磁盘上加载数据，快速恢复缓存数据

- 缓存穿透
	- 对于系统 A，假设一秒 5000 个请求，结果其中 4000 个请求是黑客发出的恶意攻击。黑客发出的那 4000 个攻击，缓存中查不到，每次你去数据库里查，也查不到
	- 解决方法
		- 每次系统 A 从数据库中只要没查到，就写一个空值到缓存里去，比如 set -999 UNKNOWN。然后设置一个过期时间，这样的话，下次有相同的 key 来访问的时候，在缓存失效之前，都可以直接从缓存中取数据

- 缓存击穿
	- 某个 key 非常热点，访问非常频繁，处于集中式高并发访问的情况，当这个 key 在失效的瞬间，大量的请求就击穿了缓存，直接请求数据库，就像是在一道屏障上凿开了一个洞
	- 解决方法
		- 若缓存的数据是基本不会发生更新的，则可尝试将该热点数据设置为永不过期
		- 若缓存的数据更新不频繁，且缓存刷新的整个流程耗时较少的情况下，则可以采用基于 redis、zookeeper 等分布式中间件的分布式互斥锁，或者本地互斥锁以保证仅少量的请求能请求数据库并重新构建缓存，其余线程则在锁释放后能访问到新缓存
		- 若缓存的数据更新频繁或者缓存刷新的流程耗时较长的情况下，可以利用定时线程在缓存过期前主动的重新构建缓存或者延后缓存的过期时间，以保证所有的请求能一直访问到对应的缓存
	
- Redis 的事务
## Spring Cloud
- https://mp.weixin.qq.com/s?__biz=MzU0OTk3ODQ3Ng==&mid=2247483712&idx=1&sn=4cd88761830428a2e485ac4c2cf120f9&chksm=fba6e943ccd16055344222ce9c794358e1a4a84fdf4263eaa7c91e9756597bd06e49f9b390cb&mpshare=1&scene=1&srcid=0114TcijHvWTvil1482b1oiI&sharer_sharetime=1579009503981&sharer_shareid=ccdc378560958fbd76d9578a2d4fd6e0#rd
### Eureka
- Eureka 是微服务架构中的注册中心，专门负责服务的注册与发现
- Eureka Client：负责将这个服务的信息注册到Eureka Server中
- Eureka Server：注册中心，里面有一个注册表，保存了各个服务所在的机器和端口号
- Eureka：就是服务注册中心（可以是一个集群），对外暴露自己的地址
- 提供者：启动后向Eureka注册自己信息（地址，提供什么服务）
- 消费者：向Eureka订阅服务，Eureka会将对应服务的所有提供者地址列表发送给消费者，并且定期更新
- 心跳(续约)：提供者定期通过http方式向Eureka刷新自己的状态
- 总结：各个服务启动时，Eureka Client都会将服务注册到Eureka Server，并且Eureka Client还可以反过来从Eureka Server拉取注册表，从而知道其他服务在哪里
### Feign
- Feign 的一个关键机制就是使用了动态代理
- 首先，如果你对某个接口定义了@FeignClient 注解，Feign ß就会针对这个接口创建一个动态代理
- 接着你要是调用那个接口，本质就是会调用 Feign创建的动态代理
- Feign 的动态代理会根据你在接口上的 @RequestMapping 等注解，来动态构造出你要请求的服务的地址
- 最后针对这个地址，发起请求、解析响应
- 总结：基于 Feign 的动态代理机制，根据注解和选择的机器，拼接请求 URL 地址，发起请求
### Ribbon
- 它的作用是负载均衡，会帮你在每次请求时选择一台机器，均匀的把请求分发到各个机器上
- Ribbon的负载均衡默认使用的最经典的 Round Robin 轮询算法
	- 就是如果订单服务对库存服务发起 10 次请求，那就先让你请求第1台机器、然后是第 2 台机器、第 3 台机器、第 4 台机器、第 5 台机器，接着再来—个循环，第 1 台机器、第 2 台机器。。。以此类推
- Ribbon 是怎么和 Feign 以及 Eureka 紧密协作，完成工作的
	- 首先Ribbon会从 Eureka Client里获取到对应的服务注册表，也就知道了所有的服务都部署在了哪些机器上，在监听哪些端口号
	- 然后Ribbon就可以使用默认的Round Robin算法，从中选择一台机器
	- Feign就会针对这台机器，构造并发起请求
- 总结：服务间发起请求的时候，基于Ribbon做负载均衡，从一个服务的多台机器中选择一台
### Hystrix
- 熔断和降级
- 总结：发起请求是通过Hystrix的线程池来走的，不同的服务走不同的线程池，实现了不同服务调用的隔离，避免了服务雪崩的问题

### Zuul
- 负责网络路由
- 总结：如果前端、移动端要调用后端系统，统一从Zuul网关进入，由Zuul网关转发请求给对应的服务
## RabbitMQ
- 如何保证消息的可靠性传输
	- 生产者这块避免数据丢失，1.开启 RabbitMQ 事务（同步不推荐）2.都是用 confirm 机制的 开启 confirm 模式（异步推荐）
	- 开启 RabbitMQ 的持久化
	- 关闭 RabbitMQ 自动 ACK，通过一个 api 来调用就行，然后每次你自己代码里确保处理完的时候，再在程序里 ack 一把
- 如何保证消息队列的高可用
	- 镜像集群模式:在镜像集群模式下，你创建的 queue，无论元数据还是 queue 里的消息都会存在于多个实例上，就是说，每个 RabbitMQ 节点都有这个 queue 的一个完整镜像，包含 queue 的全部数据的意思。然后每次你写消息到 queue 的时候，都会自动把消息同步到多个实例的 queue 上
	- 开启镜像集群模式：RabbitMQ 有很好的管理控制台，就是在后台新增一个策略，这个策略是镜像集群模式的策略，指定的时候是可以要求数据同步到所有节点的，也可以要求同步到指定数量的节点，再次创建 queue 的时候，应用这个策略，就会自动将数据同步到其他的节点上去
- 如何保证消息的顺序性
	- 拆分多个 queue，每个 queue 一个 consumer，就是多一些 queue 而已，确实是麻烦点；或者就一个 queue 但是对应一个 consumer，然后这个 consumer 内部用内存队列做排队，然后分发给底层不同的 worker 来处理
- 设计一个消息队列
  - 支持可伸缩性：需要时快速扩容，可以增加吞吐量和容量
  - mq 的数据落地磁盘：磁盘顺序读写，避免磁盘随机读写的寻址开销
  - mq 的可用性：参照 kafka 的高可用保障机制
  - mq 的数据 0 丢失：kafka 数据的零丢失方案
## ES

- es 核心概念 vs db 核心概念

|    es    |    db    |
| :------: | :------: |
|  index   |  数据库  |
|   type   |  数据表  |
| docuemnt | 一行数据 |

- 倒排索引
  - 倒排索引就是**关键词到文档** ID 的映射，每个关键词都对应着一系列的文件，这些文件中都出现了关键词

## Dubbo

- dubbo 工作原理

  - 第一层：service 层，接口层，给服务提供者和消费者来实现的
  - 第二层：config 层，配置层，主要是对 dubbo 进行各种配置的
  - 第三层：proxy 层，服务代理层，无论是 consumer 还是 provider，dubbo 都会给你生成代理，代理之间进行网络通信
  - 第四层：registry 层，服务注册层，负责服务的注册与发现
  - 第五层：cluster 层，集群层，封装多个服务提供者的路由以及负载均衡，将多个实例组合成一个服务
  - 第六层：monitor 层，监控层，对 rpc 接口的调用次数和调用时间进行监控
  - 第七层：protocal 层，远程调用层，封装 rpc 调用
  - 第八层：exchange 层，信息交换层，封装请求响应模式，同步转异步
  - 第九层：transport 层，网络传输层，抽象 mina 和 netty 为统一接口
  - 第十层：serialize 层，数据序列化层

- 一次 rpc 请求的流程

  - 第一步：provider 向注册中心去注册
  - 第二步：consumer 从注册中心订阅服务，注册中心会通知 consumer 注册好的服务
  - 第三步：consumer 调用 provider
  - 第四步：consumer 和 provider 都异步通知监控中心

- 注册中心挂了可以继续通信吗

  - 可以，因为刚开始初始化的时候，消费者会将提供者的地址等信息**拉取到本地缓存**，所以注册中心挂了可以继续通信

## redis 分布式锁和 zk 分布式锁的对比

- redis 分布式锁，其实**需要自己不断去尝试获取锁**，比较消耗性能
- zk 分布式锁，获取不到锁，注册个监听器即可，不需要不断主动尝试获取锁，性能开销较小。另外一点就是，如果是 redis 获取锁的那个客户端 出现 bug 挂了，那么只能等待超时时间之后才能释放锁；而 zk 的话，因为创建的是临时 znode，只要客户端挂了，znode 就没了，此时就自动释放锁

## 数据结构

- 红黑树
	- 根结点是黑的，每个叶结点（nil[T]）是黑的（黑头黑脚）
	- 如果一个结点是红的，那么它的两个儿子是黑的（坑爹）
	- 对每个结点，从该结点到其子孙结点的所有路径上包含相同数目的黑结点（一个都不能少）
- B-树
	- 根结点至少有两个子女
	- 每个中间节点都包含k-1个元素和k个孩子，其中 m/2 <= k <= m
	- 所有的叶子结点都位于同一层
	- 每个节点中的元素从小到大排列，节点当中k-1个元素正好是k个孩子包含的元素的值域分划
- B+树
	- 特征
		- 有k个子树的中间节点包含有k个元素（B树中是k-1个元素），每个元素不保存数据，只用来索引，所有数据都保存在叶子节点
		- 所有的叶子结点中包含了全部元素的信息，及指向含这些元素记录的指针，且叶子结点本身依关键字的大小自小而大顺序链接
		- 所有的中间节点元素都同时存在于子节点，在子节点元素中是最大（或最小）元素
		- 数据都只会存在叶子节点，非叶子节点不保存数据，只负责查找
		- B+树叶子节点是有顺序的，之间有相互的连接
	- 优势
		- ①IO次数更少 ②查询性能很稳定 ③范围查询更简便
## 计算机网络
- 三次握手
	- 客户端–发送带有 SYN 标志的数据包–一次握手–服务端
	- 服务端–发送带有 SYN/ACK 标志的数据包–二次握手–客户端
	- 客户端–发送带有 ACK 标志的数据包–三次握手–服务端
- 四次挥手
	- 任何一方都可以在数据传送结束后发出连接释放的通知，待对方确认后进入半关闭状态。当另一方也没有数据再发送的时候，则发出连接释放通知，对方确认后就完全关闭了TCP连接
	- 举个例子：A 和 B 打电话，通话即将结束后，A 说“我没啥要说的了”，B回答“我知道了”，但是 B 可能还会有要说的话，A 不能要求 B 跟着自己的节奏结束通话，于是 B 可能又巴拉巴拉说了一通，最后 B 说“我说完了”，A 回答“知道了”，这样通话才算结束
### volatile 的特性
- 可见性：对一个volatile变量的读，总是能看到（任意线程）对这个volatile变量最后的写入
- 原子性：对任意单个volatile变量的读/写具有原子性，但类似于volatile++这种复合操作不具有原子性
### spring 和 spring boot 的区别
### explain 优化
### mysql 索引结构为啥用 b+ 树
### Spring security 的原理
### AOP 和 IOC 的原理
### 基于 JDK 动态代理和 CGLIB 动态代理的区别
### spring boot 的原理
## 笔试题
- 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效

  ```
  class Solution {
      public boolean isValid(String s) {
          Stack<Character> stack= new Stack<Character>();
          for(char c:s.toCharArray()){
              if(c=='(')
                  stack.push(')');
             else if(c=='[')
                  stack.push(']');
             else if(c=='{')
                  stack.push('}');
              else if(stack.isEmpty()||stack.pop()!=c)
                  return false;    
          }       
          return stack.isEmpty();    
      }
  }
  ```

- 找到一个无序数组中找两个特定数，使其相加等于特定数字，请写代码java将它找出来，并指出时间复杂度。例如 【10,25,19,89,75,56,34,54,16，9，-5】找到相加等于28的【19,9 】

  ```
  Map<Integer, Integer> map = new HashMap<>();
  for (int i = 0; i < n; i++) {
   map.put(arr[i], 1);
  }
  for (int i = 0; i < n; i++) {
       int other = num - arr[i];
     Integer value = map.get(other);
       if (value != null) {
         System.out.println("i:" + arr[i] + "j:" + other); 
       }
  }
  ```

  

