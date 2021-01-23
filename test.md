
daemonize yes                       > 设置后台启动，一般设置yes
pidfile /var/run/redis.pid         > edis以守护进程方式运行时,redis默认会把pid写入/var/run/redis.pid文件
port 6379                  > 默认端口为6379
bind 127.0.0.1               >主机地址，设置0.0.0.0表示都可以访问。127.0.0.1表示只允许本机访问
timeout 900                 > 客户端闲置多长时间后关闭连接，如果指定为0，表示关闭该功能

logfile stdout               > 日志记录方式，默认为标准输出
logfile "./redis7001.log"          > 指明日志文件名
databases 16                > 设置数据库的数量，默认数据库为0

save                    > 有多少次更新操作，就将数据同步到数据文件
                      > Redis默认配置文件中提供了三个条件：
 save 900 1                 > 900秒（15分钟）内有1个更改
 save 300 10                > 300秒（5分钟）内有10个更改
 save 60 10000               > 60秒内有10000个更改

rdbcompression yes             > 指定存储至本地数据库时是否压缩数据
dbfilename dump.rdb             > 指定本地数据库文件名
dir ./                   > 指定本地数据库存放目录
slaveof                   > 主从同步设置，设置主数据库的ip和端口

tcp-keepalive 60              > 如果非零，则设置SO_KEEPALIVE选项来向空闲连接的客户端发送ACK
stop-writes-on-bgsave-error yes       > 默认如果开启RDB快照(至少一条save指令)并且最新的后台保存失败，Redis将会停止接受写操作
                      > 这将使用户知道数据没有正确的持久化到硬盘，否则可能没人注意到并且造成一些灾难

rdbcompression yes             > 当导出到 .rdb 数据库时是否用LZF压缩字符串对象
rdbchecksum yes               > 版本5的RDB有一个CRC64算法的校验和放在了文件的最后。这将使文件格式更加可靠。

dbfilename dump-master.rdb         > 持久化数据库的文件名

dir /usr/local/redis-4.0.8/redis_master/  > 工作目录

masterauth testmaster123          > slav服务连接master的密码


slave-serve-stale-data yes         > 当一个slave失去和master的连接，或者同步正在进行中，slave的行为可以有两种：
                         1) 如果 slave-serve-stale-data 设置为 "yes" (默认值)，slave会继续响应客户端请求，可能是正常数据，或者是过时了的数据，也可能是还没获得值的空数据。
                         2) 如果 slave-serve-stale-data 设置为 "no"，slave会回复"正在从master同步（SYNC with master in progress）"来处理各种请求，除了 INFO 和 SLAVEOF 命令。
> 配置是否仅读
slave-read-only yes
 如果你选择“yes”Redis将使用更少的TCP包和带宽来向slaves发送数据。但是这将使数据传输到slave上有延迟，Linux内核的默认配置会达到40毫秒
 如果你选择了 "no" 数据传输到salve的延迟将会减少但要使用更多的带宽
repl-disable-tcp-nodelay no
# slave的优先级，优先级数字小的salve会优先考虑提升为master
slave-priority 100
# 密码验证
requirepass testmaster123
# redis实例最大占用内存，一旦内存使用达到上限，Redis会根据选定的回收策略（参见：
# maxmemmory-policy）删除key
maxmemory 3gb
# 最大内存策略：如果达到内存限制了，Redis如何选择删除key。
# volatile-lru -> 根据LRU算法删除带有过期时间的key。
# allkeys-lru -> 根据LRU算法删除任何key。
# volatile-random -> 根据过期设置来随机删除key, 具备过期时间的key。 
# allkeys->random -> 无差别随机删, 任何一个key。 
# volatile-ttl -> 根据最近过期时间来删除（辅以TTL）, 这是对于有过期时间的key 
# noeviction -> 谁也不删，直接在写操作时返回错误。
maxmemory-policy volatile-lru
# AOF开启
appendonly no
# aof文件名
appendfilename "appendonly.aof"
# fsync() 系统调用告诉操作系统把数据写到磁盘上，而不是等更多的数据进入输出缓冲区。
# 有些操作系统会真的把数据马上刷到磁盘上；有些则会尽快去尝试这么做。
# Redis支持三种不同的模式：
# no：不要立刻刷，只有在操作系统需要刷的时候再刷。比较快。
# always：每次写操作都立刻写入到aof文件。慢，但是最安全。
# everysec：每秒写一次。折中方案。 
appendfsync everysec
# 如果AOF的同步策略设置成 "always" 或者 "everysec"，并且后台的存储进程（后台存储或写入AOF
# 日志）会产生很多磁盘I/O开销。某些Linux的配置下会使Redis因为 fsync()系统调用而阻塞很久。
# 注意，目前对这个情况还没有完美修正，甚至不同线程的 fsync() 会阻塞我们同步的write(2)调用。
# 为了缓解这个问题，可以用下面这个选项。它可以在 BGSAVE 或 BGREWRITEAOF 处理时阻止主进程进行fsync()。
# 这就意味着如果有子进程在进行保存操作，那么Redis就处于"不可同步"的状态。
# 这实际上是说，在最差的情况下可能会丢掉30秒钟的日志数据。（默认Linux设定）
# 如果你有延时问题把这个设置成"yes"，否则就保持"no"，这是保存持久数据的最安全的方式。
no-appendfsync-on-rewrite yes
# 自动重写AOF文件
auto-aof-rewrite-percentage 100
auto-aof-rewrite-min-size 64mb
# AOF文件可能在尾部是不完整的（这跟system关闭有问题，尤其是mount ext4文件系统时
# 没有加上data=ordered选项。只会发生在os死时，redis自己死不会不完整）。
# 那redis重启时load进内存的时候就有问题了。
# 发生的时候，可以选择redis启动报错，并且通知用户和写日志，或者load尽量多正常的数据。
# 如果aof-load-truncated是yes，会自动发布一个log给客户端然后load（默认）。
# 如果是no，用户必须手动redis-check-aof修复AOF文件才可以。
# 注意，如果在读取的过程中，发现这个aof是损坏的，服务器也是会退出的，
# 这个选项仅仅用于当服务器尝试读取更多的数据但又找不到相应的数据时。
aof-load-truncated yes
# Lua 脚本的最大执行时间，毫秒为单位
lua-time-limit 5000
# Redis慢查询日志可以记录超过指定时间的查询
slowlog-log-slower-than 10000
# 这个长度没有限制。只是要主要会消耗内存。你可以通过 SLOWLOG RESET 来回收内存。
slowlog-max-len 128
# 客户端的输出缓冲区的限制，可用于强制断开那些因为某种原因从服务器读取数据的速度不够快的客户端
client-output-buffer-limit normal 0 0 0
client-output-buffer-limit slave 256mb 64mb 60
client-output-buffer-limit pubsub 32mb 8mb 60
# 当一个子进程重写AOF文件时，文件每生成32M数据会被同步
aof-rewrite-incremental-fsync yes