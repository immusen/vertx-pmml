package com.hirisklab.evaluate.evaluator.actor;

import org.jpmml.evaluator.LoadingModelEvaluatorBuilder;
import javax.xml.parsers.ParserConfigurationException;
import com.hirisklab.evaluate.evaluator.util.EvaluateException;
import jakarta.xml.bind.JAXBException;
import org.xml.sax.SAXException;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import java.io.IOException;
import org.jpmml.evaluator.EvaluationException;
import org.jpmml.evaluator.Evaluator;
import org.jpmml.evaluator.InputField;
import java.util.List;
import java.io.File;
import java.util.Map;
import java.util.Optional;

/**
 * Evaluator (AI model)...
 * @author immusen
 */
public class Evaluater {

    private String name;
    private String pmml;
    private String version = "1.0.0";
    private Evaluator evaluater;

    public Evaluater(JsonObject profile) throws EvaluateException {
        this.name = Optional.ofNullable(profile.getString("name")).orElseThrow(() -> EvaluateException.InvalidConfigException("Missing pmml path"));
        this.pmml = Optional.ofNullable(profile.getString("pmml")).orElse("Untitled");
        this.version = Optional.ofNullable(profile.getString("version")).orElse("0.0.1");
        this.buildEvaluator();
    }

    public Evaluater(String pmml, String name, String version) throws EvaluateException {
        this.name = name;
        this.pmml = pmml;
        this.version = version;
        this.buildEvaluator();
    }

    public void buildEvaluator() throws EvaluateException {
        try {
            this.evaluater = new LoadingModelEvaluatorBuilder().load(new File(this.pmml)).build();
            this.evaluater.verify();
        } catch (IOException | ParserConfigurationException | SAXException | JAXBException | EvaluationException e) {
            throw EvaluateException.ServiceUnavailableException(e.getMessage());
        }
    }

    public String getName() {
        return name;
    }

    public String getPmml() {
        return pmml;
    }

    public String getVersion() {
        return version;
    }

    public Evaluator getEvaluater(){
        return this.evaluater;
    }

    public List<InputField> inputFields() {
        List<InputField> inputFields = this.evaluater.getInputFields();
        System.out.println("Input fields: " + inputFields);
        return inputFields;
    }

    public Future<String> predict(Map<String, ?> features) {
        Promise<String> promise = Promise.promise();
        Map<String, ?> results = this.evaluater.evaluate(features);
        // TODO: handle with OutputFields or model...
        promise.complete(results.toString());
        return promise.future();
    }

    @Override
    public String toString() {
        return "Evaluater{" +
                "name='" + name + '\'' +
                ", pmml='" + pmml + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
    
}
