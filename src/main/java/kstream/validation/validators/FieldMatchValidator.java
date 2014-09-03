package kstream.validation.validators;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-04-25
 * Time: 4:23 PM
 */
import kstream.validation.annotations.FieldMatch;
import org.apache.commons.beanutils.PropertyUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2013-09-04
 * Time: 10:54 PM
 */
public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private FieldMatch fieldMatch;

    public void initialize(FieldMatch fieldMatch) {
        this.fieldMatch = fieldMatch;
    }

    public boolean isValid(Object o, ConstraintValidatorContext context) {
        boolean isValid = true;

        try {
            Object source = PropertyUtils.getProperty(o, fieldMatch.source());
            Object target = PropertyUtils.getProperty(o, fieldMatch.target());

            isValid = source.equals(target);
        }
        catch (Exception e){
            // ignore
            isValid = false;
        }

        if (!isValid){
            String msg = context.getDefaultConstraintMessageTemplate();

            context.disableDefaultConstraintViolation();
            ConstraintValidatorContext.ConstraintViolationBuilder violationBuilder = context.buildConstraintViolationWithTemplate(msg);
            violationBuilder.addPropertyNode(fieldMatch.target()).addConstraintViolation();
        }


        return isValid;
    }
}

