package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.File;
@Entity
@Table(name = "addressbook")
public class ContactData {
  @Id
  @Column(name = "id")
  private int id = Integer.MAX_VALUE;

  @Column(name = "lastName")
  private String lastName;

  @Column(name = "firstName")
  private String firstName;

  @Transient
  private String address;

  @Column(name = "home")
  @Type(type = "text")
  private String homePhone;

  @Column(name = "mobile")
  @Type(type = "text")
  private String mobilePhone;

  @Column(name = "work")
  @Type(type = "text")
  private String workPhone;

  @Transient
  private String email;
  @Transient
  private String email2;
  @Transient
  private String email3;
  @Transient
  private String group;
  @Transient
  private String allPhones;
  @Transient
  private String allEmails;

  @Column(name = "photo")
  @Type(type = "text")
  private String photo;


  public File getPhoto() {
    return new File(photo);
  }
  public ContactData withPhoto(File photo) {
    this.photo = photo.getPath();
    return this;
  }


  public String getAllPhones() {
    return allPhones;
  }

  public ContactData withAllPhones(String allPhones) {
    this.allPhones = allPhones;
    return this;
  }

  public String getAllEmails() {
    return allEmails;
  }

  public ContactData withAllEmails(String allEmails) {
    this.allEmails = allEmails;
    return this;
  }

  @Override
  public String toString() {
    return "ContactData{" +
            "id='" + id + '\'' +
            ", lastName='" + lastName + '\'' +
            ", firstName='" + firstName + '\'' +
            '}';
  }


  public ContactData withId(int id) {
    this.id = id;
    return this;
  }

  public ContactData withLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public ContactData withFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public ContactData withAddress(String address) {
    this.address = address;
    return this;
  }

  public ContactData withHomePhone(String homePhone) {
    this.homePhone = homePhone;
    return this;
  }
  public ContactData withMobilePhone(String mobilePhone) {
    this.mobilePhone = mobilePhone;
    return this;
  }
  public ContactData withWorkPhone(String workPhone) {
    this.workPhone = workPhone;
    return this;
  }

  public ContactData withEmail(String email) {
    this.email = email;
    return this;
  }

  public ContactData withEmail2(String email2) {
    this.email2 = email2;
    return this;
  }

  public ContactData withEmail3(String email3) {
    this.email3 = email3;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ContactData that = (ContactData) o;

    if (id != that.id) return false;
    if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
    return firstName != null ? firstName.equals(that.firstName) : that.firstName == null;
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
    result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
    return result;
  }

  public ContactData withGroup(String group) {
    this.group = group;
    return this;
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

  public String getHomePhone() {
    return homePhone;
  }

  public String getMobilePhone() { return mobilePhone; }

  public String getWorkPhone() { return workPhone; }

  public String getEmail() { return email; }

  public String getEmail2() { return email2; }

  public String getEmail3() { return email3; }

  public String getGroup() { return group; }

}
