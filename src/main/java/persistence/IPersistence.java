package persistence;

import model.Account;
import model.User;

import java.util.List;

/**
*
* @author ouziri
* @version 0.1
*/

public interface IPersistence {
	
	Account save(Account account) throws Exception;
	Account findAccountById (int accountId) throws Exception;
	void updateBalance(int accountId, double newBalance) throws Exception;
	List<Account> findAccountByOwnerEmail(String ownerEmail) throws Exception;
	User findUserByEmail(String userEmail) throws Exception;
	User validateUserByEmailPassword(String ownerEmail, String pwd) throws Exception;
	
	
//	public Client findClientById (Long clientId); 
//	public Client registerClient(Client client);

}
