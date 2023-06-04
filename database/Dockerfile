FROM mysql
ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=jpetstore
COPY V1.0.0.01__create_schema.sql /docker-entrypoint-initdb.d/
COPY V1.0.0.02__import_data.sql /docker-entrypoint-initdb.d/
