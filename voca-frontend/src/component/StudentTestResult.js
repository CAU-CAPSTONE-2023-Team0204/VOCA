import React from "react";
import NavigationBar from "./NavigationBar";
import TeacherSidebar from "./TeacherSidebar";
import { useParams } from "react-router-dom";

const StudentTestResult = () => {
  const { class_id } = useParams();
  return (
    <div id="classmain_page_container">
      <React.Fragment>
        <NavigationBar />
      </React.Fragment>

      <div id="main_wrapper">
        <React.Fragment>
          <TeacherSidebar class_id={class_id} selected="test" />
        </React.Fragment>
        <div id="contents_wrapper"></div>
      </div>
    </div>
  );
};

export default StudentTestResult;
