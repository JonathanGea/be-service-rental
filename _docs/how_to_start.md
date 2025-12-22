tech

openjdk version "21.0.9" 2025-10-21


linux ubuntu

```
sudo apt update

sudo apt install openjdk-21-jdk

java -version
```

Mengatur JAVA_HOME 

```
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH

source ~/.bashrc

```

run

```
mvn spring-boot:run
```

