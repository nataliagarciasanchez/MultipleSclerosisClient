/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menu;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.*;

/**
 *
 * @author maipa
 */

@Table(name = "Users")
public class User implements Serializable{
    
    private static final long serialVersionUID = -7022171013544748813L; 
    
    @Id
    @GeneratedValue(generator = "Users")
    @TableGenerator(name = "Users", table = "sqlite_sequence",
    pkColumnName = "name", valueColumnName = "seq", pkColumnValue = "Users")
    
    private Integer id; 
    @Column(unique = true)
      private String email;//the email is the username 
      private String password;
      
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roleId")
    private final String role="Patient";
     
      
    public User() {
    	super();
    }
      
    public User(String email, String password) {
	super();
	this.email = email;
	this.password = password;
    }
    
    public Integer getId() {
	return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        return Objects.equals(id, other.id);
    }
    
    @Override
    public String toString() {
	return "User [id=" + id + ", email=" + email +", password=" + password + ", role="+ role + "]";
    } 
}
