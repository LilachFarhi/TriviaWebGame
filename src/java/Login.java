import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Login extends HttpServlet {

    public static final String FirstNameAttribute = "firstName";
    public static final String ErrorMessageAttribute = "errorMessage";
    public static final String LastNameAttribute = "lastName";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        response.setContentType("text/html;charset=UTF-8");
        
        String firstName = null;
        String lastName = null;
        Cookie[] cookies = request.getCookies();
        
        if (cookies != null) {
                for(int i=0; i<cookies.length; i++) {
                    Cookie c = cookies[i];
                    
                    if ((c.getName().equals(FirstNameAttribute)))
                    {
                        firstName = c.getValue();
                    }
                    
                    if ((c.getName().equals(LastNameAttribute)))
                    {
                        lastName = c.getValue();
                    }
                }
            }
        
        if (firstName != null && lastName != null)
        {
            session.setAttribute(FirstNameAttribute, firstName);
            session.setAttribute(LastNameAttribute, lastName);
            response.sendRedirect("StartGame.html");
        }
        else
        {
            Object errMessage = request.getAttribute(ErrorMessageAttribute);
            String firstNameValue = request.getParameter(FirstNameAttribute);
            String lastNameValue = request.getParameter(LastNameAttribute);
            
            try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<link rel=\"stylesheet\" href=\"Main.css\"/>");
            out.println("<title>Login</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<form action=\"ValidateUserData\" method=\"GET\">");
            out.println("<h1>Please login to start the game</h1>");
            out.println("<br>");
            if (errMessage != null)
            {
                 out.println("<h3 id=\"errorMessage\">There are validation errors: "
                    + errMessage + "</h3>");
            }
            out.println("<h2>First name: </h2>");
            out.println("<input type=\"text\" name=\"" +
                    FirstNameAttribute + "\" size=\"50\" ");
            
            if (firstNameValue != null)
            {
                out.println("value=\"" + firstNameValue + "\"");
            }
            
            out.println(">");
            out.println("<h2>Last name: </h2>");
            out.println("<input type=\"text\" name=\""+ LastNameAttribute +
                    "\" size=\"50\" ");
            
            if (lastNameValue != null)
            {
                out.println("value=\"" + lastNameValue + "\"");
            }
            
            out.println(">");
            out.println("<br>");
            out.println("<br>");
            out.println("<input type=\"submit\" value=\"Submit\">");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
