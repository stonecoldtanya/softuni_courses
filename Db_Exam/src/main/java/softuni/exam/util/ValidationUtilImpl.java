package softuni.exam.util;

import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Validator;

public class ValidationUtilImpl implements ValidationUtil {

    @Autowired
    private final Validator validator;

    public ValidationUtilImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public <E> boolean isValid(E entity) {
        return validator.validate(entity).isEmpty();
    }
}