package ru.stqa.pft.addressbook.model;

public class ContactData {
  private int id;
  private final String lastName;
  private final String firstName;
  private final String address;
  private final String homeTelephone;
  private final String email;
  private final String group;

  @Override
  public String toString() {
    return "ContactData{" +
            "id='" + id + '\'' +
            ", lastName='" + lastName + '\'' +
            ", firstName='" + firstName + '\'' +
            '}';
  }


  public ContactData(int id, String lastName, String firstName, String address, String homeTelephone, String email, String group) {
    this.id = id;
    this.lastName = lastName;
    this.firstName = firstName;
    this.address = address;
    this.homeTelephone = homeTelephone;
    this.email = email;
    this.group = group;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ContactData that = (ContactData) o;

    if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
    return firstName != null ? firstName.equals(that.firstName) : that.firstName == null;
  }

  @Override
  public int hashCode() {
    int result = lastName != null ? lastName.hashCode() : 0;
    result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
    return result;
  }

  public ContactData(String lastName, String firstName, String address, String homeTelephone, String email, String group) {
    this.id = Integer.MAX_VALUE;
    this.lastName = lastName;
    this.firstName = firstName;
    this.address = address;
    this.homeTelephone = homeTelephone;
    this.email = email;
    this.group = group;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getAddress() {
    return address;
  }

  public String getHomeTelephone() {
    return homeTelephone;
  }

  public String getEmail() {
    return email;
  }

  public String getGroup() { return group; }
}
