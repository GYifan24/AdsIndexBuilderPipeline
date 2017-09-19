# Project Title

Index Builder Pipeline

## Getting Started

Download project file
git clone git@github.com:GYifan24/AdsIndexBuilderPipeline.git

### Prerequisites

MySQL
Memcached
RabbitMQ

 

### Running the project

1. Download project file


```
git clone git@github.com:GYifan24/AdsIndexBuilderPipeline.git
```

2. Export all jar files in jarlib in all three project

```
For example, in IntelliJ, File -> Project Structure -> Modules -> + JARs
```

3. Run RabbitMQ 


```
rabbitmq-server
```

4. Open RabbitMQ GUI from webbrower 


```
http://127.0.0.1:15672/#/         Username: guest Password: guest
```

5. Run Feeder from a JAVA IDE, input parameter: rawQuery3.txt 


```
For example, in IntelliJ, Run->Edit Configuration->Program arguments: rawQuery3.txt
Then, start Run
```

6. Monitor RabbitMQ, message should be published on Queue "q_feeds"

7. Run WebCrawler from a JAVA IDE, input parameter: proxylist_bittiger.csv


```
For example, in IntelliJ, Run->Edit Configuration->Program arguments: proxylist_bittiger.csv
Then, start Run
```

8. Monitor RabbitMQ, message should be published on Queue "q_product" and "q_error"

9. Start mySql 

```
mysql -u root -p
```

10. Create database named searchads in mySql and then switch to "searchads" database

```
create database searchads;
use searchads;
```

11. Create table in searchads;

```
create table ad( adId int, campaignId int, keyWords varchar(255), bidPrice double, price double,thumbnail varchar(255), description varchar(255), brand varchar(255), detail_url varchar(255), category varchar(255), title varchar(255));
```

12. Start memcached

```
telnet 127.0.0.1 11211
```

13. Run IndexBuilder from a JAVA IDE

14. Review mySql;

```
select * from ad;
```
15. Review memcached;

```
stats items
```


