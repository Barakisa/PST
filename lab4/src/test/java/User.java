public class User {
    private String name;
    private String surname;
    private String gender;
    private String email;
    private String password;
    public User(String i) {
        this.name = "Vardenis" + i;
        this.surname = "Pavardenis" + i;
        this.gender = "Male";
        this.email = "vardenis.pavardenis" + i + "@gmail.com";
        this.password = "password" + i;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
