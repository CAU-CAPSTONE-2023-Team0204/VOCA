import React from "react";
import NavigationBar from "./NavigationBar";
import TeacherSidebar from "./TeacherSidebar";
import useAxiosPrivate from "../hooks/useAxiosPrivate";

import "../styles/class_tests.css";

const TEST_LIST_URL = "/api/test/result";

const ClassTests = () => {
  const axiosPrivate = useAxiosPrivate();

  //const response = axiosPrivate.get(TEST_LIST_URL);
  const testList = [
    {
      name: "정기 테스트 1",
      test_date: "2023/10/09",
      total_student: 10,
      submitted_student: 7,
      key: "0",
    },
    {
      name: "월간 시험 3",
      test_date: "2023/10/10",
      total_student: 12,
      submitted_student: 10,
      key: "0",
    },
    {
      name: "10월 2주차",
      test_date: "2023/10/11",
      total_student: 14,
      submitted_student: 14,
      key: "0",
    },
  ];

  const manualMakeButtonHandler = () => {};

  return (
    <div id="classtests_page_container">
      <React.Fragment>
        <NavigationBar />
      </React.Fragment>

      <div id="main_wrapper">
        <React.Fragment>
          <TeacherSidebar />
        </React.Fragment>
        <div id="contents_wrapper">
          <div id="contents_title_container">
            <div>내 시험</div>
            <div id="button_container">
              <a href="/class/vocablist" className="make_test_button">
                자동출제
              </a>
              <a href="/class/test/create/manual" className="make_test_button">
                수동출제
              </a>
            </div>
          </div>
          <div id="test_list_container">
            <div id="table_label">
              <p className="test_number">번호</p>
              <p className="test_data">이름</p>
              <p className="test_data">응시자</p>
              <p className="test_data">시험 일자</p>
            </div>
            {testList.map((test, i) => (
              <a
                key={i}
                href={"/class/test/results/" + test.key}
                className="test_link"
              >
                <p className="test_number">{i + 1}</p>
                <p className="test_data">{test.name}</p>
                <p className="test_data">
                  {test.submitted_student}/{test.total_student}
                </p>
                <p className="test_data">{test.test_date}</p>
              </a>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default ClassTests;
