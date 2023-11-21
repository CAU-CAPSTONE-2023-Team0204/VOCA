import React from "react";
import NavigationBar from "./NavigationBar";
import TeacherSidebar from "./TeacherSidebar";

const StudentTestResult = () => {
  return (
    <div id="classmain_page_container">
      <React.Fragment>
        <NavigationBar />
      </React.Fragment>

      <div id="main_wrapper">
        <React.Fragment>
          <TeacherSidebar />
        </React.Fragment>
        <div id="contents_wrapper"></div>
      </div>
    </div>
  );
};

export default StudentTestResult;
