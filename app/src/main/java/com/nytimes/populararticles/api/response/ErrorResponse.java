package com.nytimes.populararticles.api.response;

import com.nytimes.populararticles.NyTimeApp;
import com.nytimes.populararticles.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class for representing the error results
 */
public class ErrorResponse {
    private String status;
    private List<String> errors = new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public String getMessage() {
        if (errors != null && !errors.isEmpty()) {
            return errors.get(errors.size() - 1);
        } else {
            return NyTimeApp.getAppContext().getString(R.string.unknownError);
        }
    }

    public ErrorResponse() {
        // Empty constructor
    }
}
