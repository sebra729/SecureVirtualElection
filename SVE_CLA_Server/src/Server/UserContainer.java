package Server;

import java.util.LinkedList;

import java.util.UUID;

public class UserContainer {
	
	private LinkedList<User> users = new LinkedList<User>();
	
	private static class SingeltonHolder {
        public static final UserContainer INSTANCE = new UserContainer();
	}
		
	public static UserContainer instance() {
		return SingeltonHolder.INSTANCE;
	}
		
	private UserContainer() {
		User user = new User("johan", "password", null);
		users.add(user);
		user = new User("maria", "password", null);
		users.add(user);
		user = new User("erik", "password", null);
		users.add(user);
	}
	
	public void addUser(User user){
        for(User tmpUs : users){
              if(tmpUs.getUserName() == user.getUserName()){
                     return;
              }
        }
       users.add(user);
	}
	
	public User verifyUser(String[] user){
		for(User tmpUs : users){
			if(tmpUs.getUserName().equals(user[0])  && tmpUs.getPassword().equals(user[1])){
				return tmpUs;
			}
		}
		return null;
	}
		
		
		/**
		 * Update user element in user list not tested might not be needed
		 * @param user new element 
		 * @return
		 */
		public boolean updateUser(User user){
			int i = 0;
			for(User tmpUs : users){
				i++;
				if(tmpUs.getUserName() == user.getUserName()){
					System.out.println("Updating user:" + users.get(i).toString() + " TO " + user.toString() );
					users.set(i, user);
					return true;
				}
			}
			return false;
		}
    
}
