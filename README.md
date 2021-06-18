# blog4j

> This project is currently under development.
 
A blogging engine written with Java and Spring Boot.

![master branch](https://github.com/ahmedelhori/blog4j/actions/workflows/ci.yml/badge.svg)

![](.github/asset/preview.gif)

# How to run
1. Adjust the docker-compose file to your needs (don't forget to add your blog's domain name).
2. On your server run the following command:

```
$ git clone https://github.com/ahmed/blog4j && docker-compose build && docker-compose up
```

## Example Config:
### docker-compose
`./docker-compose.yml`
```
version: "3.9"
        ..
        ...
        ports:
      - "8080:8080"
    volumes:
      - /var/www/blog:/var/www/blog
    environment:
      - "BLOG4J_BLOG_HEADER=Example Blog"
      - "BLOG4J_BLOG_FOOTER=\u00a9 Example Blog"
      - "BLOG4J_BLOG_TITLE=Example Blog"
      - "BLOG4J_BLOG_DOMAIN=blog.example.com"
      - "BLOG4J_USERNAME=admin"
      - "BLOG4J_PASSWORD=admin"
      - TZ=Europe/Berlin
        ..
        ...
```

The app will bind to `port 8080` on your server, and the whole blog will be generated in the `/var/www/blog` directory.

### nginx: 
`/etc/nginx/sites-available/blog`
```
server {
	root /var/www/blog;
	index index.html index.htm index.nginx-debian.html;
	server_name blog.example.com www.blog.example.com;

	location ~/(dashboard|login|logout) {
		proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
		proxy_set_header X-Forwarded-Proto $scheme;
		proxy_set_header X-Forwarded-Port $server_port;
		proxy_set_header Host $host;
		proxy_pass http://localhost:8080;
	}

	location / {
		try_files $uri $uri/ =404;
	}
}
```
