package demo.security.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/helloWorld")
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public HomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) {
        String name = request.getParameter("name").trim();
        // text/plain prevents the browser from interpreting the reflected
        // "name" value as HTML/JavaScript, which avoids reflected XSS.
        response.setContentType("text/plain");
        try {
            PrintWriter out = response.getWriter();
            out.print("Hello " + name);
            out.close();
        } catch (IOException e) {
            // The response could not be written to; nothing more to do here.
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
