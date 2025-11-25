package seeder;


import entity.User;
import enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.UserRepository;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DatabaseSeeder.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args){

        if(userRepository.count()==0){
            log.info("Démarage de seeder et linsertion dun admin et un client  ");
            User adminUser = User.builder()
                    .username("admin@microtech.ma")
                    .password(passwordEncoder.encode("password123")) // IMPORTANT: Toujours hacher!
                    .role(UserRole.ADMIN)
                    .build();

            userRepository.save(adminUser);
            log.info("ADMIN utilisateur créé : {}", adminUser.getUsername());
        }else {
            log.info("Base de données non vide. Seeder ignoré.");
        }


    }


}
