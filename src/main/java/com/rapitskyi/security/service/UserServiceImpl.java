package com.rapitskyi.security.service;

import com.rapitskyi.security.entity.User;
import com.rapitskyi.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    public void save(User user) {
        repository.save(user);
    }
    public User findById(Integer id){
        Optional<User> optionalUser = repository.findById(id);
        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException("User with this id is not found");
        }
        return optionalUser.get();
    }
    public User findByEmail(String email){return repository.findByEmail(email).orElseThrow(()->
            new UsernameNotFoundException("There is no user with this email"));}
    public boolean existsByEmail(String email){return repository.existsByEmail(email);}

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = repository.findByEmail(email).orElseThrow(()->
//                new UsernameNotFoundException("There is no user with email"+email));
//
//        return new org.springframework.security.core.userdetails.User(
//                user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole())));
//    }
}
