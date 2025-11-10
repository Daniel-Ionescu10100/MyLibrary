package model.validator;

import java.util.ArrayList;
import java.util.List;

public class Notification<T>{
    private T result;

    private final List<String> errors;

    public Notification(){
        this.errors = new ArrayList<String>();
    }

    public void addError(String error){
        this.errors.add(error);
    }

    public boolean hasError(){
        return !this.errors.isEmpty();
    }

    public void setResult(T result) {
        if(hasError()){
            throw new ResultFetchException(errors);
        }
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    public String getFormatedErrors(){
        return String.join("\n", errors);
    }

}
