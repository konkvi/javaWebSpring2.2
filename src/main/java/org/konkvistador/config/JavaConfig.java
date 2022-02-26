package org.konkvistador.config;

import org.konkvistador.controller.PostController;
import org.konkvistador.repository.PostRepository;
import org.konkvistador.service.PostService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfig {

    @Bean
    public PostController postController(PostService service){
        return new PostController(service);
    }

    @Bean
    public PostService postService(PostRepository repository){
        return new PostService(repository);
    }

    @Bean
    public PostRepository postRepository(){
        return new PostRepository();
    }
}
