/***************************************************************************************
*   Title:                  UserProfileSysUserController.java
*   Purpose:                SERVLET FOR UNIFY DASHBOARD & PROFILE - SYSUSER (EDUBOX)
*   Created & Modified By:  TAN CHIN WEE WINSTON
*   Credits:                CHEN MENG, NIGEL LEE TJON YI, TAN CHIN WEE WINSTON, ZHU XINYI
*   Date:                   19 FEBRUARY 2018
*   Code version:           1.0
*   Availability:           === NO REPLICATE ALLOWED. YOU HAVE BEEN WARNED. ===
***************************************************************************************/

package unifycontrollers.systemuser;

import java.io.IOException;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import unifysessionbeans.systemuser.MarketplaceSysUserMgrBeanRemote;
import unifysessionbeans.systemuser.UserProfileSysUserMgrBeanRemote;
import unifysessionbeans.systemuser.VoicesSysUserMgrBeanRemote;
import unifysessionbeans.systemuser.ErrandsSysUserMgrBeanRemote;

public class UserProfileSysUserController extends HttpServlet {
    @EJB
    private MarketplaceSysUserMgrBeanRemote msmr;
    @EJB
    private UserProfileSysUserMgrBeanRemote usmr;
    @EJB
    private ErrandsSysUserMgrBeanRemote esmr;
    @EJB
    private VoicesSysUserMgrBeanRemote vsmr;
    String responseMessage = "";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            RequestDispatcher dispatcher;
            ServletContext servletContext = getServletContext();
            
            String pageAction = request.getParameter("pageTransit");
            String loggedInUsername = getCookieUsername(request);
            System.out.println(pageAction);
            
            switch (pageAction) {
                case "goToUnifyBot":
                    pageAction = "UnifyBot";
                    break;
                case "goToUserProfileSYS":
                    String itemSellerID = request.getParameter("itemSellerID");
                    request.setAttribute("userItemListSYS", usmr.viewUserItemList(loggedInUsername, itemSellerID));
                    request.setAttribute("itemCategoryStr", msmr.populateItemCategory());
                    
                    request.setAttribute("userProfileVec", usmr.viewUserProfileDetails(itemSellerID));
                    request.setAttribute("userJobListing", esmr.viewUserJobList(itemSellerID));
                    request.setAttribute("userMessageListTopThreeSYS", usmr.viewUserMessageListTopThree(loggedInUsername));
                    pageAction = "UserProfileSYS";
                    break;
                case "goToUnifyUserAccountSYS":
                    request.setAttribute("userItemAccountListSYS", (ArrayList) usmr.viewUserItemAccountList(loggedInUsername));
                    request.setAttribute("itemCategoryStr", msmr.populateItemCategory());
                    
                    request.setAttribute("userAccountVec", usmr.viewUserProfileDetails(loggedInUsername));
                    request.setAttribute("userMessageListTopThreeSYS", usmr.viewUserMessageListTopThree(loggedInUsername));
                    pageAction = "UserAccountSYS";
                    break;
                case "goToMarketplaceTransSYS":
                    request.setAttribute("itemTransListSYS", (ArrayList) usmr.viewItemTransaction(loggedInUsername));
                    request.setAttribute("userAccountVec", usmr.viewUserProfileDetails(loggedInUsername));
                    request.setAttribute("userMessageListTopThreeSYS", usmr.viewUserMessageListTopThree(loggedInUsername));
                    pageAction = "UserItemTransactionSYS";
                    break;
                case "goToMarketplaceTransDetailsSYS":
                    long itemID = Long.parseLong(request.getParameter("itemID"));
                    long itemTransID = Long.parseLong(request.getParameter("itemTransID"));
                    
                    request.setAttribute("itemTransDetailsSYSVec", usmr.viewTransactionItemDetails(itemID, itemTransID, loggedInUsername));
                    request.setAttribute("userAccountVec", usmr.viewUserProfileDetails(loggedInUsername));
                    request.setAttribute("userMessageListTopThreeSYS", usmr.viewUserMessageListTopThree(loggedInUsername));
                    pageAction = "UserItemTransactionDetailsSYS";
                    break;
                case "goToJobListingInUserProfile":
                    String user = request.getParameter("posterName");
                    request.setAttribute("userProfileVec", usmr.viewUserProfileDetails(user));
                    request.setAttribute("userJobListing", esmr.viewUserJobList(user));
                    pageAction = "JobListingInUserProfileSYS";
                    break;
                case "goToPendingItemOfferListSYS":
                    request.setAttribute("userMarketplaceOfferListSYS", (ArrayList) usmr.viewUserMarketplaceOfferList(loggedInUsername));
                    request.setAttribute("userAccountVec", usmr.viewUserProfileDetails(loggedInUsername));
                    request.setAttribute("userMessageListTopThreeSYS", usmr.viewUserMessageListTopThree(loggedInUsername));
                    pageAction = "PendingItemOfferListSYS";
                    break;
                case "goToPendingItemOfferDetailsSYS":
                    long urlitemID = Long.parseLong(request.getParameter("urlitemID"));
                    request.setAttribute("itemOfferUserListSYS", usmr.viewAnItemOfferUserList(loggedInUsername, urlitemID));
                    
                    request.setAttribute("userAccountVec", usmr.viewUserProfileDetails(loggedInUsername));
                    request.setAttribute("userMessageListTopThreeSYS", usmr.viewUserMessageListTopThree(loggedInUsername));
                    pageAction = "PendingItemOfferDetailsSYS";
                    break;
                case "acceptAnItemOfferSYS":
                    long itemIDHid = Long.parseLong(request.getParameter("itemIDHidden"));
                    long hidItemOfferID = Long.parseLong(request.getParameter("urlItemOfferID"));
                    String sellerAcceptComments = request.getParameter("sellerAcceptComments");
                    
                    responseMessage = usmr.acceptAnItemOffer(hidItemOfferID, sellerAcceptComments);
                    if (responseMessage.endsWith("!")) { request.setAttribute("successMessage", responseMessage); } 
                    else { request.setAttribute("errorMessage", responseMessage); }
                    
                    request.setAttribute("itemOfferUserListSYS", usmr.viewAnItemOfferUserList(loggedInUsername, itemIDHid));
                    request.setAttribute("userAccountVec", usmr.viewUserProfileDetails(loggedInUsername));
                    request.setAttribute("userMessageListTopThreeSYS", usmr.viewUserMessageListTopThree(loggedInUsername));
                    pageAction = "PendingItemOfferDetailsSYS";
                    break;
                case "negotiateAnItemOfferSYS":
                    long itemIDHidd = Long.parseLong(request.getParameter("itemIDHidden"));
                    long hiddItemOfferID = Long.parseLong(request.getParameter("urlItemOfferID"));
                    String sellerNegotiateComments = request.getParameter("sellerNegotiateComments");
                    
                    responseMessage = usmr.negotiateAnItemOffer(hiddItemOfferID, sellerNegotiateComments);
                    if (responseMessage.endsWith("!")) { request.setAttribute("successMessage", responseMessage); } 
                    else { request.setAttribute("errorMessage", responseMessage); }
                    
                    request.setAttribute("itemOfferUserListSYS", usmr.viewAnItemOfferUserList(loggedInUsername, itemIDHidd));
                    request.setAttribute("userAccountVec", usmr.viewUserProfileDetails(loggedInUsername));
                    request.setAttribute("userMessageListTopThreeSYS", usmr.viewUserMessageListTopThree(loggedInUsername));
                    pageAction = "PendingItemOfferDetailsSYS";
                    break;
                case "rejectAnItemOfferSYS":
                    long itemIDHiddd = Long.parseLong(request.getParameter("itemIDHidden"));
                    long hidddItemOfferID = Long.parseLong(request.getParameter("urlItemOfferID"));
                    
                    responseMessage = usmr.rejectAnItemOffer(hidddItemOfferID);
                    if (responseMessage.endsWith("!")) { request.setAttribute("successMessage", responseMessage); } 
                    else { request.setAttribute("errorMessage", responseMessage); }
                    
                    request.setAttribute("itemOfferUserListSYS", usmr.viewAnItemOfferUserList(loggedInUsername, itemIDHiddd));
                    request.setAttribute("userAccountVec", usmr.viewUserProfileDetails(loggedInUsername));
                    request.setAttribute("userMessageListTopThreeSYS", usmr.viewUserMessageListTopThree(loggedInUsername));
                    pageAction = "PendingItemOfferDetailsSYS";
                    break;
                case "completeAnItemOfferSYS":
                    long itemIDComplete = Long.parseLong(request.getParameter("itemIDHidden"));
                    long itemOfferIDComplete = Long.parseLong(request.getParameter("urlItemOfferID"));
                    String itemStatusComplete = request.getParameter("itemStatus");
                    
                    responseMessage = usmr.completeAnItemOffer(itemOfferIDComplete, itemStatusComplete);
                    if (responseMessage.endsWith("!")) { request.setAttribute("successMessage", responseMessage); } 
                    else { request.setAttribute("errorMessage", responseMessage); }
                    
                    request.setAttribute("itemOfferUserListSYS", usmr.viewAnItemOfferUserList(loggedInUsername, itemIDComplete));
                    request.setAttribute("userAccountVec", usmr.viewUserProfileDetails(loggedInUsername));
                    request.setAttribute("userMessageListTopThreeSYS", usmr.viewUserMessageListTopThree(loggedInUsername));
                    pageAction = "PendingItemOfferDetailsSYS";
                    break;
                case "reopenAnItemOfferSYS":
                    long itemIDReopen = Long.parseLong(request.getParameter("itemIDHidden"));
                    long itemOfferIDReopen = Long.parseLong(request.getParameter("urlItemOfferID"));
                    String itemStatusReopen = request.getParameter("itemStatus");
                    
                    responseMessage = usmr.reopenAnItemOffer(itemOfferIDReopen, itemStatusReopen);
                    if (responseMessage.endsWith("!")) { request.setAttribute("successMessage", responseMessage); } 
                    else { request.setAttribute("errorMessage", responseMessage); }
                    
                    request.setAttribute("itemOfferUserListSYS", usmr.viewAnItemOfferUserList(loggedInUsername, itemIDReopen));
                    request.setAttribute("userAccountVec", usmr.viewUserProfileDetails(loggedInUsername));
                    request.setAttribute("userMessageListTopThreeSYS", usmr.viewUserMessageListTopThree(loggedInUsername));
                    pageAction = "PendingItemOfferDetailsSYS";
                    break;
                case "provideFeedbackSYS":
                    long itemIDFeedback = Long.parseLong(request.getParameter("itemIDHidden"));
                    long itemOfferIDFeedback = Long.parseLong(request.getParameter("urlItemOfferID"));
                    String ratingReview = request.getParameter("ratingReview");
                    
                    responseMessage = usmr.provideTransFeedback(loggedInUsername, itemOfferIDFeedback, ratingReview);
                    if (responseMessage.endsWith("!")) { request.setAttribute("successMessage", responseMessage); } 
                    else { request.setAttribute("errorMessage", responseMessage); }
                    
                    request.setAttribute("userAccountVec", usmr.viewUserProfileDetails(loggedInUsername));
                    request.setAttribute("userMessageListTopThreeSYS", usmr.viewUserMessageListTopThree(loggedInUsername));
                    
                    if(responseMessage.contains("about the buyer")) {
                        request.setAttribute("itemOfferUserListSYS", usmr.viewAnItemOfferUserList(loggedInUsername, itemIDFeedback));
                        pageAction = "PendingItemOfferDetailsSYS";
                    } else if(responseMessage.contains("about the seller")) {
                        request.setAttribute("userMarketplaceRatingInfoVec", usmr.viewUserMarketplaceRatingInfo(loggedInUsername));
                        request.setAttribute("userBuyerOfferListSYS", (ArrayList) usmr.viewPersonalBuyerOfferList(loggedInUsername));
                        pageAction = "UserItemOfferListSYS";
                    }
                    break;
                case "goToUserItemWishlistSYS":
                    request.setAttribute("userItemWishlistSYS", (ArrayList) usmr.viewUserItemWishlist(loggedInUsername));
                    request.setAttribute("itemCategoryStr", msmr.populateItemCategory());
                    
                    request.setAttribute("userAccountVec", usmr.viewUserProfileDetails(loggedInUsername));
                    request.setAttribute("userMessageListTopThreeSYS", usmr.viewUserMessageListTopThree(loggedInUsername));
                    pageAction = "UserItemWishlistSYS";
                    break;
                case "goToUserNotificationListSYS":
                    request.setAttribute("userAccountVec", usmr.viewUserProfileDetails(loggedInUsername));
                    request.setAttribute("userMessageListSYS", usmr.viewUserMessageList(loggedInUsername));
                    request.setAttribute("userMessageListTopThreeSYS", usmr.viewUserMessageListTopThree(loggedInUsername));
                    pageAction = "UserNotificationListSYS";
                    break;
                case "goToMyBuyerOfferListSYS":
                    request.setAttribute("userMarketplaceRatingInfoVec", usmr.viewUserMarketplaceRatingInfo(loggedInUsername));
                    request.setAttribute("userBuyerOfferListSYS", (ArrayList) usmr.viewPersonalBuyerOfferList(loggedInUsername));
                    request.setAttribute("userAccountVec", usmr.viewUserProfileDetails(loggedInUsername));
                    request.setAttribute("userMessageListTopThreeSYS", usmr.viewUserMessageListTopThree(loggedInUsername));
                    pageAction = "UserItemOfferListSYS";
                    break;
                case "cancelPersonalItemOfferSYS":
                    long itemOfferHiddenID = Long.parseLong(request.getParameter("itemOfferHiddenID"));
                    
                    responseMessage = usmr.cancelPersonalItemOffer(itemOfferHiddenID);
                    response.setContentType("text/plain");
                    response.getWriter().write(responseMessage);
                    break;
                case "editPersonalItemOfferSYS":
                    long itemOfferHidID = Long.parseLong(request.getParameter("itemOfferHiddenID"));
                    String revisedOfferPrice = request.getParameter("revisedItemOffer");
                    
                    responseMessage = usmr.editPersonalItemOffer(itemOfferHidID, revisedOfferPrice);
                    response.setContentType("text/plain");
                    response.getWriter().write(responseMessage);
                    break;
                case "goToViewChatListSYS":
                    String assocItemID = request.getParameter("assocItemID");
                    request.setAttribute("userChatBuyingListSYS", usmr.viewUserChatBuyingList(loggedInUsername, assocItemID));
                    request.setAttribute("userChatSellingListSYS", usmr.viewUserChatSellingList(loggedInUsername, assocItemID));
                    request.setAttribute("userMessageListTopThreeSYS", usmr.viewUserMessageListTopThree(loggedInUsername));
                    pageAction = "UserChatListSYS";
                    break;
                case "goToViewChatListContentSYS":
                    long hidChatID = Long.parseLong(request.getParameter("hidChatID"));
                    request.setAttribute("contentChatID", hidChatID);
                    request.setAttribute("chatListContentSYS", usmr.viewChatListContent(hidChatID));
                    request.setAttribute("chatContentInfoVecSYS", usmr.viewChatContentInfo(loggedInUsername, hidChatID));
                    request.setAttribute("assocBuyingListSYS", usmr.viewAssocBuyingList(loggedInUsername, ""));
                    request.setAttribute("assocSellingListSYS", usmr.viewAssocSellingList(loggedInUsername, ""));
                    request.setAttribute("userMessageListTopThreeSYS", usmr.viewUserMessageListTopThree(loggedInUsername));
                    pageAction = "UserChatListContentSYS";
                    break;
                case "addNewChatContent":
                    String receiverID = request.getParameter("receiverID");
                    String chatContent = request.getParameter("chatContent");
                    String buyerOrSellerStat = request.getParameter("buyerOrSellerStat");
                    String buyerOrSellerID = request.getParameter("buyerOrSellerID");
                    long hiddenItemID = Long.parseLong(request.getParameter("hiddenItemID"));
                    
                    responseMessage = usmr.addNewChatContent(loggedInUsername, receiverID, chatContent, 
                            buyerOrSellerStat, buyerOrSellerID, hiddenItemID);
                    response.setContentType("text/plain");
                    response.getWriter().write(responseMessage);
                    break;
                case "goToMyJobListing":
                    request.setAttribute("userAccountVec", usmr.viewUserProfileDetails(loggedInUsername));
                    request.setAttribute("userJobListing", (ArrayList) esmr.viewUserJobList(loggedInUsername));
                    pageAction = "UserJobListingSYS";
                    break;
                case "goToViewMyJobOfferSYS":
                    request.setAttribute("message", " ");
                    request.setAttribute("userAccountVec", usmr.viewUserProfileDetails(loggedInUsername));
                    request.setAttribute("myJobOfferList", (ArrayList)esmr.viewMyJobOffer(loggedInUsername));
                    pageAction = "ViewMyJobOfferSYS";
                    break;
                case "editMyJobOfferSYS":
                    String responseMessage = editJobOffer(request, loggedInUsername);
                    request.setAttribute("message", responseMessage);
                    request.setAttribute("userAccountVec", usmr.viewUserProfileDetails(loggedInUsername));
                    request.setAttribute("myJobOfferList", (ArrayList)esmr.viewMyJobOffer(loggedInUsername));
                    pageAction = "ViewMyJobOfferSYS";
                    break;
                case "goToDeleteMyJobOfferSYS":
                    request.setAttribute("userAccountVec", usmr.viewUserProfileDetails(loggedInUsername));
                    request.setAttribute("myJobOfferList", (ArrayList)esmr.viewMyJobOffer(loggedInUsername));
                    pageAction = "ViewMyJobOfferSYS";
                    break;
                case "goToCompanyReview":
                    request.setAttribute("userAccountVec", usmr.viewUserProfileDetails(loggedInUsername));
                    request.setAttribute("userMessageListTopThreeSYS", usmr.viewUserMessageListTopThree(loggedInUsername));
                    request.setAttribute("companyReviewListSYS", (ArrayList) vsmr.viewUserCompanyReview(loggedInUsername));
                    pageAction = "UserCompanyReview";
                    break;
                case "goToDeleteReview":
                    long delReviewID = Long.parseLong(request.getParameter("hiddenReviewID"));
                    if (vsmr.deleteReview(delReviewID)) {
                        System.out.println("Selected Review has been deleted successfully.");
                    } else {
                        System.out.println("Selected Review cannot be deleted. Please try again later.");
                    }
                    request.setAttribute("userAccountVec", usmr.viewUserProfileDetails(loggedInUsername));
                    request.setAttribute("userMessageListTopThreeSYS", usmr.viewUserMessageListTopThree(loggedInUsername));
                    request.setAttribute("companyReviewListSYS", (ArrayList) vsmr.viewUserCompanyReview(loggedInUsername));
                    pageAction = "UserCompanyReview";
                    break;
                case "goToCompanyRequest":
                    request.setAttribute("userAccountVec", usmr.viewUserProfileDetails(loggedInUsername));
                    request.setAttribute("userMessageListTopThreeSYS", usmr.viewUserMessageListTopThree(loggedInUsername));
                    request.setAttribute("companyRequestListSYS", (ArrayList) vsmr.viewUserCompanyRequest(loggedInUsername));
                    pageAction = "UserCompanyRequest";
                    break;
                case "goToResume":
                    request.setAttribute("userAccountVec", usmr.viewUserProfileDetails(loggedInUsername));
                    request.setAttribute("resumeListSYS", (ArrayList) vsmr.viewUserResume(loggedInUsername));
                    pageAction = "UserResume";
                    break;
                case "goToViewResumeDetails":
                    long resumeID = Long.parseLong(request.getParameter("hiddenResumeID"));
                    request.setAttribute("basicDetailsVec", vsmr.viewResumeBasicDetails(resumeID));
                    request.setAttribute("eduExprList", vsmr.viewEduExprList(resumeID));
                    request.setAttribute("workExprList", vsmr.viewWorkExprList(resumeID));
                    request.setAttribute("proExprList", vsmr.viewProjectExprList(resumeID));
                    pageAction = "ViewResumeDetailsSYS";
                    break;
                case "markNotificationSYS":
                    long msgContentID = Long.parseLong(request.getParameter("msgContentID"));
                    String msgSenderID = (String) request.getParameter("msgSenderID");
                    System.out.print(msgContentID);
                    System.out.print(msgSenderID);
                    
                    responseMessage = usmr.markNotification(msgContentID, msgSenderID);
                    response.setContentType("text/plain");
                    response.getWriter().write(responseMessage);
                    break;
                default:
                    break;
            }
            dispatcher = servletContext.getNamedDispatcher(pageAction);
            dispatcher.forward(request, response);
        }
        catch(Exception ex) {
            log("Exception in UserProfileSysUserController: processRequest()");
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
    public String getServletInfo() { return "User Profile System User Servlet"; }
    
    /* MISCELLANEOUS METHODS */
    private String getCookieUsername(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String loggedInUsername = null;
        if(cookies!=null){
            for(Cookie c : cookies){
                if(c.getName().equals("username") && !c.getValue().equals("")){
                    loggedInUsername = c.getValue();
                }
            }
        }
        return loggedInUsername;
    }
    
    public String editJobOffer(HttpServletRequest request, String username){
        
        long jobID = Long.parseLong(request.getParameter("jobIDHidden"));
        
        String offerPrice = request.getParameter("jobOfferPrice");
        if(offerPrice.equals("")) { offerPrice = request.getParameter("hiddenOfferPrice"); }
        
        String offerComment = request.getParameter("jobOfferDescription");
        if(offerComment.equals("")) { offerComment = request.getParameter("hiddenOfferDescription"); }
        
        System.out.println(offerPrice + " " + offerComment);
        
        String message = esmr.editJobOfferPrice(jobID, username, offerPrice, offerComment);
        
        System.out.println(message);
        
        return message;
    }
}