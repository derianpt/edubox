package unifycontrollers.admin;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import unifysessionbeans.admin.UserProfileAdminMgrBeanRemote;
import unifysessionbeans.admin.MarketplaceAdminMgrBeanRemote;
import unifysessionbeans.admin.ErrandsAdminMgrBeanRemote;

public class UserProfileAdminController extends HttpServlet {
    @EJB
    private UserProfileAdminMgrBeanRemote uamr;
    @EJB
    private MarketplaceAdminMgrBeanRemote mamr;
    @EJB
    private ErrandsAdminMgrBeanRemote eamr;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            RequestDispatcher dispatcher;
            ServletContext servletContext = getServletContext();
            String pageAction = request.getParameter("pageTransit");
            
            switch (pageAction) {
                case "goToUnifyAdmin":
                    request.setAttribute("itemTransTodayCount", mamr.getItemTransTodayCount());
                    request.setAttribute("itemListingCount", mamr.getItemListingCount());
                    request.setAttribute("errandsTransTodayCount", eamr.getErrandsTransTodayCount());
                    request.setAttribute("errandsListingCount", eamr.getErrandsListingCount());
                    pageAction = "UnifyAdminDashboard";
                    break;
                default:
                    break;
            }
            dispatcher = servletContext.getNamedDispatcher(pageAction);
            dispatcher.forward(request, response);       
        }
        catch(Exception ex) {
            log("Exception in UserProfileAdminController: processRequest()");
            ex.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    public String getServletInfo() { return "User Profile Admin Servlet"; }
}