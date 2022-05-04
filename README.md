# vertx-pmml demo

```
mvn clean package
java -jar .\target\fortune-1.0.0-SNAPSHOT-fat.jar -conf config\config.json
```

```
curl --location --request POST 'http://localhost:8080/predict/iris' \
--header 'Content-Type: application/json' \
--data-raw '{
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
                "pmml": ".\\config\\model_svc.pmml",
                "name": "IRIS_SVC",
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
    }
}
```

[jpmml-evaluator](https://github.com/jpmml/jpmml-evaluator)

