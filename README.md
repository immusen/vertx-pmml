### vertx-pmml demo

```
mvn clean package
java -jar .\target\evaluate-1.0.0-SNAPSHOT-fat.jar -conf config\config.json
```

```
curl 'http://localhost:8080/predict/iris' \
-d '{
    "reqSn": "00000",
    "data": {
        "sepal_length": 5.4,
        "sepal_width": 3.9,
        "petal_length": 1.7,
        "petal_width": 0.4
    }
}'
```

### Config

```
{
    "http.port": 8080,
    "route": {
        "/predict/iris": {
            "access": "public",
            "method": "POST",
            "actor": "evaluter.predict",
            "ext": {
                "name": "IRIS_SVC",
                "pmml": ".\\config\\model_svc.pmml",
                "version": "1.0.0"
            }
        },
        "/stat/:name": {
            "access": "session.admin",
            "method": "GET",
            "actor": "evaluter.stat"
        },
        "/console/login": {
            "access": "public",
            "method": "GET",
            "actor": "web_login"
        }

        ... Any other routers with pmml or some web services ...

    }
}
```

[jpmml-evaluator](https://github.com/jpmml/jpmml-evaluator)

[demo pmml train from iris dataset](https://archive.ics.uci.edu/ml/datasets/iris)
