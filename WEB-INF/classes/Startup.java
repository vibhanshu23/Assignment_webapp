import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.File;

@WebServlet("/Startup")

/*  
startup servlet Intializes HashMap in SaxParserDataStore
*/

public class Startup extends HttpServlet
{

	public void init() throws ServletException
    {
    String relativeWebPathForProductDetails = "/ProductCatalog.xml";
  	String absoluteDiskPath = getServletContext().getRealPath(relativeWebPathForProductDetails);
    SaxParserDataStore.addHashmap(absoluteDiskPath);

  }
}
