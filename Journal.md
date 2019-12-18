# Individual Project Reflections/Notes

### Sunday, Sept 15th

### Sunday, Sept 14th 2 hours
Added ProjectPlan for the individual project, using Paula's example as
a model.  Not sure at this point in the semester about what technologies we we be using or learning so it left most of 
what Paula had in on the example project as a base.

### Monday, Sept 15th 2 hours
Started creating initial draft of some of the screens that will be needed, using paint for now.


### Tuesday, Sept 16th 2 hours
Created some additional initial version of some screens.  Started working on a flow document, but not sure if we are 
documenting from the user perspective, the actions that will occur of if documenting the flow from a technical perspective.

### Wednesday, Sept 17th
Class night.  Made additional updates to the readme file.  Looked for an API to use again.  Initially will use 
Workingdays API, which I can incorporate into the weekly schedule (create or display) to display the wording of any 
holidays that occur during that week.  Can display it below the day/date.

### Saturday, Sept 21st
Re-worked a couple screen designs, for home screen and for Add/Edit Users.

### Sunday, Sept 22nd
Finished cleaning up user stories (for now).  Updated screen shots and added a couple new one.  Put them in same order
as listed in application flow document.  Creating initial screen designs helped worked through application flow, somewhat.

### Thursday, Sept 26th
Created inital version of some of the jsp's

### Saturday, Oct 26th
Added tomcat authentication changes for user screens.  Set up AWS and created database for application on AWS.

### Sunday, Nov 3rd
Add functionality for Add user.  Need to work on roles part of it.

### Monday, Nov 4th
Updated login icon on navbar to prompt user for logon.  Updated home page to not display the scheduling and
user options if logged on user is not an Administrator or Manager.

### Sunday, Nov 24th
Add user name variable to role, since Hibernate does not seem to handle populating it, was leaving it as null.  Worked
on adding a modal to the Add/Edit User jsp be be able to add additional roles for the user.

### Sun, Dec 1st
Create Schedule and Schedule_Detail tables as well and the java class and unit test files for those tables.

### Mon, Dec 2nd
Add code to search and display schedule and schedule detail data.  Start for add and edit of a 
schedule and it's details.

### Tuesday, Dec 3rd
Worked on using Hibernate Query Language annotations

### Sunday, Dec 8th
Was having issue with post not executing for AddEditScheduleServlet.java after submitting new
schedule on scheduleAddEdit.jsp.  Found there was a unexpected end of file message showing in Intellij
It turned out to be an error because of a missing closing form tag.  After adding that in the doPost
in the servlet was exeuting.

### Tuesday, Dec 10th
Added an image to the header jumbotron.  Was trying to use the image as a background image, but was
having problems getting it working correctly.  So for not have it as a centered image.

### Thursday, Dec 12th
Trying to work on Jquery edits again.  Have to find info on the various cdn's that get pulled in for
bootstrap, jquery, etc and what order they need to be in  Need to make sure the varoius versions are all 
the correct versions that are compatible with each other.  This may be part of the
problems with some custom jquery validations not working.  Will need a lot more time to work on
that.  Will work on other outstanding pieces prior to those validation edits.

### Saturday, Dec 14th
Updated database tables in AWS and added some records in the various tables.  Deployed more recent
code to AWS.  Cleaned up some minor bugs that I found as I was making the demo video in powerpoint.

### Sunday, Dec 15th
Changed call for weather api to use class with properties file instead of having hard coded info
in a method.  Changed the search and add/edit screens and index page to display the current weather 
attribute info that is set by the call to the weather api

### Tuesday, Dec 17th
Fixed my datatables on the 3 search screens.  Wasn't using thead and tbody tags.  Now can sort by all
columns.
