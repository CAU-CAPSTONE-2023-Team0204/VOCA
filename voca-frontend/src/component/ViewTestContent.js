import React from "react";
import NavigationBar from "./NavigationBar";
import TeacherSidebar from "./TeacherSidebar";
import { useParams } from "react-router-dom";

import "../styles/test_content.css";

const ViewTestContent = () => {
  const { class_id } = useParams();
  return (
    <div id="test_content_page_container">
      <React.Fragment>
        <NavigationBar />
      </React.Fragment>

      <div id="main_wrapper">
        <React.Fragment>
          <TeacherSidebar class_id={class_id} selected="test" />
        </React.Fragment>
        <div id="contents_wrapper">
          <div id="title"> 시험내용 확인 </div>
        </div>
      </div>
    </div>
  );
};

export default ViewTestContent;
