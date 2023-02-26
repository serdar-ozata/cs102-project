package database;

public class Person {
    private String description;
    private int id;
    private String name;
    private String mail;
    private String password;
    private int userType;
    private int listingType;
    private int booleanInfo;



    public Person(int id, String mail, String name, String password, int userType, String description, int listingType, int booleanInfo) {
        this.id = id;
        this.mail = mail;
        this.name = name;
        this.password = password;
        this.userType = userType;
        this.description = description;
        this.booleanInfo = booleanInfo;
        this.listingType = listingType;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) {
        this.description = description;
    }
    public int getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getUserType() {
        return userType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getListingType() {
        return listingType;
    }

    public int getBooleanInfo() {
        return booleanInfo;
    }
    public void setBooleanInfo(int newInt){
        booleanInfo = newInt;
    }

    @Override
    public String toString() {
        return "Person{" +
                "description='" + description + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                '}';
    }
}