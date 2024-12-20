/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menu.Utilities;

import POJOs.Patient;
import POJOs.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author maipa
 */
public class Utilities {

    private static final BufferedReader r = new BufferedReader(new InputStreamReader(System.in));

    public static boolean validateDate(LocalDate doaLocalDate) {
        boolean ok = true;
        if (doaLocalDate.isAfter(LocalDate.now())) {
            ok = false;
            System.out.println("Invalid date of birth, try again");
        }
        return ok;
    }

    public static boolean isValidEmail(String email) {
        String emailFormat = "^[a-zA-Z0-9_+.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailFormat);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    public static boolean isValidPhone(String phone_number){
        String regex = "^(\\+34)?[0-9]{9}$";
        return phone_number != null && phone_number.matches(regex);
    }
    
    public static boolean isValidOnlyLetters(String input){
        String regex = "^[a-zA-Z]+$";
        return input != null && input.matches(regex);
    }

    public static boolean validMenu(int numOps, int num) { 
        boolean ok = true;
        if (num > numOps || num < 0) {
            System.out.println("Incorrect option, please type a number between 0 and " + numOps);
            ok = false;
        }

        return ok;
    }

    public static int readInteger() {
        int num = 0;
        boolean ok = false;
        do {
            try {
                num = Integer.parseInt(r.readLine());
                if (num < 0) {
                    ok = false;
                    System.out.print("You didn't type a valid number!");
                } else {
                    ok = true;
                }
            } catch (IOException e) {
                e.getMessage();
            } catch (NumberFormatException nfe) {
                System.out.print("You didn't type a valid number!");
            }
        } while (!ok);

        return num;
    }

    public static String readString() {
        String text = null;
        boolean ok = false;
        do {
            try {
                text = r.readLine();
                if (!text.isEmpty()) {
                    ok = true;
                } else {
                    System.out.println("Empty string, please try again:");
                }
            } catch (IOException e) {

            }
        } while (!ok);

        return text;
    }

    public static boolean validateID(String id) {

        boolean ok = true;
        if (id.length() != 9) {
            System.out.println("Invalid id, try again");
            ok = false;

            return ok;
        }

        for (int i = 0; i < 8; i++) {
            if (!Character.isDigit(id.charAt(i))) {
                ok = false;
                System.out.println("Invalid id, try again");
                return ok;
            }
        }
        String num = id.substring(0, 8);

        String validLeters = "TRWAGMYFPDXBNJZSQVHLCKE";
        int indexLeter = Integer.parseInt(num) % 23;
        char valid = validLeters.charAt(indexLeter);

        if (id.toUpperCase().charAt(8) != valid) {
            System.out.println("Invalid id, try again");
            ok = false;
            return ok;
        }

        return ok;
    }
    
    public static boolean isValidPassword(String password){
        
        //At least 8 characters long
        if (password.length() < 8) {
            return false;
        }

        // At least 1 capital letter
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        // At least one number must be present
        if (!password.matches(".*[0-9].*")) {
            return false;
        }
        return true;
    }
    
    /**
     * Casts a date in string format (dd/MM/yyyy) to java.sql.Date.
     *
     * @param dateStr Date as string in format dd/MM/yyyy.
     * @return Date as java.sql.Date.
     * @throws ParseException if the strig format is not valid.
     */
    public static java.sql.Date convertString2SqlDate(String dateStr) throws ParseException {
        // Formato de la fecha
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        // Parsear el String a java.util.Date
        java.util.Date utilDate = dateFormat.parse(dateStr);
        
        // Convertir java.util.Date a java.sql.Date
        return new java.sql.Date(utilDate.getTime());
    }
    
    /**
     * Casts a date in string format (yyyy-MM-dd) to java.sql.Date.
     *
     * @param dateStr Date as string in format dd/MM/yyyy.
     * @return Date as java.sql.Date.
     * @throws ParseException if the strig format is not valid.
     */
    public static java.sql.Date convertString2SqlDate2(String dateStr) throws ParseException {
        // Formato de la fecha
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        // Parsear el String a java.util.Date
        java.util.Date utilDate = dateFormat.parse(dateStr);
        
        // Convertir java.util.Date a java.sql.Date
        return new java.sql.Date(utilDate.getTime());
    }
    
    
    public static void validateRegisterPatient (Patient patient) throws IllegalArgumentException{
        
        
        if(patient.getName().trim().isEmpty()){
            throw new IllegalArgumentException("The name field cannot be empty.");
        }
        if (!isValidOnlyLetters(patient.getName())){
        throw new IllegalArgumentException("Invalid name. Only letters are accepted.");
        }
        if(patient.getSurname().trim().isEmpty()){
            throw new IllegalArgumentException("The surname field cannot be empty.");
        }
        if (!isValidOnlyLetters(patient.getSurname())){
        throw new IllegalArgumentException("Invalid surname. Only letters are accepted.");
        }
        if(patient.getNIF().trim().isEmpty()){
            throw new IllegalArgumentException("The NIF field cannot be empty.");
        }
        if(!validateID(patient.getNIF())){
            throw new IllegalArgumentException("Invalid NIF.");
        }
        if(patient.getPhone().trim().isEmpty()){
            throw new IllegalArgumentException("The phone field cannot be empty.");
        }
        if(!isValidPhone(patient.getPhone())){
            throw new IllegalArgumentException("Invalid phone.");
        }
        if(patient.getDob() == null){
            throw new IllegalArgumentException("The date of birth field cannot be empty.");
        }
        if(!validateDate(patient.getDob().toLocalDate())){
            throw new IllegalArgumentException("Invalid date of birth.");
        }
        if(patient.getGender()== null){
            throw new IllegalArgumentException("The name field cannot be empty.");
        }
        
        if (patient.getUser().getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("The username field cannot be empty.");
        }
        
        if (!isValidEmail(patient.getUser().getEmail().trim())) {
            throw new IllegalArgumentException("Invalid username.\nIt must be in the format: example@domain.com, using only letters, numbers, periods (.), underscores (_), plus signs (+), or hyphens (-).");
        }
        if (patient.getUser().getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("The password field cannot be empty.");
        }
        if (!isValidPassword(patient.getUser().getPassword().trim())) {
            throw new IllegalArgumentException("Invalid password.\nIt must be at least 8 characters long, contain at least one uppercase letter, and include at least one number.");
        }
       
        
    }
    
    public static void validateLogin(User user) throws IllegalArgumentException {
        if (user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("The username field cannot be empty");
        }
        if (user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("The password field cannot be empty");
        }
        
    }
    
    public static void validateUpdatePatient(Patient patient) throws IllegalArgumentException{
        if(patient.getName().trim().isEmpty()){
            throw new IllegalArgumentException("The name field cannot be empty.");
        }
        if (!isValidOnlyLetters(patient.getName())){
        throw new IllegalArgumentException("Invalid name. Only letters are accepted.");
        }
        if(patient.getSurname().trim().isEmpty()){
            throw new IllegalArgumentException("The surname field cannot be empty.");
        }
        if (!isValidOnlyLetters(patient.getSurname())){
        throw new IllegalArgumentException("Invalid surname. Only letters are accepted.");
        }
        if(patient.getNIF().trim().isEmpty()){
            throw new IllegalArgumentException("The NIF field cannot be empty.");
        }
        if(!validateID(patient.getNIF())){
            throw new IllegalArgumentException("Invalid NIF.");
        }
        if(patient.getPhone().trim().isEmpty()){
            throw new IllegalArgumentException("The phone field cannot be empty.");
        }
        if(!isValidPhone(patient.getPhone())){
            throw new IllegalArgumentException("Invalid phone.");
        }
        if(patient.getDob() == null){
            throw new IllegalArgumentException("The date of birth field cannot be empty.");
        }
        if(!validateDate(patient.getDob().toLocalDate())){
            throw new IllegalArgumentException("Invalid date of birth.");
        }
    }
    
    
    public static void validateUpdatePassword(String newPassword, String confirmPassword) throws IllegalArgumentException {
        
        if (newPassword.isEmpty() || confirmPassword.isEmpty()){
            throw new IllegalArgumentException("Password fields cannot be empty.");
        }
        if (!newPassword.equals(confirmPassword)){
            throw new IllegalArgumentException("New password and confirm password do not match.");
        }
        if (!isValidPassword(newPassword)) {
            throw new IllegalArgumentException("Invalid password.\nIt must be at least 8 characters long, contain at least one uppercase letter, and include at least one number.");
        }
        
    }
    
    
    
}
