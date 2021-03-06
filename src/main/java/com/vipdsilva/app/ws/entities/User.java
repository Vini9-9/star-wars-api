package com.vipdsilva.app.ws.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.vipdsilva.app.ws.model.request.UserRequestModel;
import com.vipdsilva.app.ws.model.response.UserDtoResponseModel;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class User implements UserDetails{

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    @NotBlank(message = "Name não pode estar em branco")
	private String name;
    @Email(message = "Email deve ser válido")
	private String email;
    @NotBlank(message = "Password não pode estar em branco")
	private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Profile> profiles = new ArrayList<>();

    public User() {
    }

    public User(UserRequestModel userInfo, Profile profileDefault) {
        this.name = userInfo.getName();
        this.email = userInfo.getEmail();
        this.password = userInfo.getPassword();
        setProfile(profileDefault);
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public Long getId() {
		return id;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.profiles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserDtoResponseModel toResponseDto() {
        return new UserDtoResponseModel(this);
    }

    public void clearProfiles() {
        this.profiles = new ArrayList<>();
    }

    public void setProfile(Profile profile) {
        this.profiles.add(profile);
    }

    public List<String> getProfilesName(List<Profile> profiles) {
        
        List<String> profilesName = new ArrayList<>();
        
        if(!profiles.isEmpty()) {

            for (Profile profile : profiles) {

                profilesName.add(profile.getName());

            }

        }
        
        return profilesName;
    }

}

