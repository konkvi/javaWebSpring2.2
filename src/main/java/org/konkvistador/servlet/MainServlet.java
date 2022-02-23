package org.konkvistador.servlet;

import org.konkvistador.controller.PostController;
import org.konkvistador.repository.PostRepository;
import org.konkvistador.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
    //выносим приватные для класса константы
    private static final String API_POSTS = "/api/posts";
    private static final String API_POSTS_PATH = "/api/posts/\\d+";
    private static final String SLASH = "/";

    private PostController controller;

    @Override
    public void init() {
        final var repository = new PostRepository();
        final var service = new PostService(repository);
        controller = new PostController(service);
    }

    //выносим отдельный метод с GET
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final var path = req.getRequestURI();
        if (path.equals(API_POSTS)) {
            controller.all(resp);
            return;
        }
        if (path.matches(API_POSTS_PATH)) {
            final var id = Long.parseLong(path.substring(path.lastIndexOf(SLASH)));
            controller.getById(id, resp);
            return;
        }
        super.doGet(req, resp);
    }

    //выносим отдельный метод с POST
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final var path = req.getRequestURI();
        if (path.equals(API_POSTS)) {
            controller.save(req.getReader(), resp);
            return;
        }
        super.doPost(req, resp);
    }

    //выносим отдельный метод с DELETE
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final var path = req.getRequestURI();
        if (path.matches(API_POSTS_PATH)) {
            final var id = Long.parseLong(path.substring(path.lastIndexOf(SLASH)));
            controller.removeById(id, resp);
            return;
        }
        super.doDelete(req, resp);
    }
}
