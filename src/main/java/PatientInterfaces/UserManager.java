/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package PatientInterfaces;

import POJOs.Role;
import POJOs.User;
import java.util.List;

/**
 *
 * @author maipa
 */
public interface UserManager {

    

    public void register(User user);

    public void registerDirector(Role dir);

    public void assignRole(User user, Role role);

    public Role getRole(String name);

    public User login(String name, String password);

    public void createRole(Role role);

    public void deleteUser(String name, String password);

    public void updatePassword(User user, String new_password);
}
