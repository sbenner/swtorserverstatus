package test.test;

/**
 * Created by IntelliJ IDEA.
 * User: siggi
 * Date: 19/01/12
 * Time: 13:38
 * To change this template use File | Settings | File Templates.
 */
public class Phonebook {

    int id;
    private String name;
    private String email;
    private String phone;
    private String address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Phonebook)) return false;

        Phonebook phonebook = (Phonebook) o;

        if (id != phonebook.id) return false;
        if (email != null ? !email.equals(phonebook.email) : phonebook.email != null) return false;
        if (name != null ? !name.equals(phonebook.name) : phonebook.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
