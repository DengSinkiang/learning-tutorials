### Netty 是什么

- 异步事件驱动框架，用于快速开发高性能服务端和客户端
- 封装了 JDK 底层 BIO 和 NIO 模型，提供高度可用的 API
- 自带编解码器解决拆包粘包问题，用户只用关心业务逻辑
- 精心设计的 reactor 线程模型支持高并发海量连接
- 自带各种协议栈让你处理任何一种通用协议都几乎不用亲自动手

### Netty 基本组件

- NioEventLoop
- Channel
- ByteBuf
- ChannelHandler
- Pipeline

### Netty 服务端启动

- 创建服务端 Channel
  - bind() [用户代码入口]
    - initAndRegister() [初始化并注册]
      - newChannel() [创建服务端 channel]
        - 反射创建服务端 Channel
        - newSocket() [通过 jdk 来创建底层 jdk channel]
        - NioServerSocketChannelConfig() [tcp 参数配置类]
        - AbstractNioChannel()
          - configureBlocking(false) [阻塞模式]
          - AbstractChannel() [创建 id,unsafe,pipeline]
- 初始化服务端 Channel
  - init() [初始化入口]
    - set ChannelOptions,ChannelAttrs
    - set ChannelOptions,ChildAttrs
    - config handler [配置服务端 pipeline]
    - add ServerBootstrapAcceptor [添加连接器]
- 注册 selector
  - AbstractChannel.register(channel) [入口]
    - this.eventLoop = eventLoop [绑定线程]
    - register0() [实际注册]
      - doRegister() [调用 jdk 底层注册]
      - invokeHandlerAddedIfNeeded()
      - fireChannelRegistered() [传播事件]
-  端口绑定
  - AbstractUnsafe.bind() [入口   ]
    - doBind()
      - javaChannel().bind() [jdk 底层绑定]
    - pipeline.fireChannelActive() [传播事件]
      - HeadContext.readIfIsAutoRead()

