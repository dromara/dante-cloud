# postgresql 数据库创建脚本
```
// 创建用户 herodotus 密码是 herodotus
CREATE USER herodotus WITH PASSWORD 'herodotus';

// 创建数据库 nherodotus, 并将用户 herodotus 分配给该数据
CREATE DATABASE nherodotus OWNER herodotus;

// 将数据库 nherodotus 的所有权限分配给 herodotus
GRANT ALL PRIVILEGES ON DATABASE nherodotus TO herodotus;
```
```

// 创建数据库 dathena, 并将用户 herodotus 分配给该数据
CREATE DATABASE dathena OWNER herodotus;

// 将数据库 nherodotus 的所有权限分配给 herodotus
GRANT ALL PRIVILEGES ON DATABASE dathena TO herodotus;
```
