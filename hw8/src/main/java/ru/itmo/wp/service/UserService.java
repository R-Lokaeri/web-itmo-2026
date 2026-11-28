package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.PostForm;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.repository.RoleRepository;
import ru.itmo.wp.repository.TagRepository;
import ru.itmo.wp.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    /**
     * @noinspection FieldCanBeLocal, unused
     */
    private final RoleRepository roleRepository;

    private final TagRepository tagRepository;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       TagRepository tagRepository) {
        this.userRepository = userRepository;

        this.roleRepository = roleRepository;
        for (Role.Name name : Role.Name.values()) {
            if (!roleRepository.existsByName(name)) {
                roleRepository.save(new Role(name));
            }
        }

        this.tagRepository = tagRepository;
    }

    public User register(UserCredentials userCredentials) {
        User user = new User();
        user.setLogin(userCredentials.getLogin());
        userRepository.save(user);
        userRepository.updatePasswordSha(user.getId(), userCredentials.getLogin(), userCredentials.getPassword());
        return user;
    }

    public boolean isLoginVacant(String login) {
        return userRepository.countByLogin(login) == 0;
    }

    public User findByLoginAndPassword(String login, String password) {
        return login == null || password == null ? null : userRepository.findByLoginAndPassword(login, password);
    }

    public User findById(Long id) {
        return id == null ? null : userRepository.findById(id).orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAllByOrderByIdDesc();
    }

    public void writePost(User user, PostForm postForm) {
        Post post = new Post();
        post.setTitle(postForm.getTitle());
        post.setText(postForm.getText());
        post.setUser(user);

        String tags = postForm.getTags().trim();
        List<Tag> postTags = new ArrayList<>();
        HashSet<String> usedTags = new HashSet<>();
        if (!tags.isEmpty()) {
            String[] tagArray = tags.split("\\s+");
            for (String tagName : tagArray) {
                if (!usedTags.contains(tagName)) {
                    usedTags.add(tagName);
                    Tag tag = tagRepository.findByName(tagName);
                    if (tag == null) {
                        tag = new Tag();
                        tag.setName(tagName);
                    }
                    postTags.add(tag);
                }
            }
        }
        post.setTags(postTags);

        user.addPost(post);
        userRepository.save(user);
    }
}
