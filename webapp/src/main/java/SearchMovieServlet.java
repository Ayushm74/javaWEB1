import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


@WebServlet("/searchMovie")  // URL Mapping
public class SearchMovieServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String movieName = request.getParameter("movieName");

        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT * FROM Movie WHERE Movie_name LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + movieName + "%"); // Search for similar movie names

            ResultSet rs = pstmt.executeQuery();

            out.println("<html><body>");
            out.println("<h2>Movie Search Results:</h2>");

            boolean found = false;
            while (rs.next()) {
                found = true;
                out.println("<p><strong>Movie Name:</strong> " + rs.getString("Movie_name") + "</p>");
                out.println("<p><strong>Rating:</strong> " + rs.getString("Rating") + "</p>");
                out.println("<p><strong>Actor:</strong> " + rs.getString("Actor") + "</p>");
                out.println("<p><strong>Actress:</strong> " + rs.getString("Actress") + "</p>");
                out.println("<p><strong>Director:</strong> " + rs.getString("Director") + "</p>");
                out.println("<p><strong>About:</strong> " + rs.getString("About_Movie") + "</p>");
                out.println("<hr>");
            }

            if (!found) {
                out.println("<p>No movies found with that name.</p>");
            }

            out.println("<a href='index.html'>Go back</a>");
            out.println("</body></html>");

            conn.close();
        } catch (Exception e) {
            out.println("<p>Error retrieving data</p>");
            e.printStackTrace();
        }
    }
}
