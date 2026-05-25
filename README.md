# projeto-davi-marcos
jpa/hibernate



> Container com banco de dados

```bash
docker run -d --rm -p 3306:3306 -e MYSQL_ROOT_PASSWORD=senhaRoot \
-e MYSQL_DATABASE=bcd -e MYSQL_USER=aluno -e MYSQL_PASSWORD=aluno \
-e MYSQL_ROOT_HOST='%' --name meumysql mysql/mysql-server:latest


# Comando para rodar API
./gradlew bootRun

```


## How to load data of .env

```bash
cd demo 
./load-env.sh ./gradlew bootRun


```

# terminal

```bash
cd demo
source ./load-env.sh
./gradlew bootRun

```

A diferença é importante: se você roda ./load-env.sh sozinho, ele carrega as variáveis só dentro do processo do script. Para afetar o terminal atual, precisa usar source.