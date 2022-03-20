# LuckyRpc



RPC Framework implement by Netty and Spring.



modules description:

* lucky-rpc-core: provide basic operation for LuckyRpc such as transport data format in package `cn.luckycurve.common`, load balance strategy to finish in `cn.luckycurve.common`, `cn.luckycurve.zookeeper` & `cn.luckycurve.protocol` to upload computer info to register center etc.
* lucky-rpc-server: finish server start up and synchronized with Spring IoC setup, main Class is `cn.luckycurve.server`, in this class finish ChannelPipeline construct and health info upload, provide some feature like auth protect(`cn.luckycurve.server.handler.AuthServerHandler`), idle check(`cn.luckycurve.server.handler.ServerIdleCheckHandler`) and put class annotated `@RpcService` info to registry center to wait consume.
* lucky-rpc-client: finish client start up and synchronized with Spring IoC setup, when IoC setup, enhance the field which annotated by `@RpcAutowired`, get server provider info from register center and when we call this field method, it actually call remote process and return result.
* lucky-rpc-test-*: best practices for using LuckyRpc



## Note:



provide configuration property file in classpath named `rpc.properties`, it can be empty but must exist, if it's not exist, use default value

options:

|                Key                |                    Value                     |       Default        |
| :-------------------------------: | :------------------------------------------: | :------------------: |
|          rpc.server.port          |   server port for send and receive message   |         8090         |
|     rpc.server.auth.username      |     username for auth in server endpoint     |        Lucky         |
|     rpc.server.auth.password      |     password for auth in server endpoint     |        Curve         |
|      rpc.loadBalance.cpuRate      |   cpu rate for calculate loadbalance score   |          1           |
|    rpc.loadBalance.memoryRate     | memory rate for calculate loadbalance score  |          1           |
|      rpc.idleCheck.heartTime      |     heart time for keep connection alive     |          5           |
|        rpc.zookeeper.host         |            zookeeper host address            |      localhost       |
|        rpc.zookeeper.port         |                zookeeper port                |         2181         |
|  rpc.zookeeper.ZkSessionTimeout   |              ZK session timeout              |         5000         |
| rpc.zookeeper.ZkConnectionTimeout |            ZK Connection timeout             |         5000         |
|    rpc.zookeeper.registryPath     |               ZK registry path               |      /registry       |
|      rpc.zookeeper.dataPath       |                 ZK data path                 | registryPath + /data |
|      rpc.zookeeper.namespace      | ZK namespace for many client currently visit |      lucky-rpc       |
|                                   |                                              |                      |

> heartTime means that in TCP transport protocol achieve Tcp connection keepalive to reduce the cost for establish and release. Server endpoint do check when pass 3 * heartTime, Client enpoint to keep this connection will send Empty Request when pass heartTime





### Transport Data Structure



Message:

- MessageHeader

```
  						MessageHeader
  +-------------------+------------------+------------------+
  |      version      |      opCode      |     streamId     |
  +-------------------+------------------+------------------+
  |                   |                  |                  |
  0        <=         4         <=       8         <=       16
```

* MessageBody（many implements, it depend on opcode, through opcode to create MessageBody Implements），can see class `cn.luckycurve.common.OperationType` to get correspondence from opCode to RequestBody and ResponseBody

