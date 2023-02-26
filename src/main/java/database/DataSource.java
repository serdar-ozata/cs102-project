package database;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.*;
import java.util.ArrayList;

import com.vaadin.flow.component.html.Pre;
import database.assets.*;
/* This is our database class 
    it works with an Sqlite database 
    this class has the 
    -UPDATE
    -DELETE
    -ADD
    -GET
    methods for the following classes
    -Farmland
    -Person
    -Crop
    -RealEstate
    -Government
    and also this class holds 2 additional methods
    called serialize and deSerialize those methods 
    are here for adding arraylists or arrays to the database
    in a form of BLOB
     */

public class DataSource {
    
    private static final String DB_NAME = "landTracker.db";
    private static final String CONNECTION_STRING = "jdbc:sqlite:D:\\Code\\LandTracker1\\" + DB_NAME;

    private static final String TABLE_PERSON = "person";
    private static final String PERSON_COLUMN_ID = "_ID";
    private static final String PERSON_COLUMN_MAIL = "mail";
    private static final String PERSON_COLUMN_NAME = "name";
    private static final String PERSON_COLUMN_PASSWORD = "password";
    private static final String PERSON_COLUMN_USERTYPE = "user_type";
    private static final String PERSON_COLUMN_DESCRIPTION = "description";
    private static final String PERSON_COLUMN_LISTING_TYPE = "listing_type";
    private static final String PERSON_COLUMN_BOOLEAN_INFO = "boolean_info";

    private static final String TABLE_CROP = "crop";
    private static final String CROP_COLUMN_ID = "_ID";
    private static final String CROP_COLUMN_FIELD_ID = "field_ID";
    private static final String CROP_COLUMN_NAME = "name";
    private static final String CROP_COLUMN_YIELD = "yield";
    private static final String CROP_COLUMN_COST = "cost";
    private static final String CROP_COLUMN_REVENUE = "revenue";
    private static final String CROP_COLUMN_YEAR = "year";
    private static final String CROP_COLUMN_DESCRIPTION = "description";

    private static final String TABLE_FARMLAND = "farmland";
    private static final String FARMLAND_COLUMN_ID = "_ID";
    private static final String FARMLAND_COLUMN_NAME = "name";
    private static final String FARMLAND_COLUMN_OWNER_ID = "owner_ID";
    private static final String FARMLAND_COLUMN_CURRENT_CROP = "current_crop";
    private static final String FARMLAND_COLUMN_DESCRIPTION = "description";
    private static final String FARMLAND_COLUMN_COLOR = "color";
    private static final String FARMLAND_COLUMN_GROUP_NAME = "group_name";
    private static final String FARMLAND_COLUMN_COORDINATES = "coordinates";
    private static final String FARMLAND_COLUMN_CENTER_COORDINATES = "center_coordinates";

    private static final String TABLE_REAL_ESTATE = "real_estate";
    private static final String REAL_ESTATE_COLUMN_ID = "_ID";
    private static final String REAL_ESTATE_COLUMN_NAME = "name";
    private static final String REAL_ESTATE_COLUMN_OWNER_ID = "owner_ID";
    private static final String REAL_ESTATE_COLUMN_FLOOR_NO = "floor_no";
    private static final String REAL_ESTATE_COLUMN_APT_NO = "apt_no";
    private static final String REAL_ESTATE_COLUMN_TYPE = "type";
    private static final String REAL_ESTATE_COLUMN_CURRENT = "history";
    private static final String REAL_ESTATE_COLUMN_DESCRIPTION = "description";
    private static final String REAL_ESTATE_COLUMN_COLOR = "color";
    private static final String REAL_ESTATE_COLUMN_GROUP_NAME = "group_name";
    private static final String REAL_ESTATE_COLUMN_COORDINATES = "coordinates";
    private static final String REAL_ESTATE_COLUMN_CENTER_COORDINATES = "center_coordinates";

    private static final String TABLE_GOVERNMENT = "government";
    private static final String GOVERNMENT_COLUMN_ID = "_ID";
    private static final String GOVERNMENT_COLUMN_NAME = "name";
    private static final String GOVERNMENT_COLUMN_OWNER_ID = "owner_ID";
    private static final String GOVERNMENT_COLUMN_DESCRIPTION = "description";
    private static final String GOVERNMENT_COLUMN_COLOR = "color";
    private static final String GOVERNMENT_COLUMN_GROUP_NAME = "group_name";
    private static final String GOVERNMENT_COLUMN_COORDINATES = "coordinates";
    private static final String GOVERNMENT_COLUMN_CENTER_COORDINATES = "center_coordinates";

    private static Connection connection;

    public boolean open() {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING);
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
            return false;
        }
    }

    public void close() {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            System.out.println("cannot close the connection" + e);
            e.printStackTrace();
        }
    }

    public void createDB() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_PERSON + "("
                    + PERSON_COLUMN_ID + " INTEGER PRIMARY KEY ,"
                    + PERSON_COLUMN_MAIL + " TEXT ,"
                    + PERSON_COLUMN_NAME + " TEXT ,"
                    + PERSON_COLUMN_PASSWORD + " TEXT ,"
                    + PERSON_COLUMN_USERTYPE + " INTEGER ,"
                    + PERSON_COLUMN_DESCRIPTION + " TEXT ,"
                    + PERSON_COLUMN_LISTING_TYPE + " INTEGER ,"
                    + PERSON_COLUMN_BOOLEAN_INFO + " INTEGER ,"
                    + " UNIQUE (" + PERSON_COLUMN_MAIL + ")"
                    + ")"
            );
            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_CROP + "("
                    + CROP_COLUMN_ID + " INTEGER PRIMARY KEY ,"
                    + CROP_COLUMN_FIELD_ID + " INTEGER ,"
                    + CROP_COLUMN_NAME + " TEXT ,"
                    + CROP_COLUMN_YIELD + " FLOAT ,"
                    + CROP_COLUMN_COST + " FLOAT ,"
                    + CROP_COLUMN_REVENUE + " FLOAT ,"
                    + CROP_COLUMN_YEAR + " INTEGER ,"
                    + CROP_COLUMN_DESCRIPTION + " TEXT "
                    + ")"
            );
            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_FARMLAND + "("
                    + FARMLAND_COLUMN_ID + " INTEGER PRIMARY KEY ,"
                    + FARMLAND_COLUMN_NAME + " TEXT ,"
                    + FARMLAND_COLUMN_OWNER_ID + " INTEGER ,"
                    + FARMLAND_COLUMN_CURRENT_CROP + " INTEGER ,"
                    + FARMLAND_COLUMN_DESCRIPTION + " TEXT ,"
                    + FARMLAND_COLUMN_COLOR + " TEXT ,"
                    + FARMLAND_COLUMN_GROUP_NAME + " TEXT ,"
                    + FARMLAND_COLUMN_COORDINATES + " BLOB ,"
                    + FARMLAND_COLUMN_CENTER_COORDINATES + " BLOB ,"
                    + " UNIQUE (" + FARMLAND_COLUMN_OWNER_ID + "," + FARMLAND_COLUMN_NAME + ")"
                    + ")"
            );
            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_REAL_ESTATE + "("
                    + REAL_ESTATE_COLUMN_ID + " INTEGER PRIMARY KEY ,"
                    + REAL_ESTATE_COLUMN_NAME + " TEXT ,"
                    + REAL_ESTATE_COLUMN_OWNER_ID + " INTEGER ,"
                    + REAL_ESTATE_COLUMN_FLOOR_NO + " INTEGER ,"
                    + REAL_ESTATE_COLUMN_APT_NO + " INTEGER ,"
                    + REAL_ESTATE_COLUMN_TYPE + " TEXT ,"
                    + REAL_ESTATE_COLUMN_CURRENT + " INTEGER ,"
                    + REAL_ESTATE_COLUMN_DESCRIPTION + " TEXT ,"
                    + REAL_ESTATE_COLUMN_COLOR + " TEXT ,"
                    + REAL_ESTATE_COLUMN_GROUP_NAME + " TEXT ,"
                    + REAL_ESTATE_COLUMN_COORDINATES + " BLOB ,"
                    + REAL_ESTATE_COLUMN_CENTER_COORDINATES + " BLOB ,"
                    + " UNIQUE (" + REAL_ESTATE_COLUMN_OWNER_ID + "," + REAL_ESTATE_COLUMN_NAME + ")"
                    + ")"
            );
            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_GOVERNMENT + "("
                    + REAL_ESTATE_COLUMN_ID + " INTEGER PRIMARY KEY ,"
                    + GOVERNMENT_COLUMN_NAME + " TEXT ,"
                    + GOVERNMENT_COLUMN_OWNER_ID + " INTEGER ,"
                    + GOVERNMENT_COLUMN_DESCRIPTION + " TEXT ,"
                    + GOVERNMENT_COLUMN_COLOR + " TEXT ,"
                    + GOVERNMENT_COLUMN_GROUP_NAME + " TEXT ,"
                    + GOVERNMENT_COLUMN_COORDINATES + " BLOB ,"
                    + GOVERNMENT_COLUMN_CENTER_COORDINATES + " BLOB ,"
                    + " UNIQUE (" + GOVERNMENT_COLUMN_OWNER_ID + "," + GOVERNMENT_COLUMN_NAME + ")"
                    + ")"
            );

        } catch (Exception e) {
            System.out.println("cannot create the database");
            e.printStackTrace();
        }
    }

    public boolean addPerson(String mail, String name, String password, int userType, String description, int listingType, int booleanInfo) {
        String INSERT_PERSON = "INSERT INTO " + TABLE_PERSON + "("
                + PERSON_COLUMN_MAIL + ","
                + PERSON_COLUMN_NAME + ","
                + PERSON_COLUMN_PASSWORD + ","
                + PERSON_COLUMN_USERTYPE + ","
                + PERSON_COLUMN_DESCRIPTION + ","
                + PERSON_COLUMN_LISTING_TYPE + ","
                + PERSON_COLUMN_BOOLEAN_INFO
                + ")" + "VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(INSERT_PERSON)) {
            statement.setString(1, mail);
            statement.setString(2, name);
            statement.setString(3, password);
            statement.setInt(4, userType);
            statement.setString(5, description);
            statement.setInt(6, listingType);
            statement.setInt(7, booleanInfo);
            statement.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("cannot add person ");
            e.printStackTrace();
            return false;
        }

    }

    public int getBooleanInfo(String ownerMail) {
        String GET_LISTING = " SELECT " + PERSON_COLUMN_BOOLEAN_INFO + " FROM "
                + TABLE_PERSON + " WHERE " + PERSON_COLUMN_MAIL + "=" + "\"" + ownerMail + "\"";
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_LISTING);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return resultSet.getInt(1);
        } catch (SQLException e) {
            System.out.println("cannot get boolean info ");
            e.printStackTrace();
        }
        return -1;
    }

    public boolean updateBooleanInfo(String ownerMail, int newBooleanInfo) {
        String sql = "UPDATE " + TABLE_PERSON + " SET " + PERSON_COLUMN_BOOLEAN_INFO + "=" + newBooleanInfo +
                " WHERE " + PERSON_COLUMN_MAIL + "=" + "\"" + ownerMail + "\"";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);){
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("cannot update boolean info ");
            e.printStackTrace();
            return false;
        }
    }

    public int getListingType(String ownerMail) {
        String GET_LISTING = " SELECT " + PERSON_COLUMN_LISTING_TYPE + " FROM "
                + TABLE_PERSON + " WHERE " + PERSON_COLUMN_MAIL + "=" + "\"" + ownerMail + "\"";
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_LISTING);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return resultSet.getInt(1);
        } catch (SQLException e) {
            System.out.println("cannot get listing type ");
            e.printStackTrace();
        }
        return -1;
    }

    public boolean updatePersonListingType(String mail, int listingType) {
        String sql = "UPDATE " + TABLE_PERSON + " SET " + PERSON_COLUMN_LISTING_TYPE + "=" + listingType +
                " WHERE " + PERSON_COLUMN_MAIL + "=" + "\"" + mail + "\"";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
            return true;
        } catch (Exception e) {
            System.out.println("cannot update listing type ");
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkPassword(String mail, String password) {
        String CHECK_PASS = "SELECT " + PERSON_COLUMN_PASSWORD + " FROM "
                + TABLE_PERSON + " WHERE " + PERSON_COLUMN_MAIL + "=" + "\"" + mail + "\"";
        try (PreparedStatement statement = connection.prepareStatement(CHECK_PASS);
             ResultSet result = statement.executeQuery()) {
            if (result.getString(PERSON_COLUMN_PASSWORD).equals(password)) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("cannot check the mail ");
            e.printStackTrace();
        }
        return false;
    }

    public int getOwnerID(String ownerMail) {
        String sql = "SELECT " + PERSON_COLUMN_ID + " FROM " + TABLE_PERSON
                + " WHERE " + PERSON_COLUMN_MAIL + "=" + '"' + ownerMail + '"';
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            return rs.getInt(PERSON_COLUMN_ID);
        } catch (Exception e) {
            System.out.println("cannot get owner ID ");
            e.printStackTrace();
        }
        return -1;
    }

    private byte[] serialize(Object object) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            System.out.println("cannot serialize");
            e.printStackTrace();
        }
        return null;
    }

    public boolean addFarmland(String ownerMail, String description, String fieldName, String color, String groupName, ArrayList coordinates, double[] centerCoordinates) {
        String INSERT_FARMLAND = "INSERT INTO " + TABLE_FARMLAND + "("
                + FARMLAND_COLUMN_NAME + ","
                + FARMLAND_COLUMN_OWNER_ID + ","
                + FARMLAND_COLUMN_DESCRIPTION + ","
                + FARMLAND_COLUMN_COLOR + ","
                + FARMLAND_COLUMN_GROUP_NAME + ","
                + FARMLAND_COLUMN_COORDINATES + ","
                + FARMLAND_COLUMN_CENTER_COORDINATES
                + ")" + "VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(INSERT_FARMLAND)) {
            statement.setString(1, fieldName);
            statement.setInt(2, getOwnerID(ownerMail));
            statement.setString(3, description);
            statement.setString(4, color);
            statement.setString(5, groupName);
            statement.setBytes(6, serialize(coordinates));
            statement.setBytes(7, serialize(centerCoordinates));
            statement.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("cannot add farmland " + e);
            e.printStackTrace();
            return false;
        }

    }

    public boolean addGovernment(String name, String ownerMail, String description, String color, String groupName, ArrayList coordinates, double[] centerCoordinates) {
        String INSERT_FARMLAND = "INSERT INTO " + TABLE_GOVERNMENT + "("
                + GOVERNMENT_COLUMN_NAME + ","
                + GOVERNMENT_COLUMN_OWNER_ID + ","
                + GOVERNMENT_COLUMN_DESCRIPTION + ","
                + GOVERNMENT_COLUMN_COLOR + ","
                + GOVERNMENT_COLUMN_GROUP_NAME + ","
                + GOVERNMENT_COLUMN_COORDINATES + ","
                + GOVERNMENT_COLUMN_CENTER_COORDINATES
                + ")" + "VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(INSERT_FARMLAND)) {
            statement.setString(1, name);
            statement.setInt(2, getOwnerID(ownerMail));
            statement.setString(3, description);
            statement.setString(4, color);
            statement.setString(5, groupName);
            statement.setBytes(6, serialize(coordinates));
            statement.setBytes(7, serialize(centerCoordinates));
            statement.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("cannot add government field ");
            e.printStackTrace();
            return false;
        }

    }

    public boolean addRealEstate(String name, String ownerMail, int floorNo, int apt_no, String type, String description, String color, String groupName, ArrayList coordinates, double[] centerCoordinates) {
        String INSERT_REAL_ESTATE = "INSERT INTO " + TABLE_REAL_ESTATE + "("
                + REAL_ESTATE_COLUMN_NAME + ","
                + REAL_ESTATE_COLUMN_OWNER_ID + ","
                + REAL_ESTATE_COLUMN_FLOOR_NO + ","
                + REAL_ESTATE_COLUMN_APT_NO + ","
                + REAL_ESTATE_COLUMN_TYPE + ","
                + REAL_ESTATE_COLUMN_DESCRIPTION + ","
                + REAL_ESTATE_COLUMN_COLOR + ","
                + REAL_ESTATE_COLUMN_GROUP_NAME + ","
                + REAL_ESTATE_COLUMN_COORDINATES + ","
                + REAL_ESTATE_COLUMN_CENTER_COORDINATES
                + ")" + " VALUES(?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(INSERT_REAL_ESTATE)) {
            statement.setString(1, name);
            statement.setInt(2, getOwnerID(ownerMail));
            statement.setInt(3, floorNo);
            statement.setInt(4, apt_no);
            statement.setString(5, type);
            statement.setString(6, description);
            statement.setString(7, color);
            statement.setString(8, groupName);
            statement.setBytes(9, serialize(coordinates));
            statement.setBytes(10, serialize(centerCoordinates));
            statement.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("cannot add real estate ");
            e.printStackTrace();
            return false;
        }

    }

    public boolean addCrop(int fieldID, String name, double yield, double cost, double revenue, int year, String description) {
        int cropID;
        String INSERT_CROP = "INSERT INTO " + TABLE_CROP + "("
                + CROP_COLUMN_FIELD_ID + ","
                + CROP_COLUMN_NAME + ","
                + CROP_COLUMN_YIELD + ","
                + CROP_COLUMN_COST + ","
                + CROP_COLUMN_REVENUE + ","
                + CROP_COLUMN_YEAR + ","
                + CROP_COLUMN_DESCRIPTION
                + ")" + " VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(INSERT_CROP);
             Statement statement1 = connection.createStatement()) {
            statement.setInt(1, fieldID);
            statement.setString(2, name);
            statement.setFloat(3, (float) yield);
            statement.setFloat(4, (float) cost);
            statement.setFloat(5, (float) revenue);
            statement.setInt(6, year);
            statement.setString(7, description);
            statement.execute();
            ResultSet resultSet = statement1.executeQuery("SELECT " + CROP_COLUMN_ID + " FROM " + TABLE_CROP
                    + " WHERE " + CROP_COLUMN_FIELD_ID + "=" + fieldID);
            statement1.execute("UPDATE " + TABLE_FARMLAND + " SET " + FARMLAND_COLUMN_CURRENT_CROP + "=" + resultSet.getInt(1) +
                    " WHERE " + FARMLAND_COLUMN_ID + "=" + fieldID);
            resultSet.close();
            return true;
        } catch (SQLException e) {
            System.out.println("cannot add crop ");
            e.printStackTrace();
            return false;
        }
    }

    public Person getPerson(String mail) {
        String sql = "SELECT * FROM " + TABLE_PERSON + " WHERE " + PERSON_COLUMN_MAIL + "=" + "\"" + mail + "\"";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            Person newPerson = new Person(rs.getInt(PERSON_COLUMN_ID),
                    rs.getString(PERSON_COLUMN_MAIL),
                    rs.getString(PERSON_COLUMN_NAME),
                    rs.getString(PERSON_COLUMN_PASSWORD),
                    rs.getInt(PERSON_COLUMN_USERTYPE),
                    rs.getString(PERSON_COLUMN_DESCRIPTION),
                    rs.getInt(PERSON_COLUMN_LISTING_TYPE),
                    rs.getInt(PERSON_COLUMN_BOOLEAN_INFO));
            return newPerson;
        } catch (Exception e) {
            System.out.println("cannot get person ");
            e.printStackTrace();
        }
        return null;
    }

    public Government getOneGovernmentField(String ownerMail, String fieldName) {
        String sql = "SELECT * FROM " + TABLE_GOVERNMENT
                + " WHERE " + GOVERNMENT_COLUMN_OWNER_ID + "=" + getOwnerID(ownerMail) +" AND "
                + GOVERNMENT_COLUMN_NAME + "=" + "\"" + fieldName + "\"";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            ArrayList<Government> arrayList = new ArrayList<>();
            Government newGovernment = new Government(getPerson(ownerMail)
                    , rs.getString(GOVERNMENT_COLUMN_DESCRIPTION)
                    , rs.getString(GOVERNMENT_COLUMN_NAME)
                    , rs.getString(GOVERNMENT_COLUMN_COLOR)
                    , rs.getString(GOVERNMENT_COLUMN_GROUP_NAME)
                    , (ArrayList) deSerialize(rs.getBytes(GOVERNMENT_COLUMN_COORDINATES))
                    , (double[]) deSerialize(rs.getBytes(GOVERNMENT_COLUMN_CENTER_COORDINATES)));

            return newGovernment;
        } catch (Exception e) {
            System.out.println("cannot get only one government field ");
            e.printStackTrace();
        }
        return null;
    }


    public FarmLand getOneFarmLand(String ownerMail, String fieldName) {
        String sql = "SELECT * FROM " + TABLE_FARMLAND
                + " WHERE " + FARMLAND_COLUMN_OWNER_ID + "=" +getOwnerID(ownerMail)+" AND "
                + FARMLAND_COLUMN_NAME + "=" + "\"" + fieldName + "\"";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            FarmLand newFarmLand = new FarmLand(getPerson(ownerMail)
                    , rs.getString(FARMLAND_COLUMN_DESCRIPTION)
                    , rs.getString(FARMLAND_COLUMN_NAME)
                    , rs.getString(FARMLAND_COLUMN_COLOR)
                    , rs.getString(FARMLAND_COLUMN_GROUP_NAME)
                    , rs.getInt(FARMLAND_COLUMN_CURRENT_CROP)
                    , (ArrayList) deSerialize(rs.getBytes(FARMLAND_COLUMN_COORDINATES))
                    , (double[]) deSerialize(rs.getBytes(FARMLAND_COLUMN_CENTER_COORDINATES)));
            return newFarmLand;
        } catch (Exception e) {
            System.out.println("cannot get only one farmland ");
            e.printStackTrace();
        }
        return null;
    }

    public RealEstate getOneRealEstate(String ownerMail, String fieldName) {
        String sql = "SELECT * FROM " + TABLE_REAL_ESTATE
                + " WHERE " + REAL_ESTATE_COLUMN_OWNER_ID + "=" + getOwnerID(ownerMail) + " AND "
                + REAL_ESTATE_COLUMN_NAME + "=" + "\"" + fieldName + "\"";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            RealEstate newRealEstate = new RealEstate(getPerson(ownerMail)
                    , rs.getString(REAL_ESTATE_COLUMN_TYPE)
                    , rs.getInt(REAL_ESTATE_COLUMN_FLOOR_NO)
                    , rs.getInt(REAL_ESTATE_COLUMN_APT_NO)
                    , rs.getString(REAL_ESTATE_COLUMN_DESCRIPTION)
                    , rs.getString(REAL_ESTATE_COLUMN_NAME)
                    , rs.getString(REAL_ESTATE_COLUMN_COLOR)
                    , rs.getString(REAL_ESTATE_COLUMN_GROUP_NAME)
                    , (ArrayList) deSerialize(rs.getBytes(REAL_ESTATE_COLUMN_COORDINATES))
                    , (double[]) deSerialize(rs.getBytes(REAL_ESTATE_COLUMN_CENTER_COORDINATES)));

            return newRealEstate;
        } catch (Exception e) {
            System.out.println("cannot get only one real estate ");
            e.printStackTrace();
        }
        return null;
    }
    public int getCurrentCropID(int fieldID){
        String sql = "SELECT "+FARMLAND_COLUMN_CURRENT_CROP+"  FROM " + TABLE_FARMLAND + " WHERE " + FARMLAND_COLUMN_ID + "=" + fieldID;
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            return rs.getInt(1);
        } catch (Exception e) {
            System.out.println("cannot get crop ID ");
            e.printStackTrace();
        }
        return -1;
    }
    public Crop getCurrentCrop(int fieldId){
        String sql = "SELECT *  FROM " + TABLE_CROP + " WHERE " + CROP_COLUMN_ID + "=" + getCurrentCropID(fieldId);
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            Crop newCrop = new Crop(rs.getInt(CROP_COLUMN_FIELD_ID),
                    rs.getString(CROP_COLUMN_NAME),
                    rs.getFloat(CROP_COLUMN_YIELD),
                    rs.getFloat(CROP_COLUMN_COST),
                    rs.getFloat(CROP_COLUMN_REVENUE),
                    rs.getInt(CROP_COLUMN_YEAR),
                    rs.getString(CROP_COLUMN_DESCRIPTION));
            return newCrop;
        } catch (Exception e) {
            System.out.println("cannot get current crop ");
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList getCrops(int field_ID) {
        String sql = "SELECT * FROM " + TABLE_CROP + " WHERE " + CROP_COLUMN_FIELD_ID + "=" +field_ID;
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            Crop newCrop;
            ArrayList<Crop> arrayList = new ArrayList<>();
            while (rs.next()) {
                newCrop = new Crop(rs.getInt(CROP_COLUMN_FIELD_ID),
                        rs.getString(CROP_COLUMN_NAME),
                        rs.getFloat(CROP_COLUMN_YIELD),
                        rs.getFloat(CROP_COLUMN_COST),
                        rs.getFloat(CROP_COLUMN_REVENUE),
                        rs.getInt(CROP_COLUMN_YEAR),
                        rs.getString(CROP_COLUMN_DESCRIPTION));
                arrayList.add(newCrop);
            }
            return arrayList;
        } catch (Exception e) {
            System.out.println("cannot get crops (ArrayList) ");
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<FarmLand> getFarmLand(String ownerMail) {
        String sql = "SELECT * FROM " + TABLE_FARMLAND + " WHERE " + FARMLAND_COLUMN_OWNER_ID + "=" +getOwnerID(ownerMail);
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            FarmLand newFarmLand;
            ArrayList<FarmLand> arrayList = new ArrayList<>();
            while (rs.next()) {
                newFarmLand = new FarmLand(getPerson(ownerMail)
                        , rs.getString(FARMLAND_COLUMN_DESCRIPTION)
                        , rs.getString(FARMLAND_COLUMN_NAME)
                        , rs.getString(FARMLAND_COLUMN_COLOR)
                        , rs.getString(FARMLAND_COLUMN_GROUP_NAME)
                        , rs.getInt(FARMLAND_COLUMN_CURRENT_CROP)
                        , (ArrayList) deSerialize(rs.getBytes(FARMLAND_COLUMN_COORDINATES))
                        , (double[]) deSerialize(rs.getBytes(FARMLAND_COLUMN_CENTER_COORDINATES)));

                arrayList.add(newFarmLand);
            }
            return arrayList;
        } catch (Exception e) {
            System.out.println("cannot get farmlands (ArrayList) ");
            e.printStackTrace();
        }
        return null;
    }

    private Object deSerialize(byte[] bytes) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return objectInputStream.readObject();
        } catch (Exception e) {
            System.out.println("cannot deserialize ");
            e.printStackTrace();
        }
        return null;

    }


    public ArrayList<RealEstate> getRealEstate(String ownerMail) {
        String sql = "SELECT * FROM " + TABLE_REAL_ESTATE + " WHERE " + REAL_ESTATE_COLUMN_OWNER_ID + "=" +getOwnerID(ownerMail);
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            RealEstate newRealEstate;
            ArrayList<RealEstate> arrayList = new ArrayList();
            while (rs.next()) {
                newRealEstate = new RealEstate(getPerson(ownerMail)
                        , rs.getString(REAL_ESTATE_COLUMN_TYPE)
                        , rs.getInt(REAL_ESTATE_COLUMN_FLOOR_NO)
                        , rs.getInt(REAL_ESTATE_COLUMN_APT_NO)
                        , rs.getString(REAL_ESTATE_COLUMN_DESCRIPTION)
                        , rs.getString(REAL_ESTATE_COLUMN_NAME)


                        , rs.getString(REAL_ESTATE_COLUMN_COLOR)
                        , rs.getString(REAL_ESTATE_COLUMN_GROUP_NAME)
                        , (ArrayList) deSerialize(rs.getBytes(REAL_ESTATE_COLUMN_COORDINATES))
                        , (double[]) deSerialize(rs.getBytes(REAL_ESTATE_COLUMN_CENTER_COORDINATES)));
                arrayList.add(newRealEstate);
            }
            return arrayList;
        } catch (Exception e) {
            System.out.println("cannot get real estates (ArrayList) ");
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Government> getGovernment(String ownerMail) {
        String sql = "SELECT * FROM " + TABLE_GOVERNMENT + " WHERE " + GOVERNMENT_COLUMN_OWNER_ID + "=" +getOwnerID(ownerMail);
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            ArrayList<Government> arrayList = new ArrayList<>();
            Government newGovernment;
            while (rs.next()) {
                newGovernment = new Government(getPerson(ownerMail)
                        , rs.getString(GOVERNMENT_COLUMN_DESCRIPTION)
                        , rs.getString(GOVERNMENT_COLUMN_NAME)
                        , rs.getString(GOVERNMENT_COLUMN_COLOR)
                        , rs.getString(GOVERNMENT_COLUMN_GROUP_NAME)
                        , (ArrayList) deSerialize(rs.getBytes(GOVERNMENT_COLUMN_COORDINATES))
                        , (double[]) deSerialize(rs.getBytes(GOVERNMENT_COLUMN_CENTER_COORDINATES)));
                arrayList.add(newGovernment);
            }
            return arrayList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int getFarmlandID(String fieldName, String ownerMail) {
        String sql = "SELECT " + FARMLAND_COLUMN_ID + " FROM " + TABLE_FARMLAND
                + " WHERE " + FARMLAND_COLUMN_OWNER_ID + "=" + getOwnerID(ownerMail)
                + " AND " + FARMLAND_COLUMN_NAME + "= \"" + fieldName + "\"";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet set = statement.executeQuery()) {
            return set.getInt(1);
        } catch (SQLException e) {
            System.out.println("cannot get farmland ID ");
            e.printStackTrace();
        }
        return -1;
    }

    public int getGovernmentID(String fieldName, String ownerMail) {
        String sql = "SELECT " + GOVERNMENT_COLUMN_ID + " FROM " + TABLE_GOVERNMENT
                + " WHERE " + GOVERNMENT_COLUMN_NAME + "=" + "\"" + fieldName + "\" AND "
                + GOVERNMENT_COLUMN_OWNER_ID + "=" + getOwnerID(ownerMail);
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet set = statement.executeQuery()) {
            return set.getInt(1);
        } catch (SQLException e) {
            System.out.println("cannot get government ID ");
            e.printStackTrace();
        }
        return -1;
    }

    public int getRealEstateID(String fieldName, String ownerMail) {
        String sql = "SELECT " + REAL_ESTATE_COLUMN_ID + " FROM " + TABLE_REAL_ESTATE
                + " WHERE " + REAL_ESTATE_COLUMN_NAME + "=" + "\"" + fieldName + "\" AND "
                + REAL_ESTATE_COLUMN_OWNER_ID + "=" + getOwnerID(ownerMail);
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet set = statement.executeQuery()) {
            return set.getInt(1);
        } catch (SQLException e) {
            System.out.println("cannot get Real estate ID ");
            e.printStackTrace();
        }
        return -1;
    }

    public boolean deletePerson(String personMail) {
        String sql = "DELETE FROM " + TABLE_PERSON + " WHERE "
                + PERSON_COLUMN_MAIL + "=" + "\"" + personMail + "\"";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("cannot delete person ");
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteFarmland(int fieldID) {
        String sql = "DELETE FROM " + TABLE_FARMLAND + " WHERE "
                + FARMLAND_COLUMN_ID + "=" +fieldID;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("cannot delete farmland ");
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteGovernment(int fieldID) {
        String sql = "DELETE FROM " + TABLE_GOVERNMENT + " WHERE "
                + GOVERNMENT_COLUMN_ID + "=" +fieldID;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("cannot delete government ");
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteRealEstate(int fieldID) {
        String sql = "DELETE FROM " + TABLE_REAL_ESTATE + " WHERE "
                + REAL_ESTATE_COLUMN_ID + "=" +fieldID;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("cannot delete real estate ");
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCrop(int cropID) {
        String sql = "DELETE FROM " + TABLE_CROP + " WHERE "
                + CROP_COLUMN_ID + "=" +cropID;
        String sqlSetCropToNull = "UPDATE " + TABLE_FARMLAND + " SET " + FARMLAND_COLUMN_CURRENT_CROP + " = null"
                + " WHERE " + FARMLAND_COLUMN_CURRENT_CROP +"="+cropID;
        try (PreparedStatement statement = connection.prepareStatement(sql);
             PreparedStatement statement1 = connection.prepareStatement(sqlSetCropToNull)) {
            statement1.executeUpdate();
            statement.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("cannot delete crop ");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePersonName(String mail, String newName) {
        String sql = "UPDATE " + TABLE_PERSON + " SET " + PERSON_COLUMN_NAME + "=" + "\"" + newName + "\"" +
                " WHERE " + PERSON_COLUMN_MAIL + "=" + "\"" + mail + "\"";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
            return true;
        } catch (Exception e) {
            System.out.println("cannot update person (name) ");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCoordinatesFarmland(int fieldID, ArrayList newCoordinates) {
        String sql = "UPDATE " + TABLE_FARMLAND + " SET " + FARMLAND_COLUMN_COORDINATES + "= ? " +
                " WHERE " + FARMLAND_COLUMN_ID + "=" + fieldID;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBytes(1, serialize(newCoordinates));
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("cannot update Farmland (coordinates) ");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCenterCoordinatesFarmland(int fieldID, double newCenter) {
        String sql = "UPDATE " + TABLE_FARMLAND + " SET " + FARMLAND_COLUMN_CENTER_COORDINATES + "= ? " +
                " WHERE " + FARMLAND_COLUMN_ID + "=" + fieldID;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBytes(1, serialize(newCenter));
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("cannot update Farmland (Center Coordinates) ");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCoordinatesGovernment(int fieldID, ArrayList newCoordinates) {
        String sql = "UPDATE " + TABLE_GOVERNMENT + " SET " + GOVERNMENT_COLUMN_COORDINATES + "= ? " +
                " WHERE " + GOVERNMENT_COLUMN_OWNER_ID + "=" + fieldID;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBytes(1, serialize(newCoordinates));
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("cannot update Government (coordinates) ");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCenterCoordinatesGovernment(int fieldID, double newCenter) {
        String sql = "UPDATE " + TABLE_GOVERNMENT + " SET " + GOVERNMENT_COLUMN_CENTER_COORDINATES + "= ? " +
                " WHERE " + GOVERNMENT_COLUMN_OWNER_ID + "=" + fieldID;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBytes(1, serialize(newCenter));
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("cannot update Farmland (Center Coordinates) ");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCoordinatesRealEstate(int fieldID, ArrayList newCoordinates) {
        String sql = "UPDATE " + TABLE_REAL_ESTATE + " SET " + REAL_ESTATE_COLUMN_COORDINATES + "= ? " +
                " WHERE " + REAL_ESTATE_COLUMN_ID + "=" + fieldID;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBytes(1, serialize(newCoordinates));
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("cannot update Real Estate (Coordinates) ");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCenterCoordinatesRealEstate(int fieldID, double newCenter) {
        String sql = "UPDATE " + TABLE_REAL_ESTATE + " SET " + REAL_ESTATE_COLUMN_CENTER_COORDINATES + "= ? " +
                " WHERE " + REAL_ESTATE_COLUMN_ID + "=" + fieldID;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBytes(1, serialize(newCenter));
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("cannot update Real Estate (Center Coordinates) ");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePersonPassword(String mail, String newPassword) {
        String sql = "UPDATE " + TABLE_PERSON + " SET " + PERSON_COLUMN_PASSWORD + "=" + "\"" + newPassword + "\"" +
                " WHERE " + PERSON_COLUMN_MAIL + "=" + "\"" + mail + "\"";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            System.out.println("cannot update Person (password) ");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePersonUserType(String mail, int newUserType) {
        String sql = "UPDATE " + TABLE_PERSON + " SET " + PERSON_COLUMN_USERTYPE + "=" + "\"" + newUserType + "\"" +
                " WHERE " + PERSON_COLUMN_MAIL + "=" + "\"" + mail + "\"";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            System.out.println("cannot update Person (User Type) ");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePersonDescription(String mail, String newDescription) {
        String sql = "UPDATE " + TABLE_PERSON + " SET " + PERSON_COLUMN_DESCRIPTION + "=" + "\"" + newDescription + "\"" +
                " WHERE " + PERSON_COLUMN_MAIL + "=" + "\"" + mail + "\"";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            System.out.println("cannot update Person (description) ");
            e.printStackTrace();
            return false;
        }
    }

    //UPDATE CROPS
    public boolean updateCropYield(int field_ID, double newYield) {
        String sql = "UPDATE " + TABLE_CROP + " SET " + CROP_COLUMN_YIELD + "=" +newYield+
                " WHERE " + CROP_COLUMN_FIELD_ID + "="+ field_ID;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            System.out.println("cannot update Crop (yield) ");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCropCost(int field_ID, double newCost) {
        String sql = "UPDATE " + TABLE_CROP + " SET " + CROP_COLUMN_COST + "=" +newCost+
                " WHERE " + CROP_COLUMN_FIELD_ID + "=" +field_ID;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            System.out.println("cannot update Crop (cost) ");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCropRevenue(int field_ID, double newRevenue) {
        String sql = "UPDATE " + TABLE_CROP + " SET " + CROP_COLUMN_REVENUE + "=" +newRevenue +
                " WHERE " + CROP_COLUMN_FIELD_ID + "=" +field_ID;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            System.out.println("cannot update Crop (revenue) ");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCropYear(int field_ID, int newYear) {
        String sql = "UPDATE " + TABLE_CROP + " SET " + CROP_COLUMN_YEAR + "=" +newYear +
                " WHERE " + CROP_COLUMN_FIELD_ID + "=" +field_ID;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            System.out.println("cannot update Crop (year) ");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCropDescription(int field_ID, String newDescription) {
        String sql = "UPDATE " + TABLE_CROP + " SET " + CROP_COLUMN_DESCRIPTION + "=" + "\"" + newDescription + "\"" +
                " WHERE " + CROP_COLUMN_FIELD_ID + "=" +field_ID;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            System.out.println("cannot update Crop (description) ");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCropName(int field_ID, String newName) {
        String sql = "UPDATE " + TABLE_CROP + " SET " + CROP_COLUMN_NAME + "=" + "\"" + newName + "\"" +
                " WHERE " + CROP_COLUMN_FIELD_ID + "=" +field_ID;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            System.out.println("cannot update Crop (name) ");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateFarmLandColor(int fieldID, String newColor) {
        String sql = "UPDATE " + TABLE_FARMLAND + " SET " + FARMLAND_COLUMN_COLOR + "=" + "\"" + newColor + "\"" +
                " WHERE " + FARMLAND_COLUMN_ID + "=" + fieldID;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
            return true;
        } catch (Exception e) {
            System.out.println("cannot update Farmland (color) ");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateFarmLandDescription(int fieldID, String newDescription) {
        String sql = "UPDATE " + TABLE_FARMLAND + " SET " + FARMLAND_COLUMN_DESCRIPTION + "=" + "\"" + newDescription + "\"" +
                " WHERE " + FARMLAND_COLUMN_ID + "=" + fieldID;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            System.out.println("cannot update Farmland (description) ");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateFarmLandGroupName(int fieldID, String newGroupName) {
        String sql = "UPDATE " + TABLE_FARMLAND + " SET " + FARMLAND_COLUMN_GROUP_NAME + "=" + "\"" + newGroupName + "\"" +
                " WHERE " + FARMLAND_COLUMN_ID + "=" + fieldID;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            System.out.println("cannot update Farmland (group name) ");
            e.printStackTrace();
            return false;
        }
    }


    public boolean updateRealEstateFloorNo(int fieldID, int newFloorNo) {
        String sql = "UPDATE " + TABLE_REAL_ESTATE + " SET " + REAL_ESTATE_COLUMN_FLOOR_NO + "=" +newFloorNo +
                " WHERE " + REAL_ESTATE_COLUMN_ID + "=" + fieldID;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            System.out.println("cannot update real estate (floor no) ");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateRealEstateAptNo(int fieldID, int newAptNo) {
        String sql = "UPDATE " + TABLE_REAL_ESTATE + " SET " + REAL_ESTATE_COLUMN_APT_NO + "=" +newAptNo +
                " WHERE " + REAL_ESTATE_COLUMN_ID + "=" + fieldID;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            System.out.println("cannot update real estate (apt no) ");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateRealEstateType(int fieldID, String newType) {
        String sql = "UPDATE " + TABLE_REAL_ESTATE + " SET " + REAL_ESTATE_COLUMN_TYPE + "=" + "\"" + newType + "\"" +
                " WHERE " + REAL_ESTATE_COLUMN_ID + "=" + fieldID;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            System.out.println("cannot update real estate (type) ");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateRealEstateDescription(int fieldID, String newDescription) {
        String sql = "UPDATE " + TABLE_REAL_ESTATE + " SET " + REAL_ESTATE_COLUMN_DESCRIPTION + "=" + "\"" + newDescription + "\"" +
                " WHERE " + REAL_ESTATE_COLUMN_ID + "=" + fieldID;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            System.out.println("cannot update real estate (description) ");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateRealEstateGroupName(int fieldID, String newGroupName) {
        String sql = "UPDATE " + TABLE_REAL_ESTATE + " SET " + REAL_ESTATE_COLUMN_GROUP_NAME + "=" + "\"" + newGroupName + "\"" +
                " WHERE " + REAL_ESTATE_COLUMN_ID + "=" + fieldID;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            System.out.println("cannot update real estate (group name) ");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateRealEstateColor(int fieldID, String newColor) {
        String sql = "UPDATE " + TABLE_REAL_ESTATE + " SET " + REAL_ESTATE_COLUMN_COLOR + "=" + "\"" + newColor + "\"" +
                " WHERE " + REAL_ESTATE_COLUMN_ID + "=" + fieldID;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            System.out.println("cannot update real estate (color) ");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateGovernmentDescription(int fieldID, String newDescription) {
        String sql = "UPDATE " + TABLE_GOVERNMENT + " SET " + GOVERNMENT_COLUMN_DESCRIPTION + "=" + "\"" + newDescription + "\"" +
                " WHERE " + GOVERNMENT_COLUMN_ID + "=" + fieldID;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            System.out.println("cannot update government (description) ");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateGovernmentColor(int fieldID, String newColor) {
        String sql = "UPDATE " + TABLE_GOVERNMENT + " SET " + GOVERNMENT_COLUMN_COLOR + "=" + "\"" + newColor + "\"" +
                " WHERE " + GOVERNMENT_COLUMN_ID + "=" + fieldID;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            System.out.println("cannot update government (color) ");
            e.printStackTrace();
            return false;
        }
    }


    public boolean updateGovernmentGroupName(int fieldID, String newGroupName) {
        String sql = "UPDATE " + TABLE_GOVERNMENT + " SET " + GOVERNMENT_COLUMN_GROUP_NAME + "=" + "\"" + newGroupName + "\"" +
                " WHERE " + GOVERNMENT_COLUMN_ID + "=" + fieldID;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            System.out.println("cannot update government (group name) ");
            e.printStackTrace();
            return false;
        }
    }
}
