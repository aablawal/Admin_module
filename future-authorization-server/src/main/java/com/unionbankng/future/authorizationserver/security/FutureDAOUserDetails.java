package com.unionbankng.future.authorizationserver.security;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.unionbankng.future.authorizationserver.entities.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

@Getter
@Setter
public class FutureDAOUserDetails extends User implements UserDetails, OidcUser {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

    private Map<String, Object> attributes;
    private String name;
    private Map<String, Object> claims;
    private OidcUserInfo userInfo;
    private OidcIdToken idToken;

	public FutureDAOUserDetails(User user) {

		super(user);
    }


    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

    	Collection<SimpleGrantedAuthority> c = new ArrayList<>();
    	c.add(new SimpleGrantedAuthority("USER"));
    	return c;
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getEmail();
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
        return super.getIsEnabled();
    }



}