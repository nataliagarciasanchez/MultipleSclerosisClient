/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menu;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.*;

/**
 *
 * @author maipa
 */
@Entity
@Table(name = "Users")
public class User implements Serializable{
    
    private static final long serialVersionUID = -7022171013544748813L;
    private EntityManager em; 
    
    @Id
    @GeneratedValue(generator = "Users")
    @TableGenerator(name = "Users", table = "sqlite_sequence",
    pkColumnName = "name", valueColumnName = "seq", pkColumnValue = "users")
    
    private Integer id; 
    @Column(unique = true)
      private String username;
      private String password;
      private String email; 
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roleId")
      private final String role="Patient";
     
      
    public User() {
    	super();
    }
      
    public User(String username, String password,String email) {
	super();
	this.username = username;
	this.password = password;
	this.email = email;
        em = Persistence.createEntityManagerFactory("MultipleSclerosis-provider").createEntityManager();
	em.getTransaction().begin();
	em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
	em.getTransaction().commit();
    }
    
    public Integer getId() {
	return id;
    }
    
    public void disconnect() {
	em.close();
    }

    public void register(User user) {

        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();

    }
	
    public User login(String name, String password) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM Users WHERE username = ? AND password = ? ", User.class);
            q.setParameter(1, name);
            q.setParameter(2, password);
            User user = (User) q.getSingleResult();
            return user;

        } catch (NoResultException e) {
            return null;
        }
    }
    
    public void deleteUser(String name,String password) {
    	Query q=em.createNativeQuery("SELECT * FROM Users WHERE name LIKE ? AND password LIKE ?");
    	em.getTransaction().begin();
    	q.setParameter(1, name);
    	q.setParameter(2,password);
    	User user=(User)q.getSingleResult();
        user.deleteUser(name, password);
    	em.getTransaction().commit();	
    }
    
    public void updatePassword(User user, String new_password) {
        Query q = em.createNativeQuery("UPDATE Users SET " + " password = ? " + " WHERE id = ?");
        em.getTransaction().begin();
        q.setParameter(1, new_password);
        q.setParameter(2, user.getId());
        em.getTransaction().commit();
    }
    
    public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", role="
				+ role + "]";
	} 
}
