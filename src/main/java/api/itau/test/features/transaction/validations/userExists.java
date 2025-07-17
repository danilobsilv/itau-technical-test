package api.itau.test.features.transaction.validations;

import api.itau.test.exceptionHandler.resourceNotFoundException.ResourceNotFoundException;
import api.itau.test.features.transaction.dto.CreateTransactionDto;
import api.itau.test.features.user.User;
import api.itau.test.features.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class userExists implements TransactionValidator{

    @Autowired
    UserRepository userRepository;

    @Override
    public void validate(CreateTransactionDto data) {
        boolean userExists = userRepository.existsById(data.userId());
        if (!userExists){
            throw new ResourceNotFoundException("User not found: " + data.userId());
        }
    }
}
