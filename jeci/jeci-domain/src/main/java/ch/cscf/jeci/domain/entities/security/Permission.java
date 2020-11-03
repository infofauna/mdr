package ch.cscf.jeci.domain.entities.security;

import java.util.HashSet;
import java.util.Set;

public class Permission {
	
	private String name;
	
	private Set<String> authorizedActions;
	
	public Permission(String name, Set<String> authorizedActions) {
		super();
		this.name = name;
		
		if(authorizedActions == null){
			this.authorizedActions = new HashSet<String>();
		}else{
			this.authorizedActions = authorizedActions;
		}
	}

	@Override
	public String toString() {
		return "Permission [name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Permission other = (Permission) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public String getName() {
		return name;
	}

	public Set<String> getAuthorizedActions() {
		return authorizedActions;
	}
	
}
