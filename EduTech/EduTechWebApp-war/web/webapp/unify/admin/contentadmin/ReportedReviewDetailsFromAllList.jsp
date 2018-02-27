<%@include file="/webapp/commoninfrastructure/SessionCheck.jspf" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Vector"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Unify Admin - Reported Review Details</title>

        <!-- CASCADING STYLESHEET -->
        <link href="css/unify/admin/baselayout/bootstrap-v3.1.1.min.css" rel="stylesheet" type="text/css" />
        <link href="css/unify/admin/baselayout/UnifyAdminBaseCSS.css" rel="stylesheet" type="text/css" />
        <link href="css/unify/admin/baselayout/UnifyAdminPlugins.css" rel="stylesheet" type="text/css" />
        <link href="css/unify/admin/baselayout/plugin/datatables.css" rel="stylesheet" type="text/css" />
        <link href="css/unify/admin/baselayout/plugin/datatables_bootstrap.css" rel="stylesheet" type="text/css" />
        <link href="css/unify/admin/baselayout/font-awesome-v4.7.0.min.css" rel="stylesheet" type="text/css" />
        <link href="css/unify/admin/baselayout/responsive.css" rel="stylesheet" type="text/css" />
        <link href="css/unify/admin/baselayout/icons.css" rel="stylesheet" type="text/css" />
        <link href="css/unify/admin/baselayout/easy-autocomplete/easy-autocomplete.css" rel="stylesheet" type="text/css">
        <link href="css/unify/admin/baselayout/leaflet/leaflet.css" rel="stylesheet" type="text/css">


        <!-- JAVASCRIPT -->
        <script type="text/javascript" src="js/unify/admin/basejs/jquery-v1.10.2.min.js"></script>
        <script type="text/javascript" src="js/unify/admin/basejs/bootstrap-v3.1.1.min.js"></script>
        <script type="text/javascript" src="js/unify/admin/basejs/lodash.compat-v2.0.0.min.js"></script>
        <script type="text/javascript" src="js/unify/admin/basejs/breakpoints-v1.0.js"></script>
        <script type="text/javascript" src="js/unify/admin/basejs/jquery.slimscroll-v1.3.1.min.js"></script>
        <script type="text/javascript" src="js/unify/admin/basejs/jquery.slimscroll.horizontal-v0.6.5.min.js"></script>
        <script type="text/javascript" src="js/unify/admin/basejs/jquery.sparkline-v2.1.2.min.js"></script>
        <script type="text/javascript" src="js/unify/admin/basejs/UnifyAdminAppJS.js"></script>
        <script type="text/javascript" src="js/unify/admin/basejs/UnifyAdminPluginJS.js"></script>
        <script type="text/javascript" src="js/unify/admin/basejs/UnifyAdminPluginFormComponentsJS.js"></script>
        <script type="text/javascript" src="js/unify/admin/basejs/UnifyAdminBaseJS.js"></script>
        <script type="text/javascript" src="js/unify/admin/basejs/leaflet/leaflet.js"></script>

    </head>
    <body style="background-color: #FFFFFF;">
        <%            Vector reportListVec = (Vector) request.getAttribute("reportedReviewVec");
            String reportID, reportStatus, reportDescription, reportDate, reportedReviewID,
                    reportedPosterID, reportedReporterID, reviewCompany, reviewTitle, reviewPro, reviewCon, reviewedDate;
            reportID = reportStatus = reportDescription = reportDate = reportedReviewID
                    = reportedPosterID = reportedReporterID = reviewCompany = reviewTitle = reviewPro = reviewCon = reviewedDate = "";
            if (reportListVec != null) {
                reportID = (String.valueOf(reportListVec.get(0)));
                reportStatus = (String.valueOf(reportListVec.get(1)));
                reportDescription = (String.valueOf(reportListVec.get(2)));
                reportDate = (String.valueOf(reportListVec.get(3)));
                reportedReviewID = (String.valueOf(reportListVec.get(4)));
                reportedPosterID = (String.valueOf(reportListVec.get(5)));
                reportedReporterID = (String.valueOf(reportListVec.get(6)));
                reviewedDate = (String.valueOf(reportListVec.get(7)));
                //int reportListVecSize = reportListVec.size();
                //System.out.println("report list size is: "+reportListVecSize);
                if (reportListVec.size() > 8) {
                    reviewCompany = (String.valueOf(reportListVec.get(8)));
                    reviewTitle = (String.valueOf(reportListVec.get(9)));
                    reviewPro = (String.valueOf(reportListVec.get(10)));
                    reviewCon = (String.valueOf(reportListVec.get(11)));
                }
            }
        %>




        <div style="margin: 30px 20px 0 20px">
            <div class="tabbable tabbable-custom">
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#reportInfo" data-toggle="tab">Report Details</a></li>
                    <li><a href="#reportedReview" data-toggle="tab">Review Reported</a></li>
                </ul>
                <%-- primary tab --%>
                <div class="tab-content">
                    <div class="tab-pane active" id="reportInfo">
                        <div class="form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12">Report ID:&nbsp;&nbsp;<u><%= reportID%></u></label>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12">Report Status:&nbsp;&nbsp;
                                <%
                                    if (reportStatus.equals("Resolved")) {
                                %>
                                <td><span class="label label-success">Resolved</span></td>
                                <%
                                } else {
                                %>
                                <td><span class="label label-warning">Unresolved</span></td>
                                <%
                                    }
                                %>
                            </label>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12">Report Description:&nbsp;&nbsp;<%= reportDescription%></label>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12">Report Date:&nbsp;&nbsp;<%= reportDate%></label>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12">Reported Review ID:&nbsp;&nbsp;<%= reportedReviewID%></label>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12">User ID of Review Poster:&nbsp;&nbsp;<%= reportedPosterID%></label>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12">User ID of Reporter:&nbsp;&nbsp;<%= reportedReporterID%></label>
                        </div>
                    </div>

                    <%-- secondary tab --%>
                    <div class="tab-pane" id="reportedReview">

                        <%--- to display review when found --%>
                        <%
                            if (reportListVec.size() > 8) {
                        %>
                        <div class="form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12">Company Reviewed:&nbsp;&nbsp;<%= reviewCompany%></label>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12">Title of Review:&nbsp;&nbsp;<%= reviewTitle%></label>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12">Review Pros:&nbsp;&nbsp;<%= reviewPro%></label>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12">Review Cons:&nbsp;&nbsp;<%= reviewCon%></label>
                        </div>
                        <%
                        } else {
                        %>
                        <div class="form-group">
                            <label class="control-label col-md-12 col-sm-12 col-xs-12"><font color = "red"> <b>REVIEW HAS BEEN DELETED FROM SYSTEM&nbsp;&nbsp;</b></font></label>
                        </div>
                        <%
                            }
                        %> 

                    </div>
                </div>
            </div>
        </div>

        <hr width="80%">
        <div class="ln_solid"></div>
        <div class="form-group">
            <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                <table border="0" style="margin: auto;">
                    <tr>
                        <%
                            if (reportStatus.equals("Unresolved") && !reviewCompany.equals("")) {
                        %>
                        <%-- resolve and do nothing --%>
                        <td>
                            <form action="ContentAdmin?pageTransit=goToAllReportedListing#companyReview" method="GET" target="_parent">
                                <input type="hidden" name="pageTransit" value="resolveReviewReportFromAllList"/>
                                <input type="hidden" name="reportID" value="<%= reportID%>" />
                                <input type="hidden" name="reportStatus" value="<%= reportStatus%>" />
                                <button type="submit" class="btn btn-primary">Resolved (Do Nothing)</button>
                            </form>
                        </td>
                        <%-- resolve and delete review --%>
                        <td>
                            <form action="ContentAdmin?pageTransit=goToAllReportedListing#companyReview" method="GET" target="_parent">
                                <input type="hidden" name="pageTransit" value="resolveDeleteReviewReportFromAllList"/>
                                <input type="hidden" name="reportID" value="<%= reportID%>" />
                                <input type="hidden" name="reportStatus" value="<%= reportStatus%>" />
                                <input type="hidden" name="reportedReviewID" value="<%= reportedReviewID%>" />
                                <button type="submit" class="btn btn-primary" onclick="return confirm('Confirm deletion?')">Delete Review & Resolve</button>
                            </form>
                        </td>
                        <%
                        } else if (reportStatus.equals("Unresolved") && reviewCompany.equals("")) {
                        %>
                        <%-- resolve and do nothing --%>
                        <td>
                            <form action="ContentAdmin?pageTransit=goToAllReportedListing#companyReview" method="GET" target="_parent">
                                <input type="hidden" name="pageTransit" value="resolveReviewReportFromAllList"/>
                                <input type="hidden" name="reportID" value="<%= reportID%>" />
                                <input type="hidden" name="reportStatus" value="<%= reportStatus%>" />
                                <button type="submit" class="btn btn-primary">Resolved (Do Nothing)</button>
                            </form>
                        </td>

                        <%
                        } else {
                        %>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12"><b>NO FURTHER ACTION NEEDED&nbsp;&nbsp;</b></label>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Report Resolved On:&nbsp;&nbsp;<%= reviewedDate%></label>
                    </div>
                    <%
                        }
                    %>
                    </tr>
            </div>
        </div>


    </body>
</html>