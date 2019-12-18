<!DOCTYPE html>
<%@include file="taglib.jsp"%>
<c:set var="title" value="Time Off Request Search" />
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

        <h2 class="text-center">Time Off Requests Search</h2>

        <div class="justify-content-center align-items-center">

            <form name="timeOffRequestSearchForm"
                  action="searchTimeOffRequest">

                <div class="row">
                    <div class="col-0 col-md-4"></div>

                    <div class="col-12 col-md-4">
                        <p class="font-weight-bold"><br>Search Type: </p>
                    </div>

                    <div class="col-0 col-md-4"></div>
                </div>

                <div class="row">
                    <div class="col-0 col-md-4"></div>

                    <div class="col-12 col-md-4">
                        <div class="form-check-inline">
                            <label class="form-check-label">
                                <input type="radio" id="radioAll" class="form-check-input" name="searchType" value="allTimeOffRequests" checked>All Users
                            </label>
                        </div>
                        <div class="form-check-inline">
                            <label class="form-check-label">
                                <input type="radio" id="radioUserName" class="form-check-input mb-sm-3" name="searchType" value="UserName">By User Name
                            </label>
                        </div>
                    </div>

                    <div class="col-0 col-md-4"></div>
                </div>


                <div class="row">
                    <div class="col-0 col-md-4"></div>

                    <div class="col-12 col-md-4">
                        <br>
                        <p class="font-weight-bold">User Name:</p>
                    </div>

                    <div class="col-0 col-md-4"></div>
                </div>

                <div class="row">
                    <div class="col-0 col-md-4"></div>

                    <div class="col-12 col-md-4">
                        <input type="text" class="form-control" name="searchValue" id="searchValue" >
                    </div>

                    <div class="col-0 col-md-4"></div>
                </div>


                <div class="row">
                    <div class="col-0 col-md-4"></div>
                    <br>
                    <div class="col-12 col-md-4">
                        <button type="submit" id="submitButton" class="btn btn-success mr-sm-4 mt-sm-4 mb-sm-3">Submit</button>
                        <button type="reset" class="btn btn-primary mt-sm-4 mb-sm-3">Clear</button>
                    </div>

                    <div class="col-0 col-md-4"></div>
                </div>

            </form>


        </div>
    </div>
</div>

<script src="js/timeOffRequestSearch.js"></script>
<!-- check if doing a search by user name selected.  If so, user name value is required -->

<script type=text/javascript">
    $(document.ready(function() {
$('#submittButton').on("click", function() {
    alert("the submit button has been clicked");
    });
    });
</script>

</body>
</html>