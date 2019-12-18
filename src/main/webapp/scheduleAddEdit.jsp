<!DOCTYPE html>
<%@include file="taglib.jsp"%>
<html lang="en">
<%@include file="head.jsp"%>

<body>

<div class="wrapper">
    <div class="container-fluid">
        <%@include file="header.jsp"%>
        <%@include file="navbar.jsp"%>

        <c:if test="${currentWeather != null}">
            <p>${currentWeather}</p>
        </c:if>

        <h2 class="text-center">
            <c:if test="${userAction == 'edit'}">
                Edit Weekly Schedule
            </c:if>
            <c:if test="${userAction == 'add'}">
                Add Weekly Schedule
            </c:if>
        </h2>
        <p class="text-right"><a href="searchTimeOffRequest?searchType=weeklyRequests&searchValue=${schedule.startDate} 00:00" target="_blank">View Time Off Requests for the week</a></p>

        <form id="editScheduleForm" role="form" data-toggle="validator"
                   class="form-horizontal"
                   action="addEditScheduleServlet?userAction=${userAction}"
                   method="POST">

            <c:if test="${userAction == 'edit'}">
                <input type="hidden"
                       name="id"
                       value="<c:out value='${schedule.id}' />"
                />
            </c:if>

            <input type="hidden"
               name="actionToPerform"
               value="<c:out value='${userAction}' />"
            />

        <div class="row form-group">
            <label class="control-label d-inline-block text-right col-2 col-sm-4" for="startDate">For week starting</label>
            <div class="col-10 col-sm-1">
                <input type="text" class="form-control d-inline-block" id="startDate"
                       name="startDate" readonly
                <c:if test="${userAction == 'edit'}">
                       readonly
                </c:if>
                       value = "${schedule.getStartDateForDisplay()}"
                       data-error="Please enter the username.">
            </div>

            <div class="col-0 col-sm-1 "></div>
            <label class="control-label d-inline-block text-right col-2 col-sm-1" for="name">Week Ending</label>
            <div class="col-9 col-sm-1">
                <input type="text" class="form-control d-inline-block" id="name"
                       name="endDate"
                       readonly
                       value = "${schedule.getEndDateForDisplay()}">
            </div>
        </div>


        <div class="row">
            <div class="col-0 col-sm-2"></div>
            <div class="col-12 col-sm-4">
                <h3 class="text-sm-left">${scheduleMessage}</h3>
            </div>
            <c:if test="${scheduleMessage != null}" >
                <c:remove var="scheduleMessage"  />
            </c:if>
            <div class="col-0 col-sm-6"></div>
        </div>

        <table id="scheduleTable"  class="table table-striped table-bordered display dt-responsive nowrap"  >
            <tr>${tableHeader}</tr>
            <c:forEach var="scheduleDetail" items="${schedule.scheduleDetails}">
                <div class="col-0 col-sm-2"></div>
                <input type="hidden" id="scheduleId"
                       name="detail"
                       value="${scheduleDetail.id}">
                <input type="hidden" id="userName"
                       name="detail"
                       value="${scheduleDetail.userName}">
                <tr>
                    <td>${scheduleDetail.user.getFirstName()} ${scheduleDetail.user.getLastName()}</td>
                    <td><input value = "${scheduleDetail.startTime1}" size="8" name="detail" /></td>
                    </td>
                    <td><input value = "${scheduleDetail.startTime2}" size="8" name="detail" /></td>
                    <td><input value = "${scheduleDetail.startTime3}" size="8" name="detail" /></td>
                    <td><input value = "${scheduleDetail.startTime4}" size="8" name="detail" /></td>
                    <td><input value = "${scheduleDetail.startTime5}" size="8" name="detail" /></td>
                    <td><input value = "${scheduleDetail.startTime6}" size="8" name="detail" /></td>
                    <td><input value = "${scheduleDetail.startTime7}" size="8" name="detail" /></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input value = "${scheduleDetail.endTime1}" size="8" name="detail" /></td>
                    <td><input value = "${scheduleDetail.endTime2}" size="8" name="detail" /></td>
                    <td><input value = "${scheduleDetail.endTime3}" size="8" name="detail" /></td>
                    <td><input value = "${scheduleDetail.endTime4}" size="8" name="detail" /></td>
                    <td><input value = "${scheduleDetail.endTime5}" size="8" name="detail" /></td>
                    <td><input value = "${scheduleDetail.endTime6}" size="8" name="detail" /></td>
                    <td><input value = "${scheduleDetail.endTime7}" size="8" name="detail" /></td>
                </tr>
            </c:forEach>
        </table>


        <div class="row">
            <div class="col-4 col-sm-5"></div>
            <div class="col-4 col-sm-2">
                <button type="submit" class="btn btn-primary col-sm-offset-3"
                        data-disable="true">
                    <c:if test="${userAction == 'edit'}">
                        Update
                    </c:if>
                    <c:if test="${userAction == 'add'}">
                        Add
                    </c:if>
                </button>
            </div>
            <div class="col-4 col-sm-5"></div>
        </div>
        </form>
    </div>
</div>
</body>
</html>
