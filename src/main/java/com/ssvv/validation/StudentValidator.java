package com.ssvv.validation;

import com.ssvv.domain.Student;

public class StudentValidator implements Validator<Student> {

    /**
     * Valideaza un student
     * @param entity - studentul pe care il valideaza
     * @throws ValidationException - daca studentul nu e valid
     */
    @Override
    public void validate(Student entity) throws ValidationException {
        if(entity.getID().equals("")){
            throw new ValidationException("Id incorect!");
        }
        if(entity.getID().equals("null")){
            throw new ValidationException("Id incorect!");
        }
        if(entity.getNume().isEmpty()){
            throw new ValidationException("Nume incorect!");
        }
        if(entity.getGrupa() < 0) {
            throw new ValidationException("Grupa incorecta!");
        }
        if(entity.getEmail() == null){
            throw new ValidationException("Email incorect!");
        }
        if(entity.getNume() == null || !isValidName(entity.getNume())){
            throw new ValidationException("Nume incorect!");
        }
        if(entity.getEmail().equals("")){
            throw new ValidationException("Email incorect!");
        }
        if (entity.getEmail() == null || !isValidEmail(entity.getEmail()))
            throw new ValidationException("Invalid email format!");
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    private boolean isValidName(String name) {
        return name.matches("^[A-Z][a-zA-Z ]*$") && !name.contains("-");
    }
}
