docker-registry-web
	https://github.com/atc-/docker-registry-web
启动
	docker run -p 8080:8080 -e REG1=http://<system hostname>:5000/v1/ atcol/docker-registry-ui
	docker run -p 8080:8080 -e REG1=http://10.122.75.228:5000/v1/ atcol/docker-registry-ui
开通端口
	/sbin/iptables -I INPUT -p tcp --dport 5000 -j ACCEPT
nginx配置
	
	vi /etc/nginx/conf.d/docker-registry-web.conf

	server {
    	listen       8080;
    	server_name 117.78.19.76;

    	location / {
        	proxy_pass http://10.122.75.228:8080;
        	proxy_set_header  X-Real-IP        $remote_addr;
    	}

    	charset utf-8;
    	access_log  /var/log/nginx/docker-registry.log  main;
    	error_log /var/log/nginx/docker-registry_error.log;
	}

	vi docker-registry-service.conf

	server {
    	listen       5000;
    	server_name 117.78.19.76;

    	location / {
        	proxy_pass http://10.122.75.228:5000;
        	proxy_set_header  X-Real-IP        $remote_addr;
    	}

    	charset utf-8;
    	access_log  /var/log/nginx/docker-registry-service.log  main;
    	error_log /var/log/nginx/docker-registry-service_error.log;
	}
	
待解决问题
	隐藏在nginx后，并添加认证（双向，HTTP）