/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJOs;

import java.io.Serializable;
import java.util.Objects;


/**
 *
 * @author maipa
 */

public class User implements Serializable{
    
    private static final long serialVersionUID = -7022171013544748813L; 
    
    private Integer id; 
    private String email;//the email is the username 
    private String password;
    private Role role;
     
      
    public User() {
    	super();
    }
      
    public User(String email, String password, Role role) {
	super();
	this.email = email;
	this.password = password;
        this.role=role;
    }
    
    public User(String email, String password) {
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

    public Role getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public void setRole(Role role) {
        this.role = role;
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
