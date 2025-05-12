package sh.roadmap.projects.tasktracker.exception;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.shell.ParameterValidationException;
import org.springframework.shell.command.CommandExceptionResolver;
import org.springframework.shell.command.CommandHandlingResult;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintViolation;

@Order(1)
@Component
public class GlobalExceptionHandler implements CommandExceptionResolver {

    @Override
    public CommandHandlingResult resolve(Exception e) {
        if (e instanceof TaskNotFoundException) {
            return CommandHandlingResult.of(e.getMessage() + "\n\n");
        }
        if (e instanceof ParameterValidationException pve) {
            return handleConstraintViolation(pve);
        }
        if (e instanceof ConversionFailedException cfe) {
            return handleConversionFailed(cfe);
        }
        // Handle other exceptions if needed
        return CommandHandlingResult.of("ExceptionType: " + e.getClass().getSimpleName()
                + "; Message: " + e.getMessage() + "\n\n");
    }

    private CommandHandlingResult handleConversionFailed(ConversionFailedException cfe) {
        StringBuilder errorMessage = new StringBuilder("Conversion error! ");
        errorMessage
                .append("Cannot convert value '").append(cfe.getValue())
                .append("' to type ").append(cfe.getTargetType().getName())
                .append("!\n")
                .append("Please ensure input value in the correct format.\n");
        return CommandHandlingResult.of(errorMessage.toString() + "\n");
    }

    private CommandHandlingResult handleConstraintViolation(ParameterValidationException pve) {
        StringBuilder errorMessage = new StringBuilder("Validation error(s):\n");
        for (ConstraintViolation<?> violation : pve.getConstraintViolations()) {
            errorMessage.append("- ")
                    .append(((PathImpl) violation.getPropertyPath()).getLeafNode().getName())
                    .append(": ")
                    .append(violation.getMessage())
                    .append("\n");
        }
        return CommandHandlingResult.of(errorMessage.toString() + "\n");
    }
}