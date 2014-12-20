package activiti.spring.loanRequest.springweb.services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import activiti.rest.IdentityRestClient;

@SuppressWarnings("deprecation")
public class ActivitiUserDetailService implements UserDetailsService{
	
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		String password = IdentityRestClient.findUserPassword(username);
		if (password == null)
			  throw new UsernameNotFoundException("Invalid username/password.");
		
		Collection<GrantedAuthority> grandedAuthorities = new ArrayList<GrantedAuthority>();
		grandedAuthorities.add(new GrantedAuthorityImpl("ROLE_USER"));
		return new org.springframework.security.core.userdetails.User(username, password, true, true, true, true, grandedAuthorities);
	}

}
