public class User {
    private String id;
    private String name;
    private String surname;
    private String gender;
    private String email;
    private String password;
    
    public User(String i) {
        this.id = i;
        this.name = "Vardenis" + i;
        this.surname = "Pavardenis" + i;
        this.gender = "Male";
        this.email = "vardenis.pavardenisrandbsgo." + i + "@gmail.com";
        this.password = "password" + i;
    }

    public String getId() {
        return id;
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
