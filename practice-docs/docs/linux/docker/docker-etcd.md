docker-etcd


https://github.com/coreos/etcd/

https://github.com/coreos/etcd/blob/master/Documentation/api.md

https://github.com/coreos/etcd/tree/master/etcdctl


设置key
	curl -L http://127.0.0.1:4001/v2/keys/message -XPUT -d value="Hello world"
获取key
	curl -L http://127.0.0.1:4001/v2/keys/dir
设置目录
	curl -L http://127.0.0.1:4001/v2/keys/dir -XPUT -d dir=true
获取目录
获取目录和子目录
	curl -L http://127.0.0.1:4001/v2/keys/?recursive=true
查看根目录的keys
	curl -L http://127.0.0.1:4001/v2/keys/
清空
	curl -L http://127.0.0.1:4001/v2/keys/registry?recursive=true -XDELETE