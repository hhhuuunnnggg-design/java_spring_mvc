package vn.hoidanit.laptopshop.entity.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegisterDTO {

    @NotNull(message = "tên không được để trống")
    @Size(min = 1, message = "tên phải trên 1 ký tự")
    private String firstName;

    @NotNull(message = "Họ không được để trống")
    @Size(min = 3, message = "Họ phải trên 3 ký tự")
    private String lastName;

    @NotNull(message = "Email không đưuọc để trống")
    @Size(min = 10, message = "phải có tối đa 10 ký tự")
    private String email;

    @NotNull
    @Size(min = 5, message = "passWord phải trên 5 ký tự")
    private String passWord;
    private String confirmPassWord;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getConfirmPassWord() {
        return confirmPassWord;
    }

    public void setConfirmPassWord(String confirmPassWord) {
        this.confirmPassWord = confirmPassWord;
    }

    @Override
    public String toString() {
        return "RegisterDTO [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", passWord="
                + passWord + ", confirmPassWord=" + confirmPassWord + "]";
    }

}
